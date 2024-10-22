package modelo.negocio;

import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.DaoObjetoCoche;

public class GestorCoche {

	private DaoObjetoCoche dao;
	private String rutaArchivo;

	public GestorCoche(String rutaArchivo) {
		super();
		this.rutaArchivo = rutaArchivo;
	}

	public byte guardarCoche(Coche c) throws Exception {
		dao = new DaoObjetoCoche(rutaArchivo);
		try {
			byte resultado = dao.registrar(c);
			if (resultado == 1) {
				return 1;
			}

			if (resultado == 2) {
				return 2;
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Método que devuelve el coche cuyo ID coincida con el pasado por parámetro
	 * 
	 * @param id el ID del coche a buscar
	 * @return coche el coche que coincida con el ID pasado por parámetro
	 * @throws Exception en caso de que haya algún problema de entrada/salida con el
	 *                   fichero
	 */
	public Coche getCocheById(String id) throws Exception {
		dao = new DaoObjetoCoche(rutaArchivo);
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
	 * @return 1 si se ha borrado el coche correctamente, 2 si no se ha borrado por
	 *         no encontrarse
	 * @throws Exception en caso de que haya algún problema de entrada/salida con el
	 *                   fichero
	 */
	public byte borrarCoche(String id) throws Exception {
		dao = new DaoObjetoCoche(rutaArchivo);
		try {
			if (dao.borrar()) {
				return 1;
			} else {
				return 0;
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

		dao = new DaoObjetoCoche();
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
		if (coche.getMarca().isBlank() || coche.getModelo().isBlank()) {
			if (coche.getMarca().isBlank() && coche.getModelo().isBlank()) {
				return 0;
			} else if (coche.getMarca().isBlank()) {
				return 1;
			} else if (coche.getModelo().isBlank()) {
				return 2;
			}
		}

		return 3;

	}

}
