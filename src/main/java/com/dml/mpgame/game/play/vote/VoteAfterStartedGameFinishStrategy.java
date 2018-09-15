package com.dml.mpgame.game.play.vote;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GameState;
import com.dml.mpgame.game.GameValueObject;
import com.dml.mpgame.game.finish.GameFinishStrategy;
import com.dml.mpgame.game.finish.GameFinishStrategyValueObject;

/**
 * 游戏一旦开始只有投票才能结束。没开始的时候，主机要求结束那就直接解散，付机要求结束那就自己退出。
 * 
 * @author Neo
 *
 */
public class VoteAfterStartedGameFinishStrategy implements GameFinishStrategy {

	private String hostPlayerId;

	private GameFinishVote vote;

	private VoteCalculator voteCalculator;

	public VoteAfterStartedGameFinishStrategy() {
	}

	public VoteAfterStartedGameFinishStrategy(String hostPlayerId, VoteCalculator voteCalculator) {
		this.hostPlayerId = hostPlayerId;
		this.voteCalculator = voteCalculator;
	}

	@Override
	public GameValueObject finish(String playerId, Game game) throws Exception {
		if (game.getState().equals(GameState.playing)) {
			if (vote == null || vote.getResult() == null) {
				vote = new GameFinishVote(voteCalculator);
				return vote(playerId, VoteOption.yes, game);
			} else {
				throw new VoteAlreadyExistsException();
			}
		} else {
			if (playerId.equals(hostPlayerId)) {
				return game.doFinish();
			} else {
				return game.quit(playerId);
			}
		}
	}

	public GameValueObject vote(String playerId, VoteOption option, Game game) throws Exception {
		vote.vote(playerId, option);
		vote.calculateResult(game);
		VoteResult voteResult = vote.getResult();
		if (voteResult != null) {// 出结果了
			if (voteResult.equals(VoteResult.yes)) {// 通过
				return game.doFinish();
			}
		}
		return new GameValueObject(game);
	}

	@Override
	public GameFinishStrategyValueObject generateValueObject() {
		return new VoteAfterStartedGameFinishStrategyValueObject(this);
	}

	public String getHostPlayerId() {
		return hostPlayerId;
	}

	public void setHostPlayerId(String hostPlayerId) {
		this.hostPlayerId = hostPlayerId;
	}

	public GameFinishVote getVote() {
		return vote;
	}

	public void setVote(GameFinishVote vote) {
		this.vote = vote;
	}

}
