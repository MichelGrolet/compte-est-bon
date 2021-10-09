import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Solveur du "compte est bon".
 * @author Michel Grolet & Antoine Chevaleyre
 * @version 1.0
 */
public class CompteEstBon {
	private static boolean compteEstBon = false;
	private static final ArrayList<Character> OPERATIONS = new ArrayList<>(Arrays.asList('+', '*', '-', '/'));
	private static int nbAttendu;
	private static ArrayList<String> calculsEffectues = new ArrayList<>();
	private static int nbProche = 0;
	private static int nbIterations = 1;

	public static void main(String[] args) {
		if (args.length!=7) {
			System.err.println("Erreur : Ce programme prends 7 entiers en parametre : les 6 nombres puis le resultat attendu.");
		} else {
			ArrayList<Integer> nombres = new ArrayList<Integer>();
			for (int i = 0; i < 6; i++) {
				nombres.add(Integer.parseInt(args[i]));
			}
			nbAttendu = Integer.parseInt(args[6]);
			boolean resultat = calculerCompteEstBon(nombres);
			afficherResultat(resultat);
		}
	}

	/**
	 * resoud "le compte est bon".
	 * @param valeurs plaques parmi lesquelles on fait les operations.
	 * @return true si le compte est bon.
	 */
	public static boolean calculerCompteEstBon(ArrayList<Integer> valeurs) {
		Collections.sort(valeurs);
		nbIterations++;
		compteEstBon = false;
		ArrayList<ArrayList<Integer>> couples = calculerCouples(valeurs);
		// Parcours des couples
		for (int iCouple = 0; iCouple < couples.size()-1 && !compteEstBon; iCouple++) {
			// Parcours des 4 operations
			for (int iOperation = 0; iOperation < 4 && !compteEstBon; iOperation++) {
				int a = couples.get(iCouple).get(0);
				int b = couples.get(iCouple).get(1);
				int resultat = calculer(a, b, OPERATIONS.get(iOperation));
				String calcul = a+""+OPERATIONS.get(iOperation)+""+b+" = "+resultat;
				if (resultat>=0) {
					if (resultat==nbAttendu) {
						calculsEffectues.add(0, calcul);
						return true;
					} else {
						ArrayList<Integer> nouvValeurs = new ArrayList<>(valeurs);
						nouvValeurs.remove(nouvValeurs.indexOf(a));
						nouvValeurs.remove(nouvValeurs.indexOf(b));
						nouvValeurs.add(resultat);
						// Appel recursif :
						compteEstBon = calculerCompteEstBon(nouvValeurs);
						if (compteEstBon) {
							calculsEffectues.add(0, calcul);
						}
					}	
				}
			}
		}
		return compteEstBon;
	}

	/**
	 * retourne les couples qu'on peut faire a partir d'un ensemble de nombres.
	 * @param valeurs entiers parmi lesquels on cree les couples.
	 * @return liste contenant le nombre minimal de couples.
	 */
	private static ArrayList<ArrayList<Integer>> calculerCouples(ArrayList<Integer> valeurs) {
		ArrayList<ArrayList<Integer>> couples = new ArrayList<ArrayList<Integer>>();
		for (int a = 0; a < valeurs.size()-1; a++) {
			for (int b = valeurs.size()-1; b > a; b--) {
				if (a!=b) couples.add(new ArrayList<>(Arrays.asList(valeurs.get(a), valeurs.get(b))));
			}
		}
		return couples;
	}

	/**
	 * effectue l'operation entre les deux entiers en parametre.
	 * @param a premier entier de l'operation.
	 * @param b second entier de l'operation.
	 * @param character contient l'operation.
	 * @return l'opération ou -1 si elle ne sert pas a l'algorithme (nombre negatif, calcul impossible, ...).
	 */
	private static int calculer(int a, int b, char operateur) {
		int calc=-1;
		switch (operateur) {
			case '+':
				calc=a+b;
				break;
			case '-':
				calc=Math.max(a-b, b-a);
				break;
			case '*':
				if(!(a==1||b==1)) calc=a*b;
				break;
			case '/':
				if(!(a==1||b==1)&&a>0&&b>0) {
					if (a%b==0) calc=a/b;
					else if (b%a==0) calc=b/a;
				}
				break;
			default:
				break;
		}
		return calc;
	}
	
	/**
	 * affiche les calculs du compte bon ou la valeur la plus proche obtenue.
	 * @param resultat resultat de l'execution de calculerCompteEstBon().
	 */
	private static void afficherResultat(boolean resultat) {
		if (resultat) System.out.println("Le compte est bon!! nbIt="+nbIterations);
		else System.out.println("Pas de solution exacte. \nLa valeur la plus proche est : "+nbProche);
		System.out.println("Calcul :");
		for (String calcul : calculsEffectues)
			System.out.println(calcul);
	}
}