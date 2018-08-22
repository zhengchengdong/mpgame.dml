package com.dml.mpgame.game.finish;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameValueObject;

public interface GameFinishStrategy {
	public GameValueObject finish(String playerId, Game game) throws Exception;

	public GameFinishStrategyValueObject generateValueObject();
}
