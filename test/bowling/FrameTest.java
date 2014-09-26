package bowling;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.ScoreNumber;

public class FrameTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void 각_투구의_점수를_기억할수있다() {
		Frame sut = new Frame(0);
		sut.setBall(new ScoreNumber(4));
		sut.setBall(new ScoreNumber(6));
		
		assertEquals(new ScoreNumber(4), sut.numbers[0]);
		assertEquals(new ScoreNumber(6), sut.numbers[1]);
	}

	@Test
	public void 지정_투구_이상은_기록하지_않는다() {
		try {
			Frame sut = new Frame(0);
			sut.setBall(new ScoreNumber(2));
			sut.setBall(new ScoreNumber(6));
			sut.setBall(new ScoreNumber(1));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void 둘째_투구에서_스페어_이상은_기록하지_않는다() {
		try {
			Frame sut = new Frame(0);
			sut.setBall(new ScoreNumber(5));
			sut.setBall(new ScoreNumber(6));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void 개별_투구의_점수를_출력할수_있다() {
		Frame sut = new Frame(0);
		sut.setBall(new ScoreNumber(3));
		sut.setBall(new ScoreNumber(5));
		sut.printBalls();
	}
	
	@Test
	public void 스페어를_출력할수_있다() {
		Frame sut = new Frame(0);
		sut.setBall(new ScoreNumber(3));
		sut.setBall(new ScoreNumber(7));
		sut.printBalls();
	}
	
	@Test
	public void 스트라이크를_출력할수_있다() {
		Frame sut = new Frame(0);
		sut.setBall(new ScoreNumber(10));
		sut.printBalls();
	}
	
	@Test
	public void 마지막_프레임_원스트라이크를_출력할수_있다() {
		Frame sut = new Frame(9);
		sut.setBall(new ScoreNumber(10));
		sut.setBall(new ScoreNumber(2));
		sut.setBall(new ScoreNumber(5));
		sut.printBalls();
	}
	
	@Test
	public void 마지막_프레임_투스트라이크를_출력할수_있다() {
		Frame sut = new Frame(9);
		sut.setBall(new ScoreNumber(10));
		sut.setBall(new ScoreNumber(10));
		sut.setBall(new ScoreNumber(5));
		sut.printBalls();
	}
	
	@Test
	public void 마지막_프레임_터키를_출력할수_있다() {
		Frame sut = new Frame(9);
		sut.setBall(new ScoreNumber(10));
		sut.setBall(new ScoreNumber(10));
		sut.setBall(new ScoreNumber(10));
		sut.printBalls();
	}
	
	@Test
	public void 마지막_프레임_스페어를_출력할수_있다() {
		Frame sut = new Frame(9);
		sut.setBall(new ScoreNumber(1));
		sut.setBall(new ScoreNumber(9));
		sut.setBall(new ScoreNumber(1));
		sut.printBalls();
	}
	
	@Test
	public void 마지막_프레임_보너스스페어를_출력할수_있다() {
		Frame sut = new Frame(9);
		sut.setBall(new ScoreNumber(10));
		sut.setBall(new ScoreNumber(9));
		sut.setBall(new ScoreNumber(1));
		sut.printBalls();
	}
	
	@Test
	public void 마지막_프레임에서_보너스가_없을때를_출력할수_있다() {
		Frame sut = new Frame(9);
		sut.setBall(new ScoreNumber(2));
		sut.setBall(new ScoreNumber(2));
		sut.printBalls();
	}
	
}
