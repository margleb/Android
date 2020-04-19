package com.example.myproject;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView {
    private Paint paint;

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("blue"));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(125f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("Hello!", getMeasuredWidth() / 2, getMeasuredHeight() / 2, paint);
        canvas.save();
        super.onDraw(canvas);

    }
}
