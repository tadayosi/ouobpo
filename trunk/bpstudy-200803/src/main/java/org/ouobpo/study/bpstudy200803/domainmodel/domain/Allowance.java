package org.ouobpo.study.bpstudy200803.domainmodel.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;

/**
 * 手当
 */
@Bean
public class Allowance {

  private Long   fId;
  private String fEmployeeId;

  public String toString() {
    return ToStringBuilder.reflectionToString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  //----------------------------------------------------------------------------
  // Setter/Getter
  //----------------------------------------------------------------------------

  @Id
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

}
