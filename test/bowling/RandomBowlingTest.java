package bowling;

import org.junit.Test;

public class RandomBowlingTest {
	
	RandomBowling sut = new RandomBowling();

	@Test
	public void 랜덤으로_볼을_굴릴수있다() {
		sut.playBall();
		sut.printPlayer();
	}
}
