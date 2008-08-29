package org.ouobpo.study.bpstudy200808.jruby;

import java.util.TimeZone;

import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.Duration;
import com.domainlanguage.time.TimeOfDay;
import com.domainlanguage.time.TimePoint;

public class Client {
  public static void main(String[] args) {
    SingletonS2ContainerFactory.init();
    try {
      TimeMoneyService service = SingletonS2Container.getComponent(TimeMoneyService.class);
      TimePoint alertTime = service.dailyMeetingAlert(TimeOfDay.hourAndMinute(
          10,
          0), CalendarDate.from(2008, 8, 31), Duration.minutes(5));
      out(alertTime.toString("yyyy/MM/dd HH:mm:ss", TimeZone.getDefault()));
      CalendarDate nextDay = service.nextDay(CalendarDate.from(2008, 8, 31));
      out(nextDay.toString("yyyy/MM/dd"));
    } finally {
      SingletonS2ContainerFactory.destroy();
    }
  }

  private static void out(Object obj) {
    System.out.println("> " + obj);
  }
}
