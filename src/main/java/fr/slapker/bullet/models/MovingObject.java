package fr.slapker.bullet.models;

import fr.slapker.bullet.launcher.GameParam;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;
import java.io.Serializable;

@Getter
public abstract class MovingObject extends Parent implements Serializable {
    @Setter
    protected SimpleDoubleProperty posX = new SimpleDoubleProperty();
    ;

    @Setter
    protected SimpleDoubleProperty posY = new SimpleDoubleProperty();

    @Setter
    @Getter
    protected SimpleDoubleProperty bulletRadius = new SimpleDoubleProperty();

    @Setter
    @Getter
    protected SimpleDoubleProperty expectedBulletRadius = new SimpleDoubleProperty();

    @Setter
    protected double velocityX;

    @Setter
    protected double velocityY;

    @Getter
    @Setter
    protected MouvementEnum mouvementEnum;

    @Getter
    @Setter
    protected SimpleDoubleProperty attractionDistance = new SimpleDoubleProperty();

    @Getter
    @Setter
    protected SimpleDoubleProperty mass = new SimpleDoubleProperty();


    protected double initialDirectionX;
    protected double initialDirectionY;

    public MovingObject() {

        bulletRadius.addListener((observableValue, number, t1) -> {
            mass.setValue(bulletRadius.getValue() * GameParam.getInstance().getRadiusToMassUnity().getValue());
        });

        mass.addListener((observableValue, number, t1) -> {
                    attractionDistance.setValue(t1.doubleValue() * GameParam.getInstance().getAttractionRatio());
                }
        );
    }

    public void applyGravityFromObject(MovingObject movingObject) {
        double lineLength = Point2D.distance(posX.getValue(), posY.getValue(), movingObject.getPosX().getValue(), movingObject.getPosY().getValue());
        if (lineLength < movingObject.getAttractionDistance().getValue() && this != movingObject) {
            double force = GravityUtil.computeForceStrength(this, movingObject);
            applyForceToVelocity(force, movingObject.getPosX().getValue(), movingObject.getPosY().getValue());
        }
    }

    public void applyInitialVelocity() {
        if (mouvementEnum != null && mouvementEnum.equals(MouvementEnum.INITIAL_FORCE)) {
            velocityX = 0;
            velocityY = 0;
            double lineLength = Point2D.distance(posX.getValue(), posY.getValue(), initialDirectionX, initialDirectionY);
            double angle = getAngle(initialDirectionX, initialDirectionY);
            velocityX = velocityX + (Math.cos(Math.toRadians(angle)) * lineLength);
            velocityY = velocityY + (Math.sin(Math.toRadians(angle)) * lineLength);
        }
    }


    public void setInitialDirectionX(double x) {
        initialDirectionX = x;
    }

    public void setInitialDirectionY(double y) {
        initialDirectionY = y;
    }

    /**
     * Get Angle in Degree between this object and a target
     *
     * @param pPosX
     * @param pPosY
     * @return
     */
    public double getAngle(double pPosX, double pPosY) {
        double angle = (double) Math.toDegrees(Math.atan2(pPosY - posY.getValue(), pPosX - posX.getValue()));

        return angle;
    }

    public void applyForceToVelocity(double force, double targetX, double targetY) {
        double angle = getAngle(targetX, targetY);
        velocityX = velocityX + (Math.cos(Math.toRadians(angle)) * force) / Math.pow(getMassValue(), 2);
        velocityY = velocityY + (Math.sin(Math.toRadians(angle)) * force) / Math.pow(getMassValue(), 2);
    }

    public abstract double getMassValue();

    public abstract void fusion(MovingObject movingObject);

}
