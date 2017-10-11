package br.com.getset.calendarchurch.managedBean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.getset.calendarchurch.dao.UsuarioDao;
import br.com.getset.calendarchurch.exception.HotelNuvemException;
import br.com.getset.calendarchurch.model.Usuario;
import br.com.getset.calendarchurch.util.FacesUtils;

@SuppressWarnings("serial")
@Named
@RequestScoped
public class AuthenticMB implements Serializable{
	
	private Usuario usuario;
	@Inject
	private UsuarioDao uDao;
	@Inject
	private FacesUtils facesUtils;
	
	
	@PostConstruct
	private void init(){
		usuario = new Usuario();
	}
	

	public String login(){
		
		try {
			Usuario uRet = uDao.authenticLogin(usuario.getDsLogin(), usuario.getDsSenha());
			if(uRet != null){
				facesUtils.setUsuarioSession(uRet);
				usuario = uRet;
				return "sistema/index.jsf?faces-redirect=true";
			}else{
				facesUtils.adicionaMensagemDeErro("Erro de login","Senha inválida");
				return null;
			}
		} catch (HotelNuvemException e) {
			facesUtils.adicionaMensagemDeErro("Erro de login",e.getMensagem());
			return null;
		}
		
	}
	public void logout(){
		HttpSession sessao = (HttpSession) facesUtils.getFacesContext().getExternalContext().getSession(false);
		facesUtils.setUsuarioSession(null);
		sessao.invalidate();
		try {
			facesUtils.getFacesContext().getExternalContext().redirect(facesUtils.getFacesContext().getExternalContext().getRequestContextPath()
			        + "/login.jsf");
		} catch (IOException e) {
			facesUtils.adicionaMensagemDeErro("Erro ao deslogar", "Página de login não localizada");
		}
	}
	
	
	public Usuario getUsuarioSession(){
		return facesUtils.getUsuarioSession();
	}


	public Usuario getUsuario() {
		return this.usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	
	
}
