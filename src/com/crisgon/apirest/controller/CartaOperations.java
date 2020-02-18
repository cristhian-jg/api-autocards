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
	private static Connection connection = MySQLConnector.getConnection();

	/** Devuelve todas las cartas de la base de datos */
	public static ArrayList<Carta> getAll() {

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

				carta = new Carta(identificador, marca, modelo, foto, motor, potencia, velocidad, cilindros,
						revoluciones, consumo);

				cartas.add(carta);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cartas;
	}

	public static Carta getCarta(String identificador) {

		Carta carta = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		String marca, modelo;
		byte[] foto;
		int motor, potencia, velocidad, cilindros, revoluciones;
		double consumo;

		String query = "SELECT * FROM cartas WHERE identificador = '" + identificador + "'";

		try {

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				marca = resultSet.getString("marca");
				modelo = resultSet.getString("modelo");
				foto = null;
				motor = resultSet.getInt("motor");
				potencia = resultSet.getInt("potencia");
				velocidad = resultSet.getInt("velocidad");
				cilindros = resultSet.getInt("cilindros");
				revoluciones = resultSet.getInt("revoluciones");
				consumo = resultSet.getDouble("consumo");

				carta = new Carta(identificador, marca, modelo, foto, motor, potencia, velocidad, cilindros,
						revoluciones, consumo);
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return carta;
	}

	public static void addCarta(Carta carta) {
		
		PreparedStatement preparedStatement;
		
		if (carta.getIdentificador() != null) {
			String query = "INSERT INTO cartas (identificador, marca, modelo, foto, motor,"
					+ " potencia, velocidad, cilindros, revoluciones, consumo) VALUES ("
					+ "'"+ carta.getIdentificador() + "', '" + carta.getMarca() + "', '" + carta.getModelo() + "', "
					+ "null, carta.getMotor(), carta.getPotencia(), carta.getVelocidad(), "
					+ "carta.getCilindros(), carta.getRevoluciones, carta.getConsumo)";
			
			try { 
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void deleteCarta(String identificador) {
		
		PreparedStatement preparedStatement;

		String query = "DELETE FROM cartas WHERE identificador = '" + identificador + "'";
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
