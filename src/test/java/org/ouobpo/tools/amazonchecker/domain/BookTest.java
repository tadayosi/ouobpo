package org.ouobpo.tools.amazonchecker.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.domainlanguage.money.Money;

public class BookTest {

  private static final Money YEN_1   = new Money(
                                         BigDecimal.valueOf(1),
                                         Currency.getInstance("JPY"));
  private static final Money YEN_10  = new Money(
                                         BigDecimal.valueOf(10),
                                         Currency.getInstance("JPY"));
  private static final Money YEN_100 = new Money(
                                         BigDecimal.valueOf(100),
                                         Currency.getInstance("JPY"));

  @Test
  public void testLowestListPrice() {
    Book book = new Book();
    book.addListPriceHistory(BookPrice.newListPrice(YEN_100));
    book.addListPriceHistory(BookPrice.newListPrice(null));
    book.addListPriceHistory(BookPrice.newListPrice(YEN_1));
    book.addListPriceHistory(BookPrice.newListPrice(YEN_10));

    assertThat(book.getLowestListPrice(), notNullValue());
    assertThat(book.getLowestListPrice().getPrice(), is(YEN_1));
  }

  @Test
  public void testLowestUsedPrice() {
    Book book = new Book();
    book.addUsedPriceHistory(BookPrice.newUsedPrice(YEN_100));
    book.addUsedPriceHistory(BookPrice.newUsedPrice(null));
    book.addUsedPriceHistory(BookPrice.newUsedPrice(YEN_1));
    book.addUsedPriceHistory(BookPrice.newUsedPrice(YEN_10));

    assertThat(book.getLowestUsedPrice(), notNullValue());
    assertThat(book.getLowestUsedPrice().getPrice(), is(YEN_1));
  }
}
