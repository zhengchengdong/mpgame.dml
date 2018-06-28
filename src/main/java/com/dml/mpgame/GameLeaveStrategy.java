package com.dml.mpgame;

public interface GameLeaveStrategy {
	public void leave(String playerId, Game game) throws Exception;
}
