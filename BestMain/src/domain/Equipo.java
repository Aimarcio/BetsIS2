package domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@XmlAccessorType(XmlAccessType.FIELD)
@Entity

public class Equipo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlID
	private int id;
	private String nombre;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<String> jugadores;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<String> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<String> jugadores) {
		this.jugadores = jugadores;
	}

	public Equipo(String nombre, List<String> jugadores) {
		super();
		this.nombre = nombre;
		this.jugadores = jugadores;
	}
	public String toString() {
		return nombre;
	}

	public Equipo() {
		super();
	}
	
}
