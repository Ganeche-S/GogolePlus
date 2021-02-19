package searchEngine;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	

	public static void main(String[] args) {
		//booleanEngine();
		vectorialEngine();
		
	}
	
	public static void vectorialEngine() {
		Scanner saisie = new Scanner(System.in);
		System.out.println("Que souhaitez-vous rechercher ?");
		String query = saisie.nextLine();
		String[]tabQuery = query.split(" ");
		
		//initializing the list
		
		ArrayList<String>listQuery = new ArrayList<String>();
		for(String s : tabQuery) {
			listQuery.add(s);
		}
		
		//removing all stopWords
		
		clearQuery(listQuery);
		
		
		Index index = new Index();
		index.loadVocabulary();
		index.loadDocuments();
		
		HashMap<Integer,Double> resultat = new HashMap<Integer,Double>();
        Keyword k1 = index.getKeyword(listQuery.get(0));
        System.out.println("taille de résultat = " + resultat.size());

        for(int i = 0; i < listQuery.size(); i++) {
//            k1 = index.getKeyword(listQuery.get(i));
//            TreeMap<Integer,Double> freq1;
//            freq1 = k1.getFrequences();
        	
                
            if(i == 0 && (k1 != null)) {
                TreeMap<Integer,Double> freq1 = k1.getFrequences();
            	for(Integer id : freq1.keySet()) {
                    resultat.put(id, freq1.get(id));
                }
            }
                
            Keyword k2;
            TreeMap<Integer,Double> freq2;
            
            k2 = index.getKeyword(listQuery.get(i));
            
           if(k2 != null) {

               freq2 = k2.getFrequences();
               
               System.out.println("taille freq2 = " + freq2.size());
               HashMap<Integer,Double> resultatTmp = new HashMap<Integer,Double>();
               
               for(Integer idDoc : freq2.keySet()) {
               	resultat.put(idDoc, freq2.get(idDoc));
               }
                  
                     
               //resultat = resultatTmp;
               System.out.println("nouvelle taille resultat = " + resultat.size());
           }
           else {
        	   System.out.println("mot introuvable");
           }
            
            

        }
       
            	
            
        
	}
	
	
	
	// ------------------------------------------------------------------------------------------------------------
	
	
	
	public static void booleanEngine() {
		Scanner saisie = new Scanner(System.in);
		System.out.println("Que souhaitez-vous rechercher ?");
		String query = saisie.nextLine();
		String[]tabQuery = query.split(" ");
		ArrayList<String>listQuery = new ArrayList<String>();
		for(String s : tabQuery) {
			listQuery.add(s);
		}
		
		Index index = new Index();
		index.loadVocabulary();
		index.loadDocuments();

		//ArrayList<TreeMap<Integer, Double>> listeIdFrequences = new ArrayList<TreeMap<Integer, Double>>();
        //ArrayList<String> listeOperateurs = new ArrayList<String>();
        HashMap<Integer,Double> resultat = new HashMap<Integer,Double>();
        Keyword k1 = null;
        for(int i = 0; i < listQuery.size(); i++) {
            if(i %2 == 0) { // Si i est pair alors tabQuery[i] est un mot
                k1 = index.getKeyword(listQuery.get(i));
            	TreeMap<Integer,Double> freq1;
                freq1 = k1.getFrequences();
                
                if(i == 0) {
                	for(Integer id : freq1.keySet()) {
                        resultat.put(id, freq1.get(id));
                    }
                }

//                if(k1 != null) {
//                    //listeIdFrequences.add(k.getFrequences());
//                }
            }
            else {
            	Keyword k2;
                TreeMap<Integer,Double> freq2;
                k2 = index.getKeyword(listQuery.get(i+1));
                
               
                
                
                freq2 = k2.getFrequences();
                
                System.out.println(resultat.size());
                System.out.println(freq2.size());
                HashMap<Integer,Double> resultatTmp = new HashMap<Integer,Double>();

                 switch(listQuery.get(i)) {
                 	
	                 case "and":
	                    //traitement
	                   System.out.println("and");
	                    //for(Integer idDoc : freq1.keySet()) {
	                    for(Integer idDoc : resultat.keySet()) {

	                        if (freq2.containsKey(idDoc)){
	                        	if (resultat.get(idDoc)>freq2.get(idDoc)){
	                        		resultatTmp.put(idDoc,freq2.get(idDoc));
	                            }
	                        	else{
	                        		resultatTmp.put(idDoc,resultat.get(idDoc));
	                            }
	
	                        }
	                        
	                    }
	                    resultat = resultatTmp;
	                    break;
	                    
	                 case "or":
		                   System.out.println("or");

	                	 
		                 //for(Integer idDoc : freq1.keySet()) {
		                   for(Integer idDoc : resultat.keySet()) {
		                     if (freq2.containsKey(idDoc)){
		                    	 if (resultat.get(idDoc)<freq2.get(idDoc)){
		                    		 resultatTmp.put(idDoc,freq2.get(idDoc));
		                         }
		                         else{
		                        	resultatTmp.put(idDoc,resultat.get(idDoc));
		                         }
		
		                     }
		                     else resultatTmp.put(idDoc, resultat.get(idDoc));
		                    }
		                   
		                   for(Integer idDoc : freq2.keySet()) {
			                     if (!resultat.containsKey(idDoc)){
			                    	 resultatTmp.put(idDoc, freq2.get(idDoc));
			                     }
		                   }
			                   
		                    resultat = resultatTmp;
		                    break;
		                    
	                 case "not":
		                   System.out.println("not");

	                	 //for(Integer idDoc : freq1.keySet()) {
		                 for(Integer idDoc : resultat.keySet()) {

	                		 if (!freq2.containsKey(idDoc)){
	                			 resultatTmp.put(idDoc, resultat.get(idDoc));
	                		 }
	                	 }
		                 resultat = resultatTmp;
		                 break;
	       
                  }

            }
        }
        System.out.println(resultat.size());
        for(Integer id : resultat.keySet()) {
        	Document d = index.getDocument(id);
        	System.out.println(d.getTitle());
        }
        saisie.close();
	}
	
	
	public static void clearQuery(ArrayList<String> listQuery) {
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("stopwords.txt"));
			String ligneLue = reader.readLine();
			while (ligneLue != null) {
				for(int indice = 0; indice < listQuery.size(); indice++) {
					
					if(listQuery.get(indice).equals(ligneLue)) {
						listQuery.remove(indice);
					}
				}
				
				ligneLue = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
