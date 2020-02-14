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

	public ArrayList<Jugador> getAll() {
		Connection connection = MySQLConnector.connectMySQL();

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
			
			while(resultSet.next()) {
				
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

}
