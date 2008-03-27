package org.ouobpo.study.bpstudy200803.txscript.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

/**
 * 勤怠実績（トランザクションスクリプト）
 */
@Bean
public class TimeRecord {

  private Integer id;
  private Integer employeeId;
  private Integer targetYear;
  private Integer targetMonth;
  private Integer overtimeHours;

  public TimeRecord() {}

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

  public Integer getOvertimeHours() {
    return overtimeHours;
  }

  public void setOvertimeHours(Integer overtimeHours) {
    this.overtimeHours = overtimeHours;
  }

}