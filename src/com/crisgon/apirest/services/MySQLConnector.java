package com.crisgon.apirest.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
	
	public static Connection connectMySQL() {

		// Conectar con el drive
		// Class.forName("");

		String usuario = "root";
		String contraseña = "";

		String urlConnection = "jdbc:mysql://localhost:3306/autocartas?serverTimezone=UTC"; // Puerto por defecto:
																									// 3306
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(urlConnection, usuario, contraseña);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}
	
}
