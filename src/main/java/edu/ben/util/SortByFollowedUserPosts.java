package edu.ben.util;

import edu.ben.models.Post;
import edu.ben.models.User;

import java.util.ArrayList;
import java.util.Comparator;

public class SortByFollowedUserPosts implements Comparator<Post> {

	private ArrayList<User> usersFollowed;

	public SortByFollowedUserPosts(ArrayList<User> usersFollowed) {
		super();
		this.usersFollowed = usersFollowed;
	}

	@Override
	public int compare(Post post1, Post post2) {
		if (!isUserMatch(usersFollowed, post1) && isUserMatch(usersFollowed, post2)) {
			return 1;
		} else if (isUserMatch(usersFollowed, post1) && !isUserMatch(usersFollowed, post2)) {
			return -1;
		} else {
			return 0;
		}
	}

	private boolean isUserMatch(ArrayList<User> usersFollowed, Post post) {
		for (User user : usersFollowed) {
			if (user.getUserID() == post.getUserID()) {
				return true;
			}
		}

		return false;
	}

}
