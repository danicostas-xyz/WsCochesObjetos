package modelo.persistencia;

import java.io.DataInput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import modelo.entidad.Coche;

public class DaoObjetoCoche {
	
	private static final String FICHERO = "coches.dat";
	
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
	}
	
	/**
	 * Método privado que accede al fichero para escribir, en una línea, un
	 * coche pasado por parámetro.
	 * 
	 * @param <b>c</b> El coche a escribir en fichero.
	 * @throws IOException En caso de que arroje excepción por algún problema en la
	 *                     comunicación con el fichero
	 */
	private void escribirCoche(Coche c) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(nombreFichero, true); ObjectOutputStream buffer = new ObjectOutputStream(fos)) {

			buffer.writeObject(c.toString());
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
	 * Método que devuelve un ArrayList de Coches con los coches que
	 * existan en la persistencia
	 *
	 * @return listaCoche es un ArrayList de Coche
	 * @throws Exception en caso de que suceda un error en la lectura del fichero
	 */
	public ArrayList<Coche> getListaCoche() throws Exception {

		try (FileInputStream fi = new FileInputStream(nombreFichero); ObjectInputStream oi = new ObjectInputStream(fi)) {

			ArrayList<Coche> listaCoche = new ArrayList<Coche>();
			String linea =(String)oi.readObject();

			while (linea != null) {

				Coche c = new Coche();
				//c.setId(linea.split("_")[0]);
				c.setMarca(linea.split("_")[1]);
				c.setModelo(linea.split("_")[2]);
				c.setMotor(null);//Puede dar error
				listaCoche.add(c);

				linea = (String)oi.readObject();
			}
			return listaCoche;

		} catch (Exception e) {
			throw e;
		}

	}
	
	/**
	 * Método que dado un ID pasado por parametro busca su coincidencia en el
	 * fichero "coches.dat" y en caso de que lo encuentre lo devuelve junto con
	 * su marca, modelo y tipo de motor
	 *
	 * @param nombre el nombre a buscar en el fichero
	 * @return Coche en caso de que este en en fichero, null en caso contrario
	 * @throws Exception, en caso de que haya algún problema en el fichero de
	 *                    entrada salida
	 */
	public Coche getById(String idCoche) throws Exception {
		Coche c = null;

		try (FileOutputStream fos = new FileOutputStream(nombreFichero); ObjectOutputStream os = new ObjectOutputStream(fos)) {
			String linea = (String)((ObjectInput) os).readObject();
			while (linea != null) {
				if (idCoche.equals(linea.split("_")[0])) {
					c = new Coche();
					//c.setId(linea.split("_")[0]);
					c.setMarca(linea.split("_")[1]);
					c.setModelo(linea.split("_")[2]);
					c.setMotor(null);//Puede dar error

					return c;
				}
				linea = ((DataInput) os).readLine();
			}

		} catch (Exception e) {
			throw e;
		}
		return c; // Si llega hasta aquí, es que c = null (no se encontró el coche)
	}
	
	/**
	 * Método que dado un Coche pasado por parámetro, lo busca en la
	 * persistencia y, en caso de que exista, elimina la primera ocurrencia o todas
	 * las ocurrencias, dependiendo del valor booleano de borrarTodos
	 * 
	 * @param cocheABorrar el Coche a buscar en fichero, borrarTodos la
	 *                          opción a elegir: si es true, se borran todas las
	 *                          ocurrencias del Coche a buscar. Si es false, se
	 *                          elimina la primera ocurrencia.
	 * @return
	 *         <ul>
	 *         <li><b>true</b> en caso de que el Coche exista y se borre al
	 *         menos una vez</li>
	 *         <li><b>false</b> en caso de que no se borre porque no exista</li>
	 *         </ul>
	 * @throws Exception en caso de que ocurra algún error de lectura/escritura con
	 *                   el fichero
	 * 
	 */
	public boolean borrarCoche(Coche cocheABorrar, boolean borrarTodos) throws Exception {

		ArrayList<Coche> listaCoche = getListaCoche();
		ArrayList<Coche> listaCocheModificada = getListaCoche();

		/*
		 * listaCocheModificada.remove() modifica la lista en la cual se invoca el
		 * método y elimina la primera ocurrencia del Coche pasado por parámetro.
		 * Además, devuelve un boolean. True en caso de que se haya encontrado el objeto
		 * pasado por parámetro y se haya borrado o False en caso contrario
		 * 
		 */

		// Si borrarTodos es true, se aumenta el valor de i tantas veces como
		// listaCoche.remove() sea true.
		// listaCoche.remove() es true si el juego existe. Cada vez que se invoca
		// el remove(), se borra la primera ocurrencia

		// Si borrarTodos es false, se aumenta el valor de i a 1
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
