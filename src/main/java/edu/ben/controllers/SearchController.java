package edu.ben.controllers;

import edu.ben.DAOs.PostDAO;
import edu.ben.DAOs.UserDAO;
import edu.ben.models.Post;
import edu.ben.models.User;
import edu.ben.util.SearchUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class SearchController extends BaseController {

	@GetMapping("/search")
	public String search(@RequestParam("searchString") String searchString, HttpServletRequest request, Model model) {

		ArrayList<Post> postSearchResults = new ArrayList<>(100);
		ArrayList<User> userSearchResults = new ArrayList<>(100);

		// check string not null and not empty
		if (searchString != null && searchString.length() > 0) {

			// add posts
			addPostsMatchingSearch(postSearchResults, searchString);

			// add users
			addUsersMatchingSearch(userSearchResults, searchString);
			// add posts matching tags
			addPostsMatchingTags(postSearchResults, searchString);

		}

		model.addAttribute("postList", postSearchResults);
		model.addAttribute("userSearchResults", userSearchResults);
		// model.addAttribute("searchString", searchString);

		request.setAttribute("resultsType", "Search results for \"" + searchString + "\":");

		return "results";

	}

	private void addUsersMatchingSearch(ArrayList<User> searchResults, String searchString) {

		// split search into individual terms
		String[] terms = SearchUtil.getSearchTerms(searchString);

		// loop through search terms
		for (int i = 0; i < terms.length; i++) {

			// ArrayList<Post> queryResults = PostDAO.getPostsMatchingSearch(terms[i]);
			ArrayList<User> queryResults = UserDAO.getUsersMatchingSearch(terms[i]);

			for (User user : queryResults) {
				if (!searchResults.contains(user)) {
					searchResults.add(user);
				}
			}

		}

	}

	private void addPostsMatchingSearch(ArrayList<Post> searchResults, String searchString) {

		// split search into individual terms
		String[] terms = SearchUtil.getSearchTerms(searchString);

		// loop through search terms
		for (int i = 0; i < terms.length; i++) {

			// get posts fuzzy matching search terms
			ArrayList<Post> queryResults = PostDAO.getPostsMatchingSearch(terms[i]);

			// add posts to search results
			for (Post post : queryResults) {
				if (!searchResults.contains(post)) {
					searchResults.add(post);
				}
			}
		}

	}

	private void addPostsMatchingTags(ArrayList<Post> searchResults, String searchString) {

		// split search into individual terms
		String[] terms = SearchUtil.getSearchTerms(searchString);

		// loop through search terms
		for (int i = 0; i < terms.length; i++) {

			// get posts fuzzy matching search terms
			ArrayList<Post> queryResults = PostDAO.getPostsMatchingTags(terms[i]);

			// add posts to search results
			for (Post post : queryResults) {
				if (!searchResults.contains(post)) {
					searchResults.add(post);
				}
			}
		}

	}

}