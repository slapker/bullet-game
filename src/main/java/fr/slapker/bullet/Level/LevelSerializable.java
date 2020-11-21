package fr.slapker.bullet.Level;

import fr.slapker.bullet.models.Astronaut;
import fr.slapker.bullet.models.Bullet;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.models.bonus.HourglassBonus;
import fr.slapker.bullet.models.bonus.MunitionsBonus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LevelSerializable implements Serializable {
    private List<LevelObject> astronautList = new ArrayList<>();
    private List<LevelObject> bulletList = new ArrayList<>();
    ;
    private List<LevelObject> bonusList = new ArrayList<>();

    public void addAstronaut(Astronaut astro) {
        LevelObject levelItem = new LevelObject();
        levelItem.setPosX(astro.getPosX().getValue());
        levelItem.setPosY(astro.getPosY().getValue());
        astronautList.add(levelItem);
    }

    public void addBullet(Bullet bullet) {
        LevelObject levelItem = new LevelObject();
        levelItem.setPosX(bullet.getPosX().getValue());
        levelItem.setPosY(bullet.getPosY().getValue());
        levelItem.setRotationCenterPosX(bullet.getRotationCenterX());
        levelItem.setRotationCenterPosY(bullet.getRotationCenterY());
        levelItem.setMouvementEnum(bullet.getMouvementEnum());
        levelItem.setRotationClockwise(bullet.isRotationClockwise());
        levelItem.setRotationSpeed(bullet.getRotationSpeed());
        levelItem.setInitialDirectionX(bullet.getInitialDirectionX());
        levelItem.setInitialDirectionY(bullet.getInitialDirectionY());
        levelItem.setBulletRadius(bullet.getBulletRadius().getValue());
        bulletList.add(levelItem);
    }

    public void addBonus(Bonus bonus) {
        LevelObject levelItem = new LevelObject();
        levelItem.setPosX(bonus.getPosX().getValue());
        levelItem.setPosY(bonus.getPosY().getValue());
        if (bonus instanceof HourglassBonus) {
            levelItem.setItemLevelMakerEnum(ItemLevelMakerEnum.BONUS_HOURGLASS);
            levelItem.setDurationSecondes(((HourglassBonus) bonus).getDurationSecond());
        } else if (bonus instanceof MunitionsBonus) {
            levelItem.setItemLevelMakerEnum(ItemLevelMakerEnum.BONUS_MUNITIONS);
            levelItem.setNbMunitions(((MunitionsBonus) bonus).getNbMunitions());
        }
        bonusList.add(levelItem);
    }
}
