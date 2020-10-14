package domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Apuesta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue
	@XmlID
	private int id;
	private int cantidad;
	private double ratio;
	@OneToOne(fetch = FetchType.EAGER)
	private Pronostico pronostico;
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	public Apuesta(int cantidad, double ratio, Pronostico pronostico, User user) {
		super();
		this.cantidad = cantidad;
		this.ratio = ratio;
		this.pronostico = pronostico;
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public Pronostico getPronostico() {
		return pronostico;
	}
	public void setQuestion(Pronostico pronostico) {
		this.pronostico = pronostico;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPronostico(Pronostico pronostico) {
		this.pronostico = pronostico;
	}
	public Apuesta() {
		super();
	}
	
}