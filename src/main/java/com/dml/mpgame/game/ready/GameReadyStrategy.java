package com.dml.mpgame.game.ready;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameValueObject;

public interface GameReadyStrategy {

	public GameValueObject ready(String playerId, Game game) throws Exception;

}
