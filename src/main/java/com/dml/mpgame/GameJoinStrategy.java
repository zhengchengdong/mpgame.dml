package com.dml.mpgame;

public interface GameJoinStrategy {
	public void join(String playerId, Game game) throws Exception;
}
