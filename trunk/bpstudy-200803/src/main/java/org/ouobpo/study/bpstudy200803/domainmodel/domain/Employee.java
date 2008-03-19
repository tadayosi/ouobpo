package org.ouobpo.study.bpstudy200803.domainmodel.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.ouobpo.study.bpstudy200803.domainmodel.dao.PaySlipDao;
import org.ouobpo.study.bpstudy200803.domainmodel.dao.TimeRecordDao;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

/**
 * 従業員（ドメインモデル）
 */
@Bean
public class Employee {

  public static final String JOB_TYPE_SALES = "営業";
  public static final String JOB_TYPE_SE    = "SE";
  public static final String JOB_TYPE_STAFF = "事務";

  public static final int    RANK_JUNIOR    = 1;
  public static final int    RANK_MIDDLE    = 2;
  public static final int    RANK_SENIOR    = 3;

  private Integer            fEmployeeId;
  private String             fName;
  private Position           fPosition;
  private List<Allowance>    fAllowances;

  /** XXX S2Dao用Setter/Getter実装のためだけに使用 */
  private RentAllowance      fRentAllowance;

  private TimeRecordDao      fTimeRecordDao;
  private PaySlipDao         fPaySlipDao;

  public Employee() {
    // 職責
    fPosition = new Position(this);

    // 各手当
    fAllowances = new ArrayList<Allowance>();
    // 住宅手当
    fRentAllowance = new RentAllowance(this);
    fAllowances.add(fRentAllowance);
    // 時間外手当
    fAllowances.add(new OvertimeAllowance(this));
  }

  /**
   * 給与明細作成
   */
  public void pay(int year, int month) {
    PaySlip paySlip = paySlipOf(year, month);

    // 基本給の計算
    fPosition.calculateBaseSalary(paySlip);

    // 各手当の計算
    TimeRecord timeRecord = timeRecordOf(year, month);
    for (Allowance allowance : fAllowances) {
      allowance.calculate(timeRecord, paySlip);
    }

    // 給与明細の作成
    createPaySlip(paySlip);
  }

  private PaySlip paySlipOf(int year, int month) {
    // 同一対象年月の給与明細がすでにあるかを確認
    PaySlip paySlip = fPaySlipDao.selectByEmployeeIdYearMonth(
        fEmployeeId,
        year,
        month);
    // 同一対象年月が無ければ、新規作成
    if (paySlip == null) {
      paySlip = new PaySlip(fEmployeeId, year, month);
    }
    return paySlip;
  }

  private TimeRecord timeRecordOf(int year, int month) {
    return fTimeRecordDao.selectByEmployeeIdYearMonth(fEmployeeId, year, month);
  }

  private void createPaySlip(PaySlip paySlip) {
    // IDの有無に応じて、DBに新規作成または更新
    if (paySlip.getId() == null) {
      fPaySlipDao.insert(paySlip);
    } else {
      fPaySlipDao.update(paySlip);
    }
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  //----------------------------------------------------------------------------
  // Getter/Setter
  //----------------------------------------------------------------------------

  @Id(value = IdType.IDENTITY)
  public Integer getEmployeeId() {
    return fEmployeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    fEmployeeId = employeeId;
  }

  public String getName() {
    return fName;
  }

  public void setName(String name) {
    fName = name;
  }

  public String getJobType() {
    return fPosition.getJobType();
  }

  public void setJobType(String jobType) {
    fPosition.setJobType(jobType);
  }

  public Integer getRank() {
    return fPosition.getRank();
  }

  public void setRank(Integer rank) {
    fPosition.setRank(rank);
  }

  public Boolean isRentAllowance() {
    return fRentAllowance.isApplicable();
  }

  public void setRentAllowance(Boolean rentAllowance) {
    fRentAllowance.setApplicable(rentAllowance);
  }

  public Integer getRent() {
    return fRentAllowance.getRent();
  }

  public void setRent(Integer rent) {
    fRentAllowance.setRent(rent);
  }

  //----------------------------------------------------------------------------

  public void setTimeRecordDao(TimeRecordDao timeRecordDao) {
    fTimeRecordDao = timeRecordDao;
  }

  public void setPaySlipDao(PaySlipDao paySlipDao) {
    fPaySlipDao = paySlipDao;
  }

}
