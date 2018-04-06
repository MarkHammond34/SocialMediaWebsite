package edu.ben.controllers;

import edu.ben.DAOs.CommentDAO;
import edu.ben.DAOs.PostDAO;
import edu.ben.models.Post;
import edu.ben.models.User;
import edu.ben.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class PostController extends BaseController {

	@PostMapping("/toggleLike")
	public String toggleLike(HttpServletRequest req) {
		int postID = Integer.parseInt(req.getParameter("postID"));
		String action = req.getParameter("action");
		int commentID = Integer.parseInt(req.getParameter("commentID"));

		User u = (User) (req.getSession().getAttribute("userLoggedIn"));
		int userID = u.getUserID();

		if (action.equals("like")) {
			CommentDAO.likeComment(userID, commentID);

		} else if (action.equals("unlike")) {
			CommentDAO.unlikeComment(userID, commentID);
		}

		return "redirect:/post?postId=" + postID;
	}

	@GetMapping("/post")
	public String viewPost(HttpServletRequest request, Model m) {

		int id = Integer.parseInt(request.getParameter("postId"));

		Post post = PostDAO.getPostByPostId(id);
		m.addAttribute("post", post);

		User u = (User) request.getSession().getAttribute("userLoggedIn");
		if (u != null) {
			// find out if this user liked this post
			boolean liked = PostDAO.userLikedPost(id, u.getUserID());
			m.addAttribute("liked", liked);
		}

		// get users who liked this post and preload the page
		ArrayList<User> usersLiked = PostDAO.getUsersWhoLikedPost(id);
		m.addAttribute("usersLiked", usersLiked);

		return "post";
	}

	@PostMapping("/createPost")
	public String createPost(HttpServletRequest request, @RequestParam("file") MultipartFile file, Model m)
			throws IOException, ServletException {

		Post post = new Post();

		User user = (User) request.getSession().getAttribute("userLoggedIn");
		post.setUserID(user.getUserID());

		String title = request.getParameter("title");
		String link = request.getParameter("link");
		String tags = request.getParameter("tags");
		String description = request.getParameter("description");
		String imagePath = "";
		String submit = request.getParameter("submit");

		// save image to drive and get image name
		try {
			imagePath = FileUploadUtil.uploadImage(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<String> tagsList = new ArrayList<String>();
		boolean allFields = true;

		if (tags != null && !tags.isEmpty()) {

			String[] tagsArray = tags.split(",");

			for (int i = 0; i < tagsArray.length; i++) {
				tagsList.add(tagsArray[i].trim().toUpperCase());
			}
		} else {
			allFields = false;
		}

		post.setTags(tagsList);

		if (title != null && !title.isEmpty()) {
			post.setTitle(title);
		} else {
			allFields = false;
		}
		if (link != null && !link.isEmpty()) {
			post.setLink(link);
		}
		if (description != null && !description.isEmpty()) {
			post.setDescription(description);
		} else {
			allFields = false;
		}

		// set image path
		if (imagePath != null && !imagePath.isEmpty()) {
			post.setImagePath(imagePath);
		} else {
			allFields = false;

		}

		// Part file = request.getPart("file");

		// MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)
		// request;

		// MultipartFile multipartFile = multipartRequest.getFile("file");

		// post.setImagePath(FileUploadUtil.uploadFileHandler(multipartFile.getName(),
		// multipartFile));

		if (allFields && submit.equals("Post")) {
			PostDAO.insert(post);
			addSuccessMessage(post.getTitle() + " successfully posted!");
		} else if (allFields && submit.equals("Draft")) {
			PostDAO.insertAsDraft(post);
			addSuccessMessage(post.getTitle() + " successfully saved as a draft!");
		} else {
			addErrorMessage("Please fill out all required fields");
		}

		setMessagesInRequest(request);
		return "redirect:home";
	}

	@PostMapping("/updatePost")
	public String editPost(HttpServletRequest request, Model m) {

		int postId = Integer.parseInt(request.getParameter("postId"));

		Post post = PostDAO.getPostByPostId(postId);

		String title = request.getParameter("title");
		String link = request.getParameter("link");
		String newTags = request.getParameter("newTags");
		String description = request.getParameter("description");
		String submit = request.getParameter("submit");

		ArrayList<String> tags = post.getTags();
		ArrayList<String> keepTags = new ArrayList<String>();
		for (int i = 0; i < tags.size(); i++) {
			String temp = request.getParameter(tags.get(i));
			if (temp != null && temp.equals("on")) {
				keepTags.add(tags.get(i));
			}
		}

		if (title != null && !title.isEmpty()) {
			post.setTitle(title);
		}
		if (link != null && !link.isEmpty()) {
			post.setLink(link);
		}
		if (description != null && !description.isEmpty()) {
			post.setDescription(description);
		}

		if (newTags != null && !newTags.isEmpty()) {
			String[] newTagsList = newTags.split(",");
			for (int i = 0; i < newTagsList.length; i++) {
				keepTags.add(newTagsList[i].trim().toUpperCase());
			}
		}

		post.setTags(keepTags);

		if (post.isDraft() && submit.equals("postDraft")) {
			post.setDraft(false);
		}

		PostDAO.update(post);

		return "redirect:/post?postId=" + post.getPostID();
	}

	@PostMapping("/editComment")
	public String editComment(String comment, String commentID, String postID) {

		CommentDAO.updateComment(comment, Integer.parseInt(commentID));

		return "redirect:post?postId=" + postID;
	}

	@PostMapping("/newComment")
	public String newComment(String comment, String postID, String userID) {

		int postid = Integer.parseInt(postID);
		int userid = Integer.parseInt(userID);

		CommentDAO.insertComment(comment, userid, postid);
		return "redirect:post?postId=" + postID;
	}

	@PostMapping("/deleteComment")
	public String deleteComment(String postID, String commentID) {

		int commentid = Integer.parseInt(commentID);
		CommentDAO.deleteComment(commentid);
		return "redirect:post?postId=" + postID;
	}

	@PostMapping("/tackPost")
	public String tackPost(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("userLoggedIn");
		int postID = Integer.parseInt(request.getParameter("postID"));

		if (user == null) {
			addErrorMessage("Must Login To Tack Posts");
		} else {
			if (PostDAO.tackPost(user.getUserID(), postID) == 1) {
				System.out.println("TACKED");
				addSuccessMessage("Post Tacked");
			} else if (PostDAO.tackPost(user.getUserID(), postID) == 0) {
				addWarningMessage("Post Already Tacked");
			}
		}

		setMessagesInRequest(request);
		return "redirect:/home";
	}

	@PostMapping("/likePost")
	public String likePost(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("userLoggedIn");
		int postID = 0, userID = 0;

		if (user == null) {
			addWarningMessage("Must Login To Like Posts");
		} else {

			userID = user.getUserID();
			postID = Integer.parseInt(request.getParameter("postID"));
			boolean liked = Boolean.parseBoolean(request.getParameter("liked"));

			if (liked) {
				// then, unlike it
				PostDAO.removeLikeFromPost(postID, userID);
				addSuccessMessage("Post Liked");
			} else if (!liked) {
				// the user wants to like this post, so like it
				PostDAO.addLikeToPost(postID, userID);
				addSuccessMessage("Post Unliked");
			}
		}

		setMessagesInRequest(request);
		return "redirect:/post?postId=" + postID;
	}
}
