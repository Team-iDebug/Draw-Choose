package com.iDebug.pickloose.database.client;

import java.sql.*;

import static java.sql.Types.NULL;

public class ClientDatabase {
    private static ClientDatabase database = null;
    private static String url = "jdbc:sqlite:app-client";
    private Connection connection;
    private ClientDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        createUsersTableIfNotExists();
        createKicksTableIfNotExists();
        createMsgTableIfNotExists();
        createReactsTableIfNotExists();
    }
    public static void setUrl(String id) {
        ClientDatabase.url += "-"+id+".db";
    }
    public static String getUrl() {
        return url;
    }
    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
    }
    public void closeConnection() throws SQLException {
        connection.close();
    }
    public static ClientDatabase getInstance() {
        if(database == null) {
            try {
                database = new ClientDatabase();
            }
            catch (ClassNotFoundException e) {
                System.out.println("Driver not installed.");
                e.printStackTrace();
            }
            catch (SQLException e) {
                System.out.println("Error performing SQL statement");
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return database;
    }
    private void createUsersTableIfNotExists() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users(user_id varchar(64) NOT NULL,"
                +"username varchar(64)," + "avatar varchar(64),"
                +"points integer," + "user_role varchar(16),"
                +"PRIMARY KEY(user_id));");
        closeConnection();
    }
    private void createKicksTableIfNotExists() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS kicks (" +
                "kicker varchar(64) NOT NULL REFERENCES users(user_id)," +
                "vodai varchar(64) NOT NULL REFERENCES users(user_id)," +
                "PRIMARY KEY(kicker,vodai));");
        closeConnection();
    }
    private void createMsgTableIfNotExists() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS messages ("+
                "msg_id varchar(64) NOT NULL,"+
                "sender_id varchar(64) NOT NULL,"+
                "message text,"+
                "time varchar(32),"+
                "PRIMARY KEY(msg_id));");
        closeConnection();
    }
    private void createReactsTableIfNotExists() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS reactions (" +
                "reactor_id varchar(64) NOT NULL REFERENCES users(user_id)," +
                "msg_id varchar(64) NOT NULL REFERENCES messages(msg_id)," +
                "react varchar(16) NOT NULL," +
                "PRIMARY KEY(reactor_id,msg_id));");
        closeConnection();
    }
    public ResultSet userExists(String userid) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(user_id) from users WHERE user_id = ?;");
        preparedStatement.setString(1,userid);
        return preparedStatement.executeQuery();
    }
    public void addUser(String user_id, String username, String avatar) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(user_id,username,avatar,points)" +
                "VALUES(?,?,?,?);");
        preparedStatement.setString(1,user_id);
        preparedStatement.setString(2,username);
        preparedStatement.setString(3,avatar);
        preparedStatement.setInt(4,NULL);
        preparedStatement.execute();
        closeConnection();
    }
    public void addMessage(String msg_id, String sender_id, String message, String time) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO messages(msg_id,sender_id,message,time) VALUES(?,?,?,?);");
        preparedStatement.setString(1,msg_id);
        preparedStatement.setString(2,sender_id);
        preparedStatement.setString(3,message);
        preparedStatement.setString(4,time);
        preparedStatement.execute();
        closeConnection();
    }
    public void updatePoints(String id, int points) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET points = ? WHERE user_id = ?");
        preparedStatement.setInt(1,points);
        preparedStatement.setString(2,id);
        preparedStatement.execute();
        closeConnection();
    }
    public ResultSet getUserInfo(String user_id) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
        preparedStatement.setString(1,user_id);
        return preparedStatement.executeQuery();
    }
    public ResultSet getAllUser() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT user_id,username,avatar FROM users;");
        return resultSet;
    }
    public ResultSet getAllUserID() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT user_id FROM users;");
        return resultSet;
    }
    public void getAllUsersOrderByPoints() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM users ORDER BY points DESC");
    }
    public void deleteUser(String user_id) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE user_id=?");
        preparedStatement.setString(1,user_id);
        preparedStatement.execute();
//        closeConnection();
    }
    public void updateRole(String user_id, String user_role) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET user_role = ? WHERE user_id = ?;");
        preparedStatement.setString(1,user_role);
        preparedStatement.setString(2,user_id);
        preparedStatement.execute();
        closeConnection();
    }
    public ResultSet getHost() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT user_id,username,avatar FROM users WHERE role='HOST';");
    }
    public ResultSet hostAssigned() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(user_id) FROM users WHERE user_role='HOST'");
        return resultSet;
    }
    public ResultSet isHost(String user_id) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_role FROM users WHERE user_id = ?;");
        preparedStatement.setString(1,user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    public void getAllMessages() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM messages;");
    }
    /*
        Get most reacts on a message
     */
    public void getReactInfo(String msg_id) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT react,COUNT(react) AS 'times'" +
                "FROM reactions WHERE msg_id=? GROUP BY react LIMIT 3;");
        preparedStatement.setString(1,msg_id);
        preparedStatement.execute();
    }
    /*
        Get the user details of whom gave certain react on a message
     */
    public void getAllReactors(String msg_id, String react) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT username AS 'name', avatar" +
                "from users JOIN" +
                "reactions WHERE reactions.msg_id = ? AND reactions.react = ? AND " +
                "reactions.reactor_id = users.user_id;");
        preparedStatement.setString(1,msg_id);
        preparedStatement.setString(2,react);
        preparedStatement.execute();
    }
    /*
        Get all messages of a given user.
     */
    public void getMessages(String sender_id) throws  SQLException {
        createConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE sender_id = ?");
        statement.setString(1,sender_id);
        statement.execute();
    }
    /*
        Get the kick counts
     */
    public void getKickCount(String vodai) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT vodai AS 'id'," +
                "users.username AS 'name', COUNT(kicker) AS 'kick_count'" +
                "from kicks JOIN users where vodai = ? AND kicks.vodai=users.user_id;");
        preparedStatement.setString(1,vodai);
        preparedStatement.execute();
    }
    /*
        Get the kickers who kicked a vodai
     */
    public void getKickers(String vodai) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id AS 'id', username AS 'name'," +
                "avatar FROM users JOIN" +
                "kicks WHERE kicks.vodai = ? AND kicks.kicker=users.user_id;");
        preparedStatement.setString(1,vodai);
        preparedStatement.execute();
    }
    public void display(ResultSet result) throws SQLException {
        createConnection();
        while (result.next()) {
            System.out.println("userid: " + result.getString(2)
                    + " username: " + result.getString(3) + " points: " + result.getInt(4));
        }
    }
    public void clear() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM users;");
        statement.execute("DELETE FROM kicks;");
        statement.execute("DELETE FROM messages;");
        statement.execute("DELETE FROM reactions;");
        closeConnection();
    }
    public void drop() throws SQLException {
//        createConnection();
//        Statement statement = connection.createStatement();
//        statement.execute("DROP ")
    }
}

