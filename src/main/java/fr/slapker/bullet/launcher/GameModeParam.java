package fr.slapker.bullet.launcher;

import fr.slapker.bullet.constants.BulletScreenParam;
import lombok.Getter;
import lombok.Setter;


@Setter
public class GameModeParam {
    @Getter
    private int bulletMaxOnScreen;
    @Getter
    private int bulletStartingNumber;
    private int bulletStartVelocity;
    @Getter
    private int initialMunitionAmount;

    public int getBulletStartVelocity() {
        double velocity = bulletStartVelocity* BulletScreenParam.getInstance().getScreenRatioY();
        return Math.toIntExact(Math.round(velocity));
    }

}
