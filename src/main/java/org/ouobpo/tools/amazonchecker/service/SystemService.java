package org.ouobpo.tools.amazonchecker.service;

import static org.apache.commons.lang.SystemUtils.*;

import java.io.IOException;

import org.ouobpo.tools.amazonchecker.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tadayosi
 * @since 2009/01/17
 * @version $Id$
 */
public class SystemService {
  private static final Logger LOGGER                     = LoggerFactory.getLogger(SystemService.class);

  private static final String DEFAULT_LAUNCHER_WINDOWS_1 = "cmd";
  private static final String DEFAULT_LAUNCHER_WINDOWS_2 = "/c start";
  private static final String DEFAULT_LAUNCHER_MAC       = "open";

  private static final String BROWSER_WINDOWS_EXPLORER   = "explorer";

  private final Configuration fConfiguration             = Configuration.instance();

  public static SystemService create() {
    return new SystemService();
  }

  private SystemService() {}

  public void launchBrowser(String url) {
    // カスタムブラウザを起動する場合
    if (fConfiguration.isBrowserSet()) {
      // explorerでは、URLに「?」が入っているとダブルクォートが必要
      doLaunchBrowser(fConfiguration.browser(), isWindowsExplorer()
          ? doubleQuote(url)
          : url);
      return;
    }

    // デフォルトブラウザを起動
    if (IS_OS_WINDOWS) {
      doLaunchBrowser(DEFAULT_LAUNCHER_WINDOWS_1, DEFAULT_LAUNCHER_WINDOWS_2
          + " "
          + url);
    } else if (IS_OS_MAC_OSX) {
      doLaunchBrowser(DEFAULT_LAUNCHER_MAC, url);
    } else {
      LOGGER.error("このOSはサポートされていません: {}", OS_NAME);
    }
  }

  private boolean isWindowsExplorer() {
    return IS_OS_WINDOWS
        && BROWSER_WINDOWS_EXPLORER.equalsIgnoreCase(fConfiguration.browser());
  }

  private static final String doubleQuote(String str) {
    return new StringBuilder().append("\"").append(str).append("\"").toString();
  }

  private static void doLaunchBrowser(String... command) {
    // ***デバッグログ***
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Amazonページを表示: {} {}", command);
    }
    try {
      Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      LOGGER.error("Amazonページ起動に失敗", e);
    }
  }
}
