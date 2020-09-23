package com.barmen.guessthepicture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class shareActivity extends AppCompatActivity {
    File file;
    int imageId;
    private String mImageViewQuestion;
    public EditText mEditTextShareTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        imageId = getIntent().getIntExtra("image_id",-1);
        ImageView imageView = findViewById(R.id.image_view_question);
        imageView.setImageResource(imageId);
        mEditTextShareTitle = findViewById(R.id.edit_text_share_title);
        mImageViewQuestion = getIntent().getStringExtra("text_share_extra");
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String questionTitle = sharedPreferences.getString("share title", "");
        mEditTextShareTitle.setText(questionTitle);


    }
    public void OnShareQuestionClicked(View view){
        String questionTitle = mEditTextShareTitle.getText().toString();
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
             file = new File(getFilesDir(),"image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = FileProvider.getUriForFile(shareActivity.this,"com.barmen.guesstimate.fileprovider",file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, questionTitle );
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
//        String questionTitle = mEditTextShareTitle.getText().toString();
//        Intent shareIntent = new Intent();
//        shareIntent.setAction(Intent.ACTION_SEND);
//        shareIntent.putExtra(Intent.EXTRA_TEXT, questionTitle + "\n" + mImageViewQuestion);
//        shareIntent.setType("text/plain");
//
//        shareIntent.setAction(mImageViewQuestion);
//        startActivity(shareIntent);
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("share title", questionTitle);
        editor.apply();
    }
}