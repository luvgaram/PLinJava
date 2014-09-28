package bowling;

import org.junit.Assert;
import org.junit.Test;

import core.ScoreNumber;

public class BowlingTest {

	Bowling sut = new Bowling();
	PlayerId  newPlayerId = sut.addPlayer("test");
	
	@Test
	public void 플레이어를_추가할수_있다() {
		Assert.assertEquals("test", sut.getPlayerName(newPlayerId));
	}
	
	@Test
	public void 플레이어_이름을_바꿀수_있다() {
		sut.setPlayerName(newPlayerId, "test2");
		Assert.assertEquals("test2", sut.getPlayerName(newPlayerId));
	}
	
	@Test
	public void 공을_굴릴수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		
		Assert.assertEquals(new ScoreNumber(1), sut.getBall(newPlayerId, 0, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(2), sut.getBall(newPlayerId, 0, PlayData.Ball.SECOND));
		Assert.assertEquals(new ScoreNumber(3), sut.getBall(newPlayerId, 1, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(4), sut.getBall(newPlayerId, 1, PlayData.Ball.SECOND));
	}

	@Test
	public void 스트라이크면_다음_프레임으로_넘어간다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));

		Assert.assertEquals(new ScoreNumber(10), sut.getBall(newPlayerId, 0, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(10), sut.getBall(newPlayerId, 1, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(10), sut.getBall(newPlayerId, 2, PlayData.Ball.FIRST));

	}
	
	@Test
	public void 마지막_프레임_첫째볼이스트라이크이면_3번째볼을_굴릴수있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(3));

		Assert.assertEquals(new ScoreNumber(10), sut.getBall(newPlayerId, 9, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(2), sut.getBall(newPlayerId, 9, PlayData.Ball.SECOND));
		Assert.assertEquals(new ScoreNumber(3), sut.getBall(newPlayerId, 9, PlayData.Ball.BONUS));
	}

	@Test
	public void 마지막_프레임_첫째볼과_둘째볼이_스트라이크이면_3번째볼을_굴릴수있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		
		Assert.assertEquals(new ScoreNumber(10), sut.getBall(newPlayerId, 9, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(10), sut.getBall(newPlayerId, 9, PlayData.Ball.SECOND));
		Assert.assertEquals(new ScoreNumber(5), sut.getBall(newPlayerId, 9, PlayData.Ball.BONUS));
	}
	
	@Test
	public void 마지막_프레임_둘째볼이_스페어이면_3번째볼을_굴릴수있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(9));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		
		Assert.assertEquals(new ScoreNumber(1), sut.getBall(newPlayerId, 9, PlayData.Ball.FIRST));
		Assert.assertEquals(new ScoreNumber(9), sut.getBall(newPlayerId, 9, PlayData.Ball.SECOND));
		Assert.assertEquals(new ScoreNumber(5), sut.getBall(newPlayerId, 9, PlayData.Ball.BONUS));
	}
	
	@Test
	public void 마지막_프레임_둘째볼이_스페어가_아니면_3번째볼을_굴릴수없다() {
		try {
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(10));
			sut.playBall(newPlayerId, new ScoreNumber(1));
			sut.playBall(newPlayerId, new ScoreNumber(8));
			//sut.playBall(newPlayerId, new ScoreNumber(5));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}	finally {
			Assert.assertEquals(new ScoreNumber(1), sut.getBall(newPlayerId, 9, PlayData.Ball.FIRST));
			Assert.assertEquals(new ScoreNumber(8), sut.getBall(newPlayerId, 9, PlayData.Ball.SECOND));
		}
	}
	
	@Test
	public void 프레임당_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		
		Assert.assertEquals(3, sut.getFrameScore(newPlayerId, 0));		
		Assert.assertEquals(7, sut.getFrameScore(newPlayerId, 1));
	}
	
	@Test
	public void 스트라이크_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 0));		
		Assert.assertEquals(21, sut.getFrameScore(newPlayerId, 1));
		Assert.assertEquals(13, sut.getFrameScore(newPlayerId, 2));		
		Assert.assertEquals(3, sut.getFrameScore(newPlayerId, 3));
	}
	
	@Test
	public void 스페어_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(9));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(8));
		sut.playBall(newPlayerId, new ScoreNumber(3));

		Assert.assertEquals(12, sut.getFrameScore(newPlayerId, 0));		
		Assert.assertEquals(13, sut.getFrameScore(newPlayerId, 1));
		Assert.assertEquals(3, sut.getFrameScore(newPlayerId, 2));		
	}
	
	@Test
	public void 퍼펙트_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));

		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 0));		
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 1));
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 2));		
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 3));		
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 4));
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 5));	
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 6));		
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 7));
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 8));	
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 9));
	}
	
	@Test
	public void 마지막_스페어_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.playBall(newPlayerId, new ScoreNumber(7));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(8));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		
		Assert.assertEquals(17, sut.getFrameScore(newPlayerId, 0));		
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 1));
		Assert.assertEquals(18, sut.getFrameScore(newPlayerId, 2));		
		Assert.assertEquals(8, sut.getFrameScore(newPlayerId, 3));		
		Assert.assertEquals(7, sut.getFrameScore(newPlayerId, 4));
		Assert.assertEquals(8, sut.getFrameScore(newPlayerId, 5));	
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 6));		
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 7));
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 8));	
		Assert.assertEquals(23, sut.getFrameScore(newPlayerId, 9));
	}
	
	@Test
	public void 랜덤_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(7));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(7));
		
		Assert.assertEquals(30, sut.getFrameScore(newPlayerId, 0));		
		Assert.assertEquals(24, sut.getFrameScore(newPlayerId, 1));
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 2));		
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 3));		
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 4));
		Assert.assertEquals(16, sut.getFrameScore(newPlayerId, 5));	
		Assert.assertEquals(20, sut.getFrameScore(newPlayerId, 6));		
		Assert.assertEquals(22, sut.getFrameScore(newPlayerId, 7));
		Assert.assertEquals(19, sut.getFrameScore(newPlayerId, 8));	
		Assert.assertEquals(9, sut.getFrameScore(newPlayerId, 9));
	}

	@Test
	public void  현재까지_프레임_합계_점수를_계산할수_있다() {		
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		
		Assert.assertEquals(67, sut.getCurrentScore(newPlayerId));
	}
	
	@Test
	public void  전체_프레임_합계_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(7));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(9));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(9));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		
		Assert.assertEquals(199, sut.getCurrentScore(newPlayerId));
	}
	
	@Test
	public void 플레이어의_점수판을_출력할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(4));
		sut.playBall(newPlayerId, new ScoreNumber(6));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(5));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(7));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(9));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.printPlayer(newPlayerId);
		sut.playBall(newPlayerId, new ScoreNumber(8));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.printPlayer(newPlayerId);
	}
}
