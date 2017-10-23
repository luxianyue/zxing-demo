package com.zxingdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.journeyapps.barcodescanner.ViewfinderView;

/**
 * Created by bulefin on 2017/8/31.
 */

public class MyViewFinderView extends ViewfinderView {

    //四角边框与取景边框的距离
    private static float t;
    //四角边框的长度
    private float len;
    //四角边框的宽度
    private float strokeW;

    //路劲，用于定位四角边框
    protected Path mPath;

    //四角边框的颜色
    protected int cornerColor;
    //取景边框的颜色
    protected int frameColor;

    public MyViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cornerColor = Color.GREEN;
        frameColor = Color.GREEN;
        mPath = new Path();
        strokeW = 3.5f;
        t = 2f;
        len = 25 + t;
    }

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        paint.setColor(frameColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        //画取景边框
        canvas.drawRect(frame.left, frame.top, frame.right, frame.bottom, paint);

        paint.setColor(cornerColor);
        paint.setStrokeWidth(strokeW);

        //corner -->left and top
        mPath.moveTo(frame.left + t - strokeW / 2f, frame.top + t);
        mPath.lineTo(frame.left + len, frame.top + t);
        mPath.moveTo(frame.left + t, frame.top + t);
        mPath.lineTo(frame.left + t, frame.top + len);

        //corner -->right and top
        mPath.moveTo(frame.right - t + strokeW / 2f, frame.top + t);
        mPath.lineTo(frame.right - len, frame.top + t);
        mPath.moveTo(frame.right - t, frame.top + t);
        mPath.lineTo(frame.right - t, frame.top + len);

        //corner -->left and bottom
        mPath.moveTo(frame.left + t - strokeW / 2f, frame.bottom - t);
        mPath.lineTo(frame.left + len, frame.bottom - t);
        mPath.moveTo(frame.left + t, frame.bottom - t);
        mPath.lineTo(frame.left + t, frame.bottom - len);

        //corner --> right and bottom
        mPath.moveTo(frame.right - t + strokeW / 2f, frame.bottom - t);
        mPath.lineTo(frame.right - len, frame.bottom - t);
        mPath.moveTo(frame.right - t, frame.bottom - t);
        mPath.lineTo(frame.right - t, frame.bottom - len);
        //画四角边框
        canvas.drawPath(mPath, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {

            // Draw a red "laser scanner" line through the middle to show decoding is active
            //paint.setColor(laserColor);
            //paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            //scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            //int middle = frame.height() / 2 + frame.top;
            //canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);

            /*float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new ArrayList<>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }*/
           /* if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }*/

            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            postInvalidateDelayed(ANIMATION_DELAY,
                    frame.left - POINT_SIZE,
                    frame.top - POINT_SIZE,
                    frame.right + POINT_SIZE,
                    frame.bottom + POINT_SIZE);
        }
    }
}
