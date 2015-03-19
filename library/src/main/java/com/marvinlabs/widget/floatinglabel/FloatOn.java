package com.marvinlabs.widget.floatinglabel;

/**
 * Created by izaakalpert on 2015-03-19.
 */
public enum FloatOn {
    FLOAT_ON_VALUE_PRESENT, FLOAT_ON_FOCUS;

    public static FloatOn fromString(String string){
        if(string.equals("focus")) {
            return FLOAT_ON_FOCUS;
        }
        else {
            return FLOAT_ON_VALUE_PRESENT;
        }
    }

    public static FloatOn fromInt(int anInt) {
        if(anInt == 1) {
            return FLOAT_ON_FOCUS;
        }
        else {
            return FLOAT_ON_VALUE_PRESENT;
        }
    }
}
