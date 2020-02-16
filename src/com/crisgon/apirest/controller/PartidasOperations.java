package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.crisgon.apirest.model.Jugador;
import com.crisgon.apirest.model.Partida;

public class PartidasOperations {

	private static Connection connection = MySQLConnector.getConnection();

	public static ArrayList<Partida> getAll() {

		ArrayList<Partida> partidas;

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Partida partida;

		int id;
		String jugador;
		boolean ganada, terminada;
		Date fecha;

		partidas = new ArrayList<>();

		try {

			preparedStatement = connection.prepareStatement("SELECT * FROM partidas");
			resultSet = preparedStatement.executeQuery();

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

	public static void addPartida(Partida partida) {

		PreparedStatement preparedStatement;

		if (partida.getId() != 0) {
			String query = "INSERT INTO partidas (id, jugador, ganada, terminada, fecha) VALUES(" + partida.getId()
					+ ", '" + partida.getJugador() + "', " + partida.isGanada() + ", " + partida.isTerminada() + ", '"
					+ partida.getFecha() + "'";
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void updatePartida(Partida partida) {

		PreparedStatement preparedStatement;

		StringBuilder query = new StringBuilder();

		if (partida.getJugador() != null || partida.getFecha() != null) {

			query.append("UPDATE partidas SET ");

			if (partida.getJugador() != null) {
				query.append("jugador = '" + partida.getJugador() + "'");
			}
			
			query.append(", terminada = " + partida.isTerminada());
			
			if (partida.getFecha() != null) {
				query.append(", fecha = '" + partida.getFecha() + "'");
			}

			query.append("WHERE id = '" + partida.getId() + "'");

			System.out.print(query);

			try {
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public static void deletePartida(int id) {

		PreparedStatement preparedStatement;

		String query = "DELETE FROM partidas WHERE id = " + id;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
