package bowling;

import java.util.Random;

import core.ScoreNumber;

public class RandomBowling extends Bowling {
	Player player;
	PlayData randomPlayData;
	
	RandomBowling() {
		PlayerId id = PlayerId.issue();
		player = Player.create(id, "랜덤");
		randomPlayData = new PlayData(0, PlayData.Ball.FIRST);
	}
	
	public void printPlayer() { 
		player.printBoard();
	}
	
	@Override
	public void printPlayer(PlayerId id) {
		this.printPlayer();
	}
	
	public void playBall() {
		while (randomPlayData.turn != PlayData.FINALFRAME) {
			player.setBoard(generateRandomScore());
		}

		ScoreNumber first = player.setBoard(generateRandomScore());
		if (CheckScore.isStrike(first)) {
			player.setBoard(generateRandomScore());
			player.setBoard(generateRandomScore());
			return;
		}
		ScoreNumber second = player.setBoard(generateRandomScore());
		if (CheckScore.isStrike(second) || CheckScore.isSpare(first, second)) {
			player.setBoard(generateRandomScore());
			return;
		}
		return;
	}
	
	@Override
	public void playBall(PlayerId id, ScoreNumber scoreNumber) {
		this.playBall();
	}
	
	ScoreNumber generateRandomScore() {
		Random generator = new Random();
		int randomNumber = 0;
		
		switch (randomPlayData.getBall().symbol) {
		case 0 : randomNumber = generator.nextInt(11);
					if (CheckScore.isStrike(new ScoreNumber(randomNumber)) 
							&& !CheckScore.isFinalFrame(randomPlayData)) {
						randomPlayData.increaseTurn();
						break;
					}
					randomPlayData.increaseBall();
					break;

		case 1 : if (!CheckScore.isFinalFrame(randomPlayData))  {
						randomNumber = generator.nextInt(11 - getTargetScore(PlayData.Ball.FIRST));
						randomPlayData.resetBall();
						randomPlayData.increaseTurn();
						break;
					}
					if (CheckScore.isStrike(player.getBall(randomPlayData.getTurn(), PlayData.Ball.FIRST))) {
						randomNumber = generator.nextInt(11);
						randomPlayData.increaseBall();
						break;
					}
					randomNumber = generator.nextInt(11 - getTargetScore(PlayData.Ball.FIRST));
					randomPlayData.increaseBall();
					break;

		case 2 : if (CheckScore.isStrike(player.getBall(randomPlayData.getTurn(), PlayData.Ball.SECOND))) {
							randomNumber = generator.nextInt(11);
							randomPlayData.increaseBall();
							break;
					}
					randomNumber = generator.nextInt(11 - getTargetScore(PlayData.Ball.SECOND));
					randomPlayData.increaseBall();
					break;
			
		default : randomNumber = 0;
					break;
	}
		return new ScoreNumber(randomNumber);
	}

	private int getTargetScore(PlayData.Ball ball) {
		return player.getBall(randomPlayData.getTurn(), ball).getNumber();

	}
}
