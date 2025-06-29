package main;

import java.util.HashMap;
import java.util.Map;

import tp_2.IngredienteBasico;
import tp_2.Intermedio;
import tp_2.Inventario;
import tp_2.Objeto;
import tp_2.Receta;

public class Main {
	public static void main(String[] args) {
		Objeto madera = new IngredienteBasico("Madera");

		Objeto carbon = new IngredienteBasico("Carbon");

		Map<Objeto, Integer> ingredientesPalo = new HashMap<>();
		ingredientesPalo.put(madera, 3);
		Objeto palo = new Intermedio("Palo", new Receta(2, 2, ingredientesPalo));

		Map<Objeto, Integer> ingredientesAntorcha = new HashMap<>();
		ingredientesAntorcha.put(palo, 5);
		ingredientesAntorcha.put(carbon, 1);
		Objeto antorcha = new Intermedio("Antorcha", new Receta(5, 1, ingredientesAntorcha));

		Map<Objeto, Integer> ingredientesTornillo = new HashMap<>();
		ingredientesTornillo.put(madera, 2);
		Objeto tornillo = new Intermedio("Tornillo", new Receta(3, 4, ingredientesTornillo));

		Map<Objeto, Integer> ingredientesTablon = new HashMap<>();
		ingredientesTablon.put(palo, 5);
		ingredientesTablon.put(tornillo, 1);
		Objeto tablon = new Intermedio("Tablon", new Receta(10, 1, ingredientesTablon));

		Map<Objeto, Integer> ingredientesMarco = new HashMap<>();
		ingredientesMarco.put(tablon, 2);
		ingredientesMarco.put(tornillo, 3);
		Objeto marco = new Intermedio("Marco", new Receta(8, 1, ingredientesMarco));

		Map<Objeto, Integer> ingredientesMesa = new HashMap<>();
		ingredientesMesa.put(tablon, 1);
		ingredientesMesa.put(palo, 2);
		ingredientesMesa.put(marco, 1);
		Objeto mesa = new Intermedio("Mesa", new Receta(15, 1, ingredientesMesa));

		System.out.println("RECETA ANTORCHA:\n" + antorcha.obtenerReceta());
		System.out.println(
				"RECETA ANTORCHA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + antorcha.obtenerRecetaCompleta());

		System.out.println("RECETA MESA:\n" + mesa.obtenerReceta());
		System.out.println("RECETA MESA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + mesa.obtenerRecetaCompleta());

		Map<Objeto, Integer> objetosInventario = new HashMap<>();
		objetosInventario.put(carbon, 1);
		objetosInventario.put(palo, 2);
		objetosInventario.put(tablon, 1);
		objetosInventario.put(marco, 1);
		Inventario inventario = new Inventario(objetosInventario);

		System.out.println("INVENTARIO:\n" + inventario + "\n");

		System.out.println("FALTANTES PARA CRAFTEAR ANTORCHA:\n" + inventario.faltantesParaCraftear(antorcha));

		inventario.craftear(mesa);
		System.out.println("CRAFTEAMOS MESA. INVENTARIO ACTUALIZADO:\n" + inventario + "\n");

		System.out.println("HISTORIAL DE CRAFTEOS:\n" + inventario.getHistorial());
	}
}
