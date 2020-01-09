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
import sk.tsystems.gamestudio.game.npuzzle.core.Field;
import sk.tsystems.gamestudio.game.npuzzle.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController {

	private Field field;
	private long startTime;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RatingService ratingService;

	@Autowired
	private MainController mainController;
	
	
	@RequestMapping("/puzzle")
	public String index() {
		field = new Field(4, 4);	
		startTime = System.currentTimeMillis();
		return "puzzle";
	}
	
	@RequestMapping("/puzzle/move")
	public String move(int tile) {
		field.move(tile);
		Date date = Calendar.getInstance().getTime();
		if (isSolved() && mainController.isLogged()) {
			long finalTime = System.currentTimeMillis() - startTime;
			int finalMiliSecond = (int)finalTime;
			int score = (300000-finalMiliSecond)/100;
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "puzzle", score, date));
		}
	
		return "puzzle";
	}
	
	@RequestMapping("/puzzle/comment")
	public String comment(String content) {
		try {
			commentService.addComment(new Comment(mainController.getLoggedPlayer().getName(), "puzzle", content));
		} catch (NullPointerException e) {
		}
		return "puzzle";
	}
	@RequestMapping("/puzzle/rating")
	public String setRating(int rating) {
		try {
			ratingService.setRating(new Rating(mainController.getLoggedPlayer().getName(), "puzzle", rating));
		} catch (NullPointerException e) {
		}
		return "puzzle";
	}
	
	public String getHtmlField() {
		Formatter f = new Formatter();
		
		f.format("<table>\n");
		for (int row = 0; row < field .getRowCount(); row++) {
			f.format("<tr>\n");
			for (int col = 0; col < field.getColumnCount(); col++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, col);
				if (tile != null) {
					f.format("<a href='/puzzle/move?tile=%d'><img src='/images/img%d.jpg'/></a>", tile.getValue(), tile.getValue());
				}
				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}
		f.format("</table>\n");
		
		return f.toString();
		
	}
	public boolean isSolved() {
		return field.isSolved();
	}
	
	public List<Score> getScores(){
		return scoreService.getTopScores("puzzle", 10);
	}
	public List<Score> getScoresMain(){
		return scoreService.getTopScores("puzzle", 1);
	}
	public List<Comment> getComment(){
		return commentService.getComment("puzzle");
	}

	public double getRating(){
		return ratingService.getAverageRating();
	}
}
