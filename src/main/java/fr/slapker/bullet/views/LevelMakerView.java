package fr.slapker.bullet.views;

import fr.slapker.bullet.Level.*;
import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.GameParam;
import fr.slapker.bullet.models.Astronaut;
import fr.slapker.bullet.models.Bullet;
import fr.slapker.bullet.models.MouvementEnum;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.models.bonus.HourglassBonus;
import fr.slapker.bullet.models.bonus.MunitionsBonus;
import fr.slapker.bullet.views.bonus.AbstractBonusView;
import fr.slapker.bullet.views.bonus.HourglassBonusView;
import fr.slapker.bullet.views.bonus.MunitionsBonusView;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LevelMakerView {
    private Group root;
    private Scene theScene;
    private LevelMaker levelMaker;
    private LevelMakerActionEnum levelMakerActionEnum;
    private List<BulletView> bulletViews = new ArrayList<>();
    private List<AstronautView> astronautViews = new ArrayList<>();
    private List<AbstractBonusView> bonusViews = new ArrayList<>();
    private Image image = new Image(getClass().getClassLoader().getResource("images/astronaut.png").toExternalForm(), 45, 45, false, false);
    private Image imageHourGlass = new Image(getClass().getClassLoader().getResource("images/hourglass.png").toExternalForm(), 50, 50, false, false);
    private Image imageMunition = new Image(getClass().getClassLoader().getResource("images/bonus_munition.png").toExternalForm(), 50, 50, false, false);
    private ImageView astronautImageView;
    private ImageView hourGlassImageView;
    private ImageView munitionImageView;
    private Line actualGraphLine;
    private Circle actualRotationCircle;

    public LevelMakerView(Group pRoot, LevelMaker pLevelMaker) {
        root = pRoot;
        levelMaker = pLevelMaker;
        initializeBulletViewsList();
        initializeAstronautsViewList();
        initializeBonusViewList();
        initializeItemSelector();
        createBtns();

    }

/*
    public void putToFront() {

        root.getChildren().forEach(children -> {
            if (children instanceof BulletView) {
                ((BulletView) children).getCircle().setViewOrder(-1);
                ((BulletView) children).getRotationCircle().setViewOrder(99);
                ((BulletView) children).getRotationCircle().toBack();
            }
            if (children instanceof  AstronautView) {
                ((AstronautView) children).getAstronautImageView().setViewOrder(-2);
                ((AstronautView) children).getAstronautImageView().toFront();
            }
        });

        astronautViews.forEach(atro -> {
            atro.getAstronautImageView().toFront();
        });

        bulletViews.forEach(bullet -> {
            bullet.getCircle().toFront();
            if (bullet.getRotationLine() != null && bullet.getRotationCircle() != null) {
                bullet.getRotationCircle().toBack();
                bullet.getRotationLine().toBack();
            }
        });
    }
*/

    private void initializeItemSelector() {

        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(80);
        rectangle.setWidth(300);
        rectangle.setX(30);
        rectangle.setY(30);
        rectangle.setStroke(Color.GREEN);
        rectangle.setFill(Color.TRANSPARENT);
        root.getChildren().add(rectangle);

        astronautImageView = new ImageView();
        astronautImageView.setImage(image);
        astronautImageView.setFitWidth(image.getWidth());
        astronautImageView.setFitHeight(image.getHeight());
        astronautImageView.setX(40);
        astronautImageView.setY(40);

        astronautImageView.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                levelMaker.setItemLevelMakerEnum(ItemLevelMakerEnum.ASTRONAUT);
                e.consume();
            }
        });

        Circle circle = new Circle();
        circle.setCenterX(150);
        circle.setCenterY(65);
        circle.setRadius(BulletScreenParam.getInstance().getBulletStartingRadiusSurivalMode());
        circle.setFill(Color.RED);
        circle.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                levelMaker.setItemLevelMakerEnum(ItemLevelMakerEnum.BULLET);
                e.consume();
            }
        });

        hourGlassImageView = new ImageView();
        hourGlassImageView.setImage(imageHourGlass);
        hourGlassImageView.setFitWidth(image.getWidth());
        hourGlassImageView.setFitHeight(image.getHeight());
        hourGlassImageView.setX(200);
        hourGlassImageView.setY(40);

        hourGlassImageView.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {

                levelMaker.setItemLevelMakerEnum(ItemLevelMakerEnum.BONUS_HOURGLASS);
                e.consume();
            }
        });

        munitionImageView = new ImageView();
        munitionImageView.setImage(imageMunition);
        munitionImageView.setFitWidth(image.getWidth());
        munitionImageView.setFitHeight(image.getHeight());
        munitionImageView.setX(260);
        munitionImageView.setY(40);

        munitionImageView.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                levelMaker.setItemLevelMakerEnum(ItemLevelMakerEnum.BONUS_MUNITIONS);
                e.consume();
            }
        });

        root.getChildren().add(circle);
        root.getChildren().add(astronautImageView);
        root.getChildren().add(hourGlassImageView);
        root.getChildren().add(munitionImageView);
    }

    private void initializeBulletViewsList() {
        levelMaker.getBulletList().addListener(new ListChangeListener<Bullet>() {
            @Override
            public void onChanged(Change<? extends Bullet> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        change.getAddedSubList().forEach(bullet -> {
                            BulletView bulletView = new BulletView(bullet, true);
                            bulletViews.add(bulletView);
                            createContextMenu(bulletView);
                            if (!root.getChildren().contains(bulletView)) {
                                root.getChildren().add(bulletView);
                                if (GameParam.getInstance().isShowAttractionCircle()) {
                                    bulletView.addAttractionCircleForLevelMaker();
                                }
                                if (bulletView.getBullet().getMouvementEnum() != null && bulletView.getBullet().getMouvementEnum().equals(MouvementEnum.ROTATION)) {
                                    bulletView.addRotationGraph();
                                } else if (bulletView.getBullet().getMouvementEnum() != null && bulletView.getBullet().getMouvementEnum().equals(MouvementEnum.INITIAL_FORCE)) {
                                    bulletView.addForceGraph();
                                }
                            }
                        });
                    }

                    if (change.wasRemoved()) {
                        List<BulletView> bulletsViewsToRemove = new ArrayList<>();
                        change.getRemoved().forEach(bullet -> {
                            bulletViews.forEach(tmpBulletView -> {
                                if (tmpBulletView.getBullet().equals(bullet)) {
                                    bulletsViewsToRemove.add(tmpBulletView);
                                    tmpBulletView.removeAllGraph();
                                    root.getChildren().remove(tmpBulletView);
                                }
                            });
                        });
                        bulletViews.removeAll(bulletsViewsToRemove);


                    }
                }
            }
        });
    }


    private void initializeAstronautsViewList() {
        levelMaker.getAstroList().addListener(new ListChangeListener<Astronaut>() {
            @Override
            public void onChanged(Change<? extends Astronaut> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        change.getAddedSubList().forEach(astronaut -> {
                            AstronautView astronautView = new AstronautView(astronaut);
                            astronautViews.add(astronautView);
                            createContextMenu(astronautView);
                            if (!root.getChildren().contains(astronautView)) {
                                root.getChildren().add(astronautView);
                            }
                        });
                    }
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(astronaut -> {
                            List<AstronautView> astronautViewsToRemove = new ArrayList<>();
                            astronautViews.forEach(tmpAstroView -> {
                                if (tmpAstroView.getAstronaut().equals(astronaut)) {
                                    astronautViewsToRemove.add(tmpAstroView);
                                    root.getChildren().remove(tmpAstroView);
                                }
                            });
                            astronautViews.removeAll(astronautViewsToRemove);
                        });
                    }
                }
            }
        });
    }

    private void initializeBonusViewList() {
        levelMaker.getBonuses().addListener(new ListChangeListener<Bonus>() {
            @Override
            public void onChanged(Change<? extends Bonus> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        change.getAddedSubList().forEach(bonus -> {
                            AbstractBonusView bonusView = null;
                            if (bonus instanceof MunitionsBonus) {
                                bonusView = new MunitionsBonusView(bonus);
                            } else if (bonus instanceof HourglassBonus) {
                                bonusView = new HourglassBonusView(bonus);
                            }
                            createContextMenu(bonusView);
                            bonusViews.add(bonusView);
                            if (!root.getChildren().contains(bonusView)) {
                                root.getChildren().add(bonusView);
                            }
                        });
                    }
                    if (change.wasRemoved()) {
                        change.getRemoved().forEach(mb -> {
                            List<AbstractBonusView> mbToRemove = new ArrayList<>();
                            bonusViews.forEach(tmpMbw -> {
                                if (tmpMbw.getBonus().equals(mb)) {
                                    mbToRemove.add(tmpMbw);
                                    root.getChildren().remove(tmpMbw);
                                }
                            });
                            bonusViews.removeAll(mbToRemove);
                        });
                    }
                }
            }
        });
    }

    private void createBtns() {
        int buttonSize = 100;
        int spaceBetweenButton = 5;

        Button saveButton = new Button();
        saveButton.setText("SAVE");
        saveButton.setLayoutX(spaceBetweenButton);
        saveButton.setLayoutY(spaceBetweenButton);
        saveButton.setPrefWidth(buttonSize);
        saveButton.setPrefHeight(20);

        Button newButton = new Button();
        newButton.setText("NEW");
        newButton.setLayoutX(buttonSize + spaceBetweenButton * 2);
        newButton.setLayoutY(spaceBetweenButton);
        newButton.setPrefWidth(buttonSize);
        newButton.setPrefHeight(20);

        Button loadButton = new Button();
        loadButton.setText("LOAD");
        loadButton.setLayoutX(buttonSize * 2 + spaceBetweenButton * 3);
        loadButton.setLayoutY(spaceBetweenButton);
        loadButton.setPrefWidth(buttonSize);
        loadButton.setPrefHeight(20);


        Button tryButton = new Button();
        tryButton.setText("TRY IT");
        tryButton.setLayoutX(buttonSize * 3 + spaceBetweenButton * 4);
        tryButton.setLayoutY(spaceBetweenButton);
        tryButton.setPrefWidth(buttonSize);
        tryButton.setPrefHeight(20);

        TextField gravityField = new TextField();
        gravityField.setLayoutX(buttonSize * 4 + spaceBetweenButton * 5);
        gravityField.setLayoutY(spaceBetweenButton);
        gravityField.setPrefWidth(buttonSize);
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(gravityField.textProperty(), GameParam.getInstance().getG(), converter);

        TextField radiusUnity = new TextField();
        radiusUnity.setLayoutX(buttonSize * 5 + spaceBetweenButton * 6);
        radiusUnity.setLayoutY(spaceBetweenButton);
        radiusUnity.setPrefWidth(buttonSize);
        Bindings.bindBidirectional(radiusUnity.textProperty(), GameParam.getInstance().getRadiusToMassUnity(), converter);

        root.getChildren().add(saveButton);
        root.getChildren().add(newButton);
        root.getChildren().add(loadButton);
        root.getChildren().add(tryButton);
        root.getChildren().add(gravityField);
        root.getChildren().add(radiusUnity);

        tryButton.setOnAction(event -> {
            LevelSerializable level = LevelSerializer.serializeLeve(levelMaker);
            BulletGame.getGameController().setActualEditingLevel(level);
            BulletGame.getGameController().startRescueGameFromLevelMaker();
        });

        newButton.setOnAction(event -> {
            BulletGame.getGameController().startLevelMakerFromFile(null);
        });

        loadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);


            if (BulletGame.getGameController().getLastBrowsingLevelPath() != null) {
                fileChooser.setInitialDirectory(BulletGame.getGameController().getLastBrowsingLevelPath());
            }

            //Show save file dialog
            File file = fileChooser.showOpenDialog(BulletGame.getPrimaryStage());


            if (file != null) {
                BulletGame.getGameController().setLastBrowsingLevelPath(file.getParentFile());
                BulletGame.getGameController().startLevelMakerFromFile(file);

            }
        });


        saveButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter for text files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            if (BulletGame.getGameController().getLastBrowsingLevelPath() != null) {
                fileChooser.setInitialDirectory(BulletGame.getGameController().getLastBrowsingLevelPath());
            }

            //Show save file dialog
            File file = fileChooser.showSaveDialog(BulletGame.getPrimaryStage());

            if (file != null) {
                BulletGame.getGameController().setLastBrowsingLevelPath(file.getParentFile());
                LevelSerializer.serializeLevelAndSaveToFile(levelMaker, file);
            }
        });
    }

    private void createContextMenu(BulletView bulletView) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Supprimer");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                levelMaker.getBulletList().remove(bulletView.getBullet());

            }
        });
        MenuItem item2 = new MenuItem("Add rotation");
        Menu parentMenuRotationType = new Menu("Rotation type");

        MenuItem item4 = new MenuItem("Changer Radius/Mass");
        item4.setOnAction(e -> {
            levelMaker.setActionMakerEnum(LevelMakerActionEnum.CHANGE_MASS);
            BulletGame.getPrimaryStage().getScene().setOnMouseMoved(eMouse -> {
                double lineLength = Point2D.distance(bulletView.getCircle().getCenterX(), bulletView.getCircle().getCenterY(), eMouse.getX(), eMouse.getY());
                bulletView.getBullet().getBulletRadius().setValue(lineLength);
            });
        });

        MenuItem item3 = new MenuItem("Add initial Force");
        item3.setOnAction(event -> {
            bulletView.removeForceGraphFromParent();
            levelMaker.setActionMakerEnum(LevelMakerActionEnum.INITIAL_FORCE);
            levelMaker.setSelectedBullet(bulletView.getBullet());
            actualGraphLine = new Line();
            actualGraphLine.setStroke(Color.YELLOW);
            actualGraphLine.setStrokeWidth(2);
            actualGraphLine.getStrokeDashArray().add(5d);
            actualGraphLine.startXProperty().set(bulletView.getCircle().getCenterX());
            actualGraphLine.startYProperty().set(bulletView.getCircle().getCenterY());
            BulletGame.getPrimaryStage().getScene().setOnMouseMoved(e -> {
                actualGraphLine.setEndX(e.getX());
                actualGraphLine.setEndY(e.getY());
                levelMaker.setActualMouseX(e.getX());
                levelMaker.setActualMouseY(e.getY());
            });

            root.getChildren().add(actualGraphLine);
            actualGraphLine.toBack();

        });

        Menu parentMenuRotationSpeed = new Menu("Rotation Speed");
        if (bulletView.getBullet().getMouvementEnum() == null || !bulletView.getBullet().getMouvementEnum().equals(MouvementEnum.ROTATION)) {
            parentMenuRotationType.setDisable(true);
            parentMenuRotationSpeed.setDisable(true);
        }

        item2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                parentMenuRotationType.setDisable(false);
                parentMenuRotationSpeed.setDisable(false);
                levelMaker.setActionMakerEnum(LevelMakerActionEnum.ROTATION);
                levelMaker.setSelectedBullet(bulletView.getBullet());
                actualGraphLine = new Line();
                actualRotationCircle = new Circle();
                actualRotationCircle.setStroke(Color.GREEN);
                actualRotationCircle.setStrokeWidth(1);
                actualRotationCircle.setFill(Color.TRANSPARENT);
                actualRotationCircle.getStrokeDashArray().add(3d);
                actualGraphLine.setStroke(Color.GREEN);
                actualGraphLine.setStrokeWidth(1);
                actualGraphLine.getStrokeDashArray().add(30d);
                actualGraphLine.startXProperty().set(bulletView.getCircle().getCenterX());
                actualGraphLine.startYProperty().set(bulletView.getCircle().getCenterY());
                BulletGame.getPrimaryStage().getScene().setOnMouseMoved(e -> {
                    actualGraphLine.setEndX(e.getX());
                    actualGraphLine.setEndY(e.getY());
                    actualRotationCircle.setCenterX(e.getX());
                    actualRotationCircle.setCenterY(e.getY());

                    double lineLength = Point2D.distance(actualGraphLine.getStartX(), actualGraphLine.getStartY(), actualGraphLine.getEndX(), actualGraphLine.getEndY());
                    actualRotationCircle.setRadius(lineLength);
                    levelMaker.setActualMouseX(e.getX());
                    levelMaker.setActualMouseY(e.getY());
                });
                root.getChildren().add(actualRotationCircle);
                root.getChildren().add(actualGraphLine);
                actualRotationCircle.toBack();
                actualGraphLine.toBack();
            }
        });


        RadioMenuItem radioMenuItem1 = new RadioMenuItem("Clockwise");
        RadioMenuItem radioMenuItem2 = new RadioMenuItem("Counter Clockwise");
        parentMenuRotationType.getItems().add(radioMenuItem1);
        parentMenuRotationType.getItems().add(radioMenuItem2);
        ToggleGroup group = new ToggleGroup();
        radioMenuItem1.setToggleGroup(group);
        radioMenuItem2.setToggleGroup(group);
        if (bulletView.getBullet().isRotationClockwise()) {
            radioMenuItem1.setSelected(true);
        } else {
            radioMenuItem2.setSelected(true);
        }
        radioMenuItem1.setOnAction(new EventHandler<ActionEvent>() {
                                       @Override
                                       public void handle(ActionEvent event) {
                                           bulletView.getBullet().setRotationClockwise(true);
                                       }
                                   }
        );

        radioMenuItem2.setOnAction(new EventHandler<ActionEvent>() {
                                       @Override
                                       public void handle(ActionEvent event) {
                                           bulletView.getBullet().setRotationClockwise(false);
                                       }
                                   }
        );


        ToggleGroup groupRotationSpeed = new ToggleGroup();
        for (int i = 0; i < 20; i++) {
            addRadioButtonSpeedRotation(groupRotationSpeed, parentMenuRotationSpeed, bulletView.getBullet(), i + 1);

        }
        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1, item2, item3, item4, parentMenuRotationType, parentMenuRotationSpeed);
        // When user right-click on Circle
        bulletView.getCircle().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(bulletView.getCircle(), event.getScreenX(), event.getScreenY());
            }
        });
    }

    public void removeActualRotationGraph() {
        root.getChildren().remove(actualRotationCircle);
        root.getChildren().remove(actualGraphLine);
    }

    public void removeActualForceGraph() {
        root.getChildren().remove(actualGraphLine);
    }

    private void addRadioButtonSpeedRotation(ToggleGroup group, Menu parentMenuRotationSpeed, Bullet bullet, int valueSpeed) {
        RadioMenuItem radioSpeed = new RadioMenuItem(String.valueOf(valueSpeed));
        parentMenuRotationSpeed.getItems().add(radioSpeed);
        radioSpeed.setToggleGroup(group);
        if (bullet.getRotationSpeed() == valueSpeed) {
            radioSpeed.setSelected(true);
        }

        radioSpeed.setOnAction(new EventHandler<ActionEvent>() {
                                   @Override
                                   public void handle(ActionEvent event) {
                                       bullet.setRotationSpeed(valueSpeed);
                                   }
                               }
        );
    }


    private void createContextMenu(AstronautView astronautView) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Supprimer");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                levelMaker.getAstroList().remove(astronautView.getAstronaut());

            }
        });


        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1);

        // When user right-click on Circle
        astronautView.getAstronautImageView().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(astronautView.getAstronautImageView(), event.getScreenX(), event.getScreenY());
            }
        });
    }

    private void createContextMenu(AbstractBonusView bonusView) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Supprimer");
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                levelMaker.getBonuses().remove(bonusView.getBonus());

            }
        });


        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(item1);

        // When user right-click on Circle
        bonusView.getImageView().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(bonusView.getImageView(), event.getScreenX(), event.getScreenY());
            }
        });
    }


}
