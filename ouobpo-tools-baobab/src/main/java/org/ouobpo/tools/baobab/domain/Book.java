package org.ouobpo.tools.baobab.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Book domain object.
 * 
 * @author tadayosi
 */
public class Book {

  //public static final String TABLE = "book";
  public static final String id_ID = "sequence, sequenceName=book_seq";

  private long               fId;
  private String             fTitle;
  private String             fAuthors;
  private String             fPublisher;
  private Date               fDate;
  private int                fPrice;
  private String             fNote;

  public Book() {}

  public Book(
      long id,
      String title,
      String authors,
      String publisher,
      Date date,
      int price,
      String note) {
    fId = id;
    fTitle = title;
    fAuthors = authors;
    fPublisher = publisher;
    fDate = date;
    fPrice = price;
    fNote = note;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
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