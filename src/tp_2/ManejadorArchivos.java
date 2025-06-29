package tp_2;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ManejadorArchivos {
	public void cargarRecetasDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject json = new JSONObject(contenido);

		// CREAR INGREDIENTES BASICOS
		JSONArray basicos = json.getJSONArray("ingredientes_basicos");
		for (int i = 0; i < basicos.length(); i++) {
			String nombre = basicos.getString(i);
			IngredienteBasico basico = new IngredienteBasico(nombre);
			registroObjetos.agregarObjeto(basico);
		}

		// CREAR INTERMEDIOS
		JSONArray recetas = json.getJSONArray("recetas");
		for (int i = 0; i < recetas.length(); i++) {
			JSONObject recetaJson = recetas.getJSONObject(i);
			String nombreIntermedio = recetaJson.getString("nombre");
			int cantidadDevuelta = recetaJson.getInt("cantidad_creada");
			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
			Map<Objeto, Integer> ingredientes = new HashMap<>();
			for (String nombreIngrediente : ingredientesJson.keySet()) {
				int cantidadIngrediente = ingredientesJson.getInt(nombreIngrediente);
				Objeto ingrediente = registroObjetos.obtenerObjeto(nombreIngrediente);
				if (ingrediente == null) {
					throw new Exception("Ingrediente " + nombreIngrediente + " inexistente.\n");
				}
				ingredientes.put(ingrediente, cantidadIngrediente);
			}
			double tiempoCrafteo = recetaJson.getDouble("tiempo");
			Receta receta = new Receta(tiempoCrafteo, cantidadDevuelta, ingredientes);
			registroObjetos.agregarObjeto(new Intermedio(nombreIntermedio, receta));
		}
	}
}
