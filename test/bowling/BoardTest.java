package bowling;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	Board sut = new Board();
	
	@Test
	public void 점수판을_만들수_있다() {
		assertEquals(0, sut.getCurrentFrame());
		assertEquals(0, sut.getCurrentScore());
	}
}
