package com.dml.mpgame.game.leave;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.player.GamePlayer;

/**
 * 只要离开就直接退出
 * 
 * @author lsc
 *
 */
public class PlayerGameLeaveStrategy implements GameLeaveStrategy {

	@Override
	public void leave(String playerId, Game game) throws Exception {
		GamePlayer player = game.findPlayer(playerId);
		if (player != null) {
			// 直接退出
			game.removePlayer(playerId);
		}
	}

}
