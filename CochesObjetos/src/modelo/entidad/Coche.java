package modelo.entidad;

import java.io.Serializable;
import java.util.Objects;

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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coche other = (Coche) obj;
		return Objects.equals(id, other.id);
	}

}
