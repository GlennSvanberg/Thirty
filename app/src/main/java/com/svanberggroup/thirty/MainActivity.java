package com.svanberggroup.thirty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageButton mDie0, mDie1, mDie2, mDie3, mDie4, mDie5;
    private Button mRollButton;
    private TextView mRoundTextView;
    private Spinner mOptionsSpinner;

    private Die[] mDice;
    private Option[] mOptions;
    private int mOptionNr;
    private int mRolls;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDie0 = findViewById(R.id.die0);
        mDie1 = findViewById(R.id.die1);
        mDie2 = findViewById(R.id.die2);
        mDie3 = findViewById(R.id.die3);
        mDie4 = findViewById(R.id.die4);
        mDie5 = findViewById(R.id.die5);

        mDice = new Die[] {
                new Die(mDie0.getId()),
                new Die(mDie1.getId()),
                new Die(mDie2.getId()),
                new Die(mDie3.getId()),
                new Die(mDie4.getId()),
                new Die(mDie5.getId())
        };

        mOptions = new Option[] {
                new Option("LOW", 0),
                new Option("4" ,4),
                new Option("5", 5),
                new Option("6", 6),
                new Option("7", 7),
                new Option("8", 8),
                new Option("9", 9),
                new Option("10", 10),
                new Option("11", 11),
                new Option("12", 12)
        };


        mRollButton = findViewById(R.id.rollButton);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Roll dies
                mRolls++;
                if(mRolls <= 3) {
                    for(Die die : mDice) {
                        if(die.isActive()) {
                            die.roll();
                            setDieImage(die);
                        }
                    }
                    setRoundTextViewText();

                    if(mRolls == 3) {
                        // Move String to res
                        mRollButton.setText("CHOOSE");
                    }
                } else {
                    Log.d(TAG, "time to make a choice");
                    // Deactivate button if no valid choice
                    calculatePoints();
                    newRound();
                    // Calculate points

                }

            }
        });
        mRoundTextView = findViewById(R.id.roundTextView);


        mOptionsSpinner = findViewById(R.id.optionsSpinner);
        ArrayAdapter<Option> dataAdapter = new ArrayAdapter<Option>(this, android.R.layout.simple_spinner_item, mOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOptionsSpinner.setAdapter(dataAdapter);

        mOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mOptionNr = i;
                Log.d(TAG, "Option: " + String.valueOf(mOptions[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        newRound();

    }

    public void dieClick(View view) {
        // Find the Die that has been clicked
        for (Die die : mDice) {
            if(die.getDieId() == view.getId()) {

                //Is the die active?
                if(die.isActive()) {
                    die.setActive(false);
                } else {
                    die.setActive(true);
                }
                setDieImage(die);
            }
        }

    }

    private void calculatePoints() {
        Log.d(TAG, "Calculating points");
        ArrayList<Integer> values = new ArrayList<Integer>();
        int sum = 0;
        int targetValue = mOptions[mOptionNr].getSum();

        //Get values to list
        int[] A = new int[6];
        for(int i = 0;  mDice.length > i; i++) {
            mDice[i].setCounted(false);
            Log.d(TAG, "i: " + i + " die: " + mDice[i].getDieValue());
            values.add(mDice[i].getDieValue());
            A[i] = mDice[i].getDieValue();

        }

        // Calculate LOW
        if(mOptionNr == 0) {
            for(int i : values) {
                if(i <= 3) {
                    sum += i;
                }
            }
            Log.d(TAG, "Low Sum: " + sum);

        } else {

            //Check one die
            for (int i = 0; i < mDice.length; i++) {
                if (mDice[i].getDieValue() == targetValue) {
                    Log.d(TAG, "Match 1 i: " + mDice[i].getDieValue());
                    mDice[i].setCounted(true);
                    sum = sum + mDice[i].getDieValue();
                }
            }
            // Check 2 dice
            for (int i = 0; i < mDice.length; i++) {
                for (int j = i + 1; j < mDice.length; j++) {
                    //check counted
                    if (!mDice[i].isCounted() && !mDice[j].isCounted()) {
                        //compare
                        if (mDice[i].getDieValue() + mDice[j].getDieValue() == targetValue) {
                            Log.d(TAG, "Match 2 i: " + mDice[i].getDieValue() + " j: " + mDice[j].getDieValue());
                            mDice[i].setCounted(true);
                            mDice[j].setCounted(true);
                            sum = sum + mDice[i].getDieValue() + mDice[j].getDieValue();
                        }
                    }
                }
            }

            // Check 3 dice
            for (int i = 0; i < mDice.length; i++) {
                for (int j = i + 1; j < mDice.length; j++) {
                    for (int k = j + 1; k < mDice.length; k++) {

                        // check counted
                        if (!mDice[i].isCounted() && !mDice[j].isCounted() && !mDice[k].isCounted()) {
                            // Compare
                            if (mDice[i].getDieValue() + mDice[j].getDieValue() + mDice[k].getDieValue() == targetValue) {
                                Log.d(TAG, "Match 3 i: " + mDice[i].getDieValue() + " j: " + mDice[j].getDieValue() + " k: " + mDice[k].getDieValue());
                                mDice[i].setCounted(true);
                                mDice[j].setCounted(true);
                                mDice[k].setCounted(true);
                                sum = sum + mDice[i].getDieValue() + mDice[j].getDieValue() + mDice[k].getDieValue();
                            }
                        }
                    }
                }
            }
            // Check 4 dice
            for (int i = 0; i < mDice.length; i++) {
                for (int j = i + 1; j < mDice.length; j++) {
                    for (int k = j + 1; k < mDice.length; k++) {
                        for (int l = k + 1; l < mDice.length; l++) {
                            // check counted
                            if (!mDice[i].isCounted() && !mDice[j].isCounted() && !mDice[k].isCounted() && !mDice[l].isCounted()) {
                                //Compare
                                if (mDice[i].getDieValue() + mDice[j].getDieValue() + mDice[k].getDieValue() + mDice[l].getDieValue() == targetValue) {
                                    Log.d(TAG, "Match 4 i: " + mDice[i].getDieValue() + " j: " + mDice[j].getDieValue() + " k: " + mDice[k].getDieValue() + " l: " + mDice[l].getDieValue());
                                    mDice[i].setCounted(true);
                                    mDice[j].setCounted(true);
                                    mDice[k].setCounted(true);
                                    mDice[l].setCounted(true);
                                    sum = sum + mDice[i].getDieValue() + mDice[j].getDieValue() + mDice[k].getDieValue() + mDice[l].getDieValue();
                                }
                            }
                        }
                    }
                }
            }
            // Check 5 dice

            for (int i = 0; i < mDice.length; i++) {
                for (int j = i + 1; j < mDice.length; j++) {
                    for (int k = j + 1; k < mDice.length; k++) {
                        for (int l = k + 1; l < mDice.length; l++) {
                            for (int m = l + 1; m < mDice.length; m++) {
                                // Check counted
                                if (!mDice[i].isCounted() && !mDice[j].isCounted() && !mDice[k].isCounted() && !mDice[l].isCounted() && !mDice[m].isCounted()) {
                                    // Compare
                                    if (mDice[i].getDieValue() + mDice[j].getDieValue() + mDice[k].getDieValue() + mDice[l].getDieValue() + mDice[m].getDieValue() == targetValue) {
                                        Log.d(TAG, "Match 5 i: " + mDice[i].getDieValue() + " j: " + mDice[j].getDieValue() + " k: " + mDice[k].getDieValue() + " l: " + mDice[l].getDieValue() + " m: " + +mDice[m].getDieValue());
                                        mDice[i].setCounted(true);
                                        mDice[j].setCounted(true);
                                        mDice[k].setCounted(true);
                                        mDice[l].setCounted(true);
                                        mDice[m].setCounted(true);
                                        sum = sum + mDice[i].getDieValue() + mDice[j].getDieValue() + mDice[k].getDieValue() + mDice[l].getDieValue() + mDice[m].getDieValue();
                                    }
                                }

                            }
                        }
                    }
                }
            }
            // check all dice
            int t = 0;
            for (int i = 0; i < mDice.length; i++) {
                if (!mDice[i].isCounted()) {
                    t = t + mDice[i].getDieValue();
                }
            }
            if (t == targetValue) {
                sum = t;
                Log.d(TAG, "all values: " + sum);
            }
        }

            Log.d(TAG, "Sum: " + sum);
            Toast toast = Toast.makeText(this, "Option " + targetValue + " gave you " + sum + " points! ", Toast.LENGTH_SHORT);
            toast.show();
        }







    private void newRound() {
        for (Die die : mDice) {
            mRolls = 1;
            die.roll();
            die.setActive(true);
            setDieImage(die);
        }
        mRollButton.setText("ROLL");
        setRoundTextViewText();
    }
    private void setRoundTextViewText() {
        mRoundTextView.setText("Rolls: " + mRolls + " / 3");
    }
    private void setDieImage(Die die) {
        ImageButton button = findViewById(die.getDieId());
        if(die.isActive()) {
            //set white image
            switch(die.getDieValue()) {
                case 1:
                    button.setImageResource(R.drawable.white1);
                    break;
                case 2:
                    button.setImageResource(R.drawable.white2);
                    break;
                case 3:
                    button.setImageResource(R.drawable.white3);
                    break;
                case 4:
                    button.setImageResource(R.drawable.white4);
                    break;
                case 5:
                    button.setImageResource(R.drawable.white5);
                    break;
                case 6:
                    button.setImageResource(R.drawable.white6);
                    break;
            }
        } else {
            //set grey image
            switch(die.getDieValue()) {
                case 1:
                    button.setImageResource(R.drawable.grey1);
                    break;
                case 2:
                    button.setImageResource(R.drawable.grey2);
                    break;
                case 3:
                    button.setImageResource(R.drawable.grey3);
                    break;
                case 4:
                    button.setImageResource(R.drawable.grey4);
                    break;
                case 5:
                    button.setImageResource(R.drawable.grey5);
                    break;
                case 6:
                    button.setImageResource(R.drawable.grey6);
                    break;
            }
        }
    }


}
