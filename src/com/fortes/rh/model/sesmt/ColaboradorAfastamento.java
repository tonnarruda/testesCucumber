package com.fortes.rh.model.sesmt;

import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorafastamento_sequence", allocationSize=1)
public class ColaboradorAfastamento extends AbstractModel implements Serializable, Cloneable
{
	@Temporal(TemporalType.DATE)
	private Date inicio;
	@Temporal(TemporalType.DATE)
	private Date fim;

	@Column(length=100)
	private String nomeProfissionalDaSaude;

	@Column(length=20)
	private String numeroDoRegistroDeSaude;
	
	@Column(length=4)
	private String tipoRegistroDeSaude;

	@Column(length=10)
	private String cid;

	@Lob
	private String observacao;

	@ManyToOne
	private Afastamento afastamento;

	@ManyToOne(fetch=LAZY)
	private Colaborador colaborador;
	
	@Transient
	private Integer qtdDias = 0;

	@Transient
	private Integer qtdTotalDias = 0;
	
	@Transient
	private Integer qtdAfastamentos;
	
	public ColaboradorAfastamento() { }

	public ColaboradorAfastamento(Long id, Date inicio, Date fim, String afastamentoDescricao, String colaboradorNome, String colaboradorMatricula, String estabelecimentoNome, Long areaOrganizacionalId, String cid, String nomeProfissionalDaSaude, Date dataAdmissao, String dataFimRelatorio, String tipoRegistroDeSaude, String numeroDoRegistroDeSaude)
	{
		setId(id);
		this.inicio = inicio;
		this.fim = fim;
		this.cid = cid;
		this.nomeProfissionalDaSaude = nomeProfissionalDaSaude;
		this.tipoRegistroDeSaude = tipoRegistroDeSaude;
		this.numeroDoRegistroDeSaude = numeroDoRegistroDeSaude;
		
		afastamento = new Afastamento();
		afastamento.setDescricao(afastamentoDescricao);

		colaborador = new Colaborador();
		colaborador.setNome(colaboradorNome);
		colaborador.setMatricula(colaboradorMatricula);
		colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
		colaborador.setAreaOrganizacionalId(areaOrganizacionalId);
		colaborador.setDataAdmissao(dataAdmissao);
		colaborador.setTempoServicoString(DateUtil.formataTempoExtenso(dataAdmissao, DateUtil.criarDataDiaMesAno(dataFimRelatorio)));
	}
	
	// usado em findRelatorioResumoAfastamentos
	public ColaboradorAfastamento(Long colaboradorId, String colaboradorMatricula, String colaboradorNome, Date dataAdmissao, Long areaId, Date inicio, Date fim, Integer qtdDias, Integer qtdAfastamentos)
	{
		this.inicio = inicio;
		this.fim = fim;
		this.qtdDias = qtdDias;
		this.qtdAfastamentos = qtdAfastamentos;
		
		colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
		colaborador.setMatricula(colaboradorMatricula);
		colaborador.setNome(colaboradorNome);
		colaborador.setAreaOrganizacionalId(areaId);
		colaborador.setDataAdmissao(dataAdmissao);
	}

	public String getPeriodoFormatado()
	{
		String periodoFmt = "";
		if (inicio != null)
			periodoFmt += DateUtil.formataDiaMesAno(inicio);
		if (fim != null)
			periodoFmt += " a " + DateUtil.formataDiaMesAno(fim);

		return periodoFmt;
	}
	
	public Integer getDias()
	{
		Integer periodoFmtInt = 0;
		if (inicio != null){
			if (fim != null)
				periodoFmtInt = DateUtil.diferencaEntreDatas(inicio, fim, false) + 1;
			else
				periodoFmtInt = DateUtil.diferencaEntreDatas(inicio, new Date(), false) + 1;
		}
		return periodoFmtInt;
	}
	
	public void setAfastamentoId(Long afastamentoId)
	{
		if (this.afastamento == null)
			this.afastamento = new Afastamento();
		
		this.afastamento.setId(afastamentoId);
	}
	
	public void setAfastamentoDescricao(String afastamentoDescricao)
	{
		if (this.afastamento == null)
			this.afastamento = new Afastamento();
		
		this.afastamento.setDescricao(afastamentoDescricao);
	}
	
	public void setColaboradorCodigoAC(String colaboradorCodigoAC)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setCodigoAC(colaboradorCodigoAC);
	}
	
	public void setProjectionColaboradorNome(String colaboradorNome)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setNome(colaboradorNome);
	}

	public void setProjectionColaboradorNomeComercial(String colaboradorNomeComercial)
	{
		if (this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
	}

	public Afastamento getAfastamento()
	{
		return afastamento;
	}

	public void setAfastamento(Afastamento afastamento)
	{
		this.afastamento = afastamento;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public String getAreaDescricao()
	{
		if (colaborador == null || colaborador.getAreaOrganizacional() == null || colaborador.getAreaOrganizacional().getDescricao() == null)
			return "";
		
		return colaborador.getAreaOrganizacional().getDescricao();
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Date getFim()
	{
		return fim;
	}

	public void setFim(Date fim)
	{
		this.fim = fim;
	}

	public Date getInicio()
	{
		return inicio;
	}

	public void setInicio(Date inicio)
	{
		this.inicio = inicio;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}
	
	public String getMesAno()
	{
		return DateUtil.formataMesAno(inicio);
	}

	public Integer getQtdDias() 
	{
		return qtdDias == null ? 0 : qtdDias;
	}

	public void setQtdDias(Integer qtdDias) 
	{
		this.qtdDias = qtdDias;
	}

	public Integer getQtdAfastamentos() 
	{
		return qtdAfastamentos;
	}

	public void setQtdAfastamentos(Integer qtdAfastamentos) 
	{
		this.qtdAfastamentos = qtdAfastamentos;
	}

	public Integer getQtdTotalDias() {
		return qtdTotalDias;
	}

	public void setQtdTotalDias(Integer qtdTotalDias) {
		this.qtdTotalDias = qtdTotalDias;
	}
	
	public Object clone()
	{
	   try
	   {
	      return super.clone();
	   }
	   catch (CloneNotSupportedException e)
	   {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto.");
	   }
	}

	public String getNumeroDoRegistroDeSaude() {
		return numeroDoRegistroDeSaude;
	}

	public void setNumeroDoRegistroDeSaude(String numeroDoRegistroDeSaude) {
		this.numeroDoRegistroDeSaude = numeroDoRegistroDeSaude;
	}

	public String getTipoRegistroDeSaude() {
		return tipoRegistroDeSaude;
	}

	public void setTipoRegistroDeSaude(String tipoRegistroDeSaude) {
		this.tipoRegistroDeSaude = tipoRegistroDeSaude;
	}

	@NaoAudita
	public String getTipoRegistroDeSaudeMaisRegistro() {
		String tipoMaisRegistro = "";
		
		if (tipoRegistroDeSaude != null && !"".equals(tipoRegistroDeSaude))
			tipoMaisRegistro += tipoRegistroDeSaude + ": ";
		if (numeroDoRegistroDeSaude != null && !"".equals(numeroDoRegistroDeSaude))
			tipoMaisRegistro += numeroDoRegistroDeSaude;

		return tipoMaisRegistro;
	}

	public String getNomeProfissionalDaSaude() {
		return nomeProfissionalDaSaude;
	}

	public void setNomeProfissionalDaSaude(String nomeProfissionalDaSaude) {
		this.nomeProfissionalDaSaude = nomeProfissionalDaSaude;
	}
}