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

public class ControladorJugador {

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

	public static boolean addJugador(String nickname, String nombre, String password) {
		
		Boolean validada = false;
		String query;
		
		if (nickname != null) {
			query = "INSERT INTO jugadores (nickname, nombre, password) VALUES("
					+ "'" + nickname + "'," + "'" + nombre + "'," + "'"
					+ password + "')";
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(query);
				validada = true;
			} catch (SQLException e) {
				validada = false;
				e.printStackTrace();
			}
		}
		
		return validada;
	}

	public static void updateJugador(String nickname, String nombre, String password) {

		Statement statement;

		StringBuilder query = new StringBuilder();

		if (nombre != null || password != null) {

			query.append("UPDATE jugadores SET ");

			if (nombre != null) {
				query.append("nombre = '" + nombre + "'");
			}

			if (password != null) {
				query.append(", password = '" + password + "'");
			}

			query.append("WHERE nickname = '" + nickname + "'");

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
