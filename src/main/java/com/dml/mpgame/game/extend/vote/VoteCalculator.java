package com.dml.mpgame.game.extend.vote;

import com.dml.mpgame.game.Game;

public interface VoteCalculator {
	public void calculateResult(GameFinishVote vote, Game game);
}
