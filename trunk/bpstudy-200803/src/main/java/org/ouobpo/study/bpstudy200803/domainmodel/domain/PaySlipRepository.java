package org.ouobpo.study.bpstudy200803.domainmodel.domain;

import java.util.List;

import org.ouobpo.study.bpstudy200803.domainmodel.dao.PaySlipDao;
import org.seasar.domainmodel.annotation.LifeCycleObject;

/**
 * 給与明細リポジトリ
 * 
 * ドメイン層からデータアクセスの実装（S2Dao）を隠蔽する。
 */
@LifeCycleObject
public class PaySlipRepository {

  private PaySlipDao fPaySlipDao;

  public int insert(PaySlip paySlip) {
    return fPaySlipDao.insert(paySlip);
  }

  public int update(PaySlip paySlip) {
    return fPaySlipDao.update(paySlip);
  }

  public int delete(PaySlip paySlip) {
    return fPaySlipDao.delete(paySlip);
  }

  public List<PaySlip> selectAll() {
    return fPaySlipDao.selectAll();
  }

  public PaySlip selectById(Integer id) {
    return fPaySlipDao.selectById(id);
  }

  public PaySlip selectByEmployeeIdYearMonth(
      Integer employeeId,
      int targetYear,
      int targetMonth) {
    return fPaySlipDao.selectByEmployeeIdYearMonth(
        employeeId,
        targetYear,
        targetMonth);
  }

  //----------------------------------------------------------------------------
  // Setter
  //----------------------------------------------------------------------------

  public void setPaySlipDao(PaySlipDao dao) {
    fPaySlipDao = dao;
  }

}
