package org.ouobpo.ouvroir.mule.hello;

public class HelloEnhancer {

  public String enhance(String msg) {
    return "<<< " + msg + " >>>";
  }

  public String asterisk(String msg) {
    return "*** " + msg + " ***";
  }
}
