package main;

import interfaz.InterfazUsuario;
import modelo.persistencia.DaoObjetoCoche;

public class Main {

	public static void main(String[] args) {
		new InterfazUsuario(DaoObjetoCoche.FICHERO).mostrarInterfaz();
	}

}
 