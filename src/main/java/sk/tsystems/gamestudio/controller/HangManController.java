package sk.tsystems.gamestudio.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Rating;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.hangman.HangMan;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
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
	private CommentService commentService;
	
	@Autowired
	private RatingService ratingService;

	@Autowired
	private MainController mainController;

	@RequestMapping("/hangman")
	public String getNumber() {
		objectWeb = new HangMan();
		startTime = System.currentTimeMillis();
		input = "";
		return "hangman";
	}

	@RequestMapping("/hangman/guess")
	public String getNewNumber(String input) {
		this.input = input;
		Date date = Calendar.getInstance().getTime();
		if (objectWeb.isSolved() && mainController.isLogged()) {
			long finalTime = System.currentTimeMillis() - startTime;
			int finalMiliSecond = (int)finalTime;
			int score = (300000-finalMiliSecond)/100;
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "hangman", score, date));
		}
		return "hangman";
	}
	@RequestMapping("/hangman/comment")
	public String comment(String content) {
		try {
			commentService.addComment(new Comment(mainController.getLoggedPlayer().getName(), "hangman", content));
		} catch (NullPointerException e) {
		}
		return "hangman";
	}
	@RequestMapping("/hangman/rating")
	public String setRating(int rating) {
		try {
			ratingService.setRating(new Rating(mainController.getLoggedPlayer().getName(), "hangman", rating));
		} catch (NullPointerException e) {
		}
		return "hangman";
	}

	public String getMessage() {
		Formatter f = new Formatter();

		try {
			if (objectWeb.isSolved()) {
//				return "You Win!!" + " Uhadol si meno " + objectWeb.getName();
				return "hangman";
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
		f.format("Lives: " + objectWeb.getHealth());
		return f.toString();
	}

	public boolean isSolved() {
		return objectWeb.isSolved();
	}

	public List<Score> getScores(){
		return scoreService.getTopScores("hangman", 10);
	}
	public List<Score> getScoresMain(){
		return scoreService.getTopScores("hangman", 1);
	}
	public List<Comment> getComment(){
		return commentService.getComment("hangman");
	}

}
