import java.util.HashSet;
import java.util.LinkedList;

import control.TwitterMiner;
import model.TwitterParser;
import org.apache.log4j.BasicConfigurator;

public class App {
    public static void main(String[] args) {
    	final int NR_OF_TWEETS = 1000;
//        BasicConfigurator.configure();
        TwitterMiner miner = new TwitterMiner();
        TwitterParser parser = new TwitterParser(miner.collectNrOfTweets(NR_OF_TWEETS));

        HashSet<String> langs = new HashSet<>();
        langs.add("en");
        LinkedList<String> tweets = parser.extractTweets(langs);
        printTweets(tweets);
    }
    
    private static void printTweets(LinkedList<String> tweets) {
    	for (String tweet : tweets) {
    		System.out.println(tweet);
    	}
    }
}
