package com.dml.mpgame.game.extend.fpmpv.leave;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.WaitingStart;
import com.dml.mpgame.game.extend.fpmpv.FixedPlayersMultipanAndVotetofinishGame;
import com.dml.mpgame.game.extend.vote.VoteOption;
import com.dml.mpgame.game.leave.GameLeaveStrategy;
import com.dml.mpgame.game.player.GamePlayerOnlineState;

/**
 * 离开的时候要投弃权票
 * 
 * @author Neo
 *
 */
public class FpmpvLeaveWithVoteStrategy implements GameLeaveStrategy {

	private String hostPlayerId;

	public FpmpvLeaveWithVoteStrategy() {
	}

	public FpmpvLeaveWithVoteStrategy(String hostPlayerId) {
		this.hostPlayerId = hostPlayerId;
	}

	@Override
	public void leave(String playerId, Game game) throws Exception {

		if (game.getState().name().equals(WaitingStart.name)) {// 准备阶段特殊处理
			if (playerId.equals(hostPlayerId)) {// 主机玩家正常离开
				game.updatePlayerOnlineState(playerId, GamePlayerOnlineState.offline);
			} else {// 副机玩家离开就是直接退出
				game.removePlayer(playerId);
			}
		} else {// 开玩之后,离开的时候要投弃权票
			FixedPlayersMultipanAndVotetofinishGame fpmpvGame = (FixedPlayersMultipanAndVotetofinishGame) game;
			fpmpvGame.voteToFinish(playerId, VoteOption.waiver);
			game.updatePlayerOnlineState(playerId, GamePlayerOnlineState.offline);
		}

	}

}
