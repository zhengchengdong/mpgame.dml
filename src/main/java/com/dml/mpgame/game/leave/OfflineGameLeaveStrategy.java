package com.dml.mpgame.game.leave;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.player.GamePlayerOnlineState;

/**
 * 离开就设为离线。最常用的策略。
 * 
 * @author Neo
 *
 */
public class OfflineGameLeaveStrategy implements GameLeaveStrategy {

	@Override
	public void leave(String playerId, Game game) throws Exception {
		game.updatePlayerOnlineState(playerId, GamePlayerOnlineState.offline);
	}

}
