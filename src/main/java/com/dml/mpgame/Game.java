package com.dml.mpgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 一次游戏
 * 
 * @author Neo
 *
 */
public class Game {

	private String id;
	private GameState state;
	private Map<String, GamePlayer> idPlayerMap = new HashMap<>();

	private GameLeaveStrategy leaveStrategy;
	private GameReadyStrategy readyStrategy;
	private GameJoinStrategy gameJoinStrategy;

	private FinishVote finishVote;

	public void create(String id, String createPlayerId) {
		this.id = id;
		newPlayer(createPlayerId);
		state = GameState.waitingStart;
	}

	public void newPlayer(String playerId) {
		GamePlayer player = new GamePlayer();
		player.setId(playerId);
		player.setState(GamePlayerState.joined);
		player.setOnlineState(GamePlayerOnlineState.online);
		idPlayerMap.put(playerId, player);
	}

	public int playerCounts() {
		return idPlayerMap.size();
	}

	public void join(String playerId) throws Exception {
		if (idPlayerMap.containsKey(playerId)) {
			throw new GamePlayerAlreadyInGameException();
		}
		gameJoinStrategy.join(playerId, this);
	}

	public void leave(String playerId) throws Exception {
		leaveStrategy.leave(playerId, this);
	}

	public void back(String playerId) throws Exception {
		updatePlayerOnlineState(playerId, GamePlayerOnlineState.online);
	}

	public void ready(String playerId) throws Exception {
		readyStrategy.ready(playerId, this);
	}

	public void updatePlayerState(String playerId, GamePlayerState playerState) throws GamePlayerNotFoundException {
		GamePlayer player = idPlayerMap.get(playerId);
		if (player == null) {
			throw new GamePlayerNotFoundException();
		}
		player.setState(playerState);
	}

	public void updatePlayerOnlineState(String playerId, GamePlayerOnlineState onlineState)
			throws GamePlayerNotFoundException {
		GamePlayer player = idPlayerMap.get(playerId);
		if (player == null) {
			throw new GamePlayerNotFoundException();
		}
		player.setOnlineState(onlineState);
	}

	public void removePlayer(String playerId) throws GamePlayerNotFoundException {
		if (!idPlayerMap.containsKey(playerId)) {
			throw new GamePlayerNotFoundException();
		}
		idPlayerMap.remove(playerId);
	}

	public GamePlayerState playerState(String playerId) {
		GamePlayer player = idPlayerMap.get(playerId);
		if (player != null) {
			return player.getState();
		} else {
			return null;
		}
	}

	public boolean allPlayersReady() {
		for (GamePlayer player : idPlayerMap.values()) {
			if (!player.getState().equals(GamePlayerState.readyToStart)) {
				return false;
			}
		}
		return true;
	}

	public void start() {
		for (GamePlayer player : idPlayerMap.values()) {
			player.setState(GamePlayerState.playing);
		}
		state = GameState.playing;
	}

	public List<String> allPlayerIds() {
		return new ArrayList<>(idPlayerMap.keySet());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public Map<String, GamePlayer> getIdPlayerMap() {
		return idPlayerMap;
	}

	public void setIdPlayerMap(Map<String, GamePlayer> idPlayerMap) {
		this.idPlayerMap = idPlayerMap;
	}

	public GameLeaveStrategy getLeaveStrategy() {
		return leaveStrategy;
	}

	public void setLeaveStrategy(GameLeaveStrategy leaveStrategy) {
		this.leaveStrategy = leaveStrategy;
	}

	public GameReadyStrategy getReadyStrategy() {
		return readyStrategy;
	}

	public void setReadyStrategy(GameReadyStrategy readyStrategy) {
		this.readyStrategy = readyStrategy;
	}

	public GameJoinStrategy getGameJoinStrategy() {
		return gameJoinStrategy;
	}

	public void setGameJoinStrategy(GameJoinStrategy gameJoinStrategy) {
		this.gameJoinStrategy = gameJoinStrategy;
	}

	public FinishVote getFinishVote() {
		return finishVote;
	}

	public void setFinishVote(FinishVote finishVote) {
		this.finishVote = finishVote;
	}

}
