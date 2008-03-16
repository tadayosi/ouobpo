package org.ouobpo.study.bpstudy200803.domainmodel.domain.exception;

/**
 * システム例外クラス
 */
public class SystemException extends RuntimeException {

  private static final long serialVersionUID = 1301551381457031891L;

  public SystemException() {
    super();
  }

  public SystemException(String message) {
    super(message);
  }

  public SystemException(Throwable cause) {
    super(cause);
  }

  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }

}
