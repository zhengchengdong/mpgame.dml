package com.dml.mpgame.game.finish.vote;

import com.dml.mpgame.game.Game;

public interface VoteCalculator {
	public void calculateResult(GameFinishVote vote, Game game);
}
