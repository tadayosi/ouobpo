package org.ouobpo.study.bpstudy200803.domainmodel.dao;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.domain.TimeRecord;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TimeRecord.class)
public interface TimeRecordDao {

  public int insert(TimeRecord record);

  public int update(TimeRecord record);

  public int delete(TimeRecord record);

  public List<TimeRecord> selectAll();

  @Arguments("id")
  public TimeRecord selectById(Integer id);

  @Arguments( {"employee_id", "target_year", "target_month"})
  public TimeRecord selectByEmployeeIdYearMonth(
      Integer employeeId,
      int targetYear,
      int targetMonth);

}
