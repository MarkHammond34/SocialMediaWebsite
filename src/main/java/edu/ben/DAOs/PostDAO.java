package edu.ben.DAOs;

import edu.ben.connection.ConnectionPool;
import edu.ben.models.Post;
import edu.ben.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PostDAO {

	public static void insert(Post post) {

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "INSERT INTO post(userID, title, description, link, image_path) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps;

			ps = con.prepareStatement(query);
			ps.setInt(1, post.getUserID());
			ps.setString(2, post.getTitle());
			ps.setString(3, post.getDescription());
			ps.setString(4, post.getLink());
			ps.setString(5, post.getImagePath());
			ps.executeUpdate();
			ps.close();

			int postId = getPostIdByUserIdAndTitle(post.getUserID(), post.getTitle());
			TagDAO.insertTagList(postId, post.getTags());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void insertAsDraft(Post post) {

		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "INSERT INTO post(userID, title, description, link, draft) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps;

			ps = con.prepareStatement(query);
			ps.setInt(1, post.getUserID());
			ps.setString(2, post.getTitle());
			ps.setString(3, post.getDescription());
			ps.setString(4, post.getLink());
			ps.setInt(5, 1);
			ps.executeUpdate();
			ps.close();

			int postId = getPostIdByUserIdAndTitle(post.getUserID(), post.getTitle());
			TagDAO.insertTagList(postId, post.getTags());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void update(Post post) {
		DataSource ds;
		Connection con = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE post SET " + "title = ?, " + "description = ?, " + "link = ?, draft = ? "
					+ "WHERE postID = ?";

			PreparedStatement ps;

			ps = con.prepareStatement(query);
			ps.setString(1, post.getTitle());
			ps.setString(2, post.getDescription());
			ps.setString(3, post.getLink());
			ps.setInt(4, post.isDraft() ? 1 : 0);
			ps.setInt(5, post.getPostID());
			ps.executeUpdate();
			ps.close();

			TagDAO.deleteFromPostTag(post.getPostID());
			TagDAO.insertTagList(post.getPostID(), post.getTags());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Removes a post by setting active equal to 0 or false.
	 * 
	 * @param postID
	 *            id of post being deactivating
	 * @return results integer representing the results of the query
	 */
	public int remove(int postID) {

		int results = -1;

		DataSource ds;
		Connection con = null;
		PreparedStatement ps;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE post SET active = 0 WHERE postID = ?; ";
			query += "UPDATE comment SET active = 0 WHERE postID = ?;";

			ps = con.prepareStatement(query);
			ps.setInt(1, postID);
			results = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return results;

	}

	public static ArrayList<Post> getPostsMatchingSearch(String search) {

		ArrayList<Post> posts = new ArrayList<>(100);

		String sql = "SELECT p.postID, p.userID, p.title, p.description, p.link, p.image_path, "
				+ "p.like_count, p.view_count, p.tack_count, p.active, p.created_on, u.first_name, "
				+ "u.last_name FROM post as p INNER JOIN user as u on p.userID=u.userID WHERE p.title LIKE '%" + search
				+ "%' OR p.description LIKE '%" + search + "%' AND p.active=1 AND p.draft=0;";

		// String sql = "SELECT * FROM post WHERE title LIKE %" + search + "% OR desc
		// LIKE %" + search + "%;";

		// System.out.println(sql);

		String title, description, link, imagePath, userFirstName, userLastName;
		int postID, userID, likeCount, viewCount, tackCount;
		boolean active;
		Timestamp createdOn;

		ResultSet rs = null;
		Connection con = null;

		DataSource ds;
		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				Post post = new Post();

				title = rs.getString("p.title");
				description = rs.getString("p.description");
				link = rs.getString("p.link");
				imagePath = rs.getString("p.image_path");
				postID = rs.getInt("p.postID");
				active = rs.getBoolean("p.active");
				likeCount = rs.getInt("p.like_count");
				viewCount = rs.getInt("p.view_count");
				tackCount = rs.getInt("p.tack_count");
				createdOn = rs.getTimestamp("p.created_on");

				userID = rs.getInt("p.userID");
				userFirstName = rs.getString("u.first_name");
				userLastName = rs.getString("u.last_name");

				post = new Post(postID, userID, title, description, link, imagePath, active, false, likeCount,
						viewCount, tackCount, createdOn);
				post.setUserFistName(userFirstName);
				post.setUserLastName(userLastName);

				post.setUserUsername(UserDAO.getUserByUserId(userID).getUsername());

				// /** Get tags for post */
				// post.setTags(TagDAO.getTagsByPostID(postID));
				// /** Get comments for post */
				// post.setComments(CommentDAO.getCommentsByPostId(postID));

				posts.add(post);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return posts;
	}

	/**
	 * Returns all the posts from x amount of hours ago. X is passed in as
	 * numOfHours
	 * 
	 * @param numOfHours
	 *            number of hours you want to pull posts from
	 * @return posts list of posts from the last x amount of days.
	 */
	public static ArrayList<Post> getPostsNumOfHours(int numOfHours) {

		ArrayList<Post> posts = new ArrayList<Post>();

		Timestamp oldTimestamp = new Timestamp(System.currentTimeMillis() - (3600000 * numOfHours));

		String title, description, link, imagePath, userFirstName, userLastName;
		int postID, userID, likeCount, viewCount, tackCount;
		boolean active;
		Timestamp createdOn;

		ResultSet rs = null;
		Connection con = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String postSelect = "SELECT p.postID, p.userID, p.title, p.description, p.link, p.image_path, "
					+ "p.like_count, p.view_count, p.tack_count, p.active, p.created_on, u.first_name, "
					+ "u.last_name FROM post as p INNER JOIN user as u on p.userID=u.userID WHERE p.created_on >= '"
					+ oldTimestamp.toString() + "' AND p.active=1 AND p.draft=0;";

			PreparedStatement ps = con.prepareStatement(postSelect);
			rs = ps.executeQuery();

			while (rs.next()) {

				title = rs.getString("title");
				description = rs.getString("description");
				link = rs.getString("link");
				imagePath = rs.getString("image_path");
				postID = rs.getInt("postID");
				active = rs.getBoolean("active");
				likeCount = rs.getInt("like_count");
				viewCount = rs.getInt("view_count");
				tackCount = rs.getInt("tack_count");
				createdOn = rs.getTimestamp("created_on");

				userID = rs.getInt("userID");
				userFirstName = rs.getString("first_name");
				userLastName = rs.getString("last_name");

				Post post = new Post(postID, userID, title, description, link, imagePath, active, false, likeCount,
						viewCount, tackCount, createdOn);
				post.setUserFistName(userFirstName);
				post.setUserLastName(userLastName);

				// /** Get tags for post */
				// post.setTags(TagDAO.getTagsByPostID(postID));
				// /** Get comments for post */
				// post.setComments(CommentDAO.getCommentsByPostId(postID));
				posts.add(post);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return posts;
	}

	public static ArrayList<Post> getRelevantPostsByUserID(int userID) {

		ArrayList<Post> posts = new ArrayList<Post>();

		String title, description, link, imagePath, userFirstName, userLastName;
		int postID, likeCount, viewCount, tackCount;
		boolean active;
		Timestamp createdOn;

		ResultSet rs = null;
		Connection con = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String postSelect = "SELECT p.postID, p.userID, p.title, p.description, p.link, p.image_path, p.like_count, "
					+ "p.view_count, p.tack_count, p.active, p.created_on, u.first_name, u.last_name FROM post as p INNER JOIN"
					+ " user as u on p.userID=u.userID INNER JOIN post_tag as pt ON p.postID=pt.postID WHERE "
					+ "p.active=1 AND tag IN (SELECT tag.tag FROM post_tag JOIN tag ON tag.tag = post_tag.tag WHERE "
					+ "postID IN (SELECT postID FROM user_like WHERE userID = ? UNION SELECT postID FROM user_tack WHERE "
					+ "userID = ?) ORDER BY tag.count) OR tag IN (SELECT tag.similar_tag FROM post_tag JOIN tag ON "
					+ "tag.tag = post_tag.tag WHERE postID IN (SELECT postID FROM user_like WHERE userID = ? UNION SELECT "
					+ "postID FROM user_tack WHERE userID = ?) ORDER BY tag.count) GROUP BY p.postID LIMIT 50;";

			PreparedStatement ps = con.prepareStatement(postSelect);
			ps.setInt(1, userID);
			ps.setInt(2, userID);
			ps.setInt(3, userID);
			ps.setInt(4, userID);

			rs = ps.executeQuery();

			while (rs.next()) {

				title = rs.getString("p.title");
				description = rs.getString("p.description");
				link = rs.getString("p.link");
				imagePath = rs.getString("p.image_path");
				postID = rs.getInt("p.postID");
				active = rs.getBoolean("p.active");
				likeCount = rs.getInt("p.like_count");
				viewCount = rs.getInt("p.view_count");
				tackCount = rs.getInt("p.tack_count");
				createdOn = rs.getTimestamp("p.created_on");

				userID = rs.getInt("p.userID");
				userFirstName = rs.getString("u.first_name");
				userLastName = rs.getString("u.last_name");

				Post post = new Post(postID, userID, title, description, link, imagePath, active, false, likeCount,
						viewCount, tackCount, createdOn);
				post.setUserFistName(userFirstName);
				post.setUserLastName(userLastName);

				// /** Get tags for post */
				// post.setTags(TagDAO.getTagsByPostID(postID));
				// /** Get comments for post */
				// post.setComments(CommentDAO.getCommentsByPostId(postID));
				posts.add(post);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return posts;
	}

	public static int getPostIdByUserIdAndTitle(int userId, String title) {
		int postId = 0;

		Connection con = null;
		ResultSet rs = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT postID FROM post WHERE userID='" + userId + "' and title='" + title + "';";

			PreparedStatement ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				postId = rs.getInt("postID");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return postId;
	}

	public static Post getPostByPostId(int id) {
		Post post = new Post();

		String title, description, link, imagePath, userFirstName, userLastName;
		int postID, userID, likeCount, viewCount, tackCount;
		boolean active;
		Timestamp createdOn;

		ResultSet rs = null;
		Connection con = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String postSelect = "SELECT p.postID, p.userID, p.title, p.description, p.link, "
					+ "p.image_path, p.like_count, p.view_count, p.tack_count, p.active, p.draft, "
					+ "p.created_on, u.first_name, u.last_name FROM post as p INNER JOIN user "
					+ "as u on p.userID=u.userID WHERE p.postID=?;";

			PreparedStatement ps = con.prepareStatement(postSelect);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {

				title = rs.getString("p.title");
				description = rs.getString("p.description");
				link = rs.getString("p.link");
				imagePath = rs.getString("p.image_path");
				postID = rs.getInt("p.postID");
				active = rs.getBoolean("p.active");
				boolean draft = rs.getBoolean("p.draft");
				likeCount = rs.getInt("p.like_count");
				viewCount = rs.getInt("p.view_count");
				tackCount = rs.getInt("p.tack_count");
				createdOn = rs.getTimestamp("p.created_on");

				userID = rs.getInt("p.userID");
				userFirstName = rs.getString("u.first_name");
				userLastName = rs.getString("u.last_name");

				post = new Post(postID, userID, title, description, link, imagePath, active, false, likeCount,
						viewCount, tackCount, createdOn);
				post.setUserFistName(userFirstName);
				post.setUserLastName(userLastName);
				post.setDraft(draft);

				post.setUserUsername(UserDAO.getUserByUserId(userID).getUsername());

				/** Get tags for post */
				post.setTags(TagDAO.getTagsByPostID(postID));
				/** Get comments for post */
				post.setComments(CommentDAO.getCommentsByPostId(postID));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return post;
	}

	public static boolean userLikedPost(int postID, int userID) {
		int results = 0;
		DataSource ds;
		Connection con = null;
		PreparedStatement ps;
		ResultSet rs = null;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String select = "SELECT * FROM user_like WHERE postID = ? AND userID = ?";

			ps = con.prepareStatement(select);
			ps.setInt(1, postID);
			ps.setInt(2, userID);
			rs = ps.executeQuery();

			while (rs.next()) {
				results++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results > 0;
	}

	public static int addLikeToPost(int postID, int userID) {

		int results = -1;

		DataSource ds;
		Connection con = null;
		PreparedStatement ps;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE post SET like_count=like_count+1 WHERE postID = ?; ";
			ps = con.prepareStatement(query);
			ps.setInt(1, postID);
			results = ps.executeUpdate();

			query = "INSERT INTO user_like (userID, postID) VALUES (?, ?);";
			ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, postID);
			results = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return results;

	}

	public static int removeLikeFromPost(int postID, int userID) {

		int results = -1;

		DataSource ds;
		Connection con = null;
		PreparedStatement ps;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE post SET like_count=like_count-1 WHERE postID = ? ";
			ps = con.prepareStatement(query);
			ps.setInt(1, postID);
			results = ps.executeUpdate();

			query = "DELETE FROM user_like WHERE userID=? AND postID=?";
			ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, postID);
			results += ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return results;

	}

	public static int tackPost(int userID, int postID) {

		int results = -1;

		DataSource ds;
		Connection con = null;
		PreparedStatement ps;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE post SET tack_count=tack_count+1 WHERE postID = ?; ";

			ps = con.prepareStatement(query);
			ps.setInt(1, postID);

			results = ps.executeUpdate();

			String sql = "INSERT INTO user_tack (userID, postID) VALUES (?, ?) ON DUPLICATE KEY UPDATE userID=?, postID=?;";
			ps = con.prepareStatement(sql);
			ps.setInt(1, userID);
			ps.setInt(2, postID);
			ps.setInt(3, userID);
			ps.setInt(4, postID);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return results;
	}

	public static ArrayList<User> getUsersWhoLikedPost(int postID) {
		ResultSet rs = null;
		Connection con = null;
		ArrayList<User> users = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();
			String select = "SELECT * FROM user_like WHERE postID=?";
			PreparedStatement ps = con.prepareStatement(select);
			ps.setInt(1, postID);

			rs = ps.executeQuery();
			users = new ArrayList<>();

			while (rs.next()) {
				User u = UserDAO.getUserByUserId(rs.getInt("userID"));
				users.add(u);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	public static ArrayList<Post> getPostsMatchingTags(String tag) {

		ArrayList<Post> posts = new ArrayList<>(100);

		String sql = "SELECT p.postID, p.userID, p.title, p.description, p.link, p.image_path, p.like_count, p.view_count, p.tack_count, p.active, p.created_on, u.first_name, u.last_name FROM post as p INNER JOIN user as u on p.userID=u.userID WHERE p.postID IN (SELECT pt.postID FROM post_tag as pt WHERE pt.tag='"
				+ tag + "');";

		// System.out.println(sql);

		String title, description, link, imagePath, userFirstName, userLastName;
		int postID, userID, likeCount, viewCount, tackCount;
		boolean active;
		Timestamp createdOn;

		ResultSet rs = null;
		Connection con = null;

		DataSource ds;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {

				Post post = new Post();

				title = rs.getString("p.title");
				description = rs.getString("p.description");
				link = rs.getString("p.link");
				imagePath = rs.getString("p.image_path");
				postID = rs.getInt("p.postID");
				active = rs.getBoolean("p.active");
				likeCount = rs.getInt("p.like_count");
				viewCount = rs.getInt("p.view_count");
				tackCount = rs.getInt("p.tack_count");
				createdOn = rs.getTimestamp("p.created_on");

				userID = rs.getInt("p.userID");
				userFirstName = rs.getString("u.first_name");
				userLastName = rs.getString("u.last_name");

				post = new Post(postID, userID, title, description, link, imagePath, active, false, likeCount,
						viewCount, tackCount, createdOn);
				post.setUserFistName(userFirstName);
				post.setUserLastName(userLastName);

				post.setUserUsername(UserDAO.getUserByUserId(userID).getUsername());

				/** Get tags for post */
				post.setTags(TagDAO.getTagsByPostID(postID));
				// /** Get comments for post */
				// post.setComments(CommentDAO.getCommentsByPostId(postID));

				posts.add(post);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return posts;
	}

	public static Post getPostByPostIdNoComments(int id) {
		Post post = new Post();

		String title, description, link, imagePath, userFirstName, userLastName;
		int postID, userID, likeCount, viewCount, tackCount;
		boolean active;
		Timestamp createdOn;

		ResultSet rs = null;
		Connection con = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String postSelect = "SELECT p.postID, p.userID, p.title, p.description, p.link, "
					+ "p.image_path, p.like_count, p.view_count, p.tack_count, p.active, "
					+ "p.created_on, u.first_name, u.last_name FROM post as p INNER JOIN user "
					+ "as u on p.userID=u.userID WHERE p.postID=? AND p.active=1;";

			PreparedStatement ps = con.prepareStatement(postSelect);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {

				title = rs.getString("p.title");
				description = rs.getString("p.description");
				link = rs.getString("p.link");
				imagePath = rs.getString("p.image_path");
				postID = rs.getInt("p.postID");
				active = rs.getBoolean("p.active");
				likeCount = rs.getInt("p.like_count");
				viewCount = rs.getInt("p.view_count");
				tackCount = rs.getInt("p.tack_count");
				createdOn = rs.getTimestamp("p.created_on");

				userID = rs.getInt("p.userID");
				userFirstName = rs.getString("u.first_name");
				userLastName = rs.getString("u.last_name");

				post = new Post(postID, userID, title, description, link, imagePath, active, false, likeCount,
						viewCount, tackCount, createdOn);
				post.setUserFistName(userFirstName);
				post.setUserLastName(userLastName);

				post.setUserUsername(UserDAO.getUserByUserId(userID).getUsername());

				/** Get tags for post */
				post.setTags(TagDAO.getTagsByPostID(postID));
				/** Get comments for post */
				// post.setComments(CommentDAO.getCommentsByPostId(postID));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return post;
	}

}