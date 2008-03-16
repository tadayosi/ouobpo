package org.ouobpo.study.bpstudy200803.domainmodel;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.domain.Employee;
import org.ouobpo.study.bpstudy200803.domainmodel.service.PayrollService;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

public class Main {

  public static void main(String[] args) {
    S2Container container = S2ContainerFactory.create("app.dicon");
    container.init();
    try {

      PayrollService service = (PayrollService) container.getComponent(PayrollService.class);

      // 全従業員を取得
      List<Employee> employees = service.getAllEmployees();

      for (Employee employee : employees) {
        // 従業員毎に給与明細を作成
        service.pay(employee, 2008, 3);
      }

    } finally {
      container.destroy();
    }
  }

}
