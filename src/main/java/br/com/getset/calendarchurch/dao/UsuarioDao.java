package br.com.getset.calendarchurch.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import br.com.getset.calendarchurch.exception.HotelNuvemException;
import br.com.getset.calendarchurch.model.Usuario;

@SuppressWarnings("serial")
public class UsuarioDao implements Serializable{
	@Inject
	private EntityManager entityManager;
	
	public Usuario getById(final Long id) {
		try{
			return entityManager.find(Usuario.class, id);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Usuario authenticLogin(String usuario, String senha) throws HotelNuvemException{
		TypedQuery<Usuario> query = entityManager.createQuery("SELECT u FROM Usuario u where u.dsLogin = :usuario",Usuario.class).setParameter("usuario", usuario);
		try{
			Usuario u = query.getSingleResult();
			MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(),0,senha.length());
            String passwordMd5 = new BigInteger(1,m.digest()).toString(16);
            if(passwordMd5.equals(u.getDsSenha()))
            	return u;
            else
            	return null;
		}catch(NoResultException e ){
			throw new HotelNuvemException("Usuário não encontrado !");
		}catch (NonUniqueResultException e) {
			throw new HotelNuvemException("Usuário inválido");
		}catch (Exception ex) {
			throw new HotelNuvemException("Erro genérico");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll(){
		return entityManager.createQuery("FROM "+Usuario.class.getName()).getResultList();
	}
	
	public void insert(Usuario usuario){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		//updateHoteisUsuario(usuario);
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();
	}
	
	public void update(Usuario usuario){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		//updateHoteisUsuario(usuario);
		entityManager.merge(usuario);
		entityManager.getTransaction().commit();
	}
	
	/*
	private void updateHoteisUsuario(Usuario usuario){
		String sqlDel = "delete from tb_usuario_hotel where cd_usuario = :codUser";
		String sqlIns = "insert into tb_usuario_hotel values(:codUser,:codHotel)";
		
		entityManager.createNativeQuery(sqlDel).setParameter("codUser", usuario.getCdUsuario());
		entityManager.getTransaction().commit();
		
		for(Event h : usuario.getListHoteis()){
			entityManager.createNativeQuery(sqlIns)
				.setParameter("codUser", usuario.getCdUsuario());
				//.setParameter("codHotel", h.getCdHotel());
		}
		entityManager.getTransaction().commit();
		
		
	}
	*/	
	public void remove(Usuario usuario){
		if(!entityManager.getTransaction().isActive())
			entityManager.getTransaction().begin();
		usuario = entityManager.find(Usuario.class, usuario.getCdUsuario());
		entityManager.remove(entityManager.merge(usuario));
		entityManager.getTransaction().commit();
	}
}
