package sk.tsystems.gamestudio.service;

import sk.tsystems.gamestudio.entity.Player;


public interface RegisterService {
	void addPlayer(Player player);
	
	Player getRegister(String player);
}
