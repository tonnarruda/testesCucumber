package com.fortes.rh.model.avaliacao.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.DateUtil;

public class AcompanhamentoExperienciaColaborador implements Comparable<AcompanhamentoExperienciaColaborador> {
	private String matricula;
	private String nome;
	private String cargoFaixa;
	private Date dataAdmissao;
	private AreaOrganizacional areaOrganizacional;
	private List<String> periodoExperiencias = new ArrayList<String>();

	public AcompanhamentoExperienciaColaborador(String matricula, String nome, String cargoFaixa, AreaOrganizacional areaOrganizacional, Date dataAdmissao)
	{
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.cargoFaixa = cargoFaixa;
		this.areaOrganizacional = areaOrganizacional;
		this.dataAdmissao = dataAdmissao;
	}

	public AcompanhamentoExperienciaColaborador() {
	
	}

	public void addPeriodo(Date respondidaEm, String performance, String dataPeridoExperienciaPrevista) 
	{
		if(respondidaEm != null)
			this.periodoExperiencias.add(DateUtil.formataDiaMesAno(respondidaEm) + " (" + performance + ")");
		else
			this.periodoExperiencias.add("Previsto: " + dataPeridoExperienciaPrevista);
	}

	public Long getAreaOrganizacionalId() {
		if(areaOrganizacional != null && areaOrganizacional.getId() != null)
			return areaOrganizacional.getId();
		else
			return null;
	}
	
	public String getPeriodoColunaUm()
	{
		return getPeriodoColuna(0);
	}
	public String getPeriodoColunaDois()
	{
		return getPeriodoColuna(1);
	}
	public String getPeriodoColunaTres()
	{
		return getPeriodoColuna(2);
	}
	public String getPeriodoColunaQuatro()
	{
		return getPeriodoColuna(3);
	}
	
	private String getPeriodoColuna(int coluna) 
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

	public List<String> getPeriodoExperiencias() {
		return periodoExperiencias;
	}

	public int compareTo(AcompanhamentoExperienciaColaborador acomp) {
		return (areaOrganizacional.getDescricao() + " " + nome).compareTo(acomp.getAreaOrganizacional().getDescricao() + " " + acomp.getNome());
	}
}
