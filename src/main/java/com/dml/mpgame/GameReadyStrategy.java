package com.dml.mpgame;

public interface GameReadyStrategy {

	public void ready(String playerId, Game game) throws Exception;

}
