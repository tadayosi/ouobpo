package org.ouobpo.tools.amazonchecker.service.impl;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.ouobpo.tools.amazonchecker.exception.ServiceException;
import org.ouobpo.tools.amazonchecker.service.IAmazonService.BookData;

import com.domainlanguage.money.Money;

/**
 * @author hanao
 * @since 2008/12/31
 * @version $Id$
 */
public class AWSRestAmazonServiceTest {

  @Test
  public void getBookData_book() throws ServiceException {
    AWSRestAmazonService service = new AWSRestAmazonService() {
      @Override
      protected String lookupItem(String asin) {
        try {
          return IOUtils.toString(
              AWSRestAmazonServiceTest.class.getResourceAsStream(AWSRestAmazonServiceTest.class.getSimpleName()
                  + ".xml"),
              "UTF-8");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    };

    final Money yen_2730 = new Money(
        BigDecimal.valueOf(2730),
        Currency.getInstance("JPY"));
    final Money yen_2250 = new Money(
        BigDecimal.valueOf(2250),
        Currency.getInstance("JPY"));

    BookData book = service.getBookData("487311389X");

    assertThat(
        book.getTitle(),
        is("ThoughtWorksアンソロジー ―アジャイルとオブジェクト指向によるソフトウェアイノベーション"));
    assertThat(book.getListPrice(), is(yen_2730));
    assertThat(book.getUsedPrice(), is(yen_2250));
  }
}
