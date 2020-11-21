package fr.slapker.bullet.launcher;


import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameParam {


    double timeReducerCoeff = 1;
    SimpleDoubleProperty G = new SimpleDoubleProperty(200);
    SimpleDoubleProperty radiusToMassUnity = new SimpleDoubleProperty(10);
    boolean applyGravity = true;
    boolean bulletFusion = true;
    boolean bulletCollideBorder = true;
    boolean generateBonus = false;
    boolean showAttractionCircle = false;
    double attractionRatio = 2;
    boolean showPlayer = true;
    boolean kidMode = true;

    private GameParam() {
    }

    private static GameParam INSTANCE = new GameParam();

    public static GameParam getInstance() {
        return INSTANCE;
    }
}
