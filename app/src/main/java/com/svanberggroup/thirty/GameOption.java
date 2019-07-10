package com.svanberggroup.thirty;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class GameOption{

    private static final String TAG = "GameOption";
    private String mName;
    private int mSum;
    private int mValue;
    private boolean mIsAvailable;

    /**
     * Constructor of GameOption takes name and value as parameters
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
     * Calulate the sum of the dices if the choosen option is low
     * @param mDices
     * @return
     */
    private int calculateLow(Dice[] mDices) {
        int sum = 0;
        for(int i =0; i < mDices.length; i++) {
            if(mDices[i].getValue() <= 3) {
                 sum += mDices[i].getValue();
            }
        }
        return sum;
    }

    /**
     * Caluclate sum of the dices that reaches the selected value on one single dice
     * @param mDices
     * @return
     */
    private int calculateOneDice(Dice[]mDices) {
        int sum = 0;
        for (int i = 0; i < mDices.length; i++) {
            if (mDices[i].getValue() == mValue) {
                mDices[i].setCounted(true);
                sum = sum + mDices[i].getValue();
            }
        }
        return sum;
    }

    /**
     * Caluclate sum of the dices that reaches the selected value on one two dices
     * @param mDices
     * @return
     */
    private int calculateTwoDices(Dice[]mDices) {
        int sum = 0;
        for (int i = 0; i < mDices.length; i++) {
            for (int j = i + 1; j < mDices.length; j++) {
                //check counted
                if (!mDices[i].isCounted() && !mDices[j].isCounted()) {
                    //compare
                    if (mDices[i].getValue() + mDices[j].getValue() == mValue) {
                        mDices[i].setCounted(true);
                        mDices[j].setCounted(true);
                        sum = sum + mDices[i].getValue() + mDices[j].getValue();
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Caluclate sum of the dices that reaches the selected value on three dices
     * @param mDices
     * @return
     */
    private int calculateThreeDices(Dice[]mDices) {
        int sum = 0;
        for (int i = 0; i < mDices.length; i++) {
            for (int j = i + 1; j < mDices.length; j++) {
                for (int k = j + 1; k < mDices.length; k++) {
                    // check counted
                    if (!mDices[i].isCounted() && !mDices[j].isCounted() && !mDices[k].isCounted()) {
                        // Compare
                        if (mDices[i].getValue() + mDices[j].getValue() + mDices[k].getValue() == mValue) {
                            mDices[i].setCounted(true);
                            mDices[j].setCounted(true);
                            mDices[k].setCounted(true);
                            sum = sum + mDices[i].getValue() + mDices[j].getValue() + mDices[k].getValue();
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Caluclate sum of the dices that reaches the selected value on four dices
     * @param mDices
     * @return
     */
    private int calculateFourDices(Dice[]mDices) {
        int sum = 0;
        for (int i = 0; i < mDices.length; i++) {
            for (int j = i + 1; j < mDices.length; j++) {
                for (int k = j + 1; k < mDices.length; k++) {
                    for (int l = k + 1; l < mDices.length; l++) {
                        // check counted
                        if (!mDices[i].isCounted() && !mDices[j].isCounted() && !mDices[k].isCounted() && !mDices[l].isCounted()) {
                            //Compare
                            if (mDices[i].getValue() + mDices[j].getValue() + mDices[k].getValue() + mDices[l].getValue() == mValue) {
                                mDices[i].setCounted(true);
                                mDices[j].setCounted(true);
                                mDices[k].setCounted(true);
                                mDices[l].setCounted(true);
                                sum = sum + mDices[i].getValue() + mDices[j].getValue() + mDices[k].getValue() + mDices[l].getValue();
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Caluclate sum of the dices that reaches the selected value on five dices
     * @param mDices
     * @return
     */
    private int calculateFiveDices(Dice[]mDices) {
        int sum = 0;
        for (int i = 0; i < mDices.length; i++) {
            for (int j = i + 1; j < mDices.length; j++) {
                for (int k = j + 1; k < mDices.length; k++) {
                    for (int l = k + 1; l < mDices.length; l++) {
                        for (int m = l + 1; m < mDices.length; m++) {
                            // Check counted
                            if (!mDices[i].isCounted() && !mDices[j].isCounted() && !mDices[k].isCounted() && !mDices[l].isCounted() && !mDices[m].isCounted()) {
                                // Compare
                                if (mDices[i].getValue() + mDices[j].getValue() + mDices[k].getValue() + mDices[l].getValue() + mDices[m].getValue() == mValue) {
                                    mDices[i].setCounted(true);
                                    mDices[j].setCounted(true);
                                    mDices[k].setCounted(true);
                                    mDices[l].setCounted(true);
                                    mDices[m].setCounted(true);
                                    sum = sum + mDices[i].getValue() + mDices[j].getValue() + mDices[k].getValue() + mDices[l].getValue() + mDices[m].getValue();
                                }
                            }

                        }
                    }
                }
            }
        }
        return sum;
    }

    /**
     * Caluclate sum of the dices that reaches the selected value on six dices
     * @param mDices
     * @return
     */
    private int calculateSixDices(Dice[]mDices) {
        int sum = 0;
        int t = 0;
        for (int i = 0; i < mDices.length; i++) {
            if (!mDices[i].isCounted()) {
                t = t + mDices[i].getValue();
            }
        }
        if (t == mValue) {
            sum = t;
        }
        return sum;
    }

    /**
     * returns the number of dices that has not been counted yet
     * @param mDices
     * @return
     */
    private int avaliableDices(Dice[] mDices) {
        int count = 0;
        for (Dice dice : mDices) {
            if(!dice.isCounted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Caluclates the sum of the dices passed in on the value that is set for this GameOption
     * @param mDices
     */
    public void calculatePoints(Dice[] mDices) {

        int sum = 0;
        for(int i = 0;  mDices.length > i; i++) {
            mDices[i].setCounted(false);
        }

        if(mValue == 0) {
            sum = calculateLow(mDices);
        } else {
            sum = calculateOneDice(mDices);

            if(avaliableDices(mDices) >= 2) {
                sum += calculateTwoDices(mDices);
            }
            if(avaliableDices(mDices) >= 3) {
                sum += calculateThreeDices(mDices);
            }
            if(avaliableDices(mDices) >= 4) {
                sum += calculateFourDices(mDices);
            }
            if(avaliableDices(mDices) >= 5) {
                sum += calculateFiveDices(mDices);
            }
            if(avaliableDices(mDices) >= 6) {
                sum += calculateSixDices(mDices);
            }
        }
       mIsAvailable = false;
       mSum = sum;

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
