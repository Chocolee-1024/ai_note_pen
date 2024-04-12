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
        private Path mPath;
        private Paint mPaint;

        private float lastPressure = 0;

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
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            float pressure = event.getPressure();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchStart(x, y, pressure);
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

        private void touchStart(float x, float y, float pressure) {
            mPath.reset(); // 在每次按下時重置 Path 對象
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
            mDrawing = true;
            lastPressure = pressure; // 記錄上一次的筆壓
        }

        private void touchMove(float x, float y, float pressure) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (mDrawing && (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)) {
                // 根據筆壓計算線條寬度
                float strokeWidth = pressure * 10.0f;
                mPaint.setStrokeWidth(strokeWidth);

                // 繪製線段
                mCanvas.drawLine(mX, mY, x, y, mPaint);

                mX = x;
                mY = y;
            }
        }

        private void touchUp() {
            if (mDrawing) {
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