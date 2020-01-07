package sk.tsystems.gamestudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.entity.Player;
import sk.tsystems.gamestudio.service.RegisterService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MainController {
	
	private Player loggedPlayer;
	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/login")
	public String login(Player player) {
		Player one = registerService.getRegister(player.getName());
		if (one==null)
			return "redirect:/";
		if (one.getName()
				.equals(player.getName()) 
			&& 
			one.getPass()
				.equals(player.getPass())) {
			loggedPlayer = player;	
		}
		
//		Player one = registerService.getRegister(player.getName());
//		if ("heslo".equals(player.getPass())) {
//			loggedPlayer = player;			
//		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout() {
			loggedPlayer = null;			
		return "redirect:/";
	}
	
	public boolean isLogged() {
		return loggedPlayer != null;
	}
	
	public Player getLoggedPlayer() {
		return loggedPlayer;
	}
	
	@RequestMapping("/register")
	public String register(Player player) {
		registerService.addPlayer(player);
		return "redirect:/";
	}
	
}
