	package modelo.persistencia;
	
	import java.io.DataInput;
	import java.io.EOFException;
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
		}
	
		/**
		 * Método privado que accede al fichero para escribir, en una línea, un coche
		 * pasado por parámetro.
		 * 
		 * @param <b>c</b> El coche a escribir en fichero.
		 * @throws IOException En caso de que arroje excepción por algún problema en la
		 *                     comunicación con el fichero
		 */
		public void escribirCoches(ArrayList<Coche> coches) throws IOException {
		    try (FileOutputStream fos = new FileOutputStream(nombreFichero, true);
		         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
		        for (Coche coche : coches) {
		            oos.writeObject(coche);
		        }
		    }
		}
	
		/**
		 * Método que registra un videojuego en la persistencia
		 *
		 * @param c es el coche a registrar
		 * @throws Exception si hay algún problema con el fichero
		 */
		public byte registrarCoche(ArrayList<Coche> c) throws Exception {
	
			File f = new File(nombreFichero);
			if (!f.exists()) {
				throw new Exception("Error con fichero. Inténtelo de nuevo más tarde");
			}
			escribirCoches(c);
			return 1;
		}
	
		/**
		 * Método que devuelve un ArrayList de Coches con los coches que existan en la
		 * persistencia
		 *
		 * @return listaCoche es un ArrayList de Coche
		 * @throws Exception en caso de que suceda un error en la lectura del fichero
		 */
			public ArrayList<Coche> getListaCoche() throws Exception {
			    ArrayList<Coche> listaCoche = new ArrayList<>();
		
			    try (FileInputStream fi = new FileInputStream(nombreFichero);
			         ObjectInputStream oi = new ObjectInputStream(fi)) {
			    	boolean eof = false;
			        while (!eof) {
			            try {
			                Coche c = (Coche) oi.readObject();
			                listaCoche.add(c);
			            } catch (EOFException e) {
			            	eof = true;
			                break; // Termina el bucle cuando llega al final del archivo
			            } catch (IOException e2) {
			                System.out.println("Error al leer los coches");
			                break; // Si hay un error, termina la lectura
			            } catch (ClassNotFoundException e3) {
			                System.out.println("La clase Coche no está cargada en memoria");
			                break; // Termina la lectura si la clase no se encuentra
			            }
			        }
		
			    } catch (IOException e) {
			        System.out.println("No se ha podido acceder al fichero");
			    }
		
			    return listaCoche;
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
			        while (true) {
			            try {
			                Coche coche = (Coche) ois.readObject();
			                if (idCoche.equals(coche.getId())) { // Asegúrate de que `getId()` exista en `Coche`
			                    c = coche; // Encuentra y asigna el coche
			                    break; // Rompe el bucle al encontrar el coche
			                }
			            } catch (EOFException e) {
			                break; // Termina el bucle cuando llega al final del archivo
			            } catch (ClassNotFoundException e) {
			                System.out.println("La clase Coche no está cargada en memoria");
			                break; // Termina la lectura si la clase no se encuentra
			            }
			        }
			    } catch (IOException e) {
			        System.out.println("No se ha podido acceder al fichero");
			    }

			    return c; // Retorna el coche encontrado o null si no se encontró
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
			    ArrayList<Coche> listaCoche = getListaCoche();
			    boolean cEsBorrado = false;

			    // Si borrarTodos es true, se aumentará el valor de i tantas veces como
			    // listaCoche.remove() sea true.
			    if (borrarTodos) {
			        while (listaCoche.remove(cocheABorrar)) {
			            cEsBorrado = true; // Indica que al menos un coche fue borrado
			        }
			    } else {
			        cEsBorrado = listaCoche.remove(cocheABorrar); // Elimina la primera ocurrencia
			    }

			    if (cEsBorrado) {
			        // Sobreescribe el archivo con la lista actualizada
			        escribirCoches(listaCoche);
			    }

			    return cEsBorrado;
			}

	
	}
