package org.ouobpo.study.bpstudy200803.domainmodel.domain;

/**
 * 時間外手当
 */
public class OvertimeAllowance extends Allowance {

  private static final int HOURLY_PAY = 2000;

  protected OvertimeAllowance(Employee employee) {
    super(employee);
  }

  /**
   * 時間外手当
   * 時給は一律 2,000 円とする。
   */
  @Override
  public void calculate(TimeRecord timeRecord, PaySlip paySlip) {
    // 時間外勤務の実績から、時間外手当額を算出
    int amount = timeRecord.getOvertimeHours() * HOURLY_PAY;
    paySlip.setOvertimeAllowance(amount);
  }

}
