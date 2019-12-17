package sk.tsystems.gamestudio.controller;

import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class HangManController {

	private static String[] namesSK = { "Adam", "Adolf", "Adrián", "Alan", "Albert", "Albín", "Aleš", "Alexander",
			"Alexej", "Alfonz", "Alfréd", "Alojz", "Ambróz", "Andrej", "Anton", "Arnold", "Arpád", "Augustín", "Aurel",
			"Bartolomej", "Belo", "Beňadik", "Benjamín", "Bernard", "Blahoslav", "Blažej", "Bohdan", "Bohumil",
			"Bohumír", "Bohuš", "Bohuslav", "Boleslav", "Bonifác", "Boris", "Branislav", "Bruno", "Bystrík", "Ctibor",
			"Cyprián", "Cyril", "Dalibor", "Daniel", "Dávid", "Demeter", "Denis", "Dezider", "Dionýz", "Dobroslav",
			"Dominik", "Drahomír", "Drahoslav", "Dušan", "Edmund", "Eduard", "Eliáš", "Emanuel", "Emil", "Erik",
			"Ernest", "Ervín", "Eugen", "Fedor", "Félix", "Ferdinand", "Filip", "Florián", "František", "Frederik",
			"Fridrich", "Gabriel", "Gašpar", "Gejza", "Gregor", "Gustáv", "Henrich", "Hubert", "Hugo", "Ignác", "Igor",
			"Iľja", "Imrich", "Ivan", "Izidor", "Jakub", "Ján", "Jarolím", "Jaromír", "Jaroslav", "Jerguš", "Jozef",
			"Július", "Juraj", "Kamil", "Karol", "Kazimír", "Klaudius", "Klement", "Koloman", "Konštantín", "Kornel",
			"Kristián", "Krištof", "Ladislav", "Leonard", "Leopold", "Levoslav", "Ľubomír", "Ľubor", "Ľuboš",
			"Ľuboslav", "Ľudomil", "Ľudovít", "Lukáš", "Marcel", "Marek", "Marián", "Mário", "Martin", "Matej", "Matúš",
			"Maximilián", "Medard", "Metod", "Michal", "Mikuláš", "Milan", "Miloš", "Miloslav", "Miroslav", "Mojmír",
			"Móric", "Nikolaj", "Norbert", "Oldrich", "Oleg", "Oliver", "Ondrej", "Oskár", "Oto", "Pankrác", "Patrik",
			"Pavol", "Peter", "Pravoslav", "Prokop", "Radomír", "Radoslav", "Radovan", "Radúz", "Rastislav", "René",
			"Richard", "Róbert", "Roland", "Roman", "Rudolf", "Samuel", "Sergej", "Servác", "Severín", "Silvester",
			"Šimon", "Slavomír", "Stanislav", "Štefan", "Svätopluk", "Svetozár", "Tadeáš", "Teodor", "Tibor",
			"Tichomír", "Timotej", "Tomáš", "Urban", "Václav", "Valentín", "Valér", "Vasil", "Vavrinec", "Vendelín",
			"Viktor", "Viliam", "Vincent", "Vít", "Víťazoslav", "Vladimír", "Vladislav", "Vlastimil", "Vojtech",
			"Vratislav", "Vratko", "Zdenko", "Žigmund", "Zlatko", "Zoltán" };
	private Random rand = new Random();
	private int hadaneCislo = rand.nextInt(namesSK.length);
	private String input;
	private String hadanyString = namesSK[hadaneCislo];
	private int health = 5;
	private boolean check = false;
	private String[] arrayB = new String[hadanyString.length()];

	@RequestMapping("/hangman")
	public String getNumber(String input) {
		this.input = input;
		return "hangman";
	}

	@RequestMapping("/hangman/new")
	public String getNewNumber() {
		hadaneCislo = rand.nextInt(namesSK.length);
		hadanyString = namesSK[hadaneCislo];
		return "hangman";
	}

	public String getMessage() {
		Formatter f = new Formatter();
		try {
			if (hadanyString.equals(input)) {
				return "You Win!!" + hadanyString;

			} else {
				if (input!=null && input.length()>0) {
					for (int j = 0; j < hadanyString.length(); j++) {
						if (hadanyString.charAt(j) == input.toUpperCase().charAt(0)
								|| hadanyString.charAt(j) == input.toLowerCase().charAt(0)) {
							f.format(input);
						} else {
							f.format("-");
						}
					}
					f.format("Guess Name or die" + " " + hadanyString);
					return f.toString();
				} else
					return "Guess Name or die";
			}
		} catch (NumberFormatException e) {
			return "NumberFormatException" + input;
		}

	}
}