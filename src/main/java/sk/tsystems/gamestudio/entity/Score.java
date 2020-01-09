package sk.tsystems.gamestudio.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Score {
	@Id
	@GeneratedValue
	private int ident;
	private String username;
	private String game;
	private int value;
	private Date date;
	
	public Score() {
	}
	
	public Score(String username, String game, int value, Date date) {
		this.username = username;
		this.game = game;
		this.value = value;
		this.date = date;
	}

	public int getIdent() {
		return ident;
	}
	
	public void setIdent(int ident) {
		this.ident = ident;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGame() {
		return game;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Score [ident=" + ident + ", username=" + username + ", game=" + game + ", value=" + value + "]";
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
