package tp_2;

import java.util.Map;

public class Intermedio extends Objeto {
	private Receta receta;

	public Intermedio(String nombre, Receta receta) {
		super(nombre);
		this.receta = receta;
	}
	
	public Intermedio(String nombre) {
		super(nombre);
	}
	
	public boolean agregarReceta(Receta receta) {
		if(this.receta == null) {
			this.receta = receta;
			return true;
		}
		return false;
	}

	public Receta obtenerReceta() {
		return this.receta;
	}

	public Receta obtenerRecetaCompleta() {
		return this.receta.obtenerRecetaCompleta();
	}

	@Override
	public boolean esCrafteable() {
		return true;
	}
	
	@Override
	public void mostrarArbolCrafteos() {
		System.out.println("x" + receta.getCantidadDevuelta() + " " + getNombre() + " - " + receta.getTiempoCreacion() + " minuto(s):");
		for(Objeto ingrediente : receta.getIngredientes().keySet()) {
			ingrediente.mostrarArbolCrafteos(receta.getCantIngrediente(ingrediente), 1);
		}
	}
	
	@Override
	protected void mostrarArbolCrafteos(int unidades, int nivel) {
		int cantCrafteos = (int)Math.ceil((double)unidades/receta.getCantidadDevuelta());
		for (int i = 0; i < nivel; i++)
			System.out.print("\t");
		System.out.print("x" + unidades + " " + getNombre() + " - " + receta.getTiempoCreacion() * cantCrafteos + " minuto(s):\n");
		for(Objeto ingrediente : receta.getIngredientes().keySet()) {
			ingrediente.mostrarArbolCrafteos(receta.getCantIngrediente(ingrediente) * cantCrafteos, nivel + 1);
		}
	}

	protected Receta obtenerRecetaCompleta(Map<Objeto, Integer> sobrantes) {
		return this.receta.obtenerRecetaCompleta(sobrantes);
	}
}
