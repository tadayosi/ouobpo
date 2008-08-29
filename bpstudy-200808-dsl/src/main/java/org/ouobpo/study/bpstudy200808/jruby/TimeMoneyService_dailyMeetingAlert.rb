# Daily stand-up meeting at 10:00am each work day. (We work in Honolulu, of course.)
# Notify 5 minutes before meeting starts.
# Derive the TimePoint at which I should notify on April 19 2006.

meeting_time.
on(day).
as_time_point($timeZoneFactory.timeZone).
minus(duration)
