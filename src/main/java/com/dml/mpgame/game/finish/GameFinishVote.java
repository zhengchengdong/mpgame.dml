package com.dml.mpgame.game.finish;

import java.util.HashMap;
import java.util.Map;

import com.dml.mpgame.game.Game;

public class GameFinishVote {

	private String gameId;

	private VoteCalculator calculator;

	private Map<String, VoteOption> playerIdVoteOptionMap = new HashMap<>();

	private VoteResult result;

	public GameFinishVote() {
	}

	public GameFinishVote(String gameId, VoteCalculator calculator) {
		this.gameId = gameId;
		this.calculator = calculator;
	}

	public void vote(String playerId, VoteOption option) throws Exception {
		if (result != null) {
			throw new VoteIsFinishedException();
		}
		if (playerIdVoteOptionMap.containsKey(playerId)) {
			throw new PlayerAlreadyVoteException();
		}
		playerIdVoteOptionMap.put(playerId, option);
	}

	public void calculateResult(Game game) {
		calculator.calculateResult(this, game);
	}

	public VoteOption findPlayerVoteOption(String playerId) {
		return playerIdVoteOptionMap.get(playerId);
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public VoteCalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(VoteCalculator calculator) {
		this.calculator = calculator;
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
