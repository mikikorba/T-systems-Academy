package sk.tsystems.gamestudio.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Comment;
import sk.tsystems.gamestudio.entity.Score;
import sk.tsystems.gamestudio.game.guessnumber.GuessNumber;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumberController {

	private Random rand;
	private int hadaneCislo;
	private String input;
	private GuessNumber objectWeb;
	private long startTime;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private MainController mainController;
	
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
		if (isSolved() && mainController.isLogged()) {
			long finalTime = System.currentTimeMillis() - startTime;
			int finalMiliSecond = (int)finalTime;
			int score = (300000-finalMiliSecond)/100;
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "guess", score));
		}
		return "guess";
	}
	
	@RequestMapping("/guess/comment")
	public String comment(String content) {
		try {
			commentService.addComment(new Comment(mainController.getLoggedPlayer().getName(), "guess", content));
		} catch (NullPointerException e) {
		}
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
	public List<Score> getScores(){
		return scoreService.getTopScores("guess");
	}
	public List<Comment> getComment(){
		return commentService.getComment("guess");
	}
}
