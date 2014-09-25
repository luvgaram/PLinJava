package bowling;

import core.ScoreNumber;

public class Player {
	private PlayerId id;
	private String name;
	private Board board;
		
	protected void setId(PlayerId id) {
		this.id = id;
	}

	protected boolean isSameId(PlayerId id) {
		return this.id.equals(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayerId getId() {
		return id;
	}

	public static Player create(PlayerId id, String name) {
		return new Player(id, name);
	}
	
	private Player(PlayerId id, String name) {
		this.id = id;
		this.name = name;
		board = new Board();
	}

	public void setBoard(ScoreNumber score) {
		board.setFrame(score);
	}

	public Board getBoard() {
		return board;
	}
	
}
