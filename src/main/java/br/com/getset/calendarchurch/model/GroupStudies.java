package br.com.getset.calendarchurch.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupStudies {
	
	
	public GroupStudies(List<Study> studies) {
		this.studies = studies;
	}

	private List<Study> studies;

	public List<Study> getStudies() {
		return studies;
	}

	public void setStudies(List<Study> studies) {
		this.studies = studies;
	}

	
	
	

}
