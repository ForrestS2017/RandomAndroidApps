package com.example.fsmit.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.fsmit.geoquiz.answer_is_true";
    private boolean mAnswerIsTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Get answer from new Intent
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // Set up text and button views to show answer!
        final TextView mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        Button mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else{
                    mAnswerTextView.setText(R.string.false_button);
                }
            }
        });

    }

    /**
     * Easy intent creation
     * @param packageContext package the activity class can be found in
     * @param answerIsTrue  whether the answer is True or not
     * @return  new Intent
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

}
