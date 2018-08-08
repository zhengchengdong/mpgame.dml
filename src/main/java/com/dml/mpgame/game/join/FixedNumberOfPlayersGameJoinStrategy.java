package com.dml.mpgame.game.join;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameValueObject;

public class FixedNumberOfPlayersGameJoinStrategy implements GameJoinStrategy {

	private int fixedNumberOfPlayers;

	public FixedNumberOfPlayersGameJoinStrategy() {
	}

	public FixedNumberOfPlayersGameJoinStrategy(int fixedNumberOfPlayers) {
		this.fixedNumberOfPlayers = fixedNumberOfPlayers;
	}

	@Override
	public GameValueObject join(String playerId, Game game) throws Exception {
		if (game.playerCounts() == fixedNumberOfPlayers) {
			throw new CanNotJoinMorePlayersException();
		}
		game.newPlayer(playerId);
		return new GameValueObject(game);
	}

}
