package com.example.myproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

public class Drawing extends View {
    private Paint brush;
    private Paint blueBrush;
    private LinearGradient linearGradient;
    private RadialGradient radialGradient;
    private SweepGradient sweepGradient;
    public Drawing(Context context) {
        super(context);
        init();
    }

    private void init() {
        linearGradient = new LinearGradient(0,0,200,200, Color.RED, Color.BLACK, Shader.TileMode.MIRROR);
        radialGradient = new RadialGradient(0,0, 20, Color.GREEN, Color.BLUE, Shader.TileMode.MIRROR);
        int[] colors = {
                0xFFFFFF88, // yellow
                0xFF0088FF, // blue
                0xFF000000, // black
                0xFFFFFF88  // yellow
        };
        float[] positions = {0.0f, 0.33f, 0.66f, 1.0f};
        sweepGradient = new SweepGradient(getMeasuredWidth() / 2, getMeasuredHeight() / 2, colors, positions);
        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        blueBrush = new Paint(Paint.ANTI_ALIAS_FLAG);
        blueBrush.setStrokeWidth(10f);
        brush.setColor(Color.parseColor("red"));
        // brush.setShader(linearGradient);
        // brush.setShader(radialGradient);
        brush.setShader(sweepGradient);
        blueBrush.setColor(Color.parseColor("blue"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 50f, brush);
        canvas.drawLine(0, 0, getMeasuredWidth() / 2, getMeasuredHeight() / 2, blueBrush);
        super.onDraw(canvas);
    }
}
