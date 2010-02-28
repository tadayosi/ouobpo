package org.ouobpo.tools.baobab.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utilities.
 * 
 * @author tadayosi
 */
public class DateUtils {
  /** do not instantiate this. */
  private DateUtils() {}

  public static String format(Date date) {
    DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    return format.format(date);
  }
}
