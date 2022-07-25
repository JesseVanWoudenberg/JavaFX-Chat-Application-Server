package components;

import app.MainScene;
import javafx.scene.control.Label;

public class ServerOnButton extends Label implements Button {

    private final MainScene mainScene;

    public ServerOnButton(MainScene mainScene) {

        this.mainScene = mainScene;
        this.init();

    }

    @Override
    public void init() {

        this.setProperties();
        this.createOnHoverEffect();
        this.onClick();

        this.setText("Turn server on");

    }

    @Override
    public void setProperties() {

        // Applying the default style for the button
        this.setStyle("-fx-font-size: 14px; -fx-background-color: #4dd749; -fx-alignment: center");

        this.setMinHeight(50);
        this.setMaxHeight(50);

    }

    @Override
    public void createOnHoverEffect() {

        // Applying the style for the button when a mouse hovers over it
        this.setOnMouseEntered(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #249322; -fx-alignment: center"));
        this.setOnMouseExited(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #4dd749; -fx-alignment: center"));

        this.setOnMousePressed(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #186d17; -fx-alignment: center"));
        this.setOnMouseReleased(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #4dd749; -fx-alignment: center"));

    }

    @Override
    public void onClick() {
        // Adding an event handler to check if the turn on button has been clicked
        this.setOnMouseClicked(event -> {

            // Starting the server
            this.mainScene.startServerThread();

        });
    }
}