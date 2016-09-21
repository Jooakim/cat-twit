package model;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesSampleEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.concurrent.BlockingQueue;
import java.lang.InterruptedException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;



public class TwitterAPIConnection {
    private String consumerKey, consumerSecret, token, tokenSecret;
    private BlockingQueue<String> msgQueue;
    private BasicClient client;
    private StatusesSampleEndpoint endpoint;
    private StringDelimitedProcessor stringProcessor;


    private static final TwitterAPIConnection connection = new TwitterAPIConnection();

    private TwitterAPIConnection() {
        // Create an appropriately sized blocking queue
        msgQueue = new LinkedBlockingQueue<String>(10000);

        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);

        readFromAuthenticationFile("/home/joakim/.twitterAuth");

        Authentication auth = new OAuth1(consumerKey, consumerSecret,
                token, tokenSecret);
        stringProcessor = new StringDelimitedProcessor(msgQueue);

        // Create a new BasicClient. By default gzip is enabled.
        client = new ClientBuilder()
            .name("sampleExampleClient")
            .hosts(Constants.STREAM_HOST)
            .endpoint(endpoint)
            .authentication(auth)
            .processor(stringProcessor)
            .build();

    }

    /**
     * Reads twitter authentication from a hidden file
     * in users home directory
     */
    private void readFromAuthenticationFile(String authPath) {
        File authFile = new File(authPath);
        if (authFile.exists() && !authFile.isDirectory()) {
            try {
                Scanner scan = new Scanner(authFile);
                consumerKey = scan.nextLine();
                consumerSecret = scan.nextLine();
                token = scan.nextLine();
                tokenSecret = scan.nextLine();
                scan.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } 
    }

    public String getLatestTweet() {
        return msgQueue.peek();
    }


    /**
     * Get a requested number of messages since first creation of the instance.
     *
     * @param nrOfMessages
     */
    public LinkedList<String> getNrOfMessages(int nrOfMessages) {
        client.connect();
        LinkedList<String> messages = new LinkedList<String>();
        for (int msgRead = 0; msgRead < nrOfMessages; msgRead++) {
            try {
                String msg = msgQueue.poll(1, TimeUnit.SECONDS);
                messages.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        client.stop();
        return messages;
    }

    /**
     * Returns the connection to the Twitter API
     */
    public static TwitterAPIConnection getConnection() {
        return connection;
    }

    /**
     * Checks if there is a readable file under the given path
     */
    public boolean authFileExist(String authPath) {
        File authFile = new File(authPath);
        if (authFile.exists() && !authFile.isDirectory()) {
            return true;
        } else {
            return false; 
        }
    }
}
