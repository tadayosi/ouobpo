package org.ouobpo.study.bpstudy200803.txscript.service.impl;

import java.util.List;

import org.ouobpo.study.bpstudy200803.txscript.dao.EmployeeDao;
import org.ouobpo.study.bpstudy200803.txscript.dao.PaySlipDao;
import org.ouobpo.study.bpstudy200803.txscript.dao.TimeRecordDao;
import org.ouobpo.study.bpstudy200803.txscript.entity.Employee;
import org.ouobpo.study.bpstudy200803.txscript.entity.PaySlip;
import org.ouobpo.study.bpstudy200803.txscript.entity.TimeRecord;
import org.ouobpo.study.bpstudy200803.txscript.exception.SystemException;
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
    String jobType = employee.getJobType();
    int rank = employee.getRank();
    if (Employee.JOB_TYPE_SALES.equals(jobType)) {

      // 営業職の給料体系
      if (Employee.RANK_JUNIOR.equals(rank)) {
        return 220000;
      } else if (Employee.RANK_MIDDLE == rank) {
        return 320000;
      } else if (Employee.RANK_SENIOR == rank) { return 420000; }

    } else if (Employee.JOB_TYPE_SE.equals(jobType)) {

      // SE職の給料体系
      if (Employee.RANK_JUNIOR == rank) {
        return 210000;
      } else if (Employee.RANK_MIDDLE == rank) {
        return 310000;
      } else if (Employee.RANK_SENIOR == rank) { return 410000; }

    } else if (Employee.JOB_TYPE_STAFF.equals(jobType)) {

      // 事務職の給料体系
      if (Employee.RANK_JUNIOR == rank) {
        return 200000;
      } else if (Employee.RANK_MIDDLE == rank) {
        return 300000;
      } else if (Employee.RANK_SENIOR == rank) { return 400000; }

    }
    throw new SystemException("予期しない職種またはランク: " + employee);
  }

  /**
   * 時間外手当
   * 時給は一律 2,000 円とする。
   */
  private int calculateOvertimeAllowance(Employee employee, int year, int month) {
    // 勤怠実績を取得
    TimeRecord timeRecord = fTimeRecordDao.selectByEmployeeIdAndYearAndMonth(
        employee.getEmployeeId(),
        year,
        month);
    // 時間外勤務の実績から、時間外手当額を算出
    return timeRecord.getOvertimeHours() * 2000;
  }

  /**
   * 住宅手当
   * 対象者に家賃の半額を支給。ただし、上限は 50,000 円まで。
   */
  private int calculateRentAllowance(Employee employee) {
    if (employee.isRentAllowance()) {

      // 手当対象
      int amount = employee.getRent() / 2;
      return Math.min(amount, 50000);

    } else {

      // 手当対象外
      return 0;

    }
  }

  private void createPaySlip(
      Employee employee,
      int year,
      int month,
      int baseSalary,
      int overtimeAllowance,
      int rentAllowance) {
    // 同一対象年月の給与明細がすでにあるかを確認
    PaySlip paySlip = fPaySlipDao.selectByEmployeeIdAndYearAndMonth(
        employee.getEmployeeId(),
        year,
        month);
    // 同一対象年月が無ければ、新規作成
    if (paySlip == null) {
      paySlip = new PaySlip();
    }
    // 明細データのセット
    paySlip.setEmployeeId(employee.getEmployeeId());
    paySlip.setTargetYear(year);
    paySlip.setTargetMonth(month);
    paySlip.setBaseSalary(baseSalary);
    paySlip.setOvertimeAllowance(overtimeAllowance);
    paySlip.setRentAllowance(rentAllowance);
    paySlip.setTotalAmount(baseSalary + overtimeAllowance + rentAllowance);
    // IDの有無に応じて、DBに新規作成または更新
    if (paySlip.getId() == null) {
      fPaySlipDao.insert(paySlip);
    } else {
      fPaySlipDao.update(paySlip);
    }
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
