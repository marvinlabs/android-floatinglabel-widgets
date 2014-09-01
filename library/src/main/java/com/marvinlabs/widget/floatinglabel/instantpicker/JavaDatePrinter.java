package com.marvinlabs.widget.floatinglabel.instantpicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * A default implementation using the java.util.DateFormat class with the default locale
 */
public class JavaDatePrinter<DateInstantT extends DateInstant> implements DatePrinter<DateInstantT> {

    final DateFormat dateFormat;

    /**
     * Constructor
     *
     * @param dateStyle one of DateFormat's SHORT, MEDIUM, LONG, FULL, or DEFAULT.
     */
    public JavaDatePrinter(int dateStyle) {
        this.dateFormat = DateFormat.getDateInstance(dateStyle);
    }

    @Override
    public String print(DateInstant dateInstant) {
        if (dateInstant == null) return "";

        Calendar cal = new GregorianCalendar(dateInstant.getYear(), dateInstant.getMonthOfYear() + 1, dateInstant.getDayOfMonth());
        return dateFormat.format(cal.getTime());
    }
}