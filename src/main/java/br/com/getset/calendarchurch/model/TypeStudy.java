package br.com.getset.calendarchurch.model;

public enum TypeStudy {
	DOZE(0,"12"),
	CELULA(1,"Célula");
	
	private int tipo;
	private String desc;
	
	private TypeStudy(int tipo, String desc){
		this.tipo = tipo;
		this.desc = desc;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
