package searchEngine;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FindPattern {

	public static void findPattern() throws IOException {
		String phonePattern = "(\\()?(\\d){3}(\\))?[- ](\\d){3}-(\\d){4}";
		String emailPattern = "[A-Za-z0-9._%-]+@[uwindsor]+\\.[A-Za-z]{2,}";
		File Folder = new File(Config.TEXT_FILES_PATH);
		File[] files = Folder.listFiles();
		System.out.println("Canadian Phone Numbers Found:");
		fndPhone(files, phonePattern);

		System.out.println("\nEmail Addresses Found:");
		EmailAddresses(files, emailPattern);
	}

	private static void fndPhone(File[] files, String inputPattern) throws IOException {
		Pattern pattern = Pattern.compile(inputPattern);
		Matcher matcher;
		for (File f : files) {
			Document doc = Jsoup.parse(f, "UTF-8");
			String text = doc.text();
			matcher = pattern.matcher(text);
			while (matcher.find()) {
				System.out.println(matcher.group());
			}
		}
	}

	private static void EmailAddresses(File[] files, String inputPattern) throws IOException {
		Pattern pattern = Pattern.compile(inputPattern);
		Matcher matcher;
		for (File f : files) {
			Document doc = Jsoup.parse(f, "UTF-8");
			String text = doc.text();
			matcher = pattern.matcher(text);
			while (matcher.find()) {
				System.out.println(matcher.group());
			}
		}
	}
}
