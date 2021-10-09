package CompteEstBon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompteEstBonOld {

	private static List<String> listeOpe;
	private static List<Character> ope;
	private static boolean compteEstBon = false;
	private static int numIteration = 1;

	public static void main(String[] args) {
		// liste contenant l'historique des opérations effectuées.
		listeOpe = new ArrayList<String>();
		// ajout des 4 opérations possibles dans une liste :
		ope = new ArrayList<Character>();
		ope.add('+');
		ope.add('*');
		ope.add('-');
		ope.add('/');
		// liste contenant les nombres
		ArrayList<Integer> tab = new ArrayList<Integer>();
		for (int i = 0; i < 6; i++) {
			tab.add(Integer.parseInt(args[i]));
		}
		// int tr = essayerCompteEstBon(tab, Integer.parseInt(args[6]));
		boolean tr = essayerCompteEstBon(tab, Integer.parseInt(args[6]));
		System.out.println("-----------|"+tr+"|----------");
		System.out.println(afficherRes(compteEstBon));
	}

	/**
	 * LE COMPTE EST BON
	 * @param valeurs liste des nombres
	 * @param resultat resultat attendu
	 * @return le bon compte ou le plus proche
	 */
	public static boolean essayerCompteEstBon(ArrayList<Integer> valeurs, int resAttendu) {
		System.out.println("################# iteration "+numIteration+" ##################");
		numIteration++;
		compteEstBon=false;
		// Creation des paires de valeurs
		ArrayList<Integer[]> couples = new ArrayList<Integer[]>();
		for (int i = 0; i < valeurs.size()-1; i++) {
			for (int j = valeurs.size()-1; j > i; j--) {
				if (i!=j) {
					couples.add(new Integer[]{valeurs.get(i),valeurs.get(j)});
				}
			}
		}
		System.out.println("\t- "+valeurs.size()+" nombres"+valeurs.toString()+" - "+couples.size()+" couples");

		//couple courant dans la liste des couples.
		int iCoupleCour = 0;
		//opération courante dans la liste des opérations
		int iOperationCour = 0;
		//contient le résultat d'une itération du while
		int res;

		// On parcours le tableau des couples et pour chacun on teste chaque opération possible.
		// Tant qu'il reste des couples à parcourir et qu'on a pas trouvé la solution :
		while (iCoupleCour<couples.size() && !compteEstBon && couples.size()>=1) {
			int a = couples.get(iCoupleCour)[0];
			int b = couples.get(iCoupleCour)[1];
			res = calculer(a, b, ope.get(iOperationCour));
			String chaineOpe = a+" "+ope.get(iOperationCour)+" "+b+" = "+res;
			System.out.println("\t"+"calcul : "+chaineOpe);
			// Si le calcul est faisable (calculer() retourne -1 si ce n'est pas le cas) :
			if (res!=-1 && couples.size()>2) {
				// On appelle compte() récursivement en enlevant a et b et en ajoutant res.
				ArrayList<Integer> nouvValeurs = new ArrayList<>(valeurs);
				nouvValeurs.remove(nouvValeurs.indexOf(a));
				nouvValeurs.remove(nouvValeurs.indexOf(b));
				nouvValeurs.add(0, res);
				Collections.sort(nouvValeurs);
	// APPEL RECURSIF :
				compteEstBon = essayerCompteEstBon(nouvValeurs, resAttendu);
			}
			if (compteEstBon) {
				listeOpe.add(chaineOpe);
				System.out.println("Le compte est bon !! ("+res+")");
			} else {
				System.out.println("\t\t"+"mauvaise op : "+chaineOpe);
				// On passe à l'opération suivante. Si on est à la dernière opération, on passe au couple suivant.
				if (iOperationCour<3) {
					iOperationCour++;
				} else  {
					if (iCoupleCour==couples.size()-1) {
						return false;
					} else {
						iOperationCour = 0;
						iCoupleCour++;
						System.out.println("\t\t"+"passage au couple "+iCoupleCour);
					}
				}
			}
		}
		return compteEstBon;
	}

	/**
	 * calcule l'operation op entre a et b
	 * @param a premier entier 
	 * @param b deuxieme entier
	 * @param op opération
	 * @return l'operation op entre a et b
	 */
	public static int calculer(int a, int b, char op) {
		int calc=-1;
		switch (op) {
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

	public static String afficherRes(boolean CEB) {
		String res="";
		System.out.println("Nombre d'operations : "+(listeOpe.size()));
		for (int i = listeOpe.size()-1; i >= 0; i--) {
			res+=(listeOpe.get(i)+"\n");
		}
		return res;
	}
}
