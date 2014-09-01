package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.os.Parcel;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents a time always with 24 hours
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 01/09/2014.
 */
public class TimeInstant implements Instant {

    int hourOfDay; // Always 24 hours
    int minuteOfHour;
    int secondOfMinute;

    // =============================================================================================
    // Factory methods
    // ==

    public static TimeInstant fromCalendar(Calendar cal) {
        TimeInstant i = new TimeInstant();
        i.hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        i.minuteOfHour = cal.get(Calendar.MINUTE);
        i.secondOfMinute = cal.get(Calendar.SECOND);
        return i;
    }

    // =============================================================================================
    // Lifecycle
    // ==

    public TimeInstant(int hourOfDay, int minuteOfHour, int secondOfMinute) {
        this.hourOfDay = hourOfDay;
        this.minuteOfHour = minuteOfHour;
        this.secondOfMinute = secondOfMinute;
    }

    public TimeInstant() {
        Calendar today = new GregorianCalendar();
        this.hourOfDay = today.get(Calendar.HOUR_OF_DAY);
        this.minuteOfHour = today.get(Calendar.MINUTE);
        this.secondOfMinute = today.get(Calendar.SECOND);
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

    private TimeInstant(Parcel in) {
        this.hourOfDay = in.readInt();
        this.minuteOfHour = in.readInt();
        this.secondOfMinute = in.readInt();
    }

    public static final Creator<TimeInstant> CREATOR = new Creator<TimeInstant>() {
        public TimeInstant createFromParcel(Parcel source) {
            return new TimeInstant(source);
        }

        public TimeInstant[] newArray(int size) {
            return new TimeInstant[size];
        }
    };

    // =============================================================================================
    // Other methods
    // ==

    public int getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public int getMinuteOfHour() {
        return minuteOfHour;
    }

    public void setMinuteOfHour(int minuteOfHour) {
        this.minuteOfHour = minuteOfHour;
    }

    public int getSecondOfMinute() {
        return secondOfMinute;
    }

    public void setSecondOfMinute(int secondOfMinute) {
        this.secondOfMinute = secondOfMinute;
    }
}
