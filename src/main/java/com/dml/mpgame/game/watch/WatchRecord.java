package com.dml.mpgame.game.watch;

import java.util.List;

/**
 * @author yins
 * @Description: 观战记录bean
 */
public class WatchRecord {
    private String id;
    private String gameId; // game server id
    private List<Watcher> watchers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Watcher> getWatchers() {
        return watchers;
    }

    public void setWatchers(List<Watcher> watchers) {
        this.watchers = watchers;
    }
}
