package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.crisgon.apirest.model.Partida;

public class PartidasOperations {

	public static ArrayList<Partida> getAll() {
		Connection connection = MySQLConnector.getConnection();
		
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
	
}
