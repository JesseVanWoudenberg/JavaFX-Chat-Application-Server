package loginserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class to handle login requests outside of the client so that
 * the clients never have direct access to the mysql server.
 * its a runnable so that the whole class can be put in a thread
 *
 * The server will make connections to all connected clients and the LoginServerClient Class
 * will create instances of the LoginRequest class to handle the login requests
 */

public class LoginServer implements Runnable {

    private final int port;
    public boolean serverIsRunning;
    private ServerSocket serverSocket;

    public LoginServer(int port) {

        this.port = port;
        this.serverIsRunning = true;

        try {

            this.serverSocket = new ServerSocket(this.port);

        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {

        try {

            while (this.serverIsRunning) {

                Socket socket = serverSocket.accept();

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                LoginServerClient loginServerClient = new LoginServerClient(inputStream, outputStream, this);

                Thread thread = new Thread(loginServerClient);
                thread.start();

            }
        } catch (IOException e) { e.printStackTrace(); }
    }



    // Method to stop server
    public void stopServer() {
        System.out.println("Stopping login server");
        this.serverIsRunning = false;

        // Stopping the clients acceptance loop
        try {
            this.serverSocket.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}
