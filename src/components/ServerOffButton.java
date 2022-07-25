package components;

import app.MainScene;
import javafx.scene.control.Label;

public class ServerOffButton extends Label implements Button{

    private final MainScene mainScene;
    private final CurrentlyOnlineList onlineList;

    public ServerOffButton(MainScene mainScene, CurrentlyOnlineList onlineList) {

        this.mainScene = mainScene;
        this.onlineList = onlineList;
        this.init();

    }

    @Override
    public void init() {

        this.setProperties();
        this.createOnHoverEffect();
        this.onClick();

        this.setText("Turn server off");

    }

    @Override
    public void setProperties() {

        // Applying the default style for the button
        this.setStyle("-fx-font-size: 14px; -fx-background-color: #dc5151; -fx-alignment: center");

        this.setMinHeight(50);
        this.setMaxHeight(50);

    }

    @Override
    public void createOnHoverEffect() {

        // Applying the style for the button when a mouse hovers over it
        this.setOnMouseEntered(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #c93232; -fx-alignment: center"));
        this.setOnMouseExited(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #dc5151; -fx-alignment: center"));

        this.setOnMousePressed(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #a81f1f; -fx-alignment: center"));
        this.setOnMouseReleased(event -> this.setStyle("-fx-font-size: 14px; -fx-background-color: #dc5151; -fx-alignment: center"));

    }

    @Override
    public void onClick() {
        // Adding an event handler to check if the turn on button has been clicked
        this.setOnMouseClicked(event -> {

            this.onlineList.clearList();
            this.mainScene.turnServerOff();

        });
    }
}