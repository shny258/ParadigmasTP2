package main;

import java.util.Scanner;

import prolog.ManejoProlog;
import tp_2.*;

public class Main {
	public static void main(String[] args) {
		RegistroObjetos registroObjetos = new RegistroObjetos();
		ManejadorArchivos manejador = new ManejadorArchivos();
		String pathInventario = "archivos/inventario.json";
		String pathRecetas = "archivos/recetas.json";
		String pathProlog = "archivos/crafting.pl";
		String pathPrologReglas = "archivos/reglasProlog.txt";
		ManejoProlog.getInstance(pathProlog, pathPrologReglas);
		
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
		String opcionString;
		Objeto objSolicitado;
		while(opcionInt !=9) {
			mostrarMenu();
			System.out.print("Ingrese Opcion: ");
			opcionInt = sc.nextInt();
			sc.nextLine();
			switch(opcionInt) {
			case 1:
		        System.out.println("Cual Objeto ?");
		        for(String objeto : registroObjetos.getRegistro().keySet()) {
		        	System.out.println(objeto);
		        }
		        opcionString = sc.nextLine();
		        objSolicitado = registroObjetos.obtenerObjeto(opcionString);
				System.out.println("RECETA " + opcionString.toUpperCase() + ":\n" + objSolicitado.obtenerReceta());
		        break;
		    case 2:
		    	 System.out.println("Cual Objeto ?");
			        for(String objeto : registroObjetos.getRegistro().keySet()) {
			        	System.out.println(objeto);
			        }
			        opcionString = sc.nextLine();
			        objSolicitado = registroObjetos.obtenerObjeto(opcionString);
					System.out.println("RECETA "+opcionString.toUpperCase()+" COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + objSolicitado.obtenerRecetaCompleta());
		        break;
		    case 3:
		    	System.out.println("Cual Objeto ?");
		    	for(String objeto : registroObjetos.getRegistro().keySet()) {
		        	System.out.println(objeto);
		        }
		    	opcionString = sc.nextLine();
		        objSolicitado = registroObjetos.obtenerObjeto(opcionString);
		        System.out.printf("%s\n", (inventario.faltantesParaCraftear(objSolicitado).getIngredientes().isEmpty())?"No hace falta ningún recurso para craftear este objeto, todos están en el inventario.":("FALTANTES PARA CRAFTEAR "+opcionString.toUpperCase()+":\n" + inventario.faltantesParaCraftear(objSolicitado)));
		        break;
		    case 4:
		    	System.out.println("Cual Objeto ?");
		    	for(String objeto : registroObjetos.getRegistro().keySet()) {
		        	System.out.println(objeto);
		        }
		    	opcionString = sc.nextLine();
		        objSolicitado = registroObjetos.obtenerObjeto(opcionString);
		        System.out.printf("%s\n", (inventario.faltantesParaCraftearDeCero(objSolicitado).getIngredientes().isEmpty())?"No hace falta ningún recurso para craftear este objeto, todos están en el inventario.":("FALTANTES PARA CRAFTEAR "+opcionString.toUpperCase()+" desde cero:\n" + inventario.faltantesParaCraftearDeCero(objSolicitado)));
		        break;
		    case 6:
		    	System.out.println("Cual Objeto ?");
		    	for(String objeto : registroObjetos.getRegistro().keySet()) {
		        	System.out.println(objeto);
		        }
		    	opcionString = sc.nextLine();
		    	objSolicitado = registroObjetos.obtenerObjeto(opcionString);
		    	System.out.printf("%s\n", (inventario.craftear(objSolicitado))?("CRAFTEAMOS "+opcionString+". INVENTARIO ACTUALIZADO:\n" + inventario + "\n"):"No dispones de los items requeridos para realizar el crafteo");
		    	break;
		    case 7:
		    	System.out.println("HISTORIAL DE CRAFTEOS:\n" + inventario.getHistorial());
		    	break;
		    case 8:
		    	inventario.quePuedoCraftear();
		    	break;
		    case 9:
		    	manejador.generarJsonInventario("archivos/inventario-out.json", inventario);
		    	break;
		    case 10:
		    	mostrarMenu();
		    	break;
		    case 11:
		    	System.out.println("Inventario:\n"+ inventario);
		    	break;
		    default:
		        System.out.println("Opción inválida");
		        break;
			}
		}
		sc.close();
        System.out.println("Chau Chau");
	}
	
	public static void mostrarMenu() {
		System.out.println("1. ¿Qué necesito para craftear un objeto?");
		System.out.println("2. ¿Qué necesito para craftear un objeto desde cero?");
		System.out.println("3. ¿Qué me falta para craftear un objeto?");
		System.out.println("4. ¿Qué me falta para craftear un objeto desde cero?");
		//System.out.println("5. ¿Cuántos puedo craftear?"); Eventualmente
		System.out.println("6. Realizar el crafteo indicado");
		System.out.println("7. Ver el historial de crafteos");
		System.out.println("8. ¿Qué puedo craftear con mi inventario actual?");
		System.out.println("9. Salir.");
		System.out.println("10. Mostrar esta ayuda.");
		System.out.println("11. Mostrar inventario.");
	}
}
