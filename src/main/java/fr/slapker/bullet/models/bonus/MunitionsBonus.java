package fr.slapker.bullet.models.bonus;

import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.Game;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MunitionsBonus extends Bonus {


    private Integer nbMunitions = 30;

    public MunitionsBonus(double pPosX, double pPosY) {

        super(pPosX, pPosY);
    }

    public MunitionsBonus(double pPosX, double pPosY,int pNbMunitions) {
        super(pPosX, pPosY);
        nbMunitions=pNbMunitions;
    }

    @Override
    public String consumeBonus(Game game) {
        BulletGame.getGameController().getSoundMode().getWeaponBonus().play();
        game.getPlayer().getMunitions().setValue(game.getPlayer().getMunitions().getValue()+ nbMunitions);
        game.setStartBonusTimeReduceStart(LocalDateTime.now());
        return "+ " + nbMunitions + " munitions !";
    }
}
