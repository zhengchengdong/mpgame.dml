package com.dml.mpgame.game.finish;

import com.dml.mpgame.game.Game;

public interface VoteCalculator {
	public void calculateResult(GameFinishVote vote, Game game);
}
