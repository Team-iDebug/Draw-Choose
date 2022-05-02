package com.iDebug.pickloose;

import com.iDebug.pickloose.database.client.ClientDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager {
    private static UserManager userManager;
    private UserManager() {

    }
    public static UserManager getInstance() {
        if(userManager == null)
            userManager = new UserManager();
        return userManager;
    }
    public AuthUser parseAuthUserResultSet(ResultSet resultSet) throws SQLException {
        String user_id = resultSet.getString("user_id");
        String username = resultSet.getString("username");
        String avatar = resultSet.getString("avatar");
        return new AuthUser(user_id,username,avatar);
    }
    public ArrayList<AuthUser> getPlayers() throws SQLException {
        ArrayList<AuthUser> authUsers = new ArrayList<>();
        ResultSet resultSet = ClientDatabase.getInstance().getAllUser();
        while (resultSet.next()) {
            authUsers.add(parseAuthUserResultSet(resultSet));
        }
        resultSet.close();
        ClientDatabase.getInstance().closeConnection();
        return authUsers;
    }
    public void add(AuthUser user) throws SQLException {
        // model data (database)
        String user_id = user.getUserid();
        String username = user.getUsername();
        String avatar = user.getAvatar();
        ResultSet resultSet = ClientDatabase.getInstance().userExists(user_id);
        boolean exists = resultSet.getInt(1) != 0;
        resultSet.close();
        ClientDatabase.getInstance().closeConnection();
        if(exists)
            return;
        ClientDatabase.getInstance().addUser(user_id,username,avatar);
        // view data (gui)
        switch (WindowManager.getInstance().getScene()) {
            case LOBBY -> LobbyManager.getInstance().addPlayer(user);
        }
    }
    public void remove(AuthUser user) throws SQLException {
        // model
        ResultSet resultSet = ClientDatabase.getInstance().userExists(user.getUserid());
        boolean exists = resultSet.getInt(1) != 0;
        resultSet.close();
        ClientDatabase.getInstance().closeConnection();
        if(!exists)
            return;
        ClientDatabase.getInstance().deleteUser(user.getUserid());
        // view
        switch (WindowManager.getInstance().getScene()) {
            case LOBBY -> LobbyManager.getInstance().removePlayer(user);
            case GAME -> GameManager.getInstance().removePlayer(user);
        }
    }
    public void setRole(String userid, String role) throws SQLException {
        // model
        ClientDatabase.getInstance().updateRole(userid,role);
        // view
        if(!role.equals("HOST"))
            return;
        ResultSet resultSet = ClientDatabase.getInstance().getUserInfo(userid);
        AuthUser authUser = parseAuthUserResultSet(resultSet);
        resultSet.close();
        ClientDatabase.getInstance().closeConnection();
        switch (WindowManager.getInstance().getScene()) {
            case LOBBY -> LobbyManager.getInstance().setHost(authUser);
        }
    }
    public boolean isHost(String userid) throws SQLException {
        ResultSet resultSet = ClientDatabase.getInstance().isHost(userid);
        boolean result = (resultSet.getString(1) != null && resultSet.getString(1).equals("HOST"));
        resultSet.close();
        ClientDatabase.getInstance().closeConnection();
        return result;
    }
}
