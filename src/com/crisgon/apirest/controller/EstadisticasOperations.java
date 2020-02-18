package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.crisgon.apirest.model.Estadistica;

public class EstadisticasOperations {

	private static Connection connection = MySQLConnector.getConnection();

	public static ArrayList<Estadistica> getAll() {

		ArrayList<Estadistica> estadisticas;

		Statement statement;
		ResultSet resultSet;
		Estadistica estadistica;

		int id;
		String jugador;
		int ganadas;
		int perdidas;
		int empatadas;

		estadisticas = new ArrayList<>();

		try {

			String query = "SELECT * FROM estadisticas";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {

				id = resultSet.getInt("id");
				jugador = resultSet.getString("jugador");
				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");
				
				estadistica = new Estadistica(id, jugador, ganadas, perdidas, empatadas);

				estadisticas.add(estadistica);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return estadisticas;
	}
	
	public static Estadistica getEstadistica(int id) {
		Estadistica estadistica = null;
		Statement statement;
		ResultSet resultSet;

		String jugador;
		int ganadas;
		int perdidas;
		int empatadas;
		
		String query = "SELECT * FROM estadisticas WHERE id = '" + id + "'";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				jugador = resultSet.getString("jugador");
				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");

				estadistica = new Estadistica(id, jugador, ganadas, perdidas, empatadas);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return estadistica;
	}
	
	public static boolean addEstadistica(Estadistica estadistica) {
		

		Statement statement;
		boolean validado = false;

		if (estadistica.getId() != 0) {
			String query = "INSERT INTO estadisticas (id, jugador, ganadas, perdidas, empatadas) VALUES("
					+ estadistica.getId() + ", '" + estadistica.getJugador() + "'," + estadistica.getGanadas() 
					+ "," + estadistica.getPerdidas() + "," + estadistica.getEmpatadas();
			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);
				validado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				validado = false;
			}
		}
		
		return validado;
	}
	
	public static boolean deteleEstadistica(int id) {
		Statement statement;
		boolean validado = false;
		String query = "DELETE FROM estadisticas WHERE id = " + id;
		try {
			statement = connection.createStatement();
			statement.executeQuery(query);
			validado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			validado = false;
		}

		return validado;
	}
}
