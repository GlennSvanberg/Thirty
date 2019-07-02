package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Die implements Parcelable {

    private int mValue;
    private int mId;
    private boolean mIsActive;
    private boolean mCounted;

    public Die(int dieId) {
        mId = dieId;
        mIsActive = true;
    }

    protected Die(Parcel in) {
        mValue = in.readInt();
        mId = in.readInt();
        mIsActive = in.readByte() != 0;
        mCounted = in.readByte() != 0;
    }
    public static final Creator<Die> CREATOR = new Creator<Die>() {
        @Override
        public Die createFromParcel(Parcel in) {
            Die die = new Die(in);
            die.setActive(in.readByte() != 0);
            return die;
        }

        @Override
        public Die[] newArray(int size) {
            return new Die[size];
        }
    };


    public boolean isCounted() {
        return mCounted;
    }

    public void setCounted(boolean counted) {
        mCounted = counted;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }

    public int getId() {
        return mId;
    }

    public boolean isActive() {
        return mIsActive;
    }

    public void setActive(boolean active) {
        mIsActive = active;
    }

    public void roll() {
        Random rand = new Random();
        int i = rand.nextInt(6);
        mValue = i + 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mValue);
        parcel.writeByte((byte) (mIsActive ? 1 : 0));
    }

}
