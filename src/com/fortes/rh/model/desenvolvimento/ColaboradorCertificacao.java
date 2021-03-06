package com.fortes.rh.model.desenvolvimento;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaboradorCertificacao_sequence", allocationSize=1)
public class ColaboradorCertificacao extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne(fetch = FetchType.LAZY)
	private Colaborador colaborador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Certificacao certificacao;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@ManyToMany
	private Collection<ColaboradorTurma> colaboradoresTurmas; 

	@OneToMany(fetch=FetchType.LAZY,  mappedBy="colaboradorCertificacao", cascade = {CascadeType.REMOVE, CascadeType.MERGE})
	private Collection<ColaboradorAvaliacaoPratica> colaboradoresAvaliacoesPraticas;
	
	@ManyToOne
	private ColaboradorCertificacao colaboradorCertificacaoPreRequisito;
	
	@Transient
	private String nomeCurso;
	@Transient
	private String periodoTurma;
	@Transient
	private Boolean aprovadoNaCertificacao;
	@Transient
	private Boolean ultimaCertificacao;
	@Transient
	private String qtdColaboradorAprovado = "-";
	@Transient
	private String qtdColaboradorNaoAprovado = "-";
	@Transient
	private ColaboradorAvaliacaoPratica colaboradorAvaliacaoPraticaAtual;
	@Transient
	private ColaboradorTurma colaboradorTurma;
	@Transient
	private String dataString;//Usado no mapeamento do DWR
	@Transient
	private boolean possivelInserirNotaAvPratica;

	public ColaboradorCertificacao() {
	}
	
	public ColaboradorCertificacao(Long certificacaoId, Long colaboradorId, Long certificacaoPreRequisitoId)
	{
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);

		this.certificacao = new Certificacao();
		this.certificacao.setId(certificacaoId);
		this.certificacao.setCertificacaoPreRequisitoId(certificacaoPreRequisitoId);
	}
	
	public ColaboradorCertificacao(Long id, Long certificacaoId, Long colaboradorId, Date data)
	{
		this.setId(id);
		this.setData(data);
		this.colaborador = new Colaborador();
		this.colaborador.setId(colaboradorId);

		this.certificacao = new Certificacao();
		this.certificacao.setId(certificacaoId);
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}
	
	//Usado em montaMapColaboradorTurma
	public Long getColaboradorId() {
		return colaborador.getId();
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatada() 
	{
		if(this.data == null)
			return "-";
		
		return DateUtil.formataDiaMesAno(data);
	}

	public String getDataCertificadoFormatada()
	{
		if(this.data == null)
			return "-";
		
		return DateUtil.formataDiaMesAno(this.data);
	}
	
	public String getDataVencimentoCertificacao()
	{
		if(this.data == null)
			return "-";
		else if(this.certificacao.getPeriodicidade() == null)
			return "Sem vencimento";
		else{
			Date vencimento = DateUtil.incrementaMes(this.data, this.certificacao.getPeriodicidade());
			return DateUtil.formataDiaMesAno(vencimento);
		}
	}
	
	public void setCargoNome(String cargoNome)
	{
		inicializaColaborador();
		this.colaborador.setCargoNomeProjection(cargoNome);
	}
	
	public void setCargoId(Long cargoId)
	{
		inicializaColaborador();
		this.colaborador.setCargoIdProjection(cargoId);
	}
	
	public void setEstabelecimentoId(Long estabelecimentoId)
	{
		inicializaColaborador();
		this.colaborador.setEstabelecimentoIdProjection(estabelecimentoId);
	}
	
	public void setEstabelecimentoNome(String estabelecimentoNome)
	{
		inicializaColaborador();
		this.colaborador.setEstabelecimentoNomeProjection(estabelecimentoNome);
	}
	
	public void setFaixaSalarialId(Long faixaSalarialId)
	{
		inicializaColaborador();
		this.colaborador.setFaixaSalarialIdProjection(faixaSalarialId);
	}
	
	public void setFaixaSalarialNome(String faixaSalarialNome)
	{
		inicializaColaborador();
		this.colaborador.setFaixaSalarialNomeProjection(faixaSalarialNome);
	}
	
	public void setColaboradorEmail(String colaboradorEmail)
	{
		inicializaColaborador();
		this.colaborador.setEmailColaborador(colaboradorEmail);
	}

	public void setColaboradorMatricula(String colaboradorMatricula) 
	{
		inicializaColaborador();
		this.colaborador.setMatricula(colaboradorMatricula);
	}
	
	public void setColaboradorNome(String colaboradorNome)
	{
		inicializaColaborador();
		this.colaborador.setNome(colaboradorNome);
	}
	
	public void setColaboradorNomeComercial(String colaboradorNomeComercial)
	{
		inicializaColaborador();
		this.colaborador.setNomeComercial(colaboradorNomeComercial);
	}
	
	public void setAreaOrganizacionalAreaMaeId(Long areaOrganizacionalAreaMaeId)
	{
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalAreaMaeId(areaOrganizacionalAreaMaeId);
	}
	
	public void setAreaOrganizacionalNome(String areaOrganizacionalNome)
	{
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalNome(areaOrganizacionalNome);
	}
	
	public void setAreaOrganizacionalId(Long areaOrganizacionalId)
	{
		inicializaColaborador();
		this.colaborador.setAreaOrganizacionalId(areaOrganizacionalId);
	}

	private void inicializaColaborador() 
	{
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
	} 
	
	public void setCertificacaoPeriodicidade(Integer certificacaoPeriodicidade)
	{
		iniciaCertificacao();
		this.certificacao.setPeriodicidade(certificacaoPeriodicidade);
	}
	
	public void setCertificacaoNome(String CertificacaoNome)
	{
		iniciaCertificacao();
		this.certificacao.setNome(CertificacaoNome);
	}
	
	public void setCertificacaoPreRequisitoId(Long certificacaoPreRequisitoId)
	{
		iniciaCertificacao();
		this.certificacao.setCertificacaoPreRequisitoId(certificacaoPreRequisitoId);
	}

	private void iniciaCertificacao() 
	{
		if(this.certificacao == null)
			this.certificacao = new Certificacao();
	}
	
	@Override
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

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getPeriodoTurma() {
		return periodoTurma;
	}

	public void setPeriodoTurma(String periodoTurma) {
		this.periodoTurma = periodoTurma;
	}

	public Collection<ColaboradorAvaliacaoPratica> getColaboradoresAvaliacoesPraticas() {
		return colaboradoresAvaliacoesPraticas;
	}

	public void setColaboradoresAvaliacoesPraticas(Collection<ColaboradorAvaliacaoPratica> colaboradoresAvaliacoesPraticas) {
		this.colaboradoresAvaliacoesPraticas = colaboradoresAvaliacoesPraticas;
	}

	public Collection<ColaboradorTurma> getColaboradoresTurmas() {
		return colaboradoresTurmas;
	}

	public void setColaboradoresTurmas(Collection<ColaboradorTurma> colaboradoresTurmas) {
		this.colaboradoresTurmas = colaboradoresTurmas;
	}

	public void setColaboradorId(Long colaboradorId) {
		if(this.colaborador == null)
			this.colaborador = new Colaborador();
		
		this.colaborador.setId(colaboradorId);
	}

	public void setCertificacaoId(Long certificacaoId) {
		if(this.certificacao == null)
			this.certificacao = new Certificacao();
		
		this.certificacao.setId(certificacaoId);
	}
	
	public String getAprovadoNaCertificacaoString() {
		if(this.data != null){
			if (this.certificacao.getPeriodicidade()!=null && DateUtil.incrementaMes(this.data, this.certificacao.getPeriodicidade()).getTime() < (new Date()).getTime())
				return "Certificado vencido";

			return "Certificado";
		}
		
		return "Não Certificado";
	}

	public Boolean getAprovadoNaCertificacao() {
		return aprovadoNaCertificacao != null ? aprovadoNaCertificacao : false;
	}

	public void setAprovadoNaCertificacao(Boolean aprovadoNaCertificacao) {
		this.aprovadoNaCertificacao = aprovadoNaCertificacao;
	}

	public Boolean getUltimaCertificacao() {
		return ultimaCertificacao;
	}

	public void setUltimaCertificacao(Boolean ultimaCertificacao) {
		this.ultimaCertificacao = ultimaCertificacao;
	}
	
	public String getQtdColaboradorAprovado() {
		return qtdColaboradorAprovado;
	}

	public void setQtdColaboradorAprovado(String qtdColaboradorAprovado) {
		this.qtdColaboradorAprovado = qtdColaboradorAprovado;
	}

	public String getQtdColaboradorNaoAprovado() {
		return qtdColaboradorNaoAprovado;
	}

	public void setQtdColaboradorNaoAprovado(String qtdColaboradorNaoAprovado) {
		this.qtdColaboradorNaoAprovado = qtdColaboradorNaoAprovado;
	}

	public ColaboradorCertificacao getColaboradorCertificacaoPreRequisito() {
		return colaboradorCertificacaoPreRequisito;
	}

	public void setColaboradorCertificacaoPreRequisito(ColaboradorCertificacao colaboradorCertificacaoPreRequisito) {
		this.colaboradorCertificacaoPreRequisito = colaboradorCertificacaoPreRequisito;
	}
	
	public void setColaboradorCertificacaoPreRequisitoId(Long colaboradorCertificacaoPreRequisitoId) {
		if(this.colaboradorCertificacaoPreRequisito == null)
			this.colaboradorCertificacaoPreRequisito = new ColaboradorCertificacao();
		
		this.colaboradorCertificacaoPreRequisito.setId(colaboradorCertificacaoPreRequisitoId);
	}

	public ColaboradorAvaliacaoPratica getColaboradorAvaliacaoPraticaAtual() {
		return colaboradorAvaliacaoPraticaAtual;
	}

	public void setColaboradorAvaliacaoPraticaAtual(
			ColaboradorAvaliacaoPratica colaboradorAvaliacaoPraticaAtual) {
		this.colaboradorAvaliacaoPraticaAtual = colaboradorAvaliacaoPraticaAtual;
	}

	public ColaboradorTurma getColaboradorTurma() {
		return colaboradorTurma;
	}

	public void setColaboradorTurma(ColaboradorTurma colaboradorTurma) {
		this.colaboradorTurma = colaboradorTurma;
	}
	
	private void inicializaColaboradorTurma(){
		if(this.colaboradorTurma == null)
			this.colaboradorTurma = new ColaboradorTurma();
	}
	
	public void setColaboradorTurmaId(Long colaboradorTurmaId){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setId(colaboradorTurmaId);
	}
	
	public void setColaboradorTurmaAprovado(boolean colaboradorTurmaAprovado){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setAprovado(colaboradorTurmaAprovado);
	}
	
	public void setColaboradorTurmaCursoId(Long colaboradorTurmaCursoId){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setCursoId(colaboradorTurmaCursoId);
	}

	public void setColaboradorTurmaCursoNome(String colaboradorTurmaCursoNome){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setCursoNome(colaboradorTurmaCursoNome);
	}

	public void setColaboradorTurmaTurmaId(Long colaboradorTurmaTurmaId){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setTurmaId(colaboradorTurmaTurmaId);
	}
	
	public void setColaboradorTurmaTurmaDescricao(String colaboradorTurmaTurmaDescricao){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setTurmaDescricao(colaboradorTurmaTurmaDescricao);
	}

	public void setColaboradorTurmaTurmaDataPrevIni(Date colaboradorTurmaTurmaDataPrevIni){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setTurmaDataPrevIni(colaboradorTurmaTurmaDataPrevIni);
	}

	public void setColaboradorTurmaTurmaDataPrevFim(Date colaboradorTurmaTurmaDataPrevFim){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setTurmaDataPrevFim(colaboradorTurmaTurmaDataPrevFim);
	}
	
	public void setColaboradorTurmaTurmaRealizada(Boolean colaboradorTurmaTurmaRealizada){
		inicializaColaboradorTurma();
		this.colaboradorTurma.setTurmaRealizada(colaboradorTurmaTurmaRealizada);
	}

	public String getDataString() {
		return DateUtil.formataDiaMesAno(data);
	}

	public boolean isPossivelInserirNotaAvPratica() {
		return possivelInserirNotaAvPratica;
	}

	public void setPossivelInserirNotaAvPratica(boolean possivelInserirNotaAvPratica) {
		this.possivelInserirNotaAvPratica = possivelInserirNotaAvPratica;
	}
}