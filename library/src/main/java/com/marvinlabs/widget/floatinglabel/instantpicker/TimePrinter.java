package com.marvinlabs.widget.floatinglabel.instantpicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class that knows how to print an instant
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface TimePrinter extends InstantPrinter<TimeInstant> {

    /**
     * Print a time
     *
     * @param timeInstant The time to print
     * @return
     */
    public String print(TimeInstant timeInstant);

    /**
     * A default implementation using the java.util.DateFormat class with the default locale
     */
    public static class JavaUtilPrinter implements TimePrinter {

        final DateFormat timeFormat;

        /**
         * Constructor
         *
         * @param timeStyle one of DateFormat's SHORT, MEDIUM, LONG, FULL, or DEFAULT.
         */
        public JavaUtilPrinter(int timeStyle) {
            this.timeFormat = DateFormat.getTimeInstance(timeStyle);
        }

        @Override
        public String print(TimeInstant timeInstant) {
            if (timeInstant == null) return "";

            Calendar cal = new GregorianCalendar(0, 0, 0, timeInstant.hourOfDay, timeInstant.minuteOfHour, timeInstant.secondOfMinute);
            return timeFormat.format(cal.getTime());
        }
    }
}
