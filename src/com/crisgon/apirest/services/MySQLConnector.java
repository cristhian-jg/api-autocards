package com.crisgon.apirest.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

	public static Connection connectMySQL() {

		Connection connection = null;
		String usuario = "root";
		String contraseña = "interfaces";
		String urlConnection = "jdbc:mysql://localhost:3306/autocartas?serverTimezone=UTC"; // Puerto por defecto: 3306																			 
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(urlConnection, usuario, contraseña);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;
	}

}
