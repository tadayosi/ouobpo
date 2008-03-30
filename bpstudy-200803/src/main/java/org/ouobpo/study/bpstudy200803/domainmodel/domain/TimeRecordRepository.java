package org.ouobpo.study.bpstudy200803.domainmodel.domain;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.dao.TimeRecordDao;
import org.seasar.domainmodel.annotation.LifeCycleObject;

/**
 * 勤怠実績リポジトリ
 * 
 * ドメイン層からデータアクセスの実装（S2Dao）を隠蔽する。
 */
@LifeCycleObject
public class TimeRecordRepository {

  private TimeRecordDao fTimeRecordDao;

  public int insert(TimeRecord record) {
    return fTimeRecordDao.insert(record);
  }

  public int update(TimeRecord record) {
    return fTimeRecordDao.update(record);
  }

  public int delete(TimeRecord record) {
    return fTimeRecordDao.delete(record);
  }

  public List<TimeRecord> selectAll() {
    return fTimeRecordDao.selectAll();
  }

  public TimeRecord selectById(Integer id) {
    return fTimeRecordDao.selectById(id);
  }

  public TimeRecord selectByEmployeeIdYearMonth(
      Integer employeeId,
      int targetYear,
      int targetMonth) {
    return fTimeRecordDao.selectByEmployeeIdYearMonth(
        employeeId,
        targetYear,
        targetMonth);
  }

  //----------------------------------------------------------------------------
  // Setter
  //----------------------------------------------------------------------------

  public void setTimeRecordDao(TimeRecordDao dao) {
    fTimeRecordDao = dao;
  }

}
