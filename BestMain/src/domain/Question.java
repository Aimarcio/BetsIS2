package domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@XmlID

	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question;
	private int betMinimum;
	private Pronostico result;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@XmlIDREF
	private List<Pronostico> pronosticos;
	@XmlIDREF
	private Event event;

	public Question() {
		super();
	}

	public Question(Integer queryNumber, String query, int betMinimum, Event event, List<Pronostico> pronosticos) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum = betMinimum;
		this.event = event;
		this.pronosticos = pronosticos;
	}

	public Question(String query, int betMinimum, Event event, List<Pronostico> pronosticos) {
		super();
		this.question = query;
		this.betMinimum = betMinimum;
		this.pronosticos = new Vector<Pronostico>();
		for (Pronostico p: pronosticos)this.addPronostico(p);
		this.event = event;
	}

	/**
	 * Get the number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}

	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */

	public int getBetMinimum() {
		return betMinimum;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(int betMinimum) {
		this.betMinimum = betMinimum;
	}

	/**
	 * Get the result of the query
	 * 
	 * @return the the query result
	 */
	public Pronostico getResult() {
		return result;
	}

	/**
	 * Get the result of the query
	 * 
	 * @param result of the query to be setted
	 */

	public void setResult(Pronostico result) {
		this.result = result;
	}

	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}

	public void setPronosticos(List<Pronostico> pronosticos) {
		this.pronosticos = pronosticos;
	}

	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	public String toString() {
		return question;
	}
	public void addPronostico(Pronostico p) {
		this.pronosticos.add(p);
		p.setQuestion(this);
	}
	public List<Pronostico> getPronosticos(){
		return pronosticos;
	}
	public void clearPronosticos() {
		this.pronosticos = new ArrayList<Pronostico>();
	}
	public void removePronostico(Pronostico p) {
		pronosticos.remove(p);
	}
	public Pronostico addPronostico(String pronostico, double cuota) {

		Pronostico p = new Pronostico(pronostico,cuota);
		p.setQuestion(this);
		this.pronosticos.add(p);
		return p;
		
	}
	public boolean DoesPronosticoExists(String pronostico) {
		for (Pronostico p : this.getPronosticos()) {
			if (p.getPronostico().compareTo(pronostico) == 0)
				return true;
		}
		return false;
	}


	
}