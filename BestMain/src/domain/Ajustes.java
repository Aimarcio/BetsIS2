package domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Ajustes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id@GeneratedValue
	@XmlID
	private int id;
	private boolean notificacionesPublicaciones;
	private boolean notificacionesApuesta;
	private boolean perfil;
	private boolean apuestasprivadas;
	
	
	
	public Ajustes() {
		super();
		this.notificacionesPublicaciones = true;
		this.notificacionesApuesta = true;
		this.perfil = false;
		this.apuestasprivadas = false;
	}
	
	public Ajustes(boolean notificacionesPublicaciones, boolean notificacionesApuesta, boolean perfil,
			boolean apuestasprivadas) {
		super();
		this.notificacionesPublicaciones = notificacionesPublicaciones;
		this.notificacionesApuesta = notificacionesApuesta;
		this.perfil = perfil;
		this.apuestasprivadas = apuestasprivadas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isNotificacionesPublicaciones() {
		return notificacionesPublicaciones;
	}

	public void setNotificacionesPublicaciones(boolean notificacionesPublicaciones) {
		this.notificacionesPublicaciones = notificacionesPublicaciones;
	}

	public boolean isNotificacionesApuesta() {
		return notificacionesApuesta;
	}

	public void setNotificacionesApuesta(boolean notificacionesApuesta) {
		this.notificacionesApuesta = notificacionesApuesta;
	}

	public boolean isPerfil() {
		return perfil;
	}

	public void setPerfil(boolean perfil) {
		this.perfil = perfil;
	}

	public boolean isApuestasprivadas() {
		return apuestasprivadas;
	}

	public void setApuestasprivadas(boolean apuestasprivadas) {
		this.apuestasprivadas = apuestasprivadas;
	}

	@Override
	public String toString() {
		return "Ajustes [notificacionesPublicaciones=" + notificacionesPublicaciones + ", notificacionesApuesta="
				+ notificacionesApuesta + ", perfil=" + perfil + ", apuestasprivadas=" + apuestasprivadas + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ajustes other = (Ajustes) obj;
		if (apuestasprivadas != other.apuestasprivadas)
			return false;
		if (notificacionesApuesta != other.notificacionesApuesta)
			return false;
		if (notificacionesPublicaciones != other.notificacionesPublicaciones)
			return false;
		if (perfil != other.perfil)
			return false;
		return true;
	}

}
