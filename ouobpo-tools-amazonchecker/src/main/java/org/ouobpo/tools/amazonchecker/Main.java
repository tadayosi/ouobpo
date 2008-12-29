package org.ouobpo.tools.amazonchecker;

import static org.ouobpo.tools.amazonchecker.Constants.*;
import java.io.File;

import org.ouobpo.tools.amazonchecker.gui.MainFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SATO, Tadayosi
 * @version $Id: Main.java 887 2008-05-11 12:29:14Z hanao $
 */
public class Main {
  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    LOGGER.info(
        "****** AmazonChecker v{} ******************************************************************************",
        Configuration.instance().getVersion());
    initDbDirectory();
    MainFrame window = null;
    try {
      window = new MainFrame();
    } catch (Throwable t) {
      LOGGER.error(t.getMessage(), t);
      if (window != null) {
        window.dispose();
      }
      System.exit(1);
    }
  }

  private static void initDbDirectory() {
    File dbDir = new File(DB_DIRECTORY);
    if (dbDir.exists()) {
      return;
    }
    LOGGER.info("データディレクトリの初期化: {}", DB_DIRECTORY);
    dbDir.mkdir();
  }
}
