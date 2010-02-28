package org.ouobpo.tools.baobab.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author tadayosi
 */
public class MonthlyStatistics {
  public static final String TABLE = "monthly_statistics";

  private int                fYear;
  private int                fMonth;
  private int                fNumber;
  private int                fPrice;

  public MonthlyStatistics() {}

  public MonthlyStatistics(int year, int month, int number, int price) {
    fYear = year;
    fMonth = month;
    fNumber = number;
    fPrice = price;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  //---------------------------------------------------------------------------
  // Getters
  //---------------------------------------------------------------------------

  public int getYear() {
    return fYear;
  }

  public int getMonth() {
    return fMonth;
  }

  public int getNumber() {
    return fNumber;
  }

  public int getPrice() {
    return fPrice;
  }

  //---------------------------------------------------------------------------
  // Setters
  //---------------------------------------------------------------------------

  public void setYear(int year) {
    fYear = year;
  }

  public void setMonth(int month) {
    fMonth = month;
  }

  public void setNumber(int number) {
    fNumber = number;
  }

  public void setPrice(int price) {
    fPrice = price;
  }
}
