package br.com.getset.calendarchurch.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.getset.calendarchurch.model.Event;
import br.com.getset.calendarchurch.util.Resources;

@SuppressWarnings("serial")
public class EventDao implements Serializable{
	@Inject
	private EntityManager entityManager;
	
	public EventDao(){}
	
	
	public EventDao(boolean timer){
		this.entityManager = Resources.getEntityManager();
	}
	
	public EventDao(EntityManager em){
		this.entityManager = em;
	}
	
	public Event getById(final Long id) {
		try{
			return entityManager.find(Event.class, id);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findAll(){
		return entityManager.createQuery("FROM "+Event.class.getName()).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findOnlyShow(){
		return entityManager.createQuery("FROM "+Event.class.getName()+" e where e.flShow = :s")
				.setParameter("s", true)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findByDate(Integer month, Integer year){
		return entityManager.createQuery("FROM "+Event.class.getName()+" e where year(e.dtStart) = :y and month(e.dtStart) = :m order by e.dtStart")
				.setParameter("y", year)
				.setParameter("m", month)
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> findByDay(Calendar c){
		return entityManager.createQuery("FROM "+Event.class.getName()+" e where e.flShow = true and year(e.dtStart) = :y and month(e.dtStart) = :m and day(e.dtStart) = :d order by e.dtStart")
				.setParameter("y", c.get(Calendar.YEAR))
				.setParameter("m", c.get(Calendar.MONTH)+1)
				.setParameter("d", c.get(Calendar.DAY_OF_MONTH))
				.getResultList();
	}
	
	
	public void insert(Event event){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		entityManager.persist(event);
		entityManager.getTransaction().commit();
	}
	
	public void update(Event event){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		entityManager.merge(event);
		entityManager.getTransaction().commit();
	}
	
	public void remove(Event event){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		event = entityManager.find(Event.class, event.getIdEvent());
		entityManager.remove(entityManager.merge(event));
		entityManager.getTransaction().commit();
	}
}
