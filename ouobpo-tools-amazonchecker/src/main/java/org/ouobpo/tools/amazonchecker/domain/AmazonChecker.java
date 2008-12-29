package org.ouobpo.tools.amazonchecker.domain;

import java.util.ArrayList;
import java.util.List;

import org.ouobpo.tools.amazonchecker.exception.DomainException;
import org.ouobpo.tools.amazonchecker.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SATO, Tadayosi
 * @version $Id: AmazonChecker.java 876 2008-04-28 14:16:44Z hanao $
 */
public class AmazonChecker {
  private static final Logger LOGGER = LoggerFactory.getLogger(AmazonChecker.class);

  private BookRepository      fBookRepository;

  public AmazonChecker() {
    fBookRepository = new BookRepository();
  }

  public void check() {
    List<Book> books = getActiveBooks();
    List<Thread> threads = new ArrayList<Thread>();
    for (final Book book : books) {
      Thread t = new Thread() {
        public void run() {
          doCheck(book);
        }
      };
      t.start();
      threads.add(t);
    }

    // 全スレッドの終了を待つ
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        LOGGER.warn("書籍情報更新スレッド待機中のエラー", e);
      }
    }
  }

  private void doCheck(Book book) {
    LOGGER.info("書籍「{}」をチェック", book.getTitle());
    try {
      if (book.update()) {
        fBookRepository.save(book);
      }
    } catch (DomainException e) {
      LOGGER.warn(e.getMessage(), e);
    }
  }

  public void addBook(String asin) throws DomainException {
    Book existent = fBookRepository.find(asin);
    if (existent != null && existent.isActive()) {
      // 書籍は登録済み
      throw new DomainException("ASIN=" + asin + " の書籍はすでに登録されています");
    }

    if (existent == null) {
      // 新規登録
      Book book = Book.createBookFromAmazon(asin);
      fBookRepository.save(book);
      LOGGER.info("書籍追加: ASIN={}, {}", asin, book.getTitle());
    } else {
      // 既存書籍のアクティブ化
      existent.setActive(true);
      fBookRepository.save(existent);
      LOGGER.info("書籍復元: ASIN={}, {}", asin, existent.getTitle());
    }
  }

  /**
   * ASINで書籍を整列して返す。
   */
  public List<Book> getActiveBooks() {
    Book criteria = new Book();
    criteria.setActive(true);
    List<Book> books = fBookRepository.find(criteria);
    return Book.sortByTitle(books);
  }

  /**
   * 書籍の非アクティブ化
   */
  public void deactivateBook(String asin) {
    Book book = fBookRepository.find(asin);
    if (book == null) {
      return;
    }
    book.deactivate();
    fBookRepository.save(book);
    LOGGER.info("書籍非アクティブ化: ASIN={}, {}", asin, book.getTitle());
  }
}