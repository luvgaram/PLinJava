package bowling;

public class PlayerId implements Comparable<PlayerId> {
	private static int lastId = 0;
	private int id;
	
	protected static PlayerId issue() {
		lastId++;
		return new PlayerId(lastId);
	}

	private PlayerId(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PlayerId other = (PlayerId) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(PlayerId other) {
		if (this.id > other.id) {
			return 1;
		} else if (this.id == other.id) {
			return 0;
		} else {
			return -1;
		}
	}
}

