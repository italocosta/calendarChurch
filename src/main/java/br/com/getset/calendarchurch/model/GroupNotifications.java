package br.com.getset.calendarchurch.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupNotifications {
	
	
	public GroupNotifications(List<?> objs, String type) {
		this.objs = objs;
		this.typeNotification = type;
	}

	private List<?> objs;
	
	private String typeNotification;

	public List<?> getObjs() {
		return objs;
	}

	public void setObjs(List<?> objs) {
		this.objs = objs;
	}

	public String getTypeNotification() {
		return typeNotification;
	}

	public void setTypeNotification(String typeNotification) {
		this.typeNotification = typeNotification;
	}
	
	


	
	

}
