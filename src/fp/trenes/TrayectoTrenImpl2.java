package fp.trenes;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import fp.utiles.Checkers;
import fp.utiles.Validators;

public class TrayectoTrenImpl2 implements TrayectoTren {

	private String codigoTren;
	private String nombreTrayecto;
	private TipoTren tipo;
	private List<Parada> paradas;

	public TrayectoTrenImpl2(String codigoTren, String nombreTrayecto, TipoTren tipo,
			String origen, String destino, LocalTime horaLlegada,
			LocalTime horaSalida) {

		Checkers.check("Codigo no valido",
				(codigoTren.length() == 5 && Validators.sonDigitos(codigoTren)));
		this.codigoTren = codigoTren;

		this.nombreTrayecto = nombreTrayecto;
		this.tipo = tipo;

		this.paradas = new LinkedList<>();
		Checkers.checkNoNull(horaSalida, horaLlegada);
		Checkers.check("hora de salida no valida", horaSalida.isBefore(horaLlegada));
		this.paradas.add(new Parada(origen, horaSalida, null));
		this.paradas.add(new Parada(destino, null, horaLlegada));
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
		List<String> res = new LinkedList<>();
		for (Parada p : paradas) {
			res.add(p.estacion());
		}
		return res;
	}

	@Override
	public List<LocalTime> getHorasSalida() {
		List<LocalTime> res = new LinkedList<>();
		for (Parada p : paradas) {
			res.add(p.horaSalida());
		}
		return res;
	}

	@Override
	public List<LocalTime> getHorasLlegada() {
		List<LocalTime> res = new LinkedList<>();
		for (Parada p : paradas) {
			res.add(p.horaLlegada());
		}
		return res;
	}

	@Override
	public LocalTime getHoraSalida() {
		return paradas.getFirst().horaSalida();
	}

	@Override
	public LocalTime getHoraLlegada() {
		return paradas.getLast().horaLlegada();
	}

	@Override
	public Duration getDuracion() {

		return Duration.between(getHoraSalida(), getHoraLlegada());
	}

	@Override
	public LocalTime getHoraSalida(String estacion) {
		LocalTime res = null;
		for (Parada p : paradas) {
			if (p.estacion().equals(estacion)) {
				res = p.horaSalida();
				break;
			}
		}
		return res;
	}

	@Override
	public LocalTime getHoraLlegada(String estacion) {
		LocalTime res = null;
		for (Parada p : paradas) {
			if (p.estacion().equals(estacion)) {
				res = p.horaLlegada();
				break;
			}
		}
		return res;
	}

	@Override
	public void añadirEstacionIntermedia(int posicion, String estacion,
			LocalTime horaLlegada, LocalTime horaSalida) {

		Checkers.check("Posicion no valida",
				1 <= posicion && posicion < getEstaciones().size());

		Checkers.check("Hora de salida anterior a la de llegada",
				(horaSalida.isAfter(horaLlegada)));

		Checkers.check("Hora de llegada anterior a la de salida anterior",
				(horaLlegada.isAfter(getHorasSalida().get(posicion - 1))));

		Checkers.check("Hora de salida posterior a la de llegada siguiente",
				(horaSalida.isBefore(getHorasLlegada().get(posicion))));

		Parada nuevaParada = new Parada(estacion, horaSalida, horaLlegada);
		paradas.add(posicion, nuevaParada);
	}

	@Override
	public void eliminarEstacionIntermedia(String estacion) {

		Checkers.check("La estacion es la ultima o la primera",
				(estacion != getEstaciones().getFirst()
						&& estacion != getEstaciones().getLast()));

		int posicion = getEstaciones().indexOf(estacion);

		paradas.remove(posicion);

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
