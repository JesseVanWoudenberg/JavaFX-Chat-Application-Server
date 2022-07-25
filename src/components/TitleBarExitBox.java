package components;

import app.MainScene;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TitleBarExitBox extends StackPane {

    private Text buttonIcon;
    private final Stage stage;
    private final MainScene mainScene;

    public TitleBarExitBox(Stage stage, MainScene mainScene) {

        this.mainScene = mainScene;
        this.stage = stage;
        this.init();

    }

    private void init() {

        this.createContent();
        this.setProperties();
        this.functionality();

    }

    private void createContent() {

        this.buttonIcon = GlyphsDude.createIcon(FontAwesomeIcon.TIMES, "25");
        this.buttonIcon.setFill(Color.gray(1.0));

    }

    private void setProperties() {

        this.setMinSize(40,40);
        this.setMaxSize(40,40);

        this.getChildren().add(this.buttonIcon);
        this.setStyle("-fx-background-color: #999696;");

    }

    private void functionality() {
        //ExitBox Functionality (Hover and click on action)
        this.setOnMouseEntered(event -> this.setStyle("-fx-background-color: #e03b3b;"));

        this.setOnMouseExited(event -> this.setStyle("-fx-background-color: #999696;"));

        this.setOnMousePressed(event -> this.setStyle("-fx-background-color: #e02424;"));

        this.setOnMouseReleased(event -> this.setStyle("-fx-background-color: #999696;"));

        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                this.mainScene.chatServer.sendMessageToAllClients("//SERVER-OFF");
                stage.close();
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
