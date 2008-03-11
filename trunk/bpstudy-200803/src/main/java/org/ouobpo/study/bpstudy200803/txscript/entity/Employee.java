package org.ouobpo.study.bpstudy200803.txscript.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.seasar.dao.annotation.tiger.Bean;

/**
 * 従業員
 */
@Bean
public class Employee {

  private Long   fId;
  private String fEmployeeId;
  private String fName;

  public Employee() {}

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  //----------------------------------------------------------------------------
  // Setter/Getter
  //----------------------------------------------------------------------------

  public Long getId() {
    return fId;
  }

  public void setId(Long id) {
    fId = id;
  }

  public String getEmployeeId() {
    return fEmployeeId;
  }

  public void setEmployeeId(String employeeId) {
    fEmployeeId = employeeId;
  }

  public String getName() {
    return fName;
  }

  public void setName(String name) {
    fName = name;
  }

}
