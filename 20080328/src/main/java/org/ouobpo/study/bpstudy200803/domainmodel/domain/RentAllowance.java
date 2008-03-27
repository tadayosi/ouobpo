package org.ouobpo.study.bpstudy200803.domainmodel.domain;

/**
 * 住宅手当
 */
public class RentAllowance extends Allowance {

  private static final int MAX_AMOUNT = 50000;

  private Boolean          fApplicable;
  private Integer          fRent;

  protected RentAllowance(Employee employee) {
    super(employee);
  }

  /**
   * 住宅手当
   * 対象者に家賃の半額を支給。ただし、上限は 50,000 円まで。
   */
  @Override
  public void calculate(TimeRecord timeRecord, PaySlip paySlip) {
    if (fApplicable) {

      // 手当対象
      int amount = Math.min(fRent / 2, MAX_AMOUNT);
      paySlip.setRentAllowance(amount);

    } else {

      // 手当対象外
      paySlip.setRentAllowance(0);

    }
  }

  //----------------------------------------------------------------------------
  // Getter/Setter
  //----------------------------------------------------------------------------

  public Boolean isApplicable() {
    return fApplicable;
  }

  public void setApplicable(Boolean applicable) {
    fApplicable = applicable;
  }

  public Integer getRent() {
    return fRent;
  }

  public void setRent(Integer rent) {
    fRent = rent;
  }

}
