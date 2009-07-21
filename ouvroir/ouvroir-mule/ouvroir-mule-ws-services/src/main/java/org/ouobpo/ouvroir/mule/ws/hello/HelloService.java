package org.ouobpo.ouvroir.mule.ws.hello;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface = "org.ouobpo.ouvroir.mule.ws.hello.IHelloService", serviceName = "HelloService")
public class HelloService implements IHelloService {
  private static final Logger LOGGER           = LoggerFactory.getLogger(HelloService.class);
  private static final String MESSAGE_TEMPLATE = "こんにちは、%s";

  public String say(String caller) {
    String message = String.format(MESSAGE_TEMPLATE, caller);
    LOGGER.info(message);
    return message;
  }
}
