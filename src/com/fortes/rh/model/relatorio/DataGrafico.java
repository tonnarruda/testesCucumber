package com.fortes.rh.model.relatorio;

public class DataGrafico {
	
	private Long id;
	private String label;
	private double data;
	
	public DataGrafico(Long id, String label, double data) {
		super();
		this.id = id;
		this.label = label;
		this.data = data;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public double getData() {
		return data;
	}

	public void setData(double data) {
		this.data = data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
