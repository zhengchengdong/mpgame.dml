package com.dml.mpgame.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameNotFoundException;
import com.dml.mpgame.game.player.PlayerNotInGameException;

public class GameServer {

	private String id;
	private Map<String, Game> gameIdGameMap = new HashMap<>();

	/**
	 * 一台服务器对于同一个用户（一个socket）同时只能玩一场游戏。
	 */
	private Map<String, String> playerIdGameIdMap = new HashMap<>();

	public void playerCreateGame(Game newGame, String playerId) {
		gameIdGameMap.put(newGame.getId(), newGame);
		playerIdGameIdMap.put(playerId, newGame.getId());
	}

	public void join(String playerId, String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		game.join(playerId);
		playerIdGameIdMap.put(playerId, gameId);
	}

	public void ready(String playerId) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		Game game = gameIdGameMap.get(gameId);
		game.ready(playerId);
	}

	public void leave(String playerId) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			throw new GameNotFoundException();
		}
		Game game = gameIdGameMap.get(gameId);
		game.leave(playerId);
		playerIdGameIdMap.remove(playerId);
	}

	public void back(String playerId, String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		game.back(playerId);
		playerIdGameIdMap.put(playerId, gameId);
	}

	/**
	 * 游戏结束，从服务器中注销
	 */
	public void finishGame(String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		gameIdGameMap.remove(gameId);
		game.allPlayerIds().forEach((pid) -> playerIdGameIdMap.remove(pid));
	}

	public List<String> findAllPlayerIdsForGame(String gameId) {
		Game game = gameIdGameMap.get(gameId);
		if (game != null) {
			return game.allPlayerIds();
		} else {
			return null;
		}
	}

	public void bindPlayer(String playerId, String gameId) {
		playerIdGameIdMap.put(playerId, gameId);
	}

	public String findBindGameId(String playerId) {
		return playerIdGameIdMap.get(playerId);
	}

	public Game findGame(String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		return game;
	}

	public Game findGamePlayerPlaying(String playerId) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		return gameIdGameMap.get(gameId);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Game> getGameIdGameMap() {
		return gameIdGameMap;
	}

	public void setGameIdGameMap(Map<String, Game> gameIdGameMap) {
		this.gameIdGameMap = gameIdGameMap;
	}

	public Map<String, String> getPlayerIdGameIdMap() {
		return playerIdGameIdMap;
	}

	public void setPlayerIdGameIdMap(Map<String, String> playerIdGameIdMap) {
		this.playerIdGameIdMap = playerIdGameIdMap;
	}

}
