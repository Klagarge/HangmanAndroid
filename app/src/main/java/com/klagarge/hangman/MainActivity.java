package com.klagarge.hangman;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {

    private MyView drawingView;
    final int MAX_STEPS = 8;
    private WordManager word = new WordManager();
    public TextView userWord;
    public String radiovalue;
    public char[] letter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    public void update(View v) {
        Button btn = findViewById(v.getId());
        letter = btn.getText().toString().toLowerCase().toCharArray();
        btn.setEnabled(false);

        if(word.checkLetter(letter[0])){
            userWord.setText(word.userWord);
        } else {
            drawingView.state++;
        }

        if (drawingView.state >= MAX_STEPS){
            String msg = "";
            msg += "Sorry, you have lost ;(";
            msg += "\n";
            msg += "The good word was: ";
            msg += word.secretWord;
            Toast faile = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            faile.setGravity(1, 0, 200);
            faile.show();
            continu();
        }

        if (word.isWordComplete()){
            Toast win = Toast.makeText(this, "Congratulation, you win !!", Toast.LENGTH_LONG);
            win.setGravity(1, 0, 200);
            win.show();
            continu();
        }

        drawingView.invalidate();
    }

    public void lauch(View v) {
        RadioGroup levelGroup = findViewById(R.id.Level);
        radiovalue = ((RadioButton)findViewById(levelGroup.getCheckedRadioButtonId())).getText().toString();
        word.askSecretWord(radiovalue);

        setContentView(R.layout.activity_main);
        drawingView = new MyView(this);
        LinearLayout myLayout1 = findViewById(R.id.myView);
        myLayout1.addView(drawingView);

        userWord = findViewById(R.id.userWord);
        userWord.setText(word.userWord);
    }

    private void continu(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setContentView(R.layout.menu);
            }
        }, 4000);
    }
}