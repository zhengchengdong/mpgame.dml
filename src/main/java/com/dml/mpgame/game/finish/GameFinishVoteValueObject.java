package com.dml.mpgame.game.finish;

import java.util.HashMap;
import java.util.Map;

public class GameFinishVoteValueObject {

	private String gameId;

	private Map<String, VoteOption> playerIdVoteOptionMap;

	private VoteResult result;

	public GameFinishVoteValueObject() {
	}

	public GameFinishVoteValueObject(GameFinishVote vote) {
		gameId = vote.getGameId();
		playerIdVoteOptionMap = new HashMap<>(vote.getPlayerIdVoteOptionMap());
		result = vote.getResult();
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public Map<String, VoteOption> getPlayerIdVoteOptionMap() {
		return playerIdVoteOptionMap;
	}

	public void setPlayerIdVoteOptionMap(Map<String, VoteOption> playerIdVoteOptionMap) {
		this.playerIdVoteOptionMap = playerIdVoteOptionMap;
	}

	public VoteResult getResult() {
		return result;
	}

	public void setResult(VoteResult result) {
		this.result = result;
	}

}
