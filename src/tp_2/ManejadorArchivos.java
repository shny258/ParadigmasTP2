package tp_2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
//import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

import prolog.*;

public class ManejadorArchivos {
	public void cargarRecetasDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject json = new JSONObject(contenido);
		ManejoProlog prolog = ManejoProlog.getInstance();

		// CREAR INGREDIENTES BASICOS
		try {
			JSONArray basicos = json.getJSONArray("ingredientes_basicos");
			for (int i = 0; i < basicos.length(); i++) {
				String nombre = basicos.getString(i);
				IngredienteBasico basico = new IngredienteBasico(nombre);
				registroObjetos.agregarObjeto(basico);

				prolog.elemento_basico(nombre);
			}
		} catch (Exception e) {
			// no hay ingredientes basicos
		}

		// CREAR INTERMEDIOS
		JSONArray recetas = json.getJSONArray("recetas");
		for (int i = 0; i < recetas.length(); i++) {
			JSONObject recetaJson = recetas.getJSONObject(i);
			String nombreIntermedio = recetaJson.getString("nombre");
			int cantidadDevuelta = recetaJson.getInt("cantidad_creada");
			if(cantidadDevuelta<=0)
				throw new IllegalArgumentException();
			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
			Map<Objeto, Integer> ingredientes = new HashMap<>();
			for (String nombreIngrediente : ingredientesJson.keySet()) {
				int cantidadIngrediente = ingredientesJson.getInt(nombreIngrediente);
				if(cantidadIngrediente<=0)
					throw new IllegalArgumentException();
				Objeto ingrediente = registroObjetos.obtenerObjeto(nombreIngrediente);
				if (ingrediente == null) {
					ingrediente = new Intermedio(nombreIngrediente);
					registroObjetos.agregarObjeto(ingrediente);
				}
				ingredientes.put(ingrediente, cantidadIngrediente);
			}
			double tiempoCrafteo = recetaJson.getDouble("tiempo");
			if(tiempoCrafteo<=0)
				throw new IllegalArgumentException();
			Receta receta = new Receta(tiempoCrafteo, cantidadDevuelta, ingredientes);
			Intermedio intermedio = (Intermedio) registroObjetos.obtenerObjeto(nombreIntermedio);
			if (intermedio == null) {
				registroObjetos.agregarObjeto(new Intermedio(nombreIntermedio, receta));
				prolog.ingrediente(new Intermedio(nombreIntermedio, receta));
			} else {
				intermedio.agregarReceta(receta);
				prolog.ingrediente(intermedio);
			}
		}
	}

	public Inventario cargarInventarioDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject json = new JSONObject(contenido);
		// cargamos las mesas que habilitan nuevas recetas
		JSONArray mesas = json.getJSONArray("mesas");
		String pathMesas = Main.PATH_A_MESAS;
		List<String> mesasEnInventario = new ArrayList<String>();
		for (int i = 0; i < mesas.length(); i++) {
			String nombre = mesas.getString(i);
			try {
				this.cargarRecetasDesdeJson(pathMesas + nombre + ".json", registroObjetos);
				mesasEnInventario.add(nombre);
			} catch (Exception e) {
				System.err.println("Mesa " + nombre + " inexistente");
			}
		}
		// cargamos el inventario
		JSONObject objetos = json.getJSONObject("objetos");
		Map<Objeto, Integer> objetosInventario = this.cargarInventario(objetos, registroObjetos);

		return new Inventario(objetosInventario, mesasEnInventario);
	}

	private Map<Objeto, Integer> cargarInventario(JSONObject objetos, RegistroObjetos registroObjetos)
			throws Exception {
		JSONObject inventarioJson = objetos;

		Map<Objeto, Integer> objetosInventario = new HashMap<>();
		for (String nombreObjeto : inventarioJson.keySet()) {
			int cantidadObjeto = inventarioJson.getInt(nombreObjeto);
			if(cantidadObjeto<=0)
				throw new IllegalArgumentException();
			Objeto objeto = registroObjetos.obtenerObjeto(nombreObjeto);
			if (objeto == null) {
				throw new Exception("Ingrediente " + nombreObjeto + " inexistente.\n");
			}
			objetosInventario.put(objeto, objetosInventario.getOrDefault(objeto, 0) + cantidadObjeto);
		}
		return objetosInventario;
	}

	public void generarJsonInventario(String path, Inventario inventario) {
		try {
			// Crear el objeto JSON desde el String
			JSONObject jsonObject = new JSONObject(inventario.toJson());
			// Crear la ruta completa al archivo
			String rutaCompleta = path;
			// Escribir el JSON en archivo
			try (FileWriter file = new FileWriter(rutaCompleta)) {
				file.write(jsonObject.toString(4)); // 4 = cantidad de espacios de indentado
				file.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validarExtensionJson(String path) throws Exception {
		if (!path.toLowerCase().endsWith(".json")) {
			throw new Exception("El archivo " + path + " no tiene extensión .json");
		}
	}

	public JSONObject cargarJson(String path) throws IOException, JSONException {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		return new JSONObject(contenido);
	}

	public void validarEstructura(JSONObject json) throws Exception {
		if (!json.has("recetas")) {
			throw new Exception("El JSON no contiene la clave 'recetas'.");
		}
		// Validar tipo de datos de cada clave
		if (!(json.get("recetas") instanceof JSONArray)) {
			throw new Exception("La clave 'recetas' no es un arreglo.");
		}
	}

	public void validarRecetas(JSONArray recetas) throws Exception {
		String error = new String();
		for (int i = 0; i < recetas.length(); i++) {
			JSONObject receta = recetas.getJSONObject(i);

			if (!receta.has("nombre")) {
				error += ("Falta 'nombre' en receta #" + i + "\n");
			}
			if (!receta.has("cantidad_creada")) {
				error += ("Falta 'cantidad_creada' en receta " + receta.getString("nombre") + "\n");
			}
			if (!receta.has("ingredientes")) {
				error += ("Falta 'ingredientes' en receta " + receta.getString("nombre") + "\n");
			}
			if (!receta.has("tiempo")) {
				error += ("Falta 'tiempo' en receta " + receta.getString("nombre") + "\n");
			}
		}
		if (!(error.isBlank()))
			throw new Exception(error);
	}
}
