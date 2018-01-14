package br.com.getset.calendarchurch.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.getset.calendarchurch.dao.EventDao;
import br.com.getset.calendarchurch.model.Event;
import br.com.getset.calendarchurch.model.GroupNotifications;

@SuppressWarnings("serial")
public class EventService implements Serializable{
	@Inject
	private EventDao eDao;
	private Event event;
	
	public GroupNotifications getAllEvents(){
		GroupNotifications gp = new GroupNotifications(eDao.findAll(),"Evento");
		return gp;
	}
	
	public GroupNotifications getEventsShow(){
		GroupNotifications gp = new GroupNotifications(eDao.findOnlyShow(),"Evento");
		return gp;
	}
	
	public EventDao geteDao() {
		return eDao;
	}
	public void seteDao(EventDao eDao) {
		this.eDao = eDao;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	
	
	
	
	
}
