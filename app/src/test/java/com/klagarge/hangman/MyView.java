package com.klagarge.hangman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View
{
    Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
    public int state = 0;

    public MyView(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        float scale = 3.0F;
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        if (state > 0) {
            //jambes de la potence
            canvas.drawLine(110 * scale, 210 * scale, 120 * scale, 190 * scale, paint);
            canvas.drawLine(130 * scale, 210 * scale, 120 * scale, 190 * scale, paint);
        }

        if (state > 1) {
            //potence
            canvas.drawLine(120 * scale, 190 * scale, 120 * scale, 100 * scale, paint);
        }

        if (state > 2) {
            //bras potence
            canvas.drawLine(120 * scale, 100 * scale, 180 * scale, 100 * scale, paint);
        }

        if (state > 3) {
            //fil potence
            canvas.drawLine(180 * scale, 100 * scale, 180 * scale, 110 * scale, paint);
        }

        if (state > 4) {
            //tête
            canvas.drawCircle(180 * scale, 120 * scale, 10 * scale, paint);
        }

        if (state > 5) {
            //corps
            canvas.drawLine(180 * scale, 130 * scale, 180 * scale, 170 * scale, paint);
        }

        if (state > 6) {
            //jambes
            canvas.drawLine(180 * scale, 170 * scale, 170 * scale, 190 * scale, paint);
            canvas.drawLine(180 * scale, 170 * scale, 190 * scale, 190 * scale, paint);
        }

        if (state > 7) {
            //bras
            canvas.drawLine(170 * scale, 150 * scale, 190 * scale, 150 * scale, paint);
        }
    }
}