package org.ouobpo.ouvroir.mule.scenario;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transformer.TransformerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusRegistry implements Callable {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatusRegistry.class);

  public Object onCall(MuleEventContext eventContext)
      throws TransformerException {
    // apply transformation
    eventContext.transformMessage();

    MuleMessage message = eventContext.getMessage();
    try {
      LOGGER.info(message.getPayloadAsString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return message;
  }

}
