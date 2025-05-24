package tp_2;

import java.util.HashMap;
import java.util.Map;

public class IngredienteBasico extends Objeto {
	private String nombre;

	private static final int TIEMPO_CREACION_ING_BASICO = 0; //NO SE PUEDE CRAFTEAR, SE EXTRAE.

	public IngredienteBasico(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public double getTiempoCreacion() {
		return TIEMPO_CREACION_ING_BASICO;
	}
	
	public Intermedio obtenerReceta() {
		Map<Objeto,Integer> mapRet = new HashMap<>();
		mapRet.put(this, 1);
		return new Intermedio(this.getNombre(),0, mapRet);
	}
	
	public Intermedio obtenerRecetaCompleta() {
		return this.obtenerReceta();
	}
	
	
}
