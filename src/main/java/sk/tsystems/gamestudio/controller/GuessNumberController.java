package sk.tsystems.gamestudio.controller;

import java.util.Random;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.game.guessnumber.GuessNumber;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumberController {

	private Random rand;
	private int hadaneCislo;
	private String input;
	private GuessNumber objectWeb;
	private long startTime;

	@RequestMapping("/guess")
	public String getNumber() {
		rand = new Random();
		hadaneCislo = rand.nextInt(20) + 1;
		startTime = System.currentTimeMillis();
		input = "";
		return "guess";
	}
	
	@RequestMapping("/guess/new")
	public String getNumber(String input) {
		this.input = input;
		return "guess";
	}

	public String getMessage() {
		int scan;
		try {
			scan = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return "Guess number please";
		}
		if (hadaneCislo < scan) {
			return "Guess Number is lower";
		} else if (hadaneCislo > scan) {
			return "Guess Number is higher";
		} else {
			return "WIN !!!";}
	}
	public boolean isSolved() {
		int scan;
		try {
			scan = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return false;
		}
		if (hadaneCislo!=scan) {
			return false;
		}
		return true;
	}
}
