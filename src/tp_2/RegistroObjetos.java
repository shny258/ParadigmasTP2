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
		return this.registro.get(nombre.toLowerCase());
	}

	public void agregarObjeto(Objeto objeto) {
		this.registro.put(objeto.getNombre().toLowerCase(), objeto);
	}

	public Map<String, Objeto> getRegistro() {
		return new HashMap<String, Objeto>(registro);
	}

	@Override
	public String toString() {
		return registro.toString();
	}

	public Objeto mostrarListaObjetosYSeleccionar(Scanner sc) {
		System.out.println("═════════════════════════════════════════════════════════╗");
		System.out.println("                  ¿PARA QUÉ OBJETO?:                     ║");
		System.out.println("═════════════════════════════════════════════════════════╝");
		for (String objeto : this.registro.keySet()) {
			System.out.println(this.registro.get(objeto));
		}
		System.out.println("══════════════════════════════════════════════════════════");
		System.out.print("Nombre del objeto: ");
		String opcionString = sc.nextLine().toLowerCase();
		Objeto objSolicitado;
		while ((objSolicitado = this.registro.get(opcionString)) == null) {
			System.out.println("El objeto \"" + opcionString + "\" no existe. Ingrese el nombre nuevamente:");
			opcionString = sc.nextLine().toLowerCase();
		}
		return objSolicitado;
	}
}
