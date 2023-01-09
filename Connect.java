package com.exam;

import java.sql.*;

public class Connect {
	private static Connect instance;
	private Connection connection;
	private Statement statement;
	
	private Connect() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ac","root","");
			statement = this.connection.createStatement();
			System.out.println("Connection Success");
		} catch (ClassNotFoundException ex) {
			System.err.println("Connection Error : " + ex.getMessage());
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public static Connect getInstance() throws SQLException {
		if(instance == null) {
			instance = new Connect();
		} else if (instance.getConnection().isClosed()) {
			instance = new Connect();
		}
		return instance;
	}
	
	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't execute query!");
		}
		return rs;
	}

	public void executeUpdate(String query) {
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can't execute update!");
		}
	}
	
}
