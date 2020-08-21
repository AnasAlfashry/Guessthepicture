package com.barmej.guessthepicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class shareActivity extends AppCompatActivity {
    private String mImageViewQuestion;
    public EditText mEditTextShareTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mEditTextShareTitle = findViewById(R.id.edit_text_share_title);
        mImageViewQuestion = getIntent().getStringExtra("text_share_extra");
        SharedPreferences sharedPreferences = getSharedPreferences("app pref", MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString("share title", "");
        mEditTextShareTitle.setText(questionTitle);


    }
    public void OnShareQuestionClicked(View view){
        String questionTitle = mEditTextShareTitle.getText().toString();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, questionTitle + "\n" + mImageViewQuestion);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share title", questionTitle);
        editor.apply();
    }
}