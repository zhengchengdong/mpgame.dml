package com.dml.mpgame.game.play;

/**
 * 玩游戏过程中的状态。比如 正在等下一盘开始，正在投票解散，正在温州麻将买底 等等。
 * 
 * @author Neo
 *
 */
public interface GamePlayProcessState {
	public String name();
}
