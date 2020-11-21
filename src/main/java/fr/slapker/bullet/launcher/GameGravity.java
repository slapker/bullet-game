package fr.slapker.bullet.launcher;

import fr.slapker.bullet.models.BulletGenerator;

public class GameGravity extends Game {

    public GameGravity() {
        super();
        GameParam.getInstance().setApplyGravity(true);
        GameParam.getInstance().setBulletFusion(true);
        GameParam.getInstance().setGenerateBonus(false);
        GameParam.getInstance().setBulletCollideBorder(false);
        GameParam.getInstance().setShowAttractionCircle(false);
        GameParam.getInstance().setShowPlayer(false);
        gameMode = GameModeEnum.GRAVITY;
    }

    public void generateBullet(int nbStars, int maxStarSize) {
        for (int i = 0; i < nbStars; i++) {
            bulletList.add(BulletGenerator.makeGravityBullet(maxStarSize));
        }
    }
}
