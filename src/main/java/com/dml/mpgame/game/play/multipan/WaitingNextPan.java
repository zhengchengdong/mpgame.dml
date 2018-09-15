package com.dml.mpgame.game.play.multipan;

import com.dml.mpgame.game.play.GamePlayProcessState;

public class WaitingNextPan implements GamePlayProcessState {

	public static final String name = "WaitingNextPan";

	@Override
	public String name() {
		return name;
	}

}
