package tp_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Receta {
	private double tiempoCreacion;
	private int cantidadDevuelta;
	private Map<Objeto, Integer> ingredientes;

	public Receta(double tiempoCreacion, int cantDevuelta, Map<Objeto, Integer> ingredientes) {
		this.tiempoCreacion = tiempoCreacion;
		this.cantidadDevuelta = cantDevuelta;
		this.ingredientes = new HashMap<>(ingredientes);
	}

	public Set<Objeto> getIngredientes() {
		return this.ingredientes.keySet();
	}

	public Receta obtenerRecetaCompleta() {
		Map<Objeto, Integer> mapRet = new HashMap<>();
		double tiempoRet = this.tiempoCreacion;

		for (Objeto ingrediente : this.ingredientes.keySet()) {
			int cantIngrediente = this.ingredientes.get(ingrediente);
			Receta recetaIngrediente = ingrediente.obtenerRecetaCompleta();
			int cantCrafteosNecesarios = (int) Math.ceil((double) cantIngrediente / recetaIngrediente.cantidadDevuelta);
			tiempoRet += cantCrafteosNecesarios * recetaIngrediente.tiempoCreacion;
			for (Objeto subingrediente : recetaIngrediente.ingredientes.keySet()) {
				if (mapRet.containsKey(subingrediente)) {
					mapRet.put(subingrediente, mapRet.get(subingrediente)
							+ cantCrafteosNecesarios * recetaIngrediente.ingredientes.get(subingrediente));
				} else {
					mapRet.put(subingrediente,
							cantCrafteosNecesarios * recetaIngrediente.ingredientes.get(subingrediente));
				}
			}
		}
		return new Receta(tiempoRet, this.cantidadDevuelta, mapRet);
	}

//	public int getCantIngrediente(Objeto ingrediente) {
//		return this.ingredientes.getOrDefault(ingrediente, 0);
//	}
//	
//	public double getTiempoCreacion() {
//		return this.tiempoCreacion;
//	}
//	
//	public int getCantidadDevuelta() {
//		return this.cantidadDevuelta;
//	}

	@Override
	public String toString() {
		String stringRet = "Tiempo de creacion=" + tiempoCreacion + "\n\tIngredientes:\n";

		for (Objeto ingrediente : ingredientes.keySet()) {
			stringRet = stringRet + "\t\t" + ingrediente.getNombre() + "=" + ingredientes.get(ingrediente) + "\n";
		}
		;
		return stringRet;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidadDevuelta, ingredientes, tiempoCreacion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receta other = (Receta) obj;
		return cantidadDevuelta == other.cantidadDevuelta && Objects.equals(ingredientes, other.ingredientes)
				&& Double.doubleToLongBits(tiempoCreacion) == Double.doubleToLongBits(other.tiempoCreacion);
	}
}
