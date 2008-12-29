package org.ouobpo.tools.amazonchecker.domain;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Test;

import com.domainlanguage.money.Money;

public class BookPriceTest {

  @Test
  public void testCompareTo() {
    BookPrice priceNull = BookPrice.newListPrice(null);
    BookPrice price1 = BookPrice.newListPrice(new Money(
        BigDecimal.valueOf(1),
        Currency.getInstance("JPY")));
    BookPrice price100 = BookPrice.newListPrice(new Money(
        BigDecimal.valueOf(100),
        Currency.getInstance("JPY")));

    // null が一番高い
    assertThat(priceNull.compareTo(priceNull), is(0));
    assertThat(price1.compareTo(price1), is(0));
    assertThat(price1.compareTo(price100), is(-1));
    assertThat(price1.compareTo(priceNull), is(-1));
    assertThat(priceNull.compareTo(price1), is(1));
    assertThat(price100.compareTo(price1), is(1));
    try {
      priceNull.compareTo(null);
      fail("compareTo の引数 null は許容されない。");
    } catch (Exception e) {
      // テスト成功
    }
  }
}
