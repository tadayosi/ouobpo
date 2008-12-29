package org.ouobpo.tools.amazonchecker.service;

import org.ouobpo.tools.amazonchecker.service.impl.HTMLParsingAmazonService;

/**
 * @author SATO, Tadayosi
 * @version $Id: AmazonServiceFactory.java 684 2007-02-07 14:31:17Z hanao $
 */
public class AmazonServiceFactory {

  public static IAmazonService createAmazonService() {
    return new HTMLParsingAmazonService();
  }
}