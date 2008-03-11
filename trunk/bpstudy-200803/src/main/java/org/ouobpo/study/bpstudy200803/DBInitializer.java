package org.ouobpo.study.bpstudy200803;

import org.ouobpo.dbinit.seasar.S2DBInitializer;

public class DBInitializer {
  public static void main(String[] args) {
    S2DBInitializer dbinit = new S2DBInitializer("jdbc.dicon");
    dbinit.executeFile("src/main/resources/sql/create.sql");
  }
}
