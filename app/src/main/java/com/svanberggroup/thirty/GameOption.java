package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;

public class GameOption{

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



}
