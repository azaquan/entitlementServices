package com.mirum.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.*;
import java.util.*;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;


import com.mirum.DbTools;

public class PreSignIn extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String emailAddress="";
		String password="";
		emailAddress=request.getParameter("emailAddress");
		password=request.getParameter("password");	
		request.setAttribute("emailAddress",emailAddress);
		request.setAttribute("password",password);
		
		XML xml = new XMLDocument(
		  "<credentials><emailAddress>"+emailAddress+"</emailAddress><password>"+password+"</password></credentials>"
		);

      //response.setContentType("text/xml;charset=UTF-8");
      //PrintWriter out = response.getWriter();
		//out.println(xml.toString());
response.getWriter().write(xml.toString());
		response.sendRedirect("SignInWithCredentials");
		//RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("/test.jsp");
		//request.getRequestDispatcher("SignInWithCredentials").forward(request, response);
		//RequestDispatcher dispatcher=getServletContext().getRequestDispatcher("SignInWithCredentials");
		//dispatcher.include(request, response); 
		//return;
	}
}