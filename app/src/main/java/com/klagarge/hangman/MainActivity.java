package com.klagarge.hangman;

import android.os.Bundle;
import android.util.Log;
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
    public Button BtnLaunch;
    public boolean language = false;
    public boolean level = false;
    public String radioLanguage;
    public String radioLevel;
    public char[] letter;
    private int selectedLanguage;
    private int selectedLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        findViewById(R.id.english).setVisibility(View.GONE);
        //findViewById(R.id.german).setVisibility(View.GONE);
        BtnLaunch = findViewById(R.id.lauch);
        BtnLaunch.setVisibility(View.GONE);
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
            findViewById(R.id.a_de).setEnabled(false);
            findViewById(R.id.o_de).setEnabled(false);
            findViewById(R.id.u_de).setEnabled(false);
            findViewById(R.id.s_de).setEnabled(false);

            correctWord.setText(word.secretWord);
            correctWord.setVisibility(View.VISIBLE);

            endGame();
        }

        if (word.isWordComplete()){
            Toast win = Toast.makeText(this, "Congratulations, you have won !!", Toast.LENGTH_LONG);
            win.setGravity(1, 0, 240);
            win.show();
            endGame();
        }

        drawingView.invalidate();
    }

    public void lauch(View v) {
        RadioGroup languageGroup = findViewById(R.id.Language);
        RadioGroup levelGroup = findViewById(R.id.Level);
        selectedLanguage = findViewById(languageGroup.getCheckedRadioButtonId()).getId();
        selectedLevel = findViewById(levelGroup.getCheckedRadioButtonId()).getId();
        radioLanguage = ((RadioButton)findViewById(languageGroup.getCheckedRadioButtonId())).getText().toString().toLowerCase();
        radioLevel = ((RadioButton)findViewById(levelGroup.getCheckedRadioButtonId())).getText().toString().toLowerCase();

        Log.i("toto", ""+ selectedLanguage);

        setContentView(R.layout.activity_main);
        drawingView = new MyView(this);
        LinearLayout myLayout1 = findViewById(R.id.myView);
        myLayout1.addView(drawingView);

        BtnNewGame = findViewById(R.id.btnNewGame);
        BtnNewGame.setVisibility(View.GONE);

        if(!radioLanguage.equals("deutsch")){
            findViewById(R.id.button_german).setVisibility(View.GONE);
        }

        word.askSecretWord(radioLanguage, radioLevel);
        userWord = findViewById(R.id.userWord);
        userWord.setText(word.userWord);
        correctWord = findViewById(R.id.correctWord);
        correctWord.setVisibility(View.INVISIBLE);
    }

    private void endGame() {
        BtnNewGame.setVisibility(View.VISIBLE);
    }

    public void newGame(View v) {
        setContentView(R.layout.menu);
        findViewById(R.id.english).setVisibility(View.GONE);
        //findViewById(R.id.german).setVisibility(View.GONE);
        //findViewById(R.id.lauch).setVisibility(View.GONE);
        level = false;
        RadioButton language = findViewById(selectedLanguage);
        RadioButton level = findViewById(selectedLevel);
        language.setChecked(true);
        level.setChecked(true);
        Log.i("toto", ""+selectedLanguage);
    }

    public void readyToLauch(View v) {
        RadioGroup languageGroup = findViewById(R.id.Language);
        RadioGroup levelGroup = findViewById(R.id.Level);

        if(languageGroup.getCheckedRadioButtonId() != -1){
            language = true;
        }
        if(levelGroup.getCheckedRadioButtonId() != -1){
            level = true;
        }
        if (language && level) {
            findViewById(R.id.lauch).setVisibility(View.VISIBLE);
        }
    }
}