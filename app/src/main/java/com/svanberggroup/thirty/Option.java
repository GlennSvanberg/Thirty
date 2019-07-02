package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;

public class Option implements Parcelable{

    private String mName;
    private int mSum;
    private int mValue;
    private boolean mAvailable;

    public Option(String name, int value) {
        mName = name;
        mValue = value;
        mAvailable = true;
        mSum = 0;
    }

    protected Option(Parcel in) {
        mName = in.readString();
        mSum = in.readInt();
        mValue = in.readInt();
        mAvailable = in.readByte() != 0;
    }

    public static final Creator<Option> CREATOR = new Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel in) {
            return new Option(in);
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };

    public int getValue() {
        return mValue;
    }
    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean avaliable) {
        mAvailable = avaliable;
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
        parcel.writeByte((byte) (mAvailable ? 1 : 0));
    }
}
