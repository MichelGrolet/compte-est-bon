import java.util.*;

/**
 * Solveur du "compte est bon".
 * @author Michel Grolet & Antoine Chevaleyre
 */
public class CompteEstBon {
	private static boolean compteEstBon = false;
	private static final char[] OPERATIONS = new char[]{'+', '-', '*', '/'};
	private static final ArrayList<Integer> NOMBRES_AUTORISES = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,25,50,75,100));

	private static ArrayList<String> calculsEffectues = new ArrayList<String>();
	private static String[] calculsMoinsBon = new String[5];
	private static String[] calculsMoinsBonCour = new String[5];
	private static int nbAttendu, nbProche = 0, nbIterations = 0;

	/**
	 * Lance l'algorithme puis affiche le resultat.
	 * @param args 
	 * @throws NumberFormatException
	 * @throws Exception si les arguments ne sont pas valides (cf regles du Compte est bon).
	 */
	public static void main(String[] args) throws NumberFormatException, Exception {
		boolean resultat = calculerCompteEstBon(genererNombres(args));
		afficherResultat(resultat);
	}

	/**
	 * Recupere les valeurs de args si elles sont conformes au Compte est bon.
	 * @param args tableau des arguments passes a ComteEstBon.
	 * @return liste des 6 valeurs.
	 * @throws Exception si les arguments ne sont pas valides (cf regles du Compte est bon).
	 */
	private static ArrayList<Integer> genererNombres(String[] args) throws Exception {
		if (args.length != 7) throw new Exception("CompteEstBon prends 7 parametres.");
		ArrayList<Integer> valeurs = new ArrayList<Integer>(6);
		for (int i=0; i<6; i++) {
			if (!NOMBRES_AUTORISES.contains(Integer.parseInt(args[i]))) {
				throw new Exception("Les 6 valeurs doivent etre comprises dans la liste : 1,2,3,4,5,6,7,8,9,10,25,50,75,100");
			}
			valeurs.add(Integer.parseInt(args[i]));
		}
		nbAttendu = Integer.parseInt(args[6]);
		if (nbAttendu<101 || nbAttendu>999) throw new Exception("Le resultat attendu doit etre compris entre 101 et 999.");
		return valeurs;
	}

	/**
	 * resoud Le compte est bon.
	 * @param valeurs nombres parmi lesquels on fait les operations.
	 * @return true si le compte est bon.
	 */
	public static boolean calculerCompteEstBon(ArrayList<Integer> valeurs) {
		nbIterations++;
		// Copie de la liste des valeurs qu'on va trier
		Collections.sort(valeurs, Collections.reverseOrder());
		
		// Liste des couples qu'on peut generer avec les valeurs
		ArrayList<ArrayList<Integer>> couples = calculerCouples(valeurs);

		// Parcours des couples
		for (int iCouple = 0; iCouple < couples.size() && !compteEstBon; iCouple++) {
			// Parcours des 4 operations
			for (int iOperation = 0; iOperation < 4 && !compteEstBon; iOperation++) {
				int a = couples.get(iCouple).get(0);
				int b = couples.get(iCouple).get(1);
				int resultat = calculer(a, b, OPERATIONS[iOperation]);
				// calculer() retourne -1 si le calcul n'est pas necessaire.
				if (resultat>0) {
					String calcul = a+""+OPERATIONS[iOperation]+""+b+" = "+resultat;
					calculsMoinsBonCour[6-valeurs.size()] = calcul;
					// Modifie le nbProche si resultat est plus proche du resultat attendu.
					if (Math.abs(resultat-nbAttendu)<Math.abs(nbProche-nbAttendu)) {
						nbProche = resultat;
						calculsMoinsBon = new String[5];
						calculsMoinsBon = calculsMoinsBonCour.clone();
					}
					if (resultat==nbAttendu) {
						calculsEffectues.add(0, calcul);
						return true;
					} else if (valeurs.size()>2) {
						ArrayList<Integer> nouvValeurs = new ArrayList<>(valeurs);
						nouvValeurs.remove(nouvValeurs.indexOf(a));
						nouvValeurs.remove(nouvValeurs.indexOf(b));
						nouvValeurs.add(resultat);
						compteEstBon = calculerCompteEstBon(nouvValeurs);
						if (compteEstBon) calculsEffectues.add(0, calcul);
					}
				}
			}
		}
		return compteEstBon;
	}

	/**
	 * retourne les couples qu'on peut faire a partir d'un ensemble de nombres.
	 * @param valeurs entiers parmi lesquels on cree les couples.
	 * @return liste contenant le nombre minimal de couples. La taille de cette liste est de ((2 parmi (le nombre de valeurs))-(cas ou a ou b sont nuls)).
	 */
	private static ArrayList<ArrayList<Integer>> calculerCouples(ArrayList<Integer> valeurs) {
		ArrayList<ArrayList<Integer>> couples = new ArrayList<ArrayList<Integer>>();
		for (int a = 0; a < valeurs.size()-1; a++) {
			for (int b = valeurs.size()-1; b > a; b--) {
				if (a!=b || a!=0 || b!=0) couples.add(new ArrayList<>(Arrays.asList(valeurs.get(a), valeurs.get(b))));
			}
		}
		return couples;
	}

	/**
	 * effectue l'operation entre les deux entiers en parametre.
	 * @param a premier entier de l'operation.
	 * @param b second entier de l'operation.
	 * @param character contient l'operation.
	 * @return l'opération ou -1 si elle ne sert pas a l'algorithme (nombre negatif ou nul, calcul impossible, ...).
	 */
	private static int calculer(int a, int b, char operateur) {
		int calc=-1;
		switch (operateur) {
			case '+':
				calc=a+b;
				break;
			case '-':
				// on fait a-b car comme la liste est triée, a>b
				calc=a-b;
				break;
			case '*':
				if(a!=0 && b!=0 && a!=1 && a!=1) calc=a*b;
				break;
			case '/':
				if(a!=0 && b!=0 && a!=1 && a!=1 && a%b==0 && a!=b) calc=a/b;
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
		if (resultat) {
			System.out.println("Le compte est bon!! nbIt="+nbIterations);
			for (String calcul : calculsEffectues)
				System.out.println(calcul);
		}
		else {
			System.out.println("Pas de solution exacte. \nLa valeur la plus proche est : "+nbProche+". nbIt="+nbIterations);
			System.out.println("Calcul :");
			for (String calcul : calculsMoinsBon)
				System.out.println(calcul);
		}
	}
}