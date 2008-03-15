package org.ouobpo.study.bpstudy200803.txscript.dao;

import java.util.List;

import org.ouobpo.study.bpstudy200803.txscript.entity.TimeRecord;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TimeRecord.class)
public interface TimeRecordDao {

  public int insert(TimeRecord record);

  public int update(TimeRecord record);

  public int delete(TimeRecord record);

  public List<TimeRecord> selectAll();

  @Arguments("ID")
  public TimeRecord selectById(Integer id);

  @Arguments( {"employeeId", "targetYear", "targetMonth"})
  public TimeRecord selectByEmployeeIdAndYearAndMonth(
      Integer employeeId,
      int targetYear,
      int targetMonth);

}
