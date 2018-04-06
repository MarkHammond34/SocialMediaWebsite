package edu.ben.util;

import edu.ben.models.Post;
import edu.ben.models.User;

import java.util.ArrayList;
import java.util.Collections;

public class PostUtil {

	private static final int TACK_WEIGHT = 8;
	private static final int LIKE_WEIGHT = 6;
	private static final double VIEW_WEIGHT = 1.25;
	private static final double TIME_WEIGHT = 2.5;

	/**
	 * Takes in a post object and returns it trending score
	 * 
	 * @param post
	 * @return trendingScore integer representing a score of how trending something
	 *         is
	 */
	private static int getTrendingScore(Post post) {

		double metricScore = post.getTackCount() * TACK_WEIGHT + post.getLikeCount() * LIKE_WEIGHT
				+ post.getViewCount() * VIEW_WEIGHT;

		double timeFactor = ((System.currentTimeMillis() - post.getCreatedOn().getTime()) / (1000 * 3600))
				* TIME_WEIGHT;

		int trendingScore = (int) (metricScore - timeFactor);

		if (trendingScore < 0) {
			trendingScore = 0;
		}

		return trendingScore;
	}

	public static void sortByTrending(ArrayList<Post> posts) {
		for (Post p : posts) {
			p.setTrendingScore(getTrendingScore(p));
			// System.out.println(p.getTitle() + " trending score: " + p.getTrendingScore());
		}

		if (posts.size() > 0) {
			Collections.sort(posts);
		}

	}

	public static void sortByRelevantToUser(ArrayList<String> userInterestTags, ArrayList<Post> posts) {

		Collections.sort(posts, new SortByRelevant(userInterestTags));

	}

	public static void sortByFollowedUserPosts(ArrayList<User> followedUsers, ArrayList<Post> posts) {

		Collections.sort(posts, new SortByFollowedUserPosts(followedUsers));

	}

}