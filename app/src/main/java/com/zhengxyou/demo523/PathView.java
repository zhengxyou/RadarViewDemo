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
public class PathView extends View {
    private static final String KEY_TAG = "TAG";
    private float mWidth;
    private float mHeight;
    private Paint mPaint;
    private Path mPath;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.scale(1, -1);
        mPath.addCircle(0, 0, 100, Path.Direction.CW);

        Path dst = new Path();
        dst.addRect(-200, -200, 200, 200, Path.Direction.CW);

        mPath.offset(100, 0,dst);
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(dst, mPaint);
    }
}
