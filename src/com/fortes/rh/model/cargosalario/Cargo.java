/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.cargosalario;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Competencia;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="cargo_sequence", allocationSize=1)
public class Cargo extends AbstractModel implements Serializable
{
	@Transient
	public static Boolean TODOS = null;
	@Transient
	public static Boolean ATIVO = true;
	@Transient
	public static Boolean INATIVO = false;

	@Column(length=100)
	private String nome;
	@Column(length=100)
	private String nomeMercado;
	@Lob
	private String missao;
	@Lob
	private String competencias;
	@Lob
	private String responsabilidades;
	@Column(length=5)
	private String escolaridade;
	@Lob
	private String experiencia;
	@Lob
	private String recrutamento;
	@Lob
	private String selecao;
	@Lob
	private String atitude;
	@Lob
	private String complementoConhecimento;
	@Lob
	private String observacao;
	@ManyToOne(fetch=FetchType.LAZY)
	private GrupoOcupacional grupoOcupacional;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<AreaFormacao> areaFormacaos;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<AreaOrganizacional> areasOrganizacionais;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Conhecimento> conhecimentos;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Habilidade> habilidades;
	@ManyToMany(fetch=FetchType.LAZY)
	private Collection<Atitude> atitudes;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cargo")
	private Collection<FaixaSalarial> faixaSalarials;
	@ManyToOne
	private Empresa empresa;
    @OneToMany(fetch=FetchType.LAZY)
    private Collection<EtapaSeletiva> etapaSeletivas;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cargo")
	private Collection<Funcao> funcaos;
	
	private boolean ativo = ATIVO;
	private Boolean exibirModuloExterno = false;
	
	@Transient
	private boolean possuiFaixaSalarial = false;
	
	public Cargo()
	{
	}

	public Cargo(Long id, String nome, boolean ativo)
	{
		this.setId(id);
		this.setNome(nome);
		this.ativo = ativo;
	}
	
	public Cargo(Long id, String nomeMercado, boolean ativo, Long empresaId, String empresaNome)
	{
		this.setId(id);
		this.setNomeMercado(nomeMercado);
		this.setEmpresaIdProjection(empresaId);
		this.setEmpresaNomeProjection(empresaNome);
		this.ativo = ativo;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public void setGrupoNome(String grupoNome)
	{
		if (grupoOcupacional == null)
			grupoOcupacional = new GrupoOcupacional();
		grupoOcupacional.setNome(grupoNome);
	}

	public void setGrupoOcupacionalIdProjection(Long grupoOcupacionalIdProjection)
	{
		if (this.grupoOcupacional == null)
			this.grupoOcupacional = new GrupoOcupacional();
		this.grupoOcupacional.setId(grupoOcupacionalIdProjection);
	}

	public void setEmpresaIdProjection(Long empresaIdProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setId(empresaIdProjection);
	}

	public void setEmpresaNomeProjection(String empresaNomeProjection)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setNome(empresaNomeProjection);
	}

	public Collection<Conhecimento> getConhecimentos()
	{
		return conhecimentos;
	}

	public void setConhecimentos(Collection<Conhecimento> conhecimentos)
	{
		this.conhecimentos = conhecimentos;
	}

	public Collection<AreaOrganizacional> getAreasOrganizacionais()
	{
		return areasOrganizacionais;
	}

	public void setAreasOrganizacionais(Collection<AreaOrganizacional> areasOrganizacionais)
	{
		this.areasOrganizacionais = areasOrganizacionais;
	}

	public String getCompetencias()
	{
		return competencias;
	}

	public void setCompetencias(String competencias)
	{
		this.competencias = competencias;
	}

	public String getEscolaridade()
	{
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade)
	{
		this.escolaridade = escolaridade;
	}

	public String getExperiencia()
	{
		return experiencia;
	}

	public void setExperiencia(String experiencia)
	{
		this.experiencia = experiencia;
	}

	public String getMissao()
	{
		return missao;
	}

	public void setMissao(String missao)
	{
		this.missao = missao;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getRecrutamento()
	{
		return recrutamento;
	}

	public void setRecrutamento(String recrutamento)
	{
		this.recrutamento = recrutamento;
	}

	public String getResponsabilidades()
	{
		return responsabilidades;
	}

	public void setResponsabilidades(String responsabilidades)
	{
		this.responsabilidades = responsabilidades;
	}

	public String getSelecao()
	{
		return selecao;
	}

	public void setSelecao(String selecao)
	{
		this.selecao = selecao;
	}

	public Collection<FaixaSalarial> getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public void setFaixaSalarials(Collection<FaixaSalarial> faixaSalarials)
	{
		this.faixaSalarials = faixaSalarials;
	}

	public GrupoOcupacional getGrupoOcupacional()
	{
		return grupoOcupacional;
	}

	public void setGrupoOcupacional(GrupoOcupacional grupoOcupacional)
	{
		this.grupoOcupacional = grupoOcupacional;
	}

	public Collection<AreaFormacao> getAreaFormacaos()
	{
		return areaFormacaos;
	}

	public void setAreaFormacaos(Collection<AreaFormacao> areaFormacaos)
	{
		this.areaFormacaos = areaFormacaos;
	}
	public void setNomeMercado(String nomeMercado)
	{
		this.nomeMercado = nomeMercado;
	}

	public String getNomeMercado()
	{
		return nomeMercado;
	}
	
	public String getNomeMercadoComStatus()
	{
		return nomeMercado + (ativo ? " (Ativo)" : " (Inativo)");
	}

	@NaoAudita
	public String getDescEscolaridade()
	{
		Escolaridade esc = new Escolaridade();

		return (String) esc.get(this.getEscolaridade());
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id",	this.getId());
		string.append("nome", this.nome);
		string.append("nomeMercado", this.getNomeMercado());
		string.append("grupoOcupacional", this.grupoOcupacional);
		string.append("escolaridade", this.escolaridade);
		string.append("missao", this.missao);
		string.append("responsabilidades", this.responsabilidades);
		string.append("competencias", this.competencias);
		string.append("experiencia", this.experiencia);
		string.append("selecao", this.selecao);
		string.append("recrutamento", this.recrutamento);
		string.append("observacao",	this.observacao);
		string.append("empresa", this.empresa);

		return string.toString();
	}

	@NaoAudita
	public String getDescAreaFormacao()
	{
		StringBuilder descricao = new StringBuilder();

		if(this.areaFormacaos != null)
			for (AreaFormacao areaFormacao : this.areaFormacaos)
			{
				descricao.append(areaFormacao.getNome() + "\n");
			}

		return descricao.toString();
	}
	
	@NaoAudita
	public String getDescEtapaSeletiva()
	{
		StringBuilder etapas = new StringBuilder();
		
		if(this.etapaSeletivas != null)
			for (EtapaSeletiva etapaSeletiva : this.etapaSeletivas)
			{
				etapas.append(etapaSeletiva.getNome() + "\n");
			}
		
		return etapas.toString();
	}
	
	@NaoAudita
	public String getNomeMercadoComEmpresa()
	{
		if(this.empresa != null && this.empresa.getNome() != null)
			return this.empresa.getNome() + " - " + this.nomeMercado;
		else
			return this.nomeMercado;
	}
	
	@NaoAudita
	public String getNomeMercadoComEmpresaEStatus()
	{
		if(this.empresa != null && this.empresa.getNome() != null)
			return this.empresa.getNome() + " - " + this.nomeMercado  + (ativo ? " (Ativo)" : " (Inativo)");
		else
			return this.nomeMercado  + (ativo ? " (Ativo)" : " (Inativo)");
	}

	@NaoAudita
	public String getDescAreaOrganizacioal()
	{
		StringBuilder descricao = new StringBuilder();

		if(this.areasOrganizacionais != null)
			for (AreaOrganizacional areasOrganizacional : this.areasOrganizacionais)
			{
				descricao.append(areasOrganizacional.getDescricao() + "\n");
			}

		return descricao.toString();
	}

	@NaoAudita
	public String getDescConhecimento()
	{
		StringBuilder descricao = new StringBuilder();

		if(this.conhecimentos != null)
			for (Conhecimento conhecimento : this.conhecimentos)
			{
				descricao.append(conhecimento.getNome() + "\n");
			}

		if (this.complementoConhecimento != null)
			descricao.append(this.complementoConhecimento);
		
		return descricao.toString();
	}

	@NaoAudita
	public String getDescHabilidade()
	{
		StringBuilder descricao = new StringBuilder();
		
		if(this.habilidades != null)
			for (Habilidade habilidade : this.habilidades)
				descricao.append(habilidade.getNome() + "\n");
		
		if (this.competencias != null)
			descricao.append(this.competencias);
		
		return descricao.toString();
	}
	
	@NaoAudita
	public String getDescAtitude()
	{
		StringBuilder descricao = new StringBuilder();
		
		if(this.atitudes != null)
			for (Atitude atitude : this.atitudes)
				descricao.append(atitude.getNome() + "\n");
		
		if (this.atitude != null)
			descricao.append(this.atitude);
		
		return descricao.toString();
	}

	@NaoAudita
	public String getDescFaixaSalarial()
	{
		StringBuilder descricao = new StringBuilder();

		if(this.faixaSalarials != null){
			for (FaixaSalarial faixaSalarial : this.faixaSalarials){
				descricao.append(faixaSalarial.getNome() + "\n");
				
				for (Competencia competencia : faixaSalarial.getCompetencias()){
					descricao.append("     "+competencia.getNome() +" : "+ competencia.getNivelCompetencia().getDescricao() + "\n");	
				}
			}
		}
		
		return descricao.toString();
	}

	public boolean isAtivo()
	{
		return ativo;
	}

	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}

	public Collection<Funcao> getFuncaos() {
		return funcaos;
	}

	public void setFuncaos(Collection<Funcao> funcaos) {
		this.funcaos = funcaos;
	}

	public String getAtitude() {
		return atitude;
	}

	public void setAtitude(String atitude) {
		this.atitude = atitude;
	}

	public Boolean getExibirModuloExterno() {
		return exibirModuloExterno;
	}

	public void setExibirModuloExterno(Boolean exibirModuloExterno) {
		
		if (exibirModuloExterno != null)
			this.exibirModuloExterno = exibirModuloExterno;
	}

	public Collection<EtapaSeletiva> getEtapaSeletivas() {
		return etapaSeletivas;
	}

	public void setEtapaSeletivas(Collection<EtapaSeletiva> etapaSeletivas) {
		this.etapaSeletivas = etapaSeletivas;
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

	public String getComplementoConhecimento() {
		return complementoConhecimento;
	}

	public void setComplementoConhecimento(String complementoConhecimento) {
		this.complementoConhecimento = complementoConhecimento;
	}

    public boolean isPossuiFaixaSalarial() {
        return possuiFaixaSalarial;
    }

    public void setPossuiFaixaSalarial(boolean possuiFaixaSalarial) {
        this.possuiFaixaSalarial = possuiFaixaSalarial;
    }
}