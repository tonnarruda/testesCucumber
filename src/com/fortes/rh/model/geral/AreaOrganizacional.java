/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.captacao.Atitude;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.security.auditoria.ChaveDaAuditoria;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="areaorganizacional_sequence", allocationSize=1)
public class AreaOrganizacional extends AbstractModel implements Serializable, Cloneable
{
	@Transient
	public static Boolean TODAS = null;
	@Transient
	public static Boolean ATIVA = true;
	@Transient
	public static Boolean INATIVA = false;

	@Transient
	public static int RESPONSAVEL = 1;
	@Transient
	public static int CORRESPONSAVEL = 2;
	
	@Column(length=60)
	@ChaveDaAuditoria
	private String nome;
	@Transient
	private String descricao;
	
	@ManyToOne
	private AreaOrganizacional areaMae;
	@ManyToMany(mappedBy = "areasOrganizacionais")
	private Collection<AreaInteresse> areasInteresse;
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "areaOrganizacionals")
	private Collection<Conhecimento> conhecimentos;
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "areaOrganizacionals")
	private Collection<Habilidade> habilidades;
	@ManyToMany(fetch = FetchType.LAZY,mappedBy = "areaOrganizacionals")
	private Collection<Atitude> atitudes;
	@Column(length=12)
	private String codigoAC;
	@ManyToOne
	private Colaborador responsavel;
	@ManyToOne
	private Colaborador coResponsavel;
	@ManyToOne
	private Empresa empresa;
	private boolean ativo = ATIVA;
	@Column
	private String emailsNotificacoes;
	
	@Transient
	private Integer colaboradorCount;
	@Transient
	private int nivelHierarquico;
	@Transient
	private int qtdContratados;
	@Transient
	private String mascara;
	
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

	public String getDescricaoStatusAtivo()
	{
		return getDescricao() + (ativo ? " (Ativa)" : " (Inativa)");
	}
	
	public String getDescricao()
	{
		if (getAreaMae() == null || getAreaMae().getDescricao() == null)
			descricao = nome;
		else
			descricao = getAreaMae().getDescricao() + " > " + nome;

		return descricao;
	}
	
	public String getDescricaoComCodigoAC() {
		String codigoACComMascara;
		try {
			MaskFormatter mf = new MaskFormatter(mascara);
			mf.setValueContainsLiteralCharacters(false);
			codigoACComMascara = mf.valueToString(codigoAC) + " ";
		} catch (ParseException e) {
			codigoACComMascara = "";
		}
		return codigoACComMascara + getDescricao();
	}
	
	@NaoAudita
	public Collection<Long> getDescricaoIds()
	{
		Collection<Long> familiaIds = new ArrayList<Long>();
		if (getAreaMae() == null || getAreaMae().getId() == null)
			familiaIds.add(this.getId());
		else
		{
			familiaIds.addAll(getAreaMae().getDescricaoIds());
			familiaIds.add(this.getId());
		}
		
		return familiaIds;
	}
	
	@NaoAudita
	public String getDescricaoComEmpresaStatusAtivo()
	{
		return getDescricaoComEmpresa() + (ativo ? " (Ativa)" : " (Inativa)");
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
		this.codigoAC = StringUtils.defaultIfEmpty(codigoAC, null);
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
		preparaResponsavel();
		responsavel.setNomeComercial(nomeResponsavel);
	}
	
	public void setNomeCoResponsavel(String nomeCoResponsavel)
	{
		preparaCoResponsavel();
		coResponsavel.setNomeComercial(nomeCoResponsavel);
	}
	
	public void setEmailResponsavel(String emailResponsavel)
	{
		preparaResponsavel();
		responsavel.setEmailColaborador(emailResponsavel);
	}

	private void preparaResponsavel() {
		if(responsavel == null)
			responsavel = new Colaborador();
	}
	
	public void setEmailCoResponsavel(String emailResponsavel)
	{
		preparaCoResponsavel();
		coResponsavel.setEmailColaborador(emailResponsavel);
	}
	
	private void preparaCoResponsavel() {
		if(coResponsavel == null)
			coResponsavel = new Colaborador();
	}

	public void setIdResponsavel(Long idResponsavel)
	{
		preparaResponsavel();
		responsavel.setId(idResponsavel);
	}
	
	public void setIdCoResponsavel(Long idCoResponsavel)
	{
		preparaCoResponsavel();
		coResponsavel.setId(idCoResponsavel);
	}
	
	public void setResponsavelNome(String nome) {
		preparaResponsavel();
		responsavel.setNome(nome);
	}
	
	@NaoAudita
	public String getResponsavelNome() {
		preparaResponsavel();
		return responsavel.getNome();
	}

	@NaoAudita
	public String getResponsavelNomeComercial() {
		preparaResponsavel();
		return responsavel.getNomeComercial();
	}

	@NaoAudita
	public String getCoResponsavelNome() {
		preparaCoResponsavel();
		return coResponsavel.getNome();
	}
	
	@NaoAudita
	public String getCoResponsavelNomeComercial() {
		preparaCoResponsavel();
		return coResponsavel.getNomeComercial();
	}
	
	@NaoAudita
	public String getResponsavelEmailNomeComercial() {
		try {
			String email = "";
			if(responsavel.getContato().getEmail() != null)
				email = responsavel.getContato().getEmail();
			
			if(responsavel.getNomeComercial() != null)
				email += " (" + responsavel.getNomeComercial() + ")";
				
			return email;
		} catch (Exception e) {
			return "";
		}
	}
	
	@NaoAudita
	public String getResponsavelEmail() {
		try {
			String email = "";
			
			if(responsavel.getContato().getEmail() != null)
				email = responsavel.getContato().getEmail();
			
			return email;
		} catch (Exception e) {
			return "";
		}
	}

	@NaoAudita
	public String getCoResponsavelEmail() {
		try {
			String email = "";
			
			if(coResponsavel.getContato().getEmail() != null)
				email = coResponsavel.getContato().getEmail();
			
			return email;
		} catch (Exception e) {
			return "";
		}
	}
	
	public void setAreaMaeId(Long areaMaeId)
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		areaMae.setId(areaMaeId);
	}
	
	@NaoAudita
	public Long getAreaMaeId()
	{
		if(areaMae == null)
			areaMae = new AreaOrganizacional();

		return areaMae.getId();
	}
	
	@NaoAudita
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

	public String getDescricaoAtiva()
	{
		return BooleanUtil.getDescricao(BooleanUtil.setValueCombo(ativo));
	}
	
	public boolean isAtivo()
	{
		return ativo;
	}

	public void setAtivo(boolean ativo)
	{
		this.ativo = ativo;
	}

	public void setEmailsNotificacoes(String emailsNotificacoes) {
		this.emailsNotificacoes = emailsNotificacoes;
	}

	public String getEmailsNotificacoes() {
		return emailsNotificacoes;
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

	public int getQtdContratados() {
		return qtdContratados;
	}

	public void setQtdContratados(int qtdContratados) {
		this.qtdContratados = qtdContratados;
	}

	public Colaborador getCoResponsavel() {
		return coResponsavel;
	}

	public void setCoResponsavel(Colaborador coResponsavel) {
		this.coResponsavel = coResponsavel;
	}
	
	public int getNivelHierarquico() {
		return nivelHierarquico;
	}

	public void setNivelHierarquico(int nivelHierarquico) {
		this.nivelHierarquico = nivelHierarquico;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
}
