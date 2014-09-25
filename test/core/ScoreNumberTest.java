package core;

import static org.junit.Assert.*;

import org.junit.Before;
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
	public void 십일_이상을_넣으면_에러가_발생해야한다() throws Exception {
		try {
			new ScoreNumber(11);
			fail("IllegalArgumentException 에러가 발생해야 한다.");
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
