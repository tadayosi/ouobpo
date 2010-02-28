package org.ouobpo.tools.baobab.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ouobpo.tools.baobab.dao.YearPagerCondition;
import org.ouobpo.tools.baobab.domain.Book;

/**
 * A controller for books listing.
 * 
 * @author tadayosi
 */
public class ListAction extends ActionBase {
  private static final Log   LOG = LogFactory.getLog(ListAction.class);

  private YearPagerCondition fYearCondition;
  private List<Book>         fBooks;

  //-----------------------------------------------------------------------------------------------
  // Actions
  //-----------------------------------------------------------------------------------------------

  public String initialize() {
    fBooks = fBookDao.getBooksByYearPagerCondition(fYearCondition);
    if (LOG.isDebugEnabled()) {
      LOG.debug("total books number of the year: " + fYearCondition.getCount());
    }
    return null;
  }

  //-----------------------------------------------------------------------------------------------
  // Getters
  //-----------------------------------------------------------------------------------------------

  public long getCount() {
    return fBookDao.count();
  }

  public List<Integer> getYears() {
    int latest = Calendar.getInstance().get(Calendar.YEAR);
    int oldest = fBookDao.oldestYear();
    if (oldest == 0) {
      oldest = latest;
    }
    List<Integer> years = new ArrayList<Integer>();
    for (int i = 0; latest - i >= oldest; i++) {
      years.add(latest - i);
    }
    return years;
  }

  public List<Book> getBooks() {
    return fBooks;
  }

  //-----------------------------------------------------------------------------------------------
  // Setters
  //-----------------------------------------------------------------------------------------------

  public void setYearCondition(YearPagerCondition cond) {
    fYearCondition = cond;
  }

  public void setYear(int year) {
    fYearCondition.setYear(year);
    // initialzes condition.
    fYearCondition.setOffset(0);
  }

  public void setOffset(int offset) {
    fYearCondition.setOffset(offset);
  }
}