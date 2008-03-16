package org.ouobpo.study.bpstudy200803.domainmodel.interceptor;

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import org.ouobpo.study.bpstudy200803.domainmodel.dao.EmployeeDao;
import org.ouobpo.study.bpstudy200803.domainmodel.domain.Employee;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.container.S2Container;

public class DaoSetterInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -9095670974808511695L;

  private S2Container       fContainer;

  public Object invoke(MethodInvocation invocation) throws Throwable {
    if (!(invocation.getThis() instanceof EmployeeDao)) { return invocation.proceed(); }

    Object ret = invocation.proceed();
    if (ret instanceof Employee) {
      setDao((Employee) ret);
    } else if (ret instanceof List) {
      for (Object element : (List<?>) ret) {
        setDao((Employee) element);
      }
    }
    return ret;
  }

  private void setDao(Employee employee) {
    fContainer.injectDependency(employee);
  }

  //----------------------------------------------------------------------------
  // Setter
  //----------------------------------------------------------------------------

  public void setContainer(S2Container container) {
    fContainer = container;
  }

}
