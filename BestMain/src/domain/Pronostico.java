package domain;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Pronostico implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue
	@XmlID

	int id;
    private String pronostico;
    private double cuota;
    private Question question;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@XmlIDREF
    private List<Apuesta> apuestas;
    
    public String getPronostico() {
        return pronostico;
    }
    public void setPronostico(String pronostico) {
        this.pronostico = pronostico;
    }
    public double getCuota() {
        return cuota;
    }
    public void setCuota(double cuota) {
        this.cuota = cuota;
    }
    public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<Apuesta> getApuestas() {
		return apuestas;
	}
	public void setApuestas(List<Apuesta> apuestas) {
		this.apuestas = apuestas;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pronostico(String pronostico, double cuota) {
        super();
        this.pronostico = pronostico;
        this.cuota = cuota;
        this.apuestas = new Vector<Apuesta>();
    }
    public String toString() {
        return pronostico+" cuota: "+cuota;
    }
    public void addApuesta(Apuesta a) {
    	apuestas.add(a);
    }
    public void removeApuesta(Apuesta a) {
    	apuestas.remove(a);
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pronostico other = (Pronostico) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public Pronostico() {
		super();
	}

    

}