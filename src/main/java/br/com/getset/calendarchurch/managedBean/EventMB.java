package br.com.getset.calendarchurch.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.getset.calendarchurch.dao.EventDao;
import br.com.getset.calendarchurch.model.Event;
import br.com.getset.calendarchurch.model.GroupNotifications;
import br.com.getset.calendarchurch.service.PushNotificationService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class EventMB implements Serializable{
	@Inject
	private EventDao eDao;
	@Inject
	private PushNotificationService pnService;
	private Event eventSelect;
	private Event newEvent;
	private Integer month;
	private Integer year;
	private List<Event> listEvent;
	private boolean sendNotification;
	
	@PostConstruct
	protected void init(){
		eventSelect = new Event();
		Calendar c = Calendar.getInstance();
		month = c.get(Calendar.MONTH)+1;
		year = c.get(Calendar.YEAR);
		sendNotification = true;
		searchEvents();
	}
	
	
	public String goPage() {
		return "index?faces-redirect=true";
	}
	
	public void preparNewEvent(){
		newEvent = new Event();
		newEvent.setDtStart(Calendar.getInstance());
		newEvent.getDtStart().set(Calendar.HOUR_OF_DAY, 19);
		newEvent.getDtStart().set(Calendar.MINUTE, 30);
		newEvent.setDtFinish(Calendar.getInstance());
		newEvent.getDtFinish().set(Calendar.HOUR_OF_DAY, 21);
		newEvent.getDtFinish().set(Calendar.MINUTE, 0);
	}
	
	public void updateEvent(){
		eDao.update(eventSelect);
		if(eventSelect.isFlShow() && sendNotification)
			sendNotificationEventsUpdate(eventSelect);
		sendNotification = true;
	}
	
	public void deleteEvent(){
		eDao.remove(eventSelect);
		if(eventSelect.isFlShow())
			sendNotificationEventsRemove(eventSelect);
		listEvent.remove(eventSelect);
	}
	
	public void insertEvent(){
		if(newEvent.isFlAllDay()){
			newEvent.getDtFinish().set(Calendar.DAY_OF_MONTH, newEvent.getDtStart().get(Calendar.DAY_OF_MONTH));
			newEvent.getDtFinish().set(Calendar.MONTH, newEvent.getDtStart().get(Calendar.MONTH));
			newEvent.getDtFinish().set(Calendar.YEAR, newEvent.getDtStart().get(Calendar.YEAR));
		}
		eDao.insert(newEvent);
		if(newEvent.isFlShow())
			sendNotificationEventsInsert(newEvent);
		if(newEvent.getDtStart().get(Calendar.MONTH)+1 == this.month &&
				newEvent.getDtStart().get(Calendar.YEAR) == this.year){
			listEvent.add(newEvent);
		}
	}
	
	public void searchEvents(){
		listEvent = eDao.findByDate(month, year);
	}
	
	public void setAllDay(String evento){
		if(evento.equalsIgnoreCase("edita")){
			if(eventSelect != null){
				eventSelect.getDtStart().set(Calendar.HOUR_OF_DAY, 0);
				eventSelect.getDtStart().set(Calendar.MINUTE, 0);
				eventSelect.getDtFinish().set(Calendar.HOUR_OF_DAY, 23);
				eventSelect.getDtFinish().set(Calendar.MINUTE, 0);
			}
		}else{
			if(newEvent != null){
				newEvent.getDtStart().set(Calendar.HOUR_OF_DAY, 0);
				newEvent.getDtStart().set(Calendar.MINUTE, 0);
				newEvent.getDtFinish().set(Calendar.HOUR_OF_DAY, 23);
				newEvent.getDtFinish().set(Calendar.MINUTE, 0);
			}
		}
	}
	
	public void sendNotificationEventsInsert(Event evt){
		try {
			pnService.sendNotificationBasic("Nova programação - Miash", evt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendNotificationEventsUpdate(Event evt){
		try {
			pnService.sendNotificationBasic("Programação alterada - Miash", evt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendNotificationEventsRemove(Event evt){
		try {
			pnService.sendNotificationBasic("Programação cancelada - Miash", evt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendNotificationEventsTomorrow(){
		try {
			pnService.sendNotificationBasic("Agenda de amanhã - Miash", getEventsTomorrow());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendNotificationEventsToday(){
		try {
			pnService.sendNotificationBasic("Agenda de hoje - Miash", getEventsToday());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private GroupNotifications getEventsTomorrow(){
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		return new GroupNotifications(eDao.findByDay(tomorrow),"Evento");
	}
	
	private GroupNotifications getEventsToday(){
		Calendar tomorrow = Calendar.getInstance();
		return new GroupNotifications(eDao.findByDay(tomorrow),"Evento");
	}
	
	public EventDao geteDao() {
		return eDao;
	}


	public void seteDao(EventDao eDao) {
		this.eDao = eDao;
	}


	public Event getEventSelect() {
		return eventSelect;
	}


	public void setEventSelect(Event eventSelect) {
		this.eventSelect = eventSelect;
	}


	public Event getNewEvent() {
		return newEvent;
	}


	public void setNewEvent(Event newEvent) {
		this.newEvent = newEvent;
	}


	public List<Event> getListEvent() {
		return listEvent;
	}


	public void setListEvent(List<Event> listEvent) {
		this.listEvent = listEvent;
	}


	public Integer getMonth() {
		return month;
	}


	public void setMonth(Integer month) {
		this.month = month;
	}


	public Integer getYear() {
		return year;
	}


	public void setYear(Integer year) {
		this.year = year;
	}


	public boolean isSendNotification() {
		return sendNotification;
	}


	public void setSendNotification(boolean sendNotification) {
		this.sendNotification = sendNotification;
	}
	
	

	
}
