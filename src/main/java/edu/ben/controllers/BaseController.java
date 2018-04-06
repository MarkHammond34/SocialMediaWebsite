package edu.ben.controllers;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class BaseController {

	private static ArrayList<String> errorMessages = new ArrayList<String>();
	private static ArrayList<String> warningMessages = new ArrayList<String>();
	private static ArrayList<String> successMessages = new ArrayList<String>();

	public void setMessagesInRequest(HttpServletRequest request) {
		request.setAttribute("errorMessages", errorMessages);
		request.setAttribute("warningMessages", warningMessages);
		request.setAttribute("successMessages", successMessages);
	}

	public void addErrorMessage(String error) {
		errorMessages.add(error);
	}

	public void addSuccessMessage(String suc) {
		successMessages.add(suc);
	}

	public void addWarningMessage(String warning) {
		warningMessages.add(warning);
	}

	public ArrayList<String> getErrorMessages() {
		return errorMessages;
	}

	public ArrayList<String> getWarningMessages() {
		return warningMessages;
	}

	public ArrayList<String> getSuccessMessages() {
		return successMessages;
	}

	public void clearMessages() {
		if (successMessages != null) {
			successMessages.clear();
		}
		if (warningMessages != null) {
			warningMessages.clear();
		}
		if (errorMessages != null) {
			errorMessages.clear();
		}
	}

}
