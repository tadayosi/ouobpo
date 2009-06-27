package org.ouobpo.ouvroir.camel.hello;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;

public class Hello {
  @Produce(uri = "vm:hello")
  private ProducerTemplate producer;

  @Consume(uri = "jetty:http://localhost:8080/ouvroir-camel/hello")
  public void say(Exchange exchange) {
    producer.sendBody(String.format("Hello, %s !", exchange.getIn().getBody(
        HttpServletRequest.class).getParameter("msg")));
    exchange.getOut().setBody("accepted.");
  }
}
