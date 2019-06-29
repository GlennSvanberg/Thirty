package com.svanberggroup.thirty;

public class Option {

    private String mName;
    private int mPoints;

    public Option(String name) {
        mName = name;
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

    @Override
    public String toString() {
        return mName;
    }


}
