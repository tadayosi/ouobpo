package org.ouobpo.tools.amazonchecker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
public class PropertiesUtils {
  public static Properties loadProperties(String propPath) throws IOException {
    Properties props = new Properties();

    // 最初にファイルリソースから検索
    File propFile = new File(propPath);
    if (propFile.isFile()) {
      props.load(new FileInputStream(propFile));
      return props;
    }

    // 次にJARから検索
    InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(
        propPath);
    if (inputStream != null) {
      props.load(inputStream);
    }

    return props;
  }

  /** インスタンス化して使わない。 */
  private PropertiesUtils() {}
}
