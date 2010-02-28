package org.ouobpo.tools.baobab.dao;

import java.util.List;

import org.ouobpo.tools.baobab.domain.Book;

/**
 * Interface of book data access object.
 * 
 * @author tadayosi
 */
public interface IBookDao {

  static final Class<?> BEAN = Book.class;

  void insert(Book book);

  void update(Book book);

  void delete(Book book);

  long count();

  int oldestYear();

  List<Book> getAllBooks();

  static final String getBook_ARGS = "id";

  Book getBook(long id);

  static final String getBooksByTitle_ARGS = "title";

  List<Book> getBooksByTitle(String title);

  static final String getBooksByYear_ARGS = "year";

  List<Book> getBooksByYear(int year);

  static final String getBooksByYearPagerCondition_ARGS = "cond";

  List<Book> getBooksByYearPagerCondition(YearPagerCondition cond);

  static final String searchBooksByWord_ARGS = "word";

  List<Book> searchBooksByWord(String word);
}
