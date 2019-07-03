package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;

public class GameOption implements Parcelable{

    private String mName;
    private int mSum;
    private int mValue;
    private boolean mIsAvailable;

    public GameOption(String name, int value) {
        mName = name;
        mValue = value;
        mIsAvailable = true;
        mSum = 0;
    }

    protected GameOption(Parcel in) {
        mName = in.readString();
        mSum = in.readInt();
        mValue = in.readInt();
        mIsAvailable = in.readByte() != 0;
    }

    public static final Creator<GameOption> CREATOR = new Creator<GameOption>() {
        @Override
        public GameOption createFromParcel(Parcel in) {
            return new GameOption(in);
        }

        @Override
        public GameOption[] newArray(int size) {
            return new GameOption[size];
        }
    };

    public int getValue() {
        return mValue;
    }

    public boolean isAvailable() {
        return mIsAvailable;
    }

    public void setAvailable(boolean available) {
        mIsAvailable = available;
    }

    public int getSum() {
        return mSum;
    }

    public void setSum(int sum) {
        mSum = sum;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mSum);
        parcel.writeString(mName);
        parcel.writeInt(mValue);
        parcel.writeByte((byte) (mIsAvailable ? 1 : 0));

    }


}
