package com.marvinlabs.widget.floatinglabel.instantpicker;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 01/09/2014.
 */
public interface DateInstant extends Instant {

    int getYear();

    int getMonthOfYear();

    int getDayOfMonth();

    void setYear(int year);

    void setMonthOfYear(int monthOfYear);

    void setDayOfMonth(int dayOfMonth);
}
