package org.ouobpo.javaeestudy;

import org.apache.commons.lang.StringUtils;
import org.mule.transformer.AbstractTransformer;

public class HttpRequestToNameTransformer extends AbstractTransformer {
  private static final String ANONYMOUS = "名無し";

  @Override
  protected Object doTransform(Object src, String encoding) {
    return toName(src.toString());
  }

  private static String toName(String path) {
    String name = StringUtils.substringAfter(path, "?name=");
    return StringUtils.isEmpty(name) ? ANONYMOUS : name;
  }
}
