package org.ouobpo.ouvroir.jms.hello;

import static org.ouobpo.javaextension.JavaExtensions.*;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloClient {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloClient.class);

  public static void main(String[] args) {
    HelloPublisher publisher = null;
    List<HelloSubscriber> subscribers = new ArrayList<HelloSubscriber>();
    try {
      publisher = new HelloPublisher();
      for (int i : range(1, 5)) {
        subscribers.add(new HelloSubscriber(i));
      }

      // sends message
      publisher.send("HELLO!");

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }

    sleepInSec(1);

    close(publisher, subscribers);
  }

  private static void close(
      HelloPublisher publisher,
      List<HelloSubscriber> subscribers) {
    if (publisher != null) {
      try {
        publisher.close();
      } catch (JMSException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }

    for (HelloSubscriber subscriber : subscribers) {
      try {
        subscriber.close();
      } catch (JMSException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }
}
