package prolog;

import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import tp_2.*;

public class ManejoProlog {
	public static void main(String[] args) {		
		RegistroObjetos registroObjetos = new RegistroObjetos();
		ManejadorArchivos manejador = new ManejadorArchivos();
		Inventario inventario;
		
		try {
			manejador.cargarRecetasDesdeJson("archivos/recetas.json", registroObjetos);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR LAS RECETAS");
			return;
		}
		
		try {
			inventario = manejador.cargarInventarioDesdeJson("archivos/inventario.json", registroObjetos);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO");
			return;
		}
		
		// Generar inventario
		StringBuilder prologFacts = new StringBuilder();
		for (Map.Entry<Objeto, Integer> entry : inventario.getObjetos().entrySet()) {
			Objeto nombre = entry.getKey();
			Integer cantidad = entry.getValue();

			prologFacts.append("tengo(\"" + nombre.getNombre().toLowerCase() + "\", " + cantidad + ").\n");
		}

		//generar recetas y materiales basicos
		for(Map.Entry<String, Objeto> entry : registroObjetos.getRegistro().entrySet()) {
			String nombre = entry.getKey();
			Objeto objeto = entry.getValue();
			
			if (objeto instanceof IngredienteBasico) {
				prologFacts.append("elemento_basico(\"" + nombre.toLowerCase() + "\").\n");
			}else if (objeto instanceof Intermedio) {
				Intermedio intermedio = (Intermedio) objeto;
	            Receta receta = intermedio.obtenerReceta();
	            
				for(Map.Entry<Objeto, Integer> ingrediente : receta.getIngredientes().entrySet()) {
					String nombreIngrediente = ingrediente.getKey().getNombre();
					int cantidad = ingrediente.getValue();
					
					prologFacts.append("ingrediente(\"" + nombre + "\",\"" + nombreIngrediente + "\"," + cantidad + ").\n");
				}
			}
		}

		// After building prologFacts
		try (FileWriter writer = new FileWriter(".\\src\\prolog\\crafting.pl")) {
			writer.write(prologFacts.toString());
		} catch (IOException e) {
			System.out.println("Error writing crafting.pl: " + e.getMessage());
			return;
		}

		// Load Prolog query
		String prologProgram = prologFacts.toString();
		Query q = new Query("consult", new Term[] {new Atom(".\\src\\prolog\\crafting.pl")});
		if (!q.hasSolution()) {
			System.out.println("Failed to consult Prolog file");
			return;
		}
		
		// Query craftable objects
		Variable X = new Variable("X");
		Query query = new Query("puedo_craftear", new Term[] {X});
		System.out.println("Productos que puedo craftear:");
		while (query.hasMoreSolutions()) {
			Map<String, Term> solution = query.nextSolution();
			System.out.println(solution.get("X").toString());
		}

	}
}



