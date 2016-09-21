import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import model.*;

public class TwitterAPITest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TwitterAPITest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TwitterAPITest.class);
    }

    /**
     * Test the connection to Twitter API
     */
    public void testConnection() {
        TwitterAPIConnection connection = TwitterAPIConnection.getConnection();
        if (connection.authFileExist("/home/joakim/.twitterAuth")) {
            String aTweet = connection.getNrOfMessages(1).toString();
            assertTrue(aTweet != null);
        }
    }
}
