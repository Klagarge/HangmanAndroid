package com.klagarge.hangman;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static MyView drawingView;
    static public final int MAX_STEPS = 8;
    private final WordManager word = new WordManager();
    public TextView userWord;
    public TextView correctWord;
    public TextView TextScore;
    public Button BtnNewGame;
    public Button BtnLaunch;
    private RadioGroup languageGroup;
    private RadioGroup levelGroup;
    private boolean language = false;
    private boolean level = false;
    public String radioLanguage;
    public String radioLevel;
    public char[] letter;
    private int selectedLanguage;
    private int selectedLevel;
    static public SharedPreferences settings;
    static public SharedPreferences.Editor editor;
    private Scores scores;
    private int nbClickReset = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the menu view
        setContentView(R.layout.menu);

        // Hide the launch button
        BtnLaunch = findViewById(R.id.lauch);
        BtnLaunch.setVisibility(View.GONE);

        // Define default color of reset button
        findViewById(R.id.resetScore).setBackgroundColor(Color.GRAY);

        // Set the name for the group of language and level
        languageGroup = findViewById(R.id.Language);
        levelGroup = findViewById(R.id.Level);

        // Set memories
        settings = getSharedPreferences("score", 0);
        editor = settings.edit();
        scores = new Scores(languageGroup.getChildCount());
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
            Toast fail = Toast.makeText(this, "Sorry, you have lost ;(", Toast.LENGTH_LONG);
            fail.setGravity(1, 0, 240);
            fail.show();

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

            scores.addPts(radioLanguage, radioLevel);

            endGame();
        }

        drawingView.invalidate();
    }

    public void launch(View v) {
        selectedLanguage = findViewById(languageGroup.getCheckedRadioButtonId()).getId();
        selectedLevel = findViewById(levelGroup.getCheckedRadioButtonId()).getId();
        radioLanguage = ((RadioButton)findViewById(languageGroup.getCheckedRadioButtonId())).getText().toString().toLowerCase();
        radioLevel = ((RadioButton)findViewById(levelGroup.getCheckedRadioButtonId())).getText().toString().toLowerCase();

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
        TextScore = findViewById(R.id.TextScore);

        TextScore.setText(scores.toString(radioLanguage));
    }

    private void endGame() {
        BtnNewGame.setVisibility(View.VISIBLE);

        scores.addParty(radioLanguage);
        TextScore.setText(scores.toString(radioLanguage));
        //drawingView.invalidate();
    }

    public void newGame(View v) {
        setContentView(R.layout.menu);
        findViewById(R.id.resetScore).setBackgroundColor(Color.GRAY);
        //findViewById(R.id.launch).setVisibility(View.GONE);
        level = false;
        RadioButton language = findViewById(selectedLanguage);
        RadioButton level = findViewById(selectedLevel);
        language.setChecked(true);
        level.setChecked(true);
    }

    public void reset(View v){
        nbClickReset++;
        Button pbReset = findViewById(R.id.resetScore);
        switch (nbClickReset){
            case 1:
                pbReset.setBackgroundColor(Color.YELLOW);
                Toast sure = Toast.makeText(this, "Are you sure you want to reset all your scores ?", Toast.LENGTH_SHORT);
                sure.setGravity(1, 0, 500);
                sure.show();
                break;
            case 2:
                pbReset.setBackgroundColor(Color.RED);
                Toast verySure = Toast.makeText(this, "If you click, all your scores will be reset !", Toast.LENGTH_SHORT);
                verySure.setGravity(1, 0, 500);
                verySure.show();
                break;
            default:
                scores.resetScores();
                nbClickReset = 0;
                pbReset.setBackgroundColor(Color.GRAY);
                Toast reset = Toast.makeText(this, "All scores have been reset !", Toast.LENGTH_LONG);
                reset.setGravity(1, 0, 500);
                reset.show();

        }
    }

    public void readyToLaunch(View v) {
        languageGroup = findViewById(R.id.Language);
        levelGroup = findViewById(R.id.Level);
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