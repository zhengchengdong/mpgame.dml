package com.dml.mpgame.game.extend.fpmpv;

import java.util.HashSet;
import java.util.Set;

import com.dml.mpgame.game.Finished;
import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.IllegalOperationException;
import com.dml.mpgame.game.Playing;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerPanFinishedAndVoted;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerPanFinishedAndVoting;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerReadyToStartNextPanAndVoted;
import com.dml.mpgame.game.extend.fpmpv.player.PlayerReadyToStartNextPanAndVoting;
import com.dml.mpgame.game.extend.multipan.WaitingNextPan;
import com.dml.mpgame.game.extend.multipan.player.PlayerPanFinished;
import com.dml.mpgame.game.extend.multipan.player.PlayerReadyToStartNextPan;
import com.dml.mpgame.game.extend.vote.FinishedByVote;
import com.dml.mpgame.game.extend.vote.GameFinishVote;
import com.dml.mpgame.game.extend.vote.VoteAlreadyLaunchedException;
import com.dml.mpgame.game.extend.vote.VoteCalculator;
import com.dml.mpgame.game.extend.vote.VoteOption;
import com.dml.mpgame.game.extend.vote.VoteResult;
import com.dml.mpgame.game.extend.vote.VotingWhenPlaying;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.PlayerPlaying;

/**
 * 游戏固定玩家人数，过程中要玩多盘（典型的如棋牌类游戏），并且以玩家投票的方式决定是否中止游戏。
 * 
 * @author Neo
 *
 */
public abstract class FixedPlayersMultipanAndVotetofinishGame extends Game {

	/**
	 * 第几盘，从1开始
	 */
	private int panNo = 1;

	private int fixedPlayerCount;

	private GameFinishVote vote;

	private Set<String> readyToStartNextPanPlayerIdsSet = new HashSet<>();

	/**
	 * 结束当前盘，有可能导致整个过程结束
	 */
	public void checkAndFinishPan() throws Exception {
		if (checkToFinishCurrentPan()) {
			// 还要判断游戏是否结束
			if (checkToFinishGame()) {
				finish();
			} else {
				finishCurrentPan();
			}
		}
	}

	private void finishCurrentPan() {
		state = new WaitingNextPan();
		updateAllPlayersState(new PlayerPanFinished());
	}

	protected abstract boolean checkToFinishGame() throws Exception;

	protected abstract boolean checkToFinishCurrentPan() throws Exception;

	public void readyToNextPan(String playerId) throws Exception {
		if (state.name().equals(WaitingNextPan.name)) {
			readyToStartNextPanPlayerIdsSet.add(playerId);
			updatePlayerState(playerId, new PlayerReadyToStartNextPan());
			// 还要判断是否都准备好了，开始游戏了
			if (allPlayersReadyToNextPan()) {
				startNextPan();
				panNo++;
				readyToStartNextPanPlayerIdsSet.clear();
			}
		} else {
			throw new IllegalOperationException();
		}
	}

	private boolean allPlayersReadyToNextPan() {
		return readyToStartNextPanPlayerIdsSet.size() == fixedPlayerCount;
	}

	protected abstract void startNextPan() throws Exception;

	public void launchVoteToFinish(String playerId, VoteCalculator voteCalculator) throws Exception {

		if (ifInVotingState()) {
			throw new VoteAlreadyLaunchedException();
		}

		if (state.name().equals(FinishedByVote.name) || state.name().equals(Finished.name)) {
			throw new IllegalOperationException();
		}

		vote = new GameFinishVote(voteCalculator);
		updateToVotingState();
	}

	private void updateToVotingState() {
		for (GamePlayer player : idPlayerMap.values()) {
			updatePlayerToVotingState(player);
		}
		if (state.name().equals(Playing.name)) {
			state = new VotingWhenPlaying();
		} else if (state.name().equals(WaitingNextPan.name)) {
			state = new VotingWhenWaitingNextPan();
		} else {
			updateToExtendedVotingState();
		}
	}

	private void updatePlayerToVotingState(GamePlayer player) {
		String stateName = player.getState().name();
		if (stateName.equals(PlayerPanFinished.name)) {
			player.setState(new PlayerPanFinishedAndVoting());
		} else if (stateName.equals(PlayerReadyToStartNextPan.name)) {
			player.setState(new PlayerReadyToStartNextPanAndVoting());
		} else {
		}
	}

	protected abstract void updateToExtendedVotingState();

	private boolean ifInVotingState() {
		if (state.name().equals(VotingWhenPlaying.name) || state.name().equals(VotingWhenWaitingNextPan.name)) {
			return true;
		} else {
			return ifInExtendedVotingState();
		}
	}

	protected abstract boolean ifInExtendedVotingState();

	public void voteToFinish(String playerId, VoteOption option) throws Exception {
		vote.vote(playerId, option);
		for (GamePlayer player : idPlayerMap.values()) {
			updatePlayerToVotedState(player);
		}
		vote.calculateResult(this);
		VoteResult voteResult = vote.getResult();
		if (voteResult != null) {// 出结果了
			if (voteResult.equals(VoteResult.yes)) {// 通过
				state = new FinishedByVote();
			} else {// 没通过，恢复到投票前的状态
				recoveryStateFromVoting();
			}
		}
	}

	private void updatePlayerToVotedState(GamePlayer player) {
		String stateName = player.getState().name();
		if (stateName.equals(PlayerPanFinishedAndVoting.name)) {
			player.setState(new PlayerPanFinishedAndVoted());
		} else if (stateName.equals(PlayerReadyToStartNextPanAndVoting.name)) {
			player.setState(new PlayerReadyToStartNextPanAndVoted());
		} else {
		}
	}

	private void recoveryStateFromVoting() throws Exception {
		if (state.name().equals(VotingWhenPlaying.name)) {
			state = new Playing();
			updateAllPlayersState(new PlayerPlaying());
		} else if (state.name().equals(VotingWhenWaitingNextPan.name)) {
			state = new WaitingNextPan();
			for (String playerId : idPlayerMap.keySet()) {
				if (readyToStartNextPanPlayerIdsSet.contains(playerId)) {
					updatePlayerState(playerId, new PlayerReadyToStartNextPan());
				} else {
					updatePlayerState(playerId, new PlayerPanFinished());
				}
			}
		} else {
			recoveryExtendedStateFromVoting();
		}
	}

	protected abstract void recoveryExtendedStateFromVoting() throws Exception;

	public int getPanNo() {
		return panNo;
	}

	public void setPanNo(int panNo) {
		this.panNo = panNo;
	}

	public int getFixedPlayerCount() {
		return fixedPlayerCount;
	}

	public void setFixedPlayerCount(int fixedPlayerCount) {
		this.fixedPlayerCount = fixedPlayerCount;
	}

	public GameFinishVote getVote() {
		return vote;
	}

	public void setVote(GameFinishVote vote) {
		this.vote = vote;
	}

	public Set<String> getReadyToStartNextPanPlayerIdsSet() {
		return readyToStartNextPanPlayerIdsSet;
	}

	public void setReadyToStartNextPanPlayerIdsSet(Set<String> readyToStartNextPanPlayerIdsSet) {
		this.readyToStartNextPanPlayerIdsSet = readyToStartNextPanPlayerIdsSet;
	}

}