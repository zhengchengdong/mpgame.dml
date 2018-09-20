package com.dml.mpgame.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dml.mpgame.game.back.GameBackStrategy;
import com.dml.mpgame.game.join.GameJoinStrategy;
import com.dml.mpgame.game.leave.GameLeaveStrategy;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.GamePlayerAlreadyInGameException;
import com.dml.mpgame.game.player.GamePlayerNotFoundException;
import com.dml.mpgame.game.player.GamePlayerOnlineState;
import com.dml.mpgame.game.player.GamePlayerState;
import com.dml.mpgame.game.player.PlayerJoined;
import com.dml.mpgame.game.player.PlayerReadyToStart;
import com.dml.mpgame.game.ready.GameReadyStrategy;

/**
 * 一次游戏
 * 
 * @author Neo
 *
 */
public abstract class Game {

	protected String id;
	protected String createPlayerId;
	protected GameState state;
	protected Map<String, GamePlayer> idPlayerMap = new HashMap<>();

	private GameLeaveStrategy leaveStrategy;
	private GameBackStrategy backStrategy;
	private GameReadyStrategy readyStrategy;
	private GameJoinStrategy joinStrategy;

	public void create(String id, String createPlayerId) {
		this.id = id;
		this.createPlayerId = createPlayerId;
		newPlayer(createPlayerId);
		state = new WaitingStart();
	}

	public void newPlayer(String playerId) {
		GamePlayer player = new GamePlayer();
		player.setId(playerId);
		player.setState(new PlayerJoined());
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
		joinStrategy.join(playerId, this);
	}

	public void leave(String playerId) throws Exception {
		leaveStrategy.leave(playerId, this);
	}

	public GameBackStrategy getBackStrategy() {
		return backStrategy;
	}

	public void setBackStrategy(GameBackStrategy backStrategy) {
		this.backStrategy = backStrategy;
	}

	public void quit(String playerId) throws Exception {
		if (!idPlayerMap.containsKey(playerId)) {
			throw new GamePlayerNotFoundException();
		}
		idPlayerMap.remove(playerId);
	}

	public void back(String playerId) throws Exception {
		backStrategy.back(playerId, this);
	}

	public void ready(String playerId) throws Exception {
		readyStrategy.ready(playerId, this);
	}

	public void cancel() throws Exception {
		if (state.name().equals(WaitingStart.name)) {
			state = new Canceled();
		} else {
			throw new IllegalOperationException();
		}
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
			if (!player.getState().name().equals(PlayerReadyToStart.name)) {
				return false;
			}
		}
		return true;
	}

	public abstract void start() throws Exception;

	public abstract void finish() throws Exception;

	public void updateAllPlayersState(GamePlayerState playerState) {
		for (GamePlayer player : idPlayerMap.values()) {
			player.setState(playerState);
		}
	}

	public List<String> allPlayerIds() {
		return new ArrayList<>(idPlayerMap.keySet());
	}

	public GamePlayer findPlayer(String playerId) {
		return idPlayerMap.get(playerId);
	}

	public Set<String> findOnlinePlayerIds() {
		Set<String> onlinePlayerIds = new HashSet<>();
		for (GamePlayer player : idPlayerMap.values()) {
			if (player.getOnlineState().equals(GamePlayerOnlineState.online)) {
				onlinePlayerIds.add(player.getId());
			}
		}
		return onlinePlayerIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatePlayerId() {
		return createPlayerId;
	}

	public void setCreatePlayerId(String createPlayerId) {
		this.createPlayerId = createPlayerId;
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

	public GameJoinStrategy getJoinStrategy() {
		return joinStrategy;
	}

	public void setJoinStrategy(GameJoinStrategy joinStrategy) {
		this.joinStrategy = joinStrategy;
	}

}
