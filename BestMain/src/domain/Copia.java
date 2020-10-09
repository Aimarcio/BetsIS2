package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Copia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue
	@XmlID
	private int id;
	private User userCopiando;
	private double ratio;
	public User getUserCopiando() {
		return userCopiando;
	}
	public void setUserCopiando(User userCopiando) {
		this.userCopiando = userCopiando;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	public int getId() {
		return id;
	}
	public Copia(User userCopiando, double ratio) {
		super();
		this.userCopiando = userCopiando;
		this.ratio = ratio;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Copia() {
		super();
	}

}
