package tp_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Intermedio extends Objeto {
	private String nombre;
	private double tiempoCreacion;
	private Map<Objeto, Integer> ingredientes;

	public Intermedio(String nombre, double tiempoCreacion, Map<Objeto, Integer> ingredientes) {
		super();
		this.nombre = nombre;
		this.tiempoCreacion = tiempoCreacion;
		this.ingredientes = new HashMap<>(ingredientes);
	}

//	public Intermedio(double tiempoCreacion, Map<Objeto, Integer> ingredientes) {
//		this("", tiempoCreacion, ingredientes);
//	}
	
	public String getNombre() {
		return this.nombre;
	}

	public double getTiempoCreacion() {
		return this.tiempoCreacion;
	}

	public Intermedio obtenerReceta() {
		return this;
	}

	public Intermedio obtenerRecetaCompleta() {
		Map<Objeto, Integer> mapRet = new HashMap<>();
		double tiempoRet = this.tiempoCreacion;

		for (Objeto ingrediente : this.ingredientes.keySet()) {
			int cantIngrediente = this.ingredientes.get(ingrediente);
			Intermedio recetaIngrediente = ingrediente.obtenerRecetaCompleta();
			tiempoRet += cantIngrediente * recetaIngrediente.tiempoCreacion;
			for (Objeto subingrediente : recetaIngrediente.ingredientes.keySet()) {
				if (mapRet.containsKey(subingrediente)) {
					mapRet.put(subingrediente, mapRet.get(subingrediente)
							+ cantIngrediente * recetaIngrediente.ingredientes.get(subingrediente));
				} else {
					mapRet.put(subingrediente, cantIngrediente * recetaIngrediente.ingredientes.get(subingrediente));
				}
			}
		}
		return new Intermedio(this.getNombre(), tiempoRet, mapRet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredientes, tiempoCreacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intermedio other = (Intermedio) obj;
		return Objects.equals(ingredientes, other.ingredientes)
				&& Double.doubleToLongBits(tiempoCreacion) == Double.doubleToLongBits(other.tiempoCreacion);
	}

	@Override
	public String toString() {
		String stringRet = this.getNombre() + ":\n\tTiempo de creacion=" + tiempoCreacion + "\n\tIngredientes:\n";
		
		for (Objeto ingrediente: ingredientes.keySet()) {
			stringRet = stringRet + "\t\t" + ingrediente.getNombre() + "=" + ingredientes.get(ingrediente) + "\n";
		};
		return stringRet;
	}

}
