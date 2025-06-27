package tp_2;

import java.util.HashMap;
import java.util.Map;

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
	
////CONSIDERANDO SOBRANTES
//	public Receta obtenerRecetaCompleta() {
//		return this.obtenerRecetaCompleta(new HashMap<Objeto, Integer>());
//	}
//
//	public Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
//		Map<Objeto, Integer> mapRet = new HashMap<>();
//		double tiempoRet = this.tiempoCreacion;
//
//		for (Objeto ingrediente : this.ingredientes.keySet()) {
//			int cantSobrante = sobrantes.getOrDefault(ingrediente, 0);
//			int cantIngrediente = this.ingredientes.get(ingrediente);
//			if (cantSobrante > cantIngrediente) {
//				sobrantes.put(ingrediente, cantSobrante - cantIngrediente);
//			} else {
//				if (cantSobrante != 0) {
//					sobrantes.remove(ingrediente);
//				}
//				
//				Receta recetaIngrediente = ingrediente.obtenerReceta();
//				int cantCrafteosNecesarios = (int) Math
//						.ceil((double) (cantIngrediente - cantSobrante) / recetaIngrediente.cantidadDevuelta);
//				cantSobrante = cantCrafteosNecesarios * recetaIngrediente.cantidadDevuelta
//						- (cantIngrediente - cantSobrante);
//				if (cantSobrante > 0) {
//					sobrantes.put(ingrediente, cantSobrante);
//				}
//				Receta recetaIngredienteCompleta = ingrediente.obtenerRecetaCompleta(sobrantes);
//				tiempoRet += cantCrafteosNecesarios * recetaIngredienteCompleta.tiempoCreacion;
//				
//				for (Objeto subingrediente : recetaIngredienteCompleta.ingredientes.keySet()) {
//					if (mapRet.containsKey(subingrediente)) {
//						mapRet.put(subingrediente, mapRet.get(subingrediente)
//								+ cantCrafteosNecesarios * recetaIngredienteCompleta.ingredientes.get(subingrediente));
//					} else {
//						mapRet.put(subingrediente,
//								cantCrafteosNecesarios * recetaIngredienteCompleta.ingredientes.get(subingrediente));
//					}
//				}
//
//			}
//		}
//		return new Receta(tiempoRet, this.cantidadDevuelta, mapRet);
//	}

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
	public String toString() {
		String stringRet = "Tiempo de creacion=" + tiempoCreacion + "\n\tIngredientes:\n";

		for (Objeto ingrediente : ingredientes.keySet()) {
			stringRet = stringRet + "\t\t" + ingrediente.getNombre() + "=" + ingredientes.get(ingrediente) + "\n";
		}
		;
		return stringRet;
	}
}
