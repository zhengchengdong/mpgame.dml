package com.dml.mpgame;

public class FixedNumberOfPlayersGameJoinStrategy implements GameJoinStrategy {

	private int fixedNumberOfPlayers;

	public FixedNumberOfPlayersGameJoinStrategy() {
	}

	public FixedNumberOfPlayersGameJoinStrategy(int fixedNumberOfPlayers) {
		this.fixedNumberOfPlayers = fixedNumberOfPlayers;
	}

	@Override
	public void join(String playerId, Game game) throws Exception {
		if (game.playerCounts() == fixedNumberOfPlayers) {
			throw new CanNotJoinMorePlayersException();
		}
		game.newPlayer(playerId);
	}

}
