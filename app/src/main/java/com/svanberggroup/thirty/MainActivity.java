package com.svanberggroup.thirty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageButton mDie0, mDie1, mDie2, mDie3, mDie4, mDie5;

    private Die[] mDies;

    private Button mRollButton;


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
        newRound();

        mRollButton = findViewById(R.id.rollButton);
        mRollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Die die : mDies) {
                    if(die.isActive()) {
                        die.roll();
                        setDieImage(die);
                    }
                }
            }
        });


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
            die.roll();
            setDieImage(die);
            Log.d(TAG, String.valueOf(die.getDieValue()));
        }
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
