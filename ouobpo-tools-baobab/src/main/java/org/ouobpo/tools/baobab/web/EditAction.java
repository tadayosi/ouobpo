package org.ouobpo.tools.baobab.web;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ouobpo.tools.baobab.domain.Book;

/**
 * @author tadayosi
 */
public class EditAction extends ActionBase {

  private static final Log LOG   = LogFactory.getLog(EditAction.class);

  private long             fId   = -1;
  private String           fTitle;
  private String           fAuthors;
  private String           fPublisher;
  private Date             fDate = new Date();
  private int              fPrice;
  private String           fNote;

  public String initialize() {
    if (fId == -1) {
      return "index";
    }

    Book toBeUpdated = fBookDao.getBook(fId);
    fTitle = toBeUpdated.getTitle();
    fAuthors = toBeUpdated.getAuthors();
    fPublisher = toBeUpdated.getPublisher();
    fDate = toBeUpdated.getDate();
    fPrice = toBeUpdated.getPrice();
    fNote = toBeUpdated.getNote();
    return null;
  }

  public String update() {
    if (fId <= 0) {
      return "index";
    }

    if (!validate()) {
      // update failed.
      if (LOG.isDebugEnabled()) {
        LOG.debug("book registration failed");
      }
      FacesUtils.error("書籍情報に誤りがあるため、更新できません");
      return "failure";
    }

    // pursues update.
    Book updated = new Book(
        fId,
        fTitle,
        fAuthors,
        fPublisher,
        fDate,
        fPrice,
        fNote);
    fBookDao.update(updated);
    if (LOG.isInfoEnabled()) {
      LOG.info("a book is updated: " + updated);
    }
    FacesUtils.info("書籍情報を更新しました");

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
    fId = -1;
    fDate = null;
    fTitle = null;
    fAuthors = null;
    fPublisher = null;
    fPrice = 0;
    fNote = null;
  }

  public String delete() {
    Book deleted = new Book(
        fId,
        fTitle,
        fAuthors,
        fPublisher,
        fDate,
        fPrice,
        fNote);
    fBookDao.delete(deleted);
    if (LOG.isInfoEnabled()) {
      LOG.info("a book is deleted: " + deleted);
    }
    FacesUtils.info("書籍情報を削除しました");
    // clear params.
    clear();
    return "success";
  }

  //-----------------------------------------------------------------------------------------------
  // Getters
  //-----------------------------------------------------------------------------------------------

  public long getId() {
    return fId;
  }

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

  public void setId(long id) {
    fId = id;
  }

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