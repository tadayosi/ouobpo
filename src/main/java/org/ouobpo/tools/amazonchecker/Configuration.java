package org.ouobpo.tools.amazonchecker;

import static org.apache.commons.lang.StringUtils.*;
import static org.ouobpo.tools.amazonchecker.Constants.*;

import java.io.IOException;
import java.util.Properties;

import org.ouobpo.tools.amazonchecker.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
public class Configuration {
  private static final Logger        LOGGER          = LoggerFactory.getLogger(Configuration.class);

  private static final String        PROP_VERSION    = "amazonchecker.version";
  private static final String        PROP_BROWSER    = "amazonchecker.browser";
  private static final String        PROP_PROXY_HOST = "amazonchecker.proxy.host";
  private static final String        PROP_PROXY_PORT = "amazonchecker.proxy.port";

  private static final Configuration SINGLETON       = new Configuration();

  public static Configuration instance() {
    return SINGLETON;
  }

  private String  fVersion;
  private String  fBrowser;
  private String  fProxyHost;
  private Integer fProxyPort;

  private Configuration() {
    loadSystemConfiguration();
    loadUserConfiguration();
  }

  private void loadSystemConfiguration() {
    try {
      Properties props = PropertiesUtils.loadProperties(AMAZONCHECKER_SYSTEM_PROPERTIES);
      fVersion = props.getProperty(PROP_VERSION);
    } catch (IOException e) {
      LOGGER.error("システム設定ファイル読込に失敗", e);
    }
  }

  private void loadUserConfiguration() {
    try {
      Properties props = PropertiesUtils.loadProperties(AMAZONCHECKER_PROPERTIES);
      fBrowser = props.getProperty(PROP_BROWSER);
      fProxyHost = props.getProperty(PROP_PROXY_HOST);
      fProxyPort = getIntegerProperty(props, PROP_PROXY_PORT);

      // ***ログ***
      LOGGER.info("{}={}", PROP_BROWSER, fBrowser);
      LOGGER.info("{}={}", PROP_PROXY_HOST, fProxyHost);
      LOGGER.info("{}={}", PROP_PROXY_PORT, fProxyPort);

    } catch (IOException e) {
      LOGGER.error("ユーザ設定ファイル読込に失敗", e);
    }
  }

  private static Integer getIntegerProperty(Properties props, String key) {
    String value = props.getProperty(key);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  //----------------------------------------------------------------------------
  // 設定値
  //----------------------------------------------------------------------------

  public String version() {
    return fVersion;
  }

  public String browser() {
    return fBrowser;
  }

  public boolean isBrowserSet() {
    return isNotEmpty(fBrowser);
  }

  public String proxyHost() {
    return fProxyHost;
  }

  public Integer proxyPort() {
    return fProxyPort;
  }

  public boolean isProxySet() {
    return isNotEmpty(fProxyHost) && fProxyPort != null;
  }
}
