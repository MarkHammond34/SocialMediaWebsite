package edu.ben.util;

import edu.ben.DAOs.LoginDAO;
import edu.ben.models.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

	public Mail() {
	}

	public String sendMail(User user, String type, String code) {

		String error = "";

		final String username = "cmsctestemail@gmail.com";
		final String password = "code4days";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cmsc375testemail@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
			message.setSubject(subject(type));
			message.setText(message(user, type, code));

			Transport.send(message);

//			System.out.println("Done");

		} catch (MessagingException e) {
			error = "email failed to send";
		}

		return error;
	}

	public String subject(String type) {
		if (!type.equals("accountlock")) {
			return "HoneyComb Password Reset";
		} else {
			return "HoneyComb Unlock Account";
		}
	}

	public String message(User user, String type, String code) {
		if (!type.equals("accountlock")) {
		return user.getFirstName() + " " + user.getLastName() + ","
				+ "\n\n A password reset has been requested for your account. "
				+ "Please use the following code as a temporary password: " + code
				+ ".  You may reset your password once you've logged in with the temporary password."
				+ "\n\n Best," + "\n HoneyComb Development Team ";
		} else {
			LoginDAO.setCode(user.getUserID(), code);
			return user.getFirstName() + " " + user.getLastName() + ","
				+ "\n\n You're account has recently been locked due to too many "
				+ "login in attempts. Please unlock your account by going to the following url in your "
				+ "web browser. http://localhost:8080/HoneyCombv3/unlock?code=" + code + "&userID=" + user.getUserID() + "  .\n\n Best," + "\n HoneyComb Development Team ";
		}
	}

}
