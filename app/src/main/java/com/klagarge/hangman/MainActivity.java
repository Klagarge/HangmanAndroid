package com.klagarge.hangman;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyView myViewCompteur = new MyView(this);
        LinearLayout myLayout1 = (LinearLayout)findViewById(R.id.myView);
        myLayout1.addView(myViewCompteur);
        //setContentView(myViewCompteur);
    }
}