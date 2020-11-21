package fr.slapker.bullet.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsteroidExplosion extends MovingObject {

    Color color;

    public AsteroidExplosion(Bullet fromBullet) {

        posX = new SimpleDoubleProperty(fromBullet.getPosX().getValue());
        posY = new SimpleDoubleProperty(fromBullet.getPosY().getValue());
    }

    public double getMassValue() {
        return 0;
    }

    @Override
    public void fusion(MovingObject movingObject) {

    }
}
