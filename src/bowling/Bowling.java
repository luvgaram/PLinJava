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

	// 트리 맵에서 플레이어 아이디로 플레이어를 찾아줌 
	private Player findPlayerById(PlayerId id) {
		return Players.get(id);
	}
	
	// 외부(테스트)와 통신은 Bowling 클래스의 하기 메소드들만을 통해서만 진행
	public String setPlayerName(PlayerId id, String name) {
		return findPlayerById(id).setName(name);
	}
	
	public String getPlayerName(PlayerId id) {
		return findPlayerById(id).getName();
	}

	public void playBall(PlayerId id, ScoreNumber scoreNumber) {
		findPlayerById(id).setBoard(scoreNumber);
	}
	
	public ScoreNumber getBall(PlayerId id, int turn, PlayData.Ball ball) {
		return findPlayerById(id).getBall(turn, ball);
	}
	
	public int getFrameScore(PlayerId id, int turn) {
		return findPlayerById(id).getFrameScore(turn);
	}

	public int getCurrentScore(PlayerId id) {
		return findPlayerById(id).getScore();
	}
	
	public void printPlayer(PlayerId id) {
		findPlayerById(id).printBoard();
	}
}
