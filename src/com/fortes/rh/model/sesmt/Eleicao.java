package com.fortes.rh.model.sesmt;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="eleicao_sequence", allocationSize=1)
public class Eleicao extends AbstractModel implements Serializable
{
	@Temporal(TemporalType.DATE)
	private Date posse;
	@Temporal(TemporalType.DATE)
	private Date votacaoIni;
	@Temporal(TemporalType.DATE)
	private Date votacaoFim;
	@Column(length=20)
	private String horarioVotacaoIni;
	@Column(length=20)
	private String horarioVotacaoFim;
	private Integer qtdVotoNulo = 0;
	private Integer qtdVotoBranco = 0;
	@Temporal(TemporalType.DATE)
	private Date inscricaoCandidatoIni;
	@Temporal(TemporalType.DATE)
	private Date inscricaoCandidatoFim;
	@Column(length=100)
	private String localInscricao;
	@Column(length=100)
	private String localVotacao;
	@Temporal(TemporalType.DATE)
	private Date apuracao;
	@Column(length=20)
	private String horarioApuracao;
	@Column(length=100)
	private String localApuracao;
	@Column(length=100)
	private String sindicato;
	@Column(length=100)
	private String descricao;
	private String textoAtaEleicao;
	private String textoEditalInscricao;
	private String textoChamadoEleicao;
	private String textoSindicato;
	private String textoDRT;

	@Transient
	private Integer somaVotos;

    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    private Estabelecimento estabelecimento;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="eleicao")
    private Collection<CandidatoEleicao> candidatoEleicaos;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="eleicao")
    private Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals;

    public Eleicao()
    {

    }

    public Eleicao(String horarioVotacaoIni, String horarioVotacaoFim)
	{
    	super();
		this.setHorarioVotacaoIni(horarioVotacaoIni);
		this.setHorarioVotacaoFim(horarioVotacaoFim);
		this.setHorarioApuracao(horarioVotacaoIni);
	}

    public String getVotacaoPeriodoFormatado()
    {
    	String periodoFmt = "-";
    	if (this.votacaoIni != null)
    		periodoFmt = DateUtil.formataDiaMesAno(this.votacaoIni) + " a " + DateUtil.formataDiaMesAno(this.votacaoFim);

    	return periodoFmt;
    }

    public String getDescricaoFormatada()
    {
    	String descricaoFormatada = DateUtil.formataDiaMesAno(this.posse);

    	if (StringUtils.isNotBlank(this.descricao))
    		descricaoFormatada += " - " + descricao;

    	newEstabelecimento();

    	if (StringUtils.isNotBlank(this.estabelecimento.getNome()))
    		descricaoFormatada += " (" + estabelecimento.getNome() + ")";

    	return descricaoFormatada;
    }

	//Projections
    public void setProjectionEmpresaId(Long projectionEmpresaId)
    {
    	if(empresa == null)
    		empresa = new Empresa();

    	empresa.setId(projectionEmpresaId);
    }

    public void setProjectionEstabelecimentoId(Long estabelecimentoId)
    {
    	newEstabelecimento();
    	estabelecimento.setId(estabelecimentoId);
    }
    public void setProjectionEstabelecimentoNome(String estabelecimentoNome)
    {
    	newEstabelecimento();
    	estabelecimento.setNome(estabelecimentoNome);
    }

    public void setProjectionEstabelecimentoLogradouro(String estabelecimentoLogradouro)
    {
    	newEstabelecimento();
    	estabelecimento.getEndereco().setLogradouro(estabelecimentoLogradouro);
    }
    public void setProjectionEstabelecimentoNumero(String estabelecimentoNumero)
    {
    	newEstabelecimento();
    	estabelecimento.getEndereco().setNumero(estabelecimentoNumero);
    }
    public void setProjectionEstabelecimentoBairro(String estabelecimentoBairro)
    {
    	newEstabelecimento();
    	estabelecimento.getEndereco().setBairro(estabelecimentoBairro);
    }

	private void newEstabelecimento()
	{
		if(estabelecimento == null)
    		estabelecimento = new Estabelecimento();
	}

	public String getHorarioVotacaoFim()
	{
		return horarioVotacaoFim;
	}
	public void setHorarioVotacaoFim(String horarioVotacaoFim)
	{
		this.horarioVotacaoFim = horarioVotacaoFim;
	}
	public String getHorarioVotacaoIni()
	{
		return horarioVotacaoIni;
	}
	public void setHorarioVotacaoIni(String horarioVotacaoIni)
	{
		this.horarioVotacaoIni = horarioVotacaoIni;
	}
	public Date getInscricaoCandidatoFim()
	{
		return inscricaoCandidatoFim;
	}
	public void setInscricaoCandidatoFim(Date inscricaoCandidatoFim)
	{
		this.inscricaoCandidatoFim = inscricaoCandidatoFim;
	}
	public Date getInscricaoCandidatoIni()
	{
		return inscricaoCandidatoIni;
	}
	public void setInscricaoCandidatoIni(Date inscricaoCandidatoIni)
	{
		this.inscricaoCandidatoIni = inscricaoCandidatoIni;
	}
	public String getLocalInscricao()
	{
		return localInscricao;
	}
	public void setLocalInscricao(String localInscricao)
	{
		this.localInscricao = localInscricao;
	}
	public String getLocalVotacao()
	{
		return localVotacao;
	}
	public void setLocalVotacao(String localVotacao)
	{
		this.localVotacao = localVotacao;
	}
	public Date getPosse()
	{
		return posse;
	}
	public void setPosse(Date posse)
	{
		this.posse = posse;
	}
	public Integer getQtdVotoBranco()
	{
		return qtdVotoBranco;
	}
	public void setQtdVotoBranco(Integer qtdVotoBranco)
	{
		this.qtdVotoBranco = qtdVotoBranco;
	}
	public Integer getQtdVotoNulo()
	{
		return qtdVotoNulo;
	}
	public void setQtdVotoNulo(Integer qtdVotoNulo)
	{
		this.qtdVotoNulo = qtdVotoNulo;
	}
	public Empresa getEmpresa()
	{
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Collection<CandidatoEleicao> getCandidatoEleicaos()
	{
		return candidatoEleicaos;
	}

	public void setCandidatoEleicaos(Collection<CandidatoEleicao> candidatoEleicaos)
	{
		this.candidatoEleicaos = candidatoEleicaos;
	}

	public Integer getSomaVotos()
	{
		return somaVotos;
	}

	public void setSomaVotos(Integer somaVotos)
	{
		this.somaVotos = somaVotos;
	}

	public Date getApuracao()
	{
		return apuracao;
	}

	public void setApuracao(Date apuracao)
	{
		this.apuracao = apuracao;
	}

	public String getSindicato()
	{
		return sindicato;
	}

	public void setSindicato(String sindicato)
	{
		this.sindicato = sindicato;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public Date getVotacaoFim()
	{
		return votacaoFim;
	}

	public void setVotacaoFim(Date votacaoFim)
	{
		this.votacaoFim = votacaoFim;
	}

	public Date getVotacaoIni()
	{
		return votacaoIni;
	}

	public void setVotacaoIni(Date votacaoIni)
	{
		this.votacaoIni = votacaoIni;
	}

	public Collection<EtapaProcessoEleitoral> getEtapaProcessoEleitorals()
	{
		return etapaProcessoEleitorals;
	}

	public void setEtapaProcessoEleitorals(Collection<EtapaProcessoEleitoral> etapaProcessoEleitorals)
	{
		this.etapaProcessoEleitorals = etapaProcessoEleitorals;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public String getLocalApuracao()
	{
		return localApuracao;
	}

	public void setLocalApuracao(String localApuracao)
	{
		this.localApuracao = localApuracao;
	}

	public String getHorarioApuracao()
	{
		return horarioApuracao;
	}

	public void setHorarioApuracao(String horarioApuracao)
	{
		this.horarioApuracao = horarioApuracao;
	}

	public String getTextoAtaEleicao() {
		return textoAtaEleicao;
	}

	public void setTextoAtaEleicao(String textoAtaEleicao) {
		this.textoAtaEleicao = textoAtaEleicao;
	}

	public String getTextoEditalInscricao() {
		return textoEditalInscricao;
	}

	public void setTextoEditalInscricao(String textoEditalInscricao) {
		this.textoEditalInscricao = textoEditalInscricao;
	}

	public String getTextoChamadoEleicao() {
		return textoChamadoEleicao;
	}

	public void setTextoChamadoEleicao(String textoChamadoEleicao) {
		this.textoChamadoEleicao = textoChamadoEleicao;
	}

	public String getTextoSindicato() {
		return textoSindicato;
	}

	public void setTextoSindicato(String textoSindicato) {
		this.textoSindicato = textoSindicato;
	}

	public String getTextoDRT() {
		return textoDRT;
	}

	public void setTextoDRT(String textoDRT) {
		this.textoDRT = textoDRT;
	}
}