package org.ouobpo.ouvroir.jms.hello;

import static javax.jms.Session.*;
import static org.ouobpo.javaextension.JavaExtensions.*;
import static org.ouobpo.ouvroir.jms.hello.JndiUtils.*;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.NamingException;

public class HelloPublisher {
  private TopicConnection fConnection;
  private TopicSession    fSession;
  private TopicPublisher  fPublisher;

  public HelloPublisher()
      throws NamingException, JMSException {
    fConnection = lookupTopicConnectionFactory().createTopicConnection();
    fSession = fConnection.createTopicSession(false, AUTO_ACKNOWLEDGE);
    fPublisher = fSession.createPublisher(lookupTopic());
    fConnection.start();
  }

  public void send(String text) throws JMSException {
    printfln(">>> %s", text);
    TextMessage message = fSession.createTextMessage();
    message.setText(text);
    fPublisher.publish(message);
  }

  public void close() throws JMSException {
    fConnection.close();
  }
}
