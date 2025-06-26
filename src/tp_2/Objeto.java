package tp_2;

import java.util.Map;

public abstract class Objeto {

	public abstract Receta obtenerReceta();
	public abstract Receta obtenerRecetaCompleta();
	protected abstract Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes);
	public abstract String getNombre();
	public abstract boolean equals(Object obj);
    public abstract int hashCode();
	
	

}
