package com.dml.mpgame.proxy;

import com.dml.mpgame.Game;
import com.dml.mpgame.GamePlayerNotFoundException;
import com.dml.mpgame.GameState;

/**
 * 主机游戏代理 <br/>
 * 准备阶段，主机要求投票实际是直接解散，副机暂时离开或者要求投票解散实际都是副机直接退出 <br/>
 * 开始游戏后一切正常
 * 
 * @author Neo
 *
 */
public class HostGameProxy extends GameProxy {

	public HostGameProxy() {
	}

	public HostGameProxy(Game game) {
		super(game);
	}

	@Override
	public void leave(String playerId) throws GamePlayerNotFoundException {
		GameState state = game.getState();
		String createPlayerId = game.getCreatePlayerId();
		if (state.equals(GameState.waitingStart)) {
			if (playerId.equals(createPlayerId)) {
				game.leave(playerId);
			} else {
				game.quit(playerId);
			}
		} else {
			game.leave(playerId);
		}
	}

}
