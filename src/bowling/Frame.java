package bowling;

import core.ScoreNumber;

class Frame {
	private static final int FINALFRAME = 9;
	private static final int FULLSCORE = 10;
	ScoreNumber[] numbers; // 각 투구의 점수를 담음
	private int turn; // 프레임 번호 담음
	private int totalScore; // 프레임 점수 합
	// 투구 횟수
	private enum Ball {
		FIRST(0), SECOND(1), BONUS(2);
		
		private int symbol;
		
		private Ball(int symbol) {
			this.symbol=symbol;
		}
		
		private int convertToLength () {
			return symbol + 1;
		}
	}
	
	private Ball ball;

	// 패키지 내부의 다른 클래스에서 접근하는 메소드 모음
	Frame(int turn) {
		createDefaultFrame(turn);
	}

	// 각 투구의 점수를 기록
	void setBall (ScoreNumber score) {
		// 볼 한계보다 더 던지려고 하면
		if (isAboveBallLimit()) {
			throw new IllegalArgumentException(String.format("지정 볼 이상을 던질 수 없다. 현재 값 : %d", ball));
		}
		// 마지막 프레임이면
		if (isFinalFrame(turn)) {
			setFinalFrame(score);
			return;
		}
		
		// 스페어 이상을 던지려고 하면
		if (isOverSpare(score)) {
			throw new IllegalArgumentException(String.format("스페어 이상을 던질 수 없다. 현재 값 : %d", numbers[0].getNumber() + score.getNumber()));
		}

		// 마지막 프레임이 아니면
		setNotFinalFrame(score);
	}

	private boolean isOverSpare(ScoreNumber score) {
		return ball == Ball.SECOND && numbers[Ball.FIRST.symbol].getNumber() + score.getNumber() > FULLSCORE;
	}
	
	int getTotalScore() {
		return totalScore;
	}
	
	void setTotalScore(ScoreNumber score) {
		totalScore += score.getNumber();
	}
	
	ScoreNumber getBall(int ball) {
		return numbers[ball];
	}
	
	void printBalls() {
		// 마지막 프레임이 아니면
		if (!isFinalFrame(turn)) {
			printNotFinalFrame();
			return;
		}
		
		// 마지막 프레임이면
		printFinalFrame();
	}
	
	int getFrameLength() {
		return numbers.length;
	}
 	// 패키지 내부의 다른 클래스에서 접근하는 메소드 끝

	private void printFinalFrame() {
		for (int ball = Ball.FIRST.symbol; ball <numbers.length; ball++) {
			if (isStrike(numbers[ball])) {
				printStrike();
				continue;
			}
			if (isFinalSpare(ball)) {
				printSpare();
				continue;
			}
			if (isFinalBonusBallSpare(ball)) {
				printSpare();
				continue;
			}
			printNormalNumber(ball);
		}
	}
	
	private void printNotFinalFrame() {
		if (isStrike(numbers[Ball.FIRST.symbol])) {
			printStrike();
			System.out.print("  \t|");
			return;
		}
		if (isSpare(numbers[Ball.SECOND.symbol])) {
			printNormalNumber(Ball.FIRST.symbol);
			printSpare();
			return;
		}
		printNormalNumber(Ball.FIRST.symbol);
		printNormalNumber(Ball.SECOND.symbol);
	}

	private void printSpare() {
		System.out.print(" /" + "\t|");
	}

	private boolean isFinalSpare(int ball) {
		return ball == Ball.SECOND.symbol && numbers[ball - 1].getNumber() +numbers[ball].getNumber() == FULLSCORE && numbers[ball].getNumber() != 0;
	}

	private boolean isFinalBonusBallSpare(int ball) {
		return ball == Ball.BONUS.symbol && numbers[ball - 1].getNumber() +numbers[ball].getNumber() == FULLSCORE && numbers[ball].getNumber() != 0 && !isFinalSpare(ball - 1);
	}
	
	private void printNormalNumber(int number) {
		System.out.print(" "+numbers[number].getNumber()+"\t|");
	}

	private void printStrike() {
		System.out.print(" X" + "\t|");
	}

	// turn이 10이면 보너스인 3 ball 생성, 아니면 1,2 ball만 생성  
	private void createDefaultFrame(int turn) {
		this.turn = turn;
		ball = Ball.FIRST;
		totalScore = 0;
		
		if (isFinalFrame(turn)) {
			numbers = new ScoreNumber[Ball.BONUS.convertToLength()];
			return;
		}
		numbers = new ScoreNumber[Ball.SECOND.convertToLength()];
	}

	private void setNotFinalFrame(ScoreNumber score) {
		if (isStrike(score)) {
			numbers = new ScoreNumber[numbers.length - 1];
			numbers[ball.symbol] = score;
			ball = Ball.FIRST;
			return;
		}
		numbers[ball.symbol] = score;
		increaseBall();
	}

	private void increaseBall() {
		if (ball == Ball.FIRST) {
			ball = Ball.SECOND;
			return;
		}
		if (ball == Ball.SECOND) {
			ball = Ball.BONUS;
		}
	}
	
	private void setFinalFrame(ScoreNumber score) {
		// 1, 2번째 볼이 스트라이크이거나 스페어이면
		if (ball == Ball.SECOND) {
			if (isFirstBallStrike() || isStrike(score) || isSpare(score)) {
				numbers[ball.symbol] = score;
				increaseBall();
				return;
			}
			// 스트라이크나 스페어에 실패했다면
			ScoreNumber firstBall = numbers[Ball.FIRST.symbol];
			numbers = new ScoreNumber[2];
			numbers[Ball.FIRST.symbol] = firstBall;
			numbers[Ball.SECOND.symbol] = score;
			increaseBall();
			return;
		}
		numbers[ball.symbol] = score;
		increaseBall();
	}
	
	private boolean isFirstBallStrike() {
		return numbers[Ball.FIRST.symbol].getNumber() == FULLSCORE;
	}

	private boolean isSpare(ScoreNumber score) {
		return numbers[Ball.FIRST.symbol].getNumber() + score.getNumber() == FULLSCORE;
	}
	
	private boolean isFinalFrame(int turn) {
		return turn == FINALFRAME;
	}

	private boolean isStrike(ScoreNumber score) {
		return score.getNumber() == FULLSCORE;
	}

	private boolean isAboveBallLimit() {
		return ball.symbol >= numbers.length;
	}
}
