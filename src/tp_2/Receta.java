package tp_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Receta {

	private double tiempoCreacion;
	private int cantidadDevuelta;
	private Map<Objeto, Integer> ingredientes;

	public Receta(double tiempoCreacion, int cantDevuelta, Map<Objeto, Integer> ingredientes) {
		this.tiempoCreacion = tiempoCreacion;
		this.cantidadDevuelta = cantDevuelta;
		this.ingredientes = new HashMap<>(ingredientes);
	}

	public Receta obtenerRecetaCompleta() {
		return this.obtenerRecetaCompleta(new HashMap<Objeto, Integer>());
	}

	public Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		Map<Objeto, Integer> mapRet = new HashMap<>();
		double tiempoRet = this.tiempoCreacion;

		for (Objeto ingrediente : this.ingredientes.keySet()) {
			int cantSobrante = sobrantes.getOrDefault(ingrediente, 0);
			int cantIngrediente = this.ingredientes.get(ingrediente);
			if (cantSobrante > cantIngrediente) {
				sobrantes.put(ingrediente, cantSobrante - cantIngrediente);
			} else {
				if (cantSobrante != 0) {
					sobrantes.remove(ingrediente);
				}
				Receta recetaIngrediente = ingrediente.obtenerReceta();
				int cantCrafteosNecesarios = (int) Math
						.ceil((double) (cantIngrediente - cantSobrante) / recetaIngrediente.cantidadDevuelta);
				for (int i = 0; i < cantCrafteosNecesarios; i++) {
					Receta recetaIngredienteCompleta = ingrediente.obtenerRecetaCompleta(sobrantes);
					tiempoRet += recetaIngredienteCompleta.tiempoCreacion;
					for (Objeto subingrediente : recetaIngredienteCompleta.ingredientes.keySet()) {
						if (mapRet.containsKey(subingrediente)) {
							mapRet.put(subingrediente, mapRet.get(subingrediente)
									+ recetaIngredienteCompleta.ingredientes.get(subingrediente));
						} else {
							mapRet.put(subingrediente, recetaIngredienteCompleta.ingredientes.get(subingrediente));
						}
					}
				}
				cantSobrante = cantCrafteosNecesarios * recetaIngrediente.cantidadDevuelta
						- (cantIngrediente - cantSobrante);
				if (cantSobrante > 0) {
					sobrantes.put(ingrediente, cantSobrante);
				}
			}
		}
		return new Receta(tiempoRet, this.cantidadDevuelta, mapRet);
	}

	public Map<Objeto, Integer> getIngredientes() {
		return new HashMap<Objeto, Integer>(this.ingredientes);
	}

	public int getCantIngrediente(Objeto ingrediente) {
		return this.ingredientes.getOrDefault(ingrediente, 0);
	}

	public double getTiempoCreacion() {
		return this.tiempoCreacion;
	}

	public int getCantidadDevuelta() {
		return this.cantidadDevuelta;
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
	@Override
	public String toString() {
		String stringRet = "-Tiempo de creacion: " + tiempoCreacion + "\n-Cantidad devuelta: " + cantidadDevuelta + "\n-Ingredientes:\n";

		for (Objeto ingrediente : ingredientes.keySet()) {
			stringRet = stringRet + "\t" + ingrediente.getNombre() + ": " + ingredientes.get(ingrediente) + "\n";
		}
		;
		return stringRet;
	}
}
