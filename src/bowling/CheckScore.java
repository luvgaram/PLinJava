package bowling;

import core.ScoreNumber;

public class CheckScore {
	public static boolean isFirstBallStrike(PlayData data, ScoreNumber score) {
		return isFirstBall(data) && isStrike(score);
	}
	
	public static boolean isSecondBallStrike(PlayData data,  ScoreNumber score) {
		return isSecondBall(data) && isStrike(score);
	}
	
	public static boolean isBonusBallStrike(PlayData data,  ScoreNumber score) {
		return isBonusBall(data) && isStrike(score);
	}
	
	public static boolean isSecondBallSpare(PlayData data,  ScoreNumber firstScore, ScoreNumber secondScore) {
		return isSecondBall(data) && isSpare(firstScore, secondScore);
	}
	
	public static boolean isBonusBallSpare(PlayData data,  ScoreNumber firstScore, ScoreNumber secondScore) {
		return isBonusBall(data) && isSpare(firstScore, secondScore);
	}
	
	public static boolean isSecondBallOverSpare(PlayData data,  ScoreNumber firstScore, ScoreNumber secondScore) {
		return isSecondBall(data) && isOverSpare(firstScore, secondScore);
	}
	
	public static boolean isBonusBallOverSpare(PlayData data,  ScoreNumber firstScore, ScoreNumber secondScore) {
		return isBonusBall(data) && isOverSpare(firstScore, secondScore);
	}
	
	public static boolean isFirstFrame(PlayData data) {
		return data.getTurn() == PlayData.FIRSTFRAME;
	}

	public static boolean isFinalFrame(PlayData data) {
		return data.getTurn() == PlayData.FINALFRAME;
	}

	public static boolean isFirstBall(PlayData data) {
		return data.getBall().equals(PlayData.Ball.FIRST);
	}
	
	public static boolean isSecondBall(PlayData data) {
		return data.getBall().equals(PlayData.Ball.SECOND);
	}
	
	public static boolean isBonusBall(PlayData data) {
		return data.getBall().equals(PlayData.Ball.BONUS);
	}
	
	public static boolean isFished(PlayData data) {
		return data.getBall().equals(PlayData.Ball.FINISHED);
	}
	
	public static boolean isStrike(ScoreNumber score) {
		return score.equals(PlayData.FULLSCORE);
	}
	
	public static boolean isSpare(ScoreNumber firstScore, ScoreNumber secondScore) {
		return (firstScore.plus(secondScore)).equals(PlayData.FULLSCORE);
	}
	
	public static boolean isOverSpare(ScoreNumber firstScore, ScoreNumber secondScore) {
		return  (firstScore.plus(secondScore)).overs(PlayData.FULLSCORE);
	}
}
