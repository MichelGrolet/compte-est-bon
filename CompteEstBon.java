package CompteEstBon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CompteEstBon {
		private static boolean compteEstBon = false;
		private static final ArrayList<Character> OPERATIONS = new ArrayList<>(Arrays.asList('+', '*', '-', '/'));
		private static int nbAttendu;

		public static void main(String[] args) {
			ArrayList<Integer> nombres = new ArrayList<Integer>();
			for (int i = 0; i < 6; i++) {
				nombres.add(Integer.parseInt(args[i]));
			}
			nbAttendu = Integer.parseInt(args[6]);
			boolean resultat = calculerCompteEstBon(nombres);
			System.out.println(resultat);
		}

		public static boolean calculerCompteEstBon(ArrayList<Integer> valeurs) {
			compteEstBon = false;
			ArrayList<ArrayList<Integer>> couples = calculerCouples(valeurs);
			// Parcours des couples
			for (int iCouple = 0; iCouple < couples.size()-1 && !compteEstBon; iCouple++) {
				// Parcours des 4 operations
				for (int iOperation = 0; iOperation < 4 && !compteEstBon; iOperation++) {
					int a = couples.get(iCouple).get(0);
					int b = couples.get(iCouple).get(1);
					int resultat = calculer(a, b, OPERATIONS.get(iOperation));
					if (resultat>=0) {
						if (resultat==nbAttendu) {
							return true;
						} else {
							ArrayList<Integer> nouvValeurs = new ArrayList<>(valeurs);
							nouvValeurs.remove(nouvValeurs.indexOf(a));
							nouvValeurs.remove(nouvValeurs.indexOf(b));
							nouvValeurs.add(0, resultat);
							Collections.sort(nouvValeurs);
							// Appel recursif :
							compteEstBon = calculerCompteEstBon(nouvValeurs);
						}	
					}
				}
			}
			return compteEstBon;
		}

		/**
		 * effectue l'operation entre les deux entiers en parametre.
		 * @param a
		 * @param b
		 * @param character
		 * @return l'opération ou -1 si elle ne sert pas a l'algorithme (nombre negatif, calcul impossible, ...).
		 */
		private static int calculer(int a, int b, char operateur) {
			int calc=-1;
			switch (operateur) {
				case '+':
					calc=a+b;
					break;
				case '*':
					calc=a*b;
					break;
				case '-':
					calc=Math.max(a-b, b-a);
					break;
				case '/':
					if (b!=0) {
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
		 * retourne les couples qu'on peut faire a partir d'un ensemble de nombres.
		 * @param valeurs
		 * @return
		 */
		private static ArrayList<ArrayList<Integer>> calculerCouples(ArrayList<Integer> valeurs) {
			ArrayList<ArrayList<Integer>> couples = new ArrayList<ArrayList<Integer>>();
			for (int a = 0; a < valeurs.size()-1; a++) {
				for (int b = valeurs.size()-1; b > a; b++) {
					if (a!=b) couples.add(new ArrayList<>(Arrays.asList(a, b)));
				}
			}
			return couples;
		}
}}