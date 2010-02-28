package org.ouobpo.tools.baobab.dao;

import java.util.List;

import org.ouobpo.tools.baobab.domain.MonthlyStatistics;

/**
 * This dao is for view. Only data reference operations are allowed.
 * 
 * @author tadayosi
 */
public interface IMonthlyStatisticsDao {
  static final Class<?> BEAN = MonthlyStatistics.class;

  List<MonthlyStatistics> getAllMonthlyStatistics();
}