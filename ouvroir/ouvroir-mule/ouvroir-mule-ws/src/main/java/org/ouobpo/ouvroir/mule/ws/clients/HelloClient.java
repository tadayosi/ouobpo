package org.ouobpo.ouvroir.mule.ws.clients;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.ouobpo.ouvroir.mule.ws.hello.HelloService;
import org.ouobpo.ouvroir.mule.ws.hello.IHelloService;

public class HelloClient {
  public static void main(String[] args) {
    IHelloService service = new HelloService().getHelloServicePort();
    setRequestContext(
        service,
        "http://localhost:8080/ouvroir/hello",
        "user1",
        "password");
    System.out.println(service.say(HelloClient.class.getSimpleName()));
  }

  private static void setRequestContext(
      IHelloService service,
      String endpointAddress,
      String username,
      String password) {
    Map<String, Object> context = ((BindingProvider) service).getRequestContext();
    context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
    context.put(BindingProvider.USERNAME_PROPERTY, username);
    context.put(BindingProvider.PASSWORD_PROPERTY, password);
  }
}
