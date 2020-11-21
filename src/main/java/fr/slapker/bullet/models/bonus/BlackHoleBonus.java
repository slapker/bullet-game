package fr.slapker.bullet.models.bonus;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.Game;
import fr.slapker.bullet.models.BlackHole;

import java.util.Random;

public class BlackHoleBonus extends Bonus {


    public BlackHoleBonus(double pPosX, double pPosY) {
        super(pPosX, pPosY);
    }

    @Override
    public String consumeBonus(Game game) {
        Random random = new Random();
        double posX = BulletScreenParam.getInstance().getWindowBulletX() + 200 + random.nextInt(BulletScreenParam.getInstance().getWindowBulletWidth() - 400);
        double posY = BulletScreenParam.getInstance().getWindowBulletY() + 200 + random.nextInt(BulletScreenParam.getInstance().getWindowBulletHeight() - 400);
        BlackHole blackHole = new BlackHole(posX, posY);
        BulletGame.getGameController().getGame().getBlackHoles().add(blackHole);
        return "Black Hole !";
    }


}
