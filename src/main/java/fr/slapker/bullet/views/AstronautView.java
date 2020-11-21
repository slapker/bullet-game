package fr.slapker.bullet.views;

import fr.slapker.bullet.constants.BulletScreenParam;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;
import fr.slapker.bullet.models.Astronaut;

@Setter
@Getter
public class AstronautView extends Parent {

    private double screenRatioY = BulletScreenParam.getInstance().getScreenRatioY();
    private Image image = new Image(getClass().getClassLoader().getResource("images/astronaut.png").toExternalForm(), 45*screenRatioY, 45*screenRatioY, false, false);

    private SimpleDoubleProperty posX = new SimpleDoubleProperty();
    private SimpleDoubleProperty posY = new SimpleDoubleProperty();
    private SimpleDoubleProperty rotation = new SimpleDoubleProperty();
    private ImageView astronautImageView;
    private Astronaut astronaut;
    private Circle boundsCircle;

    public AstronautView(Astronaut pAstronaut) {
        astronaut=pAstronaut;
        astronautImageView = new ImageView();
        astronautImageView.setImage(image);
        astronautImageView.setCacheHint(CacheHint.ROTATE);
        astronautImageView.setCache(true);
        astronautImageView.setFitWidth(image.getWidth());
        astronautImageView.setFitHeight(image.getHeight());
        astronautImageView.xProperty().bind(astronaut.getPosX());
        astronautImageView.yProperty().bind(astronaut.getPosY());
        astronautImageView.rotateProperty().bind((astronaut.getRotation()));
        astronautImageView.setSmooth(true);
        astronautImageView.setPreserveRatio(false);

        //Setting Bound for collides
        boundsCircle = new Circle();
        boundsCircle.setRadius(astronautImageView.getFitWidth()/3);
        boundsCircle.setVisible(false);
        //boundsCircle.setFill(Color.RED);
        boundsCircle.setCenterX(astronautImageView.getLayoutBounds().getCenterX());
        boundsCircle.setCenterY(astronautImageView.getLayoutBounds().getCenterY());
        astronautImageView.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                boundsCircle.setCenterX(t1.getCenterX());
                boundsCircle.setCenterY(t1.getCenterY());
            }
        });

        this.getChildren().add(boundsCircle);
        this.getChildren().add(astronautImageView);
    }

    public void loadImage(Image image) {
        astronautImageView.setImage(image);
        astronautImageView.setFitWidth(image.getWidth());
        astronautImageView.setFitHeight(image.getHeight());
    }

    public Bounds getBounds() {
        return boundsCircle.getBoundsInParent();
    }
}
