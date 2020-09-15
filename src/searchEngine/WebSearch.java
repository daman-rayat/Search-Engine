package searchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class WebSearch {

	private static Hashtable<String, Hashtable<String, List<Integer>>> hashTables;
	private static MyHashMap hashMap;

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

	private static boolean search(String[] query) throws IOException {
		boolean response=false;
		List<Result> results = new ArrayList<Result>();
		Enumeration<String> names = hashTables.keys();
		while (names.hasMoreElements()) {
			String fileName = (String) names.nextElement();
			float pagerank = hashMap.searchWordsInsideHashTable(hashTables.get(fileName), query);

			if (pagerank > 0) {
				Result result = new Result(fileName, pagerank);
				results.add(result);
			}
		}

		int numResults = results.size();
		System.out.println("\n\nMatching files are : " + numResults);

		if (numResults > 0) {
			Collections.sort(results, new Comparator<Result>() {
				public int compare(Result a, Result b) {
					return (int) (b.pageRank - a.pageRank);
				}
			});

			// sort the results
			for (int i = 0; i < Math.min(numResults, Config.NUM_RESULTS_TO_DISPLAY); i++) {
				System.out.println((i + 1) + ": PageRank: " + results.get(i).frequency + ", filename: "
						+ results.get(i).fileName.replace(Config.TEXT_FILES_PATH + "/", ""));
			}
			response=true;
		}
		return response;
	}
	// Prints the search query array of strings
	public static void printArray(String[] input, String message) {
		System.out.print(message);
		for (int i = 0; i < input.length; i++) {
			System.out.print("[" + i + "]:" + input[i] + " ");
		}
	}

	/*
	 * Static method to search for input string in the text files checks for
	 * spelling suggestions if any using Edit distance prints the occurrence of the
	 * string along with page ranks
	 */
	public static void webSearch() throws IOException {
		
		// Accessing the input folder for text files
		File inputDir = new File(Config.TEXT_FILES_PATH);		
		hashTables = new Hashtable<>();
		hashMap = new MyHashMap();		
		// search String initialized
		String query = "";
		try {
			System.out.print("\nPlease input to search \n");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			query = br.readLine();
			
			// splitting the search query to get list of words to search
			String[] queryList = splitStringInWordList(query);
			printArray(queryList, "Query: ");
			
			// scanning the input folder and inserting the data into hashtables
			hashMap.scanFolder(hashTables, inputDir);
			// calling the spellChecker to give suggestions if any
			MySpellChecker.spellCheck(queryList);
			// Searching for occurrence of input string in the source text files
			boolean result = search(queryList);
			if(!result) {
				System.out.println("Sorry the query has not been found!");
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

}
