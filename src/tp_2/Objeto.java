package tp_2;

import java.util.Map;
import java.util.Objects;

public abstract class Objeto {
	private String nombre;

	public Objeto(String nombre) {
		this.nombre = nombre;
	}

	public abstract Receta obtenerReceta();

	public abstract Receta obtenerRecetaCompleta();

	protected abstract boolean esCrafteable();

	protected abstract Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes);

	public String getNombre() {
		return nombre;
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
		Objeto other = (Objeto) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return nombre;
	}
}
