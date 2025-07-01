package tp_2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import prolog.*;

public class ManejadorArchivos {
	public void cargarRecetasDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject json = new JSONObject(contenido);
		ManejoProlog prolog = ManejoProlog.getInstance();
		
		// CREAR INGREDIENTES BASICOS
		JSONArray basicos = json.getJSONArray("ingredientes_basicos");
		for (int i = 0; i < basicos.length(); i++) {
			String nombre = basicos.getString(i);
			IngredienteBasico basico = new IngredienteBasico(nombre);
			registroObjetos.agregarObjeto(basico);
			
			prolog.elemento_basico(nombre);
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
			prolog.ingrediente(new Intermedio(nombreIntermedio, receta));
		}
	}

	public Inventario cargarInventarioDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject inventarioJson = new JSONObject(contenido);

		Map<Objeto, Integer> objetosInventario = new HashMap<>();
		for (String nombreObjeto : inventarioJson.keySet()) {
			int cantidadObjeto = inventarioJson.getInt(nombreObjeto);
			Objeto objeto = registroObjetos.obtenerObjeto(nombreObjeto);
			if (objeto == null) {
				throw new Exception("Ingrediente " + nombreObjeto + " inexistente.\n");
			}
			objetosInventario.put(objeto, objetosInventario.getOrDefault(objeto, 0) + cantidadObjeto);
		}
		ManejoProlog pl = ManejoProlog.getInstance();
		pl.tengo(new Inventario(objetosInventario));
		pl.escribir();
		return new Inventario(objetosInventario);
	}
	
	public void generarJsonInventario(String path, Inventario inventario) {
        try {
            // Crear el objeto JSON desde el String
            JSONObject jsonObject = new JSONObject(inventario.toJson());
            // Crear la ruta completa al archivo
            String rutaCompleta = path;
            // Escribir el JSON en archivo
            try (FileWriter file = new FileWriter(rutaCompleta)) {
                file.write(jsonObject.toString(4));  // 4 = cantidad de espacios de indentado
                file.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
