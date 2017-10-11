package br.com.getset.calendarchurch.managedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.getset.calendarchurch.dao.StudyDao;
import br.com.getset.calendarchurch.model.Event;
import br.com.getset.calendarchurch.model.Study;
import br.com.getset.calendarchurch.model.TypeStudy;
import br.com.getset.calendarchurch.service.PushNotificationService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class StudyMB implements Serializable{
	@Inject
	private StudyDao sDao;
	@Inject
	private PushNotificationService pnService;
	private Study studySelect;
	private Study newStudy;
	private Integer month;
	private Integer year;
	private TypeStudy tipo;
	private List<Study> listStudy;
	private boolean sendNotification;
	
	@PostConstruct
	protected void init(){
		studySelect = new Study();	
		Calendar c = Calendar.getInstance();
		month = c.get(Calendar.MONTH)+1;
		year = c.get(Calendar.YEAR);
		tipo = TypeStudy.CELULA;
		sendNotification = true;
		searchStudys();
	}
	
	
	public void preparNewStudy(){
		newStudy = new Study();
		newStudy.setDtStudy(Calendar.getInstance());
	}
	
	public void updateStudy(){
		sDao.update(studySelect);
		sendNotification = true;
		searchStudys();
	}
	
	public void deleteStudy(){
		sDao.remove(studySelect);
		listStudy.remove(studySelect);
	}
	
	public void insertStudy(){
		sDao.insert(newStudy);
		if(newStudy.isFlShow())
			sendNotificationStudyInsert(newStudy);
		if(newStudy.getDtStudy().get(Calendar.MONTH)+1 == this.month &&
				newStudy.getDtStudy().get(Calendar.YEAR) == this.year
				&& newStudy.getTpStudy().equals(tipo)){
			listStudy.add(newStudy);
		}
	}
	
	public void searchStudys(){
		listStudy = sDao.findByDateTipo(month, year,tipo);
	}
	
	public List<TypeStudy> listTypes(){
		List<TypeStudy> tipos = 
				new ArrayList<TypeStudy>(Arrays.asList(TypeStudy.values()));
		return tipos;
	}
	
	public void sendNotificationStudyInsert(Study std){
		try {
			pnService.sendNotificationBasic("Novo estudo de "+std.getTpStudy().getDesc()+" publicado",std);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public StudyDao geteDao() {
		return sDao;
	}


	public void seteDao(StudyDao eDao) {
		this.sDao = eDao;
	}


	public Study getStudySelect() {
		return studySelect;
	}


	public void setStudySelect(Study studySelect) {
		this.studySelect = studySelect;
	}


	public Study getNewStudy() {
		return newStudy;
	}


	public void setNewStudy(Study newStudy) {
		this.newStudy = newStudy;
	}


	public List<Study> getListStudy() {
		return listStudy;
	}


	public void setListStudy(List<Study> listStudy) {
		this.listStudy = listStudy;
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


	public TypeStudy getTipo() {
		return tipo;
	}


	public void setTipo(TypeStudy tipo) {
		this.tipo = tipo;
	}
	/*
	 * public void setTipo(int tipoInt) {
		this.tipo = TypeStudy.values()[tipoInt];
	}
	 */
	
	
}
