package com.zhengxyou.demo523;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhengxyou@163.com on 2018/5/23.
 */
public class RadarView extends View {
    private int count = 6;
    private float angle = (float) (Math.PI * 2 / count);//弧度
    private float radius;
    private int centerX;
    private int centerY;
    private String[] titles = {"a", "b", "c", "d", "e", "f"};
    private double[] data = {100, 80, 60, 30, 80, 30, 10, 20};
    private float maxValue = 100;
    private Paint mainPaint;
    private Paint valuePaint;
    private Paint textPaint;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mainPaint = new Paint();
        valuePaint = new Paint();
        textPaint = new Paint();

        mainPaint.setColor(Color.BLACK);
        mainPaint.setStrokeWidth(1);
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setAntiAlias(true);

        valuePaint.setColor(Color.BLUE);
        valuePaint.setStrokeWidth(3);
        valuePaint.setAntiAlias(true);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(3);
        textPaint.setTextSize(16);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.rotate(90, centerX, centerY);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawValue(canvas);
//        Path path = new Path();
//        float r = radius / (count - 1);
//        Log.e("TAG", radius + "," + r);
//        for (int j = 1; j < count; j++) {
//            float cuR = j * r;
////            path.reset();
//            for (int i = 0; i < count; i++) {
//                if (i == 0) {
//                    path.moveTo(centerX + cuR, centerY);
//                } else {
//                    float x = (float) (centerX + cuR * Math.cos(angle * i));
//                    float y = (float) (centerY + cuR * Math.sin(angle * i));
//                    path.lineTo(x, y);
//                }
//            }
//            path.close();
//        }
//        canvas.drawPath(path, mainPaint);
    }

    private void drawValue(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            float pre = (float) (data[i] / 100);
            float x = (float) (centerX + (radius * pre) * Math.cos(angle * i));
            float y = (float) (centerY + (radius * pre) * Math.sin(angle * i));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 3, valuePaint);
        }
//        valuePaint.setStyle(Paint.Style.STROKE);
        valuePaint.setAlpha(111);
        canvas.drawPath(path, valuePaint);
    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));

            if (angle * i >= 0 && angle * i <= Math.PI / 2) {
                canvas.drawText(titles[i], x, y, textPaint);
            } else if (angle * i >= 3 * Math.PI / 2 && angle * i <= Math.PI * 2) {//第3象限
                canvas.drawText(titles[i], x, y, textPaint);
            } else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {//第2象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else if (angle * i >= Math.PI && angle * i < 3 * Math.PI / 2) {//第1象限
                float dis = textPaint.measureText(titles[i]);//文本长度
                canvas.drawText(titles[i], x - dis, y, textPaint);
            }
//            canvas.drawText(titles[i], x, y, textPaint);
        }
    }

    private void drawLines(Canvas canvas) {
        Path linePath = new Path();
        for (int i = 0; i < count; i++) {
            linePath.reset();
            linePath.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            linePath.lineTo(x, y);
            canvas.drawPath(linePath, mainPaint);
        }
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);
        for (int i = 1; i < count; i++) {
            float curR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }

    }
}
