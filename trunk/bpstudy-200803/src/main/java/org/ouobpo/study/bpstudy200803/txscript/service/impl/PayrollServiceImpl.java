package org.ouobpo.study.bpstudy200803.txscript.service.impl;

import java.util.List;

import org.ouobpo.study.bpstudy200803.txscript.dao.EmployeeDao;
import org.ouobpo.study.bpstudy200803.txscript.dao.PaySlipDao;
import org.ouobpo.study.bpstudy200803.txscript.dao.TimeRecordDao;
import org.ouobpo.study.bpstudy200803.txscript.entity.Employee;
import org.ouobpo.study.bpstudy200803.txscript.entity.PaySlip;
import org.ouobpo.study.bpstudy200803.txscript.entity.TimeRecord;
import org.ouobpo.study.bpstudy200803.txscript.service.PayrollService;

public class PayrollServiceImpl implements PayrollService {

  private EmployeeDao   fEmployeeDao;  // 従業員DAO
  private TimeRecordDao fTimeRecordDao; // 勤怠実績DAO
  private PaySlipDao    fPaySlipDao;   // 給与明細DAO

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
    // 基本給の計算
    int baseSalary = calculateBaseSalary(employee);
    // 時間外手当
    int overtimeAllowance = calculateOvertimeAllowance(employee, year, month);
    // 住宅手当
    int rentAllowance = calculateRentAllowance(employee);
    // 給与明細の作成
    createPaySlip(
        employee,
        year,
        month,
        baseSalary,
        overtimeAllowance,
        rentAllowance);
  }

  private int calculateBaseSalary(Employee employee) {
    // FIXME Auto-generated method stub
    return 0;
  }

  private int calculateOvertimeAllowance(Employee employee, int year, int month) {
    TimeRecord timeRecord = fTimeRecordDao.selectByEmployeeIdAndYearAndMonth(
        employee.getEmployeeId(),
        year,
        month);
    // FIXME Auto-generated method stub
    return 0;
  }

  private int calculateRentAllowance(Employee employee) {
    // FIXME Auto-generated method stub
    return 0;
  }

  private void createPaySlip(
      Employee employee,
      int year,
      int month,
      int baseSalary,
      int overtimeAllowance,
      int rentAllowance) {
    PaySlip paySlip = new PaySlip();
    paySlip.setEmployeeId(employee.getEmployeeId());
    paySlip.setTargetYear(year);
    paySlip.setTargetMonth(month);
    paySlip.setBaseSalary(baseSalary);
    paySlip.setOvertimeAllowance(overtimeAllowance);
    paySlip.setRentAllowance(rentAllowance);
    fPaySlipDao.insert(paySlip);
  }

  //----------------------------------------------------------------------------
  // Setter
  //----------------------------------------------------------------------------

  public void setEmployeeDao(EmployeeDao employeeDao) {
    fEmployeeDao = employeeDao;
  }

  public void setTimeRecordDao(TimeRecordDao timeRecordDao) {
    fTimeRecordDao = timeRecordDao;
  }

  public void setPaySlipDao(PaySlipDao paySlipDao) {
    fPaySlipDao = paySlipDao;
  }

}
