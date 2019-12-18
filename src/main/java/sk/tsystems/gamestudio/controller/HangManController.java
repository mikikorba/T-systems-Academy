package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.hangman.HangMan;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class HangManController {

	private String input;
	private HangMan objectWeb;
	private long startTime;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private MainController mainController;
	
	@RequestMapping("/hangman")
	public String getNumber(String input) {
		this.input = input;
		startTime = System.currentTimeMillis();
		if (objectWeb == null) {
			objectWeb = new HangMan();
		}

		return "hangman";
	}

	@RequestMapping("/hangman/new")
	public String getNewNumber() {
		objectWeb = new HangMan();
		input = "";
		long finalTime = System.currentTimeMillis() - startTime;
		int finalMiliSecond = (int)finalTime;
		int score = (300000-finalMiliSecond)/100;
		if (isSolved() && mainController.isLogged()) {
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "hangman", score));
		}
		return "hangman";
	}

	public String getMessage() {
		Formatter f = new Formatter();

		try {
			if (objectWeb.isSolved()) {
				return "You Win!!" + " Uhadol si meno " + objectWeb.getName();
			} else if (objectWeb.getHealth() < 1) {
				return "You Lose!!" + " Spravne meno bolo " + objectWeb.getName();
			} else if (input != null && input.length() > 0) {
				f.format(objectWeb.guess(input.charAt(0)));
				return f.toString();
			}
			f.format(objectWeb.getGuessName());
			return f.toString();

		} catch (NumberFormatException e) {
			return "NumberFormatException" + input;
		}
	}

	public String getMessage2() {
		Formatter f = new Formatter();
		f.format("Guess Name or die" + " " + objectWeb.getName());
		return f.toString();
	}

	public String getMessage3() {
		Formatter f = new Formatter();
		f.format("" + objectWeb.getHealth());
		return f.toString();
	}

	public boolean isSolved() {
		return objectWeb.isSolved();
	}
	public List<Score> getScores(){
		return scoreService.getTopScores("hangman");
	}

}
