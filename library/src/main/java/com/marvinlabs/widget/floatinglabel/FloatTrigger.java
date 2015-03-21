package com.marvinlabs.widget.floatinglabel;

/**
 * Created by izaakalpert on 2015-03-19.
 */
public enum FloatTrigger {
    FOCUS_CHANGE, SET_VALUE;

    public static FloatTrigger fromString(String str) {
        if ("focusChange".equals(str)) {
            return FOCUS_CHANGE;
        } else if ("setValue".equals(str)) {
            return SET_VALUE;
        } else {
            throw new IllegalArgumentException("Invalid int value passed to FloatTrigger.fromString");
        }
    }

    public static FloatTrigger fromInt(int anInt) {
        switch (anInt) {
            case 0:
                return FOCUS_CHANGE;
            case 1:
                return SET_VALUE;
            default:
                throw new IllegalArgumentException("Invalid int value passed to FloatTrigger.fromInt");
        }
    }

    public boolean isFocusChange() {
        return this == FloatTrigger.FOCUS_CHANGE;
    }

    public boolean isSetValue() {
        return this == FloatTrigger.SET_VALUE;
    }
}
