package com.iDebug.pickloose.database.server;

import java.sql.*;

import static java.sql.Types.NULL;

public class Database {
    private static Database database = null;
    private final static String url = "jdbc:sqlite:app-server.db";
    private Connection connection;
    private Database() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        createConnection();
        createUsersTableIfNotExists();
        createKicksTableIfNotExists();
        createMsgTableIfNotExists();
        createReactsTableIfNotExists();
        closeConnection();
    }
    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
    }
    private void closeConnection() throws SQLException {
        connection.close();
    }
    public static Database getInstance() {
        if(database == null) {
            try {
                database = new Database();
            }
            catch (ClassNotFoundException e) {
                System.out.println("Driver not installed.");
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
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS users(user_id varchar(32) NOT NULL,"
                +"username varchar(32)," + "avatar varchar(16),"
                +"points integer,"
                +"PRIMARY KEY(user_id));");
    }
    private void createKicksTableIfNotExists() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS kicks (" +
                "kicker varchar(32) NOT NULL REFERENCES users(user_id)," +
                "vodai varchar(32) NOT NULL REFERENCES users(user_id)," +
                "PRIMARY KEY(kicker,vodai));");
    }
    private void createMsgTableIfNotExists() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS messages ("+
                "msg_id varchar(32) NOT NULL,"+
                "sender_id varchar(32) NOT NULL,"+
                "message text,"+
                "time varchar(32),"+
                "PRIMARY KEY(msg_id));");
    }
    private void createReactsTableIfNotExists() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE IF NOT EXISTS reactions (" +
                "reactor_id varchar(32) NOT NULL REFERENCES users(user_id)," +
                "msg_id varchar(32) NOT NULL REFERENCES messages(msg_id)," +
                "react varchar(16) NOT NULL," +
                "PRIMARY KEY(reactor_id,msg_id));");
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
    public void getUserInfo(String user_id) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
        preparedStatement.setString(1,user_id);
        preparedStatement.executeQuery();
        closeConnection();
    }
    public void getAllUser() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM users");
        closeConnection();
    }
    public void getAllUsersOrderByPoints() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM users ORDER BY points DESC");
        closeConnection();
    }
    public void deleteUser(String user_id) throws SQLException {
        createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE user_id=?");
        preparedStatement.setString(1,user_id);
        preparedStatement.execute();
        closeConnection();
    }
    public void getAllMessages() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM messages;");
        closeConnection();
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
        closeConnection();
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
        closeConnection();
    }
    /*
        Get all messages of a given user.
     */
    public void getMessages(String sender_id) throws  SQLException {
        createConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE sender_id = ?");
        statement.setString(1,sender_id);
        statement.execute();
        closeConnection();
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
        closeConnection();
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
        closeConnection();
    }
    public void display(ResultSet result) throws SQLException {
        createConnection();
        while (result.next()) {
            System.out.println("userid: " + result.getString(2)
                    + " username: " + result.getString(3) + " points: " + result.getInt(4));
        }
    }
    public void drop() throws SQLException {
        createConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE users;");
        statement.execute("DROP TABLE kicks;");
        statement.execute("DROP TABLE messages;");
        statement.execute("DROP TABLE reactions;");
        closeConnection();
    }
}

