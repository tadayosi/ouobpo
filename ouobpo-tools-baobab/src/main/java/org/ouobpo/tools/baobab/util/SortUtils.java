package org.ouobpo.tools.baobab.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

/**
 * Sorting utilities.
 * 
 * @author tadayosi
 */
public class SortUtils {
  /** do not instantiate this. */
  private SortUtils() {}

  public static List<Integer> ascend(List<Integer> integers) {
    return sort(integers, new Comparator<Integer>() {
      public int compare(Integer i1, Integer i2) {
        return i1.compareTo(i2);
      }
    });
  }

  public static List<Integer> ascend(Set<Integer> integers) {
    return ascend(toList(integers));
  }

  public static List<Integer> descend(List<Integer> integers) {
    return sort(integers, new Comparator<Integer>() {
      public int compare(Integer i1, Integer i2) {
        return i2.compareTo(i1);
      }
    });
  }

  public static List<Integer> descend(Set<Integer> integers) {
    return descend(toList(integers));
  }

  private static List<Integer> toList(Set<Integer> set) {
    @SuppressWarnings("unchecked")
    List<Integer> list = ListOrderedSet.decorate(set).asList();
    return list;
  }

  private static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
    List<T> sorted = new ArrayList<T>();
    sorted.addAll(list);
    Collections.sort(sorted, comparator);
    return sorted;
  }

  /*
   private static Object[] sort(Object[] objs, Comparator comparator) {
   Object[] sorted = (Object[]) objs.clone();
   Arrays.sort(sorted, comparator);
   return sorted;
   }
   */
}