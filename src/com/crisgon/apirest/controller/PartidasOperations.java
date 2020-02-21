package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;

public class PartidasOperations {

	private static Connection connection = MySQLConnector.getConnection();

	public static ArrayList<Partida> getAll() {

		ArrayList<Partida> partidas;

		Statement statement;
		ResultSet resultSet;
		Partida partida;

		int id;
		String jugador;
		boolean ganada, terminada;
		Date fecha;

		partidas = new ArrayList<>();

		try {

			String query = "SELECT * FROM partidas";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {

				id = resultSet.getInt("id");
				jugador = resultSet.getString("jugador");
				ganada = resultSet.getBoolean("ganada");
				terminada = resultSet.getBoolean("terminada");
				fecha = resultSet.getDate("fecha");

				partida = new Partida(id, jugador, ganada, terminada, fecha);

				partidas.add(partida);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return partidas;

	}

	public static Partida getPartida(int id) {

		Partida partida = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		String jugador;
		boolean ganada, terminada;
		Date fecha;

		String query = "SELECT * FROM partidas WHERE id = " + id;

		try {

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				jugador = resultSet.getString("jugador");
				ganada = resultSet.getBoolean("ganada");
				terminada = resultSet.getBoolean("terminada");
				fecha = resultSet.getDate("fecha");

				partida = new Partida(id, jugador, ganada, terminada, fecha);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return partida;

	}

	public static boolean addPartida(Partida partida) {
		boolean validado = false;
		if (partida.getId() != 0) {
			String query = "INSERT INTO partidas (id, jugador, ganada, terminada, fecha) VALUES(" + partida.getId() + ",'" + partida.getJugador()
					+ "', " + partida.isGanada() + ", " + partida.isTerminada() + ", '" + partida.getFecha() + "')";
			
			System.out.println(query);
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(query);
				System.out.print(query);
				validado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				validado = false;
			}
		}
		return validado;
	}

	public static boolean updatePartida(Partida partida) {

		Statement statement;
		StringBuilder query = new StringBuilder();
		boolean validado = false;

		if (partida.getJugador() != null || partida.getFecha() != null) {
			query.append("UPDATE partidas SET ");
			if (partida.getJugador() != null)
				query.append("jugador = '" + partida.getJugador() + "'");
			query.append(", terminada = " + partida.isTerminada());
			if (partida.getFecha() != null)
				query.append(", fecha = '" + partida.getFecha() + "'");
			query.append("WHERE id = '" + partida.getId() + "'");
			try {
				statement = connection.createStatement();
				statement.executeUpdate(query.toString());
				validado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				validado = false;
			}
		}
		return validado;
	}

	public static boolean deletePartida(int id) {
		boolean validado = false;
		String query = "DELETE FROM partidas WHERE id = " + id;
		try {
			Statement statement = connection.prepareStatement(query);
			statement.executeUpdate(query);
			validado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			validado = false;
		}
		return validado;
	}

}
