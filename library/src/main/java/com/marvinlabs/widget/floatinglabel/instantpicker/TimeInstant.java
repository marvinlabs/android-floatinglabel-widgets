package com.marvinlabs.widget.floatinglabel.instantpicker;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 01/09/2014.
 */
public interface TimeInstant extends Instant {

    int getHourOfDay();

    int getMinuteOfHour();

    int getSecondOfMinute();

    void setHourOfDay(int hourOfDay);

    void setMinuteOfHour(int minuteOfHour);

    void setSecondOfMinute(int secondOfMinute);
}
