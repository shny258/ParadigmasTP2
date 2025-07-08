package main;

import java.util.Scanner;
import prolog.ManejoProlog;
import tp_2.*;

public class Main {
	public static final String NOMBRE_ARCHIVO_RECETAS = "recetas"; // MODIFICAR PARA USAR OTRO ARCHIVO DE RECETAS
	public static final String NOMBRE_ARCHIVO_RECETAS_TEST = "testrecetas"; // PATH DONDE ESTA EL ARCHIVO DE RECETAS
	public static final String NOMBRE_ARCHIVO_INVENTARIO = "inventario_con_mesas"; // MODIFICAR PARA USAR OTRO ARCHIVO
																					// DE INVENTARIO

	public static final String PATH_A_RECETAS = "archivos/recetas/"; // PATH DONDE ESTA EL ARCHIVO DE RECETAS

	public static final String PATH_A_INVENTARIO = "archivos/inventario/"; // PATH DONDE ESTA EL ARCHIVO DE INVENTARIO
	public static final String PATH_A_MESAS = "archivos/recetas/mesas/"; //PATH DONDE ESTARAN LAS RECETAS QUE HABILITA CADA MESA

	public static final String PATH_REGLAS_PROLOG = "archivos/reglasProlog.pl";
	public static final String PATH_ARCHIVO_PROLOG = "archivos/crafting.pl";

	public static final String PATH_INVENTARIO_FINAL = "archivos/inventario/inventario-out.json";

	public static void main(String[] args) {
		RegistroObjetos registroObjetos = new RegistroObjetos();
		ManejadorArchivos manejador = new ManejadorArchivos();
		String pathRecetas = PATH_A_RECETAS + NOMBRE_ARCHIVO_RECETAS + ".json";
		String pathInventario = PATH_A_INVENTARIO + NOMBRE_ARCHIVO_INVENTARIO + ".json";
		ManejoProlog.getInstance();

		try {
			manejador.cargarRecetasDesdeJson(pathRecetas, registroObjetos);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR LAS RECETAS");
			return;
		}
		Inventario inventario;
		try {
			inventario = manejador.cargarInventarioDesdeJson(pathInventario, registroObjetos);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO");
			return;
		}
		Scanner sc = new Scanner(System.in);
		int opcionInt = 0;
		Objeto objSolicitado;
		while (opcionInt != 12) {
			mostrarMenu();

			System.out.print("Ingrese una opcion: ");

			String opcion = sc.nextLine();
			try {
				opcionInt = Integer.parseInt(opcion);
				switch (opcionInt) {
				case 1:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.println("RECETA " + objSolicitado.getNombre().toUpperCase() + ":\n"
							+ objSolicitado.obtenerReceta());
					break;
				case 2:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.println("RECETA " + objSolicitado.getNombre().toUpperCase()
							+ " COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + objSolicitado.obtenerRecetaCompleta());
					break;
				case 3:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.printf("%s\n",
							(inventario.faltantesParaCraftear(objSolicitado).getIngredientes().isEmpty())
									? "No hace falta ningún recurso para craftear este objeto, todos están en el inventario."
									: ("FALTANTES PARA CRAFTEAR " + objSolicitado.getNombre().toUpperCase() + ":\n"
											+ inventario.faltantesParaCraftear(objSolicitado)));
					break;
				case 4:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.printf("%s\n",
							(inventario.faltantesParaCraftearDeCero(objSolicitado).getIngredientes().isEmpty())
									? "Todos los ingredientes para craftear este objeto están en el inventario o se pueden craftear con él."
									: ("FALTANTES PARA CRAFTEAR " + objSolicitado.getNombre().toUpperCase()
											+ " desde cero:\n"
											+ inventario.faltantesParaCraftearDeCero(objSolicitado)));
					break;
				case 5:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.println("Se pueden craftear " + inventario.cuantosPuedoCraftear(objSolicitado) + " de "
							+ objSolicitado.getNombre());
					break;
				case 6:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.printf("%s\n",
							(inventario.craftear(objSolicitado))
									? ("\nx" + objSolicitado.obtenerReceta().getCantidadDevuelta() + " de "
											+ objSolicitado.getNombre() + " crafteado/a. Inventario actualizado:\n\n"
											+ inventario + "\n")
									: "No dispones de los items requeridos para realizar el crafteo");
					break;
				case 7:
					System.out.println("═════════════════════════════════════════════════════════╗");
					System.out.println("                HISTORIAL DE CRAFTEOS                    ║");
					System.out.println("═════════════════════════════════════════════════════════╝");
					System.out.println(inventario.getHistorial());
					break;
				case 8:
					inventario.quePuedoCraftear();
					break;
				case 9:
					System.out.println("═════════════════════════════════════════════════════════╗");
					System.out.println("                      INVENTARIO                         ║");
					System.out.println("═════════════════════════════════════════════════════════╝");
					System.out.println(inventario);
					break;
				case 10:
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					System.out.println("Arbol de Crafteo de " + objSolicitado.getNombre() + ":\n");
					objSolicitado.mostrarArbolCrafteos();
					break;
				case 11:
					int opcionModificarInt = 0;
					System.out.println("Opciones:\n1. Agregar unidades de objeto al inventario\n2. Sacar unidades"
							+ " de objeto del inventario\n3. Eliminar todas las unidades de objeto del inventario\n");
					while (opcionModificarInt < 1 || opcionModificarInt > 3) {
						String opcionModificar = sc.nextLine();
						try {
							opcionModificarInt = Integer.parseInt(opcionModificar);
							if (opcionModificarInt < 1 || opcionModificarInt > 3)
								System.out.println("Entrada inválida. Por favor, ingrese opción 1, 2 o 3.\n");
						} catch (NumberFormatException e) {
							System.out.println("Entrada inválida. Por favor, ingrese opción 1, 2 o 3.\n");
						}
					}
					objSolicitado = registroObjetos.mostrarListaObjetosYSeleccionar(sc);
					if (opcionModificarInt != 3) {
						System.out.printf("¿Cuántas unidades queres %s?:\n",
								(opcionModificarInt == 1) ? "agregar" : "sacar");
						int cantModificarInt = 0;
						while (cantModificarInt <= 0) {
							String cantModificar = sc.nextLine();
							try {
								cantModificarInt = Integer.parseInt(cantModificar);
								if (cantModificarInt <= 0)
									System.out.println("Entrada inválida. Por favor, ingrese un número mayor a cero.");
							} catch (NumberFormatException e) {
								System.out.println("Entrada inválida. Por favor, ingrese un número mayor a cero.");
							}
						}
						if (opcionModificarInt == 1) {
							inventario.agregar(objSolicitado, cantModificarInt);
						} else if (inventario.sacar(objSolicitado, cantModificarInt)) {
							System.out.println(
									cantModificarInt + " unidades de " + objSolicitado.getNombre() + " eliminadas.");
						} else {
							System.out.println(
									"No se pueden sacar " + cantModificarInt + " de " + objSolicitado.getNombre()
											+ " porque no se cuenta con esa cantidad en el inventario.");
						}
					} else {
						if (inventario.sacar(objSolicitado)) {
							System.out.println("Todas las unidades de " + objSolicitado.getNombre()
									+ " fueron eliminadas del inventario.");
						} else {
							System.out.println("No se pudo eliminar el objeto " + objSolicitado.getNombre()
									+ " porque no se encuentra en el inventario.");
						}
					}
					break;
				case 12:
					manejador.generarJsonInventario(PATH_INVENTARIO_FINAL, inventario);
					break;
				default:
					System.out.println("Opción inválida");
					break;
				}
				if (opcionInt != 12) {
					System.out.println("\nPresione ENTER para continuar...");
					sc.nextLine();
				}
			} catch (NumberFormatException e) {
				System.out.println("Entrada inválida. Por favor, ingrese un número.");
			}
		}
		sc.close();
		System.out.println("Chau Chau");

	}

	public static void mostrarMenu() {

		System.out.println("═════════════════════════════════════════════════════════╗");
		System.out.println("                          MENU                           ║");
		System.out.println("══╦══════════════════════════════════════════════════════╣");
		System.out.println(" 1║ ¿Qué necesito para craftear un objeto?               ║");
		System.out.println(" 2║ ¿Qué necesito para craftear un objeto desde cero?    ║");
		System.out.println(" 3║ ¿Qué me falta para craftear un objeto?               ║");
		System.out.println(" 4║ ¿Qué me falta para craftear un objeto desde cero?    ║");
		System.out.println(" 5║ ¿Cuántos puedo craftear?                             ║");
		System.out.println(" 6║ Craftear                                             ║");
		System.out.println(" 7║ Ver el historial de crafteos                         ║");
		System.out.println(" 8║ ¿Qué puedo craftear con mi inventario actual?        ║");
		System.out.println(" 9║ Mostrar inventario                                   ║");
		System.out.println("10║ Mostrar árbol de crafteos                            ║");
		System.out.println("11║ Modificar inventario                                 ║");
		System.out.println("12║ Salir y exportar inventario a JSON                   ║");
		System.out.println("══╩══════════════════════════════════════════════════════╝");
	}
}
