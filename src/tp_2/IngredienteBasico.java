package tp_2;

import java.util.HashMap;
import java.util.Map;

public class IngredienteBasico extends Objeto {
	private static final int CANT_INGREDIENTE = 1;
	private static final int CANT_DEVUELTA = 1;
	private static final int TIEMPO_CREACION_ING_BASICO = 0; // NO SE PUEDE CRAFTEAR, SE EXTRAE.

	public IngredienteBasico(String nombre) {
		super(nombre);
	}

	public Receta obtenerReceta() {
		Map<Objeto, Integer> mapRet = new HashMap<Objeto, Integer>();
		mapRet.put(this, CANT_INGREDIENTE);
		return new Receta(TIEMPO_CREACION_ING_BASICO, CANT_DEVUELTA, mapRet);
	}

	public Receta obtenerRecetaCompleta() {
		return this.obtenerReceta();
	}

	@Override
	protected boolean esCrafteable() {
		return false;
	}

	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		return this.obtenerReceta();
	}

}
