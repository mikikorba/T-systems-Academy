package sk.tsystems.gamestudio.game.obesenec;

import java.util.Random;
import java.util.Scanner;

public class Obesenec {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		Random rand;
		rand = new Random();
		
		String[] namesSK = { "Adam", "Adolf", "Adrian"};

		String t = "Try again";;
		do {
//			int hadanyString = rand.nextInt(namesSK[]);
			System.out.print("Please enter your selection <X> EXIT or some NUMBER :");
			do {
				try {
					String scanf = scanner.nextLine().toUpperCase();
					if (scanf.equals("X"))
						return;
					scan = Integer.parseInt(scanf);
				} catch (NumberFormatException e) {
					System.err.println("Invalid tile number!");
					System.err.println(t);
					continue;
				}
			} while (true);
			do {
				System.out.println("Play again?");
				System.out.println("Y or N");
				System.out.println();
				try {
					scan2 = scanner.nextLine().toUpperCase().charAt(0);
				} catch (Exception e) {
					System.err.println("Invalid input!");
					System.out.println(scan2);
					System.err.println(t);
					e.printStackTrace();
				}
			} while (!(scan2 == 'Y' || scan2 == 'N'));
		} while (scan2 != 'N');
		System.err.println("Thanks for playing :) ");

	}

}
