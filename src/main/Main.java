package main;

import prolog.ManejoProlog;
import tp_2.*;

public class Main {
	public static void main(String[] args) {
		RegistroObjetos registroObjetos = new RegistroObjetos();
		ManejadorArchivos manejador = new ManejadorArchivos();
		ManejoProlog prolog = new ManejoProlog();
		String pathInventario = "archivos/inventario.json";
		String pathRecetas = "archivos/recetas.json";
		String pathProlog = "archivos/crafting.pl";
		String pathPrologReglas = "archivos/reglasProlog.txt";
		
		try {
			manejador.cargarRecetasDesdeJson(pathRecetas, registroObjetos, prolog);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR LAS RECETAS");
			return;
		}
		Inventario inventario;
		try {
			inventario = manejador.cargarInventarioDesdeJson(pathInventario, registroObjetos, prolog);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO");
			return;
		}
		prolog.escribirReglas(pathPrologReglas);
		prolog.escribir(pathProlog);
		/*
		Objeto antorcha = registroObjetos.obtenerObjeto("Antorcha");
		System.out.println("RECETA ANTORCHA:\n" + antorcha.obtenerReceta());
		System.out.println(
				"RECETA ANTORCHA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + antorcha.obtenerRecetaCompleta());

		Objeto mesa = registroObjetos.obtenerObjeto("Mesa");
		System.out.println("RECETA MESA:\n" + mesa.obtenerReceta());
		System.out.println("RECETA MESA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + mesa.obtenerRecetaCompleta());

		System.out.println("INVENTARIO:\n" + inventario + "\n");

		System.out.println("FALTANTES PARA CRAFTEAR ANTORCHA:\n" + inventario.faltantesParaCraftear(antorcha));
		System.out.println("FALTANTES PARA CRAFTEAR ANTORCHA DESDE CERO:\n" + inventario.faltantesParaCraftearDeCero(antorcha));

		inventario.craftear(mesa);
		System.out.println("CRAFTEAMOS MESA. INVENTARIO ACTUALIZADO:\n" + inventario + "\n");
		System.out.println("FALTANTES PARA CRAFTEAR MESA DESDE CERO:\n" + inventario.faltantesParaCraftearDeCero(mesa));

		System.out.println("HISTORIAL DE CRAFTEOS:\n" + inventario.getHistorial());
*/
		prolog.quePuedoCraftear(pathProlog);
	}
}
