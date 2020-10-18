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

class BorrarPreguntaCajaBlancaTest0 {
	private static ArrayList<String> list = new ArrayList<String>();
	private static Equipo equipo1 = new Equipo() ;
	private static Equipo equipo2 = new Equipo();
	private static ArrayList<Question> listaQ = new ArrayList<Question>();
	private static ArrayList<Pronostico> listaP = new ArrayList<Pronostico>();
	private static Event E;
	private static Question Q0;
	private static Pronostico P;
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
		E = new Event();
		E.setDescription("a");
		E.setEquipo1(equipo1);
		E.setEquipo2(equipo2);
		E.setEventDate(UtilDate.newDate(2000, 01, 01));
		E.setEventNumber(10);
		Q0 = new Question();
		Q0.setBetMinimum(1);
		Q0.setEvent(E);
		Q0.setQuestion("a");
		Q0.setQuestionNumber(100);
		P = new Pronostico();
		P.setCuota(2);
		P.setId(100);
		P.setPronostico("ap");
		listaP.add(P);
		Q0.setPronosticos(listaP);
		Q0.setResult(P);
		listaQ.add(Q0);
		E.setQuestions(listaQ);

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
	@DisplayName("Borrar Pregunta Test0")
	void test0() {
		try {
			db.getTransaction().begin();
			db.persist(Q0);
			db.getTransaction().commit();
			assertNotNull(db.find(Question.class, Q0.getQuestionNumber()));
			DAO.borrarPregunta(Q0);
			assertNull(db.find(Question.class, Q0.getQuestionNumber()));
		} catch (Exception e) {
			fail("Something went wrong");
		}
	}
}
