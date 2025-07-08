package tp_2;

public class Crafteo {
	private int turno;
	private Objeto objeto;
	
	public Crafteo(int turno, Objeto objeto) {
		this.turno = turno;
		this.objeto = objeto;
	}

	public int getTurno() {
		return turno;
	}

	public Objeto getObjeto() {
		return objeto;
	}
	
}
