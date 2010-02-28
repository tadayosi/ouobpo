package org.ouobpo.tools.baobab.dao;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.extension.jdbc.UpdateHandler;
import org.seasar.extension.jdbc.impl.BasicUpdateHandler;

public class DBInitializerImpl implements IDBInitializer {
  private static final Log LOG = LogFactory.getLog(DBInitializerImpl.class);

  private final DataSource fDataSource;

  public DBInitializerImpl(DataSource ds) {
    fDataSource = ds;
  }

  /**
   * initializes table and sequence of book.
   */
  public void initBook() {
    // creates table
    String drop = "drop table book if exists";
    String create = "create table book"
        + "("
        + "    id        bigint primary key,"
        + "    title     varchar(100) not null,"
        + "    authors   varchar(100) not null,"
        + "    publisher varchar(100) not null,"
        + "    date      date not null,"
        + "    price     int not null,"
        + "    note      varchar(255)"
        + ")";
    update(drop);
    update(create);
    LOG.info("created table book");

    // creates sequence
    String dropSeq = "drop sequence book_seq if exists";
    String createSeq = "create sequence book_seq as bigint start with 1";
    update(dropSeq);
    update(createSeq);
    LOG.info("created sequence book_seq");

    // creates statistics view
    String dropStats = "drop view monthly_statistics if exists";
    String createStats = "create view monthly_statistics (year, month, number, price) as"
        + " select year(date) as year, month(date) as month, count(id), sum(price) from book"
        + " group by year(date), month(date)";
    update(dropStats);
    update(createStats);
    LOG.info("created view monthly_statistics");
  }

  private void update(String sql) {
    UpdateHandler handler = new BasicUpdateHandler(fDataSource, sql);
    handler.execute(null);
  }
}
