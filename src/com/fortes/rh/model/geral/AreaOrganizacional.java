/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="areaorganizacional_sequence", allocationSize=1)
public class AreaOrganizacional extends AbstractModel implements Serializable, Cloneable
{
	public static Boolean TODAS = null;
	public static Boolean ATIVA = true;
	public static Boolean INATIVA = false;

	@Column(length=60)
	private String nome;
	@Transient
	private String descricao;
	@ManyToOne
	private AreaOrganizacional areaMae;
	@ManyToMany(mappedBy = "areasOrganizacionais")
	private Collection<AreaInteresse> areasInteresse;
	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "areaOrganizacionals")
	private Collection<Conhecimento> conhecimentos;
	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "areaOrganizacionals")
	private Collection<Habilidade> habilidades;
	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "areaOrganizacionals")
	private Collection<Atitude> atitudes;
	@Column(length=12)
	private String codigoAC;
	@ManyToOne
	private Colaborador responsavel;
	@ManyToOne
	private Empresa empresa;
	private boolean ativo = ATIVA;
	
	@Transient
	private Integer colaboradorCount;
	
	public AreaOrganizacional(String nome){
		this.nome = nome;
	}

	public AreaOrganizacional()
	{

	}

	public AreaOrganizacional(Long id, String nome, boolean ativo)
	{
		this.setId(id);
		this.nome = nome;
		this.ativo = ativo;
	}
	
	public AreaOrganizacional(Long id, String nome, Integer count)
	{
		this.setId(id);
		this.nome = nome;
		this.colaboradorCount = count;
	}


	public Colaborador getResponsavel()
	{
		return responsavel;
	}

	public void setResponsavel(Colaborador responsavel)
	{
		this.responsavel = responsavel;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public Collection<AreaInteresse> getAreasInteresse()
	{
		return areasInteresse;
	}

	public void setAreasInteresse(Collection<AreaInteresse> areasInteresse)
	{
		this.areasInteresse = areasInteresse;
	}

	public String getDescricao()
	{
		if (getAreaMae() == null || getAreaMae().getDescricao() == null)
			descricao = nome;
		else
			descricao = getAreaMae().getDescricao() + " > " + nome;

		return descricao;
	}
	
	@NaoAudita
	public String getDescricaoComEmpresa()
	{
		if (getAreaMae() == null || getAreaMae().getId() == null || getAreaMae().getDescricaoComEmpresa() == null)
			descricao = empresa.getNome() + " - " + nome;
		else
			descricao = getAreaMae().getDescricaoComEmpresa() + " > " + nome;

		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public AreaOrganizacional getAreaMae()
	{
		return areaMae;
	}

	public void setAreaMae(AreaOrganizacional areaMae)
	{
		this.areaMae = areaMae;
	}

	public Collection<Conhecimento> getConhecimentos()
	{
		return conhecimentos;
	}

	public void setConhecimentos(Collection<Conhecimento> conhecimentos)
	{
		this.conhecimentos = conhecimentos;
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC)
	{
		this.codigoAC = codigoAC;
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id",this.getId());
		string.append("nome", this.nome);
		string.append("descricao", this.descricao);
		string.append("areaMae", this.areaMae);
		string.append("codigoAC", this.codigoAC);
		string.append("responsavel", this.responsavel);
		string.append("empresa", this.empresa);

		return string.toString();
	}

	public void setNomeAreaMae(String nomeMae)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		areaMae.setNome(nomeMae);
	}

	public void setIdAreaMae(Long idMae)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		areaMae.setId(idMae);
	}

	public void setNomeResponsavel(String nomeResponsavel)
	{
		if(responsavel == null)
			responsavel = new Colaborador();

		responsavel.setNomeComercial(nomeResponsavel);
	}

	public void setIdResponsavel(Long idResponsavel)
	{
		if(responsavel == null)
			responsavel = new Colaborador();

		responsavel.setId(idResponsavel);
	}
	
	public void setResponsavelNome(String nome) {
		if(responsavel == null) responsavel = new Colaborador();
		responsavel.setNome(nome);
	}
	
	public String getResponsavelNome() {
		if(responsavel == null) responsavel = new Colaborador();
		return responsavel.getNome();
	}

	public void setAreaMaeId(Long areaMaeId)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		areaMae.setId(areaMaeId);
	}
	public Long getAreaMaeId()
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		return areaMae.getId();
	}
	public String getAreaMaeNome()
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		return areaMae.getNome();
	}

	public void setAreaMaeNome(String areaMaeNome)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		areaMae.setNome(areaMaeNome);
	}

	public void setEmpresaId(Long empresaId)
	{
		if(empresa == null)
			empresa = new Empresa();

		empresa.setId(empresaId);
	}
	public void setEmpresaNome(String empresaNome)
	{
		if(empresa == null)
			empresa = new Empresa();
		
		empresa.setNome(empresaNome);
	}
	
	public void setEmpresaAreaMaeId(Long empresaId)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();
		if(areaMae.getEmpresa() == null)
			areaMae.setEmpresa(new Empresa());
		
		areaMae.getEmpresa().setId(empresaId);
	}
	public void setEmpresaAreaMaeNome(String empresaNome)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();
		if(areaMae.getEmpresa() == null)
			areaMae.setEmpresa(new Empresa());
		
		areaMae.getEmpresa().setNome(empresaNome);
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		try
		{
			return super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			throw new Error("This should not occur since we implement Cloneable");
		}
	}

	public boolean isAtivo()
	{
		return ativo;
	}

	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}

	/**
	 * atributo Transiente.
	 */
	public Integer getColaboradorCount() {
		return colaboradorCount;
	}

	public void setColaboradorCount(Integer colaboradorCount) {
		this.colaboradorCount = colaboradorCount;
	}

	public Collection<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(Collection<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Collection<Atitude> getAtitudes() {
		return atitudes;
	}

	public void setAtitudes(Collection<Atitude> atitudes) {
		this.atitudes = atitudes;
	}
}
