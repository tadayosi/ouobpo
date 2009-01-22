package org.ouobpo.tools.amazonchecker.service.impl;

import static org.apache.commons.lang.StringUtils.*;
import static org.ouobpo.tools.amazonchecker.util.HttpUtils.*;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Currency;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.ouobpo.tools.amazonchecker.Configuration;
import org.ouobpo.tools.amazonchecker.exception.ServiceException;
import org.ouobpo.tools.amazonchecker.service.IAmazonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.domainlanguage.money.Money;

/**
 * @author hanao
 * @since 2008/12/31
 * @version $Id$
 */
public class AWSRestAmazonService implements IAmazonService {
  private static final Logger LOGGER              = LoggerFactory.getLogger(AWSRestAmazonService.class);

  private static final String AWS_URL             = "http://ecs.amazonaws.jp/onca/xml";
  private static final String AWS_PARAMS_TEMPLATE = "?Service=AWSECommerceService"
                                                      + "&AWSAccessKeyId=041WR28EQVEF1112J6G2"
                                                      + "&Operation=ItemLookup"
                                                      + "&ItemId=%s"
                                                      + "&Condition=All"
                                                      + "&MerchantId=All"
                                                      + "&ResponseGroup=Offers,Small";

  public BookData getBookData(String asin) throws ServiceException {
    String xml = lookupItem(asin);

    String title = title(xml);
    Money listPrice = listPrice(xml);
    Money usedPrice = usedPrice(xml);

    // ***デバッグログ***
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("タイトル: {}", title);
      LOGGER.debug("価格: {}, USED: {}", listPrice, usedPrice);
    }

    return new BookData(title, listPrice, usedPrice);
  }

  protected String lookupItem(String asin) throws ServiceException {
    String url = AWS_URL + String.format(AWS_PARAMS_TEMPLATE, asin);
    Configuration config = Configuration.instance();
    if (!config.isProxySet()) {
      return get(url);
    }
    if (!config.isProxyAuthenticationSet()) {
      return getViaProxy(url, config.proxyHost(), config.proxyPort());
    }
    return getViaProxy(
        url,
        config.proxyHost(),
        config.proxyPort(),
        config.proxyUser(),
        config.proxyPassword());
  }

  private static String title(String xml) throws ServiceException {
    return readElement(
        xml,
        "/ItemLookupResponse/Items/Item/ItemAttributes/Title/text()");
  }

  private static Money listPrice(String xml) throws ServiceException {
    String amount = readElement(
        xml,
        "/ItemLookupResponse/Items/Item/OfferSummary/LowestNewPrice/Amount/text()");
    return stringToMoney(amount);
  }

  private static Money usedPrice(String xml) throws ServiceException {
    String amount = readElement(
        xml,
        "/ItemLookupResponse/Items/Item/OfferSummary/LowestUsedPrice/Amount/text()");
    return stringToMoney(amount);
  }

  private static String readElement(String xml, String expression)
      throws ServiceException {
    XPath xpath = XPathFactory.newInstance().newXPath();
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document document = builder.parse(new InputSource(new StringReader(xml)));
      return xpath.evaluate(expression, document);
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  private static Money stringToMoney(String amount) {
    if (isEmpty(amount)) {
      return null;
    }
    return new Money(BigDecimal.valueOf(Long.parseLong(amount.replaceAll(
        "[,]",
        ""))), Currency.getInstance("JPY"));
  }
}
