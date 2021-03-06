package core;

public class ScoreNumber {
	private int number;

	public ScoreNumber(int number) {
		if (number < 0) {
			throw new IllegalArgumentException(String.format("점수는 0 이상이어야 한다. 현재 값 : %d", number));
		}

		this.number = number;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public ScoreNumber plus(ScoreNumber operand) {
		return new ScoreNumber(this.number + operand.number);
	}
	
	public Boolean overs(ScoreNumber operand) {
		return this.number > operand.number;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreNumber other = (ScoreNumber) obj;
		if (number != other.number)
			return false;
		return true;
	}
}
