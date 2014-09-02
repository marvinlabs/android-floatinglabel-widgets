package com.marvinlabs.widget.floatinglabel.instantpicker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents a date in the gregorian calendar, with month index starting at 0 (January) and ending
 * at 11 (December)
 * <p/>
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 01/09/2014.
 */
public class JavaDateInstant implements DateInstant {

    int year;
    int monthOfYear; // 0 based index unlike Calendar constants
    int dayOfMonth;

    // =============================================================================================
    // Lifecycle
    // ==

    public JavaDateInstant() {
        this(new GregorianCalendar());
    }

    public JavaDateInstant(Calendar cal) {
        this(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)-1, cal.get(Calendar.DAY_OF_MONTH));
    }

    public JavaDateInstant(int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
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
        dest.writeInt(this.year);
        dest.writeInt(this.monthOfYear);
        dest.writeInt(this.dayOfMonth);
    }

    private JavaDateInstant(Parcel in) {
        this.year = in.readInt();
        this.monthOfYear = in.readInt();
        this.dayOfMonth = in.readInt();
    }

    public static final Parcelable.Creator<JavaDateInstant> CREATOR = new Parcelable.Creator<JavaDateInstant>() {
        public JavaDateInstant createFromParcel(Parcel source) {
            return new JavaDateInstant(source);
        }

        public JavaDateInstant[] newArray(int size) {
            return new JavaDateInstant[size];
        }
    };

    // =============================================================================================
    // Other methods
    // ==

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int getMonthOfYear() {
        return monthOfYear;
    }

    @Override
    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    @Override
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    @Override
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
