package com.svanberggroup.thirty;

import java.util.Random;

public class Die {

    private int mDieValue;
    private int mDieId;
    private boolean mIsActive;
    private boolean mCounted;

    public Die(int dieId) {
        mDieId = dieId;
        mIsActive = true;
    }
    public boolean isCounted() {
        return mCounted;
    }

    public void setCounted(boolean counted) {
        mCounted = counted;
    }

    public int getDieValue() {
        return mDieValue;
    }

    public void setDieValue(int dieValue) {
        mDieValue = dieValue;
    }

    public int getDieId() {
        return mDieId;
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
        mDieValue = i + 1;
    }

}
