package businessLogic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.RollbackException;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.*;
import exceptions.*; 

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
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
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, int betMinimum, List<Pronostico> pronosticos)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		DataAccess dBManager = new DataAccess();
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dBManager.createQuestion(event, question, betMinimum, pronosticos);

		dBManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public List<Event> getEvents(Date date) {
		DataAccess dbManager = new DataAccess();
		List<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public List<Date> getEventsMonth(Date date) {
		DataAccess dbManager = new DataAccess();
		List<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		DataAccess dBManager = new DataAccess();
		dBManager.initializeDB();
		dBManager.close();
	}

	@WebMethod
	public User login(String username, String password) {
		DataAccess db = new DataAccess();
		User u = db.findUser(username);
		db.close();
		if (u!=null) {
			if (!u.getPw().equals(password)) {
			return null;
			}
		}
		return u;
	}
	
	@WebMethod
	public void register(String username, String password, String email) throws UserAlreadyExists {
		User u = new User(username,password,email,false);
		DataAccess db = new DataAccess();
		
		if(db.findUser(username)!=null) {
			db.close();
			System.out.println("Error");
			throw new UserAlreadyExists("username already exists");
		}	
		System.out.println("OKE MASTA");
		db.addUser(u);
		db.close();
	}
	
	@WebMethod
	public void registerAdmin(String username, String password, String email) throws UserAlreadyExists {
		User u = new User(username,password,email,true);
		DataAccess db = new DataAccess();
		try{
			db.addUser(u);
		}catch(Exception UserAlreadyExists) {
			db.close();
			throw new UserAlreadyExists("username already exists");
		}
		db.close();
	}
	@WebMethod
	public Event createEvent(String description, Date dato,Equipo eq1, Equipo eq2)
			throws EventFinished{

		// The minimum bed must be greater than 0
		DataAccess dBManager = new DataAccess();
		Event ev = null;

		if (new Date().compareTo(dato) > 0) {
			dBManager.close();
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		}
		ev = dBManager.createEvent(description, dato,eq1,eq2);

		dBManager.close();

		return ev;
	};
	
	
	@WebMethod
    public boolean repeatedEvent(String description, Date fech) {
        DataAccess db = new DataAccess();
        Event ev = db.findEvent(description, fech);
        db.close();
        if (ev ==null) {
            return false;
        }else {
            return true;
            }
        }
	@WebMethod
    public List<User> getAllUsers(){
		DataAccess db = new DataAccess();
		List<User> users = db.getAllUsers();
		db.close();
		for (User u : users) u.setPw("");
		return users;
	}
	
	
	
	@WebMethod
	public User editUser(User guiUser, String newUsername, String nombre, String ape1, String ape2, String DNI, String email, String tarj) throws UserAlreadyExists, PermisoDenegado{
		if (guiUser.isAdmin())throw new PermisoDenegado("No puedes editar el perfil de otro administrador");
		DataAccess db = new DataAccess();
		User u = null;
		try {
			u = db.editUser(guiUser.getUsername(), newUsername, nombre, ape1, ape2, DNI, email, tarj);
			u.setPw("");
		}catch (RollbackException e){
			throw new UserAlreadyExists("Nombre de usuario en uso");
		}
		catch(Exception e){
			System.out.println("ha pasado algo");
		} finally {
			db.close();
		}
		return u;
	}
	
	@WebMethod
	public User updateOwnUser(User guiUser, String newUsername, String nombre, String ape1, String ape2, String DNI, String email, String tarj){
		DataAccess db = new DataAccess();
		User u = db.editUser(guiUser.getUsername(), newUsername, nombre, ape1, ape2, DNI, email, tarj);
		db.close();
		return u;
	}
	@WebMethod
	public User getUserInfo(String username) throws ObjectNotFound{
		DataAccess db = new DataAccess();
		User u = db.findUser(username);
	
		u.setPw("");
		//new User(u.getUsername(), "", u.getEmail(),u.isAdmin());
		db.close();
		return u;
	}
	
	@WebMethod
	public User sumarPuntos(User user, int p, String tarj) throws UserIncomplete, NoPaymentMethod  {
		DataAccess db = new DataAccess();
		try {
		db.addPoints(user.getUsername(), p, tarj);
		user.sumarPuntos(p);
		} catch(UserIncomplete exu) {
			throw new UserIncomplete("Usuario "+user.getUsername()+" incompleto");
		} catch(NoPaymentMethod exp) {
			throw new NoPaymentMethod("No hay metodo de pago");
		} finally {
		db.close();
		}
		return user;
	}
	

	
	@WebMethod
	public User restarPuntos(User user, int p) throws InsufficientPoints  {
		DataAccess db = new DataAccess();
		try {
			db.remPoints(user.getUsername(), p);
			user.restarPuntos(p);
			} catch(InsufficientPoints exi) {
				throw new InsufficientPoints("Puntos insuficientes");
			} finally {
			db.close();
			}
		return user;
	}
	@WebMethod
	public List<domain.Event> getProximosEventos(){
		Date today = new Date();
		Calendar temp = Calendar.getInstance();
		temp.setTime(today);
		temp.add(Calendar.DATE, 60);
		Date twoMonthsLater = temp.getTime();
		DataAccess db = new DataAccess();
		List<domain.Event> ev = db.getEventsInterval(today,twoMonthsLater);
		db.close();
		return ev;
	}
	@WebMethod
	public void apostar(User u, Pronostico p, int cantidad) throws ObjectNotFound,InsufficientPoints, EventFinished{
		DataAccess db = new DataAccess();
		db.apostar(u, p, cantidad,new Vector<User>());
		db.close();
	}
	@WebMethod
	public void borrarPregunta(Question q) {
	DataAccess db = new DataAccess();
	db.borrarPregunta(q);
	db.close();
	}
	
	@WebMethod
	public void borrarUsuario(User u) {
	DataAccess db = new DataAccess();
	db.borrarUsuario(u);
	db.close();
	}
	
	@WebMethod
	public void editarPregunta(Question q, String pregunta, int betMin) throws QuestionAlreadyExist {
		DataAccess db = new DataAccess();
		db.editarPregunta(q, pregunta, betMin);
		db.close();
	}
	
	@WebMethod
	public void editarPronostico(Pronostico p, String pronostico, double cuota) throws PronosticoAlreadyExist {
		DataAccess db = new DataAccess();
		db.editarPronostico(p, pronostico, cuota);
		db.close();
	}
	@WebMethod
	public void borrarPronostico(Pronostico p) {
		DataAccess db = new DataAccess();
		db.borrarPronostico(p);
		db.close();
	}
	@WebMethod
	public Pronostico addPronostico(Question q, String pronostico, double cuota) throws PronosticoAlreadyExist {
		DataAccess db = new DataAccess();
		Pronostico p = db.addPronostico(q, pronostico, cuota);
		db.close();
		return p;
	}

	@Override
	public void CambiarContraseña(User user, String Password) {
		DataAccess db = new DataAccess();
		db.CambiarContraseña(user, Password);
		db.close();
	}
	@WebMethod
	public User cancelBet(Apuesta a) throws PreguntaYaResuelta {
		DataAccess db = new DataAccess();
		User u =db.cancelBet(a);
		db.close();
		return u;
	}
	@WebMethod
	public List<User> search100Users(String searchText, int index){
		DataAccess db = new DataAccess();
		List<User> lu = db.search100Users(searchText, index);
		db.close();
		for(User u: lu) {
			u.setPw("");
		}
		return lu;
	}
	@WebMethod
	public List<User> getFollowing(String username){
		DataAccess db = new DataAccess();
		List<User> lf= db.getFollowing(username);
		db.close();
		for (User u: lf) u.setPw("");
		
		return lf;
	}
	@WebMethod
	public List<Apuesta> getApuestas(String username){
		DataAccess db = new DataAccess();
		List<Apuesta> lf= db.getApuestas(username);
		db.close();
		return lf;
	}
	@WebMethod
	public void follow(String follower, String followed) throws ObjectNotFound{
		DataAccess db = new DataAccess();
		db.follow(follower, followed);
		db.close();
	}
	@WebMethod
	public void unfollow(String follower, String followed) throws ObjectNotFound{
		DataAccess db = new DataAccess();
		db.unfollow(follower, followed);
		db.close();
	}
	@WebMethod
	public List<Publication> getPublications(String username){
		DataAccess db = new DataAccess();
		List<Publication> lp = db.getPublications(username);
		db.close();
		return lp;
	}
	@WebMethod
	public List<Equipo> getAllEquipos(){
		DataAccess db = new DataAccess();
		List<Equipo> le = db.getAllEquipos();
		db.close();
		return le;
	}
	
	@WebMethod
	public void crearEquipo(String nombre, List<String> jugadores) {
		DataAccess db = new DataAccess();
		db.crearEquipo(nombre, jugadores);
		db.close();
		
	}
	@WebMethod
	public Publication publicar(String username, String msg) {
		DataAccess db = new DataAccess();
		Publication p = db.publicar(username, msg);
		db.close();
		return p;
	}
	@WebMethod
	public void updateAjustes(String username, Ajustes ajustes) {
		DataAccess db = new DataAccess();
		db.updateAjustes(username, ajustes);
		db.close();
	}
	@WebMethod
	public List<Notification> getNotificaciones(String username) {
		DataAccess db = new DataAccess();
		List<Notification> lista = db.getNotificaciones(username);
		db.close();
		return lista;	
	}
	@WebMethod
	public void setRead(int id) {
		DataAccess db = new DataAccess();
		db.setRead(id);
		db.close();
	}
	@WebMethod
	public void notificacionGeneral(String titulo, String mensaje) {
		DataAccess db = new DataAccess();
		db.notificacionGeneral(titulo, mensaje);
		db.close();
	}
	@WebMethod
	public List<domain.Event> getUnresolvedEvents(){
		DataAccess db = new DataAccess();
		List<domain.Event> lista = db.nonResolvedEvent();
		db.close();
		return lista;
	}
	@WebMethod
	public void resolverPregunta(Pronostico p){
		DataAccess db = new DataAccess();
		db.resolverPregunta(p);
		db.close();
	}
	@WebMethod
	public List<Equipo> search100Equipos(String searchText, int index){
		DataAccess db = new DataAccess();
		List<Equipo> lu = db.search100Equipos(searchText, index);
		db.close();
		return lu;
	}
	@WebMethod
	public void editarPregunta(Integer id, String titulo,int min ,List<Pronostico> pronosticos) throws QuestionAlreadyExist{
		DataAccess db = new DataAccess();
		db.editarPreguntaCompleta(id,titulo,min,pronosticos);
		db.close();
	}
	
	@WebMethod
	public void updateUsuarioCopiado(User userLogeado,User userCopiado,double ratio) {
		DataAccess db = new DataAccess();
		db.updateUsuarioCopiado(userLogeado,userCopiado,ratio);
		db.close();
	}
}
