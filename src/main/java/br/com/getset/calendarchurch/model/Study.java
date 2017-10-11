package br.com.getset.calendarchurch.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@SuppressWarnings("serial")
@Entity
@Table(name="study")
public class Study implements Serializable{
	
	@XmlElement(name = "idStudy")
	@Id
	@Column(name = "id_study")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idStudy;
	
	@XmlElement(name = "title")
	@Column(name = "ds_title")
	private String dsTitle;
	
	@Column(name = "ds_content",columnDefinition = "TEXT")
	private String dsContent;
	
	@XmlElement(name = "date")
	@Column(name = "dt_study")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Calendar dtStudy;
	
	@XmlElement(name = "show")
	@Column(name = "fl_show")
	private boolean flShow;
	
	@XmlElement(name = "church")
	@Column(name = "id_church")
	private Long idChurch;
	
	@Column(name = "tp_study")
	@Enumerated(EnumType.ORDINAL)
	private TypeStudy tpStudy;


	public Long getIdStudy() {
		return idStudy;
	}

	public void setIdStudy(Long idStudy) {
		this.idStudy = idStudy;
	}

	public String getDsTitle() {
		return dsTitle;
	}

	public void setDsTitle(String dsTitle) {
		this.dsTitle = dsTitle;
	}
	
	@XmlTransient
	public String getDsContent() {
		return dsContent;
	}

	public void setDsContent(String dsContent) {
		this.dsContent = dsContent;
	}

	public Calendar getDtStudy() {
		return dtStudy;
	}

	public void setDtStudy(Calendar dtStudy) {
		this.dtStudy = dtStudy;
	}
	
	public String getDataFormatada(){
		return new SimpleDateFormat("dd/MM/yyyy").format(dtStudy.getTime());
	}

	public boolean isFlShow() {
		return flShow;
	}

	public void setFlShow(boolean flShow) {
		this.flShow = flShow;
	}

	public Long getIdChurch() {
		return idChurch;
	}

	public void setIdChurch(Long idChurch) {
		this.idChurch = idChurch;
	}
	
	public TypeStudy getTpStudy() {
		return tpStudy;
	}

	public void setTpStudy(TypeStudy tpStudy) {
		this.tpStudy = tpStudy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idChurch == null) ? 0 : idChurch.hashCode());
		result = prime * result + ((idStudy == null) ? 0 : idStudy.hashCode());
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
		Study other = (Study) obj;
		if (idChurch == null) {
			if (other.idChurch != null)
				return false;
		} else if (!idChurch.equals(other.idChurch))
			return false;
		if (idStudy == null) {
			if (other.idStudy != null)
				return false;
		} else if (!idStudy.equals(other.idStudy))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return dsTitle;
	}
	
	
	
}
