package org.ouobpo.ouvroir.mule.ws.hello;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IHelloService {
  String say(@WebParam(name = "caller") String caller);
}
