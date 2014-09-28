package bowling;

import core.ScoreNumber;

class Player {
	private PlayerId id;
	private String name;
	private Board board;
		
	// 팩토리 메소드
	static Player create(PlayerId id, String name) {
		return new Player(id, name);
	}
	
	String getName() {
		return name;
	}
	
	String setName(String name) {
		this.name = name;
		return this.name;
	}
	
	PlayerId getId() {
		return id;
	}
	
	ScoreNumber setBoard(ScoreNumber score) {
		board.setFrame(score);
		return score;
	}
	
	ScoreNumber getBall(int turn, PlayData.Ball ball) {
		return board.getBall(turn, ball);
	}
	
	int getFrameScore(int turn) {
		return board.getFrameScore(turn);
	}
	
	int getScore() {
		return board.getCurrentScore();
	}
	
	void printBoard() {
		System.out.println("이름: " + name + ", 점수 총합: " + getScore());
		for (int i = 1; i < 11; i++) {
			System.out.print("\t"+ i +"\t|");
		}
		System.out.println();
		board.printFrames();
		System.out.println();
	}
	
	// 생성자 (팩토리 메소드로 전달)
	private Player(PlayerId id, String name) {
		this.id = id;
		this.name = name;
		board = new Board();
	}
	
	protected void setId(PlayerId id) {
		this.id = id;
	}
	
	protected boolean isSameId(PlayerId id) {
		return this.id.equals(id);
	}
	
}
