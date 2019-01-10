package com.example.fsmit.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.fsmit.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.fsmit.geoquiz.answer_shown";
    private boolean mAnswerIsTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        // Get answer from new Intent
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        // Set up text and button views to show answer!
        final TextView mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        final Button mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShown(true);

                // Add animation to hide the button. Min SDK >= 21 Lollipop
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                   int cx = mShowAnswerButton.getWidth() / 2;
                   int cy = mShowAnswerButton.getHeight() / 2;
                   float startRadius = mShowAnswerButton.getWidth();
                   Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, startRadius, 0); // Min SKD = 21
                   anim.addListener(new AnimatorListenerAdapter() {
                       @Override
                       public void onAnimationEnd(Animator animation) {
                           super.onAnimationEnd(animation);
                           mShowAnswerButton.setVisibility(View.INVISIBLE);
                       }
                   });
                   anim.start();
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

    /**
     * Prepare an intent to return holding cheating marker
     * @param isAnswerShown boolean if user cheated
     */
    public void setAnswerShown(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    /**
     * Get cheating boolean from intent in result
     * @param result intent returned to parent activity
     * @return       boolean extra of cheating
     */
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

}
