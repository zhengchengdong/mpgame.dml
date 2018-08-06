package com.dml.mpgame;

public interface GameJoinStrategy {
	public GameValueObject join(String playerId, Game game) throws Exception;
}
