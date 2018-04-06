package edu.ben.controllers;

import edu.ben.DAOs.LoginDAO;
import edu.ben.DAOs.PostDAO;
import edu.ben.DAOs.UserDAO;
import edu.ben.models.Post;
import edu.ben.models.User;
import edu.ben.util.CodeGenerator;
import edu.ben.util.Mail;
import edu.ben.util.PostUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class UserController extends BaseController {

	@Autowired
	private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private void getPosts(HttpServletRequest request, Model m) {

		if (request.getSession().getAttribute("userLoggedIn") != null) {

			User user = (User) request.getSession().getAttribute("userLoggedIn");
			ArrayList<Post> relevantPosts = PostDAO.getRelevantPostsByUserID(user.getUserID());
			m.addAttribute("postList", relevantPosts);
			m.addAttribute("resultsType", "Relevant");
		} else {

			// Gets all posts and sorts by trending in the last 24 hours
			ArrayList<Post> trendingPosts = PostDAO.getPostsNumOfHours(24);
			PostUtil.sortByTrending(trendingPosts);

			m.addAttribute("postList", trendingPosts);
			m.addAttribute("resultsType", "Trending");
		}

	}

	@GetMapping(value = { "/", "/relevant" })
	public String hello(HttpServletRequest request, Model m) {

		getPosts(request, m);
		setMessagesInRequest(request);
		return "results";
	}

	@GetMapping("/home")
	public String homepage(HttpServletRequest request, Model m) {

		getPosts(request, m);
		setMessagesInRequest(request);
		return "results";
	}

	@GetMapping("/register")
	public String register(HttpServletRequest request) {
		setMessagesInRequest(request);
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(HttpServletRequest request, Model m) {

		// get params
		try {
			String firstname = request.getParameter("first_name").trim();
			String lastname = request.getParameter("last_name").trim();
			String email = request.getParameter("email").trim();
			String pwd = request.getParameter("password").trim();
			String pwdRepeat = request.getParameter("password_confirmation").trim();
			String username = request.getParameter("username").trim();

			System.out.println("name=" + firstname);

			// validate
			boolean validRegister = true;

			// enter error message for each failing condition
			if (firstname == "" || lastname == "" || email == "" || pwd == "" || pwdRepeat == "") {
				validRegister = false;
				addErrorMessage("Please enter information in all fields");
			}

			if (email.indexOf('@') == -1) {
				validRegister = false;
				addErrorMessage("Please enter a valid email");
			}

			if (!pwd.equals(pwdRepeat)) {
				validRegister = false;
				addErrorMessage("Please make sure your passwords match");
			}

			// check in db for existing user
			if (LoginDAO.exists(username)) {
				validRegister = false;
				addErrorMessage("This username has already been registered");

			} else if (LoginDAO.exists(email)) {
				validRegister = false;
				addErrorMessage("This email has already been registered");
			}

			String encodedPwd = passwordEncoder.encode(pwdRepeat);

			User u = LoginDAO.register(firstname, lastname, username, email, encodedPwd);
			if (u == null) {
				validRegister = false;
			}

			// redirect
			if (validRegister) {
				request.getSession().setAttribute("userLoggedIn", u);
				setMessagesInRequest(request);
				return "redirect:/";

			} else {
				setMessagesInRequest(request);
				return "register";
			}

		} catch (Exception e) {
			// if input values are null
			// enter error message and try register again
			addErrorMessage("Please enter information in all fields");
			setMessagesInRequest(request);
			return "redirect:/register";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

		// WebUtils.setSessionAttribute(arg0, arg1, arg2);
		request.getSession().setAttribute("userLoggedIn", null);
		request.getSession().invalidate();
		setMessagesInRequest(request);
		return "redirect:/home";
	}

	@GetMapping("/unlock")
	public String unlockUser(HttpServletRequest req, Model m) {
		String code = req.getParameter("code");
		int userID = Integer.parseInt(req.getParameter("userID"));

		m.addAttribute("code", code);
		m.addAttribute("userID", userID);
		setMessagesInRequest(req);
		return "unlock";
	}

	@PostMapping("/unlockuser")
	public String unlockuser(HttpServletRequest req) {
		String code = req.getParameter("code");
		int userID = Integer.parseInt(req.getParameter("userID"));

		if (code.equals(LoginDAO.getCode(userID))) {
			LoginDAO.toggleAccount(userID, true);
			LoginDAO.resetLoginAttempts(userID);
			addErrorMessage("Your account has been unlocked, you may reset your password if you dont remember it");
			setMessagesInRequest(req);
			return "redirect:/login";
		} else {
			addErrorMessage("This link is outdated, your account has not been unlocked");
			setMessagesInRequest(req);
			return "redirect:/login";
		}

	}

	@GetMapping("/login")
	public String loginPage(HttpServletRequest req) {
		setMessagesInRequest(req);
		return "login";
	}

	@PostMapping("/login") // add username/id to url?
	public String login(HttpServletRequest request, Model m) {

		String view = "";

		// grab username
		String username = request.getParameter("username");

		// grab password
		String pwd = request.getParameter("password");

		User u = LoginDAO.login(username);
		// check db (sql select statement)

		// if login valid, view = welcome

		// User doe not exist
		if (u == null) {
			addErrorMessage("Username is not registered");
			view = "login";
			// Password do not match username

		} else if (!LoginDAO.isActive(u.getUserID())) {
			addErrorMessage("this user is inactive, " + "please check email to reactivate your account");

		} else if (!passwordEncoder.matches(pwd, u.getPassword())) {
			LoginDAO.incrLoginAttempts(u.getUserID());
			// User exists and password matches
			// login attempts + 1.

			addErrorMessage("Incorrect password, try again");
			view = "login";

			if (LoginDAO.getLoginAttempts(u.getUserID()) > 3) {
				LoginDAO.toggleAccount(u.getUserID(), false);
				Mail mail = new Mail();

				addErrorMessage("Your account has been locked for too many login attempts");

				mail.sendMail(u, "accountlock", CodeGenerator.generateCode());
			}

		} else {
			request.getSession().setAttribute("userLoggedIn", u);
			view = "redirect:/";
			LoginDAO.resetLoginAttempts(u.getUserID());
			// login attempts = 0;
		}

		setMessagesInRequest(request);
		return view;
	}

	@PostMapping("/resetPass")
	public String passwordReset(HttpServletRequest request, Model m) {

		String email = request.getParameter("email");

		if (email != null) {
			User user = UserDAO.getUserByEmail(email);
			if (user.getUserID() != 0) {
				String code = CodeGenerator.generateCode();
				String encodedCode = passwordEncoder.encode(code);
				user.setPassword(encodedCode);
				UserDAO.update(user);
				Mail mail = new Mail();
				mail.sendMail(user, "reset", code);
				addSuccessMessage("A temporary password has been sent to your email address");
			} else {
				addErrorMessage("Invalid Email");
			}
		} else {
			addErrorMessage("Invalid Email");
		}

		setMessagesInRequest(request);
		return "login";
	}

	@PostMapping("/updateUserProfile")
	public String updateUserProfile(HttpServletRequest request, Model m) {

		int userId = Integer.parseInt(request.getParameter("id"));

		User user = UserDAO.getUserByUserId(userId);

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String bio = request.getParameter("bio");
		String newTags = request.getParameter("newTags");

		ArrayList<String> tags = user.getTags();
		ArrayList<String> keepTags = new ArrayList<String>();
		for (int i = 0; i < tags.size(); i++) {
			String temp = request.getParameter(tags.get(i));
			if (temp != null && temp.equals("on")) {
				keepTags.add(tags.get(i));
			}
		}

		if (firstName != null && !firstName.isEmpty()) {
			user.setFirstName(firstName);
		}
		if (lastName != null && !lastName.isEmpty()) {
			user.setLastName(lastName);
		}
		if (username != null && !username.isEmpty()) {
			user.setUsername(username);
		}
		if (password != null && !password.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(password);
			user.setPassword(encodedPassword);
		}
		if (bio != null && !bio.isEmpty()) {
			user.setBio(bio);
		}

		if (newTags != null && !newTags.isEmpty()) {
			String[] newTagsList = newTags.split(",");
			for (int i = 0; i < newTagsList.length; i++) {
				keepTags.add(newTagsList[i].trim().toUpperCase());
			}
		}

		user.setTags(keepTags);

		UserDAO.update(user);
		setMessagesInRequest(request);
		return "redirect:/profile?userId=" + user.getUserID() + "&content=Posts";
	}

	@PostMapping("/follow")
	public String followUser(HttpServletRequest request, Model m) {

		int userID = Integer.parseInt(request.getParameter("id"));

		int followedUserID = Integer.parseInt(request.getParameter("followed_userID"));

		UserDAO.followUser(userID, followedUserID);
		setMessagesInRequest(request);
		return "redirect:/profile?userId=" + followedUserID + "&content=Posts";
	}

	@PostMapping("/unfollow")
	public String unfollowUser(HttpServletRequest request, Model m) {

		int userID = Integer.parseInt(request.getParameter("id"));

		int followedUserID = Integer.parseInt(request.getParameter("followed_userID"));

		UserDAO.unfollowUser(userID, followedUserID);
		setMessagesInRequest(request);
		return "redirect:/profile?userId=" + followedUserID + "&content=Posts";
	}

}