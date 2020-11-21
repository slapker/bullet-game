package fr.slapker.bullet.Level;

import fr.slapker.bullet.models.MouvementEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class LevelObject implements Serializable {
    double posX;
    double posY;
    boolean hasRotation;
    boolean clockWise;
    double rotationCenterPosX;
    double rotationCenterPosY;
    int speed = 10;
    MouvementEnum mouvementEnum;
    boolean rotationClockwise = true;
    double rotationSpeed;
    ItemLevelMakerEnum itemLevelMakerEnum;
    int nbMunitions;
    int durationSecondes;
    double initialDirectionX;
    double initialDirectionY;
    double initialForce;
    double bulletRadius;
}
