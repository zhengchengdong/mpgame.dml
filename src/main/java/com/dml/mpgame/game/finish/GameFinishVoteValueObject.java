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

}
