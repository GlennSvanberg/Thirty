package com.svanberggroup.thirty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    private static final String EXTRA_RESULT = "com.svanberggroup.android.thirty.result";

    private TextView mTextViewLow, mTextView4, mTextView5, mTextView6, mTextView7, mTextView8, mTextView9, mTextView10, mTextView11, mTextView12;
    private int[] mValues;

    public static Intent newIntent(Context packageContext, int[] result) {
        Intent intent = new Intent(packageContext, ResultActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mValues = getIntent().getIntArrayExtra(EXTRA_RESULT);
        Log.d(TAG, "Result");
        for (int i : mValues) {
            Log.d(TAG, "val: " + i);
        }

        mTextViewLow = findViewById(R.id.resultTextViewLow);
        mTextViewLow.setText(String.valueOf(mValues[0]));

        mTextView4 = findViewById(R.id.resultTextView4);
        mTextView4.setText(String.valueOf(mValues[1]));

        mTextView5 = findViewById(R.id.resultTextView5);
        mTextView5.setText(String.valueOf(mValues[2]));

        mTextView6 = findViewById(R.id.resultTextView6);
        mTextView6.setText(String.valueOf(mValues[3]));

        mTextView7 = findViewById(R.id.resultTextView7);
        mTextView7.setText(String.valueOf(mValues[4]));

        mTextView8 = findViewById(R.id.resultTextView8);
        mTextView8.setText(String.valueOf(mValues[5]));

        mTextView9 = findViewById(R.id.resultTextView9);
        mTextView9.setText(String.valueOf(mValues[6]));

        mTextView10 = findViewById(R.id.resultTextView10);
        mTextView10.setText(String.valueOf(mValues[7]));

        mTextView11 = findViewById(R.id.resultTextView11);
        mTextView11.setText(String.valueOf(mValues[8]));

        mTextView12 = findViewById(R.id.resultTextView12);
        mTextView12.setText(String.valueOf(mValues[9]));





    }
}
