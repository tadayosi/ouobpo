package org.ouobpo.study.bpstudy200803.txscript.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

/**
 * 従業員
 */
@Bean
public class Employee {

  public static final String  JOB_TYPE_SALES = "営業";
  public static final String  JOB_TYPE_SE    = "SE";
  public static final String  JOB_TYPE_STAFF = "事務";

  public static final Integer RANK_JUNIOR    = 1;
  public static final Integer RANK_MIDDLE    = 2;
  public static final Integer RANK_SENIOR    = 3;

  private Integer             employeeId;
  private String              name;
  private String              jobType;
  private Integer             rank;
  private Boolean             rentAllowance;
  private Integer             rent;

  public Employee() {}

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  //----------------------------------------------------------------------------
  // Setter/Getter
  //----------------------------------------------------------------------------

  @Id(value = IdType.IDENTITY)
  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getJobType() {
    return jobType;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Boolean getRentAllowance() {
    return rentAllowance;
  }

  public void setRentAllowance(Boolean rentAllowance) {
    this.rentAllowance = rentAllowance;
  }

  public Integer getRent() {
    return rent;
  }

  public void setRent(Integer rent) {
    this.rent = rent;
  }

}
