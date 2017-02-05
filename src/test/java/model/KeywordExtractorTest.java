package model;

import org.junit.BeforeClass;
import org.junit.Test;

public class KeywordExtractorTest {
	private static KeywordExtractor extractor;

	@BeforeClass
	public static void setUp() {
		extractor = new KeywordExtractor();
	}

	@Test
	public void test() {
		//String[] keywords = extractor.extractKeywords("My name is Joakim");
		//assertTrue(keywords != null);
	}

}
