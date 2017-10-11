package br.com.getset.calendarchurch.util;

import java.io.Serializable;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.getset.calendarchurch.model.Usuario;

@SuppressWarnings("serial")
public class FacesUtils implements Serializable{
	
	@Inject
	private FacesContext facesContext;

	public FacesContext getFacesContext() {
		return facesContext;
	}
	
	public Usuario getUsuarioSession(){
		return ((Usuario)((HttpSession)getFacesContext()
				.getExternalContext().getSession(false)).getAttribute("usuario"));
	}
	public void setUsuarioSession(Usuario u){
		((HttpSession)getFacesContext()
				.getExternalContext().getSession(false)).setAttribute("usuario",u);
	}

	public void adicionaMensagemDeErro(String titulo,String detalhes) {
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, titulo, detalhes));
	}

	public void adicionaMensagemDeInformacao(String titulo,String detalhes) {
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, titulo, detalhes));
	}

	public boolean possuiMensagem(String msg) {
		Iterator<FacesMessage> messages = facesContext.getMessages();
		while (messages.hasNext()) {
			FacesMessage message = messages.next();
			boolean confere = message.getDetail().equals(msg);
			if (confere)
				return true;
		}
		return false;
	}
	
	/**
	 * Limpa os dados dos componentes de ediÃ§Ã£o e de seus filhos,
	 * recursivamente. Checa se o componente Ã© instÃ¢ncia de EditableValueHolder
	 * e 'reseta' suas propriedades.
	 * <p>
	 * Quando este mÃ©todo, por algum motivo, nÃ£o funcionar, parta para ignorÃ¢ncia
	 * e limpe o componente assim:
	 * <p><blockquote><pre>
	 * 	component.getChildren().clear()
	 * </pre></blockquote>
	 * :-)
	 */
	public void cleanSubmittedValues(UIComponent component) {
		if (component instanceof EditableValueHolder) {
			EditableValueHolder evh = (EditableValueHolder) component;
			evh.setSubmittedValue(null);
			evh.setValue(null);
			evh.setLocalValueSet(false);
			evh.setValid(true);
		}
		if(component.getChildCount()>0){
			for (UIComponent child : component.getChildren()) {
				cleanSubmittedValues(child);
			}
		}
	}
	
	public void cleanSubmittedValues(String componentName,FacesContext facesContext) {
		UIComponent component = facesContext.getViewRoot().findComponent(componentName);
		if (component != null)
			cleanSubmittedValues(component);
	}
}
