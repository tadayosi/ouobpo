package org.ouobpo.study.bpstudy200808.jruby;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.Duration;
import com.domainlanguage.time.TimeOfDay;
import com.domainlanguage.time.TimePoint;

public interface TimeMoneyService {
  @Arguments( {"meeting_time", "day", "duration"})
  TimePoint dailyMeetingAlert(
      TimeOfDay meetingTime,
      CalendarDate day,
      Duration duration);

  @Arguments("day")
  CalendarDate nextDay(CalendarDate day);
}
