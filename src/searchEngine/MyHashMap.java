package searchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MyHashMap {
	/*Method to scan the input folder and traverse the files to store in hashTables*/
	public void scanFolder(Hashtable<String, Hashtable<String, List<Integer>>> hashTables, final File folder)
	{
		for (final File fileName : folder.listFiles()) {	
			if (fileName.isFile()) {
				Hashtable<String, List<Integer>> hs = new Hashtable<String, List<Integer>>();
				String inputfile = Config.TEXT_FILES_PATH + "/" + fileName.getName();
				insertFileInHashTable(inputfile, hs);
				hashTables.put(inputfile, hs);
			}
		}
	}
	
	public void insertFileInHashTable(String inputfile, Hashtable<String, List<Integer>> hashtable) {
		String[] wordsFromFile = readFile(inputfile);
		for (int i=0; i < wordsFromFile.length; i++) {
			addWordToHashTable(wordsFromFile[i], i, hashtable);
		}
	}

	public void addWordToHashTable(String word, int index, Hashtable<String, List<Integer>> hashtable) {
		List<Integer> indexList;
		if (hashtable.containsKey(word)) {
			indexList = hashtable.get(word);
		} else {
			indexList = new ArrayList<Integer>();
		}
		indexList.add(index);
		hashtable.put(word, indexList);
	}
	
	/*
	 * statics method to split the query into list of Strings using String class
	 * methods returns the list of Strings
	 */
	public static String[] splitStringInWordList(String query) {
		query = query.toLowerCase();
		query = query.replaceAll("[^a-zA-Z0-9 ]", " ");
		query = query.replaceAll("\\s+", " ").replaceAll("^\\s+", "");
		String[] queryList = query.split(" ");
		return queryList;
	}

	private String[] readFile(String inputfile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(inputfile));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());				
				line = br.readLine();
			}
			br.close();
			String fileContents = sb.toString();
			String[] words = splitStringInWordList(fileContents);
			br.close();
			return words;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public float searchWordsInsideHashTable(Hashtable<String, List<Integer>> hashtable, String[] queryList) {
		float pagerank = 0.0f;
		List<List<Integer>> results = new ArrayList<List<Integer>>();
		for (String query : queryList) {
			query = query.toLowerCase();
			if (hashtable.containsKey(query)) {
				List<Integer> indexes = hashtable.get(query);
				results.add(indexes);
			} else {
				results.add(new ArrayList<Integer>());
			}
		}
		PageRank rank = new PageRank();
		pagerank = rank.pageRank(results);
		return pagerank;
	}
}
