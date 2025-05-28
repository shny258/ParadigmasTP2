package tp_2;

public abstract class Objeto {
//	private static int contador = 1;
//	private int id;
//	
//
//	public Objeto() {
//		this.id = contador++;
//	}
//	
	public abstract Receta obtenerReceta();
	public abstract Receta obtenerRecetaCompleta();
	public abstract String getNombre();
	public abstract boolean equals(Object obj);
    public abstract int hashCode();
	
//	@Override
//	public int hashCode() {
//		return Objects.hash(id);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Objeto other = (Objeto) obj;
//		return id == other.id;
//	}
}
