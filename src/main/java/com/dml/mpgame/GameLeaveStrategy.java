package com.dml.mpgame;

public interface GameLeaveStrategy {
	public GameValueObject leave(String playerId, Game game) throws Exception;
}
