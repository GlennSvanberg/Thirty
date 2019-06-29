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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageButton mDie0, mDie1, mDie2, mDie3, mDie4, mDie5;
    private Button mRollButton;
    private TextView mRoundTextView;
    private Spinner mOptionsSpinner;

    private Die[] mDies;
    private Option[] mOptions;
    private int[] mScore;
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

        mDies = new Die[] {
                new Die(mDie0.getId()),
                new Die(mDie1.getId()),
                new Die(mDie2.getId()),
                new Die(mDie3.getId()),
                new Die(mDie4.getId()),
                new Die(mDie5.getId())
        };

        mOptions = new Option[] {
                new Option("LOW"),
                new Option("4"),
                new Option("5"),
                new Option("6"),
                new Option("7"),
                new Option("8"),
                new Option("9"),
                new Option("10"),
                new Option("11"),
                new Option("12")
        };


        mRollButton = findViewById(R.id.rollButton);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Roll dies
                mRolls++;
                if(mRolls <= 3) {
                    for(Die die : mDies) {
                        if(die.isActive()) {
                            die.roll();
                            setDieImage(die);
                        }
                    }
                    setRoundTextViewText();
                } else {
                    Log.d(TAG, "time to make a cjhoice");
                    //Deactivate button

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
                Log.d(TAG, String.valueOf(mOptions[i]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        newRound();

    }

    public void dieClick(View view) {
        // Find the Die that has been clicked
        for (Die die : mDies) {
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
        for (Die die : mDies) {
            mRolls = 1;
            die.roll();
            setDieImage(die);
            Log.d(TAG, String.valueOf(die.getDieValue()));
        }
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
