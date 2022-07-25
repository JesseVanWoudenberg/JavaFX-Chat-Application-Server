package components;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class CurrentlyOnlineList extends ScrollPane {

    private final ArrayList<Label> onlineUsers;
    private final VBox usersContainer;

    public CurrentlyOnlineList() {

        this.onlineUsers = new ArrayList<>();
        this.usersContainer = new VBox();

    }

    // Method to add user to the currently online list
    public void addUser(String name) {
        Platform.runLater(() -> {
            this.onlineUsers.add(new Label(name));
        });

        this.reloadOnlineList();
    }

    public void removeUser(String name) {
        Platform.runLater(() -> {
            onlineUsers.removeIf(label -> label.getText().equals(name));
        });

        this.reloadOnlineList();
    }

    private void reloadOnlineList() {
        Platform.runLater(() -> {
            // Clearing vbox nodes
             this.usersContainer.getChildren().clear();

            // Reloading all labels
            for (Label userLabel : this.onlineUsers) {
                this.usersContainer.getChildren().add(userLabel);
            }
        });
    }

    public void clearList() {

        System.out.println("CleareD!");
        Platform.runLater(this.onlineUsers::clear);
        this.reloadOnlineList();

    }
}
