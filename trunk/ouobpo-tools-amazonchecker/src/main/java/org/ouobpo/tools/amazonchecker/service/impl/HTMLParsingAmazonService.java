package org.ouobpo.tools.amazonchecker.service.impl;

import java.math.BigDecimal;
import java.util.Currency;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.ouobpo.tools.amazonchecker.Constants;
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

  private HttpClient          fHttpClient;

  public HTMLParsingAmazonService() {
    fHttpClient = new HttpClient();
  }

  public BookData getBookData(String asin) throws ServiceException {
    String page = readWebPage(Constants.BOOK_URL_BASE + asin);

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

  private String readWebPage(String url) throws ServiceException {
    HttpMethod method = new GetMethod(url);
    // User-Agentを偽装しないとユーズド価格を取得できない
    method.setRequestHeader(
        "User-Agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
    String page = null;
    try {
      int status = fHttpClient.executeMethod(method);
      if (HttpStatus.SC_OK != status) {
        LOGGER.warn("ページ読込に失敗: ({}) {}", status, url);
      } else {
        page = method.getResponseBodyAsString();
      }
    } catch (Exception e) {
      LOGGER.warn("ページ読込に失敗: " + url, e);
    } finally {
      method.releaseConnection();
    }

    if (StringUtils.isEmpty(page)) {
      // ページの読込に失敗
      throw new ServiceException("ページの読込に失敗: " + url);
    }

    // ***デバッグログ***
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(page);
    }

    return page;
  }

  private String extractTitle(String page) {
    // 第1箇所から取得
    String title = StringUtils.substringBetween(
        page,
        HTML_TITLE_BEGIN_1,
        HTML_TITLE_END_1);
    // 取得できなければ第2箇所より
    if (title == null) {
      LOGGER.warn("タイトル読込失敗(1): {}xxx{}", HTML_TITLE_BEGIN_1, HTML_TITLE_END_1);
      title = StringUtils.substringBetween(
          page,
          HTML_TITLE_BEGIN_2,
          HTML_TITLE_END_2);
    }
    // 取得できなければ第3箇所より
    if (title == null) {
      LOGGER.warn("タイトル読込失敗(2): {}xxx{}", HTML_TITLE_BEGIN_2, HTML_TITLE_END_2);
      title = StringUtils.substringBetween(
          page,
          HTML_TITLE_BEGIN_3,
          HTML_TITLE_END_3);
    }
    if (title == null) {
      LOGGER.warn("タイトル読込失敗(3): {}xxx{}", HTML_TITLE_BEGIN_3, HTML_TITLE_END_3);
    }
    return StringEscapeUtils.unescapeHtml(StringUtils.trim(title));
  }

  private Money extractListPrice(String page, String asin)
      throws ServiceException {
    Money price = null;
    try {
      price = string2money(StringUtils.substringBetween(
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
      String priceListPage = readWebPage(Constants.BOOK_PRICE_LIST_URL_BASE
          + asin
          + "?condition=new");
      price = string2money(StringUtils.substringBetween(
          priceListPage,
          HTML_MARKETPLACE_LIST_BEGIN,
          HTML_MARKETPLACE_LIST_END));
    }

    return price;
  }

  private Money extractUsedPrice(String page) {
    try {
      return string2money(StringUtils.substringBetween(
          page,
          HTML_USED_BEGIN,
          HTML_USED_END));
    } catch (Exception e) {
      // ユーズドが存在しない場合
      return null;
    }
  }

  private static Money string2money(String amount) {
    return new Money(BigDecimal.valueOf(Long.parseLong(amount.replaceAll(
        "[,]",
        ""))), Currency.getInstance("JPY"));
  }
}
