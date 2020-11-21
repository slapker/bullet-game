package fr.slapker.bullet.views;

import fr.slapker.bullet.constants.BulletScreenParam;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class BulletPlaygroundView extends Parent {

    public BulletPlaygroundView() {
        Rectangle rectangle = new Rectangle(BulletScreenParam.getInstance().getWindowBulletX(),BulletScreenParam.getInstance().getWindowBulletY(),BulletScreenParam.getInstance().getWindow_width(),BulletScreenParam.getInstance().getWindowBulletHeight());
        rectangle.setFill(Color.BLACK);
        Line borderLeft = new Line(BulletScreenParam.getInstance().getWindowBulletX(),BulletScreenParam.getInstance().getWindowBulletY(),BulletScreenParam.getInstance().getWindowBulletX(),BulletScreenParam.getInstance().getWindowBulletMaxY());
        borderLeft.setStroke(Color.GREEN);
        borderLeft.setStrokeWidth(2);
        Line borderTop = new Line(BulletScreenParam.getInstance().getWindowBulletX(),BulletScreenParam.getInstance().getWindowBulletY(),BulletScreenParam.getInstance().getWindowBulletMaxX(),BulletScreenParam.getInstance().getWindowBulletY());
        borderTop.setStroke(Color.GREEN);
        borderTop.setStrokeWidth(2);
        Line borderRight = new Line(BulletScreenParam.getInstance().getWindowBulletMaxX(),BulletScreenParam.getInstance().getWindowBulletY(),BulletScreenParam.getInstance().getWindowBulletMaxX(),BulletScreenParam.getInstance().getWindowBulletMaxY());
        borderRight.setStroke(Color.GREEN);
        borderRight.setStrokeWidth(2);
        Line borderBottom = new Line(BulletScreenParam.getInstance().getWindowBulletX(),BulletScreenParam.getInstance().getWindowBulletMaxY(),BulletScreenParam.getInstance().getWindowBulletMaxX(),BulletScreenParam.getInstance().getWindowBulletMaxY());
        borderBottom.setStroke(Color.GREEN);
        borderBottom.setStrokeWidth(2);
        this.getChildren().add(rectangle);
        this.getChildren().add(borderLeft);
        this.getChildren().add(borderTop);
        this.getChildren().add(borderRight);
        this.getChildren().add(borderBottom);
    }
}
