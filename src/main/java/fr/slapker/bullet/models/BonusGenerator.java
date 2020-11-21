package fr.slapker.bullet.models;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.models.bonus.BlackHoleBonus;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.models.bonus.HourglassBonus;
import fr.slapker.bullet.models.bonus.MunitionsBonus;

import java.util.Random;

public class BonusGenerator {

    public Bonus makeBonus() {
        Random random = new Random();
        double posX = BulletScreenParam.getInstance().getWindowBulletX() + random.nextInt(BulletScreenParam.getInstance().getWindowBulletWidth());
        double posY = BulletScreenParam.getInstance().getWindowBulletY() + random.nextInt(BulletScreenParam.getInstance().getWindowBulletHeight());

        if (random.nextInt(10) % 4 == 0) {
            return new HourglassBonus(posX, posY);
        } else {
            if (random.nextInt(3) % 3 == 0 && BulletGame.getGameController().getGame().getBlackHoles().isEmpty() && !isBonusTypeAlreadyInGame(BlackHoleBonus.class)) {
                return new BlackHoleBonus(posX, posY);
            } else {
                return new MunitionsBonus(posX, posY);

            }
        }
    }

    public boolean isBonusTypeAlreadyInGame(Class<?> bonusClass) {
        Bonus bonus = BulletGame.getGameController().getGame().getBonuses().stream().filter(bonusTmp -> bonusTmp.getClass() == bonusClass).findFirst().orElse(null);
        if (bonus != null) {
            return true;
        }
        return false;
    }

}
