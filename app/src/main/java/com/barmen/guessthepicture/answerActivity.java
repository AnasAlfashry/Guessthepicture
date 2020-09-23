package com.barmen.guessthepicture;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class answerActivity extends AppCompatActivity {

    private TextView mTextViewAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
       mTextViewAnswer = findViewById(R.id.text_view_answer);
      String answer_description = getIntent().getStringExtra( "answer_description");
        mTextViewAnswer.setText(answer_description);
    }
    public void onReturnClicked(View view) {
        finish();
    }

}
