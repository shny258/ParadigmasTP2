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
	
	@Override
	public void mostrarArbolCrafteos() {
		System.out.println("x1 " + getNombre() + " - " + "Ingrediente Básico");
	}
	
	@Override
	protected void mostrarArbolCrafteos(int unidades, int nivel) {
		for (int i = 0; i < nivel; i++)
			System.out.print("\t");
		System.out.print("x" + unidades + " " + getNombre() + " - " + "Ingrediente Básico.\n");
	}

	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		return this.obtenerReceta();
	}

}
