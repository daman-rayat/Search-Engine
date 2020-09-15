package searchEngine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import searchEngine.Config;;
public class MySpellChecker {

	public static int editDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];
	}

	public static void checkSpelling(String word) throws IOException {
		int wLength = word.length(), count = 0, distance;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(Config.DICTIONARY));
			String file;
			System.out.println("\nWord Suggestions for " + word + ":");
			while ((file = br.readLine()) != null) {
				distance = editDistance(word, file);
				if ((wLength == file.length()) && (distance == 1)) {
					System.out.println(file + " ");
					count++;
				}
				{

				}
			}

		} finally {
			br.close();
		}
	}

	public static void spellCheck(String[] inputArray) throws IOException {
		for (String word : inputArray) {
			checkSpelling(word);
		}
	}

}
