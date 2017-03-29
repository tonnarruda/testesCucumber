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
	private String avaliadorNome;
	private String avaliacaoTitulo;
	private String diasDescricao;
	private Date dataAdmissao;
	private AreaOrganizacional areaOrganizacional;
	private String periodoExperiencia;
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
		else if(dataPeridoExperienciaPrevista!= null){
			this.periodoExperiencias.add("Previsto: " + dataPeridoExperienciaPrevista);
		}else
			this.periodoExperiencias.add(null);
	}
	
	public void addPeriodo(Date respondidaEm, String performance, String dataPeridoExperienciaPrevista, String avaliadorNome, String avaliacaoTitulo, String diasDescricao) 
	{
		this.periodoExperiencia = "";
		if(respondidaEm != null){
			this.periodoExperiencia = DateUtil.formataDiaMesAno(respondidaEm) + " (" + performance + ")";
			this.periodoExperiencias.add(this.periodoExperiencia);
		}else if(dataPeridoExperienciaPrevista!= null){
			this.periodoExperiencia = "Previsto: " + dataPeridoExperienciaPrevista;
			this.periodoExperiencias.add(this.periodoExperiencia);
		}else
			this.periodoExperiencias.add(null);
		
		this.avaliacaoTitulo = avaliacaoTitulo;
		this.avaliadorNome = avaliadorNome;	
		this.diasDescricao = diasDescricao;
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
		try {
			return periodoExperiencias.get(coluna);
		} catch (Exception e) {
			return null;
		}
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
	
	public String getDataAdmissaoString() {
		return DateUtil.formataDiaMesAno(dataAdmissao);
	}

	public List<String> getPeriodoExperiencias() {
		return periodoExperiencias;
	}

	public int compareTo(AcompanhamentoExperienciaColaborador acomp) {
		return (areaOrganizacional.getDescricao() + " " + nome).compareTo(acomp.getAreaOrganizacional().getDescricao() + " " + acomp.getNome());
	}

	public String getAvaliadorNome() {
		return avaliadorNome;
	}

	public void setAvaliadorNome(String avaliadorNome) {
		this.avaliadorNome = avaliadorNome;
	}

	public String getAvaliacaoTitulo() {
		return avaliacaoTitulo;
	}

	public void setAvaliacaoTitulo(String avaliacaoTitulo) {
		this.avaliacaoTitulo = avaliacaoTitulo;
	}

	public String getPeriodoExperiencia() {
		return periodoExperiencia;
	}

	public String getDiasDescricao() {
		return diasDescricao;
	}
}
