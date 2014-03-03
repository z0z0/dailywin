package com.example.dailywin.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uros on 3.3.14..
 */
public class BadgeType implements Parcelable {

    private int id;
    private String type;

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<BadgeType> CREATOR
            = new Parcelable.Creator<BadgeType>() {
        public BadgeType createFromParcel(Parcel in) {
            return new BadgeType(in);
        }

        public BadgeType[] newArray(int size) {
            return new BadgeType[size];
        }
    };

    public BadgeType(){

    }

    private BadgeType(Parcel in) {
        id = in.readInt();
        type = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
