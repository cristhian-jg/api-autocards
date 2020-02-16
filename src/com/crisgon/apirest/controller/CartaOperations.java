package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.crisgon.apirest.model.Carta;

/**
 * Created by @cristhian-jg on 14/02/2020
 *
 * @author Cristhian González.
 * 
 */

public class CartaOperations {
	
	/** Devuelve todas las cartas de la base de datos */
	public static ArrayList<Carta> getAll() {
		Connection connection = MySQLConnector.getConnection();
		
		ArrayList<Carta> cartas;
		
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		Carta carta;
		
		String identificador, marca, modelo;
		byte[] foto;
		int motor, potencia, velocidad, cilindros, revoluciones;
		double consumo;
		
		cartas = new ArrayList<>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM cartas");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				
				identificador = resultSet.getString("identificador");
				marca = resultSet.getString("marca");
				modelo = resultSet.getString("modelo");
				
				foto = null;
				
				motor = resultSet.getInt("motor");
				potencia = resultSet.getInt("potencia");
				velocidad = resultSet.getInt("velocidad");
				cilindros = resultSet.getInt("cilindros");
				revoluciones = resultSet.getInt("revoluciones");
				consumo = resultSet.getDouble("consumo");
				
				carta = new Carta(identificador, marca, modelo, foto, motor, potencia, velocidad, cilindros, revoluciones, consumo);
				
				cartas.add(carta);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cartas;
	}

}
