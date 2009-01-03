package org.ouobpo.tools.amazonchecker.service;

import org.ouobpo.tools.amazonchecker.service.impl.AWSRestAmazonService;

/**
 * @author SATO, Tadayosi
 * @version $Id$
 */
public class AmazonServiceFactory {
  public static IAmazonService createAmazonService() {
    return new AWSRestAmazonService();
  }
}