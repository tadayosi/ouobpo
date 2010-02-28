package org.ouobpo.tools.baobab.web;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ouobpo.tools.baobab.domain.Book;

/**
 * A controller for book registration.
 * 
 * @author tadayosi
 */
public class RegisterAction extends ActionBase {
  private static final Log LOG   = LogFactory.getLog(RegisterAction.class);

  private String           fTitle;
  private String           fAuthors;
  private String           fPublisher;
  private Date             fDate = new Date();
  private int              fPrice;
  private String           fNote;

  //-----------------------------------------------------------------------------------------------
  // Actions
  //-----------------------------------------------------------------------------------------------

  public String register() {
    if (!validate()) {
      // registration failed.
      if (LOG.isDebugEnabled()) {
        LOG.debug("book registration failed");
      }
      FacesUtils.error("書籍情報に誤りがあるため、登録できません");
      return "failure";
    }

    // pursues registration.
    Book newBook = new Book(
        -1,
        fTitle,
        fAuthors,
        fPublisher,
        fDate,
        fPrice,
        fNote);
    fBookDao.insert(newBook);
    if (LOG.isInfoEnabled()) {
      LOG.info("a book is registered: " + newBook);
    }
    FacesUtils.info("書籍情報を登録しました");

    // clear params.
    clear();

    return "success";
  }

  private boolean validate() {
    boolean valid = true;
    if (StringUtils.isEmpty(fTitle)) {
      valid = false;
    }
    if (StringUtils.isEmpty(fAuthors)) {
      valid = false;
    }
    if (StringUtils.isEmpty(fPublisher)) {
      valid = false;
    }
    if (fDate == null) {
      valid = false;
    }
    if (fPrice < 0) {
      valid = false;
    }
    return valid;
  }

  private void clear() {
    fDate = new Date();
    fTitle = "";
    fAuthors = "";
    fPublisher = "";
    fPrice = 0;
    fNote = "";
  }

  //-----------------------------------------------------------------------------------------------
  // Getters
  //-----------------------------------------------------------------------------------------------

  public String getTitle() {
    return fTitle;
  }

  public String getAuthors() {
    return fAuthors;
  }

  public String getPublisher() {
    return fPublisher;
  }

  public Date getDate() {
    return fDate;
  }

  public int getPrice() {
    return fPrice;
  }

  public String getNote() {
    return fNote;
  }

  //-----------------------------------------------------------------------------------------------
  // Setters
  //-----------------------------------------------------------------------------------------------

  public void setTitle(String title) {
    fTitle = title;
  }

  public void setAuthors(String authors) {
    fAuthors = authors;
  }

  public void setPublisher(String publisher) {
    fPublisher = publisher;
  }

  public void setDate(Date date) {
    fDate = date;
  }

  public void setPrice(int price) {
    fPrice = price;
  }

  public void setNote(String note) {
    fNote = note;
  }
}
