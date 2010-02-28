package org.ouobpo.tools.baobab.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.seasar.dao.pager.DefaultPagerCondition;

/**
 * A pager condition for year.
 * 
 * @author tadayosi
 */
@SuppressWarnings("serial")
public class YearPagerCondition extends DefaultPagerCondition {
  private int fYear = Calendar.getInstance().get(Calendar.YEAR);

  public int getYear() {
    return fYear;
  }

  public void setYear(int year) {
    fYear = year;
  }

  public List<Integer> getPages() {
    int count = getCount();
    int limit = getLimit();
    List<Integer> pages = new ArrayList<Integer>();
    int page = 1;
    int remainder = count;
    while ((remainder = remainder - limit) > 0) {
      pages.add(page);
      page++;
    }
    pages.add(page);
    return pages;
  }
}
