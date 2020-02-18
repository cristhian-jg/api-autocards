package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.crisgon.apirest.model.Jugador;

/**
 * Created by @cristhian-jg on 14/02/2020
 *
 * @author Cristhian González.
 * 
 */

public class JugadorOperations {

	private static Connection connection = MySQLConnector.getConnection();

	public static ArrayList<Jugador> getAll() {

		ArrayList<Jugador> jugadores;

		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Jugador jugador;

		String nickname, nombre, password;

		jugadores = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM jugadores");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				nickname = resultSet.getString("nickname");
				nombre = resultSet.getString("nombre");
				password = resultSet.getString("password");
				
				jugador = new Jugador(nickname, nombre, password);

				jugadores.add(jugador);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jugadores;
	}

	public static Jugador getJugador(String nickname) {

		Jugador jugador = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		String nombre, password;
		int ganadas, perdidas, empatadas;

		String query = "SELECT * FROM jugadores WHERE nickname = '" + nickname + "'";

		try {

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				nombre = resultSet.getString("nombre");
				password = resultSet.getString("password");
				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");

				jugador = new Jugador(nickname, nombre, password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jugador;
	}

	public static void addJugador(Jugador jugador) {

		PreparedStatement preparedStatement;

		if (jugador.getNickname() != null) {
			String query = "INSERT INTO jugadores (nickname, nombre, password) VALUES("
					+ "'" + jugador.getNickname() + "'," + "'" + jugador.getNombre() + "'," + "'"
					+ jugador.getPassword() + "')";

			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void updateJugador(Jugador jugador) {

		PreparedStatement preparedStatement;

		StringBuilder query = new StringBuilder();

		if (jugador.getNombre() != null || jugador.getPassword() != null) {

			query.append("UPDATE jugadores SET ");

			if (jugador.getNombre() != null) {
				query.append("nombre = '" + jugador.getNombre() + "'");
			}

			if (jugador.getPassword() != null) {
				query.append(", password = '" + jugador.getPassword() + "'");
			}

			query.append("WHERE nickname = '" + jugador.getNickname() + "'");

			System.out.print(query);

			try {
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static void deleteJugador(String nickname) {

		PreparedStatement preparedStatement;

		String query = "DELETE FROM jugadores WHERE nickname = '" + nickname + "'";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
