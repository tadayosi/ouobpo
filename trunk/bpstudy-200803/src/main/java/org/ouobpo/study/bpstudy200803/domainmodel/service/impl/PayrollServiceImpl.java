package org.ouobpo.study.bpstudy200803.domainmodel.service.impl;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.dao.EmployeeDao;
import org.ouobpo.study.bpstudy200803.domainmodel.domain.Employee;
import org.ouobpo.study.bpstudy200803.domainmodel.service.PayrollService;

public class PayrollServiceImpl implements PayrollService {

  /** 従業員DAO */
  private EmployeeDao fEmployeeDao;

  /**
   * 全従業員取得
   */
  public List<Employee> getAllEmployees() {
    return fEmployeeDao.selectAll();
  }

  /**
   * 給与明細作成
   */
  public void pay(Employee employee, int year, int month) {
    employee.pay(year, month);
  }

  //----------------------------------------------------------------------------
  // Setter
  //----------------------------------------------------------------------------

  public void setEmployeeDao(EmployeeDao employeeDao) {
    fEmployeeDao = employeeDao;
  }

}
