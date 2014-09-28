package bowling;

import core.ScoreNumber;

public class Board {
	private int currentScore;
	private Frame[] frames;
	
	private PlayData boardData;
	
	Board() {
		currentScore = 0;
		boardData = new PlayData(0, PlayData.Ball.FIRST);
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
	
	ScoreNumber getBall(int turn, PlayData.Ball ball) {
		return frames[turn].getBall(ball);
	}

	int getFrameScore(int turn) {
		return frames[turn].getTotalScore();
	}
	
	int getCurrentFrame() {
		return boardData.getTurn();
	}
	
	int getCurrentScore() {
		return currentScore;
	}
	
	void printFrames() {
		for (int i = 0; i < boardData.getTurn(); i++) {
			frames[i].printBalls();
		}
		System.out.println();
		for (int i = 0; i < boardData.getTurn(); i++) {
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
		frames[boardData.getTurn()].setBall(score);
		increaseBall();
		if (isGameFinished()) {
			boardData.increaseTurn();
		}
	}

	private boolean isGameFinished() {
		return (boardData.getBall() == PlayData.Ball.BONUS 
					&& frames[boardData.getTurn()].getFrameLength() == PlayData.Ball.SECOND.convertToLength()) 
				|| (boardData.getBall() == PlayData.Ball.FINISHED 
					&& frames[boardData.getTurn()].getFrameLength() == PlayData.Ball.BONUS.convertToLength());
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
		frames[boardData.getTurn()].setBall(score);
		if(isStrike(score)) {
			boardData.setBall(PlayData.Ball.FIRST);
			boardData.increaseTurn();
			return;
		}
		// 스트라이크가 아니면 볼만 증가	
		boardData.increaseBall();
	}
	
	private void setSecondBall(ScoreNumber score) {
		// 1투구와 2투구 합이 11이상이면 에러
		if (isSummed(score).overs(PlayData.FULLSCORE)) {
			throw new IllegalArgumentException(String.format("1,2 투구 합계 10 이상을 던질 수 없다. 현재 값 : %d", isSummed(score)));
		}
		// 1투구와 2투구 합이 10이면 = 스페어
				if (isSummed(score).equals(PlayData.FULLSCORE)) {
					
				}
		// 1투구와 2투구 합이 9이하이면	
		frames[boardData.getTurn()].setBall(score);
		boardData.setBall(PlayData.Ball.FIRST);
		boardData.increaseTurn();
	}
	
	private void setScores(ScoreNumber score) {
		frames[boardData.getTurn()].setTotalScore(score);
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
		if (findPreviousStrike(goPreviousFrame(1))) {
			setTargetScore(score, goPreviousFrame(1));
		}
	}

	private int goPreviousFrame(int target) {
		return boardData.getTurn() - target;
	}

	private void setMiddleFrameScores(ScoreNumber score) {
		// 전회가 스트라이크이면
		if (findPreviousStrike(goPreviousFrame(1))) {
			setPreviousStrikeScore(score);
			return;
		}
		
		// 첫번째 투구고 전 프레임이 스페어면
		if (isFirstBall() && findPreviousSpare(goPreviousFrame(1))) {
			// 이전 프레임에 현재 점수 더함
			setTargetScore(score, goPreviousFrame(1));
		}
	}

	private void setPreviousStrikeScore(ScoreNumber score) {
		// 전회의 점수에 현재 점수 더함
		setTargetScore(score, goPreviousFrame(1));
		
		// 첫번째 볼이고 전전회도 스트라이크면
		if (isFirstBall() && boardData.getTurn() > 1 && findPreviousStrike(goPreviousFrame(2))) {
			setTargetScore(score, boardData.getTurn() - 2);
		}
	}

	private void setTargetScore(ScoreNumber score, int target) {
		frames[target].setTotalScore(score);
	}
	
	private void increaseBall() {
		if (boardData.getBall() == PlayData.Ball.FIRST) {
			boardData.increaseBall();
			return;
		}
		if (boardData.getBall() == PlayData.Ball.SECOND) {
			boardData.increaseBall();
			return;
		}
		boardData.increaseBall();
	}
	
	// 해당 턴이 스트라이크인지 확인
	private boolean findPreviousStrike(int targetTurn) {
		return frames[targetTurn].getBall(PlayData.Ball.FIRST).equals(PlayData.FULLSCORE);
	}
	
	// 해당 턴이 스페어인지 확인
	private boolean findPreviousSpare(int targetTurn) {
		return (frames[targetTurn].getBall(PlayData.Ball.FIRST).plus(frames[targetTurn].getBall(PlayData.Ball.SECOND))).equals(PlayData.FULLSCORE);
	}

	private ScoreNumber isSummed(ScoreNumber score) {
		return getFrame(boardData.getTurn()).getBall(PlayData.Ball.FIRST).plus(score);
	}

	private boolean isFirstBall() {
		return boardData.getBall() == PlayData.Ball.FIRST;
	}
	
	private boolean isSecondBall() {
		return boardData.getBall() == PlayData.Ball.SECOND;
	}

	private boolean isBonusBall() {
		return boardData.getBall() == PlayData.Ball.BONUS;
	}

	private boolean isStrike(ScoreNumber score) {
		return score.equals(PlayData.FULLSCORE);
	}

	private boolean isFirstFrame() {
		return boardData.getTurn() == 0;
	}
	
	private boolean isFinalFrame() {
		return boardData.getTurn() == PlayData.FINALFRAME;
	}
	
	private Frame getFrame(int turn) {
		return frames[turn];
	}

}