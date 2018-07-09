package br.com.getset.calendarchurch.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.getset.calendarchurch.model.Study;
import br.com.getset.calendarchurch.model.TypeStudy;

@SuppressWarnings("serial")
public class StudyDao implements Serializable {
	@Inject
	private EntityManager entityManager;

	public StudyDao() {
	}

	public Study getById(final Long id) {
		try {
			return entityManager.find(Study.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("finally")
	public String getContent(Long idStudy) {
		String result = "";
		try {
			result = entityManager
					.createQuery("select s.dsContent FROM " + Study.class.getName() + " s where s.idStudy = :idStudy")
					.setParameter("idStudy", idStudy).getSingleResult().toString();
		} catch (NoResultException er) {
			System.out.println("Consultou conteudo de estudo inexistente");
		} finally {
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Study> findAll() {
		return entityManager.createQuery("FROM " + Study.class.getName()).getResultList();
	}

	public List<Study> findAllRest() {
		List<Study> listStudies = new ArrayList<Study>();
		listStudies.addAll(findCellRest().subList(0, 5));
		listStudies.addAll(find12Rest().subList(0, 5));
		listStudies.addAll(findChildRest().subList(0, 5));
		return listStudies;
	}

	@SuppressWarnings("unchecked")
	public List<Study> findCellRest() {
		return entityManager.createQuery("FROM " + Study.class.getName() + " s where s.flShow = true and tpStudy = "
				+ TypeStudy.CELULA.getTipo() + "ORDER BY dtStudy DESC").getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Study> find12Rest() {
		return entityManager.createQuery("FROM " + Study.class.getName() + " s where s.flShow = true and tpStudy = "
				+ TypeStudy.DOZE.getTipo() + "ORDER BY dtStudy DESC").getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Study> findChildRest() {
		return entityManager.createQuery("FROM " + Study.class.getName() + " s where s.flShow = true and tpStudy = "
				+ TypeStudy.CRIANCA.getTipo() + "ORDER BY dtStudy DESC").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Study> findByDateTipo(Integer month, Integer year, TypeStudy tipo) {
		return entityManager.createQuery("FROM " + Study.class.getName()
				+ " e where year(e.dtStudy) = :y and month(e.dtStudy) = :m and e.tpStudy = :t order by e.dtStudy")
				.setParameter("y", year).setParameter("m", month).setParameter("t", tipo).getResultList();
	}

	private void setIdStudy(Study study) {
		try {
			String nextId = entityManager
					.createQuery("select max(s.idStudy)+1 FROM " + Study.class.getName() + " s where s.tpStudy = :t")
					.setParameter("t", study.getTpStudy()).getSingleResult().toString();
			study.setIdStudy(Long.valueOf(nextId));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			study.setIdStudy(1L);
		}
	}

	public void insert(Study study) {
		setIdStudy(study);
		if (!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		study.setDsTitle(study.getDsTitle().toUpperCase());
		entityManager.persist(study);
		entityManager.getTransaction().commit();
	}

	public void update(Study study) {
		if (!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		entityManager.merge(study);
		entityManager.getTransaction().commit();
	}

	public void remove(Study study) {
		if (!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		study = entityManager.find(Study.class, study.getIdStudy());
		entityManager.remove(entityManager.merge(study));
		entityManager.getTransaction().commit();
	}
}
