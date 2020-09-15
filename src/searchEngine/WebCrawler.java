package searchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
	//creating a list to store urls
   private static List<URL> crawl(URL start, int limit) {
       List<URL> urlList = new ArrayList<URL>(limit);
       urlList.add(start);

       //Creating a set of url named urlCopy which is same as the urls' for faster execution
       Set<URL> urlCopy = new HashSet<URL>(urlList);
       int i = 0;
       while (urlList.size() < limit && i < urlList.size()) {
           URL currentUrl = urlList.get(i);
           for (URL url : fetchLinks(currentUrl)) {
               if (urlCopy.add(url)) {
                   urlList.add(url);
                   if (urlList.size() == limit) {
                       break;
                   }
               }
           }
           i++;
       }
       return urlList;
   }

   /*Static method to crawl and traverse associated links from the webpage
    * Traverses and prints the extracted associated url*/
	public static void webCrawlerDemo() {
		try {
			URL url = new URL("http://www.uwindsor.ca");
			int crawlerLimit = 100, i = 1;
			//crawl and returns the discovered links from the input url
			List<URL> discovered = WebCrawler.crawl(url, crawlerLimit);
			System.out.println("Showing results: ");
			Iterator<URL> iterator = discovered.iterator();
			//looping in for all links and extracting the contents
			while (iterator.hasNext()) {
				URL newUrl = iterator.next();
				System.out.println(i + " " + newUrl);
				String s = fetchContent(newUrl);
				String filename = newUrl.toString();
				filename = filename.substring(filename.indexOf("://") + 3);
				filename = filename.replaceAll("/", "_");				
				try {
					//saving the html file 
					saveHtmlPage(s, filename, "html");
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}

		} catch (MalformedURLException e) {
			System.err.println("The URL to start crawling with is invalid.");
		}
	}
    
   	/*Extract all links contained in web page and validate the same using patterns
    returns set of links in order they occur*/
    private static LinkedHashSet<URL> fetchLinks(URL url) {
       LinkedHashSet<URL> links = new LinkedHashSet<URL>();
       Pattern pattern = Pattern.compile("a href=\"((http://|https://|www).*?)\"");
       Matcher matcher = pattern.matcher(fetchContent(url));

       while (matcher.find()) {
           String linkStr = normalizeUrlStr(matcher.group(1));
           try {
               URL link = new URL(linkStr);
               links.add(link);
           }
           catch (MalformedURLException e) {
               System.err.println(url + " has a link to invalid URL : " + linkStr + ".");
           }
       }
       return links;
   }

   //used to fetch the content from url and return fetched content
   private static String fetchContent(URL url) {
       StringBuilder stringBuilder = new StringBuilder();
       try {
           BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
           String inputLine;
           while ((inputLine = br.readLine()) != null) {
               stringBuilder.append(inputLine+"\n");
           }
           br.close();
       }
       catch (IOException e) {
           System.err.println("An error occured from " + url);
       }    
       return stringBuilder.toString();
   }

  //It normalizes the string representation of url so its easier to transform to url object.
   private static String normalizeUrlStr(String urlStr) {
       if (!urlStr.startsWith("http")) {
           urlStr = "http://" + urlStr;
       }
       if (urlStr.endsWith("/")) {
           urlStr = urlStr.substring(0, urlStr.length() - 1);
       }
       if (urlStr.contains("#")) {
           urlStr = urlStr.substring(0, urlStr.indexOf("#"));
       }
       return urlStr;
   }

   /*Static method to take String data with name and extension to save the same
    * return boolean value to validate if saved successfully*/
   private static boolean saveHtmlPage(String data, String filename, String extension) throws IOException {
	   //File type variable to create a directory if not exists
	   File file = new File("uWindsor");
	   if(!file.exists()) {
		   file.mkdir();
	   }
	   file = new File(file.getName()+"/"+filename+"."+extension);
	   //validating if file already exists
	   if(file.exists()) {
		   System.out.println(file.getName()+" already exists");
		   return false;
	   }
	   //writing the contents using FileWriter class method
	   FileWriter fw = new FileWriter(file);
	   fw.write(data);
	   fw.close();
	   
	   return true;
   }
}
