package org.example.backend;

import java.util.Arrays;

public class HitRequest {
    private float x;
    private float y;
    private float r;

    public boolean validate() {
        return Arrays.asList(-2, -1.5, -1, -0.5, 0, 0.5, 1.5, 2).contains(x) &&
                y < 3 && y > -3 &&
                r > 2 && r < 5;
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getR(){
        return r;
    }

    public void setR(float r){
        this.r = r;
    }

}
