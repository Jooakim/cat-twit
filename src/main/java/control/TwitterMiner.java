package control;

import java.io.StringReader;
import java.util.ArrayList;
//import model.*;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.stream.JsonParser;

import model.TwitterAPIConnection;
    

public class TwitterMiner {
    
    TwitterAPIConnection connection;
    public TwitterMiner() {
        connection = new TwitterAPIConnection(); 
    }

    public void collectNrOfTweets(int nrOfTweets) {
        ArrayList<String> messages = connection.getNrOfMessages(nrOfTweets);
        LinkedList<String> tweets = parseMessages(messages);
        
        printTweets(tweets);
    }

    private void printTweets(LinkedList<String> tweets) {
    	for (String tweet : tweets) {
    		System.out.println(tweet);
    	}
		
	}

	private LinkedList<String> parseMessages(ArrayList<String> messages) {
    	LinkedList<String> tweets = new LinkedList<>();
        
        for (String message : messages) {
        	
        	if (message != null) {
        		JsonParser parser = Json.createParser(new StringReader(message));
        		while (parser.hasNext()) {
        			if (parser.next() == JsonParser.Event.KEY_NAME) {
        				String key = parser.getString();
        				if (key.equals("text")) {
        					parser.next();
        					tweets.add(parser.getString());
        				}
        				
        			}
        		}
        		parser.close();
            }
        }
        return tweets;
    }

}
