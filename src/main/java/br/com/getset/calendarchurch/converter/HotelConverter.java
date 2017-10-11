package br.com.getset.calendarchurch.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.getset.calendarchurch.dao.EventDao;
import br.com.getset.calendarchurch.managedBean.EventMB;
import br.com.getset.calendarchurch.model.Event;

@FacesConverter("hotelConverter")
public class HotelConverter implements Converter{

	private EventDao eDao;

	public HotelConverter() {
		super();
		FacesContext context = FacesContext.getCurrentInstance();
		EventMB eventMB = (EventMB) context.getELContext().getELResolver()
				.getValue(context.getELContext(), null, "eventMB");
		eDao = eventMB.geteDao();
	}

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
		if (id == null || id.isEmpty())
			throw new ConverterException(
					"Erro na conversão para Objeto hotel. Id nulo.");
		return eDao.getById(Long.parseLong(id));
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object obj) {
		if (obj == null || !(obj instanceof Event)) {
			throw new ConverterException(
					"Erro na converão. Objeto hotel inválido ou nulo.");
		}

		return ((Event) obj).toString();
	}

}
