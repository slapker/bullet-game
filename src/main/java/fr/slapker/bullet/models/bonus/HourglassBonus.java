package fr.slapker.bullet.models.bonus;

import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.Game;
import fr.slapker.bullet.launcher.GameParam;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HourglassBonus extends Bonus {

    private double reducingTimeCoeff=0.2d;
    private int durationSecond = 5;

    public HourglassBonus(double pPosX, double pPosY) {
        super(pPosX, pPosY);
    }

    public HourglassBonus(double pPosX, double pPosY, int pDurationSecond) {
        super(pPosX, pPosY);
        durationSecond=pDurationSecond;

    }

    @Override
    public String consumeBonus(Game game) {
        BulletGame.getGameController().getSoundMode().getSlowMoBonus().play();
        GameParam.getInstance().setTimeReducerCoeff(0.2);
        game.setStartBonusTimeReduceStart(LocalDateTime.now());
        game.setSlowMoDurationSeconds(durationSecond);
        BulletGame.getGameController().getSoundMode().enableSloMoSound();
        return "Slow motion !";
    }
}
