package com.wosloveslife.drawabletrain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by zhangh on 2017/4/26.
 */

public class EraseAnimDrawable extends Drawable {

    private static final int state_start = 0;
    private static final int state_running = 1;

    float left;
    float top;
    int width;
    int height;
    int interval;
    float widthSpan;
    float heightSpan;

    int state;
    boolean anim;
    private long mDuration;

    private final Paint mPaint;
    private final Paint mPaintBg;
    private final Bitmap mBitmap;
    private final Drawable mEdgeDrawable;

    public EraseAnimDrawable(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg = new Paint();
        mPaintBg.setColor(context.getResources().getColor(android.R.color.white));

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ico_rubber);

        mEdgeDrawable = context.getResources().getDrawable(R.drawable.erase_right_edge);

        width = 80;
        height = 80;
        interval = 16;

        setDuration(300);
    }

    public void start() {
        if (state == state_running) {
            reset();
        }
        anim = true;
        invalidateSelf();
    }

    public void stop() {
        anim = false;
        state = state_start;
    }

    public void reset() {
        stop();
        invalidateSelf();
    }

    public void setDuration(long duration) {
        mDuration = duration;
        if (mDuration == 0) return;
        float time = mDuration / interval;
        widthSpan = getBounds().width() / time;
        heightSpan = getBounds().height() / time * 4;
    }

    boolean direction;

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        int w = bounds.width();
        int h = bounds.height();
        boolean stop = false;
        if (state == state_start) {
            if (anim) {
                state = state_running;
            }
            left = 0;
            top = 0;
        } else {
            if (left + widthSpan >= w - width) {
                left = w - width;
                stop = true;
            } else {
                left += widthSpan;
            }

            if (direction) {
                if (top + heightSpan > h - height) {
                    top = h - height;
                    direction = false;
                } else {
                    top += heightSpan;
                }
            } else {
                if (top - heightSpan < 0) {
                    top = 0;
                    direction = true;
                } else {
                    top -= heightSpan;
                }
            }
        }

        canvas.drawRect(new RectF(0, 0, left + width, h), mPaintBg);
        mEdgeDrawable.setBounds(new Rect((int) left + width, 0, (int) left + width * 2, h));
        mEdgeDrawable.draw(canvas);

        canvas.drawBitmap(mBitmap, null, new RectF(left, top, left + width, top + height), mPaint);


        if (!anim) {
            return;
        }

        if (state == state_running) {
            if (stop) {
                state = state_start;
                anim = false;
            } else {
                invalidateSelf();
            }
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        setDuration(mDuration);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
