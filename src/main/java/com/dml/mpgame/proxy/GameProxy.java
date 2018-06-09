package com.dml.mpgame.proxy;

import com.dml.mpgame.Game;

/**
 * 用来代理一些游戏操作，以符合一些惯例。比如准备阶段主机要求投票解散那就没必要投了，直接解散。
 * 
 * @author Neo
 *
 */
public abstract class GameProxy {

	private Game game;

	public GameProxy() {
	}

	public GameProxy(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
