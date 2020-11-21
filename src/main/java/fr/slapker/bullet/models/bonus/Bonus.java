package fr.slapker.bullet.models.bonus;

import fr.slapker.bullet.launcher.Game;
import javafx.beans.property.SimpleDoubleProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Bonus {
    protected SimpleDoubleProperty posX ;
    protected SimpleDoubleProperty posY;

    public Bonus(double pPosX, double pPosY) {
        posX = new SimpleDoubleProperty(pPosX);;
        posY = new SimpleDoubleProperty(pPosY);;
    }

    public abstract String consumeBonus(Game game);
}
