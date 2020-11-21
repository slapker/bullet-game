package fr.slapker.bullet.models;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.GameParam;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Bullet extends MovingObject {


    private double rotationCenterX;
    private double rotationCenterY;
    private double rotationSpeed = 1;
    private double ANGLE_ROTATION_DEGRE = 1;
    private boolean rotationClockwise = true;
    private double rotationSpeedCoeff = 0.2d;


    public Bullet(double pPosX, double pPosY, double pVelocityX, double pVelocityY) {
        super();
        initializeBullet(pPosX, pPosY, pVelocityX, pVelocityY);
        bulletRadius.setValue(BulletScreenParam.getInstance().getBulletStartingRadiusSurivalMode());
        expectedBulletRadius.setValue(BulletScreenParam.getInstance().getBulletStartingRadiusSurivalMode());
    }

    public Bullet(double pPosX, double pPosY, double pVelocityX, double pVelocityY, double radius) {
        super();
        initializeBullet(pPosX, pPosY, pVelocityX, pVelocityY);
        bulletRadius.setValue(radius);
        expectedBulletRadius.setValue(radius);
    }

    private void initializeBullet(double pPosX, double pPosY, double pVelocityX, double pVelocityY) {
        velocityY = pVelocityY;
        velocityX = pVelocityX;
        posX.setValue(pPosX);
        posY.setValue(pPosY);
    }

    public void move(double time) {
        if (mouvementEnum != null && mouvementEnum.equals(MouvementEnum.ROTATION)) {
            rotate();
        } else {
            posY.setValue(posY.getValue() + velocityY * time * GameParam.getInstance().getTimeReducerCoeff());
            posX.setValue(posX.getValue() + velocityX * time * GameParam.getInstance().getTimeReducerCoeff());
        }

        if (bulletRadius.getValue() < expectedBulletRadius.getValue()) {
            bulletRadius.setValue(bulletRadius.getValue() + 10d * time);
        }
    }


    private void rotate() {
        double angle = (ANGLE_ROTATION_DEGRE * Math.PI / 180) * GameParam.getInstance().getTimeReducerCoeff() * rotationSpeed * rotationSpeedCoeff;
        if (rotationClockwise) {
            angle = -angle;
        }
        double xM = posX.getValue() - rotationCenterX;
        double yM = posY.getValue() - rotationCenterY;
        double newX = xM * Math.cos(angle) + yM * Math.sin(angle) + rotationCenterX;
        double newY = -xM * Math.sin(angle) + yM * Math.cos(angle) + rotationCenterY;
        posX.setValue(newX);
        posY.setValue(newY);
    }

    public double getMassValue() {
        return mass.getValue();
    }

    public void fusion(MovingObject movingObject) {
        if (movingObject instanceof Bullet) {
            double newRadius = bulletRadius.getValue() + movingObject.getBulletRadius().getValue() / 6d;
            velocityY = velocityY + (movingObject.getVelocityY() * (movingObject.getMassValue() / this.getMassValue()) / 4);
            velocityX = velocityX + (movingObject.getVelocityX() * (movingObject.getMassValue() / this.getMassValue()) / 4);
/*            velocityX = velocityX / 2;
            velocityY = velocityY / 2;*/
            expectedBulletRadius.setValue(newRadius);
        }
    }

/*    public void setRadius(double pRadius) {
        radius.setValue(pRadius);
        mass = radius.getValue() * 100;
    }*/

}
