package com.mirum.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.StringBuffer;

import com.mirum.DbTools;

public class VerifyEntitlement extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String authToken=request.getParameter("authToken");
		String productId=request.getParameter("productId");
		boolean isEntitled=DbTools.verifyProduct(authToken, productId);
      response.setContentType("text/xml");
      PrintWriter out = response.getWriter();
      String docType = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

      if(isEntitled){
			out.println(docType + 
				"HTTP 200 OK\n" +
				"Body:\n" +
				"<result httpResponseCode=\"200\">\n" +
				"<entitled>true</entitled>\n" +
				"</result>\n");
		}else{
			out.println(docType +
				"HTTP 200 OK\n" +
				"Body:\n" +
				"<result httpResponseCode=\"200\">\n" +
				"<entitled>false</entitled>\n" +
				"</result>\n");
		}
	}
}