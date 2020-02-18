package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		Statement statement;
		ResultSet resultSet;
		Jugador jugador;

		String nickname, nombre, password;

		jugadores = new ArrayList<>();

		String query = "SELECT * FROM jugadores";
		
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

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
		Statement statement;
		ResultSet resultSet;

		String nombre, password;
		int ganadas, perdidas, empatadas;

		String query = "SELECT * FROM jugadores WHERE nickname = '" + nickname + "'";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

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

	public static Boolean addJugador(Jugador jugador) {
		Boolean validada = false;
		if (jugador.getNickname() != null) {
			String query = "INSERT INTO jugadores (nickname, nombre, password) VALUES("
					+ "'" + jugador.getNickname() + "'," + "'" + jugador.getNombre() + "'," + "'"
					+ jugador.getPassword() + "')";
			try {
				Statement statement = connection.createStatement();
				statement.executeQuery(query);
				validada = true;
			} catch (SQLException e) {
				validada = false;
				e.printStackTrace();
			}
		}
		
		return validada;
	}

	public static void updateJugador(Jugador jugador) {

		Statement statement;

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
				statement = connection.createStatement();
				statement.executeQuery(query.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	public static Boolean deleteJugador(String nickname) {
		Boolean validado = false;
		String query = "DELETE FROM jugadores WHERE nickname = '" + nickname + "'";
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(query);
			validado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			validado = false;
		}

		return validado;
	}

}
