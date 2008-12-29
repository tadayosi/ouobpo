package org.ouobpo.tools.amazonchecker.service.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.ouobpo.tools.amazonchecker.exception.ServiceException;
import org.ouobpo.tools.amazonchecker.service.IAmazonService.BookData;

import com.domainlanguage.money.Money;

/**
 * @author SATO, Tadayosi
 * @version $Id: HTMLParsingAmazonServiceTest.java 886 2008-05-11 11:25:46Z hanao $
 */
public class HTMLParsingAmazonServiceTest {

  /** テスト対象 */
  private HTMLParsingAmazonService fService;

  @Before
  public void setUp() {
    fService = new HTMLParsingAmazonService();
  }

  /**
   * 「書籍」データ取得のテスト
   */
  @Test
  public void getBookData_book() throws ServiceException {
    final Money YEN_2604 = new Money(
        BigDecimal.valueOf(2604),
        Currency.getInstance("JPY"));

    BookData book = fService.getBookData("477412950X");

    assertThat(book.getTitle(), is("ソースコードリーディングから学ぶ Javaの設計と実装"));
    assertThat(book.getListPrice(), is(YEN_2604));
  }

  /**
   * 「TVゲーム」データ取得のテスト
   */
  @Test
  public void getBookData_game() throws ServiceException {
    BookData game = fService.getBookData("B0002HUCNQ");

    assertThat(game.getTitle(), is("ICO PlayStation 2 the Best"));
    assertThat(game.getListPrice(), is(notNullValue()));
  }
}
