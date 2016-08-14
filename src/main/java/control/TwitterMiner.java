package controller;

//import model.*;
import java.util.LinkedList;
import java.util.ArrayList;

import javax.json.json;
import javax.json.stream;
import java.io.StringReader;
    

public class TwitterMiner {
    
    TwitterAPIConnection connection;
    public TwitterMiner() {
        connection = new TwitterAPIConnection(); 
    }

    public void collectNrOfTweets(int nrOfTweets) {
        ArrayList<String> messages = connection.getNrOfMessages(nrOfTweets);
        LinkedList<String> tweets = parseMessages(messages);
    }

    private LinkedList<String> parseMessages(ArrayList<String> messages) {
        
        for (String message : messages) {
            JsonParser parser = Json.createParser(new StringReader(message));
            while (parser.hasNext()) {
                System.out.println(parser.getString());
            }
        }
        return null;
    }

}
