package prolog;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import tp_2.*;

public class ManejoProlog {
	private static ManejoProlog instancia;

	private StringBuilder prologTodo;
	private StringBuilder prologElemento_basico;
	private StringBuilder prologIngrediente;
	private StringBuilder prologReceta;
	private StringBuilder prologTengo;
	private StringBuilder prologReglas;
	private String pathProlog;

	private ManejoProlog(String archPlFinal, String pathReglas) {
		JPL.setDefaultInitArgs(new String[] { "--home=C:/Program Files/swipl", "--quiet" });
		prologElemento_basico = new StringBuilder();
		prologIngrediente = new StringBuilder();
		prologReceta = new StringBuilder();
		prologTodo = new StringBuilder();
		prologReglas = new StringBuilder();
		escribirReglas(pathReglas);
		this.pathProlog = archPlFinal;
	}

	public static ManejoProlog getInstance(String archPlFinal, String pathReglas) {
		if (instancia == null) {
			instancia = new ManejoProlog(archPlFinal, pathReglas);
		}
		return instancia;
	}

	public static ManejoProlog getInstance() {
		return instancia;
	}

	public void tengo(Inventario inventario) {
		prologTengo = new StringBuilder();
		for (Objeto o : inventario.getObjetos().keySet()) {
			prologTengo.append(
					"tengo(\"" + o.getNombre().toLowerCase() + "\", " + inventario.getObjetos().get(o) + ").\n");
		}
	}

	public void elemento_basico(String nombre) {
		prologElemento_basico.append("elemento_basico(\"" + nombre.toLowerCase() + "\").\n");
	}

	public void ingrediente(Objeto obj) {
		String nombre = obj.getNombre();
		Receta receta = obj.obtenerReceta();
		for (Map.Entry<Objeto, Integer> ingrediente : receta.getIngredientes().entrySet()) {
			String nombreIngrediente = ingrediente.getKey().getNombre();
			int cantidad = ingrediente.getValue();
			prologIngrediente.append("ingrediente(\"" + nombre.toLowerCase() + "\",\"" + nombreIngrediente.toLowerCase()
					+ "\"," + cantidad + ").\n");
		}
		prologReceta.append("receta(\"" + nombre.toLowerCase() + "\"," + receta.getCantidadDevuelta() + ","
				+ receta.getTiempoCreacion() + ").\n");
	}

	public void escribirReglas(String pathing) {
		try {
			String contenido = Files.readString(Paths.get(pathing));
			prologReglas.append(contenido);
		} catch (IOException e) {
			System.out.println("Error para leer archivo " + pathing + ": " + e.getMessage());
		}
	}

	public void escribir() {
		prologTodo = new StringBuilder();
		prologTodo.append(prologElemento_basico.toString() + prologIngrediente.toString() + prologTengo.toString()
				+ prologReceta.toString() + prologReglas.toString());
		try (FileWriter writer = new FileWriter(pathProlog)) {
			writer.write(prologTodo.toString());
		} catch (IOException e) {
			System.out.println("Error escribiendo " + pathProlog + ": " + e.getMessage());
			return;
		}
	}

	public void quePuedoCraftear(Inventario inv) {
		tengo(inv);
		escribir();
		new Query("unload_file", new Term[] { new Atom(pathProlog) }).hasSolution();
		Query q = new Query("reconsult", new Term[] { new Atom(pathProlog) });
		if (!q.hasSolution()) {
			System.out.println("Failed to consult Prolog file");
			return;
		}
		Variable objeto = new Variable("Objeto");
		Variable cantidad = new Variable("Cantidad");
		Variable tiempo = new Variable("Tiempo");
		Query query = new Query("quePuedoCraftear", new Term[] { objeto, cantidad, tiempo });
		System.out.printf("%s╦%s╦%s╗%n", "═".repeat(31), "═".repeat(12), "═".repeat(12));
		System.out.printf("%-30s ║ %10s ║ %10s ║\n", "OBJETO", "CANTIDAD", "TIEMPO");
		System.out.printf("%s╬%s╬%s╣%n", "═".repeat(31), "═".repeat(12), "═".repeat(12));

		while (query.hasMoreSolutions()) {
			Map<String, Term> solution = query.nextSolution();
			System.out.printf("%-30s ║ %10s ║ %10s ║\n", solution.get("Objeto").name(),
					solution.get("Cantidad").toString(), solution.get("Tiempo").toString());
		}
		System.out.printf("%s╩%s╩%s╝%n", "═".repeat(31), "═".repeat(12), "═".repeat(12));
	}
}
