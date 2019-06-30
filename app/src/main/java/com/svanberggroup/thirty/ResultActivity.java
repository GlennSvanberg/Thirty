package com.svanberggroup.thirty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_RESULT = "com.svanberggroup.android.thirty.result";

    private TextView mTextViewLow, mTextView4, mTextView5, mTextView6, mTextView7, mTextView8, mTextView9, mTextView10, mTextView11, mTextView12;
    private Option[] mOptions;

    public static Intent newIntent(Context packageContext, Option[] result) {
        Intent intent = new Intent(packageContext, ResultActivity.class);
        intent.putExtra(EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mOptions = getIntent().getParcelableArrayExtra(EXTRA_RESULT);

        mTextViewLow = findViewById(R.id.resultTextViewLow);
        mTextViewLow.setText();

        mTextView4 = findViewById(R.id.resultTextView4);
        mTextView5 = findViewById(R.id.resultTextView5);
        mTextView6 = findViewById(R.id.resultTextView6);
        mTextView7 = findViewById(R.id.resultTextView7);
        mTextView8 = findViewById(R.id.resultTextView8);
        mTextView9 = findViewById(R.id.resultTextView9);
        mTextView10 = findViewById(R.id.resultTextView10);
        mTextView11 = findViewById(R.id.resultTextView11);
        mTextView12 = findViewById(R.id.resultTextView12);




    }
}
