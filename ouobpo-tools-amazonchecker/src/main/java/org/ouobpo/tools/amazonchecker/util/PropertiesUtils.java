package org.ouobpo.tools.amazonchecker.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author SATO, Tadayosi
 * @version $Id: PropertiesUtils.java 693 2007-02-17 04:52:50Z hanao $
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
    props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(
        propPath));

    return props;
  }

  /** インスタンス化して使わない。 */
  private PropertiesUtils() {}
}