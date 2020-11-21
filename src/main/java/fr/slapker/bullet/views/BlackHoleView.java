package fr.slapker.bullet.views;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.models.BlackHole;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class BlackHoleView extends Parent {
    @Getter
    private Circle circle;

    @Getter
    private BlackHole blackHole;

    @Getter
    private ImageView imageView;

    @Getter
    private Image imageBlackHole;

    private double initialX;
    private double initialY;

    private double normalSize = 200 * BulletScreenParam.getInstance().getScreenRatioX();

    public BlackHoleView(BlackHole pBlackHole) {

        initialX = pBlackHole.getPosX().getValue();
        initialY = pBlackHole.getPosY().getValue();
        imageBlackHole = new Image(getClass().getClassLoader().getResource("images/blackHole.png").toExternalForm(), normalSize, normalSize, false, false);
        blackHole = pBlackHole;
        imageView = new ImageView(imageBlackHole);
        imageView.setX(initialX);
        imageView.setY(initialY);
        imageView.setFitHeight(0);
        imageView.setFitWidth(0);
        circle = new Circle();
        circle.setCenterX(blackHole.getPosX().getValue());
        circle.setCenterY(blackHole.getPosY().getValue());
        circle.setRadius(40);
        circle.setFill(Color.TRANSPARENT);
        this.getChildren().add(circle);
        this.getChildren().add(imageView);
    }

    public void refreshBhView() {
        imageView.setRotate(imageView.getRotate() - 4d);
        if (!blackHole.isDying()) {

            if (imageView.getFitWidth() < normalSize) {
                imageView.setFitWidth(imageView.getFitWidth() + 3);
                imageView.setFitHeight(imageView.getFitHeight() + 3);
            }
        } else {
            if (imageView.getFitWidth() > 1) {
                double newImageWidth = imageView.getFitWidth() - 3;
                double newImageHeight = imageView.getFitHeight() - 3;
                if (newImageWidth < 1) {
                    imageView.setFitWidth(1);
                } else {
                    blackHole.getBulletRadius().setValue(blackHole.getBulletRadius().getValue() / 1.5d);
                    imageView.setFitWidth(newImageWidth);
                }
                if (newImageHeight < 1) {
                    imageView.setFitHeight(imageView.getFitHeight() - 3);
                } else {
                    imageView.setFitHeight(newImageHeight);
                }
            } else if (imageView.getFitWidth() <= 1) {
                blackHole.setShouldRemove(true);
            }
        }
        imageView.setX(initialX - imageView.getFitWidth() / 2);
        imageView.setY(initialY - imageView.getFitHeight() / 2);

    }

}
