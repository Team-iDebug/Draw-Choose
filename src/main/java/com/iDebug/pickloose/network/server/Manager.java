package com.iDebug.pickloose.network.server;

import com.iDebug.pickloose.AuthUser;
import com.iDebug.pickloose.GameSettings;
import com.iDebug.pickloose.database.server.ServerDatabase;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Manager {
    private HashMap<Socket, String> socketUseridMapping;
    private HashMap<String, Socket> useridSocketMapping;
    private HashSet<String> readyUsers; // token
    private static Manager manager;
    private ServerSocket gameServerSocket;
    private ServerSocket streamServerSocket;
    private GameSettings gameSettings;

    private Manager() {
        socketUseridMapping = new HashMap<>();
        useridSocketMapping = new HashMap<>();
        readyUsers = new HashSet<>();
    }
    static Manager getInstance() {
        if(manager == null)
            manager = new Manager();
        return manager;
    }
    void add(AuthUser user, Socket socket) throws SQLException {
        socketUseridMapping.put(socket, user.getUserid());
        useridSocketMapping.put(user.getUserid(),socket);
        String user_id = user.getUserid();
        String username = user.getUsername();
        String avatar = user.getAvatar();
        ServerDatabase.getInstance().addUser(user_id, username, avatar);
    }

    void remove(AuthUser user) throws SQLException {
        readyUsers.remove(user.getUserid());
        ServerDatabase.getInstance().deleteUser(user.getUserid());
    }

    HashMap<String, Socket> getClients() {
        return useridSocketMapping;
    }

    public ServerSocket getCanvasStream() {
        if(streamServerSocket == null) {
            Server server = null;
            try {
                server = new Server(0, CanvasStreamListener.class);
                server.start();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            streamServerSocket = server.getSocket();
        }
        return streamServerSocket;
    }

    public AuthUser parseAuthUserResultSet(ResultSet resultSet) throws SQLException {
        String user_id = resultSet.getString("user_id");
        String username = resultSet.getString("username");
        String avatar = resultSet.getString("avatar");
        return new AuthUser(user_id,username,avatar);
    }

    public ArrayList<AuthUser> getAllUserInfo() throws SQLException {
        ResultSet resultSet = ServerDatabase.getInstance().getAllUser();
        ArrayList<AuthUser> authUsers = new ArrayList<>();
        while (resultSet.next()) {
            authUsers.add(parseAuthUserResultSet(resultSet));
        }
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return authUsers;
    }

    public ArrayList<String> getAllUserID() throws SQLException {
        ResultSet resultSet = ServerDatabase.getInstance().getAllUserID();
        ArrayList<String> user_ids = new ArrayList<>();
        while (resultSet.next()) {
            String user_id = resultSet.getString("user_id");
            user_ids.add(user_id);
        }
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return user_ids;
    }

    public AuthUser getUser(Socket socket) throws SQLException {
        String user_id = socketUseridMapping.get(socket);
        return getUser(user_id);
    }

    public AuthUser getUser(String user_id) throws SQLException {
        ResultSet resultSet = ServerDatabase.getInstance().getUserInfo(user_id);
        AuthUser authUser = parseAuthUserResultSet(resultSet);
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return authUser;
    }

    public AuthUser getHost() throws SQLException {
        ResultSet resultSet = ServerDatabase.getInstance().getHost();
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return parseAuthUserResultSet(resultSet);
    }

    public void setHost(AuthUser sender) throws SQLException {
        ServerDatabase.getInstance().updateRole(sender.getUserid(),"HOST");
    }

    public boolean isHost(AuthUser authSender) throws SQLException {
        ResultSet resultSet = ServerDatabase.getInstance().isHost(authSender.getUserid());
        boolean result = resultSet.getString("user_role") != null &&
                resultSet.getString("user_role").equals("HOST");
        if(result)
            System.out.println("HOST IS HERE!!!");
        else
            System.out.println("NAKLI HOST!!!");
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return result;
    }

    public boolean hostAssigned() throws SQLException {
        ResultSet resultSet = ServerDatabase.getInstance().hostAssigned();
        boolean result = resultSet.getInt(1) != 0 ;
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return result;
    }

    public HashMap<String,String> getAllUserRole() throws SQLException {
        HashMap<String,String> result = new HashMap<>();
        ResultSet resultSet = ServerDatabase.getInstance().getAllUserRole();
        while (resultSet.next()) {
            String user_id = resultSet.getString("user_id");
            String user_role = resultSet.getString("user_role");
            if(user_role == null)
                result.put(user_id,"NORMAL");
            else
                result.put(user_id,user_role);
        }
        resultSet.close();
        ServerDatabase.getInstance().closeConnection();
        return result;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setReady(AuthUser user) {
        readyUsers.add(user.getUserid());
    }

    public void setNotReady(AuthUser user) {readyUsers.remove(user.getUserid());}

    public HashSet<String> getAllReadyPlayers() {
        return readyUsers;
    }

    public boolean isAllReady() {
        try {
            for(var u : getAllUserID()) {
                if(!readyUsers.contains(u))
                    return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
