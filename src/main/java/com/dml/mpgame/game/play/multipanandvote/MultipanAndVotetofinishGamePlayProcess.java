package com.dml.mpgame.game.play.multipanandvote;

import com.dml.mpgame.game.play.GamePlayProcess;
import com.dml.mpgame.game.play.multipan.WaitingNextPan;
import com.dml.mpgame.game.play.multipan.player.PanFinished;

/**
 * 过程中要玩多盘游戏，并且以玩家投票的方式决定是否中止游戏。
 * 
 * @author Neo
 *
 */
public abstract class MultipanAndVotetofinishGamePlayProcess extends GamePlayProcess {

	/**
	 * 结束当前盘，有可能导致整个过程结束
	 */
	public void checkAndFinishPan() throws Exception {
		if (checkToFinishCurrentPan()) {
			// 还要判断游戏是否结束
			if (checkToFinishProcess()) {
				finishProcess();
			} else {
				finishCurrentPan();
			}
		}
	}

	private void finishCurrentPan() {
		state = new WaitingNextPan();
		updateAllPlayersState(new PanFinished());
	}

	protected abstract boolean checkToFinishProcess() throws Exception;

	protected abstract boolean checkToFinishCurrentPan() throws Exception;

	public void readyToNextPan(String playerId) throws Exception {
		// TODO 具体开始下一盘要抽象方法
	}

}
