package fr.slapker.bullet.models;

import fr.slapker.bullet.launcher.GameParam;
import javafx.scene.image.ImageView;

import java.util.Random;

public class WeaponBullet extends MovingObject {

    private double angle;
    private double bulletSpeed = 600;
    private int randomAccuracyReducer = 4;

    /**
     * @param pAngle     Fire angle used for fire direction
     * @param fromImage  Image used to calculate the first position of the bullet
     * @param pStartVelX X Velocity of the shooter
     * @param pStartVelY Y Velocity of the shooter
     */
    public WeaponBullet(double pAngle, ImageView fromImage, double pStartVelX, double pStartVelY) {

        velocityX = bulletSpeed + Math.abs(pStartVelX);
        velocityY = bulletSpeed + Math.abs(pStartVelY);

        bulletRadius.setValue(0.1);

        pAngle = 90 + pAngle + accuracyReducer();
        if (pAngle > 180) {
            pAngle = -(360 - pAngle);
        }

        angle = pAngle;
        angle = (angle / 180) * Math.PI;

        double centerY = fromImage.getLayoutBounds().getCenterY();
        double centerX = fromImage.getLayoutBounds().getCenterX();

        posX.setValue(centerX + (fromImage.getFitWidth() / 2) * Math.sin(angle));
        posY.setValue(centerY - (fromImage.getFitHeight() / 2) * Math.cos(angle));
    }


    public void move(double time) {
        posX.setValue(posX.getValue() + (velocityX) * time * Math.sin(angle) * GameParam.getInstance().getTimeReducerCoeff());
        posY.setValue(posY.getValue() - (velocityY) * time * Math.cos(angle) * GameParam.getInstance().getTimeReducerCoeff());

    }


    /**
     * Can reduce the fire accuracy
     *
     * @return the delta of Fire Angle to reduce accuracy
     */
    private Integer accuracyReducer() {
        Integer diffAngle = 0;
        if (randomAccuracyReducer > 0) {

            Random r = new Random();
            diffAngle = r.nextInt(randomAccuracyReducer);
            if (diffAngle % 2 == 0) {
                diffAngle = -diffAngle;
            }
        }

        return diffAngle;
    }

    public double getMassValue() {
        return mass.getValue();
    }

    @Override
    public void fusion(MovingObject movingObject) {

    }
}
