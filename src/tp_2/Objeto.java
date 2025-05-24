package tp_2;

import java.util.Objects;

public abstract class Objeto {
	private static int contador = 1;
	private int id;
	

	public Objeto() {
		this.id = contador++;
	}
	
	public abstract double getTiempoCreacion();
	public abstract Intermedio obtenerReceta();
	public abstract Intermedio obtenerRecetaCompleta();
	public abstract String getNombre();

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return id == other.id;
	}
}
