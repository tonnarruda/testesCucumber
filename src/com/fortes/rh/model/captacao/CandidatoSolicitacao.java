package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Apto;
import com.fortes.rh.model.dicionario.StatusAutorizacaoGestor;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Endereco;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.Pessoal;
import com.fortes.rh.util.DateUtil;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="candidatosolicitacao_sequence", allocationSize=1)
public class CandidatoSolicitacao extends AbstractModel implements Serializable, Cloneable
{
	@ManyToOne
	private Candidato candidato;
	@ManyToOne
	private Solicitacao solicitacao;
	private boolean triagem;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="candidatoSolicitacao")
	private Collection<HistoricoCandidato> historicoCandidatos;
	private char status = StatusCandidatoSolicitacao.INDIFERENTE;
	private Character statusAutorizacaoGestor; 
	private Date dataAutorizacaoGestor;
	private String obsAutorizacaoGestor;
	@ManyToOne
	private Usuario usuarioSolicitanteAutorizacaoGestor;

	/* Estes campos são utilizado para exibição dos candidatos da
	 * solicitação na listagem da solicitação */
	@Transient
	private Long colaboradorId;
	@Transient
	private String colaboradorNome;
	@Transient
	private char apto = Apto.SIM;
	@Transient
	private EtapaSeletiva etapaSeletiva;
	@Transient
	private Date data;
	@Transient
	private String responsavel;
	@Transient
	private String observacao;
	@Transient
	private boolean existeColaborador;
	@Transient
	private int qtdCandidatos;
	@Transient
	private int qtdAptos;
	@Transient
	private Long colaboradorQuestionarioId;

	public CandidatoSolicitacao()
	{

	}

	public CandidatoSolicitacao(Long csId, Long cId, String cNome, Boolean cContratado, String indicadoPor, Long eId, String eNome, String hResponsavel, Date hData, String hObservacao, char hApto, Long colaboradorId)
	{
		setId(csId);
		setCandidatoId(cId);
		setCandidatoNome(cNome);
		setCandidatoContratado(cContratado);
		setCandidatoIndicadoPor(indicadoPor);

		setResponsavel(hResponsavel);
		setData(hData);
		setObservacao(hObservacao);
		setApto(hApto);

		setColaboradorId(colaboradorId);

		etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(eId);
		etapaSeletiva.setNome(eNome);
	}

	public CandidatoSolicitacao(Long csId, Long cId, String cNome, Boolean cContratado, Long eId, String eNome, String hResponsavel, Date hData, String hObservacao, char hApto, Long colaboradorId)
	{
		setId(csId);
		setCandidatoId(cId);
		setCandidatoNome(cNome);
		setCandidatoContratado(cContratado);

		setResponsavel(hResponsavel);
		setData(hData);
		setObservacao(hObservacao);
		setApto(hApto);

		setColaboradorId(colaboradorId);

		etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(eId);
		etapaSeletiva.setNome(eNome);
	}
	
	public CandidatoSolicitacao(Candidato candidato, Solicitacao solicitacao, boolean triagem, char status, Character statusAutorizacaoGestor, Usuario usuarioSolicitante){
		setCandidato(candidato);
		setSolicitacao(solicitacao);
		setTriagem(triagem);
		setStatus(status);
		setStatusAutorizacaoGestor(statusAutorizacaoGestor);
		setUsuarioSolicitanteAutorizacaoGestor(usuarioSolicitante);
		setDataAutorizacaoGestor(new Date());
	}
	
	private void inicializaCandidato() {
		if (candidato == null)
			candidato = new Candidato();
	}

	//utilizado no daoHibernate
	public void setCandidatoId(Long candidatoId)
	{
		inicializaCandidato();

		candidato.setId(candidatoId);
	}

	public void setCandidatoNome(String candidatoNome)
	{
		inicializaCandidato();

		candidato.setNome(candidatoNome);
	}
	
	public void setCandidatoDdd(String candidatoDdd)
	{
		inicializaCandidato();

		candidato.setDdd(candidatoDdd);
	}

	public void setCandidatoFoneFixo(String candidatoFoneFixo)
	{
		inicializaCandidato();
		
		candidato.setFoneFixo(candidatoFoneFixo);
	}

	public void setCandidatoEmail(String candidatoEmail)
	{
		inicializaCandidato();
		
		candidato.setEmail(candidatoEmail);
	}
	
	public void setCandidatoFoneCelular(String candidatoFoneCelular)
	{
		inicializaCandidato();

		candidato.setFoneCelular(candidatoFoneCelular);
	}
	
	public void setCandidatoContratado(boolean candidatoContratado)
	{
		inicializaCandidato();

		candidato.setContratado(candidatoContratado);
	}

	public void setCandidatoIndicadoPor(String candidatoIndicadoPor)
	{
		inicializaCandidato();
		
		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());
		
		candidato.getPessoal().setIndicadoPor(candidatoIndicadoPor);
	}

	public void setProjectionCandidatoDataAtualizacao(Date projectionCandidatoDataAtualizacao)
	{
		inicializaCandidato();
		candidato.setDataAtualizacao(projectionCandidatoDataAtualizacao);
	}

	public void setProjectionCandidatoEmail(String projectionCandidatoEmail)
	{
		inicializaCandidato();
		if(candidato.getContato() == null)
			candidato.setContato(new Contato());

		candidato.getContato().setEmail(projectionCandidatoEmail);
	}

	public void setProjectionCandidatoOrigem(char projectionCandidatoOrigem)
	{
		inicializaCandidato();

		candidato.setOrigem(projectionCandidatoOrigem);
	}

	public void setProjectionCandidatoEmpresaId(Long candidatoEmpresaId)
	{
		inicializaCandidato();
		
		candidato.setEmpresaId(candidatoEmpresaId);
	}

	public void setProjectionCandidatoEmpresaNome(String candidatoEmpresaNome)
	{
		inicializaCandidato();
		
		candidato.setEmpresaNome(candidatoEmpresaNome);
	}
	
	public void setProjectionEtapaId(Long projectionEtapaId)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();
		
		etapaSeletiva.setId(projectionEtapaId);
	}
	
	public void setProjectionEtapaNome(String projectionEtapaNome)
	{
		if (etapaSeletiva == null)
			etapaSeletiva = new EtapaSeletiva();
		
		etapaSeletiva.setNome(projectionEtapaNome);
	}

	public void setProjectionCandidatoBlackList(boolean projectionCandidatoBlackList)
	{
		inicializaCandidato();

		candidato.setBlackList(projectionCandidatoBlackList);
	}

	public void setProjectionCidadeNome(String projectionCidadeNome)
	{
		inicializaCandidato();
		if (candidato.getEndereco() == null)
			candidato.setEndereco(new Endereco());
		if (candidato.getEndereco().getCidade() == null)
			candidato.getEndereco().setCidade(new Cidade());

		candidato.getEndereco().getCidade().setNome(projectionCidadeNome);
	}

	public void setProjectionSolicitacaoDescricao(String descricao)
	{
		solicitacao.setDescricao(descricao);
	}
	
	public void setProjectionUfSigla(String projectionUfSigla)
	{
		inicializaCandidato();
		if (candidato.getEndereco() == null)
			candidato.setEndereco(new Endereco());
		if (candidato.getEndereco().getUf() == null)
			candidato.getEndereco().setUf(new Estado());

		candidato.getEndereco().getUf().setSigla(projectionUfSigla);
	}

	public void setSolicitacaoId(Long solicitacaoId)
	{
		iniciaSolicitacao();
		solicitacao.setId(solicitacaoId);
	}

	public void setSolicitacaoDescricao(String solicitacaoDescricao)
	{
		iniciaSolicitacao();
		solicitacao.setDescricao(solicitacaoDescricao);
	}

	private void iniciaSolicitacao() {
		if (solicitacao == null)
			solicitacao = new Solicitacao();
	}	
	
	public void setSolicitacaoData(Date solicitacaoData)
	{
		iniciaSolicitacao();
		solicitacao.setData(solicitacaoData);
	}

	public void setCandidatoPessoalCpf(String cpf)
	{
		inicializaCandidato();

		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());

		candidato.getPessoal().setCpf(cpf);

	}

	public void setProjectionCandidatoEscolaridade(String projectionCandidatoEscolaridade)
	{
		inicializaCandidato();

		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());

		candidato.getPessoal().setEscolaridade(projectionCandidatoEscolaridade);

	}

	public void setSolicitacaoQtd(int qtd)
	{
		iniciaSolicitacao();
		solicitacao.setQuantidade(qtd);
	}

	public void setSolicitanteId(Long id)
	{
		iniciaSolicitacao();
		if (solicitacao.getSolicitante() == null)
			solicitacao.setSolicitante(new Usuario());
		solicitacao.getSolicitante().setId(id);
	}

	public void setSolicitanteNome(String nome)
	{
		iniciaSolicitacao();
		if (solicitacao.getSolicitante() == null)
			solicitacao.setSolicitante(new Usuario());
		solicitacao.getSolicitante().setNome(nome);
	}

	public void setAreaOrganizacionalId(Long id)
	{
		iniciaSolicitacao();
		if (solicitacao.getAreaOrganizacional() == null)
			solicitacao.setAreaOrganizacional(new AreaOrganizacional());
		solicitacao.getAreaOrganizacional().setId(id);
	}

	public void setAreaOrganizacionalNome(String nome)
	{
		iniciaSolicitacao();
		if (solicitacao.getAreaOrganizacional() == null)
			solicitacao.setAreaOrganizacional(new AreaOrganizacional());
		solicitacao.getAreaOrganizacional().setNome(nome);
	}

	public void setFaixaId(Long id)
	{
		iniciaSolicitacao();
		if (solicitacao.getFaixaSalarial() == null)
			solicitacao.setFaixaSalarial(new FaixaSalarial());

		solicitacao.getFaixaSalarial().setId(id);
	}

	public void setFaixaNome(String nome)
	{
		iniciaSolicitacao();
		solicitacao.setFaixaSalarialNome(nome);
	}

	public void setCargoId(Long id)
	{
		iniciaSolicitacao();
		if (solicitacao.getFaixaSalarial() == null)
			solicitacao.setFaixaSalarial(new FaixaSalarial());
		if (solicitacao.getFaixaSalarial().getCargo() == null)
			solicitacao.getFaixaSalarial().setCargo(new Cargo());

		solicitacao.getFaixaSalarial().getCargo().setId(id);
	}

	public void setCargoNome(String nome)
	{
		iniciaSolicitacao();
		solicitacao.setNomeCargo(nome);
	}

	public void setSolicitacaoObservacaoLiberador(String observacaoLiberador)
	{
		iniciaSolicitacao();

		solicitacao.setObservacaoLiberador(observacaoLiberador);
	}

	public void setDescricaoMotivoSolicitacao(String descricaoMotivoSolicitacao)
	{
		iniciaSolicitacao();
		if (solicitacao.getMotivoSolicitacao() == null)
			solicitacao.setMotivoSolicitacao(new MotivoSolicitacao());

		solicitacao.getMotivoSolicitacao().setDescricao(descricaoMotivoSolicitacao);
	}

	//get usado pelo populaCheckListBox
	public String getCandidatoNome()
	{
		String label = "";
		if(candidato != null && candidato.getNome() != null)
			label = candidato.getNome();

		if(etapaSeletiva != null && etapaSeletiva.getNome() != null && !etapaSeletiva.getNome().equals(""))
			label += " (" + etapaSeletiva.getNome() + ")";
			
		return label;
	}

	public Candidato getCandidato()
	{
		return candidato;
	}
	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}
	
	
	public void setProjectionApto(Character apto)
	{
		setApto(apto);
	}
	
	public boolean isTriagem()
	{
		return triagem;
	}
	public void setTriagem(boolean triagem)
	{
		this.triagem = triagem;
	}

	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}

	public String getDataString()
	{
		return DateUtil.formataDiaMesAno(data);
	}

	public EtapaSeletiva getEtapaSeletiva()
	{
		return etapaSeletiva;
	}
	public void setEtapaSeletiva(EtapaSeletiva etapaSeletiva)
	{
		this.etapaSeletiva = etapaSeletiva;
	}
	public String getObservacao()
	{
		return observacao;
	}
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}
	public String getResponsavel()
	{
		return responsavel;
	}
	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("etapaSeletiva", this.etapaSeletiva).append(
						"solicitacao", this.solicitacao).append("id", this.getId())
				.append("apto", this.apto).append("responsavel",
						this.responsavel).append("observacao", this.observacao)
				.append("data", this.data).append("candidato", this.candidato)
				.append("triagem", this.triagem).toString();
	}

	public boolean isExisteColaborador()
	{
		return existeColaborador;
	}

	public void setExisteColaborador(boolean existeColaborador)
	{
		this.existeColaborador = existeColaborador;
	}

	public int getQtdCandidatos()
	{
		return qtdCandidatos;
	}

	public void setQtdCandidatos(int qtdCandidatos)
	{
		this.qtdCandidatos = qtdCandidatos;
	}

	public int getQtdAptos()
	{
		return qtdAptos;
	}

	public void setQtdAptos(int qtdAptos)
	{
		this.qtdAptos = qtdAptos;
	}

	public Long getColaboradorId()
	{
		return colaboradorId;
	}

	public void setColaboradorId(Long colaboradorId)
	{
		this.colaboradorId = colaboradorId;
	}
	
	public void setColaboradorNome(String colaboradorNome)
	{
		this.colaboradorNome = colaboradorNome;
	}

	public Collection<HistoricoCandidato> getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}
	
	public Long getColaboradorQuestionarioId() {
		return colaboradorQuestionarioId;
	}

	public void setColaboradorQuestionarioId(Long colaboradorQuestionarioId) {
		this.colaboradorQuestionarioId = colaboradorQuestionarioId;
	}

	public char getApto() {
		return apto;
	}
	
	public String getAptoDescricao() {
		return Apto.getDescApto(apto);
	}
	
	public boolean getAptoBoolean() {
		return this.apto != Apto.NAO;
	}

	public void setApto(Character apto) 
	{
		if(apto == null)
			this.apto = Apto.INDIFERENTE;
		else
			this.apto = apto;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getNomeArea() 
	{
		try {
			return this.solicitacao.getAreaOrganizacional().getNome();
		} catch (Exception e) {
			return "";
		}
	}

	public String getNomeCargo() 
	{
		try {
			return this.solicitacao.getFaixaSalarial().getCargo().getNome();
		} catch (Exception e) {
			return "";
		}
	}
	
	public String getNomeSolicitante() 
	{
		try {
			return this.solicitacao.getSolicitante().getNome();
		} catch (Exception e) {
			return "";
		}
	}
	
	public void setSolicitacaoNomeCargo(String solicitacaoNomeCargo){
		iniciaSolicitacao();
		this.solicitacao.setNomeCargo(solicitacaoNomeCargo);
	}
	
	public void setSolicitacaoNomeArea(String solicitacaoNomeArea){
		iniciaSolicitacao();
		this.solicitacao.setNomeArea(solicitacaoNomeArea);
	}
	
	public void setSolicitacaoEstabelecimentoNome(String solicitacaoEstabelecimentoNome){
		iniciaSolicitacao();
		this.solicitacao.setEstabelecimentoNome(solicitacaoEstabelecimentoNome);
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}
	
	public String getStatusAutorizacaoGestorFormatado()
	{
		return StatusAutorizacaoGestor.getDescricao(getStatusAutorizacaoGestor());
	}

	public String getDataAutorizacaoGestorFormatado() {
		return DateUtil.formataDiaMesAno(dataAutorizacaoGestor);
	}
	
	public Date getDataAutorizacaoGestor() {
		return dataAutorizacaoGestor;
	}

	public void setDataAutorizacaoGestor(Date dataAutorizacaoGestor) {
		this.dataAutorizacaoGestor = dataAutorizacaoGestor;
	}

	public String getObsAutorizacaoGestor() {
		return obsAutorizacaoGestor;
	}

	public void setObsAutorizacaoGestor(String obsAutorizacaoGestor) {
		this.obsAutorizacaoGestor = obsAutorizacaoGestor;
	}

	public Character getStatusAutorizacaoGestor() {
		return statusAutorizacaoGestor;
	}

	public void setStatusAutorizacaoGestor(Character statusAutorizacaoGestor) {
		this.statusAutorizacaoGestor = statusAutorizacaoGestor;
	}

	public Usuario getUsuarioSolicitanteAutorizacaoGestor() {
		return usuarioSolicitanteAutorizacaoGestor;
	}

	public void setUsuarioSolicitanteAutorizacaoGestor(
			Usuario usuarioSolicitanteAutorizacaoGestor) {
		this.usuarioSolicitanteAutorizacaoGestor = usuarioSolicitanteAutorizacaoGestor;
	}
	
	@Override
	public Object clone()
	{
	   try{
	      return super.clone();
	   }catch (CloneNotSupportedException e) {
	      throw new Error("Ocorreu um erro interno no sistema. Não foi possível clonar o objeto.");
	   }
	}
}