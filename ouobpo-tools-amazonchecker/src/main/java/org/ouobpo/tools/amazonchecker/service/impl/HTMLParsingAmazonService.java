package org.ouobpo.tools.amazonchecker.service.impl;

import static org.apache.commons.lang.StringEscapeUtils.*;
import static org.apache.commons.lang.StringUtils.*;
import static org.ouobpo.tools.amazonchecker.Constants.*;
import static org.ouobpo.tools.amazonchecker.util.HttpUtils.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.ouobpo.tools.amazonchecker.exception.ServiceException;
import org.ouobpo.tools.amazonchecker.service.IAmazonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domainlanguage.money.Money;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
public class HTMLParsingAmazonService implements IAmazonService {
  private static final Logger LOGGER                      = LoggerFactory.getLogger(HTMLParsingAmazonService.class);

  private static final String HTML_TITLE_BEGIN_1          = "id=\"prodImage\" width=\"240\" height=\"240\" border=\"0\" alt=\"";
  private static final String HTML_TITLE_END_1            = "\"";
  private static final String HTML_TITLE_BEGIN_2          = "<span id=\"btAsinTitle\" style=\"font-weight:bold;\">";
  private static final String HTML_TITLE_END_2            = "</span>";
  private static final String HTML_TITLE_BEGIN_3          = "<title>Amazon.co.jp：";
  private static final String HTML_TITLE_END_3            = ":";
  private static final String HTML_LIST_BEGIN             = "<td><b class=\"price\">￥ ";
  private static final String HTML_LIST_END               = " （税込）</b>";
  private static final String HTML_MARKETPLACE_LIST_BEGIN = "<span class=\"price\">￥ ";
  private static final String HTML_MARKETPLACE_LIST_END   = "</span>";
  private static final String HTML_USED_BEGIN             = "新品/中古商品を見る</a>： <span class=\"price\">￥ ";
  private static final String HTML_USED_END               = "</span>";

  public BookData getBookData(String asin) throws ServiceException {
    String page = readWebPage(BOOK_URL_BASE + asin);

    String title = extractTitle(page);
    Money listPrice = extractListPrice(page, asin);
    Money usedPrice = extractUsedPrice(page);

    // ***デバッグログ***
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("タイトル: {}", title);
      LOGGER.debug("価格: {}, USED: {}", listPrice, usedPrice);
    }

    return new BookData(title, listPrice, usedPrice);
  }

  private String extractTitle(String page) {
    // 第1箇所から取得
    String title = substringBetween(page, HTML_TITLE_BEGIN_1, HTML_TITLE_END_1);
    // 取得できなければ第2箇所より
    if (title == null) {
      LOGGER.warn("タイトル読込失敗(1): {}xxx{}", HTML_TITLE_BEGIN_1, HTML_TITLE_END_1);
      title = substringBetween(page, HTML_TITLE_BEGIN_2, HTML_TITLE_END_2);
    }
    // 取得できなければ第3箇所より
    if (title == null) {
      LOGGER.warn("タイトル読込失敗(2): {}xxx{}", HTML_TITLE_BEGIN_2, HTML_TITLE_END_2);
      title = substringBetween(page, HTML_TITLE_BEGIN_3, HTML_TITLE_END_3);
    }
    if (title == null) {
      LOGGER.warn("タイトル読込失敗(3): {}xxx{}", HTML_TITLE_BEGIN_3, HTML_TITLE_END_3);
    }
    return unescapeHtml(trim(title));
  }

  private Money extractListPrice(String page, String asin)
      throws ServiceException {
    Money price = null;
    try {
      price = stringToMoney(substringBetween(
          page,
          HTML_LIST_BEGIN,
          HTML_LIST_END));
    } catch (Exception e) {
      // 新品が存在しない場合
      // NOTE: 新品の取り扱いがない書籍もある
    }

    if (price == null) {
      // Amazonの新品が存在しなかった場合は、マーケットプレイスからも
      // 新品を探す
      String priceListPage = readWebPage(BOOK_PRICE_LIST_URL_BASE
          + asin
          + "?condition=new");
      price = stringToMoney(substringBetween(
          priceListPage,
          HTML_MARKETPLACE_LIST_BEGIN,
          HTML_MARKETPLACE_LIST_END));
    }

    return price;
  }

  private Money extractUsedPrice(String page) {
    try {
      return stringToMoney(substringBetween(
          page,
          HTML_USED_BEGIN,
          HTML_USED_END));
    } catch (Exception e) {
      // ユーズドが存在しない場合
      return null;
    }
  }

  private static Money stringToMoney(String amount) {
    return new Money(BigDecimal.valueOf(Long.parseLong(amount.replaceAll(
        "[,]",
        ""))), Currency.getInstance("JPY"));
  }
}
