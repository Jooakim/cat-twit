import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

import model.*;

public class TwitterAPITest {

    /**
     * Test the connection to Twitter API
     */
    @Test
    public void testConnection() {
        assumeTrue(TwitterAPIConnection.authFileExist("/home/joakim/.twitterAuth"));
        TwitterAPIConnection connection = TwitterAPIConnection.getConnection();
        String aTweet = connection.getNrOfMessages(1).toString();
        assertTrue(aTweet != null);
    }
}
