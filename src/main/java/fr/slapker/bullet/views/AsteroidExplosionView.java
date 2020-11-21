package fr.slapker.bullet.views;

import fr.slapker.bullet.launcher.GameParam;
import fr.slapker.bullet.models.Bullet;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class AsteroidExplosionView extends Parent {
    private List<ExplosionParticuleView> particules = new ArrayList<>();
    private static Integer MIN_PARTICULES_NUMBER = 10;
    private static double OPACITY_VELOCITY = 1;
    private static double ONDE_CHOC_OPACITY_START = 0.5;
    private Bullet bullet;
    private Circle ondeDeChoc;
    private double ondeDeChocColorOpacity;
    private double opacityParticule = 1;
    private static Integer VELOCITY_ONDE_CHOC = 200;
    private static final double OPACITY_PARTICULE_VELOCITY = 0.5;

    public AsteroidExplosionView(Bullet pBullet, Color color) {
        ondeDeChocColorOpacity = ONDE_CHOC_OPACITY_START;
        bullet = pBullet;
        Random r = new Random();
        Integer particulesNumberMax = Math.toIntExact(Math.round(bullet.getBulletRadius().getValue()));
        Integer randomPart = r.nextInt(particulesNumberMax + 1);
        Integer nbParticules = MIN_PARTICULES_NUMBER + randomPart;
        ondeDeChoc = new Circle();
        ondeDeChoc.setCenterX(pBullet.getPosX().getValue());
        ondeDeChoc.setCenterY(pBullet.getPosY().getValue());
        ondeDeChoc.setStrokeWidth(bullet.getBulletRadius().getValue() / 10);
        ondeDeChoc.setFill(new Color(1, 1, 1, 0));
        ondeDeChoc.setStroke(new Color(1, 1, 1, ONDE_CHOC_OPACITY_START));
        ondeDeChoc.setRadius(bullet.getBulletRadius().getValue() / 20);
        this.getChildren().add(ondeDeChoc);
        for (int i = 0; i < nbParticules; i++) {
            ExplosionParticuleView partExpl = new ExplosionParticuleView(pBullet.getPosX().getValue(), pBullet.getPosY().getValue(), color, bullet.getBulletRadius().getValue());
            particules.add(partExpl);
            this.getChildren().add(partExpl);
        }
    }

    public void refreshExplosiontView(double elapsedTime) {
        opacityParticule = opacityParticule - elapsedTime * OPACITY_PARTICULE_VELOCITY * GameParam.getInstance().getTimeReducerCoeff();
        if (opacityParticule < 0) {
            opacityParticule = 0;
        }
        particules.forEach(particules -> {
            particules.setOpacityParticule(opacityParticule);
            particules.move(elapsedTime);
        });

        ondeDeChocColorOpacity = ondeDeChocColorOpacity - elapsedTime * OPACITY_VELOCITY * GameParam.getInstance().getTimeReducerCoeff();
        if (ondeDeChocColorOpacity < 0) {
            ondeDeChocColorOpacity = 0;
        }
        ondeDeChoc.setRadius(ondeDeChoc.getRadius() + VELOCITY_ONDE_CHOC * GameParam.getInstance().getTimeReducerCoeff() * elapsedTime);
        ondeDeChoc.setOpacity(ondeDeChocColorOpacity);
    }
}
