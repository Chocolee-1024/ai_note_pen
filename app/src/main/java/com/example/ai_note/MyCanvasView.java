package com.example.ai_note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;

public class MyCanvasView extends View {
    private Context context;
    private Paint mBitmapPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private static float PRESSURE_THRESHOLD = 0.2f; // 定義壓力閾值
    private Path mPath;
    private Paint mPaint;

    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private boolean mDrawing = false;

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAlpha(0x80);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 將 mBitmap 繪製到畫布上
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        // 再將 mPath 繪製到 mBitmap 上
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float pressure = event.getPressure();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                PRESSURE_THRESHOLD = pressure;
                mPaint.setStrokeWidth(pressure * 10.0f);
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y, pressure);
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
        }
        invalidate();
        return true;
    }

    private void touchStart(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        mDrawing = true;
    }

    private void touchMove(float x, float y, float pressure) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (mDrawing && (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)) {
            // 計算筆壓變化
            float pressureDifference = Math.abs(pressure - PRESSURE_THRESHOLD);

            if (pressureDifference > PRESSURE_THRESHOLD) {
                // 如果筆壓變化超過閾值，結束前一段線段並開始新的線段
                touchUp();
                touchStart(x, y);
            } else {
                // 如果筆壓變化未超過閾值，繼續在當前線段上繪製
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
                PRESSURE_THRESHOLD = pressure;
                mPaint.setStrokeWidth(pressure * 10.0f);
            }
        }
    }

    private void touchUp() {
        if (mDrawing) {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);
            mPath.reset();
            mPaint.setStrokeWidth(10.0f); // 重置畫筆的粗細為初始值
            mDrawing = false;
        }
    }

    public void clear() {
        mCanvas.drawColor(Color.WHITE);
        invalidate();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setStrokeWidth(int width) {
        mPaint.setStrokeWidth(width);
    }

    public void setPathEffect() {
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{10, 10}, 0);
        mPaint.setPathEffect(dashPathEffect);
    }
}