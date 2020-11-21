package fr.slapker.bullet.views;

import fr.slapker.bullet.launcher.GameParam;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class ExplosionParticuleView extends Parent {
    private Circle circle;
    private double posX;
    private double posY;
    private double velX;
    private double velY;
    private double opacityParticule = 1;
    private static Integer MIN_VELOCITY_VALUE = 600;
    private Color color;

    public ExplosionParticuleView(double posX, double posY, Color pColor, double bulletRadius) {
        this.posX = posX;
        this.posY = posY;
        color = pColor;
        circle = new Circle();
        circle.setCenterX(posX);
        circle.setCenterY(posY);
        circle.setRadius(bulletRadius / 20);
        circle.setFill(color);

        Random r = new Random();

        velX = r.nextInt(500);
        if (velX % 2 == 0) {
            velX = -velX;
        }

        velY = r.nextInt(500);
        if (velY % 2 == 0) {
            velY = -velY;
        }
        this.getChildren().add(circle);
    }


    public void move(double elapsedTime) {
        posX = posX + velX * elapsedTime * GameParam.getInstance().getTimeReducerCoeff();
        posY = posY + velY * elapsedTime * GameParam.getInstance().getTimeReducerCoeff();
        circle.setCenterX(posX);
        circle.setCenterY(posY);
        circle.setOpacity(opacityParticule);
    }
}
