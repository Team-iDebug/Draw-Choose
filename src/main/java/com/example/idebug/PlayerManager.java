package com.example.idebug;

import java.util.HashSet;

public class PlayerManager {
    private HashSet <AuthPlayer> players;
    PlayerManager() {
        players = new HashSet<>();
    }
    public void add(AuthPlayer player) {
        players.add(player);
    }
    public boolean has(AuthPlayer player) {
        return players.contains(player);
    }
    public HashSet<AuthPlayer> getPlayers() {
        return players;
    }
}
