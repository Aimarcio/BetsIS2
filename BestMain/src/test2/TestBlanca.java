package test2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Apuesta;
import domain.Equipo;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.User;

class TestBlanca {

	private static DataAccess DAO = new DataAccess(true);
	private static ArrayList<Pronostico> listaP = new ArrayList<Pronostico>();

	private static Equipo equipo1 = new Equipo();
	private static Equipo equipo2 = new Equipo();

	private static ConfigXML c = ConfigXML.getInstance();
	private static String fileName = c.getDbFilename();
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
	private static EntityManager db = emf.createEntityManager();

	private static Question Q0;
	private static Question Q1;
	private static Question Q2;
	private static Question Q3;
	private static Question Q4;

	private static Pronostico P0;
	private static Pronostico P2;
	private static Pronostico P31;
	private static Pronostico P32;
	private static Pronostico P33;
	private static Pronostico P4;

	private static Event E1;
	private static Event E2;
	private static Event E3;
	private static Event E4;

	private static User U3;
	private static User U4;

	private static Apuesta x31;
	private static Apuesta x32;
	private static Apuesta x33;
	private static Apuesta x41;
	private static Apuesta x42;
	private static Apuesta x43;

	// Utility

	public static void reopen() {
		db = emf.createEntityManager();
	}
	static public Question crearPregunta(int i, String a, int x, Pronostico Pa, Event Ea,
			ArrayList<Pronostico> listaPa) {
		Question Qx = new Question();
		Qx.setQuestionNumber(i);
		Qx.setQuestion(a);
		Qx.setBetMinimum(x);
		Qx.setResult(Pa);
		Qx.setEvent(Ea);
		Qx.setPronosticos(listaPa);
		return Qx;
	}

	static public Event crearEvento(String de, int i) {
		Event Ex = new Event();
		Ex.setDescription(de);
		Ex.setEquipo1(equipo1);
		Ex.setEquipo2(equipo2);
		Ex.setEventDate(UtilDate.newDate(2000, 01, 01));
		Ex.setEventNumber(i);
		ArrayList<Question> lista = new ArrayList<Question>();
		Ex.setQuestions(lista);
		return Ex;
	}

	static public Pronostico crearPronostico(int i, int x, String av) {
		Pronostico Px = new Pronostico();
		Px.setCuota(x);
		Px.setId(i);
		Px.setPronostico(av);
		ArrayList<Apuesta> lista = new ArrayList<Apuesta>();
		Px.setApuestas(lista);
		return Px;
	}

	static public Apuesta crearApuesta(int i) {
		Apuesta Ax = new Apuesta();
		Ax.setId(i);
		Ax.setCantidad(1);
		Ax.setRatio(1);
		return Ax;
	}

	static public User crearUsuario(String username) {
		User Ux = new User();
		Ux.setNombre("Alberto");
		Ux.setUsername(username);
		Ux.setApellido1("Primero");
		Ux.setApellido2("Segundo");
		Ux.setPw("1234");
		Ux.setEmail("Alberto@gmail.com");
		Ux.setDNI("74444444E");
		Ux.setTarjeta("E234123412341234");
		ArrayList<Apuesta> listaA = new ArrayList<Apuesta>();
		Ux.setApuestas(listaA);
		return Ux;
	}

	@BeforeAll
	static public void initialize() {
		DAO.close();
		Q0 = crearPregunta(10000, "a", 1, null, null, null);
		Q1 = crearPregunta(1001, "a", 1, null, E1, listaP);
		E2 = crearEvento("a", 1002);
		Q2 = crearPregunta(1002, "a", 1, null, E2, listaP);
		E1 = crearEvento("a", 1);

		P0 = crearPronostico(1000, 2, "ap2");
		P2 = crearPronostico(1001, 2, "ap2");
		P31 = crearPronostico(31, 2, "ap31");
		P32 = crearPronostico(32, 2, "ap32");
		P33 = crearPronostico(33, 2, "ap33");
		x31 = crearApuesta(31);
		x32 = crearApuesta(32);
		x33 = crearApuesta(33);
		P4 = crearPronostico(1004, 2, "ap31");
		x41 = crearApuesta(41);
		x42 = crearApuesta(42);
		x43 = crearApuesta(43);
		U3 = crearUsuario("Albertoini");
		E3 = crearEvento("a", 1003);
		Q3 = crearPregunta(1003, "a", 1, null, E3, listaP);
		E4 = crearEvento("a", 1004);
		Q4 = crearPregunta(1004, "a", 1, null, E4, listaP);
		U4 = crearUsuario("Albertini");

		Q0.setResult(P0);
		Q1.setEvent(E1);
		E1.getQuestions().add(Q1);
		x41.setPronostico(P4);
		x42.setPronostico(P4);
		x43.setPronostico(P4);
		P4.getApuestas().add(x41);
		P4.getApuestas().add(x42);
		P4.getApuestas().add(x43);
		x41.setUser(U4);
		x42.setUser(U4);
		x43.setUser(U4);
		U4.addApuesta(x41);
		U4.addApuesta(x42);
		U4.addApuesta(x43);
		E2.getQuestions().add(Q2);
		Q2.getPronosticos().add(P2);
		P2.setQuestion(Q2);
		E4.getQuestions().add(Q4);
		Q4.getPronosticos().add(P4);
		Q4.getPronosticos().add(P4);
		Q4.getPronosticos().add(P4);
		P4.setQuestion(Q4);
		P4.setQuestion(Q4);
		P4.setQuestion(Q4);
		x31.setPronostico(P31);
		x32.setPronostico(P32);
		x33.setPronostico(P33);
		P31.getApuestas().add(x31);
		P32.getApuestas().add(x32);
		P33.getApuestas().add(x33);

		x31.setUser(U3);
		x32.setUser(U3);
		x33.setUser(U3);
		U3.addApuesta(x31);
		U3.addApuesta(x32);
		U3.addApuesta(x33);
		E1.getQuestions().add(Q1);
		E3.getQuestions().add(Q3);
		Q3.getPronosticos().add(P31);
		Q3.getPronosticos().add(P32);
		Q3.getPronosticos().add(P33);
		P31.setQuestion(Q3);
		P32.setQuestion(Q3);
		P33.setQuestion(Q3);
		
		db.getTransaction().begin();
		db.persist(U3);
		db.persist(U4);

		db.persist(P0);
		db.persist(P2);
		db.persist(P31);
		db.persist(P32);
		db.persist(P33);
		db.persist(P4);

		db.persist(E1);
		db.persist(E2);
		db.persist(E3);
		db.persist(E4);

		db.persist(Q0);
		db.persist(Q1);
		db.persist(Q2);
		db.persist(Q3);
		db.persist(Q4);

		db.persist(x31);
		db.persist(x32);
		db.persist(x33);
		db.persist(x41);
		db.persist(x42);
		db.persist(x43);
		db.getTransaction().commit();
		db.close();
	}

	@Test
	void test0() {
		reopen();
		db.getTransaction().begin();
		assertNotNull(db.find(Question.class, Q0.getQuestionNumber()));
		db.getTransaction().commit();
		db.close();
		DAO.open(false);;
		DAO.borrarPregunta(Q0);
		DAO.close();
		reopen();
		db.getTransaction().begin();
		assertNull(db.find(Question.class, Q0.getQuestionNumber()));
		db.getTransaction().commit();
		db.close();
	}

	@Test
	void test1() {
		reopen();
		assertNotNull(db.find(Event.class, E1.getEventNumber()));
		assertNotNull(db.find(Question.class, Q1.getQuestionNumber()));
		db.close();
		DAO.open(false);
		DAO.borrarPregunta(Q1);
		DAO.open(true);
		reopen();
		assertNull(db.find(Question.class, Q1.getQuestionNumber()));
		db.close();
	}

	@Test
	void test2() {
		reopen();
		assertNotNull(db.find(Event.class, E2.getEventNumber()));
		assertNotNull(db.find(Question.class, Q2.getQuestionNumber()));
		assertNotNull(db.find(Pronostico.class, P2.getId()));
		db.close();
		DAO.open(false);
		DAO.borrarPregunta(Q2);
		DAO.close();
		reopen();
		assertNull(db.find(Question.class, Q2.getQuestionNumber()));
		assertNull(db.find(Pronostico.class, P2.getId()));
		db.close();
	}

	@Test
	void test3() {
		reopen();
		assertNotNull(db.find(Event.class, E3.getEventNumber()));
		assertNotNull(db.find(Question.class, Q3.getQuestionNumber()));
		assertNotNull(db.find(Pronostico.class, P31.getId()));
		assertNotNull(db.find(Pronostico.class, P32.getId()));
		assertNotNull(db.find(Pronostico.class, P33.getId()));
		assertNotNull(db.find(Apuesta.class, x31.getId()));
		assertNotNull(db.find(Apuesta.class, x32.getId()));
		assertNotNull(db.find(Apuesta.class, x33.getId()));
		db.close();
		DAO.open(false);
		DAO.borrarPregunta(Q3);
		DAO.close();
		reopen();
		assertNull(db.find(Question.class, Q3.getQuestionNumber()));
		assertNull(db.find(Pronostico.class, P31.getId()));
		assertNull(db.find(Pronostico.class, P32.getId()));
		assertNull(db.find(Pronostico.class, P33.getId()));
		assertNull(db.find(Apuesta.class, x31.getId()));
		assertNull(db.find(Apuesta.class, x32.getId()));
		assertNull(db.find(Apuesta.class, x33.getId()));
		db.close();
	}

	@Test
	void test4() {
		reopen();
		assertNotNull(db.find(Event.class, E4.getEventNumber()));
		assertNotNull(db.find(Question.class, Q4.getQuestionNumber()));
		assertNotNull(db.find(Pronostico.class, P4.getId()));
		assertNotNull(db.find(Apuesta.class, x41.getId()));
		assertNotNull(db.find(Apuesta.class, x42.getId()));
		assertNotNull(db.find(Apuesta.class, x43.getId()));
		db.close();
		DAO.open(false);
		DAO.borrarPregunta(Q4);
		DAO.close();
		reopen();
		assertNull(db.find(Question.class, Q4.getQuestionNumber()));
		assertNull(db.find(Pronostico.class, P4.getId()));
		assertNull(db.find(Apuesta.class, x41.getId()));
		assertNull(db.find(Apuesta.class, x42.getId()));
		assertNull(db.find(Apuesta.class, x43.getId()));
		db.close();
	}
}
