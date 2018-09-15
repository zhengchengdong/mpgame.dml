package com.dml.mpgame.game.play.vote;

import com.dml.mpgame.game.play.GamePlayProcessState;

public class FinishedByVote implements GamePlayProcessState {

	public static final String name = "FinishedByVote";

	@Override
	public String name() {
		return name;
	}

}
