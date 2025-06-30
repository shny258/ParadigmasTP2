package prolog;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import tp_2.*;

public class ManejoProlog {
	StringBuilder prologTodo;
	StringBuilder prologElemento_basico;
	StringBuilder prologIngrediente;
	StringBuilder prologReceta;
	StringBuilder prologTengo;
	
	public ManejoProlog() {
		prologElemento_basico = new StringBuilder();
		prologIngrediente = new StringBuilder();
		prologReceta = new StringBuilder();
		prologTengo = new StringBuilder();
		prologTodo = new StringBuilder();
	}

	public void tengo(String nombre, int cantidad) {
		prologTengo.append("tengo(\"" + nombre.toLowerCase() + "\", " + cantidad + ").\n");
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
			prologTodo.append(prologElemento_basico.toString() + prologIngrediente.toString() + prologTengo.toString() + prologReceta.toString());
			prologTodo.append(contenido);
		} catch (IOException e) {
			System.out.println("Error para leer archivo " + pathing + ": " + e.getMessage());
		}
	}

	public void escribir(String pathing) {
		try (FileWriter writer = new FileWriter(pathing)) {
			writer.write(prologTodo.toString());
		} catch (IOException e) {
			System.out.println("Error escribiendo " + pathing + ": " + e.getMessage());
			return;
		}
	}

	
	public void quePuedoCraftear(String pathing) {
		Query q = new Query("consult", new Term[] { new Atom(pathing) });
		if (!q.hasSolution()) {
			System.out.println("Failed to consult Prolog file");
			return;
		}
		Variable objeto = new Variable("Objeto");
		Variable cantidad = new Variable("Cantidad");
		Variable tiempo = new Variable("Tiempo");
		Query query = new Query("quePuedoCraftear", new Term[] { objeto, cantidad, tiempo });
		System.out.println("Productos que puedo craftear:");
		while (query.hasMoreSolutions()) {
			Map<String, Term> solution = query.nextSolution();
			System.out.printf("%30s | %5s | %5s\n",solution.get("Objeto").toString(),solution.get("Cantidad").toString(),solution.get("Tiempo").toString());		}
	}
}
