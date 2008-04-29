package org.ouobpo.tools.iranja;

import static org.apache.commons.io.FileUtils.moveFileToDirectory;
import static org.apache.commons.lang.time.DateFormatUtils.format;
import static org.apache.commons.lang.time.DateUtils.parseDate;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

public class JpegArranger {

  private static final Log LOG = LogFactory.getLog(JpegArranger.class);

  public static void main(String[] args) {
    LOG.info("=== START =========================================");
    new JpegArranger().run();
    LOG.info("=== END ===========================================");
  }

  //----------------------------------------------------------------------------

  public JpegArranger() {}

  public void run() {
    File currentDir = new File(".");
    List<File> jpegs = new ArrayList<File>();
    collectJpegsIn(currentDir, jpegs);
    for (File jpeg : jpegs) {
      try {
        arrange(jpeg);
      } catch (Exception e) {
        LOG.error("failed to arrange jpeg \"" + jpeg + "\"", e);
      }
    }
  }

  protected void collectJpegsIn(File rootDir, List<File> result) {
    FileFilter jpegFilter = new SuffixFileFilter(new String[] {
        ".jpeg",
        ".jpg",
        ".JPEG",
        ".JPG"});
    // root directory
    result.addAll(Arrays.asList(rootDir.listFiles(jpegFilter)));
    // sub directories, searching only child level.
    for (File dir : rootDir.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY)) {
      result.addAll(Arrays.asList(dir.listFiles(jpegFilter)));
    }
  }

  protected void arrange(File jpeg)
      throws JpegProcessingException, MetadataException, ParseException,
      IOException {
    LOG.info(jpeg);
    Date originalDate = originalDateOf(new FileInputStream(jpeg));
    File toDir = directoryOf(originalDate);
    File destination = new File(toDir, jpeg.getName());
    if (destination.equals(jpeg)) {
      LOG.info("-> jpeg already arranged.");
    } else {
      LOG.info("-> " + destination);
      moveFileToDirectory(jpeg, toDir, true);
    }
  }

  protected Date originalDateOf(InputStream jpeg)
      throws JpegProcessingException, MetadataException, ParseException {
    Metadata metadata = JpegMetadataReader.readMetadata(jpeg);
    Directory exif = metadata.getDirectory(ExifDirectory.class);
    return parseDate(
        exif.getDescription(ExifDirectory.TAG_DATETIME_ORIGINAL),
        new String[] {"yyyy:MM:dd HH:mm:ss"});
  }

  protected File directoryOf(Date date) {
    return new File(new File("."), format(date, "yyyyMMdd"));
  }

}
