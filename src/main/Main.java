package main;

import java.util.HashMap;
import java.util.Map;

import tp_2.*;

public class Main {
	public static void main(String[] args) {
		RegistroObjetos registroObjetos = new RegistroObjetos();
		ManejadorArchivos manejador = new ManejadorArchivos();
		try {
			manejador.cargarRecetasDesdeJson("archivos/testrecetas.json", registroObjetos);
			manejador = null;
			Objeto antorcha = registroObjetos.obtenerObjeto("Antorcha");
			System.out.println("RECETA ANTORCHA:\n" + antorcha.obtenerReceta());
			System.out.println(
					"RECETA ANTORCHA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + antorcha.obtenerRecetaCompleta());

			Objeto mesa = registroObjetos.obtenerObjeto("Mesa");
			System.out.println("RECETA MESA:\n" + mesa.obtenerReceta());
			System.out
					.println("RECETA MESA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + mesa.obtenerRecetaCompleta());

			Map<Objeto, Integer> objetosInventario = new HashMap<>();
			objetosInventario.put(registroObjetos.obtenerObjeto("Carbon"), 1);
			objetosInventario.put(registroObjetos.obtenerObjeto("Palo"), 2);
			objetosInventario.put(registroObjetos.obtenerObjeto("Tablon"), 1);
			objetosInventario.put(registroObjetos.obtenerObjeto("Marco"), 1);
			Inventario inventario = new Inventario(objetosInventario);

			System.out.println("INVENTARIO:\n" + inventario + "\n");

			System.out.println("FALTANTES PARA CRAFTEAR ANTORCHA:\n" + inventario.faltantesParaCraftear(antorcha));

			inventario.craftear(mesa);
			System.out.println("CRAFTEAMOS MESA. INVENTARIO ACTUALIZADO:\n" + inventario + "\n");

			System.out.println("HISTORIAL DE CRAFTEOS:\n" + inventario.getHistorial());
		} catch (Exception e) {
			System.out.println("ERROR AL CARGAR LAS RECETAS");
		}
	}
}
