package components;


import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class TitleBarMinimizeBox extends StackPane {

    private final Rectangle buttonIcon;
    private final Stage stage;

    public TitleBarMinimizeBox(Stage stage) {

        this.stage = stage;
        this.buttonIcon = new Rectangle();
        this.init();

    }

    private void init() {

        this.createContent();
        this.setProperties();
        this.functionality();

    }

    private void createContent() {

        this.buttonIcon.setWidth(25);
        this.buttonIcon.setHeight(5);
        this.buttonIcon.setArcHeight(30);
        this.buttonIcon.setArcWidth(10);
        this.buttonIcon.setFill(Color.gray(1.0));

    }

    private void setProperties() {

        this.setMinSize(40,40);
        this.setMaxSize(40,40);

        this.getChildren().add(this.buttonIcon);
        this.setStyle("-fx-background-color: #999696;");

    }

    private void functionality() {
        //MinimizeBox Functionality (Hover and click on action)
        this.setOnMouseEntered(event -> this.setStyle("-fx-background-color: #6a6666;"));

        this.setOnMouseExited(event -> this.setStyle("-fx-background-color: #999696;"));

        this.setOnMousePressed(event -> this.setStyle("-fx-background-color: #575454;"));

        this.setOnMouseReleased(event -> this.setStyle("-fx-background-color: #999696;"));

        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                stage.setIconified(true);
            }
        });
    }
}
