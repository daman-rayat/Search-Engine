package searchEngine;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.jsoup.*;

public class HtmlTextConvertor {
	public static File inputDir = new File(Config.HTML_FILES_DIR);
	public static File outputDir = new File(Config.TEXT_FILES_PATH);

	public static void htmlTextConvertor() throws IOException {		
		for (final File fileName : inputDir.listFiles()) {	
			if (fileName.isFile()) {
				convertFileToText(fileName.getName());
			}
		}				
		System.out.println("files converted to text and saved..");
	}

	private static void convertFileToText(String inputfilename) throws IOException {
		String[] fName = inputfilename.split("\\.htm");
		String fileName = fName[0];
		if (!outputDir.exists()) {
			System.out.println("creating directory: " + outputDir);
			boolean result = outputDir.mkdir();
			if (result) {
				System.out.println("DIR created");
			}
		}
		File inputFile = new File(Config.HTML_FILES_DIR + "/" + inputfilename);
		org.jsoup.nodes.Document doc = Jsoup.parse(inputFile, "UTF-8", "");	
		String outputFileName = Config.TEXT_FILES_PATH + fileName+ ".txt";
		String text = doc.text();
		PrintWriter out = new PrintWriter(outputFileName);
		out.println(text);
		out.close();
	}
}
