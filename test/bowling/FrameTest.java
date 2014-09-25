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
	public void 점수를_출력할수_있다() {
		Frame sut = new Frame(0);
		try {
		sut.setBall(new ScoreNumber(3));
		sut.setBall(new ScoreNumber(5));
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} finally {
			sut.printBalls();
		}
	}
}
