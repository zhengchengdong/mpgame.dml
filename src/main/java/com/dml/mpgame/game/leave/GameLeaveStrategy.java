package com.dml.mpgame.game.leave;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameValueObject;

public interface GameLeaveStrategy {
	public GameValueObject leave(String playerId, Game game) throws Exception;
}
