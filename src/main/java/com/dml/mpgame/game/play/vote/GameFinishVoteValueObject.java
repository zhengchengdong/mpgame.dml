package com.dml.mpgame.game.play.vote;

import java.util.HashMap;
import java.util.Map;

public class GameFinishVoteValueObject {

	private Map<String, VoteOption> playerIdVoteOptionMap;

	private VoteResult result;

	public GameFinishVoteValueObject() {
	}

	public GameFinishVoteValueObject(GameFinishVote vote) {
		playerIdVoteOptionMap = new HashMap<>(vote.getPlayerIdVoteOptionMap());
		result = vote.getResult();
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
