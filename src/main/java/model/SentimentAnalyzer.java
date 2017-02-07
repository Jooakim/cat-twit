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
    private static final String SAVED_MODEL_LOCATION = "src/main/resources/savedModel";

    public SentimentAnalyzer() {
        if (hasSavedModel()) {
            fetchSavedModel();
        } else {
            trainModel("util/test.txt");
        }
    }

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

    private boolean hasSavedModel() {
        File f = new File(SAVED_MODEL_LOCATION);
        return f.exists();
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
            saveModel(model);

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

    public void fetchSavedModel() {
        try {
            BufferedInputStream modelIn = new BufferedInputStream(new FileInputStream(SAVED_MODEL_LOCATION));
            model = new DoccatModel(modelIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveModel(DoccatModel model) {
        try {
            BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream(SAVED_MODEL_LOCATION));
            model.serialize(modelOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DoccatModel getModel() {
        return model;
    }
}
