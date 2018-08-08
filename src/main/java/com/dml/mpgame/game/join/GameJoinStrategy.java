package com.dml.mpgame.game.join;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameValueObject;

public interface GameJoinStrategy {
	public GameValueObject join(String playerId, Game game) throws Exception;
}
