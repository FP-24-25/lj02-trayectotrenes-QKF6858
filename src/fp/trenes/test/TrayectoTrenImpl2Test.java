package fp.trenes.test;

import java.time.LocalTime;

import fp.trenes.TipoTren;
import fp.trenes.TrayectoTren;
import fp.trenes.TrayectoTrenImpl2;

public class TrayectoTrenImpl2Test {

	private static void testConstructor(String codigoTren, String nombreTrayecto,
			TipoTren tipo, String origen, String destino, LocalTime horaLlegada,
			LocalTime horaSalida) {

		try {
			TrayectoTren trayecto = new TrayectoTrenImpl2(codigoTren, nombreTrayecto,
					tipo, origen, destino, horaLlegada, horaSalida);
			System.out.println("Creado objeto trayecto:\n " + trayecto);
			System.out.println();

		} catch (IllegalArgumentException e) {
			System.out.println("Capturada excepcion: " + e);
		}

	}

	private static void testGetters(TrayectoTren trayecto, String estacionSalida,
			String estacionLlegada) {
		try {
			System.out.println("Testeando getters");
			System.out.println("codigo: " + "<" + trayecto.getCodigoTren() + ">");
			System.out.println("nombre: " + "<" + trayecto.getNombre() + ">");
			System.out.println("tipo: " + "<" + trayecto.getTipo() + ">");
			System.out
					.println("estaciones: " + "<" + trayecto.getEstaciones() + ">");
			System.out.println(
					"horas de salida: " + "<" + trayecto.getHorasSalida() + ">");
			System.out.println(
					"horas de llegada: " + "<" + trayecto.getHorasLlegada() + ">");
			System.out.println("primer hora de salida: " + "<"
					+ trayecto.getHoraSalida() + ">");
			System.out.println("ultima hora de llegada: " + "<"
					+ trayecto.getHoraLlegada() + ">");
			System.out.println(
					"duracion del trayecto: " + "<" + trayecto.getDuracion() + ">");
			System.out.println("hora de salida de la estacion " + estacionSalida
					+ ": " + "<" + trayecto.getHoraSalida(estacionSalida) + ">");
			System.out.println("hora de llegada de la estacion " + estacionSalida
					+ ": " + "<" + trayecto.getHoraLlegada(estacionLlegada) + ">");
			System.out.println();

		} catch (IllegalArgumentException e) {
			System.out.println("Capturada excepcion: " + e);
		}
	}

	private static void testAñadirEstacionIntermedia(TrayectoTren t, int posicion,
			String estacion, LocalTime horaLlegada, LocalTime horaSalida) {
		try {
			System.out.println("Añadiendo una estacion");

			System.out.println("Listas antes del cambio:");
			System.out.println(t.getEstaciones());
			System.out.println(t.getHorasLlegada());
			System.out.println(t.getHorasSalida());;
			System.out.println();

			t.añadirEstacionIntermedia(posicion, estacion, horaLlegada, horaSalida);

			System.out.println("Listas despues del cambio:");
			System.out.println(t.getEstaciones());
			System.out.println(t.getHorasLlegada());
			System.out.println(t.getHorasSalida());
			System.out.println();

		} catch (IllegalArgumentException e) {
			System.out.println("Capturada excepcion: " + e);
		}
	}

	private static void testeEliminarEstacionIntermedia(TrayectoTren t,
			String estacion) {
		try {
			System.out.println("Eliminando una estacion");

			System.out.println("Listas antes del cambio:");
			System.out.println(t.getEstaciones());
			System.out.println(t.getHorasLlegada());
			System.out.println(t.getHorasSalida());

			t.eliminarEstacionIntermedia(estacion);

			System.out.println("Listas despues del cambio:");
			System.out.println(t.getEstaciones());
			System.out.println(t.getHorasLlegada());
			System.out.println(t.getHorasSalida());

		} catch (IllegalArgumentException e) {
			System.out.println("Capturada excepcion: " + e);
		}
	}

	public static void main(String[] args) {
		
		testConstructor("12345", "Sevilla-Madrid", TipoTren.AVE, "Sevilla", "Madrid",
				LocalTime.of(11, 30), LocalTime.of(8, 0));

		TrayectoTren t = new TrayectoTrenImpl2("12345", "Sevilla-Madrid",
				TipoTren.AVE, "Sevilla", "Madrid", LocalTime.of(11, 30),
				LocalTime.of(8, 0));

		testAñadirEstacionIntermedia(t, 1, "Córdoba", LocalTime.of(9, 30),
				LocalTime.of(9, 40));
		System.out.println(t + "\n");
		testGetters(t, "Córdoba", "Córdoba");

		testeEliminarEstacionIntermedia(t, "Córdoba");

	}

}

