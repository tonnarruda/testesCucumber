package com.fortes.rh.model.avaliacao.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fortes.rh.model.geral.AreaOrganizacional;

public class AcompanhamentoExperienciaColaborador {
	private String matricula;
	private String nome;
	private String cargoFaixa;
	private AreaOrganizacional areaOrganizacional;
	private Date dataAdmissao;
	private List<Date> periodoExperiencias = new ArrayList<Date>();

	public AcompanhamentoExperienciaColaborador(String matricula, String nome, String cargoFaixa, AreaOrganizacional areaOrganizacional, Date dataAdmissao)
	{
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.cargoFaixa = cargoFaixa;
		this.areaOrganizacional = areaOrganizacional;
		this.dataAdmissao = dataAdmissao;
	}

	public void addPeriodo(Date respondidaEm) 
	{
		this.periodoExperiencias.add(respondidaEm);
	}

	public Long getAreaOrganizacionalId() {
		if(areaOrganizacional != null && areaOrganizacional.getId() != null)
			return areaOrganizacional.getId();
		else
			return null;
	}
	
	public Date getPeriodoColunaUm()
	{
		return getPeriodoColuna(0);
	}
	public Date getPeriodoColunaDois()
	{
		return getPeriodoColuna(1);
	}
	public Date getPeriodoColunaTres()
	{
		return getPeriodoColuna(2);
	}
	public Date getPeriodoColunaQuatro()
	{
		return getPeriodoColuna(3);
	}
	
	private Date getPeriodoColuna(int coluna) 
	{
		try {return periodoExperiencias.get(coluna);}
		catch (Exception e){return null;}
	} 

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional) {
		this.areaOrganizacional = areaOrganizacional;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getNome() {
		return nome;
	}

	public String getCargoFaixa() {
		return cargoFaixa;
	}

	public AreaOrganizacional getAreaOrganizacional() {
		return areaOrganizacional;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public List<Date> getPeriodoExperiencias() {
		return periodoExperiencias;
	}
}
