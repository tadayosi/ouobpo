package org.ouobpo.ouvroir.jms.hello;

import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JndiUtils {
  private static final Logger   LOGGER                   = LoggerFactory.getLogger(JndiUtils.class);

  private static final String   TOPIC_CONNECTION_FACTORY = "topicConnectionFactory";
  private static final String   HELLO_TOPIC              = "HelloTopic";

  private static InitialContext fContext                 = null;
  static {
    try {
      fContext = new InitialContext();
    } catch (NamingException e) {
      LOGGER.error(e.getMessage(), e);
      System.exit(1);
    }
  }

  public static TopicConnectionFactory lookupTopicConnectionFactory()
      throws NamingException {
    return (TopicConnectionFactory) fContext.lookup(TOPIC_CONNECTION_FACTORY);
  }

  public static Topic lookupTopic() throws NamingException {
    return (Topic) fContext.lookup(HELLO_TOPIC);
  }

  private JndiUtils() {}
}
