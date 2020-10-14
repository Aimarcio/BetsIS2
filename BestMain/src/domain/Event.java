package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Event implements Serializable, Comparable<Event>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private Integer eventNumber;
	private String description;
	private Date eventDate;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Question> questions = new Vector<Question>();
	@OneToOne(fetch = FetchType.EAGER)
	private Equipo equipo1;
	@OneToOne(fetch = FetchType.EAGER)
	private Equipo equipo2;

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Event() {
		super();
	}

	public Event(String description, Date eventDate,Equipo equipo1, Equipo equipo2) {
		this.description = description;
		this.eventDate = eventDate;
		this.equipo1 = equipo1;
		this.equipo2 = equipo2;
	}

	public Integer getEventNumber() {
		return eventNumber;
	}

	public void setEventNumber(Integer eventNumber) {
		this.eventNumber = eventNumber;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	

	public Equipo getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(Equipo equipo1) {
		this.equipo1 = equipo1;
	}

	public Equipo getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(Equipo equipo2) {
		this.equipo2 = equipo2;
	}

	public String toString() {
		return equipo1.getNombre()+"-"+equipo2.getNombre()+" "+description;
	}
	
	
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual
	 * profit
	 * 
	 * @param question   to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Question addQuestion(String question, int betMinimum, List<Pronostico> pronosticos) {
		Question q = new Question(question, betMinimum, this, pronosticos);
		questions.add(q);
		return q;
	}
	
	public void removeQuestion(Question q) {
		questions.remove(q);
	}

	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */
	public boolean DoesQuestionExists(String question) {
		for (Question q : this.getQuestions()) {
			if (q.getQuestion().compareTo(question) == 0)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eventNumber;
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
		Event other = (Event) obj;
		if (eventNumber != other.eventNumber)
			return false;
		return true;
	}
	/**
	 * compara solo por fecha del evento
	 */
	@Override
	  public int compareTo(Event e) {
	    return getEventDate().compareTo(e.getEventDate());
	  }


}
