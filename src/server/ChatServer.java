package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *   Main class to store all client-server connection threads and handle join / leave requests
 *   Also this serves as a main class to send messages to all connected client like:
 *   Chat messages or update usernames requests
 */

public class ChatServer implements Runnable {

    public final ArrayList<ChatServerClient> clients;
    private final ArrayList<Thread> clientThreads;
    private boolean serverIsRunning;
    private ServerSocket serverSocket;

    public ChatServer(int port) {

        this.clients = new ArrayList<>();
        this.clientThreads = new ArrayList<>();

        this.serverIsRunning = true;

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {

        System.out.println("Server starting!");

        // Endless loop to keep connecting clients
        try {

            while (this.serverIsRunning) {

                Socket socket = this.serverSocket.accept();

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                String username = inputStream.readUTF();
                System.out.println(username);

                ChatServerClient chatServerClient = new ChatServerClient(username, this, inputStream, outputStream);

                Thread thread = new Thread(chatServerClient);
                thread.start();

                this.addClient(chatServerClient);
                this.clientThreads.add(thread);

            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Thread safe method to access the ArrayList of server clients
    public synchronized ArrayList<ChatServerClient> getClients() {
        return this.clients;
    }

    // Thread safe method to add a connection server client connection
    public synchronized void addClient(ChatServerClient chatServerClient) {
        this.clients.add(chatServerClient);
    }

    // Thread safe method to remove a client when it disconnects
    public synchronized void removeClient(ChatServerClient target) {
        this.clients.removeIf(chatServerClient -> chatServerClient == target);
    }

    // Method to stop server
    public void stopServer() {
        System.out.println("Stopping chat server");
        this.serverIsRunning = false;

        // Stopping the clients acceptance loop
        try {
            this.serverSocket.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Method to close all connection threads
    public void closeAllThreads() {

        System.out.println("Closing threads");
        for (ChatServerClient chatServerClient : this.clients) {

            chatServerClient.sendMessage("//DISCONNECT/");
            chatServerClient.isConnected = false;

        }

        for (Thread thread : this.clientThreads) {
            thread.interrupt();
        }

        this.clients.clear();
        this.clientThreads.clear();

    }

    // Method to send a message to all the connected clients
    public void sendMessageToAllClients(String message) {

        for (ChatServerClient chatServerClient : this.clients) {

            chatServerClient.sendMessage(message);

        }
    }
}
