package components;

import app.Main;
import app.MainScene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TitleBar extends AnchorPane {

    private final Stage stage;
    private TitleBarMinimizeBox minimizeBox;
    private TitleBarExitBox exitBox;
    private final MainScene mainScene;

    // X,Y coordinates for drag functionality
    private double x, y;

    public TitleBar(Stage stage, MainScene mainScene) {

        this.mainScene = mainScene;
        this.stage = stage;
        this.init();

    }

    private void init() {

        this.createButtons();
        this.setProperties();
        this.setAnchors();
        this.functionality();

    }

    private void createButtons() {

        this.minimizeBox = new TitleBarMinimizeBox(this.stage);
        this.exitBox = new TitleBarExitBox(this.stage, this.mainScene);

    }

    private void setProperties() {

        this.getChildren().addAll(this.minimizeBox, this.exitBox);

        this.setStyle("-fx-background-color: #999696;");

        this.setMinSize(500,40);
        this.setMaxSize(500,40);

    }

    private void setAnchors() {

        AnchorPane.setTopAnchor(this.minimizeBox, (double) 0);
        AnchorPane.setRightAnchor(this.minimizeBox, (double) 40);

        AnchorPane.setTopAnchor(this.exitBox, (double) 0);
        AnchorPane.setRightAnchor(this.exitBox, (double) 0);

    }

    private void functionality() {

        // Drag functionality
        this.setOnMouseDragged(event -> {

            this.stage.setX(event.getScreenX() - x);
            this.stage.setY(event.getScreenY() - y);

        });

        this.setOnMousePressed(event -> {

            this.x = event.getSceneX();
            this.y = event.getSceneY();

        });

    }
}
