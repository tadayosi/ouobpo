package org.ouobpo.ouvroir.camel.hello;

import org.apache.camel.Consume;

public class Display {
  @Consume(uri = "vm:hello")
  public void out(String msg) {
    System.out.println(msg);
  }
}
