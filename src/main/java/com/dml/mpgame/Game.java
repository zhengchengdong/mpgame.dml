package com.dml.mpgame;

import java.util.HashMap;
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
	private String createPlayerId;
	private Map<String, GamePlayer> idPlayerMap = new HashMap<>();
	private FinishVote finishVote;

	public void create(String id, String createPlayerId) {
		this.id = id;
		this.createPlayerId = createPlayerId;
		GamePlayer player = new GamePlayer();
		player.setId(createPlayerId);
		player.setState(GamePlayerState.joined);
		idPlayerMap.put(createPlayerId, player);
		state = GameState.waitingStart;
	}

	public void leave(String playerId) throws GamePlayerNotFoundException {
		GamePlayer player = idPlayerMap.get(playerId);
		if (player == null) {
			throw new GamePlayerNotFoundException();
		}
		player.setState(GamePlayerState.leave);
	}

	public void quit(String playerId) throws GamePlayerNotFoundException {
		if (!idPlayerMap.containsKey(playerId)) {
			throw new GamePlayerNotFoundException();
		}
		idPlayerMap.remove(playerId);
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

	public String getCreatePlayerId() {
		return createPlayerId;
	}

	public void setCreatePlayerId(String createPlayerId) {
		this.createPlayerId = createPlayerId;
	}

	public Map<String, GamePlayer> getIdPlayerMap() {
		return idPlayerMap;
	}

	public void setIdPlayerMap(Map<String, GamePlayer> idPlayerMap) {
		this.idPlayerMap = idPlayerMap;
	}

	public FinishVote getFinishVote() {
		return finishVote;
	}

	public void setFinishVote(FinishVote finishVote) {
		this.finishVote = finishVote;
	}

}
