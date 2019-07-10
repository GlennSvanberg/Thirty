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

    private static final String KEY_DICES = "Dices";
    private static final String KEY_ROLLS = "Rolls";
    private static final String KEY_GAME_OPTION_SUM = "GameOptionSum";
    private static final String KEY_GAME_OPTION_AVAILABLE = "GameOptionAvailable";
    private static final String KEY_GAME_OPTION_NR = "Options";

    private ImageButton mDice0, mDice1, mDice2, mDice3, mDice4, mDice5;
    private Button mRollButton;
    private TextView mRoundTextView;
    private Spinner mGameOptionsSpinner;

    private Dice[] mDices;
    private GameOption[] mGameOptions;
    private ArrayList<GameOption> mAvailableGameOptions;
    private int mGameOptionNr;
    private int mRolls;

    /**
     * Creates the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDice0 = findViewById(R.id.dice0);
        mDice1 = findViewById(R.id.dice1);
        mDice2 = findViewById(R.id.dice2);
        mDice3 = findViewById(R.id.dice3);
        mDice4 = findViewById(R.id.dice4);
        mDice5 = findViewById(R.id.dice5);

        mDices = new Dice[] {
                new Dice(mDice0.getId()),
                new Dice(mDice1.getId()),
                new Dice(mDice2.getId()),
                new Dice(mDice3.getId()),
                new Dice(mDice4.getId()),
                new Dice(mDice5.getId())
        };

        mGameOptions = new GameOption[] {
                new GameOption(getString(R.string.Low), 0),
                new GameOption(getString(R.string.Four) ,4),
                new GameOption(getString(R.string.Five), 5),
                new GameOption(getString(R.string.Six), 6),
                new GameOption(getString(R.string.Seven), 7),
                new GameOption(getString(R.string.Eight), 8),
                new GameOption(getString(R.string.Nine), 9),
                new GameOption(getString(R.string.Ten), 10),
                new GameOption(getString(R.string.Eleven), 11),
                new GameOption(getString(R.string.Twelve), 12)
        };


        /**
         * Checks mRolls number and either rolls the dices or calculates the points and
         * moves to next round if all rols for this round is completed.
         * Updates RoundTextView after every roll
         * @param view
         */
        mRollButton = findViewById(R.id.rollButton);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                    //calculatePoints();
                    mGameOptions[mGameOptionNr].calculatePoints(mDices);
                    showScore();
                    newRound();
                }
            }
        });

        mRoundTextView = findViewById(R.id.roundTextView);
        setRoundTextViewText();
        setAvailableGameOptions();

        /**
         * Makes the selected item in the spinner the chosen option
         * @param adapterView
         * @param view
         * @param i
         * @param l
         */
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
            Dice[] savedDices = (Dice[]) savedInstanceState.getParcelableArray(KEY_DICES);

            for(int i = 0; i < mDices.length; i++) {
                mDices[i].setValue(savedDices[i].getValue());
                mDices[i].setActive((savedDices[i].isActive()));
                setDiceImage(mDices[i]);
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


    /**
     * Loop through all the dice and roll the ones that has not been choosen to keep
     */
    public void updateDice() {
        // Loop all dice
        for(Dice dice : mDices) {
            // Check if Dice is active
            if(dice.isActive()) {
                dice.roll();
                setDiceImage(dice);
            }
        }
    }

    /**
     * Triggered by click on dice, activates and deactivates dice making them roll or not roll on next round
     * @param view
     */
    public void diceClick(View view) {
        for (Dice dice : mDices) {
            if(dice.getId() == view.getId()) {
                if(dice.isActive()) {
                    dice.setActive(false);
                } else {
                    dice.setActive(true);
                }
                setDiceImage(dice);
            }
        }
    }

    /**
     * Reset dices and mRolls count
     */
    private void newRound() {

        setAvailableGameOptions();
        if(mAvailableGameOptions.size() != 0) {
            for (Dice dice : mDices) {
                dice.roll();
                dice.setActive(true);
                setDiceImage(dice);
            }
            mRollButton.setText(getString(R.string.RollButtonText));
            mRolls = 1;
            setRoundTextViewText();
        } else {
            showResult();
        }
    }

    /**
     * End of game show ResultActivity and send result as an intent
     */
    private void showResult() {
        int[] sums = new int[10];
        for (int i = 0; i < mGameOptions.length; i++) {
            sums[i] = mGameOptions[i].getSum();
        }
        Intent intent = ResultActivity.newIntent(MainActivity.this, sums);
        startActivity(intent);
    }

    /**
     * Updates the spinner so it only displays the available gameoptions and not the ones that already has been choosen
     */
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

    /**
     * Update the roundTextView to display correct round
     */
    private void setRoundTextViewText() {
        mRoundTextView.setText(getString(R.string.RollText) + " " + mRolls + getString(R.string.EndRollText));
    }

    /**
     * Display a toast with the score given for the previous round
     */
    private void showScore() {
        Toast toast = Toast.makeText(this, getString(R.string.GameOptionText) + " " + mGameOptions[mGameOptionNr].getName()  + " " + getString(R.string.GaveYouText) + " " + mGameOptions[mGameOptionNr].getSum() +  " " + getString(R.string.PointsText), Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Update the image of a dice to display correct value and color
     * Display grey if dice is choosen and white if it is active and shall be rolled
     *
     * @param dice
     */
    private void setDiceImage(Dice dice) {
        ImageButton button = findViewById(dice.getId());
        if(dice.isActive()) {
            //set white image
            switch(dice.getValue()) {
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
            switch(dice.getValue()) {
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

    /**
     * Stores the state of the game
     * @param savedInstanceState
     */
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

        savedInstanceState.putParcelableArray(KEY_DICES, mDices);

        savedInstanceState.putInt(KEY_ROLLS, mRolls);
        savedInstanceState.putInt(KEY_GAME_OPTION_NR, mGameOptionNr);
    }
}
