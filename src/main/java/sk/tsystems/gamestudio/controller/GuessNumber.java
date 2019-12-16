package sk.tsystems.gamestudio.controller;

import java.util.Formatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.game.guessnumber.consoleui.ConsoleUI;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumber {
	
	@RequestMapping("/guess")
	public String index() {
		ConsoleUI ui = new ConsoleUI();
		ui.play();
		return "guess";
	}
	
	public String getHtmlField() {
		Formatter f = new Formatter();
		

		
		return f.toString();
		
	}
	

	public String getMessage() {
		return "Hello from Java";
	}
}
