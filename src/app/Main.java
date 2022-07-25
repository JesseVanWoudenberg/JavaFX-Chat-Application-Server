package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

    /**
     * The main class to start the application and create the stage.
     * This class creates the stage and sets all the basic properties.
     * This class also create the object for the scene and inserts it, Starting the app.
     */

    private Stage stage;
    private MainScene mainScene;

    @Override
    public void start(Stage primaryStage) {

        // Passing main stage object to class wide scope
        this.stage = primaryStage;

        // Adding an icon to the app in taskbar
        this.stage.getIcons().add(
                new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("../images/chatapplogo.png"))));

        // Removing the default title bar
        this.stage.initStyle(StageStyle.TRANSPARENT);

        // Settings window dimensions
        this.stage.setMinWidth(500);
        this.stage.setMaxWidth(500);
        this.stage.setMinHeight(800);
        this.stage.setMaxHeight(800);

        // Setting the scene to the stage
        this.mainScene = new MainScene(this.stage);
        this.stage.setScene(new Scene(this.mainScene));
        
        // Displaying the window
        this.stage.show();

        /*
         Setting an event handler for an external shutdown request
         this will eat the event and close the entire app and all the currently
         active threads
        */

        this.stage.setOnCloseRequest(event -> {
            this.mainScene.chatServer.sendMessageToAllClients("//SERVER-OFF");
            Platform.exit();
            System.exit(0);
        });
    }
}
