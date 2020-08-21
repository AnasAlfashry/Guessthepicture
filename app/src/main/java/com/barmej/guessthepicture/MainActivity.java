package com.barmej.guessthepicture;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private static final String[] ANSWER_DESCRIPTION = {
            "يوضع فوق الحطّة\\الغترة لتثبيتها",
            "مبطنة بالصوف، تلبس في الشتاء للوقاية من البرد",
            "أحد أنواع النسيج البدوي التقليدي",
            "تستخدم في صبغ الشعر واللحية، كما تستخدم أيضاً في تجميل الأيدي بالنسبة للنساء",
            "الوعاء المستخدم في تقديم القهوة عند العرب",
            "مصنوع من الجلد، يُلبس في القدم، اشتُهر قديماً عند العرب",
            "سلاح أبيض صغير يوضع على الخصر",
            "وسادة صغيرة يُتكأ عليها",
            "اسم الاداة التي كانت تُستخدم للإضاءة قديما",
            "أداة تُستخدم للإضاءة والزينة",
            "قبعة حمراء اشتهرت في بلاد الشام ومصر والمغرب",
            "غطاء يُكوّر فوق الرأس",
            "ثوب عربي يُلبس بكثرة في دول الخليج"
    };
    int[] mViewPictures = {
            R.drawable.icon_1,
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
    ;
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
        showNewQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuChaneLang) {
            showLanguageDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
        SharedPreferences sharedPreferences = getSharedPreferences("app_prev", MODE_PRIVATE);
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
        } catch (ArrayIndexOutOfBoundsException e) {
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