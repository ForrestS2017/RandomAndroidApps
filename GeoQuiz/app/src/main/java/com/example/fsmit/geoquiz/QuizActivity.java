package com.example.fsmit.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton, mFalseButton, mCheatButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_California, true),
            new Question(R.string.question_NewJersey, true),
            new Question(R.string.question_Texas, false),
            new Question(R.string.question_Washington, false)
    };

    private int mCurrentIndex = 0;
    private int totalQuestions, totalCorrect = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Check for our index - this is for screen rotation purposes
        if (savedInstanceState != null) mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        // Get reference to Question TextView
        mQuestionTextView = (TextView) findViewById(R.id.quest_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(+1);
            }
        });

        /* Find and prepare buttons */

        // Set true button functionality and update question upon correct answer
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkAnswer(true)) {
                    mTrueButton.setEnabled(false);
                } else {
                    updateQuestion(+1);
                }
            }
        });

        // Set false button functionality and update question upon correct answer
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!checkAnswer(false)) {
                    mFalseButton.setEnabled(false);
                } else {
                    updateQuestion(+1);
                }
            }
        });

        // Set cheat button functionality to activate the CheatActivity
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerToast(
                        Toast.makeText(QuizActivity.this, "You're Cheating! >:(", Toast.LENGTH_LONG)
                ).show();
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);

            }
        });


        /* Find and prepare image buttons */

        // Set next button functionality
        ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                updateQuestion(1);
            }
        });

        // Set previous button functionality
        ImageButton previousButton = (ImageButton) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsCheater = false;
                updateQuestion(-1);
            }
        });

        updateQuestion(0);
    }

    /**
     * Override onSaveInstanceState() to preserve data across runtime config changes
     * @param savedInstanceState Activity instance
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    /**
     * Override for detecting cheating
     * @param requestCode   Determine if activity was a cheat activity
     * @param resultCode    Success of child activity
     * @param data          Returned intent which should hold cheat detection
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if(data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    /**
     * Update question index and write question to the TextView
     */
    private void updateQuestion(int direction) {
        // Show new question
        if( mCurrentIndex == 0 && direction == -1) mCurrentIndex = mQuestionBank.length;
        mCurrentIndex = (mCurrentIndex + direction) % mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        // Unlock answer buttons
        mFalseButton.setEnabled(true);
        mTrueButton.setEnabled(true);

        totalQuestions += 1;
    }

    /**
     * Check if the user picked TRUE or FALSE, and compare to correct answer in the Question object
     * @param userPressedTrue User's response
     * @return boolean if answer was true
     */
    private boolean checkAnswer(boolean userPressedTrue) {
        boolean correctAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int responseID = R.string.incorrect_toast;

        // If correct, change the toast string, and increment correct answers
        if( correctAnswer == userPressedTrue) {
            responseID = R.string.correct_toast;
            totalCorrect += 1;
        }

        if(mIsCheater) {
            responseID = R.string.judgement_toast;
        }

        // Make message to display score. Create a new LinearLayout to center text
        String msg = new StringBuilder( getResources().getString(responseID) ).append("\n").append(totalCorrect).append("/").append(totalQuestions).append(" correct").toString();

        //Make toast and center it
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        centerToast(toast).show();

        return correctAnswer == userPressedTrue;
    }

    /**
     * Center toast via layout up-casted toast
     * @param toast toast to be centered
     */
    private Toast centerToast(Toast toast) {
        LinearLayout layout = (LinearLayout) toast.getView();
        if (layout.getChildCount() > 0) {
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }
        return toast;
    }


}