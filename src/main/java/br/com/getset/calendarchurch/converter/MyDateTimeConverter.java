package br.com.getset.calendarchurch.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter("myDateTimeConverter")
public class MyDateTimeConverter extends DateTimeConverter {
	
	public MyDateTimeConverter() {
		setPattern("dd/MM/yyyy");
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if((value == null || value.isEmpty()) || (value.length() != getPattern().length())){
			throw new ConverterException("Data inválida");
		}
		DateFormat df = new SimpleDateFormat(getPattern());
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(value));
		} catch (ParseException e) {
			throw new ConverterException("Erro na conversão para Calendar.");
		}
		return c;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if(!(obj instanceof Calendar)){
			throw new ConverterException("Objeto de data não tem instancia de java.util.Calendar");
		}
		DateFormat df = new SimpleDateFormat(getPattern());
		return df.format(((Calendar)obj).getTime());
		
	}
}
