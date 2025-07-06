package tp_2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RegistroObjetos {
	private Map<String, Objeto> registro;

	public RegistroObjetos() {
		this.registro = new HashMap<String, Objeto>();
	}

	public Objeto obtenerObjeto(String nombre) {
		return this.registro.get(nombre);
	}

	public void agregarObjeto(Objeto objeto) {
		this.registro.put(objeto.getNombre(), objeto);
	}

	public Map<String, Objeto> getRegistro() {
		return new HashMap<String, Objeto>(registro);
	}

	@Override
	public String toString() {
		return registro.toString();
	}

	public Objeto mostrarListaObjetosYSeleccionar(Scanner sc) {
		System.out.println("Cual Objeto ?");
		for (String objeto : registro.keySet()) {
			System.out.println(objeto);
		}

		String opcionString = sc.nextLine();
		Objeto objSolicitado;
		while ((objSolicitado = registro.get(opcionString)) == null) {
			System.out.println("El objeto con el nombre solicitado no existe");
			opcionString = sc.nextLine();
		}
		return objSolicitado;
	}
}
