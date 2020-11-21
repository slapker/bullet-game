package fr.slapker.bullet.views;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreView extends Parent {

    private SimpleIntegerProperty nbLives;
    private SimpleIntegerProperty nbBullets;
    private SimpleIntegerProperty score;
    private SimpleIntegerProperty munitions;
    Text nbBulletsText = new Text();
    Text nbLivesText = new Text();
    Text scoreText = new Text();
    Text munitionText = new Text();
    Text timeText = new Text();

    public ScoreView() {
        nbBullets = new SimpleIntegerProperty();
        nbBulletsText.setX(20);
        nbBulletsText.setY(20);
        nbBulletsText.setFont(Font.font ("Verdana", 20));
        nbBulletsText.setFill(Color.RED);
        nbBulletsText.setText("Balls Number : " + nbBullets.getValue().toString());

        nbLives = new SimpleIntegerProperty();
        nbLivesText.setX(400);
        nbLivesText.setY(20);
        nbLivesText.setFont(Font.font ("Verdana", 20));
        nbLivesText.setFill(Color.RED);
        nbLivesText.setText("Lives : " + nbLives.getValue().toString());

        score = new SimpleIntegerProperty();
        scoreText.setX(650);
        scoreText.setY(20);
        scoreText.setFont(Font.font ("Verdana", 20));
        scoreText.setFill(Color.RED);
        scoreText.setText("Score : " + score.getValue().toString());

        munitions = new SimpleIntegerProperty();
        munitionText.setX(850);
        munitionText.setY(20);
        munitionText.setFont(Font.font ("Verdana", 20));
        munitionText.setFill(Color.RED);
        munitionText.setText("Munitions : " + munitions.getValue().toString());

        this.getChildren().add(scoreText);
        this.getChildren().add(nbLivesText);
        this.getChildren().add(nbBulletsText);
        this.getChildren().add(munitionText);
    }

    public void redraw() {
        nbBulletsText.toFront();
        scoreText.setText("Score : " + score.getValue().toString());
        nbBulletsText.setText("Balls Number : " + nbBullets.getValue().toString());
        nbLivesText.setText("Lives : " + nbLives.getValue().toString());
        munitionText.setText("Munitions : " + munitions.getValue().toString());
    }
}
