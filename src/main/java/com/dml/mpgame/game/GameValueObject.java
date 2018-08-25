package com.dml.mpgame.game;

import java.util.ArrayList;
import java.util.List;

import com.dml.mpgame.game.finish.GameFinishStrategyValueObject;

public class GameValueObject {

	private String id;
	private GameState state;
	private List<GamePlayerValueObject> players;
	private GameFinishStrategyValueObject finishStrategy;

	public GameValueObject(Game game) {
		id = game.getId();
		state = game.getState();
		players = new ArrayList<>();
		game.getIdPlayerMap().values().forEach((player) -> players.add(new GamePlayerValueObject(player)));
		finishStrategy = game.getFinishStrategy().generateValueObject();
	}

	public GamePlayerOnlineState findPlayerOnlineState(String playerId) {
		for (GamePlayerValueObject player : players) {
			if (player.getId().equals(playerId)) {
				return player.getOnlineState();
			}
		}
		return null;
	}

	public List<String> allPlayerIds() {
		List<String> allPlayerIds = new ArrayList<>();
		players.forEach((player) -> allPlayerIds.add(player.getId()));
		return allPlayerIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public List<GamePlayerValueObject> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayerValueObject> players) {
		this.players = players;
	}

	public GameFinishStrategyValueObject getFinishStrategy() {
		return finishStrategy;
	}

	public void setFinishStrategy(GameFinishStrategyValueObject finishStrategy) {
		this.finishStrategy = finishStrategy;
	}

}
