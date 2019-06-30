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
    private ArrayList<Option> mAvalibleOptions;
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
                    //
                    newRound();


                }

            }
        });
        mRoundTextView = findViewById(R.id.roundTextView);

        setAvalibleOptions();


        mOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mOptionNr = i;
                for (int j = 0; j < mOptions.length; j++) {
                    if(mAvalibleOptions.get(i).getName() == mOptions[j].getName()) {
                        mOptionNr = j;
                    }
                }

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
    private void newRound() {
        for (Die die : mDice) {
            mRolls = 1;
            die.roll();
            die.setActive(true);
            setDieImage(die);
            setAvalibleOptions();
        }
        mRollButton.setText("ROLL");
        setRoundTextViewText();
    }

    private void setAvalibleOptions(){
        mAvalibleOptions = new ArrayList<Option>();
        for(Option o : mOptions) {
            if(o.isAvalible()) {
                mAvalibleOptions.add(o);
            }
        }
        mOptionsSpinner = findViewById(R.id.optionsSpinner);
        ArrayAdapter<Option> dataAdapter = new ArrayAdapter<Option>(this, android.R.layout.simple_spinner_item, mAvalibleOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOptionsSpinner.setAdapter(dataAdapter);
    }

    private void setRoundTextViewText() {
        mRoundTextView.setText("Rolls: " + mRolls + " / 3");
    }

    private void calculatePoints() {
        int sum = 0;
        int targetValue = mOptions[mOptionNr].getSum();

        for(int i = 0;  mDice.length > i; i++) {
            // Reset dices count
            mDice[i].setCounted(false);
        }

        // Calculate LOW
        if(mOptionNr == 0) {
            for(int i =0; i < mDice.length; i++) {
                if(mDice[i].getValue() <= 3) {
                    sum += i;
                }
            }
        } else {

            //Check one die
            for (int i = 0; i < mDice.length; i++) {
                if (mDice[i].getValue() == targetValue) {
                    mDice[i].setCounted(true);
                    sum = sum + mDice[i].getValue();
                }
            }
            // Check 2 dice
            for (int i = 0; i < mDice.length; i++) {
                for (int j = i + 1; j < mDice.length; j++) {
                    //check counted
                    if (!mDice[i].isCounted() && !mDice[j].isCounted()) {
                        //compare
                        if (mDice[i].getValue() + mDice[j].getValue() == targetValue) {
                            mDice[i].setCounted(true);
                            mDice[j].setCounted(true);
                            sum = sum + mDice[i].getValue() + mDice[j].getValue();
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
                            if (mDice[i].getValue() + mDice[j].getValue() + mDice[k].getValue() == targetValue) {
                                mDice[i].setCounted(true);
                                mDice[j].setCounted(true);
                                mDice[k].setCounted(true);
                                sum = sum + mDice[i].getValue() + mDice[j].getValue() + mDice[k].getValue();
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
                                if (mDice[i].getValue() + mDice[j].getValue() + mDice[k].getValue() + mDice[l].getValue() == targetValue) {
                                    mDice[i].setCounted(true);
                                    mDice[j].setCounted(true);
                                    mDice[k].setCounted(true);
                                    mDice[l].setCounted(true);
                                    sum = sum + mDice[i].getValue() + mDice[j].getValue() + mDice[k].getValue() + mDice[l].getValue();
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
                                    if (mDice[i].getValue() + mDice[j].getValue() + mDice[k].getValue() + mDice[l].getValue() + mDice[m].getValue() == targetValue) {
                                        mDice[i].setCounted(true);
                                        mDice[j].setCounted(true);
                                        mDice[k].setCounted(true);
                                        mDice[l].setCounted(true);
                                        mDice[m].setCounted(true);
                                        sum = sum + mDice[i].getValue() + mDice[j].getValue() + mDice[k].getValue() + mDice[l].getValue() + mDice[m].getValue();
                                    }
                                }

                            }
                        }
                    }
                }
            }
            // Check 6 dice
            int t = 0;
            for (int i = 0; i < mDice.length; i++) {
                if (!mDice[i].isCounted()) {
                    t = t + mDice[i].getValue();
                }
            }
            if (t == targetValue) {
                sum = t;
                Log.d(TAG, "all values: " + sum);
            }
        }

            // Set option not avalible
            mOptions[mOptionNr].setAvalible(false);

            Log.d(TAG, "Sum: " + sum);
            // Display result
            Toast toast = Toast.makeText(this, "Option " + targetValue + " gave you " + sum + " points! ", Toast.LENGTH_SHORT);
            toast.show();

            // Store sum in option
            mOptions[mOptionNr].setSum(sum);
        }

    private void setDieImage(Die die) {
        ImageButton button = findViewById(die.getDieId());
        if(die.isActive()) {
            //set white image
            switch(die.getValue()) {
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
            switch(die.getValue()) {
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
