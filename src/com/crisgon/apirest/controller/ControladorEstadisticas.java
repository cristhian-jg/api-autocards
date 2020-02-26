package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.crisgon.apirest.model.Estadistica;

/**
 * Clase controladora que se encarga de las operaciones deseadas con la tabla
 * Estadisticas.
 * 
 * @author Cristhian González.
 *
 * 
 */

public class ControladorEstadisticas {

	private static Connection connection = MySQLConnector.getConnection();

	public static ArrayList<Estadistica> getAll() {

		ArrayList<Estadistica> estadisticas;

		Statement statement;
		ResultSet resultSet;
		Estadistica estadistica;

		int id;
		String jugador;
		int partida;
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
				partida = resultSet.getInt("partida");
				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");

				estadistica = new Estadistica(id, jugador, partida, ganadas, perdidas, empatadas);

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
		int partida;
		int ganadas;
		int perdidas;
		int empatadas;

		String query = "SELECT * FROM estadisticas WHERE id = '" + id + "'";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				jugador = resultSet.getString("jugador");
				partida = resultSet.getInt("partida");
				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");

				estadistica = new Estadistica(id, jugador, partida, ganadas, perdidas, empatadas);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return estadistica;
	}

	public static boolean addEstadistica(String jugador, int partida, int ganadas, int perdidas, int empatadas) {

		Boolean estaAgregado = false;
		Statement statement;
		String sql;

		sql = "INSERT INTO estadisticas (jugador, partida, ganadas, perdidas, empatadas) VALUES('" + jugador + "', "
				+ partida + ", " + ganadas + "," + perdidas + "," + empatadas + ")";

		System.out.print(sql);

		try {

			statement = connection.createStatement();
			statement.executeUpdate(sql);
			estaAgregado = true;

		} catch (SQLException e) {
			e.printStackTrace();
			estaAgregado = false;
		}

		return estaAgregado;
	}

	public static boolean updateEstadistica(int id, String jugador, int partida, int ganadas, int perdidas,
			int empatadas) {

		Statement statement;
		String query;
		boolean validado = false;

		query = "UPDATE partidas SET  jugador '= " + jugador + "', partida = " + partida + ", " + "ganadas = " + ganadas
				+ " , perdidas = " + perdidas + ", empatadas = " + empatadas + " WHERE id = " + id;
		try {
			statement = connection.createStatement();

			statement.executeUpdate(query.toString());
			validado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			validado = false;
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
