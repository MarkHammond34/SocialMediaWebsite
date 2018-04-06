package edu.ben.util;

public class SearchUtil {

	private SearchUtil() {

	}

	public static String[] getSearchTerms(String search) {
		return search.split(" ");
	}

}