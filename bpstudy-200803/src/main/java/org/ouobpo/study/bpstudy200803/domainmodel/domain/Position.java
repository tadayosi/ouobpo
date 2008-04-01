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
  private String   fJobType;
  private Integer  fRank;

  Position(Employee employee) {
    fEmployee = employee;
  }

  /**
   * 基本給
   * 基本給は、職種とランクから決まる。
   */
  public void calculateBaseSalary(PaySlip paySlip) {
    Integer baseSalary = null;
    if (Employee.JOB_TYPE_SALES.equals(fJobType)) {

      // 営業職の給料体系
      if (Employee.RANK_JUNIOR == fRank) {
        baseSalary = 220000;
      } else if (Employee.RANK_MIDDLE == fRank) {
        baseSalary = 320000;
      } else if (Employee.RANK_SENIOR == fRank) {
        baseSalary = 420000;
      }

    } else if (Employee.JOB_TYPE_SE.equals(fJobType)) {

      // SE職の給料体系
      if (Employee.RANK_JUNIOR == fRank) {
        baseSalary = 210000;
      } else if (Employee.RANK_MIDDLE == fRank) {
        baseSalary = 310000;
      } else if (Employee.RANK_SENIOR == fRank) {
        baseSalary = 410000;
      }

    } else if (Employee.JOB_TYPE_STAFF.equals(fJobType)) {

      // 事務職の給料体系
      if (Employee.RANK_JUNIOR == fRank) {
        baseSalary = 200000;
      } else if (Employee.RANK_MIDDLE == fRank) {
        baseSalary = 300000;
      } else if (Employee.RANK_SENIOR == fRank) {
        baseSalary = 400000;
      }

    }
    if (baseSalary == null) {
      throw new SystemException("予期しない職種またはランク: " + this);
    }
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
    return fJobType;
  }

  void setJobType(String jobType) {
    fJobType = jobType;
  }

  Integer getRank() {
    return fRank;
  }

  void setRank(Integer rank) {
    fRank = rank;
  }

}
