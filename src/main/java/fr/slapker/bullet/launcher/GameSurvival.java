package fr.slapker.bullet.launcher;

import fr.slapker.bullet.models.BulletGenerator;

public class GameSurvival extends Game {

    public GameSurvival(GameModeEnum gameModeEnum) {
        super();
        GameParam.getInstance().setApplyGravity(false);
        GameParam.getInstance().setBulletFusion(false);
        GameParam.getInstance().setShowPlayer(true);
        GameParam.getInstance().setBulletCollideBorder(true);
        GameParam.getInstance().setShowAttractionCircle(false);
        GameModeParamLoader gameModeParamLoader = new GameModeParamLoader();
        this.gameModeParam = gameModeParamLoader.getGameMode(gameModeEnum);
        getPlayer().getMunitions().setValue(gameModeParam.getInitialMunitionAmount());
        GameParam.getInstance().setTimeReducerCoeff(1);
        GameParam.getInstance().setGenerateBonus(true);
        this.setMaxBonuses(5);
    }

    public void generateBullet() {
        for (int i = 0; i < gameModeParam.getBulletStartingNumber(); i++) {
            bulletList.add(BulletGenerator.makeBullet(gameModeParam.getBulletStartVelocity()));
        }
    }
}
