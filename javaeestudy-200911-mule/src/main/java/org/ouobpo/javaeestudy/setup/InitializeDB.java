package org.ouobpo.javaeestudy.setup;

import org.ouobpo.dbinit.DBInitializer;
import org.ouobpo.dbinit.spring.SpringDBInitializer;

public class InitializeDB {
  public static void main(String[] args) {
    DBInitializer dbinit = new SpringDBInitializer("beans.xml");
    dbinit.executeFile("src/main/resources/create_table.sql");
  }
}
