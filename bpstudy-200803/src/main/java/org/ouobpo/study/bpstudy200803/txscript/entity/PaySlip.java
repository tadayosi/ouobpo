package org.ouobpo.study.bpstudy200803.txscript.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

/**
 * 給与明細（トランザクションスクリプト）
 */
@Bean
public class PaySlip {

  private Integer id;
  private Integer employeeId;
  private Integer targetYear;
  private Integer targetMonth;
  private Integer baseSalary;
  private Integer overtimeAllowance;
  private Integer rentAllowance;
  private Integer totalAmount;

  public PaySlip() {}

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  //----------------------------------------------------------------------------
  // Getter/Setter
  //----------------------------------------------------------------------------

  @Id(value = IdType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public Integer getTargetYear() {
    return targetYear;
  }

  public void setTargetYear(Integer targetYear) {
    this.targetYear = targetYear;
  }

  public Integer getTargetMonth() {
    return targetMonth;
  }

  public void setTargetMonth(Integer targetMonth) {
    this.targetMonth = targetMonth;
  }

  public Integer getBaseSalary() {
    return baseSalary;
  }

  public void setBaseSalary(Integer baseSalary) {
    this.baseSalary = baseSalary;
  }

  public Integer getOvertimeAllowance() {
    return overtimeAllowance;
  }

  public void setOvertimeAllowance(Integer overtimeAllowance) {
    this.overtimeAllowance = overtimeAllowance;
  }

  public Integer getRentAllowance() {
    return rentAllowance;
  }

  public void setRentAllowance(Integer rentAllowance) {
    this.rentAllowance = rentAllowance;
  }

  public Integer getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Integer totalAmount) {
    this.totalAmount = totalAmount;
  }

}
