package fr.slapker.bullet.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BlackHole extends MovingObject {

    private LocalDateTime creationTime;
    private int durationInSeconds = 5;
    private boolean shouldRemove = false;
    private boolean isDying;

    public BlackHole(double pPosX, double pPosy) {
        posX.setValue(pPosX);
        posY.setValue(pPosy);
        creationTime = LocalDateTime.now();
        bulletRadius.setValue(50);
    }

    @Override
    public double getMassValue() {
        return mass.getValue();
    }

    @Override
    public void fusion(MovingObject movingObject) {

    }

    @Override
    public void applyForceToVelocity(double force, double targetX, double targetY) {
        //Do nothing !
    }
}
