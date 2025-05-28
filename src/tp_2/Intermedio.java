package tp_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Intermedio extends Objeto {
	private String nombre;
	private Receta receta;

	public Intermedio(String nombre, Receta receta) {
		this.nombre = nombre;
		this.receta = receta;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public Receta obtenerReceta() {
		return this.receta;
	}

	public Receta obtenerRecetaCompleta() {
		Map<Objeto, Integer> mapRet = new HashMap<>();
		double tiempoRet = this.receta.getTiempoCreacion();

		for (Objeto ingrediente : this.receta.getIngredientes()) {
			int cantIngrediente = this.receta.getCantIngrediente(ingrediente);
			Receta recetaIngrediente = ingrediente.obtenerRecetaCompleta();
			tiempoRet += cantIngrediente * recetaIngrediente.getTiempoCreacion();
			for (Objeto subingrediente : recetaIngrediente.getIngredientes()) {
				if (mapRet.containsKey(subingrediente)) {
					mapRet.put(subingrediente, mapRet.get(subingrediente)
							+ cantIngrediente * recetaIngrediente.getCantIngrediente(subingrediente));
				} else {
					mapRet.put(subingrediente, cantIngrediente * recetaIngrediente.getCantIngrediente(subingrediente));
				}
			}
		}
		return new Receta(tiempoRet, 0, mapRet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, receta);
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
		return Objects.equals(nombre, other.nombre) && Objects.equals(receta, other.receta);
	}
}
