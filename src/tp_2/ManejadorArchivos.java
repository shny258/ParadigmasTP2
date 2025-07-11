package tp_2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
//import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

import prolog.*;

public class ManejadorArchivos {
//	public void cargarRecetasDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
//		try {
//			validarExtensionJson(path); // primero la extensión
//
//			String contenido = new String(Files.readAllBytes(Paths.get(path)));
//			if (contenido.isBlank()) {
//				throw new Exception("El archivo " + path + " está vacío.");
//			}
//
//			JSONObject json = new JSONObject(contenido); // recién ahora parseo JSON
//			validarEstructura(json); // después validaciones propias
//			JSONArray recetas = json.getJSONArray("recetas");
//			validarRecetas(recetas);
//
//		} catch (NoSuchFileException e) {
//			System.err.println("El archivo " + path + " no existe.");
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//
//		String contenido = new String(Files.readAllBytes(Paths.get(path)));
//		JSONObject json = new JSONObject(contenido);
//		ManejoProlog prolog = ManejoProlog.getInstance();
//
//		// CREAR INGREDIENTES BASICOS
//		JSONArray basicos = json.getJSONArray("ingredientes_basicos");
//		for (int i = 0; i < basicos.length(); i++) {
//			String nombre = basicos.getString(i);
//			IngredienteBasico basico = new IngredienteBasico(nombre);
//			registroObjetos.agregarObjeto(basico);
//
//			prolog.elemento_basico(nombre);
//		}
//
//		// CREAR INTERMEDIOS
//		JSONArray recetas = json.getJSONArray("recetas");
//		for (int i = 0; i < recetas.length(); i++) {
//			JSONObject recetaJson = recetas.getJSONObject(i);
//			String nombreIntermedio = recetaJson.getString("nombre");
//			int cantidadDevuelta = recetaJson.getInt("cantidad_creada");
//			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
//			Map<Objeto, Integer> ingredientes = new HashMap<>();
//			for (String nombreIngrediente : ingredientesJson.keySet()) {
//				int cantidadIngrediente = ingredientesJson.getInt(nombreIngrediente);
//				Objeto ingrediente = registroObjetos.obtenerObjeto(nombreIngrediente);
//				if (ingrediente == null) {
//					throw new Exception("Ingrediente " + nombreIngrediente + " inexistente.\n");
//				}
//				ingredientes.put(ingrediente, cantidadIngrediente);
//			}
//			double tiempoCrafteo = recetaJson.getDouble("tiempo");
//			Receta receta = new Receta(tiempoCrafteo, cantidadDevuelta, ingredientes);
//			registroObjetos.agregarObjeto(new Intermedio(nombreIntermedio, receta));
//			prolog.ingrediente(new Intermedio(nombreIntermedio, receta));
//		}
//	}
//
//	public void cargarRecetasDesdeJsonAleatorio(String path, RegistroObjetos registroObjetos) throws Exception {
//
//		try {
//			validarExtensionJson(path); // primero la extensión
//
//			String contenido = new String(Files.readAllBytes(Paths.get(path)));
//			if (contenido.isBlank()) {
//				throw new Exception("El archivo " + path + " está vacío.");
//			}
//
//			JSONObject json = new JSONObject(contenido); // recién ahora parseo JSON
//			validarEstructura(json); // después validaciones propias
//			JSONArray recetas = json.getJSONArray("recetas");
//			validarRecetas(recetas);
//
//		} catch (NoSuchFileException e) {
//			System.err.println("El archivo " + path + " no existe.");
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		// El funcionamiento de este algoritmo se basa en el algoritmo de Kahn para el
//		// orden topológico
//		String contenido = new String(Files.readAllBytes(Paths.get(path)));
//		JSONObject json = new JSONObject(contenido);
//		ManejoProlog prolog = ManejoProlog.getInstance();
//		Map<String, List<String>> objetosConDependencias = new HashMap<>();
//		Map<String, JSONObject> mapaRecetas = new HashMap<>();
//		JSONArray recetas = json.getJSONArray("recetas");
//
//		for (int i = 0; i < recetas.length(); i++) {
//			JSONObject recetaJson = recetas.getJSONObject(i);
//			String nombreIntermedio = recetaJson.getString("nombre");
//			// Guardamos como clave el nombre del objeto y como valor todo el JSONObject
//			// asociado al mismo, para no volver
//			// a leer el archivo
//			mapaRecetas.put(nombreIntermedio, recetaJson);
//			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
//			objetosConDependencias.put(nombreIntermedio, new ArrayList<String>());
//			for (String nombreIngrediente : ingredientesJson.keySet()) {
//				// Si tiene al menos un ingrediente, guarda la lista, sino lo guarda vacio
//				objetosConDependencias.get(nombreIntermedio).add(nombreIngrediente);
//
//				if (!objetosConDependencias.containsKey(nombreIngrediente)) {
//					objetosConDependencias.put(nombreIngrediente, new ArrayList<>());
//				}
//			}
//		}
//
//		Queue<String> sinDependencias = new LinkedList<>();
//		List<String> ordenTopologico = new ArrayList<>();
//
//		// Primero guardamos en sinDependencias todos los objetos simples (cables,
//		// circuitos, capacitores, etc)
//		for (String nombreObjeto : objetosConDependencias.keySet()) {
//			if (objetosConDependencias.get(nombreObjeto).isEmpty()) {
//				sinDependencias.add(nombreObjeto);
//			}
//		}
//
//		while (!(sinDependencias.isEmpty())) {
//
//			String objetoCola = sinDependencias.poll(); // saca el primer elemento de la cola
//			ordenTopologico.add(objetoCola);
//			for (String s : objetosConDependencias.keySet()) {
//				List<String> lista = objetosConDependencias.get(s);
//				// Sacamos un objeto de sinDependencias y lo eliminamos de todas las listas que
//				// contengan dicho elemento
//				if (lista.contains(objetoCola)) {
//					// Si algun objeto ya no tiene mas dependencias, lo agregamos a sinDependencias
//					lista.remove(objetoCola);
//					if (lista.isEmpty())
//						sinDependencias.add(s);
//				}
//			}
//		}
//		// El ciclo anterior se repite hasta que sinDependencias quede vacío
//
//		for (String s : ordenTopologico) {
//			// Trabajamos con el JSONObject que guardamos como valor antes
//			JSONObject recetaJson = mapaRecetas.get(s);
//			String nombreIntermedio = recetaJson.getString("nombre");
//			int cantidadDevuelta = recetaJson.getInt("cantidad_creada");
//			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
//			if (ingredientesJson.length() == 0) // Que sea 0 significa que no tiene ingredientes -> es básico
//			{
//				IngredienteBasico basico = new IngredienteBasico(nombreIntermedio);
//				registroObjetos.agregarObjeto(basico);
//				prolog.elemento_basico(nombreIntermedio);
//			}
//
//			else // Sino es intermedio
//			{
//				Map<Objeto, Integer> ingredientes = new HashMap<>();
//				for (String nombreIngrediente : ingredientesJson.keySet()) {
//					int cantidadIngrediente = ingredientesJson.getInt(nombreIngrediente);
//					Objeto ingrediente = registroObjetos.obtenerObjeto(nombreIngrediente);
//					if (ingrediente == null) {
//						throw new Exception("Ingrediente " + nombreIngrediente + " inexistente.\n");
//					}
//					ingredientes.put(ingrediente, cantidadIngrediente);
//				}
//				double tiempoCrafteo = recetaJson.getDouble("tiempo");
//				Receta receta = new Receta(tiempoCrafteo, cantidadDevuelta, ingredientes);
//				registroObjetos.agregarObjeto(new Intermedio(nombreIntermedio, receta));
//				prolog.ingrediente(new Intermedio(nombreIntermedio, receta));
//			}
//		}
//	}

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
			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
			Map<Objeto, Integer> ingredientes = new HashMap<>();
			for (String nombreIngrediente : ingredientesJson.keySet()) {
				int cantidadIngrediente = ingredientesJson.getInt(nombreIngrediente);
				Objeto ingrediente = registroObjetos.obtenerObjeto(nombreIngrediente);
				if (ingrediente == null) {
					ingrediente = new Intermedio(nombreIngrediente);
					registroObjetos.agregarObjeto(ingrediente);
				}
				ingredientes.put(ingrediente, cantidadIngrediente);
			}
			double tiempoCrafteo = recetaJson.getDouble("tiempo");
			Receta receta = new Receta(tiempoCrafteo, cantidadDevuelta, ingredientes);
			registroObjetos.agregarObjeto(new Intermedio(nombreIntermedio, receta));
			prolog.ingrediente(new Intermedio(nombreIntermedio, receta));
		}
	}
	
	public void cargarRecetasDesdeJsonAleatorio(String path, RegistroObjetos registroObjetos) throws Exception{
		try {
		    validarExtensionJson(path); // primero la extensión
		    
		    String contenido = new String(Files.readAllBytes(Paths.get(path)));
		    if (contenido.isBlank()) {
		        throw new Exception("El archivo " + path + " está vacío.");
		    }

		    JSONObject json = new JSONObject(contenido); // recién ahora parseo JSON
		    validarEstructura(json);                     // después validaciones propias
		    JSONArray recetas = json.getJSONArray("recetas");
		    validarRecetas(recetas);
		    
		}	catch (NoSuchFileException e) {
			System.err.println("El archivo " + path + " no existe.");}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		//El funcionamiento de este algoritmo se basa en el algoritmo de Kahn para el orden topológico
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject json = new JSONObject(contenido);
		ManejoProlog prolog = ManejoProlog.getInstance();
		Map<String, List<String>> objetosConDependencias = new HashMap<>();
		Map<String, JSONObject> mapaRecetas = new HashMap<>();
		JSONArray recetas = json.getJSONArray("recetas");
		
		for (int i = 0; i < recetas.length(); i++) {
			JSONObject recetaJson = recetas.getJSONObject(i);
			String nombreIntermedio = recetaJson.getString("nombre");
			//Guardamos como clave el nombre del objeto y como valor todo el JSONObject asociado al mismo, para no volver
			//a leer el archivo
			mapaRecetas.put(nombreIntermedio, recetaJson);
			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");
			objetosConDependencias.put(nombreIntermedio, new ArrayList<String>());
			for (String nombreIngrediente : ingredientesJson.keySet()) {
				//Si tiene al menos un ingrediente, guarda la lista, sino lo guarda vacio
				objetosConDependencias.get(nombreIntermedio).add(nombreIngrediente);
				
			    if (!objetosConDependencias.containsKey(nombreIngrediente)) {
			        objetosConDependencias.put(nombreIngrediente, new ArrayList<>());
			    }
			}			
		}
		
		Queue<String> sinDependencias = new LinkedList<>();
		List<String> ordenTopologico = new ArrayList<>();
		
		//Primero guardamos en sinDependencias todos los objetos simples (cables, circuitos, capacitores, etc)
		for (String nombreObjeto : objetosConDependencias.keySet()) {
		    if (objetosConDependencias.get(nombreObjeto).isEmpty()) {
		        sinDependencias.add(nombreObjeto);
		    }
		}
		
		while(!(sinDependencias.isEmpty()))
		{
			
			String objetoCola = sinDependencias.poll(); //saca el primer elemento de la cola
			ordenTopologico.add(objetoCola); 
			for(String s : objetosConDependencias.keySet())
			{
				List<String> lista=objetosConDependencias.get(s);
				//Sacamos un objeto de sinDependencias y lo eliminamos de todas las listas que contengan dicho elemento
				if(lista.contains(objetoCola))
				{
					//Si algun objeto ya no tiene mas dependencias, lo agregamos a sinDependencias
					lista.remove(objetoCola);
					if(lista.isEmpty())
						sinDependencias.add(s);
				}
			}
		}
		//El ciclo anterior se repite hasta que sinDependencias quede vacío

		for (String s : ordenTopologico) {
			//Trabajamos con el JSONObject que guardamos como valor antes
			JSONObject recetaJson = mapaRecetas.get(s);
			String nombreIntermedio = recetaJson.getString("nombre");
			int cantidadDevuelta = recetaJson.getInt("cantidad_creada");
			JSONObject ingredientesJson = recetaJson.getJSONObject("ingredientes");			
			if(ingredientesJson.length() == 0) //Que sea 0 significa que no tiene ingredientes -> es básico
			{
				IngredienteBasico basico = new IngredienteBasico(nombreIntermedio);
				registroObjetos.agregarObjeto(basico);				
				prolog.elemento_basico(nombreIntermedio);
			}
			
			else //Sino es intermedio
			{
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
			} else {
				intermedio.agregarReceta(receta);
				prolog.ingrediente(intermedio);
			}
		}
	}

	public Inventario cargarInventarioDesdeJson(String path, RegistroObjetos registroObjetos) throws Exception {
		String contenido = new String(Files.readAllBytes(Paths.get(path)));
		JSONObject json = new JSONObject(contenido);
		//cargamos las mesas que habilitan nuevas recetas
		JSONArray mesas = json.getJSONArray("mesas");
		String pathMesas = "archivos/recetas/mesas/";
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
		//cargamos el inventario
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
