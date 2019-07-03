package com.svanberggroup.thirty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

    private static final String TAG = "DiceActivity";
    private static final String KEY_DICE = "Dice";
    private static final String KEY_ROLLS = "Rolls";
    private static final String KEY_GAME_OPTION_SUM = "GameOptionSum";
    private static final String KEY_GAME_OPTION_AVAILABLE = "GameOptionAvailable";
    private static final String KEY_GAME_OPTION_NR = "Options";


    private ImageButton mDie0, mDie1, mDie2, mDie3, mDie4, mDie5;
    private Button mRollButton;
    private TextView mRoundTextView;
    private Spinner mGameOptionsSpinner;

    private Die[] mDice;
    private GameOption[] mGameOptions;
    private ArrayList<GameOption> mAvailableGameOptions;
    private int mGameOptionNr;
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

        mGameOptions = new GameOption[] {
                new GameOption("LOW", 0),
                new GameOption("4" ,4),
                new GameOption("5", 5),
                new GameOption("6", 6),
                new GameOption("7", 7),
                new GameOption("8", 8),
                new GameOption("9", 9),
                new GameOption("10", 10),
                new GameOption("11", 11),
                new GameOption("12", 12)
        };

        mRollButton = findViewById(R.id.rollButton);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Roll dies
                roll();
            }
        });

        mRoundTextView = findViewById(R.id.roundTextView);
        setRoundTextViewText();
        setAvailableGameOptions();


        mGameOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGameOptionNr = i;
                for (int j = 0; j < mGameOptions.length; j++) {
                    if(mAvailableGameOptions.get(i).getName() == mGameOptions[j].getName()) {
                        mGameOptionNr = j;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (savedInstanceState != null) {

            mRolls = savedInstanceState.getInt(KEY_ROLLS);
            Die[] savedDice = (Die[]) savedInstanceState.getParcelableArray(KEY_DICE);

            for(int i = 0; i < mDice.length; i++) {
                mDice[i].setValue(savedDice[i].getValue());
                mDice[i].setActive((savedDice[i].isActive()));
                setDieImage(mDice[i]);
            }

            int[] savedGameOptionsSum = savedInstanceState.getIntArray(KEY_GAME_OPTION_SUM);
            byte[] savedGameOptionsIsAvialible = savedInstanceState.getByteArray(KEY_GAME_OPTION_AVAILABLE);

            for(int i = 0; i < mGameOptions.length; i++) {
                mGameOptions[i].setSum(savedGameOptionsSum[i]);
                mGameOptions[i].setAvailable(savedGameOptionsIsAvialible[i] != 0);
            }


            mGameOptionNr = savedInstanceState.getInt(KEY_GAME_OPTION_NR);

            setRoundTextViewText();
            setAvailableGameOptions();


        } else {
            newRound();
            setRoundTextViewText();

        }
    }

    private void roll() {
        // Check if it is time to roll dice
        if(mRolls <= 1) {

            updateDice();
            mRolls++;
            setRoundTextViewText();

        } else if (mRolls == 2) {
            updateDice();
            mRolls++;
            setRoundTextViewText();
            mRollButton.setText(getString(R.string.ChooseButtonText));
        }else {
            calculatePoints();
            showScore();
            newRound();
        }
    }
    public void updateDice() {
        // Loop all dice
        for(Die die : mDice) {
            // Check if die is active
            if(die.isActive()) {
                die.roll();
                setDieImage(die);
            }
        }
    }
    public void dieClick(View view) {
        for (Die die : mDice) {
            if(die.getId() == view.getId()) {
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

        setAvailableGameOptions();
        if(mAvailableGameOptions.size() != 0) {
            for (Die die : mDice) {
                die.roll();
                die.setActive(true);
                setDieImage(die);
            }
            mRollButton.setText(getString(R.string.RollButtonText));
            mRolls = 1;
            setRoundTextViewText();
        } else {
            showResult();
        }
    }

    private void showResult() {
        int[] sums = new int[10];
        for (int i = 0; i < mGameOptions.length; i++) {
            sums[i] = mGameOptions[i].getSum();
        }
        Intent intent = ResultActivity.newIntent(MainActivity.this, sums);
        startActivity(intent);
    }

    private void setAvailableGameOptions(){
        mAvailableGameOptions = new ArrayList<GameOption>();
        for(GameOption o : mGameOptions) {
            if(o.isAvailable()) {
                mAvailableGameOptions.add(o);
            }
        }
        mGameOptionsSpinner = findViewById(R.id.gameOptionsSpinner);
        ArrayAdapter<GameOption> dataAdapter = new ArrayAdapter<GameOption>(this, android.R.layout.simple_spinner_item, mAvailableGameOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGameOptionsSpinner.setAdapter(dataAdapter);
    }

    private void setRoundTextViewText() {
        mRoundTextView.setText(getString(R.string.RollText) + mRolls + getString(R.string.EndRollText));
    }

    private void calculatePoints() {
        int sum = 0;
        int targetValue = mGameOptions[mGameOptionNr].getValue();

        for(int i = 0;  mDice.length > i; i++) {
            // Reset dices count
            mDice[i].setCounted(false);
        }

        // Calculate LOW
        if(mGameOptionNr == 0) {
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
            }
        }

            mGameOptions[mGameOptionNr].setAvailable(false);
            mGameOptions[mGameOptionNr].setSum(sum);
        }

        private void showScore() {
            Toast toast = Toast.makeText(this, getString(R.string.GameOptionText) + " " + mGameOptions[mGameOptionNr].getName()  + " " + getString(R.string.GaveYouText) + " " + mGameOptions[mGameOptionNr].getSum() +  " " + getString(R.string.PointsText), Toast.LENGTH_SHORT);
            toast.show();
        }

    private void setDieImage(Die die) {
        ImageButton button = findViewById(die.getId());
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
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        int[] gameOptionSum = new int[10];
        byte[] gameOptionAvailable = new byte[10];
        for(int i = 0; i < mGameOptions.length; i++) {
            gameOptionSum[i] = mGameOptions[i].getSum();
            gameOptionAvailable[i] = (byte) (mGameOptions[i].isAvailable() ? 1 : 0);
        }
        savedInstanceState.putIntArray(KEY_GAME_OPTION_SUM, gameOptionSum);
        savedInstanceState.putByteArray(KEY_GAME_OPTION_AVAILABLE, gameOptionAvailable);

        savedInstanceState.putParcelableArray(KEY_DICE, mDice);

        savedInstanceState.putInt(KEY_ROLLS, mRolls);
        savedInstanceState.putInt(KEY_GAME_OPTION_NR, mGameOptionNr);
    }

}
