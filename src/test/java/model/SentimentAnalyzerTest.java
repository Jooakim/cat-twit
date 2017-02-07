package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import opennlp.tools.doccat.DoccatModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joakim on 05/02/17.
 */
public class SentimentAnalyzerTest {

    SentimentAnalyzer analyzer;
    @Before
    public void setUp() {
    }
    @Test
    public void sentimentTest() {
        analyzer = new SentimentAnalyzer();
        List<String> tweets = new ArrayList<>();
        tweets.add("I hate my life");
        tweets.add("I love my life");
        tweets.add("Fuck you Adam");

        Map<String, Boolean> correctSentiments = new HashMap<>();
        correctSentiments.put("I hate my life", false);
        correctSentiments.put("I love my life", true);
        correctSentiments.put("Fuck you Adam", false);


        Map<String, Boolean> sentiments = analyzer.analyzeSentiment(tweets);

        assertEquals("The sentiment is not correct", correctSentiments, sentiments) ;
    }
    @Test
    public void testWriteAndFetchModel() {
        analyzer = new SentimentAnalyzer("util/test.txt");
        DoccatModel model = analyzer.getModel();
        analyzer.saveModel(model);
        analyzer.fetchSavedModel();
        DoccatModel fetchedModel = analyzer.getModel();
        assertTrue(fetchedModel.isLoadedFromSerialized());
    }
}
