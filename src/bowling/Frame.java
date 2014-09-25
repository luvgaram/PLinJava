package bowling;

import core.ScoreNumber;

public class Frame {
	ScoreNumber[] numbers; // 각 투구의 점수를 담음
	private int turn; // 프레임 번호 담음
	private int ball; // 투구 횟수
	private int totalScore; // 프레임 점수 합

	public Frame(int turn) {
		createDefaultFrame(turn);
	}

	// turn이 10이면 보너스인 3 ball 생성, 아니면 1,2 ball만 생성  
	private void createDefaultFrame(int turn) {
		this.turn = turn;
		ball = 0;
		totalScore = 0;
		
		if (isFinalFrame(turn)) {
			numbers = new ScoreNumber[3];
			return;
		}
		numbers = new ScoreNumber[2];
	}

	// 각 투구의 점수를 기록
	void setBall (ScoreNumber score) {
		// 볼 한계보다 더 던지려고 하면
		if (isAboveBallLimit()) {
			throw new IllegalArgumentException(String.format("지정 볼 이상을 던질 수 없다. 현재 값 : %d", ball));
		}
//		System.out.println("현재 볼은 " + ball + ", 점수는 : " + score.getNumber());
		// 마지막 프레임이면
		if (isFinalFrame(turn)) {
			setFinalFrame(score);
			return;
		}
		// 마지막 프레임이 아니면
		setNotFinalFrame(score);
	}

	private void setNotFinalFrame(ScoreNumber score) {
		if (isStrike(score)) {
			numbers = new ScoreNumber[numbers.length - 1];
			numbers[ball] = score;
			ball = 0;
			return;
		}
		numbers[ball] = score;
		ball++;
	}
	
	private void setFinalFrame(ScoreNumber score) {
		// 1, 2번째 볼이 스트라이크이거나 스페어이면
		if (ball == 1) {
//			System.out.println(numbers[0].getNumber() + score.getNumber());
			if (isFirstBallStrike() || isStrike(score) || isSpare(score)) {
				numbers[ball] = score;
				ball++;
				return;
			}
			// 스트라이크나 스페어에 실패했다면
			ScoreNumber firstBall = numbers[0];
			numbers = new ScoreNumber[2];
			numbers[0] = firstBall;
			numbers[1] = score;
			ball++;
			return;
		}
		numbers[ball] = score;
		ball++;
	}

	public void setTotalScore(ScoreNumber score) {
		totalScore += score.getNumber();
	}
	
	public ScoreNumber[] getBalls () {
		return numbers;
	}
	
	public int getTotalScore() {
		return totalScore;
	}

	public void printBalls() {
		for (int i = 0; i < ball; i++) {
//			System.out.println(i + "번째 볼은 " + numbers[i].getNumber());
		}
	}
	
	private boolean isFirstBallStrike() {
		return numbers[0].getNumber() == 10;
	}

	private boolean isSpare(ScoreNumber score) {
		return numbers[0].getNumber() + score.getNumber() == 10;
	}
	
	private boolean isFinalFrame(int turn) {
		return turn == 9;
	}

	private boolean isStrike(ScoreNumber score) {
		return ball == 0 && score.getNumber() == 10;
	}

	private boolean isAboveBallLimit() {
		return ball >= numbers.length;
	}
}
