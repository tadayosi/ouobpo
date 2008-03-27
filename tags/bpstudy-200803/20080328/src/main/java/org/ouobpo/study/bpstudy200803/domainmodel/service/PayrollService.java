package org.ouobpo.study.bpstudy200803.domainmodel.service;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.domain.Employee;

/**
 * 給与計算サービス
 */
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
