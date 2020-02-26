package com.crisgon.apirest.controller;

import java.util.ArrayList;
import java.util.Random;

import com.crisgon.apirest.Caracteristica;
import com.crisgon.apirest.HandResult;
import com.crisgon.apirest.libreria.RandomNoRepeat;
import com.crisgon.apirest.model.Carta;

/**
 * Clase que controla las operaciones de la partida.
 * 
 * @author Cristhian González.
 *
 * 
 */
public class JuegoOperations {

	private static final String TAG = "ControladorJuego";
	private static final int CARTAS_POR_MANO = 6;

	private Random rnd = new Random();

	private ArrayList<Integer> numerosAleatoriosNoRepetidos;

	private ArrayList<Carta> cartasJugador = new ArrayList<>();
	private ArrayList<Carta> cartasMaquina = new ArrayList<>();

	private Carta cartaSeleccionadaCPU;

	private static String feature;
	private static int hand;

	private boolean isTurnoJugador = true;

	private static HandResult result = new HandResult();

	/**
	 * Metodo que reparte las cartas a cada jugador, utiliza la clase RandomNoRepeat
	 * 
	 * @param cartas
	 */
	public void raffle(ArrayList<Carta> cartas) {

		int aleatorio;

		numerosAleatoriosNoRepetidos = RandomNoRepeat.randomNoRepeat(12, cartas.size() - 1);

		for (int i = 0; i < numerosAleatoriosNoRepetidos.size(); i++) {
			aleatorio = numerosAleatoriosNoRepetidos.get(i);
			if (i < CARTAS_POR_MANO)
				cartasJugador.add(cartas.get(aleatorio));
			else {
				cartasMaquina.add(cartas.get(aleatorio));
			}
		}
	}

	/**
	 * Sortea quien empieza primero.
	 * 
	 * @return
	 */
	public int sortear() {
		return rnd.nextInt(2);
	}

	/**
	 * En caso de ser turno de la CPU, seleccionara la caracteristica.
	 * 
	 * @return
	 */
	public String featureSelect() {

		Random random = new Random();
		int aleatorio;

		aleatorio = random.nextInt(5);

		switch (aleatorio) {
		case 0:
			this.feature = "MOTOR";
			break;
		case 1:
			this.feature = "CILINDROS";
			break;
		case 2:
			this.feature = "CONSUMO";
			break;
		case 3:
			this.feature = "POTENCIA";
			break;
		case 4:
			this.feature = "REVOLUCIONES";
			break;
		case 5:
			this.feature = "VELOCIDAD";
			break;
		}
		return feature;
	}

	/**
	 * Elige una carta de la baraja de la CPU.
	 * 
	 * @return carata seleccionada por la CPU.
	 */
	public Carta cpuEligeCarta() {
		// Se obtiene un numero aleatorio para que la cpu seleccione la carta
		int aleatorio = rnd.nextInt(cartasMaquina.size() - 1);
		// Se obtiene el id de la carta seleccionada aleatoriamente por cpu.
		cartaSeleccionadaCPU = cartasMaquina.get(aleatorio);
		return cartaSeleccionadaCPU;
	}

	/**
	 * Clase que realizada la jugada y comprueba la carta ganadora.
	 * 
	 * @param idSesion
	 * @param idGame
	 * @param idCard
	 * @param caracteristica
	 * @param hand
	 * @return HandResult con el resultado
	 */
	public HandResult playCard(String idSesion, int idGame, String idCard, String caracteristica, int hand) {

		int aleatorio;
		String idCardCPU;

		if (isTurnoJugador) {
			this.feature = caracteristica;
		}

		this.hand = hand;

		idCardCPU = cartaSeleccionadaCPU.getIdentificador();

		// Se comprueba que quien gana de los dos
		check(idCard, idCardCPU);

		// Se remueve la carta seleccionada tanto para el jugador como para el cpu.
		removeCardPlayer(idCard);
		removeCardCPU(idCardCPU);

		if (hand == 6) {
			this.hand = 0;
		}

		// Se devuelve el resultado de la mano
		return result;
	}

	/**
	 * Eliminar la carta seleccionada por el jugador.
	 * 
	 * @param idCard
	 */
	public void removeCardPlayer(String idCard) {
		for (int i = 0; i < cartasJugador.size(); i++) {
			if (cartasJugador.get(i).getIdentificador().equals(idCard)) {
				cartasJugador.remove(i);
			}
		}
	}

	/**
	 * Elimina la carta seleccionada por la CPU.
	 * 
	 * @param idCard
	 */
	public void removeCardCPU(String idCard) {
		for (int i = 0; i < cartasMaquina.size(); i++) {
			if (cartasMaquina.get(i).equals(idCard)) {
				cartasMaquina.remove(i);
			}
		}
	}

	/**
	 * Comprueba quien es el ganador de la mano y se lleva los puntos.
	 * 
	 * @param idCartaJugador
	 * @param idCardCPU
	 */
	public void check(String idCartaJugador, String idCardCPU) {

		Carta cartaJugador = ControladorCarta.getCarta(idCartaJugador);
		Carta cartaCPU = ControladorCarta.getCarta(idCardCPU);

		switch (feature) {
		case "MOTOR":
			if (cartaJugador.getMotor() > cartaCPU.getMotor()) {
				result.aumentarMarcadorJugador();
			} else {
				result.aumentarMarcadorCPU();
			}
			break;
		case "POTENCIA":
			if (cartaJugador.getPotencia() > cartaCPU.getPotencia()) {
				result.aumentarMarcadorJugador();

			} else {
				result.aumentarMarcadorCPU();
			}
			break;

		case "VELOCIDAD":

			if (cartaJugador.getVelocidad() > cartaCPU.getVelocidad()) {
				result.aumentarMarcadorJugador();
			} else {
				result.aumentarMarcadorCPU();
			}
			break;
		case "CILINDROS":
			if (cartaJugador.getCilindros() > cartaCPU.getCilindros()) {
				result.aumentarMarcadorJugador();
			} else {
				result.aumentarMarcadorCPU();
			}
			break;

		case "REVOLUCIONES":

			if (cartaJugador.getRevoluciones() < cartaCPU.getRevoluciones()) {
				result.aumentarMarcadorJugador();
			} else {
				result.aumentarMarcadorCPU();
			}
			break;

		case "CONSUMO":

			if (cartaJugador.getConsumo() < cartaCPU.getConsumo()) {
				result.aumentarMarcadorJugador();
			} else {
				result.aumentarMarcadorCPU();
			}
			break;
		default:
			System.out.println("Algo salió mal...");
			break;
		}
	}

	public void setHand(int hand) {
		this.hand = hand;
	}

	public ArrayList<Carta> getCartasJugador() {
		return cartasJugador;
	}

	public ArrayList<Carta> getCartasMaquina() {
		return cartasMaquina;
	}

	public Carta getCartaSeleccionadaCPU() {
		return cartaSeleccionadaCPU;
	}

	public void setCartaSeleccionadaCPU(Carta cartaSeleccionadaCPU) {
		this.cartaSeleccionadaCPU = cartaSeleccionadaCPU;
	}

	public void isTurnoJugador(boolean is) {
		this.isTurnoJugador = is;
	}

}
