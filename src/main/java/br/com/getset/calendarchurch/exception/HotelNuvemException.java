package br.com.getset.calendarchurch.exception;

import java.io.Serializable;

@SuppressWarnings("serial")
public class HotelNuvemException extends Exception implements Serializable{

	private String mensagem;

	public HotelNuvemException(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
