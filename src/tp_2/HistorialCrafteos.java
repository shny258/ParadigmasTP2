package tp_2;

import java.util.HashMap;
import java.util.Map;

public class HistorialCrafteos {
	private int turnoCreacion;
	private Map<Integer, Objeto> historial;

	public HistorialCrafteos() {
		this.turnoCreacion = 1;
		this.historial = new HashMap<Integer, Objeto>();
	}

	public void agregarCrafteo(Objeto objeto) {
		this.historial.put(this.turnoCreacion, objeto);
		this.turnoCreacion++;
	}

	@Override
	public String toString() {
		String stringret = "";
		for (Integer turno : this.historial.keySet()) {
			Objeto objeto = this.historial.get(turno);
			stringret = stringret + "-Turno: " + turno + "\n-Objeto: " + objeto.getNombre() + "\n"
					+ objeto.obtenerReceta() + "\n";
			stringret += "══════════════════════════════════════════════════════════\n";
		}
		return stringret;
	}

}
