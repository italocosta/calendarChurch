package br.com.getset.calendarchurch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="church")
public class Church implements Serializable{
	@Id
	@Column(name = "id_church")
	private Long idChurch;
	
	@Column(name = "ds_church")
	private String dsChurch;
	
	public Long getIdChurch() {
		return idChurch;
	}

	public void setIdChurch(Long idChurch) {
		this.idChurch = idChurch;
	}

	public String getDsChurch() {
		return dsChurch;
	}

	public void setDsChurch(String dsChurch) {
		this.dsChurch = dsChurch;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idChurch == null) ? 0 : idChurch.hashCode());
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
		final Church other = (Church) obj;
		return this.idChurch.compareTo(other.idChurch) == 0;
	}
	
	@Override
	public String toString() {
		return dsChurch;
	}
	
}
