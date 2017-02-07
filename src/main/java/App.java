import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import control.TwitterMiner;
import model.SentimentAnalyzer;
import model.TwitterParser;

public class App {
    public static void main(String[] args) {
    	final int NR_OF_TWEETS = 1000;
        TwitterMiner miner = new TwitterMiner();
        TwitterParser parser = new TwitterParser(miner.collectNrOfTweets(NR_OF_TWEETS));

        HashSet<String> langs = new HashSet<>();
        langs.add("en");
        LinkedList<String> tweets = parser.extractTweets(langs);

        SentimentAnalyzer analyzer = new SentimentAnalyzer();
        Map<String, Boolean> sentiment = analyzer.analyzeSentiment(tweets);
        countSentiments(sentiment);
    }

    @SuppressWarnings("unused")
    private static void printTweets(LinkedList<String> tweets) {
    	for (String tweet : tweets) {
    		System.out.println(tweet);
    	}
    }

    private static void countSentiments(Map<String, Boolean> sentiments) {
        int positive = 0, negative = 0;

        for (String s : sentiments.keySet()) {
            if (sentiments.get(s)) {
                positive++;
            } else {
                negative++;
            }
        }

        System.out.println("Number of positive: " + positive + " and number of negative: " + negative);
    }
}
