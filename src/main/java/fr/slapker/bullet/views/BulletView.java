package fr.slapker.bullet.views;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.GameParam;
import fr.slapker.bullet.models.Bullet;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;


@Setter
public class BulletView extends Parent {

    @Getter
    private Circle circle;

    @Getter
    private Color color;
    Bullet bullet;

    @Getter
    private Circle rotationCircle;

    @Getter
    private Line rotationLine;

    @Getter
    private Line forceLine;

    @Getter
    private Circle attractionCirle;


    BulletView(Bullet pBullet) {
        bullet = pBullet;
        color = new Color(Math.random(), Math.random(), Math.random(), 1d);
        circle = new Circle();
        circle.centerXProperty().bind(bullet.getPosX());
        circle.centerYProperty().bind(bullet.getPosY());
        circle.radiusProperty().bind(bullet.getBulletRadius());
        circle.setFill(color);

        if (GameParam.getInstance().isShowAttractionCircle()) {
            attractionCirle = initializeAttractionCircle();
            this.getChildren().add(attractionCirle);
        }

        this.getChildren().add(circle);
    }

    BulletView(Bullet pBullet, boolean levelMakerMode) {
        bullet = pBullet;
        color = new Color(Math.random(), Math.random(), Math.random(), 1d);
        circle = new Circle();
        circle.centerXProperty().bind(bullet.getPosX());
        circle.centerYProperty().bind(bullet.getPosY());
        circle.radiusProperty().bind(bullet.getBulletRadius());
        circle.setFill(color);
        circle.setViewOrder(1);
        this.getChildren().add(circle);
    }

    private Circle initializeAttractionCircle() {
        Circle attractionCirle = new Circle();
        attractionCirle.centerXProperty().bind(bullet.getPosX());
        attractionCirle.centerYProperty().bind(bullet.getPosY());
        attractionCirle.radiusProperty().bind(bullet.getAttractionDistance());
        attractionCirle.setFill(Color.TRANSPARENT);
        attractionCirle.setStroke(Color.LIGHTGREY);
        attractionCirle.setStrokeWidth(0.5);
        attractionCirle.getStrokeDashArray().add(3d);
        return attractionCirle;
    }

    public void addAttractionCircleForLevelMaker() {
        attractionCirle = initializeAttractionCircle();
        ((Group) this.getParent()).getChildren().add(attractionCirle);
        attractionCirle.toBack();
    }

    public void addRotationGraph() {
        rotationLine = new Line();
        rotationCircle = new Circle();
        rotationCircle.setStroke(Color.GREEN);
        rotationCircle.setStrokeWidth(1);
        rotationCircle.setFill(Color.TRANSPARENT);
        rotationCircle.getStrokeDashArray().add(3d);
        rotationLine.setStroke(Color.GREEN);
        rotationLine.setStrokeWidth(1);
        rotationLine.getStrokeDashArray().add(30d);
        rotationLine.startXProperty().set(bullet.getPosX().getValue());
        rotationLine.startYProperty().set(bullet.getPosY().getValue());
        rotationLine.endXProperty().set(bullet.getRotationCenterX());
        rotationLine.endYProperty().set(bullet.getRotationCenterY());
        rotationCircle.setCenterX(bullet.getRotationCenterX());
        rotationCircle.setCenterY(bullet.getRotationCenterY());

        double lineLength = Point2D.distance(rotationLine.getStartX(), rotationLine.getStartY(), rotationLine.getEndX(), rotationLine.getEndY());
        rotationCircle.setRadius(lineLength);

        rotationCircle.setViewOrder(10);
        rotationLine.setViewOrder(10);
        ((Group) this.getParent()).getChildren().add(rotationLine);
        ((Group) this.getParent()).getChildren().add(rotationCircle);

        rotationCircle.toBack();
        rotationLine.toBack();

    }

    public void addForceGraph() {
        forceLine = new Line();
        forceLine.setStroke(Color.YELLOW);
        forceLine.setStrokeWidth(2);
        forceLine.getStrokeDashArray().add(7d);
        forceLine.startXProperty().set(bullet.getPosX().getValue());
        forceLine.startYProperty().set(bullet.getPosY().getValue());
        forceLine.endXProperty().set(bullet.getInitialDirectionX());
        forceLine.endYProperty().set(bullet.getInitialDirectionY());
        forceLine.setViewOrder(10);
        ((Group) this.getParent()).getChildren().add(forceLine);
        forceLine.toBack();
    }

    public void removeRotationGraphFromParent() {
        ((Group) this.getParent()).getChildren().remove(rotationLine);
        ((Group) this.getParent()).getChildren().remove(rotationCircle);
    }

    public void removeForceGraphFromParent() {
        ((Group) this.getParent()).getChildren().remove(forceLine);
    }

    public boolean checkHittingBorder() {
        if (circle.getCenterX() + circle.getRadius() > BulletScreenParam.getInstance().getWindowBulletMaxX()) {
            bullet.setVelocityX(-bullet.getVelocityX());
            bullet.getPosX().setValue(BulletScreenParam.getInstance().getWindowBulletMaxX() - circle.getRadius());
            return true;
        }
        if (circle.getCenterX() - circle.getRadius() < BulletScreenParam.getInstance().getWindowBulletX()) {
            bullet.getPosX().setValue(BulletScreenParam.getInstance().getWindowBulletX() + circle.getRadius());
            bullet.setVelocityX(-bullet.getVelocityX());
            return true;
        }
        if (circle.getCenterY() + circle.getRadius() > BulletScreenParam.getInstance().getWindowBulletMaxY()) {
            bullet.getPosY().setValue(BulletScreenParam.getInstance().getWindowBulletMaxY() - circle.getRadius());
            bullet.setVelocityY(-bullet.getVelocityY());
            return true;
        }
        if (circle.getCenterY() - circle.getRadius() < BulletScreenParam.getInstance().getWindowBulletY()) {
            bullet.getPosY().setValue(BulletScreenParam.getInstance().getWindowBulletX() + circle.getRadius());
            bullet.setVelocityY(-bullet.getVelocityY());
            return true;
        }
        return false;
    }

    public Bullet getBullet() {
        if (bullet == null) {
            System.out.println(("Bullet nulle !!"));
        }
        return bullet;

    }

    public Bounds getBounds() {
        return circle.getLayoutBounds();
    }


    public void removeAllGraph() {
        removeRotationGraphFromParent();
        removeForceGraphFromParent();
        ((Group) this.getParent()).getChildren().remove(attractionCirle);
    }
}
