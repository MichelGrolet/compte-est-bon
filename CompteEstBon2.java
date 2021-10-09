package CompteEstBon;

import java.util.ArrayList;
import java.util.Arrays;

public class CompteEstBon2 {
		public static void main(String[] args) {
			ArrayList<Integer> nombres = new ArrayList<Integer>();
			for (int i = 0; i < 6; i++) {
				nombres.add(Integer.parseInt(args[i]));
			}
			int nbAttendu = Integer.parseInt(args[6]);
			boolean resultat = calculerCompteEstBon(valeurs, nbAttendu);
		}

		public static void calculerCompteEstBon(ArrayList<Integer> valeurs, int nbAttendu) {
			ArrayList<ArrayList<Integer>> couples = calculerCouples(valeurs);

			
		}

		private static ArrayList<ArrayList<Integer>> calculerCouples(ArrayList<Integer> valeurs) {
			ArrayList<ArrayList<Integer>> couples = new ArrayList<ArrayList<Integer>>();

			for (int a = 0; a < valeurs.size()-1; a++) {
				for (int b = valeurs.size()-1; b > a; b++) {
					if (a!=b) couples.add(new ArrayList<>(Arrays.asList(a)))
				}
			}
		}
}