package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;

public class GameOption{

    private String mName;
    private int mSum;
    private int mValue;
    private boolean mIsAvailable;

    /**
     * Constructor of GAmeOption takes name and value as parameters
     * Initializes IsAvalible to true and sum to 0
     * @param name
     * @param value
     */
    public GameOption(String name, int value) {
        mName = name;
        mValue = value;
        mIsAvailable = true;
        mSum = 0;
    }

    /**
     * Returns the Target value for this gameoption
     *      * @return
     */
    public int getValue() {
        return mValue;
    }

    /**
     * Returns a boolean that shows if the Gameoption is available
     * @return
     */
    public boolean isAvailable() {
        return mIsAvailable;
    }

    /**
     * sets the state of the option as either avalible or not
     * @param available
     */

    public void setAvailable(boolean available) {
        mIsAvailable = available;
    }

    /**
     *  returns a integer with the score sum  for this option
     * @return
     */
    public int getSum() {
        return mSum;
    }

    /**
     * sets the score sum for this option,
     * @param sum
     */

    public void setSum(int sum) {
        mSum = sum;
    }

    /**
     * Returns a String representing the name of the gameoption
     * @return
     */

    public String getName() {
        return mName;
    }

    /**
     * Returns a string representation of the option, represented by the name
     * @return
     */

    @Override
    public String toString() {
        return mName;
    }



}
