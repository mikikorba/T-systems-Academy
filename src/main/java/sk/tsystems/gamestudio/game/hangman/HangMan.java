package sk.tsystems.gamestudio.game.hangman;

import java.text.Normalizer;
import java.util.Random;

public class HangMan {
	private static final String[] NAMES = { 
//			"Adam", "Adolf", "Adrián", "Alan", "Albert", "Albín", "Aleš", "Alexander",
//			"Alexej", "Alfonz", "Alfréd", "Alojz", "Ambróz", "Andrej", "Anton", "Arnold", "Arpád", "Augustín", "Aurel",
//			"Bartolomej", "Belo", "Beňadik", "Benjamín", "Bernard", "Blahoslav", "Blažej", "Bohdan", "Bohumil",
//			"Bohumír", "Bohuš", "Bohuslav", "Boleslav", "Bonifác", "Boris", "Branislav", "Bruno", "Bystrík", "Ctibor",
//			"Cyprián", "Cyril", "Dalibor", "Daniel", "Dávid", "Demeter", "Denis", "Dezider", "Dionýz", "Dobroslav",
//			"Dominik", "Drahomír", "Drahoslav", "Dušan", "Edmund", "Eduard", "Eliáš", "Emanuel", "Emil", "Erik",
//			"Ernest", "Ervín", "Eugen", "Fedor", "Félix", "Ferdinand", "Filip", "Florián", "František", "Frederik",
//			"Fridrich", "Gabriel", "Gašpar", "Gejza", "Gregor", "Gustáv", "Henrich", "Hubert", "Hugo", "Ignác", "Igor",
//			"Iľja", "Imrich", "Ivan", "Izidor", "Jakub", "Ján", "Jarolím", "Jaromír", "Jaroslav", "Jerguš", "Jozef",
//			"Július", "Juraj", "Kamil", "Karol", "Kazimír", "Klaudius", "Klement", "Koloman", "Konštantín", "Kornel",
//			"Kristián", "Krištof", "Ladislav", "Leonard", "Leopold", "Levoslav", "Ľubomír", "Ľubor", "Ľuboš",
//			"Ľuboslav", "Ľudomil", "Ľudovít", "Lukáš", "Marcel", "Marek", 
			"Marián"
//			, "Mário", "Martin", "Matej", "Matúš",
//			"Maximilián", "Medard", "Metod", "Michal", "Mikuláš", "Milan", "Miloš", "Miloslav", "Miroslav", "Mojmír",
//			"Móric", "Nikolaj", "Norbert", "Oldrich", "Oleg", "Oliver", "Ondrej", "Oskár", "Oto", "Pankrác", "Patrik",
//			"Pavol", "Peter", "Pravoslav", "Prokop", "Radomír", "Radoslav", "Radovan", "Radúz", "Rastislav", "René",
//			"Richard", "Róbert", "Roland", "Roman", "Rudolf", "Samuel", "Sergej", "Servác", "Severín", "Silvester",
//			"Šimon", "Slavomír", "Stanislav", "Štefan", "Svätopluk", "Svetozár", "Tadeáš", "Teodor", "Tibor",
//			"Tichomír", "Timotej", "Tomáš", "Urban", "Václav", "Valentín", "Valér", "Vasil", "Vavrinec", "Vendelín",
//			"Viktor", "Viliam", "Vincent", "Vít", "Víťazoslav", "Vladimír", "Vladislav", "Vlastimil", "Vojtech",
//			"Vratislav", "Vratko", "Zdenko", "Žigmund", "Zlatko", "Zoltán" 
			};
	private Random rand;
	private char[] arrayGuess;
	private String name;
	private int health = 10;
	

	public HangMan() {
		rand = new Random();
		genereteName();
	}
	
	public String guess(char c) {
		boolean goodGuess = false;
		String nameWithoutInterpunction = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		
		if (nameWithoutInterpunction.charAt(0) == Character.toUpperCase(c)) {
			arrayGuess[0] = Character.toUpperCase(name.charAt(0));
			goodGuess = true;
		}
		for (int i = 1; i < arrayGuess.length; i++) {
			if (nameWithoutInterpunction.charAt(i) == c) {
				arrayGuess[i] = name.charAt(i);
				goodGuess = true;
			}
		}
		if(!goodGuess)
			health--;
		return new String(arrayGuess);
	}
	
	public void genereteName() {
		name = NAMES[rand.nextInt(NAMES.length)];
		arrayGuess = new char[name.length()];

		for (int j = 0; j < name.length(); j++) {
			arrayGuess[j] = '-';
		}
	}

	public boolean isSolved() {
		for (int i = 0; i < arrayGuess.length; i++) {
			if (arrayGuess[i] == '-') {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public String getGuessName() {
		return new String(arrayGuess);
	}

	public int getHealth() {
		return health;
	}
}

