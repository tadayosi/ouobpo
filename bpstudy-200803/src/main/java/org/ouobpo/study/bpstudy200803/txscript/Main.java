package org.ouobpo.study.bpstudy200803.txscript;

import org.ouobpo.study.bpstudy200803.txscript.service.PayrollService;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

public class Main {

  public static void main(String[] args) {
    S2Container container = S2ContainerFactory.create("app.dicon");
    container.init();
    try {
      PayrollService service = (PayrollService) container.getComponent(PayrollService.class);
      service.calculate();
    } finally {
      container.destroy();
    }
  }

}
