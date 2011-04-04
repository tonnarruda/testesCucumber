package com.fortes.rh.model.relatorio;

public class DataGrafico {
	
	private String label;
	private int data;
	
	public DataGrafico(String label, int data) {
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
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	

}
