package fr.slapker.bullet.views;

import fr.slapker.bullet.constants.BulletScreenParam;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import fr.slapker.bullet.models.WeaponBullet;

@Getter
@Setter
public class WeaponBulletView extends Parent {
    private double radius;
    private Circle circle;
    protected double posX;
    protected double posY;
    private WeaponBullet weaponBullet;

    WeaponBulletView (WeaponBullet pWeaponBullet) {

        weaponBullet=pWeaponBullet;
        radius= BulletScreenParam.getInstance().getWeapon_bullet_radius();
        Color color = new Color(1,0.31,0,1);

        circle=new Circle();
        circle.centerXProperty().bind(weaponBullet.getPosX());
        circle.centerYProperty().bind(weaponBullet.getPosY());
        circle.setRadius(BulletScreenParam.getInstance().getWeapon_bullet_radius());
        circle.setFill(color);
        this.getChildren().add(circle);
    }
}
