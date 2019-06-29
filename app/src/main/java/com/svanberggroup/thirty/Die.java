package com.svanberggroup.thirty;

import java.util.Random;

public class Die {



    private int mDieValue;
    private int mDieId;

    public Die(int dieId) {
        mDieId = dieId;
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


    public void roll() {
        Random rand = new Random();
        int i = rand.nextInt(6);
        mDieValue = i + 1;
    }

}
