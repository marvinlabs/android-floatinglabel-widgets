package com.marvinlabs.widget.floatinglabel.itemchooser;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface ItemPrinter<ItemT> {

    public String print(ItemT item);


    public static class ToStringItemPrinter<ItemT> implements ItemPrinter<ItemT> {
        public String print(ItemT item) {
            return item==null ? "" : item.toString();
        }
    }
}
