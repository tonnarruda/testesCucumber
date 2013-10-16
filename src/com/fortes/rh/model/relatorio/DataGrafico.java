package com.fortes.rh.model.relatorio;

public class DataGrafico {
	
	private Long id;
	private String label;
	private double data;
	private String descricao;
	
	public DataGrafico(String label, double data) {
		super();
		this.label = label;
		this.data = data;
	}
	
	public DataGrafico(Long id, String label, double data, String descricao) {
		super();
		this.id = id;
		this.label = label;
		this.data = data;
		this.descricao = descricao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}
