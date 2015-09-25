package com.mirum.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import com.mirum.DbTools;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;

public class SignInWithCredentials extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String emailAddress="";
		String password="";
		String xmlResponse="";
		String credentials=request.getParameter("credentials");
		try{
			XML xml=new XMLDocument(credentials);
			emailAddress=xml.xpath("//emailAddress/text()").get(0);
			System.out.println("@@ emailAddress->"+emailAddress);
			password=xml.xpath("//password/text()").get(0);
			System.out.println("@@ password->"+password);
		}catch(Exception e){
			System.out.println("mirum error->"+e);
		}

		String authToken=DbTools.getAuthentication(emailAddress,password);
      PrintWriter out = response.getWriter();
      String docType = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
      if(authToken.equals("")){
			out.println(docType +
				"HTTP 401 Unauthorized\n" +
				"Body:\n" +
				"<result httpResponseCode=”401” errorCode=””/>\n");
		}else{
			out.println(docType +
				"HTTP 200 OK\n" +
				"Body:\n" +
				"<result httpResponseCode=”200”>\n" +
				"<authToken>"+authToken+"<authToken>\n" +
				"</result>\n");
		}
	}
}