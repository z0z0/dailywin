package com.example.dailywin.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zorana on 3/2/14.
 */
public class Event implements Parcelable {
    
    
    private int id;
    private int nailitId;
    private String created;
    
    

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Event () {
    }

    private Event(Parcel in) {
        id = in.readInt();
        nailitId = in.readInt();
        created = in.readString();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNailitId() {
        return nailitId;
    }

    public void setNailitId(int nailitId) {
        this.nailitId = nailitId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
