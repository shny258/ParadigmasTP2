package tp_2;

public class Intermedio extends Objeto {
//	private String nombre;
	private Receta receta;

	public Intermedio(String nombre, Receta receta) {
//		this.nombre = nombre;
		super(nombre);
		this.receta = receta;
	}
	
//	public String getNombre() {
//		return this.nombre;
//	}

	public Receta obtenerReceta() {
		return this.receta;
	}

	public Receta obtenerRecetaCompleta() {
		return this.receta.obtenerRecetaCompleta();
	}
	
//	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
//		return this.receta.obtenerRecetaCompleta(sobrantes);
//	}
}
