package fr.slapker.bullet.controller;

import fr.slapker.bullet.Level.ItemLevelMakerEnum;
import fr.slapker.bullet.Level.LevelMaker;
import fr.slapker.bullet.Level.LevelMakerActionEnum;
import fr.slapker.bullet.models.MouvementEnum;
import fr.slapker.bullet.views.BulletView;
import fr.slapker.bullet.views.LevelMakerView;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.List;

public class MouseLevelMakerController {

    private ArrayList<String> inputs = new ArrayList<String>();

    public List<String> initializeControls(Scene theScene, LevelMaker levelMaker, LevelMakerView levelMakerView) {
        theScene.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (levelMaker.getActionMakerEnum() != null) {
                    if (levelMaker.getActionMakerEnum().equals(LevelMakerActionEnum.ROTATION)) {
                        levelMaker.getSelectedBullet().setRotationCenterX(levelMaker.getActualMouseX());
                        levelMaker.getSelectedBullet().setRotationCenterY(levelMaker.getActualMouseY());
                        levelMaker.getSelectedBullet().setMouvementEnum(MouvementEnum.ROTATION);
                        BulletView actualBulletView = levelMakerView.getBulletViews().stream().filter(bullet -> bullet.getBullet() == levelMaker.getSelectedBullet()).findFirst().get();
                        actualBulletView.addRotationGraph();
                        levelMakerView.removeActualRotationGraph();

                    }
                    if (levelMaker.getActionMakerEnum().equals(LevelMakerActionEnum.INITIAL_FORCE)) {
                        levelMaker.getSelectedBullet().setInitialDirectionX(levelMaker.getActualMouseX());
                        levelMaker.getSelectedBullet().setInitialDirectionY(levelMaker.getActualMouseY());
                        levelMaker.getSelectedBullet().setMouvementEnum(MouvementEnum.INITIAL_FORCE);
                        BulletView actualBulletView = levelMakerView.getBulletViews().stream().filter(bullet -> bullet.getBullet() == levelMaker.getSelectedBullet()).findFirst().get();
                        actualBulletView.addForceGraph();
                        levelMakerView.removeActualForceGraph();
                    }
                    levelMaker.setActionMakerEnum(null);
                    theScene.setOnMouseMoved(null);
                } else {
                    if (levelMaker.getItemLevelMakerEnum() != null) {
                        if (levelMaker.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BULLET)) {
                            levelMaker.addBullet(e.getX(), e.getY());
                        } else if (levelMaker.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.ASTRONAUT)) {
                            levelMaker.addAstro(e.getX(), e.getY());
                        } else if (levelMaker.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BONUS_HOURGLASS)) {
                            levelMaker.addBonusHourGlass(e.getX(), e.getY());
                        } else if (levelMaker.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BONUS_MUNITIONS)) {
                            levelMaker.addMunitionBonus(e.getX(), e.getY());
                        }
                    }
                }
            } else if (e.getButton() == MouseButton.SECONDARY) {

            } else if (e.getButton() == MouseButton.MIDDLE) {
                //LevelSerializer.serializeLevel(levelMaker);
            }
        });

        theScene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            inputs.remove(code);
        });

        return inputs;
    }
}
