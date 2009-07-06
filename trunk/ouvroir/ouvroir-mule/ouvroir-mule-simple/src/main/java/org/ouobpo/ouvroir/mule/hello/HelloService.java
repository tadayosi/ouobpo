package org.ouobpo.ouvroir.mule.hello;

public class HelloService {
  private String fMessageTemplate = "こんにちは、%s。\n";

  public String hello(String caller) {
    return String.format(fMessageTemplate, caller);
  }
}
