package interfaz;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import modelo.entidad.Coche;
import modelo.entidad.Motor;
import modelo.negocio.GestorCoche;
import modelo.persistencia.DaoObjetoCoche;

public class InterfazUsuario {

	private GestorCoche gc;
	private String rutaArchivo = DaoObjetoCoche.FICHERO;
	private Scanner scInt = new Scanner(System.in);
	private Scanner sc = new Scanner(System.in);

	public InterfazUsuario(String rutaArchivo) {
		super();
		this.rutaArchivo = rutaArchivo;
	}

	public void mostrarInterfaz() {

		System.out.println("-------------");
		System.out.println("APP DE COCHES");
		System.out.println("-------------");

		seleccionarOpcion();

	}
	
	public void seleccionarOpcion() {
		byte opcion = printMenu();

		switch (opcion) {
		case 0:
			salir();
			break;
		case 1:
			introducirCoche();
			break;
		case 2:
			mostrarCocheById();
			break;
		case 3:
			borrarCochePorId();
			break;
		case 4:
			listarTodosLosCoches();
			break;
		}
	}

	private void borrarCochePorId() {
		gc = new GestorCoche(rutaArchivo);
		System.out.print("Escribe el ID del coche que deseas borrar: ");
		String idSeleccionado = sc.nextLine();
		try {
			byte resultado = gc.borrar(idSeleccionado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public byte printMenu() {

		byte opcion = 0;
		boolean flag = false;

		do {
			System.out.println("Seleccione una opción");
			System.out.println("");
			System.out.println("- 0. Salir");
			System.out.println("- 1. Introducir coche");
			System.out.println("- 2. Obtener coche por ID");
			System.out.println("- 3. Borrar coche por ID ");
			System.out.println("- 4. Listar todos los coches");
			System.out.println("");
			System.out.print("Opción: ");
			opcion = scInt.nextByte();
			try {
				flag = validarOpcion(opcion);
			} catch (InputMismatchException e) {
				System.out.println("Introduce un carácter válido");
			}
		} while (!flag);

		return opcion;
	}

	private boolean validarOpcion(byte opcion) throws InputMismatchException {
		try {
			if (opcion >= 0 && opcion <= 4) {
				return true;
			}
			return false;
		} catch (InputMismatchException e) {
			throw e;
		}
	}

	private void listarTodosLosCoches() {
		gc = new GestorCoche(rutaArchivo);

		try {
			gc.getListaCoches().forEach(coche -> {
				System.out.println("=====================================");
				System.out.println("           Detalles del Coche        ");
				System.out.println("=====================================");
				System.out.println("ID Coche : " + coche.getId());
				System.out.println("Marca    : " + coche.getMarca());
				System.out.println("Modelo   : " + coche.getModelo());
				System.out.println("Motor    : " + coche.getMotor());
				System.out.println("-------------------------------------");
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void mostrarCocheById() {
		gc = new GestorCoche(rutaArchivo);
		System.out.print("Escribe el ID del coche que deseas buscar: ");
		String idSeleccionado = sc.nextLine();
		Coche c = null;
		try {
			c = gc.getCocheById(idSeleccionado);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("=====================================");
		System.out.println("           Detalles del Coche        ");
		System.out.println("=====================================");
		System.out.println("ID Coche : " + c.getId());
		System.out.println("Marca    : " + c.getMarca());
		System.out.println("Modelo   : " + c.getModelo());
		System.out.println("Motor    : " + c.getMotor());
		System.out.println("-------------------------------------");

	}

	private void introducirCoche() {
		gc = new GestorCoche(rutaArchivo);

		System.out.println("Rellena los datos del coche que quieres introducir en la base de datos: ");
		System.out.print("Introduce la marca: ");
		String marca = sc.nextLine();
		System.out.print("Introduce el modelo: ");
		String modelo = sc.nextLine();
		System.out.println("Seleccione un motor: ");
		System.out.println("- 1. Gasolina");
		System.out.println("- 2. Diésel");
		System.out.println("- 3. Hidrógeno");
		byte opcionMotor = scInt.nextByte();
		Motor motor = null; // The local variable motor may not have been initialized

		switch (opcionMotor) {
		case 1:
			motor = Motor.GASOLINA;
			break;
		case 2:
			motor = Motor.DIÉSEL;
			break;
		case 3:
			motor = Motor.HIDRÓGENO;
			break;
		}

		Coche c = new Coche(marca, modelo, motor);

		byte resultadoValidacion = gc.validarCoche(c);

		while (resultadoValidacion != 3) {

			if (resultadoValidacion == 0) {
				System.out.println("Marca y modelo no válidos.");
				System.out.print("Por favor, escribe una marca: ");
				c.setMarca(sc.nextLine());
				System.out.print("Por favor, escribe un modelo: ");
				c.setModelo(sc.nextLine());
			}

			if (resultadoValidacion == 1) {
				System.out.println("No has escrito una marca. Por favor, escribe una marca: ");
				c.setMarca(sc.nextLine());
			}

			if (resultadoValidacion == 2) {
				System.out.println("No has escrito un modelo. Por favor, escribe un modelo: ");
				c.setModelo(sc.nextLine());
			}

			resultadoValidacion = gc.validarCoche(c);
		}

		try {
			gc.validarCoche(c);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void salir() {
		System.out.println("");
		System.out.println("-------------------");
		System.out.println("PROGRAMA FINALIZADO");
		System.out.println("-------------------");
	}
}
