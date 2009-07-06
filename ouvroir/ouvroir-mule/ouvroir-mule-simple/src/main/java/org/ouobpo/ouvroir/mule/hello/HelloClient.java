package org.ouobpo.ouvroir.mule.hello;

import static org.ouobpo.javaextension.JavaExtensions.*;

import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.config.spring.SpringXmlConfigurationBuilder;
import org.mule.context.DefaultMuleContextFactory;
import org.mule.module.client.MuleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

  public static void main(String[] args) {
    HelloService service = new HelloService();
    print(service.hello(HelloClient.class.getSimpleName()));

    MuleContext context = null;
    try {
      context = startMuleServer("hello-config.xml");

      MuleClient client = new MuleClient();
      // enhance
      client.send(
          "vm://hello?method=enhance",
          HelloClient.class.getSimpleName(),
          null);
      // asterisk
      client.send(
          "vm://hello?method=asterisk",
          HelloClient.class.getSimpleName(),
          null);
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
    } finally {
      if (context != null) {
        stopMuleServer(context);
      }
    }
  }

  private static MuleContext startMuleServer(String config) {
    try {
      MuleContext context = new DefaultMuleContextFactory().createMuleContext(new SpringXmlConfigurationBuilder(
          config));
      context.start();
      return context;
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private static void stopMuleServer(MuleContext context) {
    try {
      context.stop();
    } catch (MuleException e) {
      LOGGER.error(e.getMessage());
    }
    context.dispose();
  }
}
