package org.ouobpo.tools.amazonchecker.exception;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
@SuppressWarnings("serial")
public class DomainException extends Exception {
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
