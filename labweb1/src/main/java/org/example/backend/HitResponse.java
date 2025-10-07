package org.example.backend;

import java.time.LocalDate;

public class HitResponse {
    private double x;
    private double y;
    private double r;
    private LocalDate date = LocalDate.now();
    private boolean hit;

    public HitResponse(double x, double y, double r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }
    public HitResponse() {
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public boolean getHit() {
        return hit;
    }
    public void setHit(boolean hit) {
        this.hit = hit;
    }
}