package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@XmlID
	private String username;
	private String userVisibleName;
	private String pw;
	private String email;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String DNI;
	private boolean admin;
	private int puntos; 
	private String tarjeta;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@XmlIDREF
	private List<Copia> copiando;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@XmlIDREF
	private List<Apuesta> apuestas;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@XmlIDREF
	private List<Publication> publicaciones;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Notification> notificaciones;
	@OneToMany(fetch = FetchType.LAZY)
	@XmlIDREF
	private List<User> followers;
	@OneToMany(fetch = FetchType.LAZY)
	@XmlIDREF
	private List<User> following;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Ajustes settings;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	public List<Publication> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publication> publicaciones) {
		this.publicaciones = publicaciones;
	}

	public List<Notification> getNotificaciones() {
		return notificaciones;
	}

	public void setNotificaciones(List<Notification> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public String getUserVisibleName() {
		return userVisibleName;
	}

	public void setUserVisibleName(String userVisibleName) {
		this.userVisibleName = userVisibleName;
	}
	public User(String username, String pw, String email, boolean admin) {
		this.username = username;
		this.userVisibleName = username;
		this.pw = pw;
		this.email = email;
		this.admin = admin;
		this.nombre = "";
		this.apellido1 = "";
		this.apellido2 = "";
		this.DNI = "";
		this.puntos = 0;
		this.tarjeta = "";
		this.apuestas = new ArrayList<Apuesta>();
		this.following = new ArrayList<User>();
		this.followers = new ArrayList<User>();
		this.publicaciones = new ArrayList<Publication>();
		this.notificaciones = new ArrayList<Notification>();
		this.settings = new Ajustes(); 
		this.copiando = new Vector<Copia>();
		
	}
	
	public List<Copia> getCopiando() {
		return copiando;
	}

	public void setCopiando(List<Copia> copiando) {
		this.copiando = copiando;
	}

	public User(String username, String pw, String email, String nombre, String apellido1, String apellido2, String dNI,
			boolean admin, String tarj) {
		super();
		this.username = username;
		this.userVisibleName = username;
		this.pw = pw;
		this.email = email;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		DNI = dNI;
		this.admin = admin;
		this.puntos = 0;
		this.tarjeta = tarj;
		this.apuestas = new ArrayList<Apuesta>();
		this.apuestas = new ArrayList<Apuesta>();
		this.following = new ArrayList<User>();
		this.followers = new ArrayList<User>();
		this.publicaciones = new ArrayList<Publication>();
		this.notificaciones = new ArrayList<Notification>();
		this.settings = new Ajustes(); 
		this.copiando = new Vector<Copia>();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String nombre) {
		this.username = nombre;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public List<Apuesta> getApuestas() {
		return apuestas;
	}
	public void setApuestas(List<Apuesta> apuestas) {
		this.apuestas = apuestas;
	}
	public String toString() {
		return userVisibleName;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public void sumarPuntos(int p) {
		this.puntos = this.puntos +p;
	}
	public void restarPuntos(int p) {
		this.puntos = this.puntos -p;
	}
	public void addApuesta(Apuesta a) {
		this.apuestas.add(0, a);
	}
	public void removeApuesta(Apuesta a) {
		this.apuestas.remove(a);
	}
	
	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}
	public void addFollower(User u) {
		followers.add(u);
	}
	public void addFollowing(User u) {
		following.add(u);
	}
	public void removeFollower(User u) {
		followers.remove(u);
	}
	public void removeFollowing(User u) {
		following.remove(u);
	}
	public void addPublication(Publication p) {
		publicaciones.add(0, p);
	}
	public void addNotification(Notification n) {
		notificaciones.add(0, n);
		if(notificaciones.size()>100)notificaciones.remove(100);
	}
	
	public Ajustes getSettings() {
		return settings;
	}

	public void setSettings(Ajustes settings) {
		this.settings = settings;
	}

	public void copy(User u) {
		this.apellido1 = u.getApellido1();
		this.apellido2 = u.getApellido2();
		this.apuestas = u.getApuestas();
		this.DNI = u.getDNI();
		this.email = u.getEmail();
		this.nombre = u.getNombre();
		this.puntos= u.getPuntos();
		this.pw = u.getPw();
		this.tarjeta = u.getTarjeta();
		this.username = u.getUsername();
		this.userVisibleName = u.getUserVisibleName();
		this.settings = u.getSettings(); 
	}
	
	public void addCopiando(Copia c) {
		copiando.add(c);
	}
	public void rmCopiando(User u) {
		for(Copia c:copiando) {
			if(c.getUserCopiando().equals(u)) {
				copiando.remove(c);
				return;
			}
		}
	}

	public User() {
		super();
	}
	
}
