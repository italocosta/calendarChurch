package br.com.getset.calendarchurch.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ContentStudy {
	
	public ContentStudy(String content) {
		this.content = content;
	}

	@XmlElement(name = "content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
