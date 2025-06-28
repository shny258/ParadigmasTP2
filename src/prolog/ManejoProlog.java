package prolog;

import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

import tp_2.IngredienteBasico;
import tp_2.Intermedio;
import tp_2.Inventario;
import tp_2.Objeto;
import tp_2.Receta;


public class ManejoProlog {
	public static void main(String[] args) {
		// Existing object creation (same as before)
		Objeto madera = new IngredienteBasico("Madera");
		Objeto carbon = new IngredienteBasico("Carbon");
		Map<Objeto, Integer> ingredientesPalo = new HashMap<>();
		ingredientesPalo.put(madera, 3);
		Objeto palo = new Intermedio("Palo", new Receta(2, 2, ingredientesPalo));
		
		Map<Objeto, Integer> ingredientesTornillo = new HashMap<>();
		ingredientesTornillo.put(madera, 2);
		Objeto tornillo = new Intermedio("Tornillo", new Receta(3, 4, ingredientesTornillo));
		
		Map<Objeto, Integer> ingredientesTablon = new HashMap<>();
		ingredientesTablon.put(palo, 5);
		ingredientesTablon.put(tornillo, 1);
		Objeto tablon = new Intermedio("Tablon", new Receta(10, 1, ingredientesTablon));
		
		Map<Objeto, Integer> ingredientesMarco = new HashMap<>();
		ingredientesMarco.put(tablon, 2);
		ingredientesMarco.put(tornillo, 4); // Modified recipe
		Objeto marco = new Intermedio("Marco", new Receta(8, 1, ingredientesMarco));
		
		Map<Objeto, Integer> ingredientesMesa = new HashMap<>();
		ingredientesMesa.put(tablon, 1);
		ingredientesMesa.put(palo, 2);
		ingredientesMesa.put(marco, 1);
		Objeto mesa = new Intermedio("Mesa", new Receta(15, 1, ingredientesMesa));
		
		// Set up inventory
		Map<Objeto, Integer> objetosInventario = new HashMap<>();
		objetosInventario.put(carbon, 1);
		objetosInventario.put(palo, 2);
		objetosInventario.put(madera, 30);
		Inventario inventario = new Inventario(objetosInventario);
		
		// Generate Prolog facts dynamically
		StringBuilder prologFacts = new StringBuilder();
		for (Map.Entry<Objeto, Integer> entry : inventario.getObjetos().entrySet()) {
			prologFacts.append("tengo(").append(entry.getKey().getNombre().toLowerCase())
			.append(", ").append(entry.getValue()).append(").\n");
		}
		prologFacts.append("elemento_basico(madera).\n");
		prologFacts.append("elemento_basico(carbon).\n");
		prologFacts.append("ingrediente(palo, madera, 3).\n");
		prologFacts.append("ingrediente(antorcha, palo, 5).\n");
		prologFacts.append("ingrediente(antorcha, carbon, 1).\n");
		prologFacts.append("ingrediente(tornillo, madera, 2).\n");
		prologFacts.append("ingrediente(tablon, palo, 5).\n");
		prologFacts.append("ingrediente(tablon, tornillo, 1).\n");
		prologFacts.append("ingrediente(marco, tablon, 2).\n");
		prologFacts.append("ingrediente(marco, tornillo, 4).\n");
		prologFacts.append("ingrediente(mesa, tablon, 1).\n");
		prologFacts.append("ingrediente(mesa, palo, 2).\n");
		prologFacts.append("ingrediente(mesa, marco, 1).\n");
		prologFacts.append("puedo_craftear(Objeto) :- ingrediente(Objeto, Ingrediente, Cantidad), "
				+ "tengo(Ingrediente, CantDisponible), CantDisponible >= Cantidad, "
				+ "(elemento_basico(Ingrediente) ; puedo_craftear(Ingrediente)).\n");
		
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



