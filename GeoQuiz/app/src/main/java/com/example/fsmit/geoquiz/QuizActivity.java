package com.example.fsmit.geoquiz;

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

    private Button mTrueButton, mFalseButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_California, true),
            new Question(R.string.question_NewJersey, true),
            new Question(R.string.question_Texas, false),
            new Question(R.string.question_Washington, false)
    };

    private int mCurrentIndex = 0;
    private int totalQuestions, totalCorrect = 0;

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

        // Set next button functionality
        ImageButton nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestion(1);
            }
        });

        // Set previous button functionality
        ImageButton previousButton = (ImageButton) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        // Make message to display score. Create a new LinearLayout to center text
        String msg = new StringBuilder( getResources().getString(responseID) ).append("\n").append(totalCorrect).append("/").append(totalQuestions).append(" correct").toString();

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        LinearLayout layout = (LinearLayout) toast.getView();
        if (layout.getChildCount() > 0) {
            TextView tv = (TextView) layout.getChildAt(0);
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }
        toast.show();

        return correctAnswer == userPressedTrue;
    }
}