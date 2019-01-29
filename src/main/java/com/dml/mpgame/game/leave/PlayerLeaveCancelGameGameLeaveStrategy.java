package com.dml.mpgame.game.leave;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.player.GamePlayer;
import com.dml.mpgame.game.player.GamePlayerOnlineState;

/**
 * 玩家退出就取消游戏
 * 
 * @author lsc
 *
 */
public class PlayerLeaveCancelGameGameLeaveStrategy implements GameLeaveStrategy {

	@Override
	public void leave(String playerId, Game game) throws Exception {
		GamePlayer player = game.findPlayer(playerId);
		if (player != null && !player.getOnlineState().equals(GamePlayerOnlineState.offline)) {
			game.updatePlayerOnlineState(playerId, GamePlayerOnlineState.offline);
			// 取消游戏
			game.cancel();
		}
	}

}
