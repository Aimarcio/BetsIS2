package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.Apuesta;
import domain.Equipo;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.User;

class BorrarPreguntaCajaNegraTest {
	private static BLFacadeImplementation sut;
	@Mock
	private static DataAccess DAO;
	private static ArrayList<String> list = new ArrayList();
	private static Equipo equipo1 = new Equipo();
	private static Equipo equipo2 = new Equipo();
	private static ArrayList<Question> listaQ = new ArrayList();
	private static ArrayList<Pronostico> listaP = new ArrayList();
	private static ConfigXML c;
	private static EntityManagerFactory emf;
	private static EntityManager db;

	// Utility

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
		c = ConfigXML.getInstance();
		String fileName = c.getDbFilename() + ";drop;";
		emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
		db = emf.createEntityManager();
	}

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sut = new BLFacadeImplementation(DAO);
	}

	@Test
	void test0() {
		Pronostico P = crearPronostico(1000,2,"ap2");
		Event E = crearEvento("a",1);
		Question Q = crearPregunta(10000,"a",1,null,E,listaP);
		User U = crearUsuario("Albertokoni");
		Apuesta x = crearApuesta(1);
		x.setUser(U);
		Q.setEvent(E);
		E.getQuestions().add(Q);
		P.setQuestion(Q);
		Q.getPronosticos().add(P);
		U.getApuestas().add(x);
		P.addApuesta(x);
		x.setPronostico(P);
		db.getTransaction().begin();
		db.persist(Q);
		db.persist(E);
		db.persist(x);
		db.persist(U);
		db.persist(P);
		db.getTransaction().commit();
		sut.borrarPregunta(Q);
		
		
	}

	@Test
	void test1() {
		Pronostico P1 = crearPronostico(1001,2,"ap2");
		Event E1 = crearEvento("a",1000);
		Question Q1 = crearPregunta(10001,"a",1,P1,E1,listaP);
		User U1 = crearUsuario("Albertoidni");
		Apuesta x1 = crearApuesta(1009);
		x1.setUser(U1);
		Q1.setEvent(E1);
		E1.getQuestions().add(Q1);
		P1.setQuestion(Q1);
		Q1.getPronosticos().add(P1);
		U1.getApuestas().add(x1);
		P1.addApuesta(x1);
		x1.setPronostico(P1);
		db.getTransaction().begin();
		db.persist(Q1);
		db.persist(E1);
		db.persist(x1);
		db.persist(U1);
		db.persist(P1);
		db.getTransaction().commit();
		sut.borrarPregunta(Q1);
	}

	@Test
	void test2() {
		Pronostico P2 = crearPronostico(1002,2,"ap2");
		Event E2 = null;
		Question Q2 = crearPregunta(10002,"a",1,P2,E2,listaP);
		User U2 = crearUsuario("Albertoinisadni");
		Apuesta x2 = crearApuesta(10000);
		x2.setUser(U2);
		Q2.setEvent(E2);
		P2.setQuestion(Q2);
		Q2.getPronosticos().add(P2);
		U2.getApuestas().add(x2);
		P2.addApuesta(x2);
		x2.setPronostico(P2);
		db.getTransaction().begin();
		try{
		db.persist(Q2);
		db.persist(E2);
		db.persist(x2);
		db.persist(U2);
		db.persist(P2);
		} catch(Exception e) {
			System.out.println("no mete nulos");
		}
		db.getTransaction().commit();
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q2));
	}

	@Test
	void test3() {
		Pronostico P3 = crearPronostico(1003,2,"ap2");
		Question Q3 = crearPregunta(10003,"a",1,P3,null,listaP);
		User U3 = crearUsuario("Albertoindsaino");
		Apuesta x3 = crearApuesta(3);
		x3.setUser(U3);
		P3.setQuestion(Q3);
		Q3.getPronosticos().add(P3);
		U3.getApuestas().add(x3);
		P3.addApuesta(x3);
		x3.setPronostico(P3);
		db.getTransaction().begin();
		db.persist(Q3);
		db.persist(x3);
		db.persist(U3);
		db.persist(P3);
		db.getTransaction().commit();
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q3));
	}

	@Test
	void test4() {
		Question Q4 = null;
		db.getTransaction().begin();
		try {
		db.persist(Q4);
		} catch(Exception e) {
			System.out.println("Ni siquiera permite introducir nulos");
		}
		db.getTransaction().commit();

		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q4));
	}

	@Test
	void test5() {
		Question Q5 = crearPregunta(10005,"a",1,null,null,listaP);
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q5));
	}

	@Test
	void test6() {
		Pronostico P6 = crearPronostico(1006,2,"ap2");
		Question Q6 = crearPregunta(10006,"a",1,P6,null,listaP);
		User U6 = null;
		Apuesta x6 = crearApuesta(6);
		x6.setUser(U6);
		P6.setQuestion(Q6);
		x6.setPronostico(P6);
		P6.addApuesta(x6);
		db.getTransaction().begin();
		try {
		db.persist(Q6);
		db.persist(x6);
		db.persist(U6);
		db.persist(P6);
		} catch(Exception e) {
			System.out.println("Ni siquiera permite introducir nulos");
		}
		
		db.getTransaction().commit();
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q6));
	}

	@Test
	void test7() {
		Pronostico P7 = crearPronostico(1007,2,"ap2");
		Question Q = crearPregunta(1000100,"a",1,null,null,null);
		Question Q7 = crearPregunta(10007,"a",1,P7,null,listaP);
		Apuesta x7 = crearApuesta(7);
		P7.setQuestion(Q7);
		Q7.getPronosticos().add(P7);
		db.getTransaction().begin();
		db.persist(Q7);
		db.persist(x7);
		db.persist(P7);
		db.getTransaction().commit();
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q));
	}

	@Test
	void test8() {
		Pronostico P8 = crearPronostico(1008,2,"ap2");
		Question Q8 = crearPregunta(10008,"a",1,P8,null,listaP);
		Apuesta x8 = null;
		P8.setQuestion(Q8);
		Q8.getPronosticos().add(P8);
		db.getTransaction().begin();
		try {
		db.persist(Q8);
		db.persist(x8);
		db.persist(P8);

		} catch(Exception e) {
			System.out.println("Ni siquiera permite introducir nulos");
		}
		db.getTransaction().commit();
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q8));
	}

	@Test
	void test9() {
		Pronostico P9 = crearPronostico(1009,2,"ap2");
		Question Q9= crearPregunta(10009,"a",1,P9,null,listaP);
		P9.setQuestion(Q9);
		db.getTransaction().begin();
		db.persist(Q9);
		db.persist(P9);
		db.getTransaction().commit();
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(Q9));
	}

	@Test
	void test10() {
		Mockito.doThrow(Exception.class).when(DAO).borrarPregunta(Mockito.anyObject());
		assertThrows(Exception.class, ()->sut.borrarPregunta(null));
	}
}
