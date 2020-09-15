package searchEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SearchEngine_App {

	// Method to start the Search Engine application
	private static String startApp() throws IOException {
		System.out.println("\n\t\tWelcome to Search Engine....!!");
		System.out.println("\nPlease select an option to proceed !!");
		System.out.println("1.Web Crawler \n2.Web Search \n3.Pincode search \n4.Pattern Search\n5.About \n0.Exit");
		System.out.print("\nEnter Choice : ");	
		//Scanner kb = new Scanner(System.in);		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
		
	}

	public static void pinCodeSearch() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the City : ");
		String source = br.readLine();
		String[] pincodes = SymbolGraph.getPincodes(source);
		for (String p : pincodes) {
			System.out.println(p);
		}
	}
	
	private static void printAbout() {

		System.out.println("\t    Web Search Engine"+ "\n\tAdvance Computing Concepts" + "\n\t\tCOMP 8547" +"\n\t    5University of Windsor");
		System.out.println(" Group 28 \n Damanpal Singh Rayat (110013656)\n Harshit Sahu (110013591) \n Sarabjit Singh (110010484)");
	}

	// The main method for the search engine application
	public static void main(String[] args) {
		// String variable for recording user input
		String inputOption = "";

		// Looping based on user selection
		while (!inputOption.equals("0")) {
			try {
				// initialize application
				inputOption = startApp();
				switch (inputOption) {
					case "1":{
						// Web crawler to crawl and traverse website urls'
						WebCrawler.webCrawlerDemo();
						HtmlTextConvertor.htmlTextConvertor();
						break;
					}
					case "2":{
						// Web search engine
						WebSearch.webSearch();						
						break;}
	
					case "3":{
						// Pin code search
						pinCodeSearch();
						break;}
	
					case "4":{
						// Pattern search for phone,email and pin codes
						FindPattern.findPattern();
						break;
					}
					case "5":{
						// Information about creators
						printAbout();
						break;
					}
					case "0":{
						// Exit application
						System.out.println("Exiting Application!!");
						System.exit(0);
						break;
					}
					default:{
						// Default alert for invalid input from user
						System.out.println("Invalid option.. Please select another option!!");
				}}
				// Exception block
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
