package org.ouobpo.study.bpstudy200803.domainmodel.dao;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.domain.Employee;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = Employee.class)
public interface EmployeeDao {

  public int insert(Employee employee);

  public int update(Employee employee);

  public int delete(Employee employee);

  public List<Employee> selectAll();

  @Arguments("employee_id")
  public Employee selectById(Integer employeeId);

}
