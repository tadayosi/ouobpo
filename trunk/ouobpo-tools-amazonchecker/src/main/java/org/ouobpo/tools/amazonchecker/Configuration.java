package org.ouobpo.tools.amazonchecker;

import java.io.IOException;
import java.util.Properties;

import org.ouobpo.tools.amazonchecker.util.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SATO, Tadayosi
 * @version $Id: Configuration.java 887 2008-05-11 12:29:14Z hanao $
 */
public class Configuration {
  private static final Logger        LOGGER          = LoggerFactory.getLogger(Configuration.class);

  private static final String        PROP_VERSION    = "amazonchecker.version";
  private static final String        PROP_BROWSER    = "amazonchecker.browser";

  private static final String        DEFAULT_BROWSER = "explorer";

  private static final Configuration SINGLETON       = new Configuration();

  public static Configuration instance() {
    return SINGLETON;
  }

  private String fVersion;
  private String fBrowser;

  private Configuration() {
    loadSystemConfiguration();
    loadUserConfiguration();
  }

  private void loadSystemConfiguration() {
    try {
      Properties props = PropertiesUtils.loadProperties(Constants.AMAZONCHECKER_SYSTEM_PROPERTIES);
      fVersion = props.getProperty(PROP_VERSION);
    } catch (IOException e) {
      LOGGER.error("システム設定ファイル読込に失敗", e);
    }
  }

  private void loadUserConfiguration() {
    try {
      Properties props = PropertiesUtils.loadProperties(Constants.AMAZONCHECKER_PROPERTIES);
      fBrowser = props.getProperty(PROP_BROWSER, DEFAULT_BROWSER);
      LOGGER.info("{}={}", PROP_BROWSER, fBrowser);
    } catch (IOException e) {
      LOGGER.error("ユーザ設定ファイル読込に失敗", e);
    }
  }

  //----------------------------------------------------------------------------
  // Getters
  //----------------------------------------------------------------------------

  public String getVersion() {
    return fVersion;
  }

  public String getBrowser() {
    return fBrowser;
  }
}
