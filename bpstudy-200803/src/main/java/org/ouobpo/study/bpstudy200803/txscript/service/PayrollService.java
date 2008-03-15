package org.ouobpo.study.bpstudy200803.txscript.service;

import java.util.List;

import org.ouobpo.study.bpstudy200803.txscript.entity.Employee;

public interface PayrollService {

  /**
   * 全従業員取得
   */
  List<Employee> getAllEmployees();

  /**
   * 給与明細作成
   */
  void pay(Employee employee, int year, int month);

}
