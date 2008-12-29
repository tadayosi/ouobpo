package org.ouobpo.tools.amazonchecker.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ソート用ユーティリティ。
 * 
 * @author SATO, Tadayosi
 * @version $Id: SortUtils.java 693 2007-02-17 04:52:50Z hanao $
 */
public class SortUtils {
  public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
    List<T> sorted = new ArrayList<T>();
    sorted.addAll(list);
    Collections.sort(sorted, comparator);
    return sorted;
  }

  /** インスタンス化して使わない。 */
  private SortUtils() {}
}