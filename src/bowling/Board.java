package bowling;

import core.ScoreNumber;

public class Board {
	private int currentTurn;
	private int currentBall;
	private int currentScore;
	private Frame[] frames;
	
	Board() {
		currentTurn = 0;
		currentScore = 0;
		frames = new Frame[10];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new Frame(i);
		}
	}
	
	// 패키지 내부의 다른 클래스에서 접근하는 메소드 모음
	void setFrame(ScoreNumber score) {
		setScores(score);
		setTotalScore();
		
		if (isFinalFrame()) {
			setFinalFrame(score);
			return;
		}
		// 마지막 프레임이 아니면
		setNotFinalFrame(score);
	}
	
	ScoreNumber getBall(int turn, int ball) {
		return frames[turn].getBall(ball);
	}

	int getFrameScore(int turn) {
		return frames[turn].getTotalScore();
	}
	
	int getCurrentFrame() {
		return currentTurn;
	}
	
	int getCurrentScore() {
		return currentScore;
	}
	
	void printFrames() {
		for (int i = 0; i < currentTurn; i++) {
			frames[i].printBalls();
		}
		System.out.println();
		for (int i = 0; i < currentTurn; i++) {
			System.out.print("\t"+frames[i].getTotalScore()+"\t|");
		}
	}
	// 패키지 내부의 다른 클래스에서 접근하는 메소드 끝
	
	private void setTotalScore() {
		int frameScoreTotal = 0;
		for (int i = 0; i < frames.length; i++) {
			frameScoreTotal += frames[i].getTotalScore();
		}
		currentScore = frameScoreTotal;
	}
	
	private void setFinalFrame(ScoreNumber score) {
		frames[currentTurn].setBall(score);
		currentBall++;
		if (isGameFinished()) {
			currentTurn++;
		}
	}

	private boolean isGameFinished() {
		return (currentBall == 2 && frames[currentTurn].getFrameLength() == 2) || (currentBall == 3 && frames[currentTurn].getFrameLength() == 3);
	}

	private void setNotFinalFrame(ScoreNumber score) {
		if(isFirstBall()) {
			setFirstBall(score);
			return;
		}
		setSecondBall(score);
	}

	private void setFirstBall(ScoreNumber score) {
		// 스트라이크면 턴을 증가하고 볼을 리셋
		frames[currentTurn].setBall(score);
		if(isStrike(score)) {
			currentBall = 0;
			currentTurn++;
			return;
		}
		// 스트라이크가 아니면 볼만 증가	
		currentBall++;
	}
	
	private void setSecondBall(ScoreNumber score) {
		// 1투구와 2투구 합이 11이상이면 에러
		if (isSummed(score) > 10) {
			throw new IllegalArgumentException(String.format("1,2 투구 합계 10 이상을 던질 수 없다. 현재 값 : %d", isSummed(score)));
		}
		// 1투구와 2투구 합이 10이면 = 스페어
				if (isSummed(score) == 10) {
					
				}
		// 1투구와 2투구 합이 9이하이면	
		frames[currentTurn].setBall(score);
		currentBall = 0;
		currentTurn++;
	}
	
	private void setScores(ScoreNumber score) {
		frames[currentTurn].setTotalScore(score);
		// 첫회거나 마지막회의 보너스 볼이면
		if (isFirstFrame() || isBonusBall()) {
			return;
		}
		// 마지막회 두번째 볼이면
		if (isFinalFrame() && isSecondBall()) {
			setFinalFrameScores(score);
			return;
		}
		setMiddleFrameScores(score);
	}
	
	// 마지막회 2번째 볼 점수 계산
	private void setFinalFrameScores(ScoreNumber score) {
		// 전회가 스트라이크면 전회에 더해줌
		if (findPreviousStrike(currentTurn - 1)) {
			setTargetScore(score, currentTurn - 1);
		}
	}

	private void setMiddleFrameScores(ScoreNumber score) {
		// 전회가 스트라이크이면
		if (findPreviousStrike(currentTurn - 1)) {
			setPreviousStrikeScore(score);
			return;
		}
		
		// 첫번째 투구고 전 프레임이 스페어면
		if (isFirstBall() && findPreviousSpare(currentTurn - 1)) {
			// 이전 프레임에 현재 점수 더함
			setTargetScore(score, currentTurn - 1);
		}
	}

	private void setPreviousStrikeScore(ScoreNumber score) {
		// 전회의 점수에 현재 점수 더함
		setTargetScore(score, currentTurn - 1);
		
		// 첫번째 볼이고 전전회도 스트라이크면
		if (isFirstBall() && currentTurn > 1 && findPreviousStrike(currentTurn - 2)) {
			setTargetScore(score, currentTurn - 2);
		}
	}

	private void setTargetScore(ScoreNumber score, int target) {
		frames[target].setTotalScore(score);
	}
	
	// 해당 턴이 스트라이크인지 확인
	private boolean findPreviousStrike(int targetTurn) {
		return frames[targetTurn].getBall(0).getNumber() == 10;
	}
	
	// 해당 턴이 스페어인지 확인
	private boolean findPreviousSpare(int targetTurn) {
		return frames[targetTurn].getBall(0).getNumber() + frames[targetTurn].getBall(1).getNumber() == 10;
	}

	private int isSummed(ScoreNumber score) {
		return getFrame(currentTurn).getBall(0).getNumber() + score.getNumber();
	}

	private boolean isFirstBall() {
		return currentBall == 0;
	}
	
	private boolean isSecondBall() {
		return currentBall == 1;
	}

	private boolean isBonusBall() {
		return currentBall == 2;
	}

	private boolean isStrike(ScoreNumber score) {
		return score.getNumber() == 10;
	}

	private boolean isFirstFrame() {
		return currentTurn == 0;
	}
	
	private boolean isFinalFrame() {
		return currentTurn == 9;
	}
	
	private Frame getFrame(int turn) {
		return frames[turn];
	}

}