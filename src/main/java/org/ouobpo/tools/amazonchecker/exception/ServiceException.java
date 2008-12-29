package org.ouobpo.tools.amazonchecker.exception;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
public class ServiceException extends Exception {

  private static final long serialVersionUID = 2364969966268747669L;

  public ServiceException() {
    super();
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(Throwable cause) {
    super(cause);
  }
}
