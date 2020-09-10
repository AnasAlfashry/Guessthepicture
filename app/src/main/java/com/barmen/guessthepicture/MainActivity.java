package com.barmej.guessthepicture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static com.barmej.guessthepicture.R.drawable.icon_1;


public class MainActivity extends AppCompatActivity {
    private String[] ANSWER_DESCRIPTION;
    int[] mViewPictures = {
            icon_1,
            R.drawable.icon_2,
            R.drawable.icon_3,
            R.drawable.icon_4,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10,
            R.drawable.icon_11,
            R.drawable.icon_12,
            R.drawable.icon_13
    };

    int mCurrentIndex = 0;
    ImageView QuestionImageView;
    private String mCurrentAnswerDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_PREF,  MODE_PRIVATE);
        String appLang = sharedPreferences.getString(Constants.APP_LANG,"");
        if (!appLang.equals(""))
            LocalHelper.setLocal(this, appLang);
        setContentView(R.layout.activity_main);
        QuestionImageView = findViewById(R.id.image_view_question);
        findViewById(R.id.button_change_question);
        findViewById(R.id.button_open_answer);
        ANSWER_DESCRIPTION = getResources().getStringArray(R.array.answer_description);
        showNewQuestion();
    }


    public void buttonChangeLanguage(View view){
        showLanguageDialog();
        findViewById(R.id.button_change_language);

    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.Change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String language = "ar";
                        switch (which){
                            case 0:
                               language = "ar";
                               break;
                            case 1:
                               language = "en";
                               break;
                            case 2:
                               language = "ru";
                               break;
                        }
                        saveLanguage(language);
                        LocalHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }
    private void saveLanguage(String lang) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang",lang);
        editor.apply();
    }

    private void showNewQuestion(){
        Random random = new Random();
        int randomQuestionIndex = random.nextInt(ANSWER_DESCRIPTION.length);
        mCurrentAnswerDescription = ANSWER_DESCRIPTION[randomQuestionIndex];
    }

    public void onChangeImageClicked(View view) {
        try {
        mCurrentAnswerDescription = ANSWER_DESCRIPTION[mCurrentIndex];
            Drawable questionDrawable = ContextCompat.getDrawable(this, mViewPictures[mCurrentIndex++]);
            QuestionImageView.setImageDrawable(questionDrawable);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }
    public void buttonOpenAnswer(View view){
        Intent intent = new Intent(MainActivity.this,answerActivity.class);
        intent.putExtra("answer_description",mCurrentAnswerDescription);
        startActivity(intent);
    }
    public void buttonShareQuestionClicked(View view){
        Intent intent = new Intent(MainActivity.this,shareActivity.class);
        intent.putExtra("text_share_extra",mViewPictures);
        startActivity(intent);

    }

}