package control;

import java.util.LinkedList;

import model.TwitterAPIConnection;
    

public class TwitterMiner {
    
    TwitterAPIConnection connection;
    public TwitterMiner() {
        connection = TwitterAPIConnection.getConnection();
    }

    public LinkedList<String> collectNrOfTweets(int nrOfTweets) {
    	return connection.getNrOfMessages(nrOfTweets);
    }
}
