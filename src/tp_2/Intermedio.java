package tp_2;

import java.util.Map;

public class Intermedio extends Objeto {
	private Receta receta;

	public Intermedio(String nombre, Receta receta) {
		super(nombre);
		this.receta = receta;
	}

	public Receta obtenerReceta() {
		return this.receta;
	}

	public Receta obtenerRecetaCompleta() {
		return this.receta.obtenerRecetaCompleta();
	}

	@Override
	protected boolean esCrafteable() {
		return true;
	}

	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		return this.receta.obtenerRecetaCompleta(sobrantes);
	}
}
