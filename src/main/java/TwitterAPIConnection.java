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



public class TwitterAPIConnection {
    private String consumerKey, consumerSecret, token, tokenSecret;
    BlockingQueue<String> msgQueue;
    BasicClient client;
    public TwitterAPIConnection() {
        // Create an appropriately sized blocking queue
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        // Define our endpoint: By default, delimited=length is set (we need this for our processor)
        // and stall warnings are on.
        StatusesSampleEndpoint endpoint = new StatusesSampleEndpoint();
        endpoint.stallWarnings(false);

        //Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
        readFromAuthenticationFile();

        Authentication auth = new OAuth1(consumerKey, consumerSecret,
                token, tokenSecret);
        //Authentication auth = new com.twitter.hbc.httpclient.auth.BasicAuth(username, password);

        // Create a new BasicClient. By default gzip is enabled.
        BasicClient client = new ClientBuilder()
            .name("sampleExampleClient")
            .hosts(Constants.STREAM_HOST)
            .endpoint(endpoint)
            .authentication(auth)
            .processor(new StringDelimitedProcessor(queue))
            .build();

        // Establish a connection
        client.connect();

        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            if (client.isDone()) {
                System.out.println("Client connection closed unexpectedly: " + client.getExitEvent().getMessage());
                break;
            }

            try {
                String msg = queue.poll(5, TimeUnit.SECONDS);
                if (msg == null) {
                    System.out.println("Did not receive a message in 5 seconds");
                } else {
                    System.out.println(msg);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        client.stop();

        // Print some stats
        System.out.printf("The client read %d messages!\n", client.getStatsTracker().getNumMessages());
        //msgQueue = new LinkedBlockingQueue<String>(10000);

        //StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        //readFromAuthenticationFile();

        //Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret,
        //token, tokenSecret);

        //ClientBuilder builder = new ClientBuilder() 
        //.name("Hosebird-Client-01")
        //.hosts(Constants.STREAM_HOST)
        //.authentication(hosebirdAuth)
        //.endpoint(hosebirdEndpoint)
        //.processor(new StringDelimitedProcessor(msgQueue));


        //BasicClient hosebirdClient = builder.build();

        //hosebirdClient.connect();


        //while (!hosebirdClient.isDone()) {
        //try {
        //String message = msgQueue.take();
        //System.out.println(message);
        //} catch (Exception e) {
        //e.printStackTrace();
        //}
        //}

    }

    /**
     * Reads twitter authentication from a hidden file
     * in users home directory
     */
    private void readFromAuthenticationFile() {
        try {
            Scanner scan = new Scanner(new File("/home/joakim/.twitterAuth"));
            consumerKey = scan.nextLine();
            consumerSecret = scan.nextLine();
            token = scan.nextLine();
            tokenSecret = scan.nextLine();
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean testConnection() {
        return true;
    }

    public String getLatestTweet() {
        return msgQueue.peek();

    }


}
