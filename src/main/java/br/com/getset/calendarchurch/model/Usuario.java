package br.com.getset.calendarchurch.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class Usuario implements Serializable{
	
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cdUsuario;
	
	@Column(name = "ds_name")
	private String dsNome;
	
	@Column(name = "ds_login")
	private String dsLogin;
	
	@Column(name = "ds_password")
	private String dsSenha;
	
	@Column(name = "tp_user")
	private String tpUsuario;
	
	@Transient
	private String nomeExibicao;
	
	/*
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_usuario_hotel", 
    	joinColumns = @JoinColumn(name = "cd_usuario"), 
    	inverseJoinColumns = @JoinColumn(name = "cd_hotel"))
	private List<Event> listHoteis = new ArrayList<Event>();
	*/

	public String getNomeExibicao() {
		String nomes[] = getDsNome().split(" ");
		return nomes[0]+" "+nomes[nomes.length-1];
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}

	public Long getCdUsuario() {
		return cdUsuario;
	}

	public void setCdUsuario(Long cdUsuario) {
		this.cdUsuario = cdUsuario;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsLogin() {
		return dsLogin;
	}

	public void setDsLogin(String dsLogin) {
		this.dsLogin = dsLogin;
	}

	public String getDsSenha() {
		return dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public String getTpUsuario() {
		return tpUsuario;
	}

	public void setTpUsuario(String tpUsuario) {
		this.tpUsuario = tpUsuario;
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdUsuario == null) ? 0 : cdUsuario.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Usuario other = (Usuario) obj;
		return this.cdUsuario.compareTo(other.cdUsuario) == 0;
	}
	
	@Override
	public String toString() {
		return cdUsuario.toString();
	}
	
	
}
