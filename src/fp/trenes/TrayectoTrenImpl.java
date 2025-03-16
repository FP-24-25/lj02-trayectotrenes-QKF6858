package fp.trenes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import fp.utiles.Checkers;
import fp.utiles.Validators;

public class TrayectoTrenImpl implements TrayectoTren {

	String codigoTren;
	String nombreTrayecto;
	TipoTren tipo;
	List<String> estaciones;
	List<LocalTime> horasSalida;
	List<LocalTime> horasLlegada;

	public TrayectoTrenImpl(String codigoTren, String nombreTrayecto, TipoTren tipo,
			String origen, String destino, LocalTime horaLlegada,
			LocalTime horaSalida) {

		Checkers.check("Codigo no valido",
				(codigoTren.length() == 5 && Validators.sonDigitos(codigoTren)));
		this.codigoTren = codigoTren;

		this.nombreTrayecto = nombreTrayecto;
		this.tipo = tipo;

		this.estaciones = new LinkedList<String>();
		estaciones.add(origen);
		estaciones.add(destino);

		Checkers.checkNoNull(horaSalida, horaLlegada);
		Checkers.check("hora de salida no valida", horaSalida.isBefore(horaLlegada));

		this.horasSalida = new LinkedList<LocalTime>();
		horasSalida.add(horaSalida);
		horasSalida.add(null);

		this.horasLlegada = new LinkedList<LocalTime>();
		horasLlegada.add(null);
		horasLlegada.add(horaLlegada);

	}

	@Override
	public String getCodigoTren() {
		return codigoTren;
	}

	@Override
	public String getNombre() {
		return nombreTrayecto;
	}

	@Override
	public TipoTren getTipo() {
		return tipo;
	}

	@Override
	public List<String> getEstaciones() {
		return new LinkedList<>(estaciones);
	}

	@Override
	public List<LocalTime> getHorasSalida() {
		return new LinkedList<>(horasSalida);
	}

	@Override
	public List<LocalTime> getHorasLlegada() {
		return new LinkedList<>(horasLlegada);
	}

	@Override
	public LocalTime getHoraSalida() {
		return horasSalida.getFirst();
	}

	@Override
	public LocalTime getHoraLlegada() {
		return horasLlegada.getLast();
	}

	@Override
	public Duration getDuracion() {
		LocalTime horaSalida = horasSalida.getFirst();
		LocalTime horaLlegada = horasLlegada.getLast();
		return Duration.between(horaSalida, horaLlegada);
	}

	@Override
	public LocalTime getHoraSalida(String estacion) {
		int i = estaciones.indexOf(estacion);
		return (i != -1 && i < estaciones.size() - 1) ? horasSalida.get(i) : null;
	}

	@Override
	public LocalTime getHoraLlegada(String estacion) {
		int i = estaciones.indexOf(estacion);
		return (i > 0) ? horasLlegada.get(i) : null;
	}

	@Override
	public void añadirEstacionIntermedia(int posicion, String estacion,
			LocalTime horaLlegada, LocalTime horaSalida) {

		Checkers.check("Posicion no valida",
				1 <= posicion && posicion < estaciones.size());

		Checkers.check("Hora de salida anterior a la de llegada",
				(horaSalida.isAfter(horaLlegada)));

		Checkers.check("Hora de llegada anterior a la de salida anterior",
				(horaLlegada.isAfter(horasSalida.get(posicion - 1))));

		Checkers.check("Hora de salida posterior a la de llegada siguiente",
				(horaSalida.isBefore(horasLlegada.get(posicion))));

		estaciones.add(posicion, estacion);
		horasSalida.add(posicion, horaSalida);
		horasLlegada.add(posicion, horaLlegada);
	}

	@Override
	public void eliminarEstacionIntermedia(String estacion) {

		Checkers.check("La estacion es la ultima o la primera",
				(estacion != estaciones.getFirst()
						&& estacion != estaciones.getLast()));

		int posicion = estaciones.indexOf(estacion);
		estaciones.remove(posicion);
		horasSalida.remove(posicion);
		horasLlegada.remove(posicion);

	}

	@Override
	public String toString() {
		String tab = "\t";
		String cadena = nombreTrayecto + "-" + tipo + " (" + codigoTren + ")\n\t"
				+ estaciones.getFirst() + tab + " " + tab + horasSalida.getFirst()
				+ "\n";

		for (int i = 1; i < estaciones.size() - 1; i++) {
			cadena += tab + estaciones.get(i) + tab + horasLlegada.get(i) + tab
					+ horasSalida.get(i) + "\n";
		}

		cadena += tab + estaciones.getLast() + tab + horasLlegada.getLast() + tab
				+ "";
		return cadena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoTren == null) ? 0 : codigoTren.hashCode());
		result = prime * result
				+ ((getHoraSalida() == null) ? 0 : getHoraSalida().hashCode());
		result = prime * result
				+ ((nombreTrayecto == null) ? 0 : nombreTrayecto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof TrayectoTrenImpl))
			return false;
		TrayectoTrenImpl other = (TrayectoTrenImpl) obj;
		if (codigoTren == null) {
			if (other.codigoTren != null)
				return false;
		} else if (!codigoTren.equals(other.codigoTren))
			return false;
		if (getHoraSalida() == null) {
			if (other.getHoraSalida() != null)
				return false;
		} else if (!getHoraSalida().equals(other.getHoraSalida()))
			return false;
		if (nombreTrayecto == null) {
			if (other.nombreTrayecto != null)
				return false;
		} else if (!nombreTrayecto.equals(other.nombreTrayecto))
			return false;
		return true;
	}

	@Override
	public int compareTo(TrayectoTren t) {
		int res = this.getNombre().compareTo(t.getNombre());
		if (res == 0) {
			res = this.getHoraSalida().compareTo(t.getHoraSalida());
			if (res == 0) {
				res = this.getCodigoTren().compareTo(t.getCodigoTren());
			}
		}
		return res;
	}

}
