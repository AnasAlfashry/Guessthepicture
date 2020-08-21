package com.barmej.guessthepicture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class answerActivity extends AppCompatActivity {
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


    public String mCurrentAnswerDescription;
    private TextView mTextViewAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mTextViewAnswer = findViewById(R.id.text_view_answer);
        String answer_description = getIntent().getStringExtra( "answer_description");
        mTextViewAnswer.setText(answer_description);
        showNewQuestion();
    }
    private void showNewQuestion(){
        Random random = new Random();
        int randomQuestionIndex = random.nextInt(ANSWER_DESCRIPTION.length);
        mCurrentAnswerDescription = ANSWER_DESCRIPTION[randomQuestionIndex];
    }

    public void onReturnClicked(View view) {
        finish();
    }
}
