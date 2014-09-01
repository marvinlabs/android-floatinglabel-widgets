package com.marvinlabs.widget.floatinglabel.instantpicker;

/**
 * Class that knows how to print an instant
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface InstantPrinter<IntantT extends Instant> {

    /**
     * Print an instant
     *
     * @param instant The instant to print
     * @return
     */
    public String print(IntantT instant);

}
