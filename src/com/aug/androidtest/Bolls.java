
package com.aug.androidtest;

public class Bolls {
    private float cx;
    private float cy;
    private float radius;
    private int addx;
    private int addy;
    private int addRadius;
    MySurfaceView view;

    public Bolls(MySurfaceView view, int width, int height) {
        this.view = view;
        cx = (float) (Math.random() * width);
        cy = (float) (Math.random() * height);
        radius = (float) (Math.random() * 30);
        addx = (int) (Math.random() * 10);
        addy = (int) (Math.random() * 10);
        addRadius = (int) (Math.random() * 10);
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void draw() {
        cx += addx;
        cy += addy;
        radius += addRadius;
        if (cx < radius) {
            addx = Math.abs(addx);
        }
        if (cx > view.getWidth() - radius) {
            addx = -Math.abs(addx);
        }
        if (cy < radius) {
            addy = Math.abs(addy);
        }
        if (cy > view.getHeight() - radius) {
            addy = -Math.abs(addy);
        }
    }
}
