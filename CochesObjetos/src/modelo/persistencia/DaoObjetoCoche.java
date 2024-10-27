package modelo.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modelo.entidad.Coche;

public class DaoObjetoCoche {

	public static final String FICHERO = "coches.dat";

	private String nombreFichero;

	public String getNombreFichero() {
		return nombreFichero;
	}

	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public DaoObjetoCoche(String nombreFichero) {
		super();
	    this.nombreFichero = nombreFichero;
	    File file = new File(nombreFichero);
	    File idFile = new File("contadorID.txt");

	    try {
	        if (!file.exists()) file.createNewFile();
	        if (!idFile.exists()) idFile.createNewFile();
	    } catch (IOException e) {
	        System.err.println("Error creando archivos: " + e.getMessage());
	    }
	}

	/**
	 * Método privado que accede al fichero para escribir, en una línea, un coche
	 * pasado por parámetro.
	 * 
	 * @param <b>c</b> El coche a escribir en fichero.
	 * @throws IOException En caso de que arroje excepción por algún problema en la
	 *                     comunicación con el fichero
	 */
	private void escribirCoche(Coche c) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(nombreFichero, true);
				ObjectOutputStream buffer = new ObjectOutputStream(fos)) {
			buffer.writeObject(c);
		}
		registrarId(c);
	}

	private void registrarId(Coche c) throws IOException {
		try (FileWriter fw = new FileWriter("contadorID.txt", true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(c.getId());
			bw.newLine();
		}
	}

	public long getLastIdIndex() throws FileNotFoundException, IOException, Exception {

		try (FileReader fr = new FileReader("contadorID.txt"); BufferedReader br = new BufferedReader(fr)) {
			String linea = br.readLine();
			long lastIdIndex = 0;
			while (linea != null) {
				lastIdIndex++;
				linea = br.readLine();
			}
			return lastIdIndex;
		}
	}

	/**
	 * Método que registra un videojuego en la persistencia
	 *
	 * @param c es el coche a registrar
	 * @throws Exception si hay algún problema con el fichero
	 */
	public void registrarCoche(Coche c) throws Exception {

		File f = new File(nombreFichero);
		if (!f.exists()) {
			throw new Exception("Error con fichero. Inténtelo de nuevo más tarde");
		}
		escribirCoche(c);
	}

	/**
	 * Método que devuelve un ArrayList de Coches con los coches que existan en la
	 * persistencia
	 *
	 * @return listaCoche es un ArrayList de Coche
	 * @throws Exception en caso de que suceda un error en la lectura del fichero
	 */
	public ArrayList<Coche> getListaCoches() throws Exception {

		try (FileInputStream fis = new FileInputStream(nombreFichero);
				ObjectInputStream ois = new ObjectInputStream(fis)) {

			Coche c = new Coche();
			ArrayList<Coche> listaCoche = new ArrayList<Coche>();

			boolean eof = false;

			while (!eof) {
				try {
					c = (Coche) ois.readObject();
					listaCoche.add(c);
				} catch (EOFException e) {
					eof = true;
				}
			}

			return listaCoche;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Método que dado un ID pasado por parametro busca su coincidencia en el
	 * fichero "coches.dat" y en caso de que lo encuentre lo devuelve junto con su
	 * marca, modelo y tipo de motor
	 *
	 * @param nombre el nombre a buscar en el fichero
	 * @return Coche en caso de que este en en fichero, null en caso contrario
	 * @throws Exception, en caso de que haya algún problema en el fichero de
	 *                    entrada salida
	 */
	public Coche getById(String idCoche) throws Exception {
		Coche c = null;

		try (FileInputStream fis = new FileInputStream(nombreFichero);
				ObjectInputStream ois = new ObjectInputStream(fis)) {

			boolean eof = false;

			while (!eof) {
				try {
					c = (Coche) ois.readObject();
					if (c.getId().equals(idCoche)) {
						return c;
					}
				} catch (EOFException e) {
					eof = true;
				}
			}
		}

		return c; // Si llega hasta aquí, es que c = null (no se encontró el coche)
	}

	/**
	 * Método que dado un Coche pasado por parámetro, lo busca en la persistencia y,
	 * en caso de que exista, elimina la primera ocurrencia o todas las ocurrencias,
	 * dependiendo del valor booleano de borrarTodos
	 * 
	 * @param cocheABorrar el Coche a buscar en fichero, borrarTodos la opción a
	 *                     elegir: si es true, se borran todas las ocurrencias del
	 *                     Coche a buscar. Si es false, se elimina la primera
	 *                     ocurrencia.
	 * @return
	 *         <ul>
	 *         <li><b>true</b> en caso de que el Coche exista y se borre al menos
	 *         una vez</li>
	 *         <li><b>false</b> en caso de que no se borre porque no exista</li>
	 *         </ul>
	 * @throws Exception en caso de que ocurra algún error de lectura/escritura con
	 *                   el fichero
	 * 
	 */
	public boolean borrarCoche(Coche cocheABorrar, boolean borrarTodos) throws Exception {

		ArrayList<Coche> listaCoche = getListaCoches();
		ArrayList<Coche> listaCocheModificada = getListaCoches();

		/*
		 * listaCocheModificada.remove() modifica la lista en la cual se invoca el
		 * método y elimina la primera ocurrencia del Coche pasado por parámetro.
		 * Además, devuelve un boolean. True en caso de que se haya encontrado el objeto
		 * pasado por parámetro y se haya borrado o False en caso contrario
		 * 
		 */

		// Si borrarTodos es true, se aumenta el valor de i tantas veces como
		// listaCoche.remove() sea true.
		// listaCoche.remove() es true si el coche existe en el fichero.
		// Cada vez que se invoca el remove(), se borra la primera ocurrencia

		// Si borrarTodos es false, se aumenta el valor de i a 1, para que el bucle de
		// abajo (el del contador j), se ejecute una sola vez y por lo tanto se borre
		// una sola vez el coche deseado

		int i = 0;
		if (borrarTodos) {
			while (listaCoche.remove(cocheABorrar)) {
				i++;
			}
		} else
			i = 1;

		boolean cEsBorrado = false;

		// Bucle que ejecuta listaCocheModificada.remove() i veces, en función del
		// resultado obtenido arriba
		for (int j = 0; j < i; j++) {
			cEsBorrado = listaCocheModificada.remove(cocheABorrar);
		}

		// Una vez que tenemos en memoria la lista modificada, necesitamos modificar el
		// fichero. Para ello, creamos un objeto File que nos permite verificar si el
		// fichero especificado existe. En caso de que no exista, se arroja una
		// excepción porque hay algún problema con el fichero especificado. En caso de
		// que sí exista, ejecutamos el método file.delete() para eliminar el fichero
		// que contiene la lista sin modificar. Una vez eliminado dicho fichero,
		// ejecutamos un bucle que recorra la lista de coches modificada y, por cada
		// iteración, ejecutamos el método escribirCoche(), para persistir en el nuevo
		// fichero (que se llama igual que el que acabamos de borrar) la lista de coches
		// modificada.

		File f = new File(nombreFichero);

		if (cEsBorrado) {
			if (!f.exists()) {
				throw new Exception("Error con fichero. Inténtelo de nuevo más tarde");
			} else {
				f.delete();
				for (Coche c : listaCocheModificada) {
					escribirCoche(c);
				}
			}
		}
		return cEsBorrado;
	}
}
