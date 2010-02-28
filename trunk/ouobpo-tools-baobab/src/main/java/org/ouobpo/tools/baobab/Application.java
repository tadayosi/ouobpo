package org.ouobpo.tools.baobab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ouobpo.tools.baobab.dao.IBookDao;
import org.ouobpo.tools.baobab.dao.IDBInitializer;

/**
 * Central point of the application. But, for now, this class actually
 * do nothing.
 * 
 * <p>
 * Global ToDo list of the application: <br/> 
 *   FIXME: Authors refinement.
 *   TODO: When editing in search page, page after finished should be the last search page.
 * </p>
 * 
 * @author tadayosi
 */
public class Application {
  private static final Log LOG = LogFactory.getLog(Application.class);

  /**
   * initializes DB on startup if DB is not yet initialized.
   */
  public Application(IBookDao bookDao, IDBInitializer initializer) {
    try {
      // checks if DB has a table already.
      bookDao.count();
    } catch (Exception e) {
      if (LOG.isInfoEnabled()) {
        LOG.info("initializes DB now.");
      }
      initializer.initBook();
    }
  }

  public String getApplicationTitle() {
    return "蔵書管理ツールBaobab v" + Constants.VERSION;
  }
}
