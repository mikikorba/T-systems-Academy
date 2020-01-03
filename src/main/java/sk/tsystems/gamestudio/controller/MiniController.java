package sk.tsystems.gamestudio.controller;

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
import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.GameState;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;
import sk.tsystems.gamestudio.service.CommentService;
import sk.tsystems.gamestudio.service.RatingService;
import sk.tsystems.gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MiniController {
	private Field field;
	private long startTime;
	private boolean marking;

	public boolean isMarking() {
		return marking;
	}
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private MainController mainController;
	
	@Autowired
	private RatingService ratingService;

	@RequestMapping("/mini")
	public String index() {
		field = new Field(10, 10, 5);
		startTime = System.currentTimeMillis();
		return "mini";
	}

	@RequestMapping("/mini/openTile")
	public String openTile(int row, int column) {
		if (field.getState() == GameState.PLAYING) {
			if (marking)
				field.markTile(row, column);
			else {
				field.openTile(row, column);
			}

		}
		if (isWin() && mainController.isLogged()) {
			long finalTime = System.currentTimeMillis() - startTime;
			int finalMiliSecond = (int)finalTime;
			int score = (300000-finalMiliSecond)/100;
			scoreService.addScore(new Score(mainController.getLoggedPlayer().getName(), "mini", score));
		}
		else if (isLose() && mainController.isLogged()) {
			}
		return "mini";
	}

	@RequestMapping("/mini/change")
	public String change() {
		marking = !marking;
		return "mini";
	}
	
	@RequestMapping("/mini/comment")
	public String comment(String content) {
		try {
			commentService.addComment(new Comment(mainController.getLoggedPlayer().getName(), "mini", content));
		} catch (NullPointerException e) {
		}
		return "mini";
	}
	@RequestMapping("/mini/rating")
	public String setRating(int rating) {
		try {
			ratingService.setRating(new Rating(mainController.getLoggedPlayer().getName(), "mini", rating));
		} catch (NullPointerException e) {
		}
		return "mini";
	}

	public String getHtmlField() {
		@SuppressWarnings("resource")
		Formatter f = new Formatter();

		f.format("<table>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			f.format("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				f.format("<td>\n");
				Tile tile = field.getTile(row, column);

					f.format("<a href='/mini/openTile?row=%d&column=%d'>", row, column);
					f.format("<img src='/images/mini/%s.png'></a>", getImageName(tile));

				f.format("</td>\n");
			}
			f.format("</tr>\n");
		}
		f.format("</table>\n");
		return f.toString();
	}

	public String getImageName(Tile tile) {
		switch (tile.getState()) {
		case CLOSED:
			return "closed";
		case MARKED:
			return "marked";
		case OPEN:
			if (tile instanceof Clue)
				return "open" + ((Clue) tile).getValue();
			else
				return "mine";
		default:
			throw new IllegalArgumentException();
		}
	}

	public boolean isSolved() {
		return field.isSolved();
	}
	public boolean isLose() {
		return field.isLose();
	}
	public boolean isWin() {
		return field.isWin();
	}
	
	public List<Score> getScores(){
		return scoreService.getTopScores("mini");
	}
	public List<Comment> getComment(){
		return commentService.getComment("mini");
	}
}