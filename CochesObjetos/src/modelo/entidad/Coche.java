package modelo.entidad;

import java.io.Serializable;

public class Coche implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8682262085850821685L;
	private String id;
	private String marca;
	private String modelo;
	private Motor motor;

	public Coche() {
		super();
	}

	public Coche(String marca, String modelo, Motor motor) {
		super();
		this.marca = marca;
		this.modelo = modelo;
		this.motor = motor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Motor getMotor() {
		return motor;
	}

	public void setMotor(Motor motor) {
		this.motor = motor;
	}

}
