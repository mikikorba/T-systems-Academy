package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.game.hangman.Web;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class HangManController {

	private String input;
	
	private int health = 5;

	private Web objectWeb;

	@RequestMapping("/hangman")
	public String getNumber(String input) {
		if (objectWeb == null) {
			objectWeb = new Web();
		}
		this.input = input;
		return "hangman";
	}

	@RequestMapping("/hangman/new")
	public String getNewNumber() {
			objectWeb = new Web();
			input = "";
		return "hangman";
	}

	public String getMessage() {
		Formatter f = new Formatter();
		try {
			if (objectWeb.isSolved()) {
				return "You Win!!" + " " + objectWeb.getName();
			} else {
				if (input != null && input.length() > 0) {
					f.format(objectWeb.guess(input.charAt(0)));
					return f.toString();
				}
				f.format(objectWeb.getGuessName());
				return f.toString();
			}
		} catch (NumberFormatException e) {
			return "NumberFormatException" + input;
		}
	}
	public String getMessage2() {
		Formatter f = new Formatter();
		f.format("Guess Name or die" + " " + objectWeb.getName());
		return f.toString();
	}
}
