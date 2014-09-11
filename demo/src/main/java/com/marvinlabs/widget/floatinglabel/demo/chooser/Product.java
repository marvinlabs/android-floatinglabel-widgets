package com.marvinlabs.widget.floatinglabel.demo.chooser;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Vincent Mimoun-Prat @ MarvinLabs, 11/09/2014.
 */
public class Product implements Parcelable {

    String title;
    int quantity;

    // =============================================================================================
    // Lifecycle
    // ==

    public Product(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
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
        dest.writeString(this.title);
        dest.writeInt(this.quantity);
    }

    public Product() {
    }

    private Product(Parcel in) {
        this.title = in.readString();
        this.quantity = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    // =============================================================================================
    // Other methods
    // ==

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format( "%1$s - %2$d", title, quantity);
    }
}
