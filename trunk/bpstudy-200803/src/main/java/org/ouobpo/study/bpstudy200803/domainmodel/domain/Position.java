package org.ouobpo.study.bpstudy200803.domainmodel.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.ouobpo.study.bpstudy200803.domainmodel.domain.exception.SystemException;
import org.seasar.dao.annotation.tiger.Bean;

/**
 * 職責（ドメインモデル）
 */
@Bean
public class Position {

  @SuppressWarnings("unused")
  private Employee fEmployee;
  private String   jobType;
  private Integer  rank;

  Position(Employee employee) {
    fEmployee = employee;
  }

  /**
   * 基本給
   * 基本給は、職種とランクから決まる。
   */
  public void calculateBaseSalary(PaySlip paySlip) {
    Integer baseSalary = null;
    if (Employee.JOB_TYPE_SALES.equals(jobType)) {

      // 営業職の給料体系
      if (Employee.RANK_JUNIOR == rank) {
        baseSalary = 220000;
      } else if (Employee.RANK_MIDDLE == rank) {
        baseSalary = 320000;
      } else if (Employee.RANK_SENIOR == rank) {
        baseSalary = 420000;
      }

    } else if (Employee.JOB_TYPE_SE.equals(jobType)) {

      // SE職の給料体系
      if (Employee.RANK_JUNIOR == rank) {
        baseSalary = 210000;
      } else if (Employee.RANK_MIDDLE == rank) {
        baseSalary = 310000;
      } else if (Employee.RANK_SENIOR == rank) {
        baseSalary = 410000;
      }

    } else if (Employee.JOB_TYPE_STAFF.equals(jobType)) {

      // 事務職の給料体系
      if (Employee.RANK_JUNIOR == rank) {
        baseSalary = 200000;
      } else if (Employee.RANK_MIDDLE == rank) {
        baseSalary = 300000;
      } else if (Employee.RANK_SENIOR == rank) {
        baseSalary = 400000;
      }

    }
    if (baseSalary == null) { throw new SystemException("予期しない職種またはランク: "
        + this); }
    // 算出結果のセット
    paySlip.setBaseSalary(baseSalary);
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  //----------------------------------------------------------------------------
  // Getter/Setter
  //----------------------------------------------------------------------------

  String getJobType() {
    return jobType;
  }

  void setJobType(String jobType) {
    this.jobType = jobType;
  }

  Integer getRank() {
    return rank;
  }

  void setRank(Integer rank) {
    this.rank = rank;
  }

}
