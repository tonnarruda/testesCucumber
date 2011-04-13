package com.fortes.rh.model.relatorio;

public class DataGrafico {
	
	private String label;
	private double data;
	
	public DataGrafico(String label, double data) {
		super();
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
	

}
