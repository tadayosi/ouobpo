package org.ouobpo.ouvroir.sastruts.action;

import javax.annotation.Resource;

import org.ouobpo.ouvroir.sastruts.service.HelloService;
import org.seasar.struts.annotation.Execute;

public class HelloAction {
  public String        message;

  @Resource
  private HelloService helloService;

  @Execute(validator = false)
  public String index() {
    message = helloService.say();
    return "index.jsp";
  }
}
