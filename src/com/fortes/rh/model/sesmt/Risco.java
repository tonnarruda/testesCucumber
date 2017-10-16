package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.dicionario.GrupoRisco;
import com.fortes.rh.model.dicionario.GrupoRiscoESocial;
import com.fortes.rh.model.geral.Empresa;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="risco_sequence", allocationSize=1)
public class Risco extends AbstractModel implements Serializable
{
	@Column(length=100)
	private String descricao;
	
	@Column(length=5)
	private String grupoRisco;
	
	@Column(length=5)
	private String grupoRiscoESocial;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Epi> epis;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Empresa empresa;
	
	@OneToMany(mappedBy="risco")
	private Collection<RiscoMedicaoRisco> riscoMedicaoRiscos;
	
	@ManyToOne
	private FatorDeRisco fatorDeRisco;
	
	public Risco() {	}
	
	public Risco(Long id, String descricao)
	{
		setId(id);
		this.descricao = descricao;
	}
	
	public Risco(Long id, String descricao, String grupoRisco)
	{
		setId(id);
		this.descricao = descricao;
		this.grupoRisco = grupoRisco;
	}
	
	public Boolean getEpiEficaz()
	{
		return epis != null && !epis.isEmpty();
	}
	
	public String getDescricaoGrupoRisco()
	{
		return (String) GrupoRisco.getInstance().get(grupoRisco);
	}
	
	public String getDescricaoGrupoRiscoESocial()
	{
		return (String) GrupoRiscoESocial.getInstance().get(grupoRiscoESocial);
	}
	
	public Collection<Epi> getEpis()
	{
		return epis;
	}
	public void setEpis(Collection<Epi> epis)
	{
		this.epis = epis;
	}
	public String getDescricao()
	{
		return descricao;
	}
	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	public String getGrupoRisco()
	{
		return grupoRisco;
	}
	public void setGrupoRisco(String grupoRisco)
	{
		this.grupoRisco = grupoRisco;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public String toString()
	{
		return (getId() == null)? "null": getId().toString();
	}
	
	public void setEmpresaId(Long empresaId)
	{
		if (empresa == null)
			empresa = new Empresa();
		
		empresa.setId(empresaId);
	}

	public Collection<RiscoMedicaoRisco> getRiscoMedicaoRiscos() {
		return riscoMedicaoRiscos;
	}

	public void setRiscoMedicaoRiscos(
			Collection<RiscoMedicaoRisco> riscoMedicaoRiscos) {
		this.riscoMedicaoRiscos = riscoMedicaoRiscos;
	}

	public String getGrupoRiscoESocial() {
		return grupoRiscoESocial;
	}

	public void setGrupoRiscoESocial(String grupoRiscoESocial) {
		this.grupoRiscoESocial = grupoRiscoESocial;
	}

	public FatorDeRisco getFatorDeRisco() {
		return fatorDeRisco;
	}

	public void setFatorDeRisco(FatorDeRisco fatorDeRisco) {
		this.fatorDeRisco = fatorDeRisco;
	}
	
	public void setFatorDeRiscoCodigo(String fatorDeRiscoCodigo){
		inicializaFatorDeRisco();
		this.fatorDeRisco.setCodigo(fatorDeRiscoCodigo);
	}
	
	public void setFatorDeRiscoDescricao(String fatorDeRiscoDescricao){
		inicializaFatorDeRisco();
		this.fatorDeRisco.setDescricao(fatorDeRiscoDescricao);
	}
	
	private void inicializaFatorDeRisco(){
		if(this.fatorDeRisco == null)
			this.fatorDeRisco = new FatorDeRisco();
	}
}