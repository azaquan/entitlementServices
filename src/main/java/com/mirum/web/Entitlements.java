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

public class Entitlements extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		String authToken=request.getParameter("authToken");
		List<String> products = new ArrayList<String>();
		products=DbTools.getProducts(authToken);
		StringBuffer xmlString=new StringBuffer("HTTP 200 OK\nBody:\n<result httpResponseCode=\"200\">\n<entitlements>\n");
		Iterator<String> iterator = products.iterator();
		while(iterator.hasNext()){
			String product = iterator.next();
			xmlString.append("<productId>"+product+"</productId>\n");
		}
		xmlString.append("<entitlements>\n");
		xmlString.append("</result>\n");
      response.setContentType("text/xml");
      PrintWriter out = response.getWriter();
      String docType = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
      System.out.println("@@@ products->"+xmlString.length());
      if(xmlString.length()>0){
			out.println(docType + xmlString.toString());
		}else{
			out.println(docType +
				"HTTP 401 Unauthorized\n" +
				"Body:\n" +
				"<result httpResponseCode=\"401\" errorCode=\"\"/>\n");
		}
	}
}