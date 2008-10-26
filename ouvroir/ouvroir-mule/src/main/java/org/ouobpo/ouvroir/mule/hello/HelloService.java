package org.ouobpo.ouvroir.mule.hello;

public class HelloService {
  private String fHello = "こんにちは、";

  public String hello(String caller) {
    return fHello + caller;
  }
}
