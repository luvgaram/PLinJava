package core;

import static org.junit.Assert.*;
import org.junit.Test;

public class ScoreNumberTest {

	@Test
	public void 숫자를_반환할수_있다() throws Exception {
		ScoreNumber number = new ScoreNumber(0);
		assertEquals(0, number.getNumber());
	}
	
	@Test
	public void 음수를_넣으면_에러가_발생해야한다() throws Exception {
		try {
			new ScoreNumber(-1);
			fail("IllegalArgumentException 에러가 발생해야 한다.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void 더하기_연산을_할수있다() {
		ScoreNumber number = new ScoreNumber(3);
		assertEquals(new ScoreNumber(8), number.plus(new ScoreNumber(5)));
	}
	
	@Test
	public void 같은지_비교_연산을_할수있다() {
		ScoreNumber number = new ScoreNumber(10);
		assertTrue(number.equals(new ScoreNumber(10)));
	}
	
	@Test
	public void 큰지_비교연산을_할수있다() {
		ScoreNumber number = new ScoreNumber(10);
		assertTrue(number.overs(new ScoreNumber(9)));
	}
}
