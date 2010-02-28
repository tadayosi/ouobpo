package org.ouobpo.tools.baobab.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.ouobpo.tools.baobab.dao.IBookDao;
import org.ouobpo.tools.baobab.domain.Book;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

public class IBookDaoTest {

  @Test
  public void simpleCRUD() {
    S2Container container = S2ContainerFactory.create("org/ouobpo/tools/baobab/baobab.dicon");
    container.init();
    IBookDao dao = (IBookDao) container.getComponent(IBookDao.class);

    // create & read test
    Book newBook = new Book(
        -1,
        "デザインパターン",
        "Gang of Four",
        "Addison-Wesley",
        new Date(),
        12345,
        "メモ");
    dao.insert(newBook);
    List<Book> createds = dao.getBooksByTitle("デザインパターン");
    assertTrue(createds.size() > 0);
    Book created = (Book) createds.get(0);
    assertNotNull(created);
    assertEquals("Gang of Four", created.getAuthors());
    assertEquals(12345, created.getPrice());

    // update test
    created.setAuthors("Gamma, Helm, Johnson, Vlissides");
    created.setPrice(6789);
    dao.update(created);
    Book updated = dao.getBook(created.getId());
    assertEquals("Gamma, Helm, Johnson, Vlissides", updated.getAuthors());
    assertEquals(6789, updated.getPrice());

    // delete test
    dao.delete(created);
    assertNull(dao.getBook(created.getId()));

    container.destroy();
  }
}