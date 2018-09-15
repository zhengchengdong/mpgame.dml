package com.dml.mpgame.game.play;

/**
 * 具体玩游戏的过程。描述玩 具体 游戏内部的状态机。比如正在等下一盘开始，正在投票解散，正在温州麻将买底 等等。
 * 
 * @author Neo
 *
 */
public abstract class GamePlayProcess {

	protected GamePlayProcessState state;

	public GamePlayProcessState getState() {
		return state;
	}

	public void setState(GamePlayProcessState state) {
		this.state = state;
	}

}
