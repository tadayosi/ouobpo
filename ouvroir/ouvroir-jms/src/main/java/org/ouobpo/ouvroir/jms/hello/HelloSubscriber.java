package org.ouobpo.ouvroir.jms.hello;

import static javax.jms.Session.*;
import static org.ouobpo.javaextension.JavaExtensions.*;
import static org.ouobpo.ouvroir.jms.hello.JndiUtils.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloSubscriber implements MessageListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloSubscriber.class);

  private TopicConnection     fConnection;
  private TopicSession        fSession;

  private int                 fNumber;

  public HelloSubscriber(int number)
      throws NamingException, JMSException {
    fNumber = number;
    fConnection = lookupTopicConnectionFactory().createTopicConnection();
    fSession = fConnection.createTopicSession(false, AUTO_ACKNOWLEDGE);
    TopicSubscriber subscriber = fSession.createSubscriber(lookupTopic());
    subscriber.setMessageListener(this);
    fConnection.start();
  }

  public void onMessage(Message message) {
    try {
      printfln("[%s] %s", fNumber, ((TextMessage) message).getText());
    } catch (JMSException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  public void close() throws JMSException {
    fConnection.close();
  }
}
