package org.ouobpo.tools.baobab.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ouobpo.tools.baobab.util.SortUtils;

/**
 * @author tadayosi
 */
public class MonthlyStatisticsTable {

  private int                             fYear;
  private Map<Integer, MonthlyStatistics> fData;

  /**
   * Factory method. Created list is descendent order in year.
   */
  public static List<MonthlyStatisticsTable> createTables(
      List<MonthlyStatistics> statsList) {
    Map<Integer, MonthlyStatisticsTable> tables = new HashMap<Integer, MonthlyStatisticsTable>();
    for (MonthlyStatistics stats : statsList) {
      if (!tables.containsKey(stats.getYear())) {
        tables.put(stats.getYear(), new MonthlyStatisticsTable(stats.getYear()));
      }
      MonthlyStatisticsTable table = tables.get(stats.getYear());
      table.add(stats);
    }
    // sorts in descent order.
    List<Integer> descYears = SortUtils.descend(tables.keySet());
    List<MonthlyStatisticsTable> ret = new ArrayList<MonthlyStatisticsTable>();
    for (int year : descYears) {
      ret.add(tables.get(year));
    }
    return ret;
  }

  public MonthlyStatisticsTable(int year) {
    fYear = year;
    fData = new HashMap<Integer, MonthlyStatistics>();
    for (int i = 1; i < 13; i++) {
      fData.put(i, new MonthlyStatistics(year, i, 0, 0));
    }
  }

  public void add(MonthlyStatistics stats) {
    fData.put(stats.getMonth(), stats);
  }

  //---------------------------------------------------------------------------
  // Getters
  //---------------------------------------------------------------------------

  public int getYear() {
    return fYear;
  }

  public int getTotalNumber() {
    int total = 0;
    for (MonthlyStatistics stats : fData.values()) {
      total += stats.getNumber();
    }
    return total;
  }

  public int getTotalPrice() {
    int total = 0;
    for (MonthlyStatistics stats : fData.values()) {
      total += stats.getPrice();
    }
    return total;
  }

  /**
   * ascendent order in month.
   */
  public List<MonthlyStatistics> getStatsList() {
    List<Integer> ascMonths = SortUtils.ascend(fData.keySet());
    List<MonthlyStatistics> ret = new ArrayList<MonthlyStatistics>();
    for (int month : ascMonths) {
      ret.add(fData.get(month));
    }
    return ret;
  }
}
