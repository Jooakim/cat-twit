package model;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by joakim on 05/02/17.
 */
public class SentimentAnalyzer {

    private DoccatModel model;

    public SentimentAnalyzer(String trainFile) {
        trainModel(trainFile);
    }

    public Map<String, Boolean> analyzeSentiment(List<String> tweets)  {

        Map<String, Boolean> sentiments = new HashMap<>();
        for (String tweet : tweets) {
            boolean sentiment = determineSentiment(tweet);
            sentiments.put(tweet, sentiment);
        }

        return sentiments;
    }

    private boolean determineSentiment(String tweet) {
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        double[] outcomes = categorizer.categorize(tweet);
        String category = categorizer.getBestCategory(outcomes);
        return category.equals("1");
    }

    private void trainModel(String inputFile) {
        try {
            // Setup training file
            InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File(inputFile));
            ObjectStream lineStream = new PlainTextByLineStream(inputStreamFactory, "UTF-8");
            ObjectStream sampleStream = new DocumentSampleStream(lineStream);
            DoccatFactory factory = new DoccatFactory();

            TrainingParameters params = createParams();

            model = DocumentCategorizerME.train("en", sampleStream, params, factory);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TrainingParameters createParams() {
        TrainingParameters parameters = new TrainingParameters();
        parameters.put("Cutoff", "2");
        parameters.put("Iterations", "25");

        return parameters;
    }
}
