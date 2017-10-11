package br.com.getset.calendarchurch.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@SuppressWarnings("serial")
@Entity
@Table(name="event")
public class Event implements Serializable{
	
	
	
	
	public Event() {
		this.idChurch = 1L; //Temporary
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_event")
	private Long idEvent;
	
	@XmlElement(name = "title")
	@Column(name = "ds_event")
	private String dsEvent;
	
	@XmlElement(name = "obs")
	@Column(name = "ds_obs")
	private String dsObs;
	
	@XmlElement(name = "startTime")
	@Column(name = "dt_start")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar dtStart;
	
	@XmlElement(name = "endTime")
	@Column(name = "dt_finish")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar dtFinish;
	
	@XmlElement(name = "allDay")
	@Column(name = "fl_allday")
	private boolean flAllDay;
	
	@XmlElement(name = "show")
	@Column(name = "fl_show")
	private boolean flShow;
	
	@XmlElement(name = "church")
	@Column(name = "id_church")
	private Long idChurch;
	
	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public String getDsEvent() {
		return dsEvent;
	}

	public void setDsEvent(String dsEvent) {
		this.dsEvent = dsEvent;
	}

	public String getDsObs() {
		return dsObs;
	}
	
	public String getDsObsSubstring() {
		if(dsObs.length()<=50)
			return this.dsObs;
		else
			return dsObs.substring(0, 50).concat("...");
	}
	
	public void setDsObs(String dsObs) {
		this.dsObs = dsObs;
	}

	public Calendar getDtStart() {
		return dtStart;
	}

	public void setDtStart(Calendar dtStart) {
		this.dtStart = dtStart;
	}

	public Calendar getDtFinish() {
		return dtFinish;
	}

	public void setDtFinish(Calendar dtFinish) {
		this.dtFinish = dtFinish;
	}
	
	public Calendar getHrStart() {
		return dtStart;
	}

	public void setHrStart(Calendar hrStart) {
		this.dtStart.set(Calendar.HOUR_OF_DAY, hrStart.get(Calendar.HOUR_OF_DAY));
		this.dtStart.set(Calendar.MINUTE, hrStart.get(Calendar.MINUTE));
	}

	public Calendar getHrFinish() {
		return dtFinish;
	}

	public void setHrFinish(Calendar hrFinish) {
		this.dtFinish.setTimeInMillis(this.dtStart.getTimeInMillis());
		this.dtFinish.set(Calendar.HOUR_OF_DAY, hrFinish.get(Calendar.HOUR_OF_DAY));
		this.dtFinish.set(Calendar.MINUTE, hrFinish.get(Calendar.MINUTE));
	}

	public String getDataFormatada(){
		String ret = "";
		if(flAllDay)
			ret = new SimpleDateFormat("dd/MM/yyyy").format(dtStart.getTime());
		else
			ret =  new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dtStart.getTime()).concat(" - "+new SimpleDateFormat("HH:mm").format(dtFinish.getTime()));
		return ret;
	}

	public boolean isFlAllDay() {
		return flAllDay;
	}

	public void setFlAllDay(boolean flAllDay) {
		this.flAllDay = flAllDay;
	}

	public boolean isFlShow() {
		return flShow;
	}

	public void setFlShow(boolean flShow) {
		this.flShow = flShow;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEvent == null) ? 0 : idEvent.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Event other = (Event) obj;
		return this.idEvent.compareTo(other.idEvent) == 0;
	}
	
	@Override
	public String toString() {
		return dsEvent;
	}
	
}
