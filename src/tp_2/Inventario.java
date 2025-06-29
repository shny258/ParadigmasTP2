package tp_2;

import java.util.ArrayList;
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
		return objetos;
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
			} else if (cantIngredienteEnInventario != 0) {
				cantFaltante = ingredientesFaltantes.get(ingrediente) - cantIngredienteEnInventario;
				ingredientesFaltantes.put(ingrediente, cantFaltante);
			}
			int cantCrafteosNecesarios = (int) Math
					.ceil((double) cantFaltante / ingrediente.obtenerReceta().getCantidadDevuelta());
			tiempoRet += ingrediente.obtenerReceta().getTiempoCreacion() * cantCrafteosNecesarios;
		}
		ingredientesFaltantes.keySet().removeAll(ingredientesTenidos);

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
		this.objetos.put(objeto, objeto.obtenerReceta().getCantidadDevuelta());
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
