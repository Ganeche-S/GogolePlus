package searchEngine;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		Scanner saisie = new Scanner(System.in);
		System.out.println("Que souhaitez-vous rechercher ?");
		String query = saisie.nextLine();
		String[]tabQuery = query.split(" ");
		
		Index.loadVocabulary();
		Index index = new Index();

		ArrayList<TreeMap<Integer, Double>> listeIdFrequences = new ArrayList<TreeMap<Integer, Double>>();
		for(int i = 0; i < tabQuery.length; i++) {
			if(i %2 == 0) { // Si i est pair alors tabQuery[i] est un mot
				Keyword k = index.getKeyword(tabQuery[i]);
				listeIdFrequences.add(k.getFrequences());
			}
		}
		
		
//		ArrayList<String> queryCleared;
//		try {
//			queryCleared = Index.creerListeMot(query);
//		
//			for(String s: queryCleared) {
//				System.out.println(s);
//			}
//		}
//		catch (FileNotFoundException e) {
//			e.getMessage();
//		}
		
		
		
	}

}
