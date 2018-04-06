package edu.ben.controllers;

import edu.ben.DAOs.PostDAO;
import edu.ben.models.Post;
import edu.ben.util.PostUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class TrendingController extends BaseController {

	@GetMapping("/trending")
	public String trendingGet(HttpServletRequest request, Model m) {

		// Gets all posts and sorts by trending in the last 24 hours
		ArrayList<Post> trendingPosts = PostDAO.getPostsNumOfHours(24);

		PostUtil.sortByTrending(trendingPosts);

		m.addAttribute("postList", trendingPosts);

		// If user is signed in, return to trending page
		// if (request.getSession().getAttribute("userLoggedIn") != null) {
		// return "results";
		// }

		m.addAttribute("resultsType", "Trending");

		return "results";
	}

	@PostMapping("/trending")
	public void trendingPost(HttpServletRequest request, Model m) {
		trendingGet(request, m);
	}
}