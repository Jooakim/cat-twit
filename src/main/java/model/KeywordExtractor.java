package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

public class KeywordExtractor {
	
	public KeywordExtractor() {
		// Empty for now
	}
	
	public String[] extractKeywords(String str) {
		try {
			parseString(str);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void parseString(String str) throws FileNotFoundException {
			InputStream modelIn = new FileInputStream("util/en-parser-chunking.bin");
			Parser parser;
		    try {
		      ParserModel model = new ParserModel(modelIn);
		      parser = ParserFactory.create(model);
		      Parse p = new Parse(str, null, null, 0, 0);
		      p = parser.parse(p);
		      System.out.println(p.getText());
		    } catch (IOException e) {
		      e.printStackTrace();
		    } finally {
		        try {
		          modelIn.close();
                } catch (IOException e) {
		            e.printStackTrace();
		        }
            }
    }
}

