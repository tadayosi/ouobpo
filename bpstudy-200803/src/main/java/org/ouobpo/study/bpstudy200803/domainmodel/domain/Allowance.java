package org.ouobpo.study.bpstudy200803.domainmodel.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 手当
 */
public abstract class Allowance {

  protected Employee fEmployee;

  protected Allowance(Employee employee) {
    fEmployee = employee;
  }

  /**
   * 手当額算出
   * サブクラスはこのメソッドを実装して、それぞれの手当額算出を行う。
   */
  public abstract void calculate(TimeRecord timeRecord, PaySlip paySlip);

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

}
