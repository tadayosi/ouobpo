package org.ouobpo.tools.iranja;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.MetadataException;

public class JpegArrangerTest {

  private JpegArranger fJpegArranger;

  @Before
  public void setUp() {
    fJpegArranger = new JpegArranger();
  }

  @Test
  public void testCollectJpegsIn() {
    List<File> result = new ArrayList<File>();
    fJpegArranger.collectJpegsIn(new File("src/test/resources"), result);
    assertThat(result.size(), is(1));
    assertThat(
        result.contains(new File("src/test/resources/sample.jpg")),
        is(true));

    result = new ArrayList<File>();
    fJpegArranger.collectJpegsIn(new File("src/test"), result);
    assertThat(result.size(), is(1));
    assertThat(
        result.contains(new File("src/test/resources/sample.jpg")),
        is(true));

    result = new ArrayList<File>();
    fJpegArranger.collectJpegsIn(new File("src"), result);
    assertThat(result.isEmpty(), is(true));
  }

  @Test
  public void testOriginalDateOf()
      throws JpegProcessingException, MetadataException, ParseException {
    Date originalDateTime = fJpegArranger.originalDateOf(JpegArrangerTest.class.getClassLoader().getResourceAsStream(
        "sample.jpg"));
    assertThat(
        DateFormatUtils.format(originalDateTime, "yyyy/MM/dd HH:mm"),
        is("2008/03/09 12:48"));
  }

  @Test
  public void testDirectoryOf() throws ParseException {
    File dir = fJpegArranger.directoryOf(DateUtils.parseDate(
        "2008/01/01",
        new String[] {"yyyy/MM/dd"}));
    assertThat(dir.getName(), is("20080101"));
  }

}
