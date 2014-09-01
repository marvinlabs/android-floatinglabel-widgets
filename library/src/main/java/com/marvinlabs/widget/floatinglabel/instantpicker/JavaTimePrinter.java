package com.marvinlabs.widget.floatinglabel.instantpicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A default implementation using the java.util.DateFormat class with the default locale
 */
public class JavaTimePrinter<TimeInstantT extends TimeInstant> implements TimePrinter<TimeInstantT> {

    final DateFormat timeFormat;

    /**
     * Constructor
     *
     * @param timeStyle one of DateFormat's SHORT, MEDIUM, LONG, FULL, or DEFAULT.
     */
    public JavaTimePrinter(int timeStyle) {
        this.timeFormat = DateFormat.getTimeInstance(timeStyle);
    }

    @Override
    public String print(TimeInstantT timeInstant) {
        if (timeInstant == null) return "";

        Calendar cal = new GregorianCalendar(0, 0, 0, timeInstant.getHourOfDay(), timeInstant.getMinuteOfHour(), timeInstant.getSecondOfMinute());
        return timeFormat.format(cal.getTime());
    }
}