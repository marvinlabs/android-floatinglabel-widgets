package com.marvinlabs.widget.floatinglabel.itempicker;

import java.util.Collection;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface ItemPrinter<ItemT> {

    public String print(ItemT item);

    public String printCollection(Collection<ItemT> items);


    public static class ToStringItemPrinter<ItemT> implements ItemPrinter<ItemT> {

        public String print(ItemT item) {
            return item==null ? "" : item.toString();
        }

        public String printCollection(Collection<ItemT> items) {
            if (items.size() == 0) return "";

            StringBuilder sb = new StringBuilder();
            boolean prependSeparator = false;
            for (ItemT item : items) {
                if (prependSeparator) {
                    sb.append(", ");
                } else {
                    prependSeparator = true;
                }

                sb.append(print(item));
            }

            return sb.toString();
        }
    }
}
