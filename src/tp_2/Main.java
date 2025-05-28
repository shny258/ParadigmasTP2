package tp_2;

import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Objeto madera = new IngredienteBasico("Madera");

		Objeto carbon = new IngredienteBasico("Carbon");

		Map<Objeto, Integer> ingredientesPalo = new HashMap<>();
		ingredientesPalo.put(madera, 2);
		Objeto palo = new Intermedio("Palo", new Receta(2, 1, ingredientesPalo));

		Map<Objeto, Integer> ingredientesAntorcha = new HashMap<>();
		ingredientesAntorcha.put(palo, 2);
		ingredientesAntorcha.put(carbon, 1);
		Objeto antorcha = new Intermedio("Antorcha", new Receta(5, 1, ingredientesAntorcha));

//		Map<Objeto, Integer> objetosInventario = new HashMap<>();
//		objetosInventario.put(antorcha, 1);
//		Inventario inventario = new Inventario(objetosInventario);
		System.out.println("RECETA ANTORCHA:\n" + antorcha.obtenerReceta());
		System.out.println("RECETA ANTORCHA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + antorcha.obtenerRecetaCompleta());
	}
}
