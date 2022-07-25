package mysqlconnection;


import java.sql.*;

public class SqlConnection {

    private static Connection conn = null;
    public static String displayname;

    public static void ConnectMySQL() {

        try {

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/scuffedteams", "root", "");

        } catch (SQLException e) { e.printStackTrace(); }
    }

    /**
     *
     * This method will check the user passed in login details in the database and return an answer
     *
     * @param username the username that was received from the client that needs to be checked
     * @param password the password that was received from the client that needs to be checked
     * @return the method will return true of false whether or not the login details are correct
     */

    public static boolean checkData(String username, String password) {

        ConnectMySQL();

        Statement stmt;
        ResultSet rs;

        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT displayname, username, password " +
                                       "FROM users " +
                                       "WHERE username = " + username);

            while (rs.next()) {

                if ( username.equals( rs.getString("username")) && password.equals( rs.getString("password")) ) {

                    SqlConnection.displayname = rs.getString("displayname");
                    return true;

                }
            } return false;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
