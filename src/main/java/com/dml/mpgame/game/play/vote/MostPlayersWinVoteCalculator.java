package com.dml.mpgame.game.play.vote;

import java.util.List;

import com.dml.mpgame.game.Game;
import com.dml.mpgame.game.GamePlayer;
import com.dml.mpgame.game.GamePlayerOnlineState;

/**
 * 少数服从多数.平手的话算不通过
 * 
 * @author Neo
 *
 */
public class MostPlayersWinVoteCalculator implements VoteCalculator {

	@Override
	public void calculateResult(GameFinishVote vote, Game game) {
		List<String> allPlayerIds = game.allPlayerIds();
		int yesCount = 0;
		int noCount = 0;
		int notDecidedCount = 0;
		for (String playerId : allPlayerIds) {
			GamePlayer player = game.findPlayer(playerId);
			// 已投票的计票，还没投票的，不在线的视为弃权，不计票
			VoteOption playerOption = vote.findPlayerVoteOption(playerId);
			if (playerOption != null) {
				if (playerOption.equals(VoteOption.yes)) {
					yesCount++;
				} else if (playerOption.equals(VoteOption.no)) {
					noCount++;
				} else {
				}
			} else {
				if (player.getOnlineState().equals(GamePlayerOnlineState.online)) {
					notDecidedCount++;
				}
			}
		}

		if (yesCount > noCount) {
			if (yesCount > (noCount + notDecidedCount)) {
				vote.setResult(VoteResult.yes);
			}
		} else {
			if (yesCount <= (noCount + notDecidedCount)) {
				vote.setResult(VoteResult.no);
			}
		}

	}

}
