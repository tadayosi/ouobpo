package org.ouobpo.tools.amazonchecker.service;

import org.ouobpo.tools.amazonchecker.service.impl.HTMLParsingAmazonService;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
public class AmazonServiceFactory {

  public static IAmazonService createAmazonService() {
    return new HTMLParsingAmazonService();
  }
}