package com.example.dailywin.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zorana on 2/22/14.
 */
public class NailIt implements Parcelable {

    private int id;
    private String name;
    private String created;
    private String freq;
    private int importance;
    private int fArh;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<NailIt> CREATOR
            = new Parcelable.Creator<NailIt>() {
        public NailIt createFromParcel(Parcel in) {
            return new NailIt(in);
        }

        public NailIt[] newArray(int size) {
            return new NailIt[size];
        }
    };

    public NailIt () {
    }

    private NailIt(Parcel in) {

        id = in.readInt();
        name = in.readString();
        created = in.readString();
        freq = in.readString();
        importance = in.readInt();
        fArh = in.readInt();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public int getfArh() {
        return fArh;
    }

    public void setfArh(int fArh) {
        this.fArh = fArh;
    }
}
