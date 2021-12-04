package com.klagarge.hangman;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

public class MyView extends View
{
    //Paint paint = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);
    Paint paint = new Paint(Paint.LINEAR_TEXT_FLAG);
    //Path curvePath;
    //Paint textPaint = new Paint(Paint.LINEAR_TEXT_FLAG);

    public MyView(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // draw basic shapes

        paint.setColor(Color.WHITE);

        float scale = 3.0F;

        //jambes de la potence
        canvas.drawLine(110 * scale, 210 * scale, 120 * scale, 190 * scale, paint);
        canvas.drawLine(130 * scale, 210 * scale, 120 * scale, 190 * scale, paint);

        //potence
        canvas.drawLine(120 * scale, 190 * scale, 120 * scale, 100 * scale, paint);

        //bras potence
        canvas.drawLine(120 * scale, 100 * scale, 180 * scale, 100 * scale, paint);

        //fil potence
        canvas.drawLine(180 * scale, 100 * scale, 180 * scale, 110 * scale, paint);

        //tête
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(180 * scale, 120 * scale, 10 * scale, paint);

        //corps
        canvas.drawLine(180 * scale, 130 * scale, 180 * scale, 170 * scale, paint);

        //jambes
        canvas.drawLine(180 * scale, 170 * scale, 170 * scale, 190 * scale, paint);
        canvas.drawLine(180 * scale, 170 * scale, 190 * scale, 190 * scale, paint);

        //bras
        canvas.drawLine(170 * scale, 150 * scale, 190 * scale, 150 * scale, paint);

    }
}