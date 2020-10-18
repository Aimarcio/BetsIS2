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

	public DataAccess dbManager;
	
	
	public BLFacadeImplementation(DataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();
		}
		dbManager=da;
		}
	
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
		dbManager.open (false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum, pronosticos);

		dbManager.close();

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
		dbManager.open (false);
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
		dbManager.open (false);
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
		dbManager.open (false);
		dbManager.initializeDB();
		dbManager.close();
	}

	@WebMethod
	public User login(String username, String password) {
		dbManager.open (false);
		User u = dbManager.findUser(username);
		dbManager.close();
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
		dbManager.open (false);		
		if(dbManager.findUser(username)!=null) {
			dbManager.close();
			System.out.println("Error");
			throw new UserAlreadyExists("username already exists");
		}	
		System.out.println("OKE MASTA");
		dbManager.addUser(u);
		dbManager.close();
	}
	
	@WebMethod
	public void registerAdmin(String username, String password, String email) throws UserAlreadyExists {
		User u = new User(username,password,email,true);
		dbManager.open (false);
		try{
			dbManager.addUser(u);
		}catch(Exception UserAlreadyExists) {
			dbManager.close();
			throw new UserAlreadyExists("username already exists");
		}
		dbManager.close();
	}
	@WebMethod
	public Event createEvent(String description, Date dato,Equipo eq1, Equipo eq2)
			throws EventFinished{

		// The minimum bed must be greater than 0
		dbManager.open (false);
		Event ev = null;

		if (new Date().compareTo(dato) > 0) {
			dbManager.close();
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
		}
		ev = dbManager.createEvent(description, dato,eq1,eq2);

		dbManager.close();

		return ev;
	};
	
	
	@WebMethod
    public boolean repeatedEvent(String description, Date fech) {
		dbManager.open (false);
		Event ev = dbManager.findEvent(description, fech);
		dbManager.close();
        if (ev ==null) {
            return false;
        }else {
            return true;
            }
        }
	@WebMethod
    public List<User> getAllUsers(){
		dbManager.open (false);
		List<User> users = dbManager.getAllUsers();
		dbManager.close();
		for (User u : users) u.setPw("");
		return users;
	}
	
	
	
	@WebMethod
	public User editUser(User guiUser, String newUsername, String nombre, String ape1, String ape2, String DNI, String email, String tarj) throws UserAlreadyExists, PermisoDenegado{
		if (guiUser.isAdmin())throw new PermisoDenegado("No puedes editar el perfil de otro administrador");
		dbManager.open (false);
		User u = null;
		try {
			u = dbManager.editUser(guiUser.getUsername(), newUsername, nombre, ape1, ape2, DNI, email, tarj);
			u.setPw("");
		}catch (RollbackException e){
			throw new UserAlreadyExists("Nombre de usuario en uso");
		}
		catch(Exception e){
			System.out.println("ha pasado algo");
		} finally {
			dbManager.close();
		}
		return u;
	}
	
	@WebMethod
	public User updateOwnUser(User guiUser, String newUsername, String nombre, String ape1, String ape2, String DNI, String email, String tarj){
		dbManager.open (false);
		User u = dbManager.editUser(guiUser.getUsername(), newUsername, nombre, ape1, ape2, DNI, email, tarj);
		dbManager.close();
		return u;
	}
	@WebMethod
	public User getUserInfo(String username) throws ObjectNotFound{
		dbManager.open (false);
		User u = dbManager.findUser(username);
	
		u.setPw("");
		//new User(u.getUsername(), "", u.getEmail(),u.isAdmin());
		dbManager.close();
		return u;
	}
	
	@WebMethod
	public User sumarPuntos(User user, int p, String tarj) throws UserIncomplete, NoPaymentMethod  {
		dbManager.open (false);
		try {
			dbManager.addPoints(user.getUsername(), p, tarj);
		user.sumarPuntos(p);
		} catch(UserIncomplete exu) {
			throw new UserIncomplete("Usuario "+user.getUsername()+" incompleto");
		} catch(NoPaymentMethod exp) {
			throw new NoPaymentMethod("No hay metodo de pago");
		} finally {
			dbManager.close();
		}
		return user;
	}
	

	
	@WebMethod
	public User restarPuntos(User user, int p) throws InsufficientPoints  {
		dbManager.open (false);
		try {
			dbManager.remPoints(user.getUsername(), p);
			user.restarPuntos(p);
			} catch(InsufficientPoints exi) {
				throw new InsufficientPoints("Puntos insuficientes");
			} finally {
				dbManager.close();
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
		dbManager.open (false);
		List<domain.Event> ev = dbManager.getEventsInterval(today,twoMonthsLater);
		dbManager.close();
		return ev;
	}
	@WebMethod
	public void apostar(User u, Pronostico p, int cantidad) throws ObjectNotFound,InsufficientPoints, EventFinished{
		dbManager.open(false);
		dbManager.apostar(u, p, cantidad,new Vector<User>());
		dbManager.close();
	}
	@WebMethod
	public void borrarPregunta(Question q) {
		dbManager.open(false);
		dbManager.borrarPregunta(q);
		dbManager.close();
	}
	
	@WebMethod
	public void borrarUsuario(User u) {
		dbManager.open (false);
		dbManager.borrarUsuario(u);
		dbManager.close();
	}
	
	@WebMethod
	public void editarPregunta(Question q, String pregunta, int betMin) throws QuestionAlreadyExist {
		dbManager.open (false);
		dbManager.editarPregunta(q, pregunta, betMin);
		dbManager.close();
	}
	
	@WebMethod
	public void editarPronostico(Pronostico p, String pronostico, double cuota) throws PronosticoAlreadyExist {
		dbManager.open (false);
		dbManager.editarPronostico(p, pronostico, cuota);
		dbManager.close();
	}
	@WebMethod
	public void borrarPronostico(Pronostico p) {
		dbManager.open (false);
		dbManager.borrarPronostico(p);
		dbManager.close();
	}
	@WebMethod
	public Pronostico addPronostico(Question q, String pronostico, double cuota) throws PronosticoAlreadyExist {
		dbManager.open (false);
		Pronostico p = dbManager.addPronostico(q, pronostico, cuota);
		dbManager.close();
		return p;
	}

	@Override
	public void CambiarContraseña(User user, String Password) {
		dbManager.open (false);
		dbManager.CambiarContraseña(user, Password);
		dbManager.close();
	}
	@WebMethod
	public User cancelBet(Apuesta a) throws PreguntaYaResuelta {
		dbManager.open (false);
		User u =dbManager.cancelBet(a);
		dbManager.close();
		return u;
	}
	@WebMethod
	public List<User> search100Users(String searchText, int index){
		dbManager.open (false);
		List<User> lu = dbManager.search100Users(searchText, index);
		dbManager.close();
		for(User u: lu) {
			u.setPw("");
		}
		return lu;
	}
	@WebMethod
	public List<User> getFollowing(String username){
		dbManager.open (false);
		List<User> lf= dbManager.getFollowing(username);
		dbManager.close();
		for (User u: lf) u.setPw("");
		
		return lf;
	}
	@WebMethod
	public List<Apuesta> getApuestas(String username){
		dbManager.open (false);
		List<Apuesta> lf= dbManager.getApuestas(username);
		dbManager.close();
		return lf;
	}
	@WebMethod
	public void follow(String follower, String followed) throws ObjectNotFound{
		dbManager.open (false);
		dbManager.follow(follower, followed);
		dbManager.close();
	}
	@WebMethod
	public void unfollow(String follower, String followed) throws ObjectNotFound{
		dbManager.open (false);
		dbManager.unfollow(follower, followed);
		dbManager.close();
	}
	@WebMethod
	public List<Publication> getPublications(String username){
		dbManager.open (false);
		List<Publication> lp = dbManager.getPublications(username);
		dbManager.close();
		return lp;
	}
	@WebMethod
	public List<Equipo> getAllEquipos(){
		dbManager.open (false);
		List<Equipo> le = dbManager.getAllEquipos();
		dbManager.close();
		return le;
	}
	
	@WebMethod
	public void crearEquipo(String nombre, List<String> jugadores) {
		dbManager.open (false);
		dbManager.crearEquipo(nombre, jugadores);
		dbManager.close();
		
	}
	@WebMethod
	public Publication publicar(String username, String msg) {
		dbManager.open (false);
		Publication p = dbManager.publicar(username, msg);
		dbManager.close();
		return p;
	}
	@WebMethod
	public void updateAjustes(String username, Ajustes ajustes) {
		dbManager.open (false);
		dbManager.updateAjustes(username, ajustes);
		dbManager.close();
	}
	@WebMethod
	public List<Notification> getNotificaciones(String username) {
		dbManager.open (false);
		List<Notification> lista = dbManager.getNotificaciones(username);
		dbManager.close();
		return lista;	
	}
	@WebMethod
	public void setRead(int id) {
		dbManager.open (false);
		dbManager.setRead(id);
		dbManager.close();
	}
	@WebMethod
	public void notificacionGeneral(String titulo, String mensaje) {
		dbManager.open (false);
		dbManager.notificacionGeneral(titulo, mensaje);
		dbManager.close();
	}
	@WebMethod
	public List<domain.Event> getUnresolvedEvents(){
		dbManager.open (false);
		List<domain.Event> lista = dbManager.nonResolvedEvent();
		dbManager.close();
		return lista;
	}
	@WebMethod
	public void resolverPregunta(Pronostico p){
		dbManager.open (false);
		dbManager.resolverPregunta(p);
		dbManager.close();
	}
	@WebMethod
	public List<Equipo> search100Equipos(String searchText, int index){
		dbManager.open (false);
		List<Equipo> lu = dbManager.search100Equipos(searchText, index);
		dbManager.close();
		return lu;
	}
	@WebMethod
	public void editarPregunta(Integer id, String titulo,int min ,List<Pronostico> pronosticos) throws QuestionAlreadyExist{
		dbManager.open (false);
		dbManager.editarPreguntaCompleta(id,titulo,min,pronosticos);
		dbManager.close();
	}
	
	@WebMethod
	public void updateUsuarioCopiado(User userLogeado,User userCopiado,double ratio) {
		dbManager.open (false);
		dbManager.updateUsuarioCopiado(userLogeado,userCopiado,ratio);
		dbManager.close();
	}
}
