package fr.slapker.bullet.views;

import fr.slapker.bullet.models.Player;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerView extends Parent {
    private Player player;
    private SimpleDoubleProperty posX = new SimpleDoubleProperty();
    private SimpleDoubleProperty posY = new SimpleDoubleProperty();
    private SimpleDoubleProperty rotation = new SimpleDoubleProperty();
    private Bounds bounds;
    private Circle boundsCircle;
    private ImageView shipImageView;

    public PlayerView(Player pPlayer) {
        player = pPlayer;
        shipImageView = new ImageView();
        shipImageView.setImage(player.getShipImageOff());
        shipImageView.setFitWidth(player.getShipImage().getWidth());
        shipImageView.setFitHeight(player.getShipImage().getHeight());
        shipImageView.xProperty().bind(player.getPosX());
        shipImageView.yProperty().bind(player.getPosY());
        shipImageView.rotateProperty().bind((player.getRotation()));

        boundsCircle = new Circle();
        boundsCircle.setRadius(shipImageView.getFitWidth() / 3);
        boundsCircle.setVisible(false);


        shipImageView.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                boundsCircle.setCenterX(t1.getCenterX());
                boundsCircle.setCenterY(t1.getCenterY());
            }
        });

        this.getChildren().add(boundsCircle);
        this.getChildren().add(shipImageView);
    }

    public void loadImage(Image image) {
        shipImageView.setImage(image);
        shipImageView.setFitWidth(image.getWidth());
        shipImageView.setFitHeight(image.getHeight());
    }

    public Bounds getBounds() {
        return boundsCircle.getLayoutBounds();
    }


}
