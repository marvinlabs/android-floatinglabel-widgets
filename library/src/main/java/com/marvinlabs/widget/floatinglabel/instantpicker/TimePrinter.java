package com.marvinlabs.widget.floatinglabel.instantpicker;

/**
 * Class that knows how to print an instant
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface TimePrinter<TimeInstantT extends TimeInstant> extends InstantPrinter<TimeInstantT> {

    /**
     * Print a time
     *
     * @param timeInstant The time to print
     * @return
     */
    public String print(TimeInstantT timeInstant);

}
