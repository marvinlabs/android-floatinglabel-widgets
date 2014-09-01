package com.marvinlabs.widget.floatinglabel.instantpicker;

/**
 * Class that knows how to print an instant
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface DatePrinter<DateInstantT extends DateInstant> extends InstantPrinter<DateInstantT> {

    /**
     * Print a date
     *
     * @param dateInstant The date to print
     * @return
     */
    public String print(DateInstantT dateInstant);

}
