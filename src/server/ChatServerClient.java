package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class ChatServerClient implements Runnable, Serializable {

    private final DataInputStream input;
    private final DataOutputStream output;
    private final ChatServer chatServer;
    public boolean isConnected = true;
    private String username;

    public ChatServerClient(String username, ChatServer chatServer, DataInputStream input, DataOutputStream output) {

        this.username = username;
        this.chatServer = chatServer;
        this.input = input;
        this.output = output;

    }

    public void sendMessage(String message) {
        try {
            this.output.writeUTF(message);
            this.output.flush();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        // Sending all currently connected users names to this connected client to add them to the online list
        try {

            for (int i = 0; i < this.chatServer.getClients().size(); i++) {

                if (!(this.chatServer.getClients().get(i) == this)) {

                    System.out.println("//JOINED/" + this.chatServer.getClients().get(i).username);
                    this.output.writeUTF("//JOINED/" + this.chatServer.getClients().get(i).username);
                    this.output.flush();

                }
            }

            // Telling all connected clients (including this client) that this client connected
            this.chatServer.sendMessageToAllClients("//JOINED/" + this.username);

        } catch (IOException e) { e.printStackTrace(); }


        while (this.isConnected) {

            try {

                String message = input.readUTF();
                System.out.println(message);

                if (message.equals("//DISCONNECT/")) {

                    this.chatServer.sendMessageToAllClients("//DISCONNECT/" + this.username);

                    this.chatServer.removeClient(this);
                    this.isConnected = false;

                } else if (message.contains("//UPDATE/")) {

                    String[] seperatedUsernames = message.replace("//UPDATE/", "").split(":");
                    this.username = seperatedUsernames[1];

                    this.chatServer.sendMessageToAllClients(message);


                } else {

                    this.chatServer.sendMessageToAllClients(message);

                }

            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}