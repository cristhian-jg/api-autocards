package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

		Statement statement;
		ResultSet resultSet;
		Carta carta;

		String identificador, marca, modelo;
		byte[] foto;
		int motor, potencia, velocidad, cilindros, revoluciones;
		double consumo;

		cartas = new ArrayList<>();

		try {

			String query = "SELECT * FROM cartas";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

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
		Statement statement;
		ResultSet resultSet;

		String marca, modelo;
		byte[] foto;
		int motor, potencia, velocidad, cilindros, revoluciones;
		double consumo;

		String query = "SELECT * FROM cartas WHERE identificador = '" + identificador + "'";

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

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

	public static boolean addCarta(Carta carta) {

		Statement statement;
		boolean validado = false;

		if (carta.getIdentificador() != null) {
			String query = "INSERT INTO cartas (identificador, marca, modelo, foto, motor,"
					+ " potencia, velocidad, cilindros, revoluciones, consumo) VALUES (" + "'"
					+ carta.getIdentificador() + "', '" + carta.getMarca() + "', '" + carta.getModelo() + "', "
					+ "null,"+ carta.getMotor()+ "," + carta.getPotencia() + "," + carta.getVelocidad() + ", "
					+ carta.getCilindros() + "," + carta.getRevoluciones() + "," + carta.getConsumo() + ")";
			try {
				statement = connection.createStatement();
				statement.executeUpdate(query);
				validado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				validado = false;
			}
		}

		return validado;
	}

	public static boolean deleteCarta(String identificador) {
	
		Statement statement;
		boolean validado = false;
		String query = "DELETE FROM cartas WHERE identificador = '" + identificador + "'";
		try {
			statement = connection.createStatement();
			statement.executeQuery(query);
			validado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			validado = false;
		}

		return validado;
	}
}
