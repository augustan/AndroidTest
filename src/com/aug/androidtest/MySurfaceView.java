
package com.aug.androidtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint paint = new Paint();
    private Timer timer;
    private TimerTask task;
    private Bolls bolls;

    public MySurfaceView(Context context) {
        super(context);
        paint.setColor(Color.YELLOW);
        getHolder().addCallback(this);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.YELLOW);
        getHolder().addCallback(this);
    }
    
    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint.setColor(Color.YELLOW);
        getHolder().addCallback(this);
    }

    public void draw() {
        Canvas canvas = getHolder().lockCanvas();// ����
        // ����ͼ��
        canvas.drawColor(Color.WHITE);// ��ʼ������
        for (int i = 0; i < 20; i++) {
            bolls = new Bolls(this, canvas.getWidth(), canvas.getHeight());
            canvas.drawCircle(bolls.getCx(), bolls.getCy(), bolls.getRadius(), paint);
//            bolls.draw();
        }

        getHolder().unlockCanvasAndPost(canvas);// ����
    }

    public void startTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        };
        timer.schedule(task, 100, 100);
    }

    public void stopTimer() {
        timer.cancel();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startTimer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopTimer();
    }
}
