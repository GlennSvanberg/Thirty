package com.svanberggroup.thirty;

public class Option {

    private String mName;
    private int mPoints;
    private int mSum;
    private boolean mChoosen;
    private boolean mAvalible;

    public Option(String name, int sum) {
        mName = name;
        mSum = sum;
        mChoosen = false;
        mAvalible = true;
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

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int points) {
        this.mPoints = points;
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


}
