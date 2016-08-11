import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
    public void testConnection () {
        TwitterAPIConnection connection = new TwitterAPIConnection();
        String aTweet = connection.getLatestTweet();
        System.out.println(aTweet);
        assertTrue(connection.testConnection());
        assertTrue(aTweet != null);
        
    }
}
