package com.dml.mpgame.game.play.vote;

import com.dml.mpgame.game.Game;

public interface VoteCalculator {
	public void calculateResult(GameFinishVote vote, Game game);
}
