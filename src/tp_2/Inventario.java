package tp_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventario {
	private Map<Objeto, Integer> objetos;
	private HistorialCrafteos historial;

	public Inventario(Map<Objeto, Integer> objetos) {
		this.objetos = objetos;
		this.historial = new HistorialCrafteos();
	}

	public Map<Objeto, Integer> getObjetos() {
		return new HashMap<Objeto, Integer>(objetos);
	}

	public Receta faltantesParaCraftear(Objeto objeto) {
		Receta recetaObjeto = objeto.obtenerReceta();
		Map<Objeto, Integer> ingredientesFaltantes = recetaObjeto.getIngredientes();
		List<Objeto> ingredientesTenidos = new ArrayList<>();
		double tiempoRet = recetaObjeto.getTiempoCreacion();

		for (Objeto ingrediente : ingredientesFaltantes.keySet()) {
			int cantIngredienteEnInventario = this.objetos.getOrDefault(ingrediente, 0);
			int cantFaltante = ingredientesFaltantes.get(ingrediente);
			if (cantIngredienteEnInventario >= cantFaltante) {
				ingredientesTenidos.add(ingrediente);
			} else {
				cantFaltante = ingredientesFaltantes.get(ingrediente) - cantIngredienteEnInventario;
				if (cantIngredienteEnInventario != 0) {
					ingredientesFaltantes.put(ingrediente, cantFaltante);
				}
				int cantCrafteosNecesarios = (int) Math
						.ceil((double) cantFaltante / ingrediente.obtenerReceta().getCantidadDevuelta());
				tiempoRet += ingrediente.obtenerReceta().getTiempoCreacion() * cantCrafteosNecesarios;
			}
		}
		ingredientesFaltantes.keySet().removeAll(ingredientesTenidos);

		return new Receta(tiempoRet, recetaObjeto.getCantidadDevuelta(), ingredientesFaltantes);
	}

	public Receta faltantesParaCraftearDeCero(Objeto objeto) {
		Inventario inventario = new Inventario(this.getObjetos()); // copia del inventario
		return inventario.faltantesParaCraftearDeCeroRec(objeto);
	}

	private Receta faltantesParaCraftearDeCeroRec(Objeto objeto) {
		if (!objeto.esCrafteable()) {
			return objeto.obtenerReceta();
		}
		Map<Objeto, Integer> ingredientesFaltantes = new HashMap<Objeto, Integer>();
		Receta recetaObjeto = objeto.obtenerReceta();
		double tiempoRet = recetaObjeto.getTiempoCreacion();
		Map<Objeto, Integer> ingredientesNecesarios = recetaObjeto.getIngredientes();

		for (Objeto ingrediente : ingredientesNecesarios.keySet()) {
			int cantIngredienteEnInventario = this.objetos.getOrDefault(ingrediente, 0);
			int cantIngrediente = ingredientesNecesarios.get(ingrediente);
			if (cantIngredienteEnInventario > cantIngrediente) {
				this.objetos.put(ingrediente, cantIngredienteEnInventario - cantIngrediente);
			} else {
				if (cantIngredienteEnInventario != 0) {
					this.objetos.remove(ingrediente);
				}
				Receta recetaIngrediente = ingrediente.obtenerReceta();
				int cantCrafteosNecesarios = (int) Math.ceil((double) (cantIngrediente - cantIngredienteEnInventario)
						/ recetaIngrediente.getCantidadDevuelta());
				for (int i = 0; i < cantCrafteosNecesarios; i++) {
					Receta recetaFaltantes = this.faltantesParaCraftearDeCeroRec(ingrediente);
					tiempoRet += recetaFaltantes.getTiempoCreacion();
					for (Objeto subingrediente : recetaFaltantes.getIngredientes().keySet()) {
						if (ingredientesFaltantes.containsKey(subingrediente)) {
							ingredientesFaltantes.put(subingrediente, ingredientesFaltantes.get(subingrediente)
									+ recetaFaltantes.getIngredientes().get(subingrediente));
						} else {
							ingredientesFaltantes.put(subingrediente,
									recetaFaltantes.getIngredientes().get(subingrediente));
						}
					}
				}
				cantIngredienteEnInventario = cantCrafteosNecesarios * recetaIngrediente.getCantidadDevuelta()
						- (cantIngrediente - cantIngredienteEnInventario);
				if (cantIngredienteEnInventario > 0) {
					this.objetos.put(ingrediente, cantIngredienteEnInventario);
				}
			}
		}
		return new Receta(tiempoRet, recetaObjeto.getCantidadDevuelta(), ingredientesFaltantes);
	}

	public boolean craftear(Objeto objeto) {
		Receta recetaFaltantes = this.faltantesParaCraftear(objeto);
		if (!recetaFaltantes.getIngredientes().isEmpty()) {
			return false;
		}
		Map<Objeto, Integer> ingredientes = objeto.obtenerReceta().getIngredientes();
		for (Objeto ingrediente : ingredientes.keySet()) {
			int cantActualizada = this.objetos.get(ingrediente) - ingredientes.get(ingrediente);
			if (cantActualizada == 0) {
				this.objetos.remove(ingrediente);
			} else {
				this.objetos.put(ingrediente, cantActualizada);
			}
		}
		this.objetos.put(objeto, this.objetos.getOrDefault(objeto, 0) + objeto.obtenerReceta().getCantidadDevuelta());
		this.historial.agregarCrafteo(objeto);

		return true;
	}

	public HistorialCrafteos getHistorial() {
		return this.historial;
	}

	@Override
	public String toString() {
		return objetos.toString();
	}

}
