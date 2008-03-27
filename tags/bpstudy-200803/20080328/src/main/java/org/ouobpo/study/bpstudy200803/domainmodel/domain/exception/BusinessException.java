package org.ouobpo.study.bpstudy200803.domainmodel.domain.exception;

/**
 * ビジネス例外クラス
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 3005907434556469158L;

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(Throwable cause) {
    super(cause);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

}
