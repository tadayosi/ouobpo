package org.ouobpo.ouvroir.mule.hello;

import static org.ouobpo.javaextension.JavaExtensions.*;

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
    println(service.hello(HelloClient.class.getSimpleName()));

    MuleContext context = startMuleServer();
    try {
      MuleClient client = new MuleClient();
      // enhance
      MuleMessage message = client.send(
          "vm://hello?method=enhance",
          HelloClient.class.getSimpleName(),
          null);
      println(message.getPayload());
      // asterisk
      message = client.send(
          "vm://hello?method=asterisk",
          HelloClient.class.getSimpleName(),
          null);
      println(message.getPayload());
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
    } finally {
      stopMuleServer(context);
    }
  }

  private static MuleContext startMuleServer() {
    try {
      MuleContext context = new DefaultMuleContextFactory().createMuleContext(new SpringXmlConfigurationBuilder(
          "org/ouobpo/ouvroir/mule/hello/hello-config.xml"));
      context.start();
      return context;
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private static void stopMuleServer(MuleContext context) {
    if (context != null) {
      try {
        context.stop();
      } catch (MuleException e) {
        LOGGER.error(e.getMessage());
      }
      context.dispose();
    }
  }
}
