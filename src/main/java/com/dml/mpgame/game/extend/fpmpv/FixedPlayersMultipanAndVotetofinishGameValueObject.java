package com.dml.mpgame.game.extend.fpmpv;

import java.util.HashSet;
import java.util.Set;

import com.dml.mpgame.game.GameValueObject;
import com.dml.mpgame.game.extend.vote.GameFinishVoteValueObject;

public abstract class FixedPlayersMultipanAndVotetofinishGameValueObject extends GameValueObject {

	private int fixedPlayerCount;

	private GameFinishVoteValueObject vote;

	private Set<String> readyToStartNextPanPlayerIdsSet;

	public FixedPlayersMultipanAndVotetofinishGameValueObject(FixedPlayersMultipanAndVotetofinishGame game) {
		super(game);
		fixedPlayerCount = game.getFixedPlayerCount();
		vote = new GameFinishVoteValueObject(game.getVote());
		readyToStartNextPanPlayerIdsSet = new HashSet<>(game.getReadyToStartNextPanPlayerIdsSet());
	}

	public int getFixedPlayerCount() {
		return fixedPlayerCount;
	}

	public void setFixedPlayerCount(int fixedPlayerCount) {
		this.fixedPlayerCount = fixedPlayerCount;
	}

	public GameFinishVoteValueObject getVote() {
		return vote;
	}

	public void setVote(GameFinishVoteValueObject vote) {
		this.vote = vote;
	}

	public Set<String> getReadyToStartNextPanPlayerIdsSet() {
		return readyToStartNextPanPlayerIdsSet;
	}

	public void setReadyToStartNextPanPlayerIdsSet(Set<String> readyToStartNextPanPlayerIdsSet) {
		this.readyToStartNextPanPlayerIdsSet = readyToStartNextPanPlayerIdsSet;
	}

}
