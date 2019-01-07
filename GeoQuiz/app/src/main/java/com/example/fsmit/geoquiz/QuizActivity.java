package com.example.fsmit.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton, mFalseButton;
    private ImageButton mNextButton, mPreviousButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_California, true),
            new Question(R.string.question_NewJersey, true),
            new Question(R.string.question_Texas, false),
            new Question(R.string.question_Washington, false)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Check for our index - this is for screen rotation purposes
        if (savedInstanceState != NULL) mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        // Get reference to Question TextView
        mQuestionTextView = (TextView) findViewById(R.id.quest_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(+1);
            }
        });
        updateQuestion(0);

        // Set true button functionality
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        // Set false button functionality
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // Set next button functionality
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(1);
            }
        });

        // Set previous button functionality
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(-1);
            }
        });

    }

    /**
     * Override onSaveInstanceState() to preserve data across runtime config changes
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedINstanceState.putInt(KEY_INDEX, mCurrentIndex);

    }

    /**
     * Update question index and write question to the TextView
     */
    private void updateQuestion(int direction) {
        if( mCurrentIndex == 0 && direction == -1) mCurrentIndex = mQuestionBank.length;
        mCurrentIndex = (mCurrentIndex + direction) % mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    /**
     * Check if the user picked TRUE or FALSE, and compare to correct answer in the Question object
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue) {
        boolean correctAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int responseID = userPressedTrue == correctAnswer ? R.string.corret_toast : R.string.incorrect_toast;
        Toast.makeText(this, responseID, Toast.LENGTH_SHORT).show();
    }
}