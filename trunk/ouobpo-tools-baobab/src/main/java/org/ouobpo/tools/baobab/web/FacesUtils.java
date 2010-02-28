package org.ouobpo.tools.baobab.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Utilities for JSF controllers.
 * 
 * @author tadayosi
 */
public class FacesUtils {

  public static void error(String msg) {
    error(msg, "");
  }

  public static void error(String msg, String detail) {
    FacesContext.getCurrentInstance().addMessage(
        null,
        new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail));
  }

  public static void info(String msg) {
    info(msg, "");
  }

  public static void info(String msg, String detail) {
    FacesContext.getCurrentInstance().addMessage(
        null,
        new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detail));
  }

  /**
   * do not instantiate.
   */
  private FacesUtils() {}
}
