package br.com.getset.calendarchurch.managedBean;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.getset.calendarchurch.dao.UsuarioDao;
import br.com.getset.calendarchurch.model.Usuario;
import br.com.getset.calendarchurch.util.FacesUtils;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ProfileMB implements Serializable{
	@Inject
	private UsuarioDao uDao;
	@Inject
	private FacesUtils facesUtils;
	private Usuario usuarioLogado;
	private String estado;
	private final String VER = "_VER";
	private final String EDITAR = "_EDITAR";
	private String senhaConfim;
	
	
	@PostConstruct
	protected void init(){
		usuarioLogado = facesUtils.getUsuarioSession();
		estado = VER;
	}
	
	public void atualizaUsuarioLogado(){
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(usuarioLogado.getDsSenha().getBytes(),0,usuarioLogado.getDsSenha().length());
			String passwordMd5 = new BigInteger(1,m.digest()).toString(16);
			usuarioLogado.setDsSenha(passwordMd5);
			uDao.update(usuarioLogado);
			this.estado =VER;
		} catch (NoSuchAlgorithmException e) {
			facesUtils.adicionaMensagemDeErro("Não foi possível alterar senha", "Erro no momento de encriptar");
		}
	}
	
	public void editar(){
		this.estado = EDITAR;
	}
	
	public void sairEdicao(){
		this.estado = VER;
	}
	
	public UsuarioDao getuDao() {
		return uDao;
	}

	public void setuDao(UsuarioDao uDao) {
		this.uDao = uDao;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	public boolean isEditar(){
		return this.estado.equals(EDITAR);
	}
	public boolean isVer(){
		return this.estado.equals(VER);
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSenhaConfim() {
		return senhaConfim;
	}

	public void setSenhaConfim(String senhaConfim) {
		this.senhaConfim = senhaConfim;
	}

	
	
}
