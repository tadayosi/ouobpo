package org.ouobpo.tools.amazonchecker.util;

import static org.apache.commons.lang.StringUtils.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.ouobpo.tools.amazonchecker.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hanao
 * @since 2008/12/31
 * @version $Id$
 */
public class HttpUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

  public static String readWebPage(String url) throws ServiceException {
    HttpMethod method = new GetMethod(url);
    // User-Agentを偽装しないとユーズド価格を取得できない
    method.setRequestHeader(
        "User-Agent",
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
    String page = null;
    try {
      int status = new HttpClient().executeMethod(method);
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

    if (isEmpty(page)) {
      // ページの読込に失敗
      throw new ServiceException("ページの読込に失敗: " + url);
    }

    // ***デバッグログ***
    if (LOGGER.isTraceEnabled()) {
      LOGGER.trace(page);
    }

    return page;
  }

  /** インスタンス化して使わない。 */
  private HttpUtils() {}
}
