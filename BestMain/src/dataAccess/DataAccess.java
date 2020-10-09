package dataAccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;


import javax.jws.WebMethod;
import javax.persistence.CascadeType;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.EntityExistsException;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.*;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public DataAccess() {
		new DataAccess(false);
	}

	public void open(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}
	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}
			
			Equipo madrid = new Equipo("Madrid",Arrays.asList("Ronaldo","Messi","Usain","MessiJunior","RuuunaldoJunior")) ;
			db.persist(madrid);
			Equipo barsa = new Equipo("Barsa",Arrays.asList("Messi","Cavo", "Pepe", "Casillas", "Chipsa"));
			db.persist(barsa);
			Equipo nosedefutbol = new Equipo("NinjasInPijamas",Arrays.asList("Pyrocynical", "Dafran", "Godyr"));
			db.persist(nosedefutbol);
			Equipo real = new Equipo("Erreala", Arrays.asList("Arnaldo Otegi", "Urkullu", "Patxi", "Aizkor", "Ekpilot"));
			db.persist(real);
			Equipo Fnatic = new Equipo("Fnatic", Arrays.asList("Craps", "Doubleint", "Feika", "HYLINTSAN", "XQCPeppega"));
			db.persist(Fnatic);

			Event ev1 = new Event("", UtilDate.newDate(year, month, 17),madrid, barsa);
			Event ev2 = new Event("", UtilDate.newDate(year, month, 17),nosedefutbol, barsa);
			Event ev3 = new Event("", UtilDate.newDate(year, month, 17),barsa, madrid);
			Event ev4 = new Event("", UtilDate.newDate(year, month, 17),real,nosedefutbol);
			Event ev5 = new Event("", UtilDate.newDate(year, month, 17),nosedefutbol,barsa);
			Event ev6 = new Event("", UtilDate.newDate(year, month, 17),barsa,real);
			Event ev7 = new Event ("", UtilDate.newDate(year, month, 17),real,barsa);
			Event ev8 = new Event("", UtilDate.newDate(year, month, 17),madrid,barsa);
			Event ev9 = new Event("Final Futbol epicofantástica", UtilDate.newDate(year, month, 17),barsa,madrid);
			Event ev10 = new Event("", UtilDate.newDate(year, month, 17),barsa,real);

			Event ev11 = new Event("", UtilDate.newDate(year, month, 1),barsa,nosedefutbol);
			Event ev12 = new Event("", UtilDate.newDate(year, month, 1),nosedefutbol,madrid);
			Event ev13 = new Event("", UtilDate.newDate(year, month, 1),real,barsa);
			Event ev14 = new Event("Madre mai el derbi not rly xd", UtilDate.newDate(year, month, 1),real,barsa);
			Event ev15 = new Event("", UtilDate.newDate(year, month, 1),real, madrid);
			Event ev16 = new Event("", UtilDate.newDate(year, month, 1),madrid,real);

			Event ev17 = new Event("", UtilDate.newDate(year, month, 28),madrid,barsa);
			Event ev18 = new Event("", UtilDate.newDate(year, month, 28),barsa,madrid);
			Event ev19 = new Event("", UtilDate.newDate(year, month, 28),madrid,barsa);
			Event ev20 = new Event("", UtilDate.newDate(year, month, 28),barsa,madrid);
			Event ev777 = new Event("",UtilDate.newDate(2020, 1, 1),madrid,barsa);
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			
			List<Pronostico> lista = new Vector<Pronostico>();
			List<Pronostico> lista2 = new Vector<Pronostico>();
			List<Pronostico> listaYN = new Vector<Pronostico>();
			lista.clear();
			lista.add(new Pronostico("1", 2.0));
			lista.add(new Pronostico("x", 15.0));
			lista.add(new Pronostico("2",3.0));
			lista2.clear();
			lista2.add(new Pronostico("1", 1.5));
			lista2.add(new Pronostico("2", 2));
			lista2.add(new Pronostico("3", 1.5));
			lista2.add(new Pronostico("4", 3));
			lista2.add(new Pronostico("5", 5));
			lista2.add(new Pronostico("6+", 15));
			listaYN.clear();
			listaYN.add(new Pronostico("Si",2));
			listaYN.add(new Pronostico("No",2));
			ev777.addQuestion("Who idk man", 1, lista);
			lista.clear();
			lista.add(new Pronostico("1", 2.0));
			lista.add(new Pronostico("x", 15.0));
			lista.add(new Pronostico("2",3.0));
			ev777.addQuestion("Who gol 1", 1, listaYN);
			listaYN.clear();
			listaYN.add(new Pronostico("Si",2));
			listaYN.add(new Pronostico("No",2));
			db.persist(ev777);
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("Â¿QuiÃ©n ganarÃ¡ el partido?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q2 = ev1.addQuestion("Â¿QuiÃ©n meterÃ¡ el primer gol?", 2,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q3 = ev11.addQuestion("Â¿QuiÃ©n ganarÃ¡ el partido?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q4 = ev11.addQuestion("Â¿CuÃ¡ntos goles se marcarÃ¡n?", 2,lista2);
				lista2.clear();
				lista2.add(new Pronostico("1", 1.5));
				lista2.add(new Pronostico("2", 2));
				lista2.add(new Pronostico("3", 1.5));
				lista2.add(new Pronostico("4", 3));
				lista2.add(new Pronostico("5", 5));
				lista2.add(new Pronostico("6+", 15));
				q5 = ev17.addQuestion("Â¿QuiÃ©n ganarÃ¡ el partido?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q6 = ev17.addQuestion("Â¿HabrÃ¡ goles en la primera parte?", 2,listaYN);
				listaYN.clear();
				listaYN.add(new Pronostico("1", 2.0));
				listaYN.add(new Pronostico("x", 15.0));
				listaYN.add(new Pronostico("2",3.0));
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q2 = ev1.addQuestion("Who will score first?", 2,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q3 = ev11.addQuestion("Who will win the match?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2,lista2);
				lista2.clear();
				lista2.add(new Pronostico("1", 1.5));
				lista2.add(new Pronostico("2", 2));
				lista2.add(new Pronostico("3", 1.5));
				lista2.add(new Pronostico("4", 3));
				lista2.add(new Pronostico("5", 5));
				lista2.add(new Pronostico("6+", 15));
				q5 = ev17.addQuestion("Who will win the match?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2,listaYN);
				listaYN.clear();
				listaYN.add(new Pronostico("Si",2));
				listaYN.add(new Pronostico("No",2));
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2,lista2);
				lista2.clear();
				lista2.add(new Pronostico("1", 1.5));
				lista2.add(new Pronostico("2", 2));
				lista2.add(new Pronostico("3", 1.5));
				lista2.add(new Pronostico("4", 3));
				lista2.add(new Pronostico("5", 5));
				lista2.add(new Pronostico("6+", 15));
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1,lista);
				lista.clear();
				lista.add(new Pronostico("1", 2.0));
				lista.add(new Pronostico("x", 15.0));
				lista.add(new Pronostico("2",3.0));
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2,listaYN);
				listaYN.clear();
				listaYN.add(new Pronostico("Si",2));
				listaYN.add(new Pronostico("No",2));

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);
			
			List<User> lf = new ArrayList<User>();
			User u;
			u = new User("admin","admin1","arrobajemeilpuntocom","Jesús","Fernandez", "De Cáceres","23456783W",true,"324");
			db.persist(u);
			u = new User("alexelcapo","croquetta","alexelcapo@gmail.com","Jordi","Capo", "Alfonso","23456783W",false,"324");
			u.addPublication(new Publication(u,"asidbfnasdbfasijbdfasdfadskadfkn amnsdfmn asdmnfamnsdf"+"<br>"+"mnsdmnfdmnx cvmn ,xmn,cv mn,dsxcmnv ,adlmns,fmnv,adsfmn,admns ,fmn ,asdfmnamns ,dfmn ,as dmn,fmn ,asdfmn ,a mns,dfmn ,asdmn ,fmn ,asdfmn" +"<br>"+"<br>"+",asmn ,df mn,asmn d,fmn ,asdmn,famns ,dfmn,asdfmn,amn ,sdfmn ,"));
			u.addPublication(new Publication(u,"ZULUL"));
			u.addNotification(new Notification("DabPoggersMonkaW","I am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worshipI am the admin, a superior being plebs like you should worship"));
			lf.add(u);
			Apuesta a = new Apuesta(5000,1.5,ev777.getQuestions().get(0).getPronosticos().get(0),u);
			u.addApuesta(a);
			ev777.getQuestions().get(0).getPronosticos().get(0).addApuesta(a);
			db.persist(ev777);
			u.sumarPuntos(1000000);
			db.persist(u);
			u = new User("pichacaliente235","martinez1","PPAP@gmail.com","Pedro","Angel", "Pichardo","23456783W",false,"324");
			lf.add(u);
			db.persist(u);
			u = new User("buiblemp","buiblemp1","pamelaSisgenero@gmail.com","Pamela","Sisneros", "Arroyo","23456783W",false,"324");
			lf.add(u);
			db.persist(u);
			u = new User("lown1958","lown1958","hernan1958@gmail.com","Hernando","Nuñez", "Ruiz","23456783W",false,"324");
			db.persist(u);
			u = new User("China Number One","comunismo1","WeenieDPoo@chaina.com","Olimpo","Corona", "Vijil","23456783W",false,"324");
			lf.add(u);
			db.persist(u);
			u = new User("lumseforme","lumdeforme1","ic@gmail.com","Ascensión","Casillas", "Armas","23456783W",false,"324");
			db.persist(u);
			u = new User("dahme1969","moro1","JuliusNovachroma@gmail.com","Agus","Mena", "Narvaez","23456783W",false,"324");
			db.persist(u);
			u = new User("discovor","discovor1","WollMasta@gmail.com","Karmele","Ornelas", "DWallz","23456783W",false,"324");
			u.setFollowing(lf);
			for(User usr : lf) {
				usr.addFollower(u);
				db.persist(usr);
			}
			u.sumarPuntos(1000000);
			db.persist(u);
			u = new User("postmeran","postmalone","postMalone@gmail.com","Lívero","Gimine", "Pagan","23456783W",false,"324");
			db.persist(u);
			u = new User("tokinat","tokinat1","tokinamo@gmail.com","Abi","Barela", "Lemur","23456783W",false,"324");
			db.persist(u);
			u = new User("ayaya","ayaya1","EXUPUROOOOOSHION@gmail.com","Kono","Subarashii", "Isekai","23456783W",false,"324");
			db.persist(u);
			u = new User("envil1942","envil1942","evilboy@gmail.com","Adelmar","Leiba", "Santana","23456783W",false,"324");
			db.persist(u);
			u = new User("overtaker","overtaker1","overtaker999@gmail.com","Uenu","Tejeda", "Pina","23456783W",false,"324");
			db.persist(u);
			u = new User("mersed","mersed1","mercedes@gmail.com","Florencia","Paez", "Pabón","23456783W",false,"324");
			db.persist(u);
			u = new User("ritter74","ritter74","ritter743@gmail.com","Palemón","Cachón", "Carranza","23456783W",false,"324");
			db.persist(u);
			u = new User("conory","conory1","Corona19@gmail.com","Liberato","Perez", "León","23456783W",false,"324");
			db.persist(u);
			u = new User("androm","androm1","android1423@gmail.com","Tusnelda","Quesada", "Ocampo","23456783W",false,"324");
			db.persist(u);
			u = new User("pamen1984","pamen1984","pasmela@gmail.com","Exaltación","Centeno", "Lozada","23456783W",false,"324");
			db.persist(u);
			u = new User("wiltionew1988","wiltionew1988","wiltionew@gmail.com","Yaguati","Alemán", "Arebalo","23456783W",false,"324");
			db.persist(u);
			
			for(int i=0; i<150;i++) {
				u = new User("a"+i,"a","arrobajemeilpuntocom","Jesús","Fernandez", "De Cáceres","23456783W",true,"324");
				db.persist(u);
			}
			db.getTransaction().commit();
			
		
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, int betMinimum, List<Pronostico> pronosticos) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum, pronosticos);
		/*
		 * for ( Pronostico p : pronosticos) db.persist(p);
		 */
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public List<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public List<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}
	
	public User findUser(String username) {
		return db.find(User.class, username);
	}
	
	public void addUser(User u) throws UserAlreadyExists {
		try {
			db.getTransaction().begin();
			db.persist(u);
			db.getTransaction().commit();
			System.out.println("usuario aï¿½adido: "+u);
		}catch(Exception EntityExistsException) {
			throw new UserAlreadyExists();
		}
	}
	
	public Event createEvent(String description, Date dato,Equipo eq1, Equipo eq2) {
		System.out.println(">> DataAccess: createEvent=> description= " + description + " date= " + dato);
		db.getTransaction().begin();
		Event ev = new Event(description, dato,eq1,eq2);
		db.persist(ev);
		db.getTransaction().commit();
		System.out.println("evento aï¿½adido: "+ev);
		return ev;

	}
	
	public Event findEvent(String description, Date fech) {
        TypedQuery<Event> query = db.createQuery(
                "SELECT ev FROM Event ev WHERE ev.description= ?1 and ev.eventDate= ?2", Event.class);
        query.setParameter(1, description);
        query.setParameter(2, fech);
        List<Event> events = query.getResultList();
        try{return events.get(0);
        }catch(Exception e) {
            return null;
        }
    }
	
	public List<User> getAllUsers(){
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u",User.class);
		return query.getResultList();
	}
	
	public User editUser(String oldUsername, String newUsername, String nombre,String ape1, String ape2, String DNI, String email, String tarj){
			User oldUser = db.find(User.class, oldUsername);
			db.getTransaction().begin();
			oldUser.setUserVisibleName(newUsername);
			oldUser.setNombre(nombre);
			oldUser.setApellido1(ape1);
			oldUser.setApellido2(ape2);
			oldUser.setDNI(DNI);
			oldUser.setEmail(email);
			oldUser.setTarjeta(tarj);
			db.getTransaction().commit();
			return oldUser;	
		
	}
	
	public void addPoints(String username, int p, String tarj) throws UserIncomplete, NoPaymentMethod {
		User u = db.find(User.class, username);
		if ((u.getApellido1() == null | u.getApellido2() == null | u.getDNI() == null) ||
				u.getNombre().equals("")  | u.getApellido1().equals("") | u.getApellido2().equals("")| u.getDNI().equals("") | u.getNombre().equals("")) {
			throw new UserIncomplete("usuario "+username+" incompleto");
		}
		if (tarj == null || tarj.equals("")) {
			throw new NoPaymentMethod("usuario "+username+" no tiene tarjeta");
		}
		db.getTransaction().begin();
		u.setPuntos(u.getPuntos()+p);
		db.persist(u);
		db.getTransaction().commit();
	}
	
	public void remPoints(String username, int p) throws InsufficientPoints {
		User u = db.find(User.class, username);
		if (u.getPuntos()-p<0) {
			throw new InsufficientPoints("Usuario "+username+" no tiene suficientes puntos");
		}
		db.getTransaction().begin();
		u.setPuntos(u.getPuntos()-p);
		db.persist(u);
		db.getTransaction().commit();
	}
	public List<domain.Event> getEventsInterval(Date start, Date end){
		TypedQuery<Event> query = db.createQuery(
                "SELECT ev FROM Event ev WHERE ev.eventDate>= ?1 and ev.eventDate< ?2", Event.class);
		query.setParameter(1, start);
		query.setParameter(2, end);
		return query.getResultList();
	}
	public void apostar(User u, Pronostico p, int cantidad, List<User> usuariosConCopia) throws ObjectNotFound,InsufficientPoints, EventFinished{
		boolean hacerTransaccion = usuariosConCopia.isEmpty();
		User user = db.find(User.class, u.getUsername());
		Pronostico pronostico = db.find(Pronostico.class, p.getId());
		
		//checkear errores
		if (user == null) throw new ObjectNotFound("no se ha encontrado el usuario");
		if (pronostico == null) throw new ObjectNotFound("no se ha encontrado el pronostico");
		Date hoy = new Date();
		if(pronostico.getQuestion().getEvent().getEventDate().compareTo(hoy)<0) throw new EventFinished("El evento ya ha terminado");
		if(u.getPuntos()<cantidad)throw new InsufficientPoints("no tienes suficientes puntos");
		if(u.getPuntos()<p.getQuestion().getBetMinimum())throw new InsufficientPoints("en esta pregunta has de apostar más de "+p.getQuestion().getBetMinimum());
		
		//crear la apuesta
		if(hacerTransaccion)
		db.getTransaction().begin();
		Apuesta a = new Apuesta(cantidad, pronostico.getCuota(),pronostico , user);
		pronostico.addApuesta(a);
		user.addApuesta(a);
		user.restarPuntos(cantidad);
		usuariosConCopia.add(user);
		db.persist(user);
		db.persist(pronostico);
		
		
		//generar las notificaciones
		if(!(user.getSettings().isPerfil() | user.getSettings().isApuestasprivadas())) {
			String titulo = "Apuesta de "+user.getUserVisibleName()+"!";
			String mensaje = "Evento: "+p.getQuestion().getEvent()+"<br>"+
								"Pregunta: "+p.getQuestion()+"<br>"+
								"Pronostico: "+p+"<br>"+
								"Cantidad: "+cantidad;
			for(User usr : user.getFollowers()) 
				if(usr.getSettings().isNotificacionesApuesta()) {
					usr.addNotification(new Notification(titulo,mensaje));
					db.persist(usr);
				}
		}
		
		//apostar a los que le copian
		
			for(Copia c : user.getCopiando()) {
				try {
					if(!usuariosConCopia.contains(c.getUserCopiando()))//evitar bucles infinitos
					apostar(c.getUserCopiando(), pronostico, (int) (cantidad*c.getRatio()),usuariosConCopia);
				}catch(Exception e) {
					System.out.println(e.toString());
				}
			}
		
		
		if(hacerTransaccion)
		db.getTransaction().commit();
	}
	
	public void borrarPregunta(Question q) {
		db.getTransaction().begin();
		q = db.find(Question.class, q.getQuestionNumber());
		if(q.getResult()!= null) {
			db.remove(q);
			return;
		}
		for(Pronostico p: q.getPronosticos()) {
			for(Apuesta a: p.getApuestas())
				try {
					User u = db.find(User.class, a.getUser().getUsername());
					u.sumarPuntos(a.getCantidad());
					u.removeApuesta(a);
					db.persist(u);
				}catch(Exception e) {
				}
		}
			
				
		Event e = db.find(Event.class, q.getEvent().getEventNumber());
		e.removeQuestion(q);
		db.persist(e);
		db.getTransaction().commit();
	}

	public void borrarUsuario(User u) {
		db.getTransaction().begin();
		u = db.find(User.class, u.getUsername());
		db.remove(u);
		db.getTransaction().commit();
	}
		
	public void editarPregunta(Question q, String pregunta, int betMin) throws QuestionAlreadyExist {
		db.getTransaction().begin();
		q = db.find(Question.class, q.getQuestionNumber());
		if(!q.getQuestion().equals(pregunta) && q.getEvent().DoesQuestionExists(pregunta)) {
			throw new QuestionAlreadyExist("La pregunta ya existe");
		}
		q.setQuestion(pregunta);
		q.setBetMinimum(betMin);
		db.getTransaction().commit();
		}
		
	public void editarPronostico(Pronostico p, String pronostico, double cuota) throws PronosticoAlreadyExist {
		db.getTransaction().begin();
		p = db.find(Pronostico.class, p.getId());
		if(!p.getPronostico().equals(pronostico) && p.getQuestion().DoesPronosticoExists(pronostico)) {
			throw new PronosticoAlreadyExist("El pronostico ya existe");
		}
		p.setPronostico(pronostico);
		p.setCuota(cuota);
		db.getTransaction().commit();
		}
		
		public void borrarPronostico(Pronostico p) {
		db.getTransaction().begin();
		p = db.find(Pronostico.class, p.getId());
		Question q = db.find(Question.class, p.getQuestion().getQuestionNumber());
		q.removePronostico(p);
		db.remove(p);
		db.getTransaction().commit();
	}
	
	public Pronostico addPronostico(Question q, String pronostico, double cuota) throws PronosticoAlreadyExist{
		if(q.DoesPronosticoExists(pronostico)) {
			throw new PronosticoAlreadyExist("El pronostico ya existe");
		}
	db.getTransaction().begin();
	q = db.find(Question.class, q.getQuestionNumber());
	Pronostico p = q.addPronostico(pronostico,cuota);
	db.persist(q);
	db.getTransaction().commit();
	return p;
	}
	public void CambiarContraseña(User user, String Password) {
		db.getTransaction().begin();
		User Usuario=db.find(User.class, user);
		Usuario.setPw(Password);
		db.getTransaction().commit();
	}
	public User cancelBet(Apuesta a) throws PreguntaYaResuelta{
		
		db.getTransaction().begin();
		Apuesta apuesta = db.find(Apuesta.class, a.getId());
		if(apuesta.getPronostico().getQuestion().getResult()!= null) throw new PreguntaYaResuelta("La pregunta ya ha sido resuelta");
		User u = db.find(User.class, apuesta.getUser().getUsername());
		u.sumarPuntos(apuesta.getCantidad());
		u.removeApuesta(apuesta);
		Pronostico p = db.find(Pronostico.class, apuesta.getPronostico().getId());
		p.removeApuesta(apuesta);
		db.remove(apuesta);
		db.persist(u);
		db.persist(p);
		db.getTransaction().commit();
		return u;
	}
	public List<User> search100Users(String textSearch, int index){
		TypedQuery<User> query = db.createQuery(
                "SELECT u FROM User u WHERE u.settings.perfil=?1 AND LOWER(u.userVisibleName) LIKE '%"+textSearch.toLowerCase()+"%' GROUP BY u ORDER BY SIZE(u.followers) ASC", User.class);
        query.setParameter(1, false);
		List<User> lu = query.getResultList();
        List<User> result = new Vector<User>();
        for(int i = Integer.min(index+100, lu.size())-1;i>=index;i--)result.add(lu.get(i));
        return result;
	}
	public List<Equipo> search100Equipos(String textSearch, int index){
		TypedQuery<Equipo> query = db.createQuery(
                "SELECT u FROM Equipo u WHERE LOWER(u.nombre) LIKE '%"+textSearch.toLowerCase()+"%'", Equipo.class);
        List<Equipo> lu = query.getResultList();
        List<Equipo> result = new Vector<Equipo>();
        for(int i = Integer.min(index+100, lu.size())-1;i>=index;i--)result.add(lu.get(i));
        return result;
	}
	public List<User> getFollowing(String username){
		User u = db.find(User.class, username);
		for (User user: u.getFollowing());
		return u.getFollowing();
	}
	public List<Apuesta> getApuestas(String username){
		User u = db.find(User.class, username);
		for (Apuesta a: u.getApuestas());
		return u.getApuestas();
	}
	public void follow(String follower, String followed) throws ObjectNotFound{
		db.getTransaction().begin();
		User fer = db.find(User.class, follower);
		User fed = db.find(User.class, followed);
		if(fer == null || fed == null) throw new ObjectNotFound();
		for(User u :fer.getFollowing()) if (u.getUsername().equals(fed)) return;
		fer.addFollowing(fed);
		fed.addFollower(fer);
		db.persist(fer);
		db.persist(fed);
		db.getTransaction().commit();
	}
	public void unfollow(String follower, String followed) throws ObjectNotFound{
		db.getTransaction().begin();
		User fer = db.find(User.class, follower);
		User fed = db.find(User.class, followed);
		if(fer == null || fed == null) throw new ObjectNotFound();
		fer.removeFollowing(fed);
		fed.removeFollower(fer);
		db.persist(fer);
		db.persist(fed);
		db.getTransaction().commit();
	}
	public List<Publication> getPublications(String username){
		User u = db.find(User.class, username);
		for (Publication p: u.getPublicaciones());
		return u.getPublicaciones();
	}
	public List<Equipo> getAllEquipos(){
		TypedQuery<Equipo> query = db.createQuery("SELECT e FROM Equipo e",Equipo.class);
		return query.getResultList();
	}
	
	public void crearEquipo(String nombre, List<String> jugadores) {
		db.getTransaction().begin();
		Equipo e = new Equipo(nombre,jugadores);
		db.persist(e);
		db.getTransaction().commit();
	}
	public Publication publicar(String username, String msg) {
		db.getTransaction().begin();
		//Añadir la publicacion a la persona que la ha hecho
		User u = db.find(User.class, username);
		Publication p = new Publication(u,msg);
		u.addPublication(p);
		db.persist(u);
		db.persist(p);
		//Añadir las n otificaciones pertinentes a los followers
		if(!u.getSettings().isPerfil()) { // si el propietario de la publicacion no tiene el perfil en privado
			String titulo = "Nueva publicación de "+u.getUserVisibleName()+"!";
			String mensaje = p.getMessage();
			for(User usr : u.getFollowers()) {
				if(usr.getSettings().isNotificacionesPublicaciones()) { //si el usuario quiere notificaciones de publicaciones
					usr.addNotification(new Notification(titulo,mensaje));
					db.persist(usr);
				}
			}
		}
		db.getTransaction().commit();
		return p;
	}
	public void updateAjustes(String username, Ajustes ajustes) {
		db.getTransaction().begin();
		User u = db.find(User.class, username);
		u.setSettings(ajustes);
		db.persist(u);
		db.getTransaction().commit();
	}
	
	public List<Notification> getNotificaciones(String username){
		User u = db.find(User.class, username);
		List<Notification> list = u.getNotificaciones();
		for(Notification p:list);
		return list;
	}
	
	public void setRead(int id) {
		db.getTransaction().begin();
		Notification n = db.find(Notification.class, id);
		n.setRead(true);
		db.persist(n);
		db.getTransaction().commit();
	}
	public void notificacionGeneral(String titulo, String mensaje) {
		db.getTransaction().begin();
		TypedQuery<User> query = db.createQuery("SELECT u FROM User u", User.class);
		List<User> lu = query.getResultList();
		for(User u: lu) {
			u.addNotification(new Notification(titulo,mensaje));
			db.persist(u);
		}
		db.getTransaction().commit();
	}
	public List<domain.Event> nonResolvedEvent(){
		db.getTransaction().begin();
		Date hoy = new Date();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM domain.Event ev WHERE ev.eventDate<?1", domain.Event.class);
		query.setParameter(1, hoy);
		List<Event> le = query.getResultList();
		List<Event> lre = new Vector<Event>();
		for(Event ev: le) {
			for(Question q: ev.getQuestions() ){
				if (q.getResult() != null)
				System.out.println(q.getQuestion()+" "+q.getResult());
				else
					System.out.println(q.getQuestion()+ " null");
				if(q.getResult()==null) {
					lre.add(ev);
					break;
				}
			}
		}
		db.getTransaction().commit();
		return lre;
	}
	public void resolverPregunta(Pronostico p) {
		db.getTransaction().begin();
		Pronostico pron = db.find(Pronostico.class, p);
		pron.getQuestion().setResult(pron);
		for(Apuesta a : pron.getApuestas()) {
			a.getUser().sumarPuntos((int)(a.getCantidad() * a.getRatio()));
			db.persist(a);
		}
		db.persist(pron.getQuestion());
		db.getTransaction().commit();
	}
	public void editarPreguntaCompleta(Integer id, String titulo,int min ,List<Pronostico> pronosticos) throws QuestionAlreadyExist {
		db.getTransaction().begin();
		Question q = db.find(Question.class, id);
		if(q == null) return;
		
		if(!q.getQuestion().equals(titulo) && q.getEvent().DoesQuestionExists(titulo)) throw new QuestionAlreadyExist("Esta pregunta ya existe");
		
		q.setBetMinimum(min);
		q.setQuestion(titulo);
		List<Pronostico> lu = new Vector<Pronostico>(); //lista de pronosticos que se van a quedar en la pregunta
		for (Pronostico p:pronosticos) {
			//se mira si ya existia el pronostico para no eliminar las apuestas y meter un nuevo pronostico
			Pronostico pn = db.find(Pronostico.class, p.getId());
			if(pn == null) {
				p.setQuestion(q);
				lu.add(p);
			} else {
			lu.add(pn);
			}
		}
		List<Pronostico> remove = q.getPronosticos();
		Pronostico aux;
		while(!remove.isEmpty()) {
			aux = remove.remove(0);
			if(!lu.contains(aux)) {//si este pronostico que habia en la pregunta no esta entre los nuevos pronosticos, se elimina (devolviendo puntos a usuarios)
				for(Apuesta a:aux.getApuestas()) {
					try {
						User u = db.find(User.class, a.getUser().getUsername());
						u.sumarPuntos(a.getCantidad());
						u.removeApuesta(a);
						db.persist(u);
					}catch(Exception e) {
					}
				}
					
				db.remove(aux);
			}
		}
		q.setPronosticos(lu);
		db.persist(q);
		db.getTransaction().commit();
	}
	
	public void updateUsuarioCopiado(User userLogeado,User userCopiado,double ratio) {
		db.getTransaction().begin();
		User u = db.find(User.class, userLogeado.getUsername());
		User uc = db.find(User.class,userCopiado.getUsername());
		if(ratio == 0) {
			uc.rmCopiando(u);
		}else {
		Copia c = new Copia(u,ratio);
		uc.addCopiando(c);
		}
		db.persist(uc);
		db.getTransaction().commit();
	}
}
