package org.ouobpo.tools.amazonchecker.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.ouobpo.tools.amazonchecker.Configuration;
import org.ouobpo.tools.amazonchecker.domain.AmazonChecker;
import org.ouobpo.tools.amazonchecker.domain.Book;
import org.ouobpo.tools.amazonchecker.domain.BookPrice;
import org.ouobpo.tools.amazonchecker.exception.DomainException;

import com.domainlanguage.time.Duration;
import com.domainlanguage.timeutil.SystemClock;

/**
 * @author SATO, Tadayosi
 * @version $Id: MainFrame.java 887 2008-05-11 12:29:14Z hanao $
 */
public class MainFrame extends JFrame {
  private static final long serialVersionUID   = -393193197673432437L;

  private AmazonChecker     fAmazonChecker     = new AmazonChecker();

  private List<Book>        fBooks             = null;
  // FIXME: 可変履歴サイズ
  private int               fHistorySize       = 5;

  private JPanel            rootContentPane    = null;
  private JTabbedPane       tabbedPane         = null;

  private JPanel            mainPanel          = null;
  private JScrollPane       priceScrollPane    = null;
  private JTable            priceTable         = null;

  private JPanel            settingsPanel      = null;
  private JButton           updateButton       = null;
  private JTextField        inputAsinField     = null;
  private JButton           addBookButton      = null;
  private JLabel            statusLabel        = null;
  private JPanel            messagePanel       = null;
  private JPanel            addBookPanel       = null;
  private JLabel            jLabel             = null;
  private JScrollPane       settingsScrollPane = null;
  private JTable            settingsTable      = null;

  public MainFrame() {
    super();
    initialize();
  }

  private void initialize() {
    doLoadBooks();

    this.setSize(800, 600);
    this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    this.setContentPane(getRootContentPane());
    this.setTitle("AmazonChecker v" + Configuration.instance().getVersion());
    this.setVisible(true);
  }

  private JPanel getRootContentPane() {
    if (rootContentPane == null) {
      BorderLayout borderLayout = new BorderLayout();
      rootContentPane = new JPanel();
      rootContentPane.setLayout(borderLayout);
      rootContentPane.add(getTabbedPane(), BorderLayout.CENTER);
      rootContentPane.add(getMessagePanel(), BorderLayout.SOUTH);
    }
    return rootContentPane;
  }

  private JTabbedPane getTabbedPane() {
    if (tabbedPane == null) {
      tabbedPane = new JTabbedPane();
      tabbedPane.addTab("メイン", null, getMainPanel(), null);
      tabbedPane.addTab("設定", null, getSettingsPanel(), null);
    }
    return tabbedPane;
  }

  //----------------------------------------------------------------------------
  // メイン
  //----------------------------------------------------------------------------

  private JPanel getMainPanel() {
    if (mainPanel == null) {
      mainPanel = new JPanel();
      mainPanel.setLayout(new BorderLayout());

      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      panel.add(getUpdateButton());
      mainPanel.add(panel, BorderLayout.NORTH);

      mainPanel.add(getPriceScrollPane(), BorderLayout.CENTER);
    }
    return mainPanel;
  }

  private JButton getUpdateButton() {
    if (updateButton == null) {
      updateButton = new JButton();
      updateButton.setText("更　新");
      updateButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          doUpdate();
        }
      });
    }
    return updateButton;
  }

  private JScrollPane getPriceScrollPane() {
    if (priceScrollPane == null) {
      priceScrollPane = new JScrollPane();
      priceScrollPane.setViewportView(getPriceTable());
    }
    return priceScrollPane;
  }

  private JTable getPriceTable() {
    if (priceTable == null) {
      priceTable = new JTable();
      JPopupMenu menu = new JPopupMenu();
      JMenuItem jump = new JMenuItem("Amazonページへ");
      jump.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          doGoToWebSite();
        }
      });
      menu.add(jump);
      priceTable.setComponentPopupMenu(menu);
      doRefreshPriceTable();
    }
    return priceTable;
  }

  //----------------------------------------------------------------------------
  // 設定
  //----------------------------------------------------------------------------

  private JPanel getSettingsPanel() {
    if (settingsPanel == null) {
      settingsPanel = new JPanel();
      settingsPanel.setLayout(new BorderLayout());
      settingsPanel.add(getAddBookPanel(), BorderLayout.NORTH);
      settingsPanel.add(getSettingsScrollPane(), BorderLayout.CENTER);
    }
    return settingsPanel;
  }

  private JPanel getAddBookPanel() {
    if (addBookPanel == null) {
      jLabel = new JLabel();
      jLabel.setText("ASIN");
      addBookPanel = new JPanel();
      addBookPanel.setLayout(new FlowLayout());
      addBookPanel.add(jLabel, null);
      addBookPanel.add(getInputAsinField(), null);
      addBookPanel.add(getAddBookButton(), null);
    }
    return addBookPanel;
  }

  private JTextField getInputAsinField() {
    if (inputAsinField == null) {
      inputAsinField = new JTextField();
      inputAsinField.setColumns(20);
      inputAsinField.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          doAddBook();
        }
      });
    }
    return inputAsinField;
  }

  private JButton getAddBookButton() {
    if (addBookButton == null) {
      addBookButton = new JButton();
      addBookButton.setText("追　加");
      addBookButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          doAddBook();
        }
      });
    }
    return addBookButton;
  }

  private JScrollPane getSettingsScrollPane() {
    if (settingsScrollPane == null) {
      settingsScrollPane = new JScrollPane();
      settingsScrollPane.setViewportView(getSettingsTable());
    }
    return settingsScrollPane;
  }

  private JTable getSettingsTable() {
    if (settingsTable == null) {
      settingsTable = new JTable();
      JPopupMenu menu = new JPopupMenu();
      JMenuItem delete = new JMenuItem("非アクティブ化");
      delete.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          doDeactivate();
        }
      });
      menu.add(delete);
      settingsTable.setComponentPopupMenu(menu);
      doRefleshSettingsTable();
    }
    return settingsTable;
  }

  //----------------------------------------------------------------------------
  // ステータス
  //----------------------------------------------------------------------------

  private JPanel getMessagePanel() {
    if (messagePanel == null) {
      FlowLayout flowLayout = new FlowLayout();
      flowLayout.setHgap(0);
      flowLayout.setVgap(0);
      flowLayout.setAlignment(FlowLayout.LEFT);
      messagePanel = new JPanel();
      messagePanel.setLayout(flowLayout);
      statusLabel = new JLabel();
      messagePanel.add(new JLabel("> "), null);
      messagePanel.add(statusLabel, null);
    }
    return messagePanel;
  }

  //----------------------------------------------------------------------------
  // コントローラ
  //----------------------------------------------------------------------------

  private void doLoadBooks() {
    fBooks = fAmazonChecker.getActiveBooks();
  }

  private void doRefreshPriceTable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ASIN");
    model.addColumn("タイトル");
    model.addColumn("価格");
    model.addColumn("USED");
    for (Book book : fBooks) {
      // FIXME: 価格の変化に色をつける
      // 最新値＆最安値
      addLatestAndLowestRow(model, book);
      List<BookPrice> list = book.getListPriceHistory();
      List<BookPrice> used = book.getUsedPriceHistory();
      // 履歴
      for (int i = 1; i < Math.min(
          Math.max(list.size(), used.size()),
          fHistorySize - 1); i++) {
        model.addRow(new Object[] {
            book.getAsin(),
            "",
            list.size() > i ? list.get(list.size() - i - 1) : "-",
            used.size() > i ? used.get(used.size() - i - 1) : "-"});
      }
    }
    priceTable.setModel(model);
    priceTable.getColumn("ASIN").setPreferredWidth(0);
    priceTable.getColumn("タイトル").setPreferredWidth(500);
    priceTable.getColumn("価格").setPreferredWidth(100);
    priceTable.getColumn("USED").setPreferredWidth(100);
  }

  private void addLatestAndLowestRow(DefaultTableModel model, Book book) {
    model.addRow(new Object[] {
        book.getAsin(),
        book.getTitle(),
        book.getLatestListPrice()
            + " ("
            + book.getLowestListPrice()
            + ") "
            + listPriceIndicator(book),
        book.getLatestUsedPrice()
            + " ("
            + book.getLowestUsedPrice()
            + ") "
            + usedPriceIndicator(book)});
  }

  private String listPriceIndicator(Book book) {
    if (SystemClock.now().minus(Duration.hours(12)).isBefore(
        book.getLatestListPrice().getCreatedTime())) {
      return book.listPriceIndicator();
    }
    return "";
  }

  private String usedPriceIndicator(Book book) {
    if (SystemClock.now().minus(Duration.hours(12)).isBefore(
        book.getLatestUsedPrice().getCreatedTime())) {
      return book.usedPriceIndicator();
    }
    return "";
  }

  private void doRefleshSettingsTable() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ASIN");
    model.addColumn("タイトル");
    model.addColumn("登録日");
    for (Book book : fBooks) {
      model.addRow(new Object[] {
          book.getAsin(),
          book.getTitle(),
          book.getCreatedTimeInString("yyyy/MM/dd")});
    }
    settingsTable.setModel(model);
    settingsTable.getColumn("ASIN").setPreferredWidth(40);
    settingsTable.getColumn("タイトル").setPreferredWidth(550);
    settingsTable.getColumn("登録日").setPreferredWidth(30);
  }

  private void doAddBook() {
    if ("".equals(inputAsinField.getText())) {
      error("ASINが入力されていません");
      return;
    }
    try {
      fAmazonChecker.addBook(inputAsinField.getText());
      doLoadBooks();
      doRefleshSettingsTable();
      doRefreshPriceTable();
      info("書籍を追加しました");
      inputAsinField.setText("");
    } catch (DomainException e) {
      error(e.getMessage());
    }
  }

  private void doDeactivate() {
    if (settingsTable.getSelectedRow() < 0) {
      error("書籍が選択されていません");
      return;
    }
    String asin = (String) settingsTable.getModel().getValueAt(
        settingsTable.getSelectedRow(),
        0);
    String title = (String) settingsTable.getModel().getValueAt(
        settingsTable.getSelectedRow(),
        1);
    int result = JOptionPane.showConfirmDialog(this, "「"
        + title
        + "」を非アクティブにします。", "書籍の非アクティブ化", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      // 非アクティブ化実行
      fAmazonChecker.deactivateBook(asin);
      doLoadBooks();
      doRefleshSettingsTable();
      doRefreshPriceTable();
      info("非アクティブ化しました");
    }
  }

  private void doUpdate() {
    if (fBooks.isEmpty()) {
      error("書籍情報がありません");
      return;
    }
    fAmazonChecker.check();
    doLoadBooks();
    doRefleshSettingsTable();
    doRefreshPriceTable();
    info("更新しました");
  }

  private void doGoToWebSite() {
    if (priceTable.getSelectedRow() < 0) {
      error("書籍が選択されていません");
      return;
    }
    String asin = (String) priceTable.getModel().getValueAt(
        priceTable.getSelectedRow(),
        0);
    for (Book book : fBooks) {
      if (book.getAsin().equals(asin)) {
        book.showAmazonPage();
      }
    }
  }

  //----------------------------------------------------------------------------
  // メッセージ出力
  //----------------------------------------------------------------------------

  private void info(String msg) {
    statusLabel.setForeground(Color.BLACK);
    statusLabel.setText(msg);
  }

  private void error(String msg) {
    statusLabel.setForeground(Color.RED);
    statusLabel.setText(msg);
  }
}
