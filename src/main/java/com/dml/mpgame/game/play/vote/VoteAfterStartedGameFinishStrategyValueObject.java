package com.dml.mpgame.game.play.vote;

import com.dml.mpgame.game.finish.GameFinishStrategyValueObject;

public class VoteAfterStartedGameFinishStrategyValueObject implements GameFinishStrategyValueObject {

	private String hostPlayerId;

	private GameFinishVoteValueObject vote;

	public VoteAfterStartedGameFinishStrategyValueObject() {
	}

	public VoteAfterStartedGameFinishStrategyValueObject(
			VoteAfterStartedGameFinishStrategy voteAfterStartedGameFinishStrategy) {
		hostPlayerId = voteAfterStartedGameFinishStrategy.getHostPlayerId();
		if (voteAfterStartedGameFinishStrategy.getVote() != null) {
			vote = new GameFinishVoteValueObject(voteAfterStartedGameFinishStrategy.getVote());
		}
	}

	public String getHostPlayerId() {
		return hostPlayerId;
	}

	public void setHostPlayerId(String hostPlayerId) {
		this.hostPlayerId = hostPlayerId;
	}

	public GameFinishVoteValueObject getVote() {
		return vote;
	}

	public void setVote(GameFinishVoteValueObject vote) {
		this.vote = vote;
	}

}
