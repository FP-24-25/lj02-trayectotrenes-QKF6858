package fp.trenes;

import java.time.LocalTime;
import fp.utiles.Checkers;

public record Parada(String estacion, LocalTime horaSalida, LocalTime horaLlegada) {

	public Parada {
		Checkers.check("Las horas no son validas",
				   horaSalida == null && horaLlegada != null
				|| horaSalida != null && horaLlegada == null
				|| horaSalida != null && horaLlegada != null
				|| horaLlegada.isBefore(horaSalida));
	}
}
