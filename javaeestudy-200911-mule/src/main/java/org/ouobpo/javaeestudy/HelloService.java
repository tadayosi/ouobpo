package org.ouobpo.javaeestudy;

import org.apache.commons.lang.StringUtils;

public class HelloService {
  private static final String MESSAGE   = "こんにちは、%s";
  private static final String ANONYMOUS = "名無し";

  public String say(String path) {
    return String.format(MESSAGE, toName(path));
  }

  private static String toName(String path) {
    String name = StringUtils.substringAfter(path, "?name=");
    return StringUtils.isEmpty(name) ? ANONYMOUS : name;
  }
}
