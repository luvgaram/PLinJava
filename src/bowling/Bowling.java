package bowling;

import java.util.Map;
import java.util.TreeMap;

import core.ScoreNumber;


public class Bowling {
	private Map<PlayerId, Player> Players = new TreeMap<PlayerId, Player>();

	public PlayerId addPlayer(String name) {
		PlayerId id = PlayerId.issue();
		Player player = Player.create(id, name);
		Players.put(player.getId(), player);
		return id;
	}

	public Player findPlayerById(PlayerId id) {
		return Players.get(id);
	}

	public void playBall(PlayerId id, ScoreNumber scoreNumber) {
		Player targetPlayer = findPlayerById(id);
		targetPlayer.setBoard(scoreNumber);
		
	}

}
