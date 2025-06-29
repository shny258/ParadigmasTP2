package tp_2;

import java.util.HashMap;
import java.util.Map;

public class RegistroObjetos {
	private Map<String, Objeto> registro;

	public RegistroObjetos() {
		this.registro = new HashMap<String, Objeto>();
	}

	public Objeto obtenerObjeto(String nombre) {
		return this.registro.get(nombre);
	}

	public void agregarObjeto(Objeto objeto) {
		this.registro.put(objeto.getNombre(), objeto);
	}

	@Override
	public String toString() {
		return registro.toString();
	}
	
	
}
