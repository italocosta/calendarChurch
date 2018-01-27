package br.com.getset.calendarchurch.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.getset.calendarchurch.model.Study;
import br.com.getset.calendarchurch.model.TypeStudy;

@SuppressWarnings("serial")
public class StudyDao implements Serializable{
	@Inject
	private EntityManager entityManager;
	
	public StudyDao(){}
	
	
	public Study getById(final Long id) {
		try{
			return entityManager.find(Study.class, id);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("finally")
	public String getContent(Long id){
		String result = "";
		try{
			result = entityManager.createQuery("select s.dsContent FROM "+Study.class.getName()+" s where s.idStudy = :id")
					.setParameter("id", id)
					.getSingleResult().toString();
		}catch(NoResultException er){
			System.out.println("Consultou conteudo de estudo inexistente");
		}finally {
			return result;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Study> findAll(){
		return entityManager.createQuery("FROM "+Study.class.getName()).getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Study> findAllRest(){
		return entityManager.createQuery("FROM "+Study.class.getName()
				+" s where s.flShow = true").getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Study> findCellRest(){
		return entityManager.createQuery("FROM "+Study.class.getName()
				+" s where s.flShow = true and tpStudy = "+TypeStudy.CELULA.getTipo()).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Study> findByDateTipo(Integer month, Integer year, TypeStudy tipo){
		return entityManager.createQuery("FROM "+Study.class.getName()+
				" e where year(e.dtStudy) = :y and month(e.dtStudy) = :m and e.tpStudy = :t order by e.dtStudy")
				.setParameter("y", year)
				.setParameter("m", month)
				.setParameter("t", tipo)
				.getResultList();
	}
	
	
	public void insert(Study study){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		entityManager.persist(study);
		entityManager.getTransaction().commit();
	}
	
	public void update(Study study){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		entityManager.merge(study);
		entityManager.getTransaction().commit();
	}
	
	public void remove(Study study){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		study = entityManager.find(Study.class, study.getIdStudy());
		entityManager.remove(entityManager.merge(study));
		entityManager.getTransaction().commit();
	}
}
