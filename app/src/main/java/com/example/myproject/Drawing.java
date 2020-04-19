package com.example.myproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Drawing extends View {
    private Paint brush;
    private Paint blueBrush;
    public Drawing(Context context) {
        super(context);
        init();
    }

    private void init() {
        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        blueBrush = new Paint(Paint.ANTI_ALIAS_FLAG);
        blueBrush.setStrokeWidth(10f);
        brush.setColor(Color.parseColor("red"));
        blueBrush.setColor(Color.parseColor("blue"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 54f, brush);
        canvas.drawLine(0, 0, getMeasuredWidth() / 2, getMeasuredHeight() / 2, blueBrush);
        super.onDraw(canvas);
    }
}
