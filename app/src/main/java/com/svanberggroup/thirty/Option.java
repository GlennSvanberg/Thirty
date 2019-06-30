package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;

public class Option implements Parcelable {

    private String mName;
    private int mSum;
    private int mValue;
    private boolean mChoosen;
    private boolean mAvalible;

    public Option(String name, int value) {
        mName = name;
        mValue = value;
        mChoosen = false;
        mAvalible = true;
        mSum = 0;
    }

    protected Option(Parcel in) {
        mName = in.readString();
        mSum = in.readInt();
        mChoosen = in.readByte() != 0;
        mAvalible = in.readByte() != 0;
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
    public boolean isAvalible() {
        return mAvalible;
    }

    public void setAvalible(boolean avalible) {
        mAvalible = avalible;
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

    public void setChoosen(boolean choosen) {
        this.mChoosen = choosen;
    }

    public boolean choosen() {
        return mChoosen;
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
        parcel.writeString(mName);
        parcel.writeInt(mSum);
        parcel.writeByte((byte) (mChoosen ? 1 : 0));
        parcel.writeByte((byte) (mAvalible ? 1 : 0));

    }
}
