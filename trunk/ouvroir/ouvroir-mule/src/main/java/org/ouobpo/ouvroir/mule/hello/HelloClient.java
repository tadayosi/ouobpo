package org.ouobpo.ouvroir.mule.hello;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.module.client.MuleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

  public static void main(String[] args) {
    HelloService service = new HelloService();
    out(service.hello(HelloClient.class.getSimpleName()));

    MuleContext context = null;
    try {
      context = new DefaultMuleContextFactory().createMuleContext(new SpringXmlConfigurationBuilder(
          "org/ouobpo/ouvroir/mule/hello/hello.xml"));
      context.start();
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
    }

    try {
      MuleClient client = new MuleClient();
      // enhance
      MuleMessage message = client.send(
          "vm://hello?method=enhance",
          HelloClient.class.getSimpleName(),
          null);
      out(message.getPayload());
      // asterisk
      message = client.send(
          "vm://hello?method=asterisk",
          HelloClient.class.getSimpleName(),
          null);
      out(message.getPayload());
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
    }

    if (context != null) {
      try {
        context.stop();
      } catch (MuleException e) {
        LOGGER.error(e.getMessage());
      }
      context.dispose();
    }
  }

  private static void out(Object msg) {
    System.out.println(msg);
  }
}
