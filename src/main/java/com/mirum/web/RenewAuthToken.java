package com.mirum.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import com.mirum.DbTools;

public class RenewAuthToken extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String authToken=request.getParameter("authToken");
		String newToken=DbTools.getNewToken(authToken);

      response.setContentType("text/xml");
      PrintWriter out = response.getWriter();
      String docType = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
      if(newToken.equals("")){
			out.println(docType +
				"HTTP 401 Unauthorized\n" +
				"Body:\n" +
				"<result httpResponseCode=”401” errorCode=””/>\n");
		}else{
			out.println(docType +
				"HTTP 200 OK\n" +
				"Body:\n" +
				"<result httpResponseCode=”200”>\n" +
				"<authToken>"+newToken+"<authToken>\n" +
				"</result>\n");
		}
	}
}