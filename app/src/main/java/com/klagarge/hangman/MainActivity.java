package com.klagarge.hangman;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MyView drawingView;
    final int MAX_STEPS = 8;
    private WordManager word = new WordManager();
    public TextView userWord;
    public TextView correctWord;
    public Button BtnNewGame;
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
            Toast faile = Toast.makeText(this, "Sorry, you have lost ;(", Toast.LENGTH_LONG);
            faile.setGravity(1, 0, 240);
            faile.show();

            for (char i = 'a'; i <= 'z'; i++) {
                String myLetter = "";
                myLetter += i;
                Button pbTMP = findViewById(getResources().getIdentifier(myLetter, "id", this.getPackageName()));
                pbTMP.setEnabled(false);
            }

            correctWord.setText(word.secretWord);
            correctWord.setVisibility(View.VISIBLE);

            continu();
        }

        if (word.isWordComplete()){
            Toast win = Toast.makeText(this, "Congratulations, you have won !!", Toast.LENGTH_LONG);
            win.setGravity(1, 0, 240);
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

        BtnNewGame = findViewById(R.id.btnNewGame);
        BtnNewGame.setVisibility(View.GONE);

        userWord = findViewById(R.id.userWord);
        userWord.setText(word.userWord);
        correctWord = findViewById(R.id.correctWord);
        correctWord.setVisibility(View.INVISIBLE);
    }

    private void continu(){
        BtnNewGame.setVisibility(View.VISIBLE);
    }

    public void newGame(View v){
        setContentView(R.layout.menu);
    }
}