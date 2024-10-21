package modelo.negocio;

import java.util.ArrayList;
import java.util.List;

import modelo.entidad.Coche;
import modelo.persistencia.DaoObjetoCoche;

public class GestorCoche {

	private DaoObjetoCoche dao;
	private String rutaArchivo;
	private Coche coche;

	public GestorCoche(String rutaArchivo, Coche coche) {
		super();
		this.rutaArchivo = rutaArchivo;
		this.coche = coche;
	}

	public byte guardarCoche() {
		dao = new DaoObjetoCoche(rutaArchivo);
		
		if (validarCoche(coche) == 1) {
			dao.registrar();
			return 1;
		} else {
			return 0;
		}

	}

	public byte borrarCoche() {
		dao = new DaoObjetoCoche(rutaArchivo);
		if (validarCoche(coche) == 1) {
			dao.borrar();
			return 1;
		} else {
			return 0;
		}

	}

	public ArrayList<Coche> mostrarListaCoches() {
		
		dao = new DaoObjetoCoche();
		List<Coche> listaCoches = dao.getListaCoches();
		
		return null;
	}

	public byte validarCoche(Coche coche) {
		if (coche.getMarca().isBlank() || coche.getModelo().isBlank()) {
			return 0;
		} else return 1;

	}

}
