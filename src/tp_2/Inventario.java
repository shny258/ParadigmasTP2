package tp_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prolog.ManejoProlog;

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
		if (!objeto.esCrafteable()) {
			return false;
		}
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

	private boolean puedoCraftear(Objeto objeto, int cant) {
		if (!objeto.esCrafteable()) {
			return this.objetos.getOrDefault(objeto, 0) >= cant;
		}

		Receta recetaObjeto = objeto.obtenerReceta();
		Map<Objeto, Integer> ingredientes = recetaObjeto.getIngredientes();
		int cantDevuelta = recetaObjeto.getCantidadDevuelta();
		int cantCrafteos = (int) Math.ceil((double) cant / cantDevuelta);

		Map<Objeto, Integer> ingredientesTotales = new HashMap<Objeto, Integer>();

		// agrego a un map todos los ingredientes que necesito para poder craftear cant
		// veces el objeto
		for (Objeto ingrediente : ingredientes.keySet()) {
			ingredientesTotales.put(ingrediente, ingredientes.get(ingrediente) * cantCrafteos);
		}

		for (Objeto ingrediente : ingredientesTotales.keySet()) {
			int cantEnInventario = this.objetos.getOrDefault(ingrediente, 0);
			int cantNecesaria = ingredientesTotales.get(ingrediente);
			if (cantEnInventario >= cantNecesaria) {
				this.objetos.put(ingrediente, cantEnInventario - cantNecesaria);
			} else {
				this.objetos.remove(ingrediente);
				int cantFaltante = cantNecesaria - cantEnInventario;

				if (!ingrediente.esCrafteable()) {
					return false;
				}
				if (!puedoCraftear(ingrediente, cantFaltante)) {
					return false;
				}
			}
		}
		int cantSobrantes = cant - (cantCrafteos * cantDevuelta);
		if(cantSobrantes > 0) {
			this.objetos.put(objeto, this.objetos.getOrDefault(objeto, 0) + cantSobrantes);			
		}

		return true;
	}

	public int cuantosPuedoCraftear(Objeto objeto) {
		int cant = 0;
		Inventario copiaInventario = new Inventario(this.getObjetos());

		while (copiaInventario.puedoCraftear(objeto, cant + 1)) {
			cant++;
			copiaInventario = new Inventario(this.getObjetos());
		}

		return cant;
	}

	public HistorialCrafteos getHistorial() {
		return this.historial;
	}

	public void quePuedoCraftear() {
		ManejoProlog.getInstance().quePuedoCraftear(this);
	}

	public String toJson() {
		String cadenaRet = new String("{\n");
		for (Objeto ingrediente : objetos.keySet()) {
			cadenaRet += "\t" + "\"" + ingrediente.getNombre() + "\":" + objetos.get(ingrediente) + ",\n";
		}
		cadenaRet += "}";
		return cadenaRet;
	}

	@Override
	public String toString() {
		return objetos.toString();
	}

}
