package fr.slapker.bullet.views.bonus;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.models.bonus.Bonus;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractBonusView extends Parent {

    protected double screenRatio = BulletScreenParam.getInstance().getScreenRatioY();
    protected Circle circle;
    protected Image imageBonus;
    protected ImageView imageView;
    protected double initialWidth = 50 * screenRatio;
    protected double initialHeight = 50 * screenRatio;
    protected boolean upSizingImage = true;
    protected static double COEFF_RESIZING = 1.1;
    protected static Integer VELOCITY_RESIZING = 10;
    protected static double OPACITY_VELOCITY = 0.7;
    protected static double CIRCLE_OPACITY_MAX = 0.6;
    protected static double CIRCLE_OPACITY_MIN = 0.2;
    protected Bonus bonus;
    protected double image_size = 50 * screenRatio;
    protected double radiusCircle = (image_size / 2) + 5;

    public AbstractBonusView(Bonus bonus) {


        imageView = new ImageView();
        imageView.setFitWidth(initialWidth);
        imageView.setFitHeight(initialHeight);
        imageView.xProperty().bind(bonus.getPosX());
        imageView.yProperty().bind(bonus.getPosY());
        circle = new Circle();
        circle.setCenterX(bonus.getPosX().getValue());
        circle.setCenterY(bonus.getPosY().getValue());
        circle.setStrokeWidth(5);
        circle.setStroke(Color.YELLOW);
        circle.setRadius(radiusCircle);
        circle.setFill(new Color(1, 1, 1, 0));
        circle.setOpacity(CIRCLE_OPACITY_MAX);

        circle.setCenterX(imageView.getLayoutBounds().getCenterX());
        circle.setCenterY(imageView.getLayoutBounds().getCenterY());
        this.getChildren().add(circle);
        this.getChildren().add(imageView);
    }

    public void refreshImage(double elapsedTime) {
/*        if (imageView.getFitWidth() > initialWidth * COEFF_RESIZING*5) {
            imageView.setFitWidth(initialWidth);
            imageView.setFitHeight(initialHeight);
            circle.setRadius(radiusCircle);
        }*/
        circle.setCenterX(imageView.getLayoutBounds().getCenterX());
        circle.setCenterY(imageView.getLayoutBounds().getCenterY());
        if (upSizingImage) {
            if (imageView.getFitWidth() < initialWidth * COEFF_RESIZING
                    && (imageView.getFitWidth() + (VELOCITY_RESIZING * elapsedTime) <= initialWidth * COEFF_RESIZING)) {

                imageView.setFitWidth(imageView.getFitWidth() + (VELOCITY_RESIZING * elapsedTime));
                imageView.setFitHeight(imageView.getFitHeight() + (VELOCITY_RESIZING * elapsedTime));
                circle.setRadius(circle.getRadius() + (VELOCITY_RESIZING * elapsedTime));
                double opacity = circle.getOpacity() + OPACITY_VELOCITY * elapsedTime;
                if (opacity <= CIRCLE_OPACITY_MAX) {
                    circle.setOpacity(opacity);
                }
            } else {
                upSizingImage = false;
            }
        }

        if (!upSizingImage) {
            if (imageView.getFitWidth() > initialWidth * (1 / COEFF_RESIZING)
                    && (imageView.getFitWidth() - (VELOCITY_RESIZING * elapsedTime) >= initialWidth * (1 / COEFF_RESIZING))) {
                imageView.setFitWidth(imageView.getFitWidth() - (VELOCITY_RESIZING * elapsedTime));
                imageView.setFitHeight(imageView.getFitHeight() - (VELOCITY_RESIZING * elapsedTime));
                circle.setRadius(circle.getRadius() - (VELOCITY_RESIZING * elapsedTime));
                double opacity = circle.getOpacity() - OPACITY_VELOCITY * elapsedTime;
                if (opacity >= CIRCLE_OPACITY_MIN) {
                    circle.setOpacity(opacity);
                }
            } else {
                upSizingImage = true;
            }
        }
    }
}
