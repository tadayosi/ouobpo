package org.ouobpo.study.bpstudy200803.txscript.dao;

import java.util.List;

import org.ouobpo.study.bpstudy200803.txscript.entity.PaySlip;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = PaySlip.class)
public interface PaySlipDao {

  public int insert(PaySlip slip);

  public int update(PaySlip slip);

  public int delete(PaySlip slip);

  public List<PaySlip> selectAll();

  @Arguments("id")
  public PaySlip selectById(Integer id);

  @Arguments( {"employee_id", "target_year", "target_month"})
  public PaySlip selectByEmployeeIdYearMonth(
      Integer employeeId,
      int targetYear,
      int targetMonth);

}
