package modelo.negocio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import modelo.entidad.Coche;
import modelo.persistencia.DaoObjetoCoche;

public class GestorCoche {

	private DaoObjetoCoche dao;

	public GestorCoche(String rutaArchivo) {
		super();
		dao = new DaoObjetoCoche(rutaArchivo);
	}

	public boolean guardarCoche(Coche c) throws Exception {
		c.setId(generarID(c));
		try {
			dao.registrarCoche(c);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private String generarID(Coche c) throws FileNotFoundException, IOException, Exception {
		switch (c.getMotor()) {
		case DIESEL:
			return "DIE_";
		case GASOLINA:
			return "GAS_";
		case HIDROGENO:
			return "HID_";
		}
		return null;
	}

	/**
	 * Método que devuelve el coche cuyo ID coincida con el pasado por parámetro
	 * 
	 * @param id el ID del coche a buscar
	 * @return coche el coche que coincida con el ID pasado por parámetro, null en
	 *         caso de que no se encuentre ningún Coche con el ID indicado
	 * @throws Exception en caso de que haya algún problema de entrada/salida con el
	 *                   fichero
	 */
	public Coche getCocheById(String id) throws Exception {
		Coche coche = null;
		try {
			coche = dao.getById(id);
		} catch (Exception e) {
			throw e;
		}
		return coche;
	}

	/**
	 * Método que borra un coche cuyo ID sea igual al que se pasa por parámetro
	 * 
	 * @param id el ID asociado al coche que queremos eliminar.
	 * @return true si se ha borrado el coche correctamente, false si no se ha
	 *         borrado por no encontrarse
	 * @throws Exception en caso de que haya algún problema de entrada/salida con el
	 *                   fichero
	 */
	public boolean borrarCochePorId(String id) throws Exception {
		Coche c = new Coche();
		c.setId(id);
		try {
			if (dao.borrarCoche(c, false)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Método que devuelve la lista de coches almacenada en la persistencia
	 * 
	 * @return listaCoches la lista de coches recuperada de la persistencia
	 * @throws Exception en caso de que haya algún problema de entrada/salida con el
	 *                   fichero
	 */
	public ArrayList<Coche> getListaCoches() throws Exception {
		ArrayList<Coche> listaCoches = null;
		try {
			listaCoches = dao.getListaCoches();
		} catch (Exception e) {
			throw e;
		}
		return listaCoches;
	}

	/**
	 * Método que valida que el coche pasado por parámetro no tenga los atributos
	 * marca y modelo vacíos o con espacios en blanco
	 * 
	 * @param coche el coche pasado por parámetro
	 * @return <b>0</b> en caso de que el coche pasado por parámetro no sea válido
	 *         (marca y modelo vacíos o solo con espacios en blanco), <b>1</b> en
	 *         caso de que el coche pasado por parámetro no sea válido (marca vacía
	 *         o solo con espacios en blanco), <b>2</b> en caso de que el coche
	 *         pasado por parámetro no sea válido (modelo vacío o solo con espacios
	 *         en blanco), <b>3</b> en caso de que sea válido.
	 */
	public byte validarCoche(Coche coche) {
			if (coche.getMarca().isBlank() && coche.getModelo().isBlank())
				return 0;
			if (coche.getMarca().isBlank())
				return 1;
			if (coche.getModelo().isBlank()) {
				return 2;
			}
		return 3;
	}

}
