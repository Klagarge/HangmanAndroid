package com.klagarge.hangman;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private MyView drawingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = new MyView(this);
        LinearLayout myLayout1 = (LinearLayout)findViewById(R.id.myView);
        myLayout1.addView(drawingView);
    }

    public void update(View view) {
        drawingView.state++;
        drawingView.invalidate();
    }
}