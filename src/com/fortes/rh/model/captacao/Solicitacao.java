package com.fortes.rh.model.captacao;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "sequence", sequenceName = "solicitacao_sequence", allocationSize = 1)
public class Solicitacao extends AbstractModel implements Serializable, Cloneable
{
	
	@Column(length=100)
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Date data;
	@Temporal(TemporalType.DATE)
	private Date dataPrevisaoEncerramento;
	@Temporal(TemporalType.DATE)
	private Date dataEncerramento;
	@Temporal(TemporalType.DATE)
	private Date dataStatus;
	private int quantidade;
	@Column(length=5)
	private String vinculo;
	@Column(length=5)
	private String escolaridade;
	private Double remuneracao;
	private Integer idadeMinima;
	private Integer idadeMaxima;
	private String horarioComercial = "";
	@Column(length=5)
	private String sexo;
	@Lob
	private String infoComplementares;
	@Lob
	private String observacaoLiberador;
	private boolean encerrada;
	private char status = StatusAprovacaoSolicitacao.ANALISE;
	private boolean invisivelParaGestor;
	@ManyToOne
	private MotivoSolicitacao motivoSolicitacao;
	@Column(length=100)
	private String colaboradorSubstituido;
	@ManyToOne
	private AreaOrganizacional areaOrganizacional;
	@ManyToOne
	private Estabelecimento estabelecimento;
	@ManyToOne
	private FaixaSalarial faixaSalarial;
	@ManyToOne
	private Funcao funcao;
	@ManyToOne
	private Ambiente ambiente;
	@ManyToOne
	private Usuario solicitante;
	@ManyToOne
	private Usuario liberador;
	@OneToOne (mappedBy="solicitacao", fetch = FetchType.LAZY)
	private Anuncio anuncio;
	@OneToMany (mappedBy="solicitacao", fetch = FetchType.LAZY)
	private Collection<CandidatoSolicitacao> candidatoSolicitacaos;

	@ManyToMany(fetch = FetchType.EAGER)
	private Collection<Bairro> bairros;
	
	@ManyToOne
	public Cidade cidade;

	@ManyToOne
	private Empresa empresa;
	
	private boolean suspensa;
	
	@Lob
	private String obsSuspensao;
	
	@Lob
	private String experiencias;
	
	@OneToMany (mappedBy="solicitacao", fetch=FetchType.LAZY)
	private Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos;
	
	@OneToMany (mappedBy="solicitacao", fetch=FetchType.LAZY)
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios;
	
	@OneToMany (mappedBy="solicitacao", fetch=FetchType.LAZY)
	private Collection<PausaPreenchimentoVagas> pausasPreenchimentoVagas;

	@Transient
	private String valorPromocao;
	
	@Transient
	private Integer qtdVagasPreenchidas;
	
	@Transient
	private Integer qtdAvaliacoes;
	
	@Transient
	private Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias;
	
	@Transient
	private Collection<NivelCompetencia> nivelCompetencias;
	
	public Solicitacao(){
	}
	
	public Solicitacao(Long id){
		setId(id);
	}

	public Solicitacao(Long id, int quantidade, Date data, boolean encerrada, Long empresaId, Double valorDoHistoricoDaFaixaSalarial, Long faixaSalarialId, Long idCargo, String nomeCargo, String nomeAreaOrganizacional, String nomeSolicitante, Integer qtdVagasPreenchidas)
	{
		setId(id);
		setQuantidade(quantidade);
		setData(data);
		setNomeArea(nomeAreaOrganizacional);
		setSolicitanteNome(nomeSolicitante);
		setProjectionEmpresaId(empresaId);
		this.encerrada = encerrada;
		this.qtdVagasPreenchidas = qtdVagasPreenchidas;
		
		setProjectionFaixaSalarialId(faixaSalarialId);
		getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());
		getFaixaSalarial().getFaixaSalarialHistoricoAtual().setValor(valorDoHistoricoDaFaixaSalarial);
		getFaixaSalarial().setCargo(new Cargo());
		getFaixaSalarial().getCargo().setId(idCargo);
		getFaixaSalarial().getCargo().setNome(nomeCargo);
	}
	
	public Solicitacao(Long id, Integer quantidade, Integer qtdVagasPreenchidas){
		setId(id);
		setQuantidade(quantidade);
		setQtdVagasPreenchidas(qtdVagasPreenchidas);
	}

	public String getObsSuspensao()
	{
		return obsSuspensao;
	}

	public void setObsSuspensao(String obsSuspensao)
	{
		this.obsSuspensao = obsSuspensao;
	}

	public boolean isSuspensa()
	{
		return suspensa;
	}

	public void setSuspensa(boolean suspensa)
	{
		this.suspensa = suspensa;
	}

	public void setProjectionCidadeId(Long projectionCidadeId)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();

		this.cidade.setId(projectionCidadeId);
	}

	public void setProjectionCidadeNome(String projectionCidadeNome)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();
		
		this.cidade.setNome(projectionCidadeNome);
	}

	public void setProjectionCidadeUf(Long projectionCidadeUf)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();

		if(this.cidade.getUf() == null)
			this.getCidade().setUf(new Estado());

		this.getCidade().getUf().setId(projectionCidadeUf);
	}

	public void setProjectionCidadeUfSigla(String projectionCidadeUfSigla)
	{
		if(this.cidade == null)
			this.cidade = new Cidade();
		
		if(this.cidade.getUf() == null)
			this.getCidade().setUf(new Estado());
		
		this.getCidade().getUf().setSigla(projectionCidadeUfSigla);
	}

	public void setProjectionEstabelecimentoId(Long projectionEstabelecimentoId)
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setId(projectionEstabelecimentoId);
	}

	public void setProjectionEstabelecimentoNome(String projectionEstabelecimentoNome)
	{
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();

		this.estabelecimento.setNome(projectionEstabelecimentoNome);
	}

	public void setProjectionMotivoSolicitacaoId(Long projectionMotivoSolicitacaoId)
	{
		if(this.motivoSolicitacao == null)
			motivoSolicitacao = new MotivoSolicitacao();

		motivoSolicitacao.setId(projectionMotivoSolicitacaoId);
	}
	
	public void setProjectionMotivoSolicitacaoDescricao(String motivoSolicitacaoDescricao)
	{
		if(this.motivoSolicitacao == null)
			motivoSolicitacao = new MotivoSolicitacao();

		motivoSolicitacao.setDescricao(motivoSolicitacaoDescricao);
	}
	
	public void setProjectionMotivoSolicitacaoTurnover(boolean motivoSolicitacaoTurnover) 
	{
		if(this.motivoSolicitacao == null)
			motivoSolicitacao = new MotivoSolicitacao();

		motivoSolicitacao.setTurnover(motivoSolicitacaoTurnover);
	}
	
	private void iniciaAmbiente() 
	{
		if(this.ambiente == null)
			ambiente = new Ambiente();
	}
	
	public void setProjectionAmbienteId(Long projectionAmbienteId)
	{
		iniciaAmbiente();
		ambiente.setId(projectionAmbienteId);
	}

	public void setProjectionAmbienteNome(String projectionAmbienteNome)
	{
		iniciaAmbiente();
		ambiente.setNome(projectionAmbienteNome);
	}

	private void iniciaFuncao() 
	{
		if(this.funcao == null)
			funcao = new Funcao();
	}
	
	public void setProjectionFuncaoId(Long projectionFuncaoId)
	{
		iniciaFuncao();
		funcao.setId(projectionFuncaoId);
	}
	
	public void setProjectionFuncaoNome(String projectionFuncaoNome)
	{
		iniciaFuncao();
		funcao.setNome(projectionFuncaoNome);
	}


	public void setProjectionEmpresaId(Long projectionEmpresaId)
	{
		if(this.empresa == null)
			empresa = new Empresa();

		empresa.setId(projectionEmpresaId);
	}

	public void setProjectionEmpresaNome(String projectionEmpresaNome)
	{
		if(this.empresa == null)
			empresa = new Empresa();

		empresa.setNome(projectionEmpresaNome);
	}
	
	public void setNomeCargo(String nomeCargo)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());

		getFaixaSalarial().setNomeCargo(nomeCargo);
	}

	public void setProjectionFaixaSalarialCargoId(Long cargoId)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		if(faixaSalarial.getCargo() == null)
			getFaixaSalarial().setCargo(new Cargo());
		
		getFaixaSalarial().getCargo().setId(cargoId);
	}

	public void setNomeArea(String nomeArea)
	{
		if(this.areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setNome(nomeArea);
	}

	public void setCargoId(Long cargoId)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		if(faixaSalarial.getCargo() == null)
			getFaixaSalarial().setCargo(new Cargo());
		faixaSalarial.getCargo().setId(cargoId);
	}

	public void setProjectionFaixaSalarialId(Long projectionFaixaSalarialId)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		faixaSalarial.setId(projectionFaixaSalarialId);
	}

	public void setProjectionFaixaSalarialNome(String projectionFaixaSalarialNome)
	{
		if(faixaSalarial == null)
			setFaixaSalarial(new FaixaSalarial());
		faixaSalarial.setNome(projectionFaixaSalarialNome);
	}

	public void setProjectionAreaId(Long projectionAreaId)
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();

		areaOrganizacional.setId(projectionAreaId);
	}

	public void setProjectionSolicitanteId(Long projectionSolicitanteId)
	{
		if(solicitante == null)
			solicitante = new Usuario();

		solicitante.setId(projectionSolicitanteId);
	}
	public void setSolicitanteLogin(String solicitanteLogin)
	{
		if(solicitante == null)
			solicitante = new Usuario();
		
		solicitante.setLogin(solicitanteLogin);
	}

	public void setSolicitanteNome(String solicitanteNome)
	{
		if(solicitante == null)
			solicitante = new Usuario();

		solicitante.setNome(solicitanteNome);
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}

	public Date getData()
	{
		return data;
	}

	public void setData(Date data)
	{
		this.data = data;
	}

	public String getDataFormatada()
	{
		return DateUtil.formataDiaMesAno(this.data);
	}

	public boolean isEncerrada()
	{
		return encerrada;
	}

	public void setEncerrada(boolean encerrada)
	{
		this.encerrada = encerrada;
	}

	public String getEscolaridade()
	{
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade)
	{
		this.escolaridade = escolaridade;
	}


	public String getInfoComplementares()
	{
		return infoComplementares;
	}

	public void setInfoComplementares(String infoComplementares)
	{
		this.infoComplementares = infoComplementares;
	}

	public int getQuantidade()
	{
		return quantidade;
	}

	public void setQuantidade(int quantidade)
	{
		this.quantidade = quantidade;
	}

	public String getRemuneracaoFormatada()
	{
		DecimalFormat format = new DecimalFormat("#,###.##");
		return format.format(remuneracao);
	}
	
	public Double getRemuneracao()
	{
		return remuneracao;
	}

	public void setRemuneracao(Double remuneracao)
	{
		this.remuneracao = remuneracao;
	}

	public String getSexo()
	{
		return sexo;
	}

	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}

	public Usuario getSolicitante()
	{
		return solicitante;
	}

	public void setSolicitante(Usuario solicitante)
	{
		this.solicitante = solicitante;
	}
	
	public String getVinculoDescricao()
	{
		Vinculo vinculoDicionario = new Vinculo(); 
		return (String)vinculoDicionario.get(vinculo);
	}

	public String getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(String vinculo)
	{
		this.vinculo = vinculo;
	}
	
	public Integer getIdadeMinima() {
		return idadeMinima;
	}

	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}

	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}

	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
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

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append("solicitante", this.solicitante);
		string.append("data", this.data);
		string.append("areaOrganizacional", this.areaOrganizacional);
		string.append("estabelecimento", this.estabelecimento);
		string.append("idadeMinima", this.idadeMinima);
		string.append("idadeMaxima", this.idadeMaxima);
		string.append("escolaridade", this.escolaridade);
		string.append("sexo", this.sexo);
		string.append("vinculo", this.vinculo);
		string.append("remuneracao", this.remuneracao);
		string.append("quantidade", this.quantidade);
		string.append("infoComplementares", this.infoComplementares);
		string.append("encerrada", this.encerrada);

		 return string.toString();
	}

	public MotivoSolicitacao getMotivoSolicitacao()
	{
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao)
	{
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public String getDescricaoFormatada()
	{
    	String codigo = getId().toString();
		String nomeDoCargo = this.getNomeDoCargoDaFaixaSalarial();
		String data = DateUtil.formataDate(this.getData(),"dd/MM/yyyy");
		String descricaoDaAreaOrg = this.getDescricaoDaAreaOrganizacional();
		
		return codigo +" - " + this.descricao + " - " + nomeDoCargo +" - "+ data +" - " + descricaoDaAreaOrg;
	}

	public String getValorPromocao()
	{
		return valorPromocao;
	}

	public void setValorPromocao(String valorPromocao)
	{
		this.valorPromocao = valorPromocao;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Date getDataPrevisaoEncerramento()
	{
		return dataPrevisaoEncerramento;
	}
	
	public String getDataPrevisaoEncerramentoFormatada()
	{
		return DateUtil.formataDiaMesAno(this.dataPrevisaoEncerramento);
	}

	public void setDataPrevisaoEncerramento(Date dataPrevisaoEncerramento)
	{
		this.dataPrevisaoEncerramento = dataPrevisaoEncerramento;
	}

	public Date getDataEncerramento()
	{
		return dataEncerramento;
	}
	
	public String getDataEncerramentoFormatada()
	{
		return DateUtil.formataDiaMesAno(this.dataEncerramento);
	}

	public void setDataEncerramento(Date dataEncerramento)
	{
		this.dataEncerramento = dataEncerramento;
	}

	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public Collection<Bairro> getBairros()
	{
		return bairros;
	}

	public void setBairros(Collection<Bairro> bairros)
	{
		this.bairros = bairros;
	}

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}

	public FaixaSalarial getFaixaSalarial()
	{
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}
	
	public String getNomeDoCargoDaFaixaSalarial()
	{
		FaixaSalarial faixa = this.getFaixaSalarial();
		if (faixa != null)
			return faixa.getNomeDoCargo();
		return "";
	}

	@NaoAudita
	public String getDescricaoDaAreaOrganizacional()
	{
		AreaOrganizacional area = getAreaOrganizacional();
		if (area != null)
			return area.getDescricao();
		return "";
	}
	
	public String getDescricaoDoMotivoSolicitacao(){
		MotivoSolicitacao motivoSolicitacao = getMotivoSolicitacao();
		if (motivoSolicitacao != null) {
			return motivoSolicitacao.getDescricao();
		}
		return "";
	}

	public Anuncio getAnuncio()
	{
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio)
	{
		this.anuncio = anuncio;
	}

	public void setProjectionAnuncioExibirModuloExterno(Boolean exibirModuloExterno)
	{
		if (this.anuncio == null)
			this.anuncio = new Anuncio();

		if (exibirModuloExterno != null)
			this.anuncio.setExibirModuloExterno(exibirModuloExterno);
	}

	public String getIdadeMinimaDesc()
	{
		return StringUtil.valueOf(this.idadeMinima);
	}

	public String getIdadeMaximaDesc()
	{
		return StringUtil.valueOf(this.idadeMaxima);
	}

	public Usuario getLiberador()
	{
		return liberador;
	}

	public void setLiberador(Usuario liberador)
	{
		this.liberador = liberador;
	}

	public void setLiberadorNome(String liberadorNome)
	{
		if (this.liberador == null)
			this.liberador = new Usuario();

		this.liberador.setNome(liberadorNome);
	}

	public void setLiberadorId(Long liberadorId)
	{
		if (this.liberador == null)
			this.liberador = new Usuario();

		this.liberador.setId(liberadorId);
	}

	public String getHorarioComercial()
	{
		return horarioComercial;
	}

	public void setHorarioComercial(String horarioComercial)
	{
		this.horarioComercial = horarioComercial;
	}

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos()
	{
		return candidatoSolicitacaos;
	}

	public void setCandidatoSolicitacaos(Collection<CandidatoSolicitacao> candidatoSolicitacaos)
	{
		this.candidatoSolicitacaos = candidatoSolicitacaos;
	}

	public String getStatusFormatado()
	{
		return StatusAprovacaoSolicitacao.getDescricao(getStatus());
	}

	public String getDataStatusFormatada()
	{
		return DateUtil.formataDiaMesAno(dataStatus);
	}

	public String getStatusFormatadoComData() {
		String status = getStatusFormatado();
		if (dataStatus != null) {
			status += "<br>" + DateUtil.formataDiaMesAno(dataStatus);
		}

		return status;
	}
	
	public String getAprovadorSolicitacao(){
		return (getLiberador() != null && getLiberador().getNome() != null && getLiberador().getNome().trim().length() > 0) ? getLiberador().getNome() : "Não informado"; 
	}

	public char getStatus()
	{
		return status;
	}

	public void setStatus(char status)
	{
		this.status = status;
	}

	public String getObservacaoLiberador()
	{
		return observacaoLiberador;
	}

	public void setObservacaoLiberador(String observacaoLiberador)
	{
		this.observacaoLiberador = observacaoLiberador;
	}

	public String getColaboradorSubstituido()
	{
		if (colaboradorSubstituido != null)
			return colaboradorSubstituido.replace("|;", ", ");

		return colaboradorSubstituido;
	}

	public void setColaboradorSubstituido(String colaboradorSubstituido)
	{
		this.colaboradorSubstituido = colaboradorSubstituido;
	}

	public Integer getQtdVagasPreenchidas()
	{
		return qtdVagasPreenchidas;
	}

	public void setQtdVagasPreenchidas(Integer qtdVagasPreenchidas)
	{
		this.qtdVagasPreenchidas = qtdVagasPreenchidas;
	}

	public Collection<SolicitacaoAvaliacao> getSolicitacaoAvaliacaos()
	{
		return solicitacaoAvaliacaos;
	}

	public void setSolicitacaoAvaliacaos(Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos)
	{
		this.solicitacaoAvaliacaos = solicitacaoAvaliacaos;
	}

	public Integer getQtdAvaliacoes()
	{
		return qtdAvaliacoes;
	}

	public void setQtdAvaliacoes(Integer qtdAvaliacoes)
	{
		this.qtdAvaliacoes = qtdAvaliacoes;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios()
	{
		return colaboradorQuestionarios;
	}

	public void setColaboradorQuestionarios(Collection<ColaboradorQuestionario> colaboradorQuestionarios)
	{
		this.colaboradorQuestionarios = colaboradorQuestionarios;
	}

	public Funcao getFuncao()
	{
		return funcao;
	}

	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}

	public Ambiente getAmbiente()
	{
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente)
	{
		this.ambiente = ambiente;
	}

	public Date getDataStatus()
	{
		return dataStatus;
	}

	public void setDataStatus(Date dataStatus)
	{
		this.dataStatus = dataStatus;
	}

	public Collection<ConfiguracaoNivelCompetencia> getConfiguracaoNivelCompetencias() 
	{
		return configuracaoNivelCompetencias;
	}

	public void setConfiguracaoNivelCompetencias(Collection<ConfiguracaoNivelCompetencia> configuracaoNivelCompetencias) 
	{
		this.configuracaoNivelCompetencias = configuracaoNivelCompetencias;
	}
	
	public Collection<PausaPreenchimentoVagas> getPausasPreenchimentoVagas() {
		return pausasPreenchimentoVagas;
	}

	public void setPausasPreenchimentoVagas(
			Collection<PausaPreenchimentoVagas> pausasPreenchimentoVagas) {
		this.pausasPreenchimentoVagas = pausasPreenchimentoVagas;
	}
	
	public void setDescricaoMotivoSolicitacao(String descricaoMotivoSolicitacao){
		if (this.motivoSolicitacao == null) {
			this.motivoSolicitacao = new MotivoSolicitacao();
		}
		this.motivoSolicitacao.setDescricao(descricaoMotivoSolicitacao);
	}

	public boolean isInvisivelParaGestor() {
		return invisivelParaGestor;
	}

	public void setInvisivelParaGestor(boolean invisivelParaGestor) {
		this.invisivelParaGestor = invisivelParaGestor;
	}
	
	public Collection<NivelCompetencia> getNivelCompetencias() {
		return nivelCompetencias;
	}

	public void setNivelCompetencias(Collection<NivelCompetencia> nivelCompetencias) {
		this.nivelCompetencias = nivelCompetencias;
	}

	public void setFaixaSalarialNome(String faixaSalarialNome) {
		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		
		this.faixaSalarial.setNome(faixaSalarialNome);
	}

	public void setEstabelecimentoNome(String estabelecimentoNome) {
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		this.estabelecimento.setNome(estabelecimentoNome);
	}
	
	public String getExperiencias() {
		return experiencias;
	}

	public void setExperiencias(String experiencias) {
		this.experiencias = experiencias;
	}
}