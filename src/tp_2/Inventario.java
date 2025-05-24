package tp_2;

import java.util.Map;

public class Inventario {
	private Map<Objeto, Integer> objetos;

	public Inventario(Map<Objeto, Integer> objetos) {
		this.objetos = objetos;
	}

	public Map<Objeto, Integer> getObjetos() {
		return objetos;
	}
}
