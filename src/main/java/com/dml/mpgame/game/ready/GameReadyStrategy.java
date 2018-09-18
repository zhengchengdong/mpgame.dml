package com.dml.mpgame.game.ready;

import com.dml.mpgame.game.Game;

public interface GameReadyStrategy {

	public void ready(String playerId, Game game) throws Exception;

}
