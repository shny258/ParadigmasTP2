package tp_2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventario {
	private Map<Objeto, Integer> objetos;

	public Inventario(Map<Objeto, Integer> objetos) {
		this.objetos = objetos;
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
				int faltantesActualizado = ingredientesFaltantes.get(ingrediente) - cantIngredienteEnInventario;
				ingredientesFaltantes.put(ingrediente, faltantesActualizado);
				int cantCrafteosNecesarios = (int) Math
						.ceil((double) faltantesActualizado / ingrediente.obtenerReceta().getCantidadDevuelta());
				tiempoRet += ingrediente.obtenerReceta().getTiempoCreacion() * cantCrafteosNecesarios;
			}
		}
		ingredientesFaltantes.keySet().removeAll(ingredientesTenidos);

		return new Receta(tiempoRet, recetaObjeto.getCantidadDevuelta(), ingredientesFaltantes);
	}
}
