package org.ouobpo.tools.baobab.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.ouobpo.tools.baobab.domain.Book;

/**
 * @author tadayosi
 */
public class CSVUploadAction extends ActionBase {
  private static final Log LOG = LogFactory.getLog(CSVUploadAction.class);

  private UploadedFile     fFile;

  public String load() {
    if (LOG.isInfoEnabled()) {
      LOG.info("loading file <" + fFile.getName() + ">...");
    }
    try {
      String csv = new String(fFile.getBytes());
      List<Book> books = csv2books(csv);

      // registers books.
      for (Book book : books) {
        fBookDao.insert(book);
      }

      if (LOG.isInfoEnabled()) {
        LOG.info("loaded " + books.size() + " books.");
      }
      FacesUtils.info(books.size() + " 冊をファイルから登録しました。");

    } catch (Exception e) {
      if (LOG.isWarnEnabled()) {
        LOG.warn("error occured while reading <" + fFile.getName() + ">.", e);
      }
      FacesUtils.error("ファイルの読込に失敗しました: ", e.getMessage());
      return "failure";
    }
    return "success";
  }

  private List<Book> csv2books(String csv) {
    List<Book> books = new ArrayList<Book>();
    String[] lines = csv.split("\r\n");
    for (String line : lines) {
      if (StringUtils.isEmpty(line)) {
        continue;
      }
      String[] fields = line.split(",");
      Calendar calendar = Calendar.getInstance();
      calendar.clear();
      calendar.set(
          Integer.parseInt(fields[0].trim()),
          Integer.parseInt(fields[1].trim()) - 1,
          Integer.parseInt(fields[2].trim()));
      String title = fields[3].trim();
      String authors = fields[4].trim();
      String publisher = fields[5].trim();
      int price = Integer.parseInt(fields[6].startsWith("\\")
          ? fields[6].substring(1).trim()
          : fields[6].trim());
      books.add(new Book(
          -1,
          title,
          authors,
          publisher,
          calendar.getTime(),
          price,
          null));
    }
    return books;
  }

  //-----------------------------------------------------------------------------------------------
  // Getters
  //-----------------------------------------------------------------------------------------------

  public UploadedFile getFile() {
    return fFile;
  }

  //-----------------------------------------------------------------------------------------------
  // Setters
  //-----------------------------------------------------------------------------------------------

  public static final String file_BINDING = "bindingType=none"; // annotation

  public void setFile(UploadedFile file) {
    fFile = file;
  }
}