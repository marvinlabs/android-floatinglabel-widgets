package com.marvinlabs.widget.floatinglabel.picker;

import java.util.Collection;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 29/08/2014.
 */
public interface ItemPrinter<ItemT> {

    public String itemToString(ItemT item);

    public String itemsToString(Collection<ItemT> items);


    public static class ToStringItemPrinter<ItemT> implements ItemPrinter<ItemT> {

        public String itemToString(ItemT item) {
            return item==null ? "" : item.toString();
        }

        public String itemsToString(Collection<ItemT> items) {
            if (items.size() == 0) return "";

            StringBuilder sb = new StringBuilder();
            boolean prependSeparator = false;
            for (ItemT item : items) {
                if (prependSeparator) {
                    sb.append(", ");
                } else {
                    prependSeparator = true;
                }

                sb.append(itemToString(item));
            }

            return sb.toString();
        }
    }
}
