package org.example.backend;

public class HitChecker {

    public boolean isHit(HitRequest request){
        float x = request.getX();
        float y = request.getY();
        float r = request.getR();

        if (x >= 0 && y >= 0){
            return (y <= r/2) && (x <= r); // принадлежность прямоугольнику
        }if (x < 0 && y < 0){
            return Math.sqrt((x*x + y*y)) <= r; // принадлежность сектору
        }if (x > 0 && y < 0){
            return (Math.abs(y) * x)/2f <= (r * r)/2f;  // принадлежность треугольнику
        }
        return false;
    }

}
