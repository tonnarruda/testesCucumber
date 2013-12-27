package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fortes.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="pcmat_sequence", allocationSize=1)
public class Pcmat extends AbstractModel implements Serializable, Cloneable
{
	@Temporal(TemporalType.DATE)
	private Date aPartirDe;
	@Temporal(TemporalType.DATE)
	private Date dataIniObra;
	@Temporal(TemporalType.DATE)
	private Date dataFimObra;
	@ManyToOne
	private Obra obra;
	
	private Integer qtdFuncionarios;
	private String objetivo;
	
	private String textoCondicoesTrabalho;
	private String textoAreasVivencia;
	private String textoAtividadesSeguranca;
	private String textoEpis;
	private String textoEpcs;
	private String textoSinalizacao;
	
	@OneToMany(mappedBy="pcmat")
	private Collection<FasePcmat> fasesPcmat;
	
	@Override
	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o PCMAT.");
	   }
	}
	
	public void setProjectionIdObra(Long idObra){
		inicializaObra();
		obra.setId(idObra);
	}
	
	public void setProjectionNomeObra(String nomeObra){
		inicializaObra();
		obra.setNome(nomeObra);
	}
	
	private void inicializaObra() {
		if(obra == null)
			obra = new Obra();
	}
	
	public Date getAPartirDe() {
		return aPartirDe;
	}
	
	public void setAPartirDe(Date aPartirDe) {
		this.aPartirDe = aPartirDe;
	}
	
	public Date getDataIniObra() {
		return dataIniObra;
	}
	
	public void setDataIniObra(Date dataIniObra) {
		this.dataIniObra = dataIniObra;
	}
	
	public Date getDataFimObra() {
		return dataFimObra;
	}
	
	public void setDataFimObra(Date dataFimObra) {
		this.dataFimObra = dataFimObra;
	}
	
	public Obra getObra() {
		return obra;
	}
	
	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public Collection<FasePcmat> getFasesPcmat() {
		return fasesPcmat;
	}

	public void setFasesPcmat(Collection<FasePcmat> fasesPcmat) {
		this.fasesPcmat = fasesPcmat;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public Integer getQtdFuncionarios() {
		return qtdFuncionarios;
	}

	public void setQtdFuncionarios(Integer qtdFuncionarios) {
		this.qtdFuncionarios = qtdFuncionarios;
	}

	public String getTextoCondicoesTrabalho() {
		return textoCondicoesTrabalho;
	}

	public void setTextoCondicoesTrabalho(String textoCondicoesTrabalho) {
		this.textoCondicoesTrabalho = textoCondicoesTrabalho;
	}

	public String getTextoAreasVivencia() {
		return textoAreasVivencia;
	}

	public void setTextoAreasVivencia(String textoAreasVivencia) {
		this.textoAreasVivencia = textoAreasVivencia;
	}

	public String getTextoAtividadesSeguranca() {
		return textoAtividadesSeguranca;
	}

	public void setTextoAtividadesSeguranca(String textoAtividadesSeguranca) {
		this.textoAtividadesSeguranca = textoAtividadesSeguranca;
	}

	public String getTextoEpis() {
		return textoEpis;
	}

	public void setTextoEpis(String textoEpis) {
		this.textoEpis = textoEpis;
	}

	public String getTextoEpcs() {
		return textoEpcs;
	}

	public void setTextoEpcs(String textoEpcs) {
		this.textoEpcs = textoEpcs;
	}

	public String getTextoSinalizacao() {
		return textoSinalizacao;
	}

	public void setTextoSinalizacao(String textoSinalizacao) {
		this.textoSinalizacao = textoSinalizacao;
	}
}