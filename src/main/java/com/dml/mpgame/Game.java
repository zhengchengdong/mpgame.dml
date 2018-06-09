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
	private GameState state = GameState.waitingStart;
	private String createPlayerId;
	private Map<String, GamePlayer> idPlayerMap = new HashMap<>();
	private FinishVote finishVote;

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

}
