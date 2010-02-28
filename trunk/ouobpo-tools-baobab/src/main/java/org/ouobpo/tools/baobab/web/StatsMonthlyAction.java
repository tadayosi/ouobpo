package org.ouobpo.tools.baobab.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ouobpo.tools.baobab.domain.MonthlyStatistics;
import org.ouobpo.tools.baobab.domain.MonthlyStatisticsTable;

/**
 * A controller which shows monthly statistics of books.
 * 
 * @author tadayosi
 */
public class StatsMonthlyAction extends ActionBase {
  private static final Log             LOG = LogFactory.getLog(StatsMonthlyAction.class);

  /** statistics tables. */
  private List<MonthlyStatisticsTable> fMonthlyStatisticsTables;

  public StatsMonthlyAction() {
    fMonthlyStatisticsTables = new ArrayList<MonthlyStatisticsTable>();

  }

  //-----------------------------------------------------------------------------------------------
  // Actions
  //-----------------------------------------------------------------------------------------------

  public String initialize() {
    // reads statistics.
    List<MonthlyStatistics> allStats = fMonthlyStatisticsDao.getAllMonthlyStatistics();
    // *** DEBUG LOG ***
    if (LOG.isDebugEnabled()) {
      LOG.debug("size of monthly statistics: " + allStats.size());
    }

    // initializes tables.
    fMonthlyStatisticsTables = MonthlyStatisticsTable.createTables(allStats);

    return null;
  }

  //-----------------------------------------------------------------------------------------------
  // Getters
  //-----------------------------------------------------------------------------------------------

  public List<MonthlyStatisticsTable> getTables() {
    return fMonthlyStatisticsTables;
  }

  //-----------------------------------------------------------------------------------------------
  // Setters
  //-----------------------------------------------------------------------------------------------

}