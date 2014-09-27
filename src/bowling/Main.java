package bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.ScoreNumber;

public class Main {
	static List<PlayerId> playerList = new ArrayList<PlayerId>();
	static Scanner sc = new Scanner(System.in);
	static Bowling bowling = new Bowling();
	static int score;
	
	public static void main(String[] args) {
		setPlayer(playerList, bowling);
		for (int turn = 0; turn < 9; turn++) {
			playBowling();
		}
		playFinalFrame();
		System.out.println("볼링을 종료합니다. 감사합니다.");
	}

	private static void playFinalFrame() {
		for (int playOrder = 0; playOrder < playerList.size(); playOrder++) {
			PlayerId targetId = playerList.get(playOrder);
			String targetName = bowling.getPlayerName(targetId);
			sc = new Scanner(System.in);
			
			System.out.println(targetName + "님의 차례입니다.");
			int firstScore = putBallScore(targetId);
			
			if (firstScore == 10) {
				putBallScore(targetId);
				putBallScore(targetId);
				bowling.printPlayer(targetId);
				continue;
			}

			int secondScore = putBallScore(targetId);
			if (secondScore == 10 || firstScore + secondScore == 10) {
				putBallScore(targetId);
			}
			bowling.printPlayer(targetId);
		}
	}

	private static void playBowling() {
		for (int playOrder = 0; playOrder < playerList.size(); playOrder++) {
			PlayerId targetId = playerList.get(playOrder);
			String targetName = bowling.getPlayerName(targetId);			
			sc = new Scanner(System.in);
			
			System.out.println(targetName+ "님의 차례입니다.");
			putBallScore(targetId);
			
			if (score < 10) {
				putBallScore(targetId);
			}
			bowling.printPlayer(targetId);
		}
	}

	private static int putBallScore(PlayerId targetId) {
		System.out.print("핀을 몇개 맞추셨나요?: ");
		score = sc.nextInt();
		bowling.playBall(targetId, new ScoreNumber(score));
		return score;
	}

	private static void setPlayer(List<PlayerId> playerList, Bowling bowling) {
		System.out.print("몇 분이 플레이하시나요?: ");
		int numberOfPlayer = sc.nextInt();
		
		for (int i = 0; i < numberOfPlayer; i++) {
			System.out.print(i+1 +" 번째 손님, 이름을 입력해주세요: ");
			sc = new Scanner(System.in);
			String name = sc.nextLine();
			playerList.add(bowling.addPlayer(name));
		}
	}

}
