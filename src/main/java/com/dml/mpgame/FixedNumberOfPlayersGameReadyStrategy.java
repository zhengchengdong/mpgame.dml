package com.dml.mpgame;

public class FixedNumberOfPlayersGameReadyStrategy implements GameReadyStrategy {

	private int fixedNumberOfPlayers;

	public FixedNumberOfPlayersGameReadyStrategy() {
	}

	public FixedNumberOfPlayersGameReadyStrategy(int fixedNumberOfPlayers) {
		this.fixedNumberOfPlayers = fixedNumberOfPlayers;
	}

	@Override
	public GameValueObject ready(String playerId, Game game) throws Exception {

		if (!game.getState().equals(GameState.waitingStart)) {
			throw new IllegalOperationException();
		}
		if (!game.playerState(playerId).equals(GamePlayerState.joined)) {
			throw new IllegalOperationException();
		}
		game.updatePlayerState(playerId, GamePlayerState.readyToStart);

		int playerCounts = game.playerCounts();
		if (playerCounts == fixedNumberOfPlayers) {// 达到游戏规定人数
			if (game.allPlayersReady()) {// 并且所有玩家都准备好了，那就开始
				game.start();
			}
		}
		return new GameValueObject(game);
	}

}
