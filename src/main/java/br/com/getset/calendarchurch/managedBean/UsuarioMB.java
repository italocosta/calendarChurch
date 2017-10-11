package br.com.getset.calendarchurch.managedBean;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.getset.calendarchurch.dao.UsuarioDao;
import br.com.getset.calendarchurch.model.Usuario;
import br.com.getset.calendarchurch.util.FacesUtils;

@SuppressWarnings("serial")

@ViewScoped
public class UsuarioMB implements Serializable{
	@Inject
	private UsuarioDao uDao;
	@Inject
	private FacesUtils facesUtils;
	private Usuario usuarioSelect;
	private Usuario newUsuario;
	private List<Usuario> listUsuario;
	private String senha;
	
	@PostConstruct
	protected void init(){
		usuarioSelect = new Usuario();
		listUsuario = new ArrayList<Usuario>();
	}
	
	public void preparaNovoUsuario(){
		newUsuario = new Usuario();
	}
	
	public void inseriUsuario(){
		if(senha != null && !senha.trim().isEmpty()){
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(senha.getBytes(),0,senha.length());
				String passwordMd5 = new BigInteger(1,m.digest()).toString(16);
				newUsuario.setDsSenha(passwordMd5);
				uDao.insert(newUsuario);
				atualizaLista();
			} catch (NoSuchAlgorithmException e) {
				facesUtils.adicionaMensagemDeErro("Não foi possível alterar senha", "Erro no momento de encriptar");
			}
		}
	}
	
	public void atualizaUsuario(){
		if(senha != null && !senha.trim().isEmpty()){
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(senha.getBytes(),0,senha.length());
				String passwordMd5 = new BigInteger(1,m.digest()).toString(16);
				usuarioSelect.setDsSenha(passwordMd5);
				uDao.update(usuarioSelect);
				atualizaLista();
			} catch (NoSuchAlgorithmException e) {
				facesUtils.adicionaMensagemDeErro("Não foi possível alterar senha", "Erro no momento de encriptar");
			}
		}
	}
	
	public void excluiUsuario(){
		uDao.remove(usuarioSelect);
		atualizaLista();
	}
	

	private void atualizaLista(){
		listUsuario = uDao.findAll();
	}
	
	public List<Usuario> getListUsuario() {
		if(listUsuario == null || listUsuario.size()==0)
			listUsuario = uDao.findAll();
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public UsuarioDao getuDao() {
		return uDao;
	}

	public void setuDao(UsuarioDao uDao) {
		this.uDao = uDao;
	}

	public Usuario getUsuarioSelect() {
		return usuarioSelect;
	}

	public void setUsuarioSelect(Usuario usuarioSelect) {
		this.usuarioSelect = usuarioSelect;
	}

	public Usuario getNewUsuario() {
		return newUsuario;
	}

	public void setNewUsuario(Usuario newUsuario) {
		this.newUsuario = newUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
	
}
