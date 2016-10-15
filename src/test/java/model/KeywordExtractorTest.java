package model;

import static org.junit.Assert.*;

import org.junit.Before;
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
		String[] keywords = extractor.extractKeywords("My name is Joakim");
		assertTrue(keywords != null);
	}

}
