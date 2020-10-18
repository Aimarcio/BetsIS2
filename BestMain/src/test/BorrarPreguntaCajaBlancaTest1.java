package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;

class BorrarPreguntaCajaBlancaTest1 {
	private static ArrayList<String> list = new ArrayList<String>();
	private static Equipo equipo1 = new Equipo() ;
	private static Equipo equipo2 = new Equipo();
	private static ArrayList<Question> listaQ = new ArrayList<Question>();
	private static ArrayList<Pronostico> listaP = new ArrayList<Pronostico>();
	private static Event E1;
	private static User u = new User("Alberto","1234","Alberto@gmail.com","Alberto","Primero", "Segundo","74444444E",false,"E234123412341234");
	private static Question Q1;
	private static Pronostico P;
	private static Apuesta x;
	private static DataAccess DAO;
	private static ConfigXML c;
	private static EntityManagerFactory emf;
	private static EntityManager db;
	@BeforeAll
	static public void initialize() {
		list.add("Craps");
		equipo1.setJugadores(list);
		equipo1.setNombre("Fnatic");
		list.remove(0);
		list.add("´Juanito");
		equipo2.setJugadores(list);
		equipo2.setNombre("TSM");
		E1 = new Event();
		E1.setDescription("a");
		E1.setEquipo1(equipo1);
		E1.setEquipo2(equipo2);
		E1.setEventDate(UtilDate.newDate(2000, 01, 01));
		E1.setEventNumber(10);
		Q1 = new Question();
		Q1.setBetMinimum(1);
		Q1.setEvent(E1);
		Q1.setQuestion("a");
		Q1.setQuestionNumber(1);
		P = new Pronostico();
		P.setCuota(2);
		P.setId(100);
		P.setPronostico("ap");
		x = new Apuesta();
		x.setCantidad(1);
		x.setId(1);
		x.setPronostico(P);
		x.setRatio(1);
		x.setUser(u);
		u.addApuesta(x);
		Q1.setPronosticos(listaP);
		listaQ.add(Q1);
		E1.setQuestions(listaQ);
		DAO = new DataAccess(true);
	}
	
	@BeforeEach
	public void setUp() {
		c = ConfigXML.getInstance();
		String fileName = c.getDbFilename();
		emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
		db = emf.createEntityManager();
		DAO.setDB(db);
	}
	
	@AfterEach
	public void finish() {
	}
	
	@AfterAll
	static public void end() {
		DAO.close();
	}
	
	@Test
	@DisplayName("Borrar Pregunta Test1")
	void test1() {
		try {
			db.getTransaction().begin();
			db.persist(Q1);
			db.persist(E1);
			db.getTransaction().commit();
			assertNotNull(db.find(Question.class, Q1.getQuestionNumber()));
			assertNotNull(db.find(Event.class, E1.getEventNumber()));
			DAO.borrarPregunta(Q1);
			assertNull(db.find(Question.class, Q1.getQuestionNumber()));
			assertNull(db.find(Event.class, E1.getEventNumber()));
		} catch (Exception e) {
			fail("Something went wrong");
		}
	}
}
