package com.dml.mpgame.game.extend.vote;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameFinishVote {

	private VoteCalculator calculator;

	private Set<String> votePlayerIds;

	private Map<String, VoteOption> playerIdVoteOptionMap = new HashMap<>();

	private VoteResult result;

	public GameFinishVote() {
	}

	public GameFinishVote(VoteCalculator calculator, Set<String> votePlayerIds) {
		this.calculator = calculator;
		this.votePlayerIds = votePlayerIds;
	}

	public void vote(String playerId, VoteOption option) throws Exception {
		if (result != null) {
			throw new VoteIsFinishedException();
		}
		if (ifPlayerVoted(playerId)) {
			throw new PlayerAlreadyVoteException();
		}
		playerIdVoteOptionMap.put(playerId, option);
	}

	public void join(String playerId) {
		votePlayerIds.add(playerId);
	}

	public void calculateResult() {
		calculator.calculateResult(this);
	}

	public VoteOption findPlayerVoteOption(String playerId) {
		return playerIdVoteOptionMap.get(playerId);
	}

	public boolean ifPlayerVoted(String playerId) {
		return playerIdVoteOptionMap.containsKey(playerId);
	}

	public boolean ifPlayerJoinVote(String playerId) {
		return votePlayerIds.contains(playerId);
	}

	public VoteCalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(VoteCalculator calculator) {
		this.calculator = calculator;
	}

	public Set<String> getVotePlayerIds() {
		return votePlayerIds;
	}

	public void setVotePlayerIds(Set<String> votePlayerIds) {
		this.votePlayerIds = votePlayerIds;
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
