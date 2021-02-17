package searchEngine;

import java.io.*;
import java.util.TreeMap;



public class Index {

   static final String pathDocs= "documents.data";;
   static final String pathVoc = "vocabulary.data";

   static TreeMap<String,Keyword> keywords;
   static TreeMap<Integer,Document> documents;






    public Index(){
        keywords = new TreeMap<String,Keyword>();
        documents = new TreeMap<Integer,Document>();
    }




    public Keyword getKeyword(String key){

        return keywords.get(key);
    }

    public void addKeyword(String key, Keyword keyword){

        keywords.put(key, keyword);
    }





    public Document getDocument(Integer id){

        return documents.get(id);
    }

    public void addDocument(Integer id, Document doc){

        documents.put(id, doc);
    }





	public static void saveVocabulary() {
		try {
			File fileTemp = new File(pathVoc);
			if (fileTemp.exists()) {
				fileTemp.delete();
			}
			// Write to disk with FileOutputStream
			FileOutputStream f_out = new FileOutputStream(pathVoc);

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

			// Write object out to disk
			obj_out.writeObject(keywords);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static TreeMap<String, Keyword> loadVocabulary() {
		try {
			System.out.println("Loading keywords...");
			long start = System.nanoTime();
			InputStream is = Index.class.getClassLoader().getResourceAsStream(pathVoc);
			if (is == null)
				throw new IOException(pathVoc + " doesn't exists");

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(is);

			// Read an object
			keywords = (TreeMap<String, Keyword>) obj_in.readObject();
			long end = System.nanoTime();
			System.out.println("Loading finished. Loaded " + keywords.values().size() + " keywords in " + Math.round((end - start) / 1_000_000d) / 1_000d + " s");
			return keywords;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static void saveDocuments() {
		try {
			File fileTemp = new File(pathDocs);
			if (fileTemp.exists()) {
				fileTemp.delete();
			}
			// Write to disk with FileOutputStream
			FileOutputStream f_out = new FileOutputStream(pathDocs);

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

			// Write object out to disk
			obj_out.writeObject(documents);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static TreeMap<Integer, Document> loadDocuments() {
		try {
			// Read from disk using FileInputStream
			System.out.println("Loading Documents...");
			long start = System.nanoTime();
			InputStream is = Index.class.getClassLoader().getResourceAsStream(pathDocs);
			if (is == null)
				throw new IOException(pathDocs + " doesn't exists");

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream(is);

			// Read an object
			documents = (TreeMap<Integer, Document>) obj_in.readObject();
			long end = System.nanoTime();
			System.out.println("Loading finished. Loaded " + documents.values().size() + " documents in " + Math.round((end - start) / 1_000_000d) / 1_000d + " s");
			return documents;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}



}
