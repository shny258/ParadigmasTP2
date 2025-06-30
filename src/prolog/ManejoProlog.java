package prolog;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import tp_2.*;

public class ManejoProlog {
	StringBuilder prologFacts;

	public ManejoProlog() {
		prologFacts = new StringBuilder();
	}

	public void tengo(String nombre, int cantidad) {
		prologFacts.append("tengo(\"" + nombre.toLowerCase() + "\", " + cantidad + ").\n");
	}

	public void elemento_basico(String nombre) {
		prologFacts.append("elemento_basico(\"" + nombre.toLowerCase() + "\").\n");
	}

	public void ingrediente(Objeto obj) {
		String nombre = obj.getNombre();
		Receta receta = obj.obtenerReceta();
		for (Map.Entry<Objeto, Integer> ingrediente : receta.getIngredientes().entrySet()) {
			String nombreIngrediente = ingrediente.getKey().getNombre();
			int cantidad = ingrediente.getValue();
			prologFacts.append("ingrediente(\"" + nombre.toLowerCase() + "\",\"" + nombreIngrediente.toLowerCase()
					+ "\"," + cantidad + ").\n");
		}
		prologFacts.append("receta(\"" + nombre.toLowerCase() + "\"," + receta.getCantidadDevuelta() + ","
				+ receta.getTiempoCreacion() + ").\n");
	}

	public void escribir(String pathing) {
		try (FileWriter writer = new FileWriter(pathing)) {
			writer.write(prologFacts.toString());
		} catch (IOException e) {
			System.out.println("Error escribiendo crafting.pl: " + e.getMessage());
			return;
		}
	}

	public void escribirReglas(String pathing) {
		try {
			String contenido = Files.readString(Paths.get(pathing));
			prologFacts.append(contenido);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void quePuedoCraftear() {
		Query q = new Query("consult", new Term[] { new Atom(".\\src\\prolog\\crafting.pl") });
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
			System.out.println(solution.get("Objeto").toString() + solution.get("Cantidad").toString() + solution.get("Tiempo").toString());
		}
	}
}
