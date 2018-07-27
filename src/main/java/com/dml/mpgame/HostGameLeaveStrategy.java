package com.dml.mpgame;

public class HostGameLeaveStrategy implements GameLeaveStrategy {

	private String hostPlayerId;

	public HostGameLeaveStrategy() {
	}

	public HostGameLeaveStrategy(String hostPlayerId) {
		this.hostPlayerId = hostPlayerId;
	}

	@Override
	public void leave(String playerId, Game game) throws Exception {

		if (game.getState().equals(GameState.waitingStart)) {// 准备阶段特殊处理
			if (playerId.equals(hostPlayerId)) {// 主机玩家正常离开
				game.updatePlayerOnlineState(playerId, GamePlayerOnlineState.offline);
			} else {// 副机玩家离开就是直接退出
				game.removePlayer(playerId);
			}
		} else {// 开玩之后一切正常处理
			game.updatePlayerOnlineState(playerId, GamePlayerOnlineState.offline);
		}

	}

}
