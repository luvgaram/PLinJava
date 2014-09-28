package bowling;

import core.ScoreNumber;
public class PlayData {
	
	static final int FIRSTFRAME = 0;
	static final int FINALFRAME = 9;
	static final int FULLSCORE = 10;
	static final ScoreNumber FULLSCORENUMBER = new ScoreNumber(10);
	
	public int turn;
	public Ball ball;
	// 투구 횟수
	enum Ball {
		FIRST(0), SECOND(1), BONUS(2), FINISHED(3);
		
		int symbol;
		
		Ball(int symbol) {
			this.symbol=symbol;
		}
		
		int convertToLength() {
			return symbol + 1;
		}
	}
	
	PlayData(int turn, Ball ball) {
		this.turn = turn;
		this.ball = ball;
	}
	
	int getTurn() {
		return turn;
	}
	
	Ball getBall() {
		return ball;
	}
	
	void setBall(Ball ball) {
		this.ball = ball;
	}
	
	int getBallNumber() {
		return ball.symbol;
	}
	
	void increaseTurn() {
		this.turn++;
	}
	
	void increaseBall() {
		switch (ball.symbol) {
			case 0 : ball = Ball.SECOND;
			break;
			
			case 1 : ball = Ball.BONUS;
			break;
			
			case 2 : ball = Ball.FINISHED;
			break;
			
			default : throw new IllegalArgumentException(String.format("보너스 볼 이상을 던질 수 없다. 현재 값 : %d", ball.symbol));
		}
	}
}
