package com.mirum;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.List;
import java.util.ArrayList;

public class DbTools {
	@Resource(name="jdbc/Mirum")
	private static DataSource ds;
	
	public static Connection connect(){
		Connection conn = null;
		try{		
			Context ctx = new InitialContext();
			ds= (DataSource)ctx.lookup("java:comp/env/jdbc/Mirum");
			conn= ds.getConnection();
		}catch(SQLException e){
			System.out.println("@@ DbTools.connect sql error: "+e);
		}catch(Exception e){
			System.out.println("@@ DbTools.connect error: "+e);
		}
		return conn;
	}
	
	public static String getAuthentication(String emailAddress, String password){
		String authToken="";
		Statement stmt;
		ResultSet rs;
		Long record;
		String query = "select count(1) as records from customer where emailAddress='"+emailAddress+"' and password='"+password+"'";
		try{		
			stmt = connect().createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()){
				record=rs.getLong("records");
				if(record>0){
					authToken=getToken();
					if(!updateCustomer(authToken, emailAddress, password)){
						authToken="";
					}
				}
			}
		}catch(SQLException e){
			authToken="";
			System.out.println("@@ DbTools.test sql error: "+e);
		}catch(Exception e){
			authToken="";
			System.out.println("@@ DbTools.test error: "+e);
		}
		return authToken;
	}
	
	public static boolean updateCustomer(String token,String emailAddress, String password){
		boolean response=false;
		String query = "update customer set authToken='"+token+"' where emailAddress= ? and password= ?";
		try{		
			PreparedStatement pstmt = connect().prepareStatement(query);
			pstmt.setString(1,emailAddress);
			pstmt.setString(2,password);
			pstmt.executeUpdate();
			response=true;
		}catch(SQLException e){
			System.out.println("@@ DbTools.updateCustomer sql error: "+e);
		}catch(Exception e){
			System.out.println("@@ DbTools.updateCustomer error: "+e);
		}
		return response;
	}
	
	public static String getToken(){
		String token="";
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		token = bytes.toString();
		return token;
	}
	
	public static String getNewToken(String oldToken){
		Long record;
		String newToken=getToken();
		String query = "update customer set authToken= ? where authToken= ?";
		try{		
			PreparedStatement pstmt = connect().prepareStatement(query);
			pstmt.setString(1,newToken);
			pstmt.setString(2,oldToken);
			pstmt.executeUpdate();
		}catch(SQLException e){
			newToken="";
			System.out.println("@@ DbTools.test sql error: "+e);
		}catch(Exception e){
			newToken="";
			System.out.println("@@ DbTools.test error: "+e);
		}
		return newToken;
	}
	
	public static List<String> getProducts(String token){
		List<String> products = new ArrayList<String>();
		Statement stmt;
		ResultSet rs;
		Long record;
		String newToken=getToken();
		String query = "select product_id from customer c, entitlement e " +
			"where e.customer_id=c.id and c.authToken='"+token+"'";
		try{			
			stmt = connect().createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()){
				String product=rs.getString("product_id");
				products.add(product);
			}
		}catch(SQLException e){
			System.out.println("@@ DbTools.test sql error: "+e);
		}catch(Exception e){
			System.out.println("@@ DbTools.test error: "+e);
		}
		return products;
	}
	
	public static boolean verifyProduct(String token, String productId){
		boolean response=false;
		Statement stmt;
		ResultSet rs;
		Long record;
		String newToken=getToken();
		String query = "select product_id from customer c, entitlement e " +
			"where e.customer_id=c.id and c.authToken='"+token+"' and e.product_id='"+productId+"'";
		try{			
			stmt = connect().createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()){
				response=true;
			}
		}catch(SQLException e){
			System.out.println("@@ DbTools.test sql error: "+e);
		}catch(Exception e){
			System.out.println("@@ DbTools.test error: "+e);
		}
		return response;
	}
}