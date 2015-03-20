package com.marvinlabs.widget.floatinglabel;

/**
 * Created by izaakalpert on 2015-03-19.
 */
public enum FloatTrigger {
    FOCUS, VALUE_PRESENT;

    public static FloatTrigger fromString(String string){
        if(string.equals("focusChange")) {
            return FOCUS;
        }
        else {
            return VALUE_PRESENT;
        }
    }

    public static FloatTrigger fromInt(int anInt) {
        if(anInt == 0) {
            return FOCUS;
        }
        else {
            return VALUE_PRESENT;
        }
    }
}
