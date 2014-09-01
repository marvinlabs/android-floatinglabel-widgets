package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.os.Parcel;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents a time always with 24 hours
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 01/09/2014.
 */
public class JavaTimeInstant implements TimeInstant {

    int hourOfDay; // Always 24 hours
    int minuteOfHour;
    int secondOfMinute;

    // =============================================================================================
    // Lifecycle
    // ==

    public JavaTimeInstant() {
        this(new GregorianCalendar());
    }

    public JavaTimeInstant(Calendar cal) {
        this(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }

    public JavaTimeInstant(int hourOfDay, int minuteOfHour, int secondOfMinute) {
        this.hourOfDay = hourOfDay;
        this.minuteOfHour = minuteOfHour;
        this.secondOfMinute = secondOfMinute;
    }

    // =============================================================================================
    // Parcelable implementation
    // ==

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.hourOfDay);
        dest.writeInt(this.minuteOfHour);
        dest.writeInt(this.secondOfMinute);
    }

    private JavaTimeInstant(Parcel in) {
        this.hourOfDay = in.readInt();
        this.minuteOfHour = in.readInt();
        this.secondOfMinute = in.readInt();
    }

    public static final Creator<TimeInstant> CREATOR = new Creator<TimeInstant>() {
        public TimeInstant createFromParcel(Parcel source) {
            return new JavaTimeInstant(source);
        }

        public TimeInstant[] newArray(int size) {
            return new TimeInstant[size];
        }
    };

    // =============================================================================================
    // Other methods
    // ==

    @Override
    public int getHourOfDay() {
        return hourOfDay;
    }

    @Override
    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    @Override
    public int getMinuteOfHour() {
        return minuteOfHour;
    }

    @Override
    public void setMinuteOfHour(int minuteOfHour) {
        this.minuteOfHour = minuteOfHour;
    }

    @Override
    public int getSecondOfMinute() {
        return secondOfMinute;
    }

    @Override
    public void setSecondOfMinute(int secondOfMinute) {
        this.secondOfMinute = secondOfMinute;
    }
}
