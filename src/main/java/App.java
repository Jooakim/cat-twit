import control.TwitterMiner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        TwitterMiner miner = new TwitterMiner();
        miner.collectNrOfTweets(1000);
    }
}
