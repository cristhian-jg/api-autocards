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
		int ganadas, perdidas, empatadas;

		jugadores = new ArrayList<>();

		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM jugadores");
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				nickname = resultSet.getString("nickname");
				nombre = resultSet.getString("nombre");
				password = resultSet.getString("password");

				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");

				jugador = new Jugador(nickname, nombre, password, ganadas, perdidas, empatadas);

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

		String query = "SELECT * FROM jugadores WHERE nickname = " + nickname;

		try {

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				nombre = resultSet.getString("nombre");
				password = resultSet.getString("password");
				ganadas = resultSet.getInt("ganadas");
				perdidas = resultSet.getInt("perdidas");
				empatadas = resultSet.getInt("empatadas");

				jugador = new Jugador(nickname, nombre, password, ganadas, perdidas, empatadas);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jugador;
	}

	public static void addJugador(Jugador jugador) {

		PreparedStatement preparedStatement;

		String query = "INSERT INTO jugadores VALUES(" + "'" + jugador.getNickname() + "'," + "'" + jugador.getNombre()
				+ "'," + "'" + jugador.getPassword() + "'," + "0, 0, 0";

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void updateJugador(Jugador jugador) {

		PreparedStatement preparedStatement;

		String query = "UPDATE jugadores SET " + "nombre = '" + jugador.getNombre() + "'," + "password = '"
				+ jugador.getPassword() + "'," + "ganadas = " + jugador.getGanadas() + "," + "perdidas = "
				+ jugador.getPerdidas() + "," + "empatadas = " + jugador.getEmpatadas();

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void deleteJugador(Jugador jugador) {
		
		PreparedStatement preparedStatement;

		String query = "DELETE FROM jugadores WHERE nickname = " + jugador.getNickname();
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
