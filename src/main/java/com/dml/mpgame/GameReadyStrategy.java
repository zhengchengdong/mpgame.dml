package com.dml.mpgame;

public interface GameReadyStrategy {

	public GameValueObject ready(String playerId, Game game) throws Exception;

}
