package org.ouobpo.study.bpstudy200808.drools;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Employee {

  private String employeeNo;
  private String name;
  private int    age;

  public Employee(String employeeNo, String name, int age) {
    this.employeeNo = employeeNo;
    this.name = name;
    this.age = age;
  }

  public String toString() {
    return ReflectionToStringBuilder.toString(
        this,
        ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public String getEmployeeNo() {
    return employeeNo;
  }

  public void setEmployeeNo(String employeeNo) {
    this.employeeNo = employeeNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

}
