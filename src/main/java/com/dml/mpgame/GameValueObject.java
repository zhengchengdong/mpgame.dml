package com.dml.mpgame;

import java.util.ArrayList;
import java.util.List;

public class GameValueObject {

	private String id;
	private GameState state;
	private List<GamePlayer> players;

	public GameValueObject(Game game) {
		id = game.getId();
		state = game.getState();
		players = new ArrayList<GamePlayer>();
		game.getIdPlayerMap().values().forEach((player) -> {
			GamePlayer copy = new GamePlayer();
			copy.setId(player.getId());
			copy.setState(player.getState());
			copy.setOnlineState(player.getOnlineState());
			players.add(copy);
		});
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

	public List<GamePlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayer> players) {
		this.players = players;
	}

}
