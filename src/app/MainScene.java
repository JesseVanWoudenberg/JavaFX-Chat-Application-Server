package app;

import components.CurrentlyOnlineList;
import components.ServerOffButton;
import components.ServerOnButton;
import components.TitleBar;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import loginserver.LoginServer;
import server.ChatServer;

public class MainScene extends AnchorPane {

    // Node objects
    private CurrentlyOnlineList onlineList;
    private TitleBar titleBar;
    private ServerOnButton serverOnButton;
    private ServerOffButton serverOffButton;
    private Label headerLabel;

    // Stage object to pass to title bar object
    private final Stage stage;

    // Server and client object
    public ChatServer chatServer;
    public LoginServer loginServer;

    // Server on/off boolean
    public boolean serverOn = false;

    // Thread variables
    Thread mainServerThread;
    Thread loginServerThread;

    public MainScene(Stage stage) {

        this.stage = stage;
        this.init();

    }

    private void init() {

        this.createComponents();
        this.setProperties();
        this.setAnchors();

    }

    public void startServerThread() {

        this.serverOn = true;
        this.mainServerThread = new Thread(this.chatServer = new ChatServer(1111));
        this.mainServerThread.start();

        this.loginServerThread = new Thread(this.loginServer = new LoginServer(10000));
        this.loginServerThread.start();

        this.getChildren().remove(this.serverOnButton);
        this.getChildren().add(this.serverOffButton);

    }

    public void turnServerOff() {

        this.serverOn = false;
        this.chatServer.stopServer();
        this.chatServer.closeAllThreads();

        this.loginServer.serverIsRunning = false;
        this.loginServer.stopServer();

        this.mainServerThread.interrupt();
        this.loginServerThread.interrupt();

        this.getChildren().remove(this.serverOffButton);
        this.getChildren().add(this.serverOnButton);
    }

    private void createComponents() {

        this.onlineList = new CurrentlyOnlineList();

        this.titleBar = new TitleBar(this.stage, this);

        this.headerLabel = new Label("Currently Online");
        this.headerLabel.setStyle("-fx-font-size: 20px; -fx-alignment: center");

        this.headerLabel.setMinSize(500,50);
        this.headerLabel.setMaxSize(500, 50);

        this.serverOnButton = new ServerOnButton(this);
        this.serverOffButton = new ServerOffButton(this, this.onlineList);

    }

    private void setProperties() {

        // Add all nodes to the scene
        this.getChildren().addAll(this.titleBar, this.headerLabel, this.onlineList, this.serverOnButton);
        // Set scene dimensions
        this.setMinSize(500, 800);
        this.setMaxSize(500, 800);

    }

    private void setAnchors() {

        // Title bar anchors
        AnchorPane.setTopAnchor(this.titleBar, (double) 0);
        AnchorPane.setLeftAnchor(this.titleBar, (double) 0);

        // Header label anchors
        AnchorPane.setTopAnchor(this.headerLabel, (double) 40);
        AnchorPane.setLeftAnchor(this.headerLabel, (double) 0);
        AnchorPane.setRightAnchor(this.headerLabel, (double) 0);

        // Online list anchors
        AnchorPane.setTopAnchor(this.onlineList, (double) 90);
        AnchorPane.setLeftAnchor(this.onlineList, (double) 0);
        AnchorPane.setRightAnchor(this.onlineList, (double) 0);
        AnchorPane.setBottomAnchor(this.onlineList, (double) 100);

        // Server on button anchors
        AnchorPane.setBottomAnchor(this.serverOnButton, (double) 25);
        AnchorPane.setLeftAnchor(this.serverOnButton, (double) 25);
        AnchorPane.setRightAnchor(this.serverOnButton, (double) 25);

        // Server off button anchors
        AnchorPane.setBottomAnchor(this.serverOffButton, (double) 25);
        AnchorPane.setLeftAnchor(this.serverOffButton, (double) 25);
        AnchorPane.setRightAnchor(this.serverOffButton, (double) 25);

    }
}
