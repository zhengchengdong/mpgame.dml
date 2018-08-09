package com.dml.mpgame.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameNotFoundException;
import com.dml.mpgame.game.GameValueObject;
import com.dml.mpgame.game.PlayerNotInGameException;
import com.dml.mpgame.game.finish.GameFinishVote;
import com.dml.mpgame.game.finish.GameFinishVoteValueObject;
import com.dml.mpgame.game.finish.GameIsNotVoteToFinishException;
import com.dml.mpgame.game.finish.VoteAlreadyExistsException;
import com.dml.mpgame.game.finish.VoteCalculator;
import com.dml.mpgame.game.finish.VoteOption;
import com.dml.mpgame.game.finish.VoteResult;
import com.dml.mpgame.game.join.GameJoinStrategy;
import com.dml.mpgame.game.leave.GameLeaveStrategy;
import com.dml.mpgame.game.ready.GameReadyStrategy;

public class GameServer {

	private String id;
	private Map<String, Game> gameIdGameMap = new HashMap<>();
	private Map<String, GameFinishVote> gameIdFinishVoteMap = new HashMap<>();

	/**
	 * 一台服务器对于同一个用户（一个socket）同时只能玩一场游戏。
	 */
	private Map<String, String> playerIdGameIdMap = new HashMap<>();

	public void playerCreateGame(String gameId, GameJoinStrategy joinStrategy, GameReadyStrategy readyStrategy,
			GameLeaveStrategy leaveStrategy, String playerId) {
		Game newGame = new Game();
		newGame.setGameJoinStrategy(joinStrategy);
		newGame.setReadyStrategy(readyStrategy);
		newGame.setLeaveStrategy(leaveStrategy);
		newGame.create(gameId, playerId);
		gameIdGameMap.put(gameId, newGame);
		playerIdGameIdMap.put(playerId, gameId);
	}

	public GameValueObject join(String playerId, String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		GameValueObject gameValueObject = game.join(playerId);
		playerIdGameIdMap.put(playerId, gameId);
		return gameValueObject;
	}

	public GameValueObject ready(String playerId) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		Game game = gameIdGameMap.get(gameId);
		return game.ready(playerId);
	}

	public GameValueObject leave(String playerId) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			return null;
		}
		Game game = gameIdGameMap.get(gameId);
		GameValueObject gameValueObject = game.leave(playerId);
		playerIdGameIdMap.remove(playerId);
		return gameValueObject;
	}

	public GameValueObject back(String playerId, String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		GameValueObject gameValueObject = game.back(playerId);
		playerIdGameIdMap.put(playerId, gameId);
		return gameValueObject;
	}

	public GameValueObject finish(String gameId) throws Exception {
		Game game = gameIdGameMap.get(gameId);
		if (game == null) {
			throw new GameNotFoundException();
		}
		GameValueObject gameValueObject = game.finish();
		gameIdGameMap.remove(gameId);
		game.allPlayerIds().forEach((pid) -> playerIdGameIdMap.remove(pid));
		gameIdFinishVoteMap.remove(gameId);
		return gameValueObject;
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

	/**
	 * 发起结束投票.发起人认为是投同意结束
	 */
	public GameFinishVoteValueObject startFinishVote(String playerId, VoteCalculator voteCalculator) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		GameFinishVote vote = gameIdFinishVoteMap.get(gameId);
		if (vote != null) {
			throw new VoteAlreadyExistsException();
		}
		vote = new GameFinishVote(gameId, voteCalculator);
		gameIdFinishVoteMap.put(gameId, vote);

		return voteToFinishGame(playerId, VoteOption.yes);

	}

	public GameFinishVoteValueObject voteToFinishGame(String playerId, VoteOption option) throws Exception {
		String gameId = playerIdGameIdMap.get(playerId);
		if (gameId == null) {
			throw new PlayerNotInGameException();
		}
		GameFinishVote vote = gameIdFinishVoteMap.get(gameId);
		if (vote == null) {
			throw new GameIsNotVoteToFinishException();
		}
		vote.vote(playerId, option);
		Game game = gameIdGameMap.get(gameId);
		vote.calculateResult(game);
		VoteResult voteResult = vote.getResult();
		if (voteResult != null) {// 出结果了
			if (voteResult.equals(VoteResult.yes)) {// 通过
				finish(gameId);
			}
			gameIdFinishVoteMap.remove(gameId);
		}
		return new GameFinishVoteValueObject(vote);
	}

	public GameFinishVoteValueObject calculateVoteResultToFinishGame(String gameId) throws Exception {
		GameFinishVote vote = gameIdFinishVoteMap.get(gameId);
		if (vote == null) {
			throw new GameIsNotVoteToFinishException();
		}
		Game game = gameIdGameMap.get(gameId);
		vote.calculateResult(game);
		VoteResult voteResult = vote.getResult();
		if (voteResult != null) {// 出结果了
			if (voteResult.equals(VoteResult.yes)) {// 通过
				finish(gameId);
			}
			gameIdFinishVoteMap.remove(gameId);
		}
		return new GameFinishVoteValueObject(vote);
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
