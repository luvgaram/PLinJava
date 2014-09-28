package bowling;

import core.ScoreNumber;

class Frame {

	ScoreNumber[] numbers; // 각 투구의 점수를 담음
	private int totalScore; // 프레임 점수 합
	private PlayData frameData;

	// 패키지 내부의 다른 클래스에서 접근하는 메소드 모음
	Frame(int turn) {
		createDefaultFrame(turn);
	}

	// 각 투구의 점수를 기록
	void setBall (ScoreNumber score) {
		// 볼 한계보다 더 던지려고 하면
		if (isAboveBallLimit()) {
			throw new IllegalArgumentException(String.format("지정 볼 이상을 던질 수 없다. 현재 값 : %d", frameData.ball.symbol));
		}
		// 마지막 프레임이면
		if (isFinalFrame(frameData.getTurn())) {
			setFinalFrame(score);
			return;
		}
		
		// 10점 이상을 던지려고 하면
		if (isOverEleven(score)) {
			throw new IllegalArgumentException(String.format("십 이상을 던질 수 없다. 현재 값 : %d", score));
		}
		
		// 스페어 이상을 던지려고 하면
		if (isOverSpare(score)) {
			throw new IllegalArgumentException(String.format("스페어 이상을 던질 수 없다. 현재 값 : %d", numbers[0].plus(score).getNumber()));
		}

		// 마지막 프레임이 아니면
		setNotFinalFrame(score);
	}
	
	private boolean isOverEleven(ScoreNumber score) {
		return score.overs(PlayData.FULLSCORENUMBER);
	}

	private boolean isOverSpare(ScoreNumber score) {
		return (frameData.getBall() == PlayData.Ball.SECOND) 
				&& (numbers[PlayData.Ball.FIRST.symbol].plus(score).overs(PlayData.FULLSCORENUMBER));
	}
	
	int getTotalScore() {
		return totalScore;
	}
	
	void setTotalScore(ScoreNumber score) {
		totalScore += score.getNumber();
	}
	
	ScoreNumber getBall(PlayData.Ball ball) {
		return numbers[ball.symbol];
	}
	
	void printBalls() {
		// 마지막 프레임이 아니면
		if (!isFinalFrame(frameData.turn)) {
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
		for (int ball = PlayData.Ball.FIRST.symbol; ball <numbers.length; ball++) {
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
		if (isStrike(numbers[PlayData.Ball.FIRST.symbol])) {
			printStrike();
			System.out.print("  \t|");
			return;
		}
		if (isSpare(numbers[PlayData.Ball.SECOND.symbol])) {
			printNormalNumber(PlayData.Ball.FIRST.symbol);
			printSpare();
			return;
		}
		printNormalNumber(PlayData.Ball.FIRST.symbol);
		printNormalNumber(PlayData.Ball.SECOND.symbol);
	}

	private void printSpare() {
		System.out.print(" /" + "\t|");
	}

	private boolean isFinalSpare(int ball) {
		return ball == PlayData.Ball.SECOND.symbol 
				&& numbers[ball - 1].plus(numbers[ball]).equals(PlayData.FULLSCORENUMBER) 
				&& !numbers[ball].equals(new ScoreNumber(0));
	}

	private boolean isFinalBonusBallSpare(int ball) {
		return ball == PlayData.Ball.BONUS.symbol 
				&& numbers[ball - 1].plus(numbers[ball]).equals(PlayData.FULLSCORENUMBER)
				&& !numbers[ball].equals(new ScoreNumber(0)) 
				&& !isFinalSpare(ball - 1);
	}
	
	private void printNormalNumber(int number) {
		System.out.print(" "+numbers[number].getNumber()+"\t|");
	}

	private void printStrike() {
		System.out.print(" X" + "\t|");
	}

	// turn이 10이면 보너스인 3 ball 생성, 아니면 1,2 ball만 생성  
	private void createDefaultFrame(int turn) {
		frameData = new PlayData(turn, PlayData.Ball.FIRST);
		totalScore = 0;
		
		if (isFinalFrame(frameData.getTurn())) {
			numbers = new ScoreNumber[PlayData.Ball.BONUS.convertToLength()];
			return;
		}
		numbers = new ScoreNumber[PlayData.Ball.SECOND.convertToLength()];
	}

	private void setNotFinalFrame(ScoreNumber score) {
		if (isStrike(score)) {
			numbers = new ScoreNumber[numbers.length - 1];
			numbers[frameData.ball.symbol] = score;
			frameData.ball =PlayData.Ball.FIRST;
			return;
		}
		numbers[frameData.ball.symbol] = score;
		increaseBall();
	}

	private void increaseBall() {
		if (frameData.ball == PlayData.Ball.FIRST) {
			frameData.ball = PlayData.Ball.SECOND;
			return;
		}
		if (frameData.ball == PlayData.Ball.SECOND) {
			frameData.ball = PlayData.Ball.BONUS;
		}
	}
	
	private void setFinalFrame(ScoreNumber score) {
		// 1, 2번째 볼이 스트라이크이거나 스페어이면
		if (frameData.ball == PlayData.Ball.SECOND) {
			if (isFirstBallStrike() || isStrike(score) || isSpare(score)) {
				numbers[frameData.ball.symbol] = score;
				increaseBall();
				return;
			}
			// 스트라이크나 스페어에 실패했다면
			ScoreNumber firstBall = numbers[PlayData.Ball.FIRST.symbol];
			numbers = new ScoreNumber[2];
			numbers[PlayData.Ball.FIRST.symbol] = firstBall;
			numbers[PlayData.Ball.SECOND.symbol] = score;
			increaseBall();
			return;
		}
		numbers[frameData.ball.symbol] = score;
		increaseBall();
	}
	
	private boolean isFirstBallStrike() {
		return numbers[PlayData.Ball.FIRST.symbol].equals(PlayData.FULLSCORENUMBER);
	}

	private boolean isSpare(ScoreNumber score) {
		return numbers[PlayData.Ball.FIRST.symbol].plus(score).equals(PlayData.FULLSCORENUMBER);
	}
	
	private boolean isFinalFrame(int turn) {
		return turn == PlayData.FINALFRAME;
	}

	private boolean isStrike(ScoreNumber score) {
		return score.equals(PlayData.FULLSCORENUMBER);
	}

	private boolean isAboveBallLimit() {
		return frameData.ball.symbol >= numbers.length;
	}
}
