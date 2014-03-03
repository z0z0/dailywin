package com.example.dailywin.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Uros on 3.3.14..
 */
public class Badge implements Parcelable{

    private int id;
    private int typeId;
    private String name;
    private int trigger;
    private String iconUrl;
    private String columnText;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static final Parcelable.Creator<Badge> CREATOR
            = new Parcelable.Creator<Badge>() {
        public Badge createFromParcel(Parcel in) {
            return new Badge(in);
        }

        public Badge[] newArray(int size) {
            return new Badge[size];
        }
    };

    public Badge(){

    }

    private Badge(Parcel in){
        id = in.readInt();
        typeId = in.readInt();
        name = in.readString();
        trigger = in.readInt();
        iconUrl = in.readString();
        columnText = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrigger() {
        return trigger;
    }

    public void setTrigger(int trigger) {
        this.trigger = trigger;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getColumnText() {
        return columnText;
    }

    public void setColumnText(String columnText) {
        this.columnText = columnText;
    }
}
