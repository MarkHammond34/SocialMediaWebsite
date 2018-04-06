package edu.ben.controllers;

import edu.ben.DAOs.UserDAO;
import edu.ben.models.Post;
import edu.ben.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class ProfileController extends BaseController {

	@GetMapping("/profile")
	public String viewProfile(HttpServletRequest request, Model m) {

		boolean hasFollowedUser = false;

		int id = Integer.parseInt(request.getParameter("userId"));

		User user = UserDAO.getUserByUserId(id);
		m.addAttribute("user", user);

		String content = request.getParameter("content");

		ArrayList<Post> posts = new ArrayList<Post>();
		ArrayList<User> usersFollowed = new ArrayList<>();
		ArrayList<User> usersFollowedBy = new ArrayList<>();

		if (content.equals("Posts")) {
			posts = UserDAO.getPostsByUserId(id);

		} else if (content.equals("Tacks")) {
			posts = UserDAO.getTacksByUserId(id);

		} else if (content.equals("Likes")) {
			posts = UserDAO.getLikesByUserId(id);

		} else if (content.equals("Following")) {
			usersFollowed = UserDAO.getFollowedUsersByUserID(id);

		} else if (content.equals("Followers")) {
			usersFollowedBy = UserDAO.getUsersFollowingUserID(id);
		} else if (content.equals("Drafts")) {
			posts = UserDAO.getDraftsByUserId(id);
		}

		User userLoggedIn = (User) request.getSession().getAttribute("userLoggedIn");

		if (userLoggedIn != null) {

			ArrayList<User> myFollowedUsers = UserDAO.getFollowedUsersByUserID(userLoggedIn.getUserID());

			if (myFollowedUsers.contains(UserDAO.getUserByUserId(id))) {
				hasFollowedUser = true;
			}
		}

		m.addAttribute("content", content);
		m.addAttribute("postList", posts);
		m.addAttribute("usersFollowed", usersFollowed);
		m.addAttribute("usersFollowedBy", usersFollowedBy);
		m.addAttribute("hasFollowedUser", hasFollowedUser);

		setMessagesInRequest(request);
		return "profile";

	}

}
