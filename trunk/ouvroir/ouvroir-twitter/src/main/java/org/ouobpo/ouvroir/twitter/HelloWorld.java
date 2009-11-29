package org.ouobpo.ouvroir.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class HelloWorld {
  public static void main(String[] args) {
    Twitter twitter = new Twitter("username", "password");
    try {
      Status status = twitter.updateStatus("Hello, world!");
      System.out.println(status.getText());
    } catch (TwitterException e) {
      e.printStackTrace();
    }
  }
}
