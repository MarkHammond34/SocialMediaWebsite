package edu.ben.controllers;

import edu.ben.DAOs.PostDAO;
import edu.ben.DAOs.TagDAO;
import edu.ben.DAOs.UserDAO;
import edu.ben.models.Post;
import edu.ben.models.User;
import edu.ben.util.PostUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class SortController {

	private static final String SORT_TRENDING = "trending";
	private static final String SORT_RELEVANT = "relevant";
	private static final String SORT_FOLLOWED = "followed";

	@PostMapping("/sort")
	public String sort(@RequestParam("sortType") String sortType, HttpServletRequest request, Model m) {

		String[] postIds = request.getParameterValues("postID");

		ArrayList<Post> postList = new ArrayList<>(100);

		// add posts into list
		for (int i = 0; i < postIds.length; i++) {
			postList.add(PostDAO.getPostByPostIdNoComments(Integer.parseInt(postIds[i])));
		}

		// trending
		if (postList != null && sortType.equals(SORT_TRENDING)) {

			// System.out.println("sort by trending");

			PostUtil.sortByTrending(postList);

			// relevant
		} else if (postList != null && sortType.equals(SORT_RELEVANT)) {

			// System.out.println("sort by relevant");

			User user = (User) request.getSession().getAttribute("userLoggedIn");
			ArrayList<String> userInterestTags = TagDAO.getTagsByUserID(user.getUserID());

			PostUtil.sortByRelevantToUser(userInterestTags, postList);

			// following
		} else if (postList != null && sortType.equals(SORT_FOLLOWED)) {

			// System.out.println("sort by followed");

			User user = (User) request.getSession().getAttribute("userLoggedIn");
			ArrayList<User> usersFollowed = UserDAO.getFollowedUsersByUserID(user.getUserID());

			PostUtil.sortByFollowedUserPosts(usersFollowed, postList);
		}

		request.setAttribute("postList", postList);

		request.setAttribute("resultsType", request.getParameter("resultsType"));

		// System.out.println("post list" + postList);

		return "results";
	}

}
