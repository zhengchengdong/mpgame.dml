package com.dml.mpgame.game.extend.vote;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameFinishVoteValueObject {

	private Set<String> votePlayerIds;

	private Map<String, VoteOption> playerIdVoteOptionMap;

	private VoteResult result;

	public GameFinishVoteValueObject() {
	}

	public Set<String> getVotePlayerIds() {
		return votePlayerIds;
	}

	public void setVotePlayerIds(Set<String> votePlayerIds) {
		this.votePlayerIds = votePlayerIds;
	}

	public GameFinishVoteValueObject(GameFinishVote vote) {
		votePlayerIds = new HashSet<>(vote.getVotePlayerIds());
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
