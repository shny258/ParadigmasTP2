package tp_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IngredienteBasico extends Objeto {
	private String nombre;
	private static final int CANT_INGREDIENTE = 1;
	private static final int CANT_DEVUELTA = 1;
	private static final int TIEMPO_CREACION_ING_BASICO = 0; //NO SE PUEDE CRAFTEAR, SE EXTRAE.

	public IngredienteBasico(String nombre) {
		this.nombre = nombre;
		//super(nombre);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public double getTiempoCreacion() {
		return TIEMPO_CREACION_ING_BASICO;
	}
	
	public Receta obtenerReceta() {
		Map<Objeto,Integer> mapRet = new HashMap<Objeto,Integer>();
		mapRet.put(this, CANT_INGREDIENTE);
		return new Receta(TIEMPO_CREACION_ING_BASICO, CANT_DEVUELTA, mapRet);
	}
	
	public Receta obtenerRecetaCompleta() {
		return this.obtenerReceta();
	}
	
	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		return this.obtenerReceta();
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IngredienteBasico other = (IngredienteBasico) obj;
		return Objects.equals(nombre, other.nombre);
	}

}
