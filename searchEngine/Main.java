package searchEngine;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
	

	public static void main(String[] args) {
		//booleanEngine();
		vectorialEngine(); 
		//probabilisticEngine();
	}
	

	
	public static void probabilisticEngine() {
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
        System.out.println("taille de résultat = " + resultat.size());

        for(int i = 0; i < listQuery.size(); i++) {
                
            Keyword k;
            TreeMap<Integer,Double> freq;
            
            k = index.getKeyword(listQuery.get(i));
            
           if(k != null) {

               freq = k.getFrequences();
               
               System.out.println("taille freq = " + freq.size());
              // HashMap<Integer,Double> resultatTmp = new HashMap<Integer,Double>();
               
               for(Integer idDoc : freq.keySet()) {
               	resultat.put(idDoc, freq.get(idDoc));
               }
                  
                     
               //resultat = resultatTmp;
               System.out.println("nouvelle taille resultat = " + resultat.size()+ "\n");
           }
           else {
        	   System.out.println("mot introuvable");
           }
            
            

       }

        ajoutParPertinence(index, resultat, listQuery);
        vectorialEngine(listQuery);	//penser à retirer le commentaire après les tests
        
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
        System.out.println("taille de résultat = " + resultat.size());

        for(int i = 0; i < listQuery.size(); i++) {
                
            Keyword k;
            TreeMap<Integer,Double> freq;
            
            k = index.getKeyword(listQuery.get(i));
            
           if(k != null) {

               freq = k.getFrequences();
               
               System.out.println("taille freq = " + freq.size());
              // HashMap<Integer,Double> resultatTmp = new HashMap<Integer,Double>();
               
               for(Integer idDoc : freq.keySet()) {
               	resultat.put(idDoc, freq.get(idDoc));
               }
                  
                     
               //resultat = resultatTmp;
               System.out.println("nouvelle taille resultat = " + resultat.size()+ "\n");
           }
           else {
        	   System.out.println("mot introuvable");
           }
            
            

       }
       //code calcul ici
        TreeMap<Integer,Double> associationDocumentsPoids = calculPoidsClassementVectoriel(resultat, index, listQuery);
        for(Integer id : associationDocumentsPoids.keySet()) {
        	Document d = index.getDocument(id);
        	//System.out.println(id + "   " + associationDocumentsPoids.get(id));
        }
        
            	
         saisie.close();      
	}
	
	//------------------------------------------------------------------------------------------------------------------------------------------------
	
	public static void vectorialEngine(ArrayList<String>listQuery) {
	
		
		clearQuery(listQuery);
		
		
		Index index = new Index();
		index.loadVocabulary();
		index.loadDocuments();
		
		HashMap<Integer,Double> resultat = new HashMap<Integer,Double>();
        System.out.println("taille de résultat = " + resultat.size());

        for(int i = 0; i < listQuery.size(); i++) {
                
            Keyword k;
            TreeMap<Integer,Double> freq;
            
            k = index.getKeyword(listQuery.get(i));
            
           if(k != null) {

               freq = k.getFrequences();
               
               System.out.println("taille freq = " + freq.size());
              // HashMap<Integer,Double> resultatTmp = new HashMap<Integer,Double>();
               
               for(Integer idDoc : freq.keySet()) {
               	resultat.put(idDoc, freq.get(idDoc));
               }
                  
                     
               //resultat = resultatTmp;
               System.out.println("nouvelle taille resultat = " + resultat.size()+ "\n");
           }
           else {
        	   System.out.println("mot introuvable");
           }
            
            

       }
       //code calcul ici
        TreeMap<Integer,Double> associationDocumentsPoids = calculPoidsClassementVectoriel(resultat, index, listQuery);
        for(Integer id : associationDocumentsPoids.keySet()) {
        	Document d = index.getDocument(id);
        	//System.out.println(id + "   " + associationDocumentsPoids.get(id));
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
	
	public static TreeMap<Integer,Double> calculPoidsClassementVectoriel(HashMap<Integer,Double> resultat, Index index, ArrayList<String>listQuery) {
		TreeMap<Integer,Double> associationDocumentsPoids = new TreeMap<Integer,Double>();
		
		for(Integer idDoc : resultat.keySet()) {
			Document doc = index.getDocument(idDoc);
			TreeMap<String,Double> associationMotsFreq = doc.getFrequences();
			
			double num = 0, denoGauche = 0, denoDroit = 0;

			for(String mot : associationMotsFreq.keySet()) {
				if(listQuery.contains(mot)) {
					double freqMotRequete = (double)(Collections.frequency(listQuery, mot)/(double)listQuery.size());
					
					//debut calcul
					
					num += freqMotRequete *  associationMotsFreq.get(mot);
					denoGauche += Math.pow(associationMotsFreq.get(mot), 2);
					denoDroit += Math.pow(freqMotRequete, 2);
					
					//System.out.println(freqMotRequete + " " + associationMotsFreq.get(mot));
					//System.out.println("\n\n" + denoGauche + " " + denoDroit+ " " + num);


				}
			}
			
			double ValClassement = num/(Math.sqrt(denoGauche) * Math.sqrt(denoDroit));
			System.out.println(ValClassement);
			//System.out.println(Math.sqrt(denoGauche));
			//System.out.println(Math.sqrt(denoDroit));
			associationDocumentsPoids.put(idDoc, ValClassement);
			
			//break;
		}
		return associationDocumentsPoids;
	}
	
	public static void ajoutParPertinence(Index index, HashMap<Integer,Double> resultat, ArrayList<String>listQuery) {
		double N = index.documents.size();
		double R = resultat.size();
		
//		System.out.println("taille de N = " + N);
//		System.out.println("taille de R = " + R);
		
		
		HashSet<String> mots = new HashSet<String>();
		HashMap<String, Double> associationMotPertinance = new HashMap<String, Double>();
		
		for(Integer idDoc : resultat.keySet()) {
			Document doc = index.getDocument(idDoc);
			
			
			for(String mot : doc.getFrequences().keySet()) {
				if(!mots.contains(mot)) mots.add(mot);
				
			}
		}
		
		for(String mot : mots) {
			if(!listQuery.contains(mot)) {
				TreeMap<Integer, Double> associationIdFreq = index.getKeyword(mot).getFrequences();
 				double n = (double)associationIdFreq.keySet().size();
				double r = 0;
				
				
				
				for(Integer id : associationIdFreq.keySet()) {
					if(resultat.containsKey(id)) {
						r++;
					}
				}
//				System.out.println(N);
//				System.out.println(R);
				

				double valPertinence = Math.log10((r/(R-r))/((n-r)/(N-n-R+r))) * Math.abs((r-R)-(n-r)/(N-R));
				
				
//				if(valPertinence >= seuil) {
//					System.out.println("Nouveau mot ajouté: " + mot);
//					System.out.println("Valeur de N = " + N);
//					System.out.println("Valeur de n = " + n);
//					System.out.println("Valeur de R = " + R);
//					System.out.println("Valeur de r = " + r);
//					System.out.println("Valeur de pertinence = " + valPertinence + "\n\n");
//					listQuery.add(mot);
//				}
				
//					if(nouveauMot == null) {
//						nouveauMot = mot;
//						pertinanceNouveauMot = valPertinence;
//					}
//					else {
//						if(valPertinence > pertinanceNouveauMot) {
//							nouveauMot = mot;
//							pertinanceNouveauMot = valPertinence;
//						}
//					}
				if(valPertinence != Double.POSITIVE_INFINITY) {
					associationMotPertinance.put(mot, valPertinence);
					System.out.println("mot = " + mot + " val = " + valPertinence);
				}
				
			}
			
				}
				
//			HashMap<String, Double> mapTriee = sortHashMapByValues(associationMotPertinance);
//			String nouveauMot = (String) mapTriee.keySet().toArray()[0];

				Entry<String, Double> maxEntry = null;

				for (Entry<String, Double> entry : associationMotPertinance.entrySet()) {
				
				    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				    
				        maxEntry = entry;
				    }
				}
				System.out.println(maxEntry.getKey());
				listQuery.add(maxEntry.getKey());
		

	}
	
	public static HashMap<String, Double> sortHashMapByValues(HashMap<String, Double> passedMap) {
		
	    List<String> mapKeys = new ArrayList<>(passedMap.keySet());
	    List<Double> mapValues = new ArrayList<>(passedMap.values());
	    Collections.sort(mapValues);
	    Collections.sort(mapKeys);

	    HashMap<String, Double> sortedMap = new HashMap<>();

	    Iterator<Double> valueIt = mapValues.iterator();
	    while (valueIt.hasNext()) {
	        double val = valueIt.next();
	        Iterator<String> keyIt = mapKeys.iterator();

	        while (keyIt.hasNext()) {
	            String key = keyIt.next();
	            double comp1 = passedMap.get(key);
	            double comp2 = val;

	            if (comp1 == comp2) {
	                keyIt.remove();
	                sortedMap.put(key, val);
	                break;
	            }
	        }
	    }
	    return sortedMap;
	}


	
}
