package org.ouobpo.tools.amazonchecker.exception;

/**
 * @author SATO, Tadayosi
 * @version $Id$
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
