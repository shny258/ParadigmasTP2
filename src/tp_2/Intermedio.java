package tp_2;

import java.util.Map;
import java.util.Objects;

public class Intermedio extends Objeto {
	private String nombre;
	private Receta receta;

	public Intermedio(String nombre, Receta receta) {
		this.nombre = nombre;
		//super(nombre);
		this.receta = receta;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public Receta obtenerReceta() {
		return this.receta;
	}

	public Receta obtenerRecetaCompleta() {
		return this.receta.obtenerRecetaCompleta();
	}
	
	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		return this.receta.obtenerRecetaCompleta(sobrantes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre, receta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intermedio other = (Intermedio) obj;
		return Objects.equals(nombre, other.nombre) && Objects.equals(receta, other.receta);
	}
	
}
