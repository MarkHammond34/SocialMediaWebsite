package edu.ben.DAOs;

import edu.ben.models.Comment;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

	private static NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(BasicDataSource ds) {
		jdbc = new NamedParameterJdbcTemplate(ds);
	}

	public static int insertComment(String c, int userID, int postID) {

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("comment", c);
		params.addValue("userID", userID);
		params.addValue("postID", postID);

		return jdbc.update("INSERT INTO comment (userID, postID, message) VALUES ( ?, ?, ?);", params);

	}

	public static int updateComment(String c, int commentID) {

		MapSqlParameterSource params = new MapSqlParameterSource();

		params.addValue("comment", c);
		params.addValue("commentID", commentID);

		return jdbc.update("UPDATE comment SET message = ? WHERE commentID = ?", params);

	}

	public static List<Comment> getCommentsByPostId(int id) {

		return jdbc.query("SELECT * from comment as c INNER JOIN user as u ON c.userID=u.userID WHERE postID= " + id,
				new RowMapper<Comment>() {
					public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
						Comment com = new Comment();
						com.setCommentId(rs.getInt("commentID"));
						com.setUserID(rs.getInt("userID"));
						com.setMessage(rs.getString("message"));
						com.setUserFirstName(rs.getString("first_name"));
						com.setUserLastName(rs.getString("last_name"));
						com.setLikeCount(rs.getInt("like_count"));
						return com;
					}
				});
	}

	public static int deleteComment(int commentid) {

		return jdbc.update("UPDATE comment SET message = ? WHERE commentID = ?");
	}

	public int addLikeToComment(int commentID) {

		int results = -1;

		DataSource ds;
		Connection con = null;
		PreparedStatement ps;

		try {

			ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "UPDATE comment SET like_count=like_count+1 WHERE commentID = ?; ";

			ps = con.prepareStatement(query);
			ps.setInt(1, commentID);
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

	public static void likeComment(int userID, int commentID) {
		Connection con = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "INSERT INTO comment_like " + "(userID, commentID) VALUES (?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, commentID);

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
	}

	public static boolean likesComment(int userID, int commentID) {
		Connection con = null;
		ResultSet rs = null;
		int result = 0;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "SELECT * FROM comment_like WHERE " + "userID=? AND commentID=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, commentID);
			rs = ps.executeQuery();

			while (rs.next()) {
				result++;
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

		return result > 0;
	}

	public static void unlikeComment(int userID, int commentID) {
		Connection con = null;

		try {

			DataSource ds = ConnectionPool.setUpPool();
			con = ds.getConnection();

			String query = "DELETE FROM comment_like " + "WHERE userID=? AND commentID=?";

			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, userID);
			ps.setInt(2, commentID);

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
	}
}