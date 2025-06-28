package tp_2;

import java.util.HashMap;
import java.util.Map;

public class IngredienteBasico extends Objeto {
//	private String nombre;
	private static final int CANT_INGREDIENTE = 1;
	private static final int CANT_DEVUELTA = 1;
	private static final int TIEMPO_CREACION_ING_BASICO = 0; //NO SE PUEDE CRAFTEAR, SE EXTRAE.

	public IngredienteBasico(String nombre) {
//		this.nombre = nombre;
		super(nombre);
	}
	
//	public String getNombre() {
//		return this.nombre;
//	}
		
	public Receta obtenerReceta() {
		Map<Objeto,Integer> mapRet = new HashMap<Objeto,Integer>();
		mapRet.put(this, CANT_INGREDIENTE);
		return new Receta(TIEMPO_CREACION_ING_BASICO, CANT_DEVUELTA, mapRet);
	}
	
	public Receta obtenerRecetaCompleta() {
		return this.obtenerReceta();
	}
	
//	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
//		return this.obtenerReceta();
//	}
	
}
