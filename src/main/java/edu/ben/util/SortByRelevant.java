package edu.ben.util;

import edu.ben.models.Post;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByRelevant implements Comparator<Post> {

	private ArrayList<String> userInterestTags;

	public SortByRelevant(ArrayList<String> userTags) {
		super();
		this.userInterestTags = userTags;
	}

	@Override
	public int compare(Post post1, Post post2) {

		return getMatchCount(userInterestTags, post2) - getMatchCount(userInterestTags, post1);
	}

	private int getMatchCount(ArrayList<String> userInterestTags, Post post) {

		int matchCount = 0;

		for (int i = 0; i < post.getTags().size(); i++) {
			for (int j = 0; j < userInterestTags.size(); j++) {
				if (post.getTags().get(i).equals(userInterestTags.get(j))) {
					matchCount++;
				}
			}
		}
		// System.out.println(matchCount);
		return matchCount;
	}

}
