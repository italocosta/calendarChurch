package br.com.getset.calendarchurch.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.getset.calendarchurch.dao.StudyDao;
import br.com.getset.calendarchurch.model.Study;
import br.com.getset.calendarchurch.model.ContentStudy;
import br.com.getset.calendarchurch.model.GroupStudies;

@SuppressWarnings("serial")
public class StudyService implements Serializable{
	@Inject
	private StudyDao sDao;
	private Study study;
	
	public GroupStudies getAllStudies(){
		GroupStudies gp = new GroupStudies(sDao.findAllRest());
		return gp;
	}
	public ContentStudy getStudyContent(Long id){
		return new ContentStudy(sDao.getContent(id));
	}
	
	public StudyDao getsDao() {
		return sDao;
	}
	public void setsDao(StudyDao sDao) {
		this.sDao = sDao;
	}
	public Study getStudy() {
		return study;
	}
	public void setStudy(Study study) {
		this.study = study;
	}
	
	
	
	
	
	
	
}
