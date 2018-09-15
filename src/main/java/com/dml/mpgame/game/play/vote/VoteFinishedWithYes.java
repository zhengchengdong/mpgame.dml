package com.dml.mpgame.game.play.vote;

import com.dml.mpgame.game.play.GamePlayProcessState;

public class VoteFinishedWithYes implements GamePlayProcessState {

	public static final String name = "VoteFinishedWithYes";

	@Override
	public String name() {
		return name;
	}

}
