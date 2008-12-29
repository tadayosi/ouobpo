package org.ouobpo.tools.amazonchecker.exception;

/**
 * @author SATO, Tadayosi
 * @version $Id: DomainException.java 835 2008-03-07 15:17:25Z hanao $
 */
public class DomainException extends Exception {

  private static final long serialVersionUID = -2802686566573654960L;

  public DomainException() {
    super();
  }

  public DomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainException(String message) {
    super(message);
  }

  public DomainException(Throwable cause) {
    super(cause);
  }
}
