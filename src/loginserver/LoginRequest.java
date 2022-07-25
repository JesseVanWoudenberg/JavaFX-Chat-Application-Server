package loginserver;

import mysqlconnection.SqlConnection;

import java.io.DataOutputStream;
import java.io.IOException;

public class LoginRequest {

    DataOutputStream outputStream;
    String username;
    String password;

    public LoginRequest(String username, String password, DataOutputStream outputStream) {

        this.username = username;
        this.password = password;
        this.outputStream = outputStream;

        this.handleLoginRequest();

    }

    public void handleLoginRequest() {

        if (SqlConnection.checkData(this.username, this.password)) {

            try {

                this.outputStream.writeBoolean(true);
                this.outputStream.writeUTF(SqlConnection.displayname);
                this.outputStream.flush();

            } catch (IOException e) { e.printStackTrace(); }

        } else {

            try {

                this.outputStream.writeBoolean(false);
                this.outputStream.flush();

            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
