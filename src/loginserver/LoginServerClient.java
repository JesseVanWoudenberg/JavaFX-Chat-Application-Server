package loginserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class LoginServerClient implements Runnable, Serializable {

    private final DataInputStream input;
    private final DataOutputStream output;
    private final LoginServer loginServer;
    public boolean isConnected;

    public LoginServerClient(DataInputStream input, DataOutputStream output, LoginServer loginServer) {

        this.input = input;
        this.output = output;
        this.loginServer = loginServer;
        this.isConnected = true;

    }

    @Override
    public void run() {

        while (this.isConnected) {

            try {

                String message = input.readUTF();

                if (message.equals("//LOGIN/")) {

                    String username = input.readUTF();
                    String password = input.readUTF();

                    LoginRequest loginRequest = new LoginRequest(username, password, this.output);
                    loginRequest.handleLoginRequest();

                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
