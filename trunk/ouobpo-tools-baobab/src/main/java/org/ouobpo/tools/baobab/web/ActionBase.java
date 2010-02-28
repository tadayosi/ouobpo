package org.ouobpo.tools.baobab.web;

import org.ouobpo.tools.baobab.dao.IBookDao;
import org.ouobpo.tools.baobab.dao.IMonthlyStatisticsDao;

/**
 * Base impl for actions.
 * 
 * @author tadayosi
 */
public abstract class ActionBase {

  protected IBookDao              fBookDao;
  protected IMonthlyStatisticsDao fMonthlyStatisticsDao;

  public void setBookDao(IBookDao bookDao) {
    fBookDao = bookDao;
  }

  public void setMonthlyStatisticsDao(IMonthlyStatisticsDao monthlyStatisticsDao) {
    fMonthlyStatisticsDao = monthlyStatisticsDao;
  }
}
