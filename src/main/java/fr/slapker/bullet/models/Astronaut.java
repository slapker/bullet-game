package fr.slapker.bullet.models;

import fr.slapker.bullet.launcher.GameParam;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Astronaut implements Serializable {

    private SimpleDoubleProperty posX = new SimpleDoubleProperty();
    private SimpleDoubleProperty posY = new SimpleDoubleProperty();
    private SimpleDoubleProperty rotation = new SimpleDoubleProperty();
    private SimpleDoubleProperty velX = new SimpleDoubleProperty();
    private SimpleDoubleProperty velY = new SimpleDoubleProperty();
    private double rotationInertie;

    public Astronaut() {

    }

    public Astronaut(double pPosX, double pPosY) {
        posX.setValue(pPosX);
        posY.setValue(pPosY);
    }

    public void refreshPosition(double elapsedTime) {
        posX.setValue(posX.getValue()+velX.getValue()*elapsedTime*GameParam.getInstance().getTimeReducerCoeff());
        posY.setValue(posY.getValue()+velY.getValue()*elapsedTime*GameParam.getInstance().getTimeReducerCoeff());
        rotation.setValue(rotation.getValue()+rotationInertie*elapsedTime);
    }

}
