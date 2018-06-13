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

	public String getId() {
		return id;
	}

	public String getCreatePlayerId() {
		return createPlayerId;
	}

}
