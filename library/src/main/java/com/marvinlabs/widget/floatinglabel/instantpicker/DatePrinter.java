package com.marvinlabs.widget.floatinglabel.instantpicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class that knows how to print an instant
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface DatePrinter extends InstantPrinter<DateInstant> {

    /**
     * Print a date
     *
     * @param dateInstant The date to print
     * @return
     */
    public String print(DateInstant dateInstant);

    /**
     * A default implementation using the java.util.DateFormat class with the default locale
     */
    public static class JavaUtilPrinter implements DatePrinter {

        final DateFormat dateFormat;

        /**
         * Constructor
         *
         * @param dateStyle one of DateFormat's SHORT, MEDIUM, LONG, FULL, or DEFAULT.
         */
        public JavaUtilPrinter(int dateStyle) {
            this.dateFormat = DateFormat.getDateInstance(dateStyle);
        }

        @Override
        public String print(DateInstant dateInstant) {
            if (dateInstant == null) return "";

            Calendar cal = new GregorianCalendar(dateInstant.year, dateInstant.monthOfYear + 1, dateInstant.dayOfMonth);
            return dateFormat.format(cal.getTime());
        }
    }
}
