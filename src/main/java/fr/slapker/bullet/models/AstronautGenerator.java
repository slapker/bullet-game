package fr.slapker.bullet.models;

import fr.slapker.bullet.constants.BulletScreenParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class AstronautGenerator {

    public Astronaut makeAstronaut() {
        Astronaut astronaut = new Astronaut();
        Random r = new Random();
        double defaultInertie = 1+r.nextInt(70);
        if (defaultInertie%2==0) {
            defaultInertie=-defaultInertie;
        }
        astronaut.setRotationInertie(defaultInertie);
        astronaut.getVelX().setValue(r.nextInt(10));
        astronaut.getVelY().setValue(r.nextInt(10));

        double randomPosX = BulletScreenParam.getInstance().getWindowBulletX() + r.nextInt(BulletScreenParam.getInstance().getWindowBulletWidth());
        double randomPosY = BulletScreenParam.getInstance().getWindowBulletY() + r.nextInt(BulletScreenParam.getInstance().getWindowBulletHeight());

        astronaut.getPosX().setValue(randomPosX);
        astronaut.getPosY().setValue(randomPosY);
        return astronaut;
    }
}
