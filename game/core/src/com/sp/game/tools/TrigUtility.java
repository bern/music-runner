package com.sp.game.tools;

/**
 * Created by Troy on 11/16/2015.
 */
public class TrigUtility {
    //Projectiles, when fired, compute a trajectory angle. Updates in their movement are facilitated
    //by this math utility class

    private float originX;
    private float originY;

    private float destX;
    private float destY;

    private float deltaX;
    private float deltaY;

    public TrigUtility(float originX, float originY, float destX, float destY) {
        this.originX = originX;
        this.originY = originY;
        this.destX = destX;
        this.destY = destY;

        calculateDeltas();
    }

    private void calculateDeltas() {
        float dX = destX - originX;
        float dY = destY - originY;

        double angle = Math.atan(dY / dX);      //arctangent of dY / dX returns angle, bewteen -pi/2 and pi/2

        //We must adjust angle to account for arctan limitation
        if (dX < 0) {
            //If we are in Q2 or Q3 (considering source point as origin), we must add Pi.
            angle += Math.PI;
        }

        deltaX = (float) Math.cos(angle);
        deltaY = (float) Math.sin(angle);
    }

    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }
}

