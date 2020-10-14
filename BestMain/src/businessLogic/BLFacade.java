package businessLogic;

import java.util.Vector;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.*;
import exceptions.*;

import javax.jws.WebMethod;
import javax.jws.WebService;


/**
 * Interface that specifies the business logic.
 */
@WebService(endpointInterface = "service.WebServiceLogicInterface")
public interface BLFacade {

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
	public Question createQuestion(Event event, String question, int betMinimum, List<Pronostico> pronosticos) throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public List<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public List<Date> getEventsMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();
	
	@WebMethod
	public Event createEvent(String description, Date dato,Equipo eq1, Equipo eq2)
			throws EventFinished;
	
	@WebMethod
	public boolean repeatedEvent(String description, Date fech);
	
	@WebMethod
    public List<User> getAllUsers();
	@WebMethod
	public User editUser(User guiUser, String newUsername, String nombre, String ape1, String ape2, String DNI, String email, String tarj) throws UserAlreadyExists, PermisoDenegado;
	/**
	 * usará el parametro username para encontrar un usuario. Devolverá ese usuario con el campo de la contraseña vacio.
	 */
	@WebMethod
	public User getUserInfo(String username) throws ObjectNotFound;
	
	@WebMethod
	public User sumarPuntos(User user, int p, String tarj) throws UserIncomplete, NoPaymentMethod;
	
	@WebMethod
	public User restarPuntos(User user, int p) throws  InsufficientPoints;
	
	@WebMethod
	public User updateOwnUser(User guiUser, String newUsername, String nombre, String ape1, String ape2, String DNI, String email, String tarj);
	@WebMethod
	public List<domain.Event> getProximosEventos();
	@WebMethod
	public void apostar(User u, Pronostico p, int cantidad) throws ObjectNotFound,InsufficientPoints, EventFinished;
	@WebMethod
	public void borrarPregunta(Question q);
	@WebMethod
	public void borrarUsuario(User u);
	@WebMethod
	public void editarPregunta(Question q, String pregunta, int betMin) throws QuestionAlreadyExist;
	@WebMethod
	public void editarPronostico(Pronostico p, String pronostico, double cuota) throws PronosticoAlreadyExist;
	@WebMethod
	public void borrarPronostico(Pronostico p);
	@WebMethod
	public Pronostico addPronostico(Question q, String pronostico, double cuota) throws PronosticoAlreadyExist;
	@WebMethod
	public void CambiarContraseña(User u,String Password);
	@WebMethod
	public User cancelBet(Apuesta a) throws PreguntaYaResuelta;
	@WebMethod
	public List<User> search100Users(String searchText, int index);
	@WebMethod
	public List<Equipo> search100Equipos(String searchText, int index);
	@WebMethod
	public List<User> getFollowing(String username);
	@WebMethod
	public List<Apuesta> getApuestas(String username);
	@WebMethod
	public void follow(String follower, String followed) throws ObjectNotFound;
	@WebMethod
	public void unfollow(String follower, String followed) throws ObjectNotFound;
	@WebMethod
	public List<Publication> getPublications(String username);
	@WebMethod
	public List<Equipo> getAllEquipos();
	@WebMethod
	public void crearEquipo(String nombre, List<String> jugadores);
	@WebMethod
	public Publication publicar(String username, String msg);
	@WebMethod
	public void updateAjustes(String username, Ajustes ajustes);
	@WebMethod
	public List<Notification> getNotificaciones(String username);
	@WebMethod
	public void setRead(int id);
	@WebMethod
	public void notificacionGeneral(String titulo, String mensaje);
	@WebMethod
	public List<domain.Event> getUnresolvedEvents();
	@WebMethod
	public void resolverPregunta(Pronostico p);
	@WebMethod
	public void editarPregunta(Integer id, String titulo,int min ,List<Pronostico> pronosticos)  throws QuestionAlreadyExist;
	@WebMethod
	public void updateUsuarioCopiado(User userLogeado,User userCopiado,double ratio);
	
}

