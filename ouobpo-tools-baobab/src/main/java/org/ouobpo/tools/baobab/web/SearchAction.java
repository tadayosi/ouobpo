package org.ouobpo.tools.baobab.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ouobpo.tools.baobab.dao.YearPagerCondition;
import org.ouobpo.tools.baobab.domain.Book;

/**
 * A controller for searching books.
 * 
 * @author tadayosi
 */
public class SearchAction extends ActionBase {
  private static final Log   LOG = LogFactory.getLog(SearchAction.class);

  private String             fWord;
  private List<Book>         fBooks;

  /** session managed. */
  private YearPagerCondition fYearCondition;

  //-----------------------------------------------------------------------------------------------
  // Actions
  //-----------------------------------------------------------------------------------------------

  public String initialize() {
    // invalid
    if (StringUtils.isEmpty(fWord)) {
      FacesUtils.error("検索語を入力してください");
      fBooks = new ArrayList<Book>();
    }
    // valid
    else {
      fBooks = fBookDao.searchBooksByWord("%" + fWord.trim() + "%");
      FacesUtils.info(fBooks.size() + " 件の書籍が該当しました");
      // *** DEBUG LOG ***
      if (LOG.isDebugEnabled()) {
        LOG.debug("word=\"" + fWord + "\" : " + fBooks.size() + " books.");
      }
    }

    // updates count on the menu part.
    fYearCondition.setCount(fBooks.size());

    return null;
  }

  //-----------------------------------------------------------------------------------------------
  // Getters
  //-----------------------------------------------------------------------------------------------

  public String getWord() {
    return fWord;
  }

  public List<Book> getBooks() {
    return fBooks;
  }

  //-----------------------------------------------------------------------------------------------
  // Setters
  //-----------------------------------------------------------------------------------------------

  public void setWord(String title) {
    fWord = title;
  }

  public void setYearCondition(YearPagerCondition condition) {
    fYearCondition = condition;
  }
}
