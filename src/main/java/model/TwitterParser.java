package model;

import java.io.StringReader;
import java.util.HashSet;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.stream.JsonParser;

public class TwitterParser {
	
	private LinkedList<String> messages;
	public TwitterParser(LinkedList<String> tweets) {
		this.messages = tweets;
	}
	
	public LinkedList<String> extractTweets(HashSet<String> languages) {
    	LinkedList<String> tweets = new LinkedList<>();
        
        for (String tweet : messages) {
        	
        	if (tweet != null) {
        		JsonParser parser = Json.createParser(new StringReader(tweet));
				String text = null;
				boolean correctLang = false;
        		while (parser.hasNext()) {
        			if (parser.next() == JsonParser.Event.KEY_NAME) {
        				String key = parser.getString();
        				if (key.equals("text")) {
        					parser.next();
        					text = parser.getString();
						} else if (key.equals("lang")) {
        					parser.next();
        					if (languages.contains(parser.getString())) {
        						correctLang = true;
							}
        				}
        			}
        		}
        		parser.close();
        		if (correctLang && text != null) {
        			tweets.add(text);
        		}
            }
        }
        return tweets;

		
	}

}
