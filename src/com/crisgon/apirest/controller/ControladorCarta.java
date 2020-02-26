package com.crisgon.apirest.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.crisgon.apirest.model.Carta;

/**
 * Clase controladora que se encarga de las operaciones deseadas con la tabla
 * Carta.
 * 
 * @author Cristhian González.
 *
 * 
 */

public class ControladorCarta {
	private static Connection connection = MySQLConnector.getConnection();

	/**
	 * 
	 * @return
	 */
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

	// TODO El NULL de la SQL puede llegar a dar problemas
	public static boolean addCarta(String identificador, String marca, String modelo, byte[] foto, int motor,
			int potencia, int velocidad, int cilindros, int revoluciones, double consumo) {

		Boolean estaAgregado = false;
		Statement statement;
		String sql;

		if (identificador != null) {

			sql = "INSERT INTO cartas (identificador, marca, modelo, foto, motor,"
					+ " potencia, velocidad, cilindros, revoluciones, consumo) VALUES (" + "'" + identificador + "', '"
					+ marca + "', '" + modelo + "', " + "null," + motor + "," + potencia + "," + velocidad + ", "
					+ cilindros + "," + revoluciones + "," + consumo + ")";

			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
				estaAgregado = true;
			} catch (SQLException e) {
				e.printStackTrace();
				estaAgregado = false;
			}

		}

		return estaAgregado;
	}

	public static boolean updateCarta(String identificador, String marca, String modelo, byte[] foto, int motor,
			int potencia, int velocidad, int cilindros, int revoluciones, double consumo) {

		Statement statement;
		String query;
		boolean validado = false;

		query = "UPDATE partidas SET  marca = " + marca + ", modelo = " + modelo + ", " + "foto = " + foto
				+ " , motor = " + motor + ", potencia = " + potencia + ", " + "velocidad = " + velocidad
				+ ", cilindros = " + cilindros + ", revoluciones = " + revoluciones + ", consumo = " + consumo
				+ " WHERE identificador = " + identificador;
		try {
			statement = connection.createStatement();

			statement.executeUpdate(query.toString());
			validado = true;
		} catch (SQLException e) {
			e.printStackTrace();
			validado = false;
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
