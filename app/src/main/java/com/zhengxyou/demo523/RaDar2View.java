package com.zhengxyou.demo523;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhengxyou@163.com on 2018/5/23.
 */
public class RaDar2View extends View {
    private final int HIERARCHY = 5;
    private final float maxValue = 100.0f;
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();
    private int fillAlpha = 122;
    private int count = 6;//几边形
    private Paint.Style mStyle = Paint.Style.FILL;
    private float radius;
    private float angle;
    private float centerX;
    private float centerY;
    private boolean isDrawCircle = false;
    private float mCirCleRadius = 3;
    private Paint mainPaint;
    private Paint valuePaint;
    private Paint textPaint;

    public RaDar2View(Context context) {
        this(context, null);
    }

    public RaDar2View(Context context, @Nullable AttributeSet attrs) {
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
        setCount(count);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2 * 0.8f;
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画多边形
        drawPolygonal(canvas);
        //画连线
        drawLiens(canvas);
        //画文字
        drawText(canvas);
        //画值连线
        drawValue(canvas);
    }

    private void drawValue(Canvas canvas) {
        valuePaint.setAlpha(getFillAlpha());
        valuePaint.setStyle(getStyle());
        Path path = new Path();
        for (int i = 0; i < getCount(); i++) {
            float percentage = getValues().get(i) / getMaxValue();
            float x = (float) (centerX + (radius * percentage) * Math.cos(getAngle() * i));
            float y = (float) (centerY + (radius * percentage) * Math.sin(getAngle() * i));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            if (isDrawCircle()) {
                canvas.drawCircle(x, y, getCirCleRadius(), valuePaint);
            }
        }
        path.close();
        canvas.drawPath(path, valuePaint);
    }

    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < getCount(); i++) {
            float x = (float) (centerX + (radius + fontHeight) * Math.cos(getAngle() * i));
            float y = (float) (centerY + (radius + fontHeight) * Math.sin(getAngle() * i));
            canvas.drawText(getTitles().get(i), x, y, textPaint);
        }
    }

    private void drawLiens(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < getCount(); i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            float x = (float) (centerX + radius * Math.cos(getAngle() * i));
            float y = (float) (centerY + radius * Math.sin(getAngle() * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mainPaint);
        }
    }

    private void drawPolygonal(Canvas canvas) {
        Path path = new Path();
        float r = radius / HIERARCHY;
        for (int j = 1; j <= HIERARCHY; j++) {
            path.reset();
            float curR = r * j;
            for (int i = 0; i < getCount(); i++) {
                if (i == 0) {
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float x = (float) (centerX + curR * Math.cos(getAngle() * i));
                    float y = (float) (centerY + curR * Math.sin(getAngle() * i));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }

    }

    public void setCount(int count) {
        this.count = count;
        setAngle();
        postInvalidate();
    }

    public int getCount() {
        return count;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setFillAlpha(int fillAlpha) {
        this.fillAlpha = fillAlpha;
    }

    public int getFillAlpha() {
        return fillAlpha;
    }

    public void setStyle(Paint.Style mStyle) {
        this.mStyle = mStyle;
    }

    public Paint.Style getStyle() {
        return mStyle;
    }

    public void setAngle() {
        this.angle = (float) (Math.PI * 2 / getCount());
    }

    public float getAngle() {
        return angle;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles.clear();
        this.titles.addAll(titles);
    }

    public ArrayList<String> getTitles() {
        if (titles.size() == 0) {
            titles.add("A");
            titles.add("B");
            titles.add("C");
            titles.add("D");
            titles.add("E");
            titles.add("F");
            titles.add("G");
            titles.add("H");
            titles.add("J");
        }
        return titles;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values.clear();
        this.values.addAll(values);
    }

    public ArrayList<Integer> getValues() {
        if (values.size() == 0) {
            Random random = new Random();
            for (int i = 0; i < 16; i++) {
                values.add(random.nextInt(100));
            }
        }
        return values;
    }

    public void setDrawCircle(boolean drawCircle) {
        isDrawCircle = drawCircle;
    }

    public boolean isDrawCircle() {
        return isDrawCircle;
    }

    public float getCirCleRadius() {
        return mCirCleRadius;
    }

    public void setCirCleRadius(float mCirCleRadius) {
        this.mCirCleRadius = mCirCleRadius;
    }
}
