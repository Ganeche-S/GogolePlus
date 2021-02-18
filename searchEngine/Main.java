package searchEngine;

import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	

	public static void main(String[] args) {
		Scanner saisie = new Scanner(System.in);
		System.out.println("Que souhaitez-vous rechercher ?");
		String query = saisie.nextLine();
		String[]tabQuery = query.split(" ");
		
		Index index = new Index();
		index.loadVocabulary();

		//ArrayList<TreeMap<Integer, Double>> listeIdFrequences = new ArrayList<TreeMap<Integer, Double>>();
        //ArrayList<String> listeOperateurs = new ArrayList<String>();
        HashMap<Integer,Double> resultat = new HashMap<Integer,Double>();
        Keyword k1 = null;
        for(int i = 0; i < tabQuery.length; i++) {
            if(i %2 == 0) { // Si i est pair alors tabQuery[i] est un mot
                k1 = index.getKeyword(tabQuery[i]);
//                if(k1 != null) {
//                    //listeIdFrequences.add(k.getFrequences());
//                }
            }
            else {
            	Keyword k2;
            	TreeMap<Integer,Double> freq1;
                TreeMap<Integer,Double> freq2;
                k2 = index.getKeyword(tabQuery[i+1]);
                freq1 = k1.getFrequences();
                freq2 = k2.getFrequences();
                 switch(tabQuery[i]) {
                 	
	                 case "and":
	                    //traitement
	                   
	                    for(Integer idDoc : freq1.keySet()) {
	                        if (freq2.containsKey(idDoc)){
	                        	if (freq1.get(idDoc)>freq2.get(idDoc)){
	                        		resultat.put(idDoc,freq2.get(idDoc));
	                            }
	                        	else{
	                        		resultat.put(idDoc,freq1.get(idDoc));
	                            }
	
	                        }
	                    }
	                    break;
	                    
	                 case "or":
	                	 
		                 for(Integer idDoc : freq1.keySet()) {
		                     if (freq2.containsKey(idDoc)){
		                    	 if (freq1.get(idDoc)<freq2.get(idDoc)){
		                    		 resultat.put(idDoc,freq2.get(idDoc));
		                         }
		                         else{
		                        	resultat.put(idDoc,freq1.get(idDoc));
		                         }
		
		                        }
		                    }
		                    break;
		                    
	                 case "not":
	                	 for(Integer idDoc : freq1.keySet()) {
	                		 if (!freq2.containsKey(idDoc)){
	                			 resultat.put(idDoc, freq1.get(idDoc));
	                		 }
	                	 }
	                	 
                 
                  }
                 
		
		
            }
        }
        saisie.close();
	}
}
