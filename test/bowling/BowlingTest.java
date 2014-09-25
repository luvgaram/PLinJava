package bowling;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import core.ScoreNumber;

public class BowlingTest {

	Bowling sut = new Bowling();
	PlayerId  newPlayerId = sut.addPlayer("test");
	
	@Test
	public void 플레이어를_추가할수_있다() {
		Assert.assertEquals("test", sut.findPlayerById(newPlayerId).getName());
	}
	
	@Test
	public void 플레이어_이름을_바꿀수_있다() {
		sut.findPlayerById(newPlayerId).setName("test2");
		Assert.assertEquals("test2", sut.findPlayerById(newPlayerId).getName());
	}
	
	@Test
	public void 공을_굴릴수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		ScoreNumber[] frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getBalls();
		Assert.assertEquals(new ScoreNumber(1), frame[0]);
		Assert.assertEquals(new ScoreNumber(2), frame[1]);
		
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(4));
		frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getBalls();
		Assert.assertEquals(new ScoreNumber(3), frame[0]);
		Assert.assertEquals(new ScoreNumber(4), frame[1]);
	}
	
	@Test
	public void 스트라이크면_다음_프레임으로_넘어간다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		ScoreNumber[] frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getBalls();
		Assert.assertEquals(new ScoreNumber(10), frame[0]);
		
		sut.playBall(newPlayerId, new ScoreNumber(10));
		frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getBalls();
		Assert.assertEquals(new ScoreNumber(10), frame[0]);
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
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		ScoreNumber[] frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getBalls();
		Assert.assertEquals(new ScoreNumber(10), frame[0]);
		Assert.assertEquals(new ScoreNumber(10), frame[1]);
		Assert.assertEquals(new ScoreNumber(10), frame[2]);
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
		sut.playBall(newPlayerId, new ScoreNumber(10));
		
		ScoreNumber[] frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getBalls();
		Assert.assertEquals(new ScoreNumber(10), frame[0]);
		Assert.assertEquals(new ScoreNumber(10), frame[1]);
		Assert.assertEquals(new ScoreNumber(10), frame[2]);
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
		ScoreNumber[] frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getBalls();
		Assert.assertEquals(new ScoreNumber(1), frame[0]);
		Assert.assertEquals(new ScoreNumber(9), frame[1]);
		Assert.assertEquals(new ScoreNumber(5), frame[2]);
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
			sut.playBall(newPlayerId, new ScoreNumber(5));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} finally {
			try{
				ScoreNumber[] frame = sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getBalls();
				Assert.assertEquals(new ScoreNumber(1), frame[0]);
				Assert.assertEquals(new ScoreNumber(8), frame[1]);
				Assert.assertNull(frame[2]);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
	}
	
	@Test
	public void 점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(3));
		sut.playBall(newPlayerId, new ScoreNumber(4));

		Assert.assertEquals(3, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(7, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
	}
	
	@Test
	public void 스트라이크_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(10));
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(2));

		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(21, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
		Assert.assertEquals(13, sut.findPlayerById(newPlayerId).getBoard().getFrame(2).getTotalScore());
		Assert.assertEquals(3, sut.findPlayerById(newPlayerId).getBoard().getFrame(3).getTotalScore());
	}
	
	@Test
	public void 스페어_점수를_계산할수_있다() {
		sut.playBall(newPlayerId, new ScoreNumber(1));
		sut.playBall(newPlayerId, new ScoreNumber(9));
		sut.playBall(newPlayerId, new ScoreNumber(2));
		sut.playBall(newPlayerId, new ScoreNumber(8));
		sut.playBall(newPlayerId, new ScoreNumber(3));

		Assert.assertEquals(12, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(13, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
		Assert.assertEquals(3, sut.findPlayerById(newPlayerId).getBoard().getFrame(2).getTotalScore());
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

		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(2).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(3).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(4).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(5).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(6).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(7).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(8).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getTotalScore());
	}
	
	@Test
	public void 마지막_스페어_점수를_계산할수_있다() {
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

		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(2).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(3).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(4).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(5).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(6).getTotalScore());
		Assert.assertEquals(21, sut.findPlayerById(newPlayerId).getBoard().getFrame(7).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(8).getTotalScore());
		Assert.assertEquals(15, sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getTotalScore());
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
		
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(24, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(2).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(3).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(4).getTotalScore());
		Assert.assertEquals(16, sut.findPlayerById(newPlayerId).getBoard().getFrame(5).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(6).getTotalScore());
		Assert.assertEquals(22, sut.findPlayerById(newPlayerId).getBoard().getFrame(7).getTotalScore());
		Assert.assertEquals(19, sut.findPlayerById(newPlayerId).getBoard().getFrame(8).getTotalScore());
		Assert.assertEquals(9, sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getTotalScore());
	}
	
	@Test
	public void 랜덤_점수를_계산할수_있다_투() {
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
		
		Assert.assertEquals(13, sut.findPlayerById(newPlayerId).getBoard().getFrame(0).getTotalScore());
		Assert.assertEquals(8, sut.findPlayerById(newPlayerId).getBoard().getFrame(1).getTotalScore());
		Assert.assertEquals(27, sut.findPlayerById(newPlayerId).getBoard().getFrame(2).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(3).getTotalScore());
		Assert.assertEquals(19, sut.findPlayerById(newPlayerId).getBoard().getFrame(4).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(5).getTotalScore());
		Assert.assertEquals(30, sut.findPlayerById(newPlayerId).getBoard().getFrame(6).getTotalScore());
		Assert.assertEquals(29, sut.findPlayerById(newPlayerId).getBoard().getFrame(7).getTotalScore());
		Assert.assertEquals(20, sut.findPlayerById(newPlayerId).getBoard().getFrame(8).getTotalScore());
		Assert.assertEquals(13, sut.findPlayerById(newPlayerId).getBoard().getFrame(9).getTotalScore());
	}
}
