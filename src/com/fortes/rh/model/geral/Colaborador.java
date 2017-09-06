package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fortes.model.AbstractModel;
import com.fortes.model.type.File;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.ConfiguracaoNivelCompetenciaColaborador;
import com.fortes.rh.model.captacao.Ctps;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.Habilitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.TituloEleitoral;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.IndiceHistorico;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SalarioUtil;
import com.fortes.security.auditoria.ChaveDaAuditoria;
import com.fortes.security.auditoria.NaoAudita;

@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name="sequence", sequenceName="colaborador_sequence", allocationSize=1)
public class Colaborador extends AbstractModel implements Serializable, Cloneable
{
	@Column(length=20)
	private String matricula;
	@Column(length=70)
	private String nome;
	@Column(length=30)
	private String nomeComercial;
	@Lob
	private String observacao;
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;

	private boolean desligado = false;
	@Temporal(TemporalType.DATE)
	private Date dataDesligamento;
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacaoDesligamento;
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacaoDesligamentoAc;
	@ManyToOne
	private MotivoDemissao motivoDemissao;
	@Lob
	private String observacaoDemissao;
	@ManyToOne
	private Colaborador solicitanteDemissao;
	private Character demissaoGerouSubstituicao;
	
	@ManyToOne
	private Empresa empresa;
	@Embedded
	private Habilitacao habilitacao = new Habilitacao();
	@Embedded
	private Endereco endereco = new Endereco();
	@Embedded
	private Pessoal pessoal = new Pessoal();
	@Embedded
	private Contato contato = new Contato();
	@OneToOne
	private Usuario usuario;
	@Column(length=5)
	private String vinculo;
	@Temporal(TemporalType.DATE)
	private Date dataEncerramentoContrato;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="colaborador")
	private Collection<Dependente> dependentes;
	@Column(length=12)
	private String codigoAC;
    @Lob
    private String cursos;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<Formacao> formacao;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<Experiencia> experiencias;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorIdioma> colaboradorIdiomas;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorOcorrencia> colaboradorOcorrencia;
	@OneToMany(fetch = FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorQuestionario> colaboradorQuestionarios;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<HistoricoColaborador> historicoColaboradors;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorAfastamento> colaboradorAfastamento;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<SolicitacaoExame> solicitacaoExames;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorTurma> colaboradorTurmas;
	@OneToMany(mappedBy="colaborador")
	private Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradors;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<Cat> cats;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorPeriodoExperienciaAvaliacaos; 
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ComissaoMembro> comissaoMembros;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<OrdemDeServico> ordensDeServico;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="colaborador")
	private Collection<ColaboradorCertificacao> colaboradorCertificacaos;
	@OneToOne(optional=true)
	private Candidato candidato;
//	@OneToOne(optional=true)
//	private Solicitacao solicitacao; // solicitação de pessoal que contratou o colaborador.
	@Column(length=50)
	private String regimeRevezamento;

	private boolean naoIntegraAc = false;
	
	private boolean respondeuEntrevista = false;
	private File foto;
	
	@OneToOne(fetch=FetchType.LAZY)
	private CamposExtras camposExtras;

	@Transient
	private boolean temFuncao;
	@Transient
	private HistoricoColaborador historicoColaborador;
	@Transient
	private ReajusteColaborador reajusteColaborador;
	@Transient
	private Boolean ehProjecao;
	@Transient
	private Date avaliacaoRespondidaEm;
	@Transient
	private String datasDeAvaliacao = "";
	@Transient
	private String statusAvaliacao = "";
	@Transient
	private Long avaliacaoDesempenhoId;
	@Transient
	private String avaliacaoDesempenhoTitulo;
	@Transient
	private boolean exibePerformanceProfissional;
	@Transient
	private Boolean afastado = false;
	@Transient
	private int somaCompetencias = 0;
	@Transient
	private double percentualCompatibilidade = 0.0;
	@Transient
	private Integer tempoServico;
	@Transient
	private String tempoServicoString;
	@Transient
	private String intervaloTempoServico;
	@Transient
	private boolean membroComissaoCipa = false;
	@Transient
	private Date dataFimComissaoCipa;
	@Transient
	private Estabelecimento estabelecimento;
	@Transient
	private AreaOrganizacional areaOrganizacional;
	@Transient
	private AreaOrganizacional areaOrganizacionalMatriarca;
	@Transient
	private FaixaSalarial faixaSalarial;
	@Transient
	private Indice indice;
	@Transient
	private Double salario;
	@Transient
	private Double salarioVariavel;
	@Transient
	private Double mensalidade;
	@Temporal(TemporalType.DATE)
	private Date dataAtualizacao = new Date();
	@Transient
	private Funcao funcao;
	@Transient
	private Ambiente ambiente;
	@Transient
	private Long avaliacaoId;
	@Transient
	private String avaliacaoTitulo;
	@Transient
	private String avaliadorNome;
	@Transient
	private Date respondidaEm;
	@Transient
	private Integer pesoAvaliador;
	@Transient
	private Double performance;
	@Transient
	private Double mediaPerformance;
	@Transient
	private String titulo;
	@Transient
	private String nomeAvaliador;
	@Transient
	private Integer admitidoHa;//usado no ireport
	@Transient
	private Integer diasDeEmpresa;
	@Transient
	private Integer qtdDiasRespondeuAvExperiencia;
	@Transient
	private Long periodoExperienciaId;
	@Transient
	private String sugestaoPeriodoAcompanhamentoExperiencia;
	@Transient
	private Integer statusAcPessoal;
	@Transient
	private BigDecimal nota;
	@Transient
	private boolean manterFoto;
	@Transient
	private Double qtdAnosDeEmpresa;
	@Transient
	private Collection<Colaborador> avaliados;
	@Transient
	private Collection<FaixaSalarial> faixaSalariaisAvaliados;
	@Transient
	private ColaboradorQuestionario colaboradorQuestionario;
	@Transient
	private Double produtividade;
	@Transient
	private ColaboradorCertificacao colaboradorCertificacao;
	@Transient
	private boolean iscritoNaTurma;
	@Transient
	private String naturalidade = "";
	@Transient
	private String nacionalidade = "";

	public Colaborador()
	{
	}

	public Colaborador(Long id, String nomeComercial, String nomeEmpresa)
	{
		this.setId(id);
		this.nomeComercial = nomeComercial;
		this.empresa = new Empresa();
		this.empresa.setNome(nomeEmpresa);
	}
	
	//findComAnoDeEmpresa
	public Colaborador(Long id, Date dataAdmissao, String nomeComercial)
	{
		this.setId(id);
		this.nomeComercial = nomeComercial;
		this.dataAdmissao = dataAdmissao;
	}
	
	//findComAnoDeEmpresa
	public Colaborador(Long id, String nome, String email, Double qtdAnosDeEmpresa )
	{
		this.setId(id);
		this.nome = nome;
		this.qtdAnosDeEmpresa = qtdAnosDeEmpresa;
		this.setEmailColaborador(email);
	}

	//findDemitidosTurnover
	public Colaborador(Long id, String nome, String nomeComercial, Date dataAdmissao, Date dataDesligamento, Integer tempoServico)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.dataAdmissao = dataAdmissao;
		this.dataDesligamento = dataDesligamento;
		this.tempoServico = tempoServico;
	}
	//findDemitidosTurnoverAgrupadosPorAreaOrganizacional
	public Colaborador(Long id, String nome, String nomeComercial, Date dataAdmissao, Date dataDesligamento, Integer tempoServico, String areaOrganizacionalNome)
	{
		this(id, nome, nomeComercial, dataAdmissao, dataDesligamento, tempoServico);
		setAreaOrganizacionalNome(areaOrganizacionalNome);
	}
	//findDemitidosTurnoverAgrupadosPorCargo
	public Colaborador(Long id, String nome, String nomeComercial, Date dataAdmissao, Date dataDesligamento, Integer tempoServico, Long cardoId, String cargoNome)
	{
		this(id, nome, nomeComercial, dataAdmissao, dataDesligamento, tempoServico);		
		setCargoNomeProjection(cargoNome);
	}
	
	public Colaborador(String nome, String nomeComercial)
	{
		this.nome = nome;
		this.nomeComercial = nomeComercial;
	}
	
	public Colaborador(String nome, Long id)
	{
		setId(id);
		this.nome = nome;
	}

	public Colaborador(Long id, String nome, Date dataFimComissaoCipa)
	{
		this.setId(id);
		this.nome = nome;
		this.dataFimComissaoCipa = dataFimComissaoCipa;
	}

	//Construtor usado por findAdmitidosNoPeriodo
	public Colaborador(Long id, String nome, String matricula, Date dataAdmissao, String faixaSalarialNome, String cargoNome, Long areaOrganizacionalId, String responsavelDaArea, Integer diasDeEmpresa, String areaNome, String areaMaeNome, String estabelecimentoNome)
	{
		this.setId(id);
		this.setNome(nome);
		this.setMatricula(matricula);
		this.setDataAdmissao(dataAdmissao);
				
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaOrganizacionalId);
		this.areaOrganizacional.setResponsavelNome(responsavelDaArea);
		this.areaOrganizacional.setAreaMae(new AreaOrganizacional());
		this.areaOrganizacional.getAreaMae().setNome(areaMaeNome);
		
		this.diasDeEmpresa = diasDeEmpresa;
		
		setFaixaSalarialNomeProjection(faixaSalarialNome);
		setCargoNomeProjection(cargoNome);
		setAreaOrganizacionalNome(areaNome);
		setEstabelecimentoNomeProjection(estabelecimentoNome);
	}

	public Colaborador(Long id, String matricula, String nome, Date dataAdmissao, String responsavelDaArea, Integer diasDeEmpresa, Long areaId, String cargoNome)
	{
		this.setId(id);
		this.setMatricula(matricula);
		this.setNome(nome);
		this.setDataAdmissao(dataAdmissao);
		
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setResponsavelNome(responsavelDaArea);
		
		this.diasDeEmpresa = diasDeEmpresa;

		setCargoNomeProjection(cargoNome);
	}

	//findComAvaliacoesExperiencias
	public Colaborador(Long id, Date avaliacaoRespondidaEm, Double performance, Integer qtdDiasRespondeuAvExperiencia, Long periodoExperienciaId, String avaliacaoTitulo, String avaliadorNome)
	{
		this.setId(id);
		this.avaliacaoRespondidaEm = avaliacaoRespondidaEm;
		this.qtdDiasRespondeuAvExperiencia = qtdDiasRespondeuAvExperiencia;
		this.performance = performance;
		this.periodoExperienciaId = periodoExperienciaId;
		this.avaliacaoTitulo = avaliacaoTitulo;
		this.avaliadorNome = avaliadorNome;
	}
	
	public Colaborador(Long id, String nome, String nomeComercial, String matricula, Boolean desligado, Boolean naoIntegraAc, Double historicoSalario,
			Long areaId, String areaNome, Long faixaId, String faixaNome, Long cargoId, String cargoNome, Long grupoId, String grupoNome)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
		this.desligado = desligado;
		this.naoIntegraAc = naoIntegraAc;

		this.salario = historicoSalario;
		setAreaOrganizacionalId(areaId);
		setAreaOrganizacionalNome(areaNome);

		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();

		this.faixaSalarial.setId(faixaId);
		this.faixaSalarial.setNome(faixaNome);

		setCargoIdProjection(cargoId);
		setCargoNomeProjection(cargoNome);
		setGrupoIdProjection(grupoId);
		setGrupoNomeProjection(grupoNome);
	}
	
	//usado em Relatório de Desligamento (findColaboradores)
	public Colaborador(Long id, String nome, String matricula, Date dataAdmissao, Date dataDesligamento, String observacaoDemissao, String vinculo, Long motivoId, String motivoDemissao, String cargoNome, String faixaSalarialNome, Long estabelecimentoId, String estabelecimentoNome, Long areaId, String areaNome, Boolean turnover)
	{
		this.setId(id);
		this.nome = nome;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		this.dataDesligamento = dataDesligamento;
		this.observacaoDemissao = observacaoDemissao;
		this.vinculo = vinculo;
		
		if(this.motivoDemissao == null)
			this.motivoDemissao = new MotivoDemissao();
		this.motivoDemissao.setId(motivoId);
		this.motivoDemissao.setMotivo(motivoDemissao);
		this.motivoDemissao.setTurnover( (turnover == null ? false : turnover) );

		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setNome(faixaSalarialNome);

		if(this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());
		this.faixaSalarial.getCargo().setNome(cargoNome);
		
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
		this.estabelecimento.setNome(estabelecimentoNome);
		
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setNome(areaNome);
	}

	//usado no Relatório de Admitidos
	public Colaborador(Long id, String nome, String nomeComercial, String matricula, Date dataAdmissao, boolean desligado, String cargoNome, String faixaSalarialNome, Long estabelecimentoId, String estabelecimentoNome, String empresaNome, Long areaId, String areaNome, Long areaMaeId, String areaMaeNome, String motivoSolicitacaoDescricao, Boolean motivoSolicitacaoTurnover)
	{
		this.setId(id);
		this.nome = nome;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		this.desligado = desligado;
		
		if (nome.equals(nomeComercial))
			this.nomeComercial = "";
		else
			this.nomeComercial = nomeComercial;

		if(this.faixaSalarial == null)
			this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setNome(faixaSalarialNome);

		if(this.faixaSalarial.getCargo() == null)
			this.faixaSalarial.setCargo(new Cargo());
		this.faixaSalarial.getCargo().setNome(cargoNome);
		
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setNome(areaNome);

		if(this.areaOrganizacional.getAreaMae() == null)
			this.areaOrganizacional.setAreaMae(new AreaOrganizacional());
		this.areaOrganizacional.getAreaMae().setId(areaMaeId);
		this.areaOrganizacional.getAreaMae().setNome(areaMaeNome);

		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(estabelecimentoId);
		this.estabelecimento.setNome(estabelecimentoNome);
		
		this.estabelecimento.setProjectionEmpresaNome(empresaNome);

		if(this.historicoColaborador == null)
			this.historicoColaborador = new HistoricoColaborador();
		
		if(this.historicoColaborador.getCandidatoSolicitacao() == null)
			this.historicoColaborador.setCandidatoSolicitacao(new CandidatoSolicitacao());
		
		this.historicoColaborador.getCandidatoSolicitacao().setSolicitacao(new Solicitacao());
		this.historicoColaborador.getCandidatoSolicitacao().getSolicitacao().setProjectionMotivoSolicitacaoDescricao(motivoSolicitacaoDescricao);
		this.historicoColaborador.getCandidatoSolicitacao().getSolicitacao().setProjectionMotivoSolicitacaoTurnover(motivoSolicitacaoTurnover != null && motivoSolicitacaoTurnover);
	}
	
	public Colaborador(Long id, String nome, String nomeComercial, Long historicoAreaId, Long historicoEstabelecimentoId)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;

		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(historicoAreaId);

		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(historicoEstabelecimentoId);
	}

	public Colaborador(Long id, String nomeComercial)
	{
		this.setId(id);
		this.nomeComercial = nomeComercial;
	}

	public Colaborador(Long id, String matricula, String faixaNome, String cargoNome)
	{
		this.setId(id);
		this.matricula = matricula;
		if(this.historicoColaborador == null)
			this.historicoColaborador = new HistoricoColaborador();
		if(this.historicoColaborador.getFaixaSalarial() == null)
			this.historicoColaborador.setFaixaSalarial(new FaixaSalarial());

		this.historicoColaborador.getFaixaSalarial().setNome(faixaNome);

		if(this.historicoColaborador.getFaixaSalarial().getCargo() == null)
			this.historicoColaborador.getFaixaSalarial().setCargo(new Cargo());

		this.historicoColaborador.getFaixaSalarial().getCargo().setNome(cargoNome);
	}

	public Colaborador(Long id, String nome, String nomeComercial, Long historicoAreaId, String historicoAreaNome,Long historicoAreaMaeId, String historicoAreaMaeNome, Long historicoEstabelecimentoId, String historicoEstabelecimentoNome)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;

		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(historicoAreaId);
		this.areaOrganizacional.setNome(historicoAreaNome);

		if(this.areaOrganizacional.getAreaMae() == null)
			this.areaOrganizacional.setAreaMae(new AreaOrganizacional());
		this.areaOrganizacional.getAreaMae().setId(historicoAreaMaeId);
		this.areaOrganizacional.getAreaMae().setNome(historicoAreaMaeNome);

		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		this.estabelecimento.setId(historicoEstabelecimentoId);
		this.estabelecimento.setNome(historicoEstabelecimentoNome);
	}
	
	// usado em findByAreasOrganizacionaisEstabelecimentos
	public Colaborador(Long id, String nome, String nomeComercial, Long historicoAreaId, String historicoAreaNome,Long historicoAreaMaeId, String historicoAreaMaeNome, Long historicoEstabelecimentoId, String historicoEstabelecimentoNome, Long historicoFaixaSalarialId, String historicoFaixaSalarialNome)
	{
		this(id, nome, nomeComercial, historicoAreaId, historicoAreaNome, historicoAreaMaeId, historicoAreaMaeNome, historicoEstabelecimentoId, historicoEstabelecimentoNome);
		
		this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setId(historicoFaixaSalarialId);
		this.faixaSalarial.setNome(historicoFaixaSalarialNome);
	}

	public Colaborador(Long id, String nome, Date dataNascimento, Character sexo, String escolaridade, int somaCompetencias)
	{
		this.setId(id);
		this.nome = nome;
		setProjectionDataNascimento(dataNascimento);
		setProjectionSexo(sexo);
		setPessoalEscolaridade(escolaridade);
		this.somaCompetencias = somaCompetencias;
	}

	public Colaborador(Colaborador colaborador, HistoricoColaborador historicoColaborador, FaixaSalarial faixaSalarial,
					   FaixaSalarialHistorico faixaSalarialHistorico, Indice indiceFaixaSalarial,
					   IndiceHistorico indiceHistoricoFaixaSalarial, Indice indice, IndiceHistorico indiceHistorico)
	{
		historicoColaborador.setFaixaSalarial(faixaSalarial);

		historicoColaborador.getFaixaSalarial().setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);

		if(faixaSalarial.getFaixaSalarialHistoricoAtual() != null && faixaSalarial.getFaixaSalarialHistoricoAtual().getTipo() == TipoAplicacaoIndice.INDICE)
		{
			historicoColaborador.getFaixaSalarial().getFaixaSalarialHistoricoAtual().setIndice(indiceFaixaSalarial);
			if(historicoColaborador.getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice() != null)
				historicoColaborador.getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(indiceHistoricoFaixaSalarial);
		}

		this.historicoColaborador = historicoColaborador;
		this.historicoColaborador.setIndice(indice);

		if(this.historicoColaborador.getIndice() != null)
			this.historicoColaborador.getIndice().setIndiceHistoricoAtual(indiceHistorico);

		this.setId(colaborador.getId());
		this.matricula = colaborador.getMatricula();
		this.nome = colaborador.getNome();
		this.nomeComercial = colaborador.getNomeComercial();
		this.desligado = colaborador.isDesligado();
		this.dataDesligamento = colaborador.getDataDesligamento();
		this.observacao = colaborador.getObservacao();
		this.observacaoDemissao = colaborador.getObservacaoDemissao();
		this.dataAdmissao = colaborador.getDataAdmissao();
		this.foto = colaborador.getFoto();

		this.areaOrganizacional = historicoColaborador.getAreaOrganizacional();
		this.salario = historicoColaborador.getSalario();

		this.empresa = colaborador.getEmpresa();
		this.endereco = colaborador.getEndereco();
		this.pessoal = colaborador.getPessoal();
		this.habilitacao = colaborador.getHabilitacao();
		this.contato = colaborador.getContato();
		this.usuario = colaborador.getUsuario();
		this.vinculo = colaborador.getVinculo();
		this.dataEncerramentoContrato = colaborador.getDataEncerramentoContrato();
		this.dependentes = colaborador.getDependentes();
		this.codigoAC = colaborador.getCodigoAC();
	    this.cursos = colaborador.getCursos();
		this.colaboradorOcorrencia = colaborador.getColaboradorOcorrencia();
		this.candidato = colaborador.getCandidato();
		this.motivoDemissao = colaborador.getMotivoDemissao();
		this.regimeRevezamento = colaborador.getRegimeRevezamento();
		this.respondeuEntrevista= colaborador.isRespondeuEntrevista();
		this.funcao = historicoColaborador.getFuncao();
		this.naoIntegraAc = colaborador.isNaoIntegraAc();
		this.camposExtras = colaborador.getCamposExtras();
//		this.solicitacao = colaborador.getSolicitacao();
		this.dataSolicitacaoDesligamento = colaborador.getDataSolicitacaoDesligamento();
		this.dataSolicitacaoDesligamentoAc = colaborador.getDataSolicitacaoDesligamentoAc();
	}

	public Colaborador(String esNome, String aoNome, String reNome, String coNome, String cgNome, String fsNome)
	{
	  this.setEstabelecimentoNomeProjection(esNome);
	  this.setAreaOrganizacionalNome(aoNome);
	  this.setAreaOrganizacionalResponsavelNomeProjection(reNome);
	  this.setNome(coNome);
	  this.setFaixaSalarialNomeProjection(fsNome);
	  this.setCargoNomeProjection(cgNome);
	}
	
	//findAreaOrganizacionalByAreas
	public Colaborador(
						Long esId, String esNome, Long aoId, String aoNome, String reNome, Long coId, String coNome, String cgNome, String fsNome, Long empresaId, String empresaNome, Boolean empresaAcIntegra, 
						String nomeComercial, String matricula, String codigoAC, Boolean desligado, Date dataAdmissao, Date dataDesligamento, String vinculo, boolean naoIntegraAc,  String cursos, Date dataEncerramentoContrato,
						String estadoCivil, String escolaridade, String mae, String pai, String cpf, String pis, String rg, 
						String rgOrgaoEmissor,  String rgUfSigla,  Character deficiencia, Date rgDataExpedicao, Character sexo, 
						Date dataNascimento, String conjuge, Integer qtdFilhos, String ctpsNumero, String ctpsSerie, Character ctpsDv, String numeroHab, Date emissao, 
						Date vencimento, String categoria, String logradouro, String complemento, String numero, 
						String bairro, String cep, String email, String dddCelular, String foneCelular, String dddFoneFixo, String foneFixo, String funcaoNome, String ambienteNome,  
						String cidadeNome, String ufSigla, Date afastamentoInicio, Date afastamentoFim, String candIndicadoPor,
						Double salario, int tipoSalario, Double quantidadeIndice, Indice indice, FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, IndiceHistorico indiceHistorico, Indice faixaIndice, IndiceHistorico faixaHistoricoIndice,  
						String texto1,  String texto2,  String texto3,  String texto4,  String texto5,  String texto6,  String texto7,  String texto8,  String textolongo1,  String textolongo2,
						Date data1,  Date data2,  Date data3,  Double valor1,  Double valor2,  Integer numero1  
					   ) 
	{
		
		this(esId, esNome, aoId, aoNome, reNome, coId, coNome, cgNome, fsNome, empresaId, empresaNome, empresaAcIntegra, nomeComercial,
				matricula, codigoAC, desligado, dataAdmissao, dataDesligamento, vinculo, naoIntegraAc, cursos, dataEncerramentoContrato, estadoCivil, escolaridade, mae, pai,
				cpf, pis, rg, rgOrgaoEmissor, rgUfSigla, deficiencia, rgDataExpedicao, sexo, dataNascimento, conjuge, qtdFilhos, ctpsNumero, ctpsSerie,
				ctpsDv, numeroHab, emissao, vencimento, categoria, logradouro, complemento, numero, bairro, cep, email, dddCelular, foneCelular, dddFoneFixo, foneFixo, 
				funcaoNome, ambienteNome, cidadeNome, ufSigla, afastamentoInicio, afastamentoFim, candIndicadoPor,
				salario, tipoSalario, quantidadeIndice, indice, faixaSalarial, faixaSalarialHistorico, indiceHistorico, faixaIndice, faixaHistoricoIndice);
		
		if (this.camposExtras == null)
			this.camposExtras = new CamposExtras();
		
		this.getCamposExtras().setTexto1(texto1);
		this.getCamposExtras().setTexto2(texto2);
		this.getCamposExtras().setTexto3(texto3);
		this.getCamposExtras().setTexto4(texto4);
		this.getCamposExtras().setTexto5(texto5);
		this.getCamposExtras().setTexto6(texto6);
		this.getCamposExtras().setTexto7(texto7);
		this.getCamposExtras().setTexto8(texto8);
		this.getCamposExtras().setTextolongo1(textolongo1);
		this.getCamposExtras().setTextolongo2(textolongo2);
		this.getCamposExtras().setData1(data1);
		this.getCamposExtras().setData2(data2);
		this.getCamposExtras().setData3(data3);
		this.getCamposExtras().setValor1(valor1);
		this.getCamposExtras().setValor2(valor2);
		this.getCamposExtras().setNumero1(numero1);
	}

	//findAreaOrganizacionalByAreas
	public Colaborador(
			Long esId, String esNome, Long aoId, String aoNome, String reNome, Long coId, String coNome, String cgNome, String fsNome, Long empresaId, String empresaNome, Boolean empresaAcIntegra, 
			String nomeComercial,  String matricula, String codigoAC, Boolean desligado, Date dataAdmissao, Date dataDesligamento, String vinculo, boolean naoIntegraAc, String cursos, Date dataEncerramentoContrato, 
			String estadoCivil, String escolaridade, String mae, String pai, String cpf, String pis, String rg, 
			String rgOrgaoEmissor, String rgUfSigla, Character deficiencia, Date rgDataExpedicao, Character sexo, 
			Date dataNascimento, String conjuge, Integer qtdFilhos, String ctpsNumero, String ctpsSerie, Character ctpsDv, String numeroHab, Date emissao, 
			Date vencimento, String categoria, String logradouro, String complemento, String numero, 
			String bairro, String cep, String email, String dddCelular, String foneCelular, String dddFoneFixo, String foneFixo, String funcaoNome, String ambienteNome, 
			String cidadeNome, String ufSigla, Date afastamentoInicio, Date afastamentoFim, String candIndicadoPor,
			Double salario, int tipoSalario, Double quantidadeIndice, Indice indice, FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, IndiceHistorico indiceHistorico, Indice faixaIndice, IndiceHistorico faixaHistoricoIndice
			) 
	{
		this(coId, coNome, esId, esNome, aoId, aoNome, reNome, cgNome, fsNome,
				empresaId, empresaNome, empresaAcIntegra, nomeComercial,
				matricula, codigoAC, desligado, dataAdmissao, dataDesligamento, vinculo,
				naoIntegraAc, cursos, dataEncerramentoContrato, estadoCivil, escolaridade, mae, pai, cpf,
				pis, rg, rgOrgaoEmissor, deficiencia, rgDataExpedicao, sexo,
				dataNascimento, conjuge, qtdFilhos, ctpsNumero, ctpsSerie,
				ctpsDv, numeroHab, emissao, vencimento, categoria, logradouro,
				complemento, numero, bairro, cep, email, dddCelular, foneCelular, dddFoneFixo, foneFixo,
				cidadeNome, ufSigla, afastamentoInicio, afastamentoFim,
				candIndicadoPor);
		
		inicializaFuncao();
		this.funcao.setNome(funcaoNome);

		if (this.ambiente == null)
			this.ambiente =  new Ambiente();
		this.ambiente.setNome(ambienteNome);
		
		if (this.historicoColaborador == null)
			historicoColaborador = new HistoricoColaborador();
		
		historicoColaborador.setSalario(salario);
		historicoColaborador.setTipoSalario(tipoSalario);
		historicoColaborador.setQuantidadeIndice(quantidadeIndice);
		
		historicoColaborador.setIndice( indice != null ? indice : new Indice() );
		historicoColaborador.getIndice().setIndiceHistoricoAtual(indiceHistorico);

		historicoColaborador.setFaixaSalarial( faixaSalarial != null ? faixaSalarial : new FaixaSalarial() );
		historicoColaborador.getFaixaSalarial().setFaixaSalarialHistoricoAtual(faixaSalarialHistorico);
		if (faixaSalarialHistorico != null)
			historicoColaborador.getFaixaSalarial().getFaixaSalarialHistoricoAtual().setIndice(faixaIndice);
		if(faixaIndice != null)
			historicoColaborador.getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(faixaHistoricoIndice);
		
		if(this.getPessoal() == null)
			this.setPessoal(new Pessoal());
		
		this.getPessoal().setRgUfSigla(rgUfSigla);
	}

	public Colaborador(Long coId, String coNome, Long esId, String esNome, Long aoId, String aoNome, String reNome, String cgNome,
			String fsNome, Long empresaId, String empresaNome, Boolean empresaAcIntegra, String nomeComercial, String matricula,
			String codigoAC, Boolean desligado, Date dataAdmissao, Date dataDesligamento, String vinculo, boolean naoIntegraAc, String cursos, Date dataEncerramentoContrato,
			String estadoCivil, String escolaridade, String mae, String pai, String cpf, String pis, String rg, String rgOrgaoEmissor,
			Character deficiencia, Date rgDataExpedicao, Character sexo, Date dataNascimento, String conjuge, Integer qtdFilhos,
			String ctpsNumero, String ctpsSerie, Character ctpsDv, String numeroHab, Date emissao, Date vencimento, String categoria,
			String logradouro, String complemento, String numero, String bairro, String cep, String email,String dddCelular, String foneCelular, String dddFoneFixo,
			String foneFixo, String cidadeNome, String ufSigla, Date afastamentoInicio, Date afastamentoFim, String candIndicadoPor) 
	{
		
		this.setEstabelecimentoNomeProjection(esNome);
		this.setEstabelecimentoIdProjection(esId);
		this.setAreaOrganizacionalId(aoId);
		this.setAreaOrganizacionalNome(aoNome);
		this.setAreaOrganizacionalResponsavelNomeProjection(reNome);
		this.setId(coId);
		this.setNome(coNome);
		this.setFaixaSalarialNomeProjection(fsNome);
		this.setCargoNomeProjection(cgNome);
		this.setEmpresaId(empresaId);
		this.setEmpresaNome(empresaNome);
		this.setEmpresaAcIntegra(empresaAcIntegra);
		this.setDataEncerramentoContrato(dataEncerramentoContrato);
		
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
		this.codigoAC = codigoAC;
		this.dataAdmissao = dataAdmissao;
		this.desligado = BooleanUtils.toBoolean(desligado);
		this.tempoServico = DateUtil.mesesEntreDatas(dataAdmissao, new Date());
		this.dataDesligamento = dataDesligamento;
		this.vinculo = vinculo;
		this.naoIntegraAc = naoIntegraAc;
		this.afastado = isAfastado(afastamentoInicio, afastamentoFim);
		this.cursos = cursos;
		
		if (this.pessoal == null)
			this.pessoal = new Pessoal();
		
		this.getPessoal().setEstadoCivil(estadoCivil);
		this.getPessoal().setEscolaridade(escolaridade);
		this.getPessoal().setMae(mae);
		this.getPessoal().setPai(pai);
		this.getPessoal().setCpf(cpf);
		this.getPessoal().setPis(pis);
		this.getPessoal().setRg(rg);
		this.getPessoal().setRgOrgaoEmissor(rgOrgaoEmissor);
		this.getPessoal().setDeficiencia(deficiencia);
		this.getPessoal().setRgDataExpedicao(rgDataExpedicao);
		this.getPessoal().setSexo(sexo);
		this.getPessoal().setDataNascimento(dataNascimento);
		this.getPessoal().setConjuge(conjuge);
		this.getPessoal().setQtdFilhos(qtdFilhos);
		
		if (this.pessoal.getCtps() == null)
			this.pessoal.setCtps(new Ctps());
		
		this.getPessoal().getCtps().setCtpsNumero(ctpsNumero);
		this.getPessoal().getCtps().setCtpsSerie(ctpsSerie);
		this.getPessoal().getCtps().setCtpsDv(ctpsDv);
				
		if (this.habilitacao == null)
			this.habilitacao = new Habilitacao();
		
		this.getHabilitacao().setNumeroHab(numeroHab);
		this.getHabilitacao().setEmissao(emissao);
		this.getHabilitacao().setVencimento(vencimento);
		this.getHabilitacao().setCategoria(categoria);
		
		if (this.endereco == null)
			this.endereco = new Endereco();

		Cidade cidade = new Cidade();
		cidade.setNome(cidadeNome);
		Estado uf = new Estado();
		uf.setSigla(ufSigla);
		
		this.getEndereco().setLogradouro(logradouro);
		this.getEndereco().setComplemento(complemento);
		this.getEndereco().setNumero(numero);
		this.getEndereco().setBairro(bairro);
		this.getEndereco().setCep(cep);
		this.getEndereco().setCidade(cidade);
		this.getEndereco().setUf(uf);
		
		if (contato == null)
			this.contato = new Contato();
		
		this.getContato().setEmail(email);
		this.getContato().setFoneCelular(foneCelular);
		this.getContato().setFoneFixo(foneFixo);
		this.getContato().setDddCelular(dddCelular);
		this.getContato().setDdd(dddFoneFixo);
		
		if (candidato == null)
			this.candidato = new Candidato();
		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());
		
		candidato.getPessoal().setIndicadoPor(candIndicadoPor);
	}

	private boolean isAfastado(Date afastamentoInicio, Date afastamentoFim) 
	{
		Date hoje = new Date();
		return afastamentoInicio != null && afastamentoFim != null && hoje.getTime() >= afastamentoInicio.getTime() && hoje.getTime() <= afastamentoFim.getTime() 
				|| (afastamentoInicio != null && afastamentoFim == null && DateUtil.formataDiaMesAno(hoje).equals(DateUtil.formataDiaMesAno(afastamentoInicio)));
	}

	//findProjecaoSalarialByTabelaReajusteColaborador e findProjecaoSalarialByHistoricoColaborador
	public Colaborador(Long id, String nome, Long estabelecimentoId, String estabelecimentoNome, Long areaOrganizacionalId, String areaOrganizacionalNome, String faixaSalarialNome, String cargoNome,
			           Integer historicoColaboradorTipoSalario, Double historicoColaboradorSalario, Double historicoColaboradorQuantidadeIndice, Integer historicoColaboradorStatus,
			           Double historicoColaboradorIndiceHistoricoValor, Integer faixaSalarialHistoricoTipo, Double faixaSalarialHistoricoValor,
			           Double faixaSalarialHistoricoQuantidade, Integer faixaSalarialHistoricoStatus, Double faixaSalarialHistoricoIndiceHistoricoValor, Long areaMaeId)
	{
		this.setId(id);
		this.setNome(nome);
		this.setEstabelecimentoIdProjection(estabelecimentoId);
		this.setEstabelecimentoNomeProjection(estabelecimentoNome);
		this.setAreaOrganizacionalId(areaOrganizacionalId);
		this.setAreaOrganizacionalNome(areaOrganizacionalNome);
		this.areaOrganizacional.setAreaMaeId(areaMaeId);
		this.setFaixaSalarialNomeProjection(faixaSalarialNome);
		this.setCargoNomeProjection(cargoNome);
		this.setHistoricoColaboradorTipoSalarioProjection(historicoColaboradorTipoSalario);
		this.setHistoricoColaboradorSalarioProjection(historicoColaboradorSalario);
		this.setHistoricoColaboradorQuantidadeIndiceProjection(historicoColaboradorQuantidadeIndice);
		this.setHistoricoColaboradorStatusProjection(historicoColaboradorStatus);
		this.setHistoricoColaboradorIndiceHistoricoValorProjection(historicoColaboradorIndiceHistoricoValor);
		this.setFaixaSalarialHistoricoTipoProjection(faixaSalarialHistoricoTipo);
		this.setFaixaSalarialHistoricoValorProjection(faixaSalarialHistoricoValor);
		this.setFaixaSalarialHistoricoQuantidadeProjection(faixaSalarialHistoricoQuantidade);
		this.setFaixaSalarialHistoricoStatusProjection(faixaSalarialHistoricoStatus);
		this.setFaixaSalarialHistoricoIndiceHistoricoValorProjection(faixaSalarialHistoricoIndiceHistoricoValor);
	}
	
	//usado por findHistoricoByColaboradors
	public Colaborador(Long id, String nome, String nomeComercial, Long areaOrganizacionalId, String areaOrganizacionalNome, String faixaSalarialNome, String cargoNome)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.setAreaOrganizacionalId(areaOrganizacionalId);
		this.setAreaOrganizacionalNome(areaOrganizacionalNome);
		this.setFaixaSalarialNomeProjection(faixaSalarialNome);
		this.setCargoNomeProjection(cargoNome);
	}
	
	public Colaborador(Long id, String nome, String nomeComercial, Long empresaId, Long areaOrganizacionalId, String areaOrganizacionalNome, Long faixaSalarialId, String faixaSalarialNome, Long cargoId, String cargoNome)
	{
		this(id, nome, nomeComercial, areaOrganizacionalId, areaOrganizacionalNome, faixaSalarialNome, cargoNome);
		this.setEmpresaId(empresaId);
		this.setFaixaSalarialIdProjection(faixaSalarialId);
		this.setCargoIdProjection(cargoId);
	}
	
	//findParticipantes
	public Colaborador(Long id, String nome, String nomeComercial, Long empresaId, Long areaOrganizacionalId, String areaOrganizacionalNome, Long faixaSalarialId, String faixaSalarialNome, Long cargoId, String cargoNome, Double produtividade)
	{
		this(id, nome, nomeComercial, areaOrganizacionalId, areaOrganizacionalNome, faixaSalarialNome, cargoNome);
		this.setEmpresaId(empresaId);
		this.setFaixaSalarialIdProjection(faixaSalarialId);
		this.setCargoIdProjection(cargoId);
		this.produtividade = produtividade;
	}
	
	//usado por findByIdHistoricoAtual 
	public Colaborador(Long id, String nome, String nomeComercial, String matricula, Long areaOrganizacionalId, String areaOrganizacionalNome, String faixaSalarialNome, String cargoNome, 
			Date dataAdmissao, String estabelecimentoNome, String ddd, String foneFixo, String celular, String dddCelular)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
		this.setAreaOrganizacionalId(areaOrganizacionalId);
		this.setAreaOrganizacionalNome(areaOrganizacionalNome);
		this.setFaixaSalarialNomeProjection(faixaSalarialNome);
		this.setCargoNomeProjection(cargoNome);
		this.setEstabelecimentoNomeProjection(estabelecimentoNome);
		this.setDataAdmissao(dataAdmissao);
		this.setContatoDdd(ddd);
		this.setContatoFoneFixo(foneFixo);
		this.setContatoCelular(celular);
		this.setContatoDddCelular(dddCelular);
	}
	
	//Construtor usado pela consulta de aniversariantes
	public Colaborador(Date dataNascimento, Long colaboradorId, String colaboradorMatricula, String colaboradorNome, String colaboradorNomeComercial, String cargoNome, String faixaSalarialNome, String areaOrganizacionalNome, String estabelecimentoNome, Long areaOrganizacionalId, Endereco endereco)
	{
		setProjectionDataNascimento(dataNascimento);
		setId(colaboradorId);
		this.matricula = colaboradorMatricula;
		this.nome = colaboradorNome;
		this.nomeComercial = colaboradorNomeComercial;
		setCargoNomeProjection(cargoNome);
		setFaixaSalarialNomeProjection(faixaSalarialNome);
		setEstabelecimentoNomeProjection(estabelecimentoNome);
		setAreaOrganizacionalId(areaOrganizacionalId);
		setAreaOrganizacionalNome(areaOrganizacionalNome);
		setEndereco(endereco);
	}

	//Construtor usado em findByIdDadosBasicos
	public Colaborador (Long id, String nome, String nomeComercial, String matricula, Date dataAdmissao, Date dataDesligamento, Integer statusRetornoAC, AreaOrganizacional areaOrganizacional, Cargo cargo, FaixaSalarial faixaSalarial, Empresa empresa, Long estabelecimentoId, String funcaoNome)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		this.dataDesligamento = dataDesligamento;
		setAreaOrganizacional(areaOrganizacional);
		setEstabelecimentoIdProjection(estabelecimentoId);
		setEmpresa(empresa);

		if(this.faixaSalarial == null)
			setFaixaSalarial(faixaSalarial);
			
		this.getFaixaSalarial().setCargo(cargo);

		inicializaHistoricoColaborador();
		this.historicoColaborador.setStatus(statusRetornoAC);
		
		this.funcao = new Funcao(null, funcaoNome);
	}

	public Colaborador(String nome, String nomeComercial, Long id, Long empresaId, String empresaNome)
	{
		super();
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.setId(id);
		setEmpresaId(empresaId);
		setEmpresaNome(empresaNome);
	}
	
	public Colaborador(String nome, String nomeComercial, Long id, Long empresaId, String empresaNome, Long areaId)
	{
		this(nome, nomeComercial, id, empresaId, empresaNome);
		if(this.areaOrganizacional == null)
			this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setId(areaId);
	}

	public Colaborador(String nome, String nomeComercial, String avaliacaoTitulo, Date respondidaEm, Double performance, String titulo)
	{
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.avaliacaoTitulo = avaliacaoTitulo;
		this.respondidaEm = respondidaEm;
		this.performance = performance;
		this.titulo = titulo;
	}

	//findColabPeriodoExperiencia
	public Colaborador(Long id, String nome, String nomeComercial, String nomeAvaliador, Date respondidaEm, Integer pesoAvaliador, Double performance, Double performanceNivelCompetencia, boolean anonima, Long avaliacaoDesempenhoId, String avaliacaoDesempenhoTitulo, String nomeEmpresa, String areaNome)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		if(anonima)
			this.nomeAvaliador = " - ";
		else
			this.nomeAvaliador = nomeAvaliador;
		
		this.respondidaEm = respondidaEm;
		this.pesoAvaliador = pesoAvaliador;
		this.performance = performance + (performanceNivelCompetencia == null ? 0 : performanceNivelCompetencia);
		this.avaliacaoDesempenhoTitulo = avaliacaoDesempenhoTitulo;
		this.avaliacaoDesempenhoId = avaliacaoDesempenhoId;//para o relatório em XLS
		this.areaOrganizacional = new AreaOrganizacional(areaNome);

		empresa = new Empresa();
		this.empresa.setNome(nomeEmpresa);
	}
	
	public Colaborador(Long id, String nome,String nomeComercial,  boolean desligado)
	{
		super();
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.desligado = desligado;
	}
	
	// findColaboradoresMesmoCpf
	public Colaborador(Long id, String nome, String cpf, Date dataDeligamento)
	{
		super();
		this.setId(id);
		this.setNome(nome);
		this.setPessoalCpf(cpf);
		this.setDataDesligamento(dataDeligamento);
	}

	public Colaborador(Long id, String nomeComercial, Funcao funcao, Ambiente ambiente)
	{
		super();
		this.setId(id);
		this.nomeComercial = nomeComercial;
		this.funcao = funcao;
		this.ambiente = ambiente;
	}
	
	public Colaborador(String nome, String email, Long id)
	{
		super();
		this.nome = nome;
		this.contato.setEmail(email);
		this.setId(id);
	}
	
	//Construtor usado por findAdmitidosHaDias
	public Colaborador(Long id, String nome, String nomeComercial, String matricula, String cargoNome, String faixaNome, Long areaId, String areaNome, Long areaMaeId, String areaMaeNome, Long empresaId, String estabelecimentoNome, Long avaliacaoId, String empresaNome, String funcaoNome)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
		
		this.empresa = new Empresa();
		this.empresa.setId(empresaId);
		this.empresa.setNome(empresaNome);
		
		this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setNome(faixaNome);
		this.faixaSalarial.setNomeCargo(cargoNome);
		
		this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setNome(areaNome);
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setAreaMaeId(areaMaeId);
		this.areaOrganizacional.setAreaMaeNome(areaMaeNome);
		
		this.funcao = new Funcao();
		this.funcao.setNome(funcaoNome);
		
		this.setEstabelecimentoNomeProjection(estabelecimentoNome);
		this.setAvaliacaoId(avaliacaoId);
	}
	
	public Colaborador(Long id, String nome, String nomeComercial, String cargoNome, String faixaNome, Long areaId, String areaNome, Long areaMaeId, String areaMaeNome, Long empresaId, Date admissao)
	{
		this.setId(id);
		this.nome = nome;
		this.dataAdmissao = admissao;
		this.nomeComercial = nomeComercial;
		this.empresa = new Empresa();
		this.empresa.setId(empresaId);
		this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setNome(faixaNome);
		this.faixaSalarial.setNomeCargo(cargoNome);
		this.areaOrganizacional = new AreaOrganizacional();
		this.areaOrganizacional.setNome(areaNome);
		this.areaOrganizacional.setId(areaId);
		this.areaOrganizacional.setAreaMaeId(areaMaeId);
		this.areaOrganizacional.setAreaMaeNome(areaMaeNome);
	}

	//findAniversariantesPorTempoDeEmpresa
	public Colaborador(Long id, String nome, String nomeComercial, String matricula, Long empresaId, String empresaNome, Long estabelecimentoId, String estabelecimentoNome, 
			String cargoNome, String faixaNome, Long areaId, String areaNome, Date dataAdmissao, Integer tempoDeEmpresa)
	{
		this.setId(id);
		this.setNome(nome);
		this.setNomeComercial(nomeComercial);
		this.setMatricula(matricula);
		this.setEmpresaId(empresaId);
		this.setEmpresaNome(empresaNome);
		this.setEstabelecimentoIdProjection(estabelecimentoId);
		this.setEstabelecimentoNomeProjection(estabelecimentoNome);
		this.setFaixaSalarialNomeProjection(faixaNome);
		this.faixaSalarial.setNomeCargo(cargoNome);
		this.setAreaOrganizacionalId(areaId);
		this.setAreaOrganizacionalNome(areaNome);
		this.getAreaOrganizacional().setEmpresa(empresa);
		this.setDataAdmissao(dataAdmissao);
		this.setTempoServico(tempoDeEmpresa);
	}	
	
	public Colaborador(String nome) {
		this.nome = nome;
	}

	private void setFaixaSalarialHistoricoStatusProjection(Integer faixaSalarialHistoricoStatus)
	{
		inicializaHistoricoColaborador();

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setStatus(faixaSalarialHistoricoStatus);
	}
	
	private void setHistoricoColaboradorStatusProjection(Integer historicoColaboradorStatus)
	{
		inicializaHistoricoColaborador();

		this.getHistoricoColaborador().setStatus(historicoColaboradorStatus);
	}

	private void setFaixaSalarialHistoricoIndiceHistoricoValorProjection(Double faixaSalarialHistoricoIndiceHistoricoValor)
	{
		inicializaHistoricoColaborador();

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice() == null)
			this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setIndice(new Indice());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().getIndice().getIndiceHistoricoAtual().setValor(faixaSalarialHistoricoIndiceHistoricoValor);
	}

	private void setFaixaSalarialHistoricoQuantidadeProjection(Double faixaSalarialHistoricoQuantidade)
	{
		inicializaHistoricoColaborador();

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setQuantidade(faixaSalarialHistoricoQuantidade);
	}

	private void setFaixaSalarialHistoricoValorProjection(Double faixaSalarialHistoricoValor)
	{
		inicializaHistoricoColaborador();

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setValor(faixaSalarialHistoricoValor);
	}

	private void setFaixaSalarialHistoricoTipoProjection(Integer faixaSalarialHistoricoTipo)
	{
		inicializaHistoricoColaborador();

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setTipo(faixaSalarialHistoricoTipo);
	}

	private void setHistoricoColaboradorIndiceHistoricoValorProjection(Double historicoColaboradorIndiceHistoricoValor)
	{
		inicializaHistoricoColaborador();

		if(this.getHistoricoColaborador().getIndice() == null)
			this.getHistoricoColaborador().setIndice(new Indice());

		if(this.getHistoricoColaborador().getIndice().getIndiceHistoricoAtual() == null)
			this.getHistoricoColaborador().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.getHistoricoColaborador().getIndice().getIndiceHistoricoAtual().setValor(historicoColaboradorIndiceHistoricoValor);
	}

	private void setHistoricoColaboradorQuantidadeIndiceProjection(Double historicoColaboradorQuantidadeIndice)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setQuantidadeIndice(historicoColaboradorQuantidadeIndice);
	}

	private void setHistoricoColaboradorSalarioProjection(Double historicoColaboradorSalario)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setSalario(historicoColaboradorSalario);
	}

	private void setHistoricoColaboradorTipoSalarioProjection(Integer historicoColaboradorTipoSalario)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setTipoSalario(historicoColaboradorTipoSalario);
	}

	private void inicializaHistoricoColaborador() 
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());
	}
	
	public void setHistoricoColaboradorDataProjection(Date historicoColaboradorData)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setData(historicoColaboradorData);
	}
	
	public void setHistoricoColaboradorIdProjection(Long historicoColaboradorId)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setId(historicoColaboradorId);
	}
	
	public void setHistoricoColaboradorGfipProjection(String historicoColaboradorGfip)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setGfip(historicoColaboradorGfip);
	}
	
	public void setHistoricoColaboradorMotivoProjection(String historicoColaboradorMotivo)
	{
		inicializaHistoricoColaborador();
		this.getHistoricoColaborador().setMotivo(historicoColaboradorMotivo);
	}
	
	public void setProjectionCamposExtrasId(Long projectionCamposExtrasId)
 	{
		if(this.camposExtras == null)
			this.camposExtras = new CamposExtras();
		
		this.camposExtras.setId(projectionCamposExtrasId);
	 }
	
	private void setGrupoIdProjection(Long grupoId)
	{
		iniciaFaixaSalarial();

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		if(this.getFaixaSalarial().getCargo().getGrupoOcupacional() == null)
			this.getFaixaSalarial().getCargo().setGrupoOcupacional(new GrupoOcupacional());

		this.getFaixaSalarial().getCargo().getGrupoOcupacional().setId(grupoId);
	}

	private void setGrupoNomeProjection(String grupoNome)
	{
		iniciaFaixaSalarial();

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		if(this.getFaixaSalarial().getCargo().getGrupoOcupacional() == null)
			this.getFaixaSalarial().getCargo().setGrupoOcupacional(new GrupoOcupacional());

		this.getFaixaSalarial().getCargo().getGrupoOcupacional().setNome(grupoNome);
	}

	public void setCargoIdProjection(Long cargoId)
	{
		iniciaFaixaSalarial();

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		this.getFaixaSalarial().getCargo().setId(cargoId);
	}

	public void setCargoNomeProjection(String cargoNome)
	{
		iniciaFaixaSalarial();

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		this.getFaixaSalarial().getCargo().setNome(cargoNome);
	}

	public void setFaixaSalarialNomeProjection(String faixaSalarialNome)
	{
		iniciaFaixaSalarial();
		this.getFaixaSalarial().setNome(faixaSalarialNome);
	}

	public void setFaixaSalarialIdProjection(Long faixaSalarialId)
	{
		iniciaFaixaSalarial();
		this.getFaixaSalarial().setId(faixaSalarialId);
	}

	public void setFaixaSalarialCodigoACProjection(String codigoAC)
	{
		iniciaFaixaSalarial();
		this.getFaixaSalarial().setCodigoAC(codigoAC);
	}

	private void iniciaFaixaSalarial() 
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());
	}

	public void setIndiceCodigoAC(String codigoAC)
	{
		if(this.indice == null)
			this.indice = new Indice();
		
		this.indice.setCodigoAC(codigoAC);
	}
	
	public void setEstabelecimentoNomeProjection(String estabelecimentoNome)
	{
		iniciaEstabelecimento();
		this.getEstabelecimento().setNome(estabelecimentoNome);
	}
	
	public void setEstabelecimentoComplementoCNPJ(String complementoCNPJ)
	{
		iniciaEstabelecimento();
		this.getEstabelecimento().setComplementoCnpj(complementoCNPJ);
	}
	
	public void setEstabelecimentoCodigoACProjection(String codigoAC)
	{
		iniciaEstabelecimento();
		this.getEstabelecimento().setCodigoAC(codigoAC);
	}

	private void iniciaEstabelecimento() 
	{
		if(this.estabelecimento == null)
			this.setEstabelecimento(new Estabelecimento());
	}

	public void setEstabelecimentoIdProjection(Long estabelecimentoId)
	{
		iniciaEstabelecimento();
		this.getEstabelecimento().setId(estabelecimentoId);
	}

	private void setAreaOrganizacionalResponsavelNomeProjection(String reNome)
	{
		if(this.areaOrganizacional == null)
			this.setAreaOrganizacional(new AreaOrganizacional());

		if(this.getAreaOrganizacional().getResponsavel() == null)
			this.getAreaOrganizacional().setResponsavel(new Colaborador());

		this.areaOrganizacional.getResponsavel().setNome(reNome);
	}

	//utilizado no daoHibernate
	private void inicializaCamposExtras() 
	{
		if (this.camposExtras == null)
			this.camposExtras = new CamposExtras();
	}

	
	public void setProjectionTexto1(String texto1)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto1(texto1);
	}
	
	public void setProjectionTexto2(String texto2)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto2(texto2);
	}
	
	public void setProjectionTexto3(String texto3)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto3(texto3);
	}
	
	public void setProjectionTexto4(String texto4)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto4(texto4);
	}
	
	public void setProjectionTexto5(String texto5)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto5(texto5);
	}
	
	public void setProjectionTexto6(String texto6)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto6(texto6);
	}
	
	public void setProjectionTexto7(String texto7)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto7(texto7);
	}
	
	public void setProjectionTexto8(String texto8)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto8(texto8);
	}
	
	public void setProjectionTextolongo1(String textolongo1)
	{
		inicializaCamposExtras();
		this.camposExtras.setTextolongo1(textolongo1);
	}
	
	public void setProjectionTextolongo2(String textolongo2)
	{
		inicializaCamposExtras();
		this.camposExtras.setTextolongo2(textolongo2);
	}
	
	public void setProjectionData1(Date data1)
	{
		inicializaCamposExtras();
		this.camposExtras.setData1(data1);
	}
	
	public void setProjectionData2(Date data2)
	{
		inicializaCamposExtras();
		this.camposExtras.setData2(data2);
	}
	
	public void setProjectionData3(Date data3)
	{
		inicializaCamposExtras();
		this.camposExtras.setData3(data3);
	}
	
	public void setProjectionValor1(Double valor1)
	{
		inicializaCamposExtras();
		this.camposExtras.setValor1(valor1);
	}
	
	public void setProjectionValor2(Double valor2)
	{
		inicializaCamposExtras();
		this.camposExtras.setValor2(valor2);
	}
	
	public void setProjectionNumero1(Integer numero1)
	{
		inicializaCamposExtras();
		this.camposExtras.setNumero1(numero1);
	}
		
	public void setCandidatoId(Long candidatoId)
	{
		if (this.candidato == null)
			this.candidato = new Candidato();
		this.candidato.setId(candidatoId);
	}
	
	public void setCandidatoNome(String candidatoNome)
	{
		if (this.candidato == null)
			this.candidato = new Candidato();
		this.candidato.setNome(candidatoNome);
	}

	public void setProjectionRg(String rg)
	{
		inicializaPessoal();
		pessoal.setRg(rg);
	}

	public void setProjectionRgOrgaoEmissor(String rgOrgaoEmissor)
	{
		inicializaPessoal();
		pessoal.setRgOrgaoEmissor(rgOrgaoEmissor);
	}

	public void setProjectionRgDataExpedicao(Date rgDataExpedicao)
	{
		inicializaPessoal();
		pessoal.setRgDataExpedicao(rgDataExpedicao);
	}

	public void setProjectionRgUfSigla(String rgUfSigla)
	{
		inicializaPessoal();
		if (this.pessoal.getRgUf() == null)
			this.pessoal.setRgUf(new Estado());
		this.pessoal.getRgUf().setSigla(rgUfSigla);
	}

	public void setProjectionRgUfId(Long rgUfId)
	{
		inicializaPessoal();
		if (this.pessoal.getRgUf() == null)
			this.pessoal.setRgUf(new Estado());
		this.pessoal.getRgUf().setId(rgUfId);
	}

	public void setProjectionCtpsUfId(Long ctpsId)
	{
		inicializaPessoal();
		if (this.pessoal.getCtps().getCtpsUf() == null)
			this.pessoal.getCtps().setCtpsUf(new Estado());
		this.pessoal.getCtps().getCtpsUf().setId(ctpsId);
	}

	public void setProjectionCtpsUfSigla(String ctpsUf)
	{
		inicializaPessoal();
		if (this.pessoal.getCtps().getCtpsUf() == null)
			this.pessoal.getCtps().setCtpsUf(new Estado());
		this.pessoal.getCtps().getCtpsUf().setSigla(ctpsUf);
	}

	public void setProjectionCtpsNumero(String ctpsNumero)
	{
		inicializaPessoal();
		this.pessoal.getCtps().setCtpsNumero(ctpsNumero);
	}

	public void setProjectionCtpsSerie(String ctpsSerie)
	{
		inicializaPessoal();
		this.pessoal.getCtps().setCtpsSerie(ctpsSerie);
	}

	public void setProjectionCtpsDv(Character ctpsDv)
	{
		inicializaPessoal();
		this.pessoal.getCtps().setCtpsDv(ctpsDv);
	}

	public void setProjectionCtpsDataExpedicao(Date dataExpedicao)
	{
		inicializaPessoal();
		this.pessoal.getCtps().setCtpsDataExpedicao(dataExpedicao);
	}

	public void setPessoalCpf(String pessoalCpf)
	{
		inicializaPessoal();
		pessoal.setCpf(pessoalCpf);
	}

	public void setPessoalMae(String mae)
	{
		inicializaPessoal();
		pessoal.setMae(mae);
	}

	public void setPessoalPai(String pai)
	{
		inicializaPessoal();
		pessoal.setPai(pai);
	}

	public void setPessoalEstadoCivil(String estadoCivil)
	{
		inicializaPessoal();
		this.pessoal.setEstadoCivil(estadoCivil);
	}

	public void setPessoalEscolaridade(String escolaridade)
	{
		inicializaPessoal();
		pessoal.setEscolaridade(escolaridade);
	}

	public void setProjectionCidadeCodigoAC(String codigoAC)
	{
		inicializaEndereco();
		if (this.endereco.getCidade() == null)
			this.endereco.setCidade(new Cidade());
		this.endereco.getCidade().setCodigoAC(codigoAC);
	}

	private void inicializaEndereco()
	{
		if (this.endereco == null)
			this.endereco = new Endereco();
	}

	public void setEnderecoLogradouro(String logradouro)
	{
		inicializaEndereco();
		this.endereco.setLogradouro(logradouro);
	}

	public void setEnderecoComplemento(String complemento)
	{
		inicializaEndereco();
		this.endereco.setComplemento(complemento);
	}

	public void setEnderecoNumero(String numero)
	{
		inicializaEndereco();
		this.endereco.setNumero(numero);
	}

	public void setEnderecoCep(String cep)
	{
		inicializaEndereco();
		this.endereco.setCep(cep);
	}

	public void setEnderecoBairro(String bairro)
	{
		inicializaEndereco();
		this.endereco.setBairro(bairro);
	}

	public void setEnderecoCidadeNome(String nome)
	{
		inicializaEndereco();
		if (this.endereco.getCidade() == null)
			this.endereco.setCidade(new Cidade());
		this.endereco.getCidade().setNome(nome);
	}

	public void setEnderecoCidadeId(Long cidadeId)
	{
		inicializaEndereco();
		if (this.endereco.getCidade() == null)
			this.endereco.setCidade(new Cidade());
		this.endereco.getCidade().setId(cidadeId);
	}

	public void setEnderecoUfSigla(String sigla)
	{
		inicializaEndereco();
		if (this.endereco.getUf() == null)
			this.endereco.setUf(new Estado());
		this.endereco.getUf().setSigla(sigla);
	}

	public void setEnderecoUfId(Long enderecoUfId)
	{
		inicializaEndereco();
		if (this.endereco.getUf() == null)
			this.endereco.setUf(new Estado());
		this.endereco.getUf().setId(enderecoUfId);
	}
	
	private void  inicializaEstabelecimentoEndereco(){
		if(this.estabelecimento == null)
			this.estabelecimento = new Estabelecimento();
		
		if(this.estabelecimento.getEndereco() == null){
			this.estabelecimento.setEndereco(new Endereco());
		}
	}
	
	public void setEstabelecimentoEnderecoLogradouro(String logradouro)
	{
		inicializaEstabelecimentoEndereco();
		this.estabelecimento.getEndereco().setLogradouro(logradouro);
	}

	public void setEstabelecimentoEnderecoComplemento(String complemento)
	{
		inicializaEstabelecimentoEndereco();
		this.estabelecimento.getEndereco().setComplemento(complemento);
	}

	public void setEstabelecimentoEnderecoNumero(String numero)
	{
		inicializaEstabelecimentoEndereco();
		this.estabelecimento.getEndereco().setNumero(numero);
	}

	public void setEstabelecimentoEnderecoCep(String cep)
	{
		inicializaEstabelecimentoEndereco();
		this.estabelecimento.getEndereco().setCep(cep);
	}

	public void setEstabelecimentoEnderecoBairro(String bairro)
	{
		inicializaEstabelecimentoEndereco();
		this.estabelecimento.getEndereco().setBairro(bairro);
	}

	public void setEstabelecimentoEnderecoCidadeNome(String nome)
	{
		inicializaEstabelecimentoEndereco();
		if (this.estabelecimento.getEndereco().getCidade() == null)
			this.estabelecimento.getEndereco().setCidade(new Cidade());
		this.estabelecimento.getEndereco().getCidade().setNome(nome);
	}

	public void setEstabelecimentoEnderecoUfSigla(String sigla)
	{
		inicializaEstabelecimentoEndereco();
		if (this.estabelecimento.getEndereco().getUf() == null)
			this.estabelecimento.getEndereco().setUf(new Estado());
		this.estabelecimento.getEndereco().getUf().setSigla(sigla);
	}

	public void setContatoFoneFixo(String foneFixo)
	{
		if (this.contato == null)
			this.contato = new Contato();
		this.contato.setFoneFixo(foneFixo);
	}

	public void setContatoDdd(String ddd)
	{
		if (this.contato == null)
			this.contato = new Contato();
		this.contato.setDdd(ddd);
	}
	
	public void setContatoDddCelular(String dddCelular)
	{
		if (this.contato == null)
			this.contato = new Contato();
		this.contato.setDddCelular(dddCelular);
	}

	public void setContatoCelular(String celular)
	{
		if (this.contato == null)
			this.contato = new Contato();
		this.contato.setFoneCelular(celular);
	}

	public void setContatoEmail(String email){
		if(this.contato == null)
			this.contato = new Contato();
		this.contato.setEmail(email);
	}
	
	public void setPessoalConjuge(String conjuge)
	{
		inicializaPessoal();
		this.pessoal.setConjuge(conjuge);
	}

	public void setPessoalQtdFilhos(int qtdFilhos)
	{
		inicializaPessoal();
		this.pessoal.setQtdFilhos(qtdFilhos);
	}

	public void setProjectionDeficiencia(char deficiencia)
	{
		inicializaPessoal();
		this.pessoal.setDeficiencia(deficiencia);
	}

	public void setProjectionPis(String pis)
	{
		inicializaPessoal();
		this.pessoal.setPis(pis);
	}

	public void setProjectionDataNascimento(Date dataNascimento)
	{
		inicializaPessoal();
		this.pessoal.setDataNascimento(dataNascimento);
	}

	public void setProjectionSexo(char sexo)
	{
		inicializaPessoal();
		this.pessoal.setSexo(sexo);
	}

	private void inicializaPessoal()
	{
		if (this.pessoal == null)
			this.pessoal = new Pessoal();
	}

	private void inicializaHabilitacao()
	{
		if (this.habilitacao == null)
			this.habilitacao = new Habilitacao();
	}

	private void inicializaTitulo()
	{
		inicializaPessoal();
		if (this.pessoal.getTituloEleitoral() == null)
			this.pessoal.setTituloEleitoral(new TituloEleitoral());
	}

	public void setProjectionTituloNumero(String numero)
	{
		inicializaTitulo();
		this.pessoal.getTituloEleitoral().setTitEleitNumero(numero);
	}

	public void setProjectionTituloZona(String zona)
	{
		inicializaTitulo();
		this.pessoal.getTituloEleitoral().setTitEleitZona(zona);
	}

	public void setProjectionTituloSecao(String secao)
	{
		inicializaTitulo();
		this.pessoal.getTituloEleitoral().setTitEleitSecao(secao);
	}

	public void setProjectionRegistroHabilitacao(String registro)
	{
		inicializaHabilitacao();
		this.habilitacao.setRegistro(registro);
	}

	public void setProjectionNumeroHabilitacao(String numeroHab)
	{
		inicializaHabilitacao();
		this.habilitacao.setNumeroHab(numeroHab);
	}

	public void setProjectionEmissaoHabilitacao(Date emissao)
	{
		inicializaHabilitacao();
		this.habilitacao.setEmissao(emissao);
	}

	public void setProjectionVencimentoHabilitacao(Date vencimento)
	{
		inicializaHabilitacao();
		this.habilitacao.setVencimento(vencimento);
	}

	public void setProjectionCategoriaHabilitacao(String categoria)
	{
		inicializaHabilitacao();
		this.habilitacao.setCategoria(categoria);
	}
	
	public void setProjectionUFHabilitacao(Long ufId)
	{
		inicializaHabilitacao();
		this.habilitacao.setIdUFHabilitacao(ufId);
	}

	public void setProjectionCertMilNumero(String numero)
	{
		this.pessoal.getCertificadoMilitar().setCertMilNumero(numero);
	}

	public void setProjectionCertMilTipo(String tipo)
	{
		this.pessoal.getCertificadoMilitar().setCertMilTipo(tipo);
	}

	public void setProjectionCertMilSerie(String serie)
	{
		this.pessoal.getCertificadoMilitar().setCertMilSerie(serie);
	}

	public void setProjectionMotivoDemissaoId(Long projectionMotivoDemissaoId)
	{
		if (this.motivoDemissao == null)
			this.motivoDemissao = new MotivoDemissao();
		this.motivoDemissao.setId(projectionMotivoDemissaoId);
	}

	public void setMotivoDemissaoMotivo(String motivo)
	{
		if (this.motivoDemissao == null)
			this.motivoDemissao = new MotivoDemissao();
		this.motivoDemissao.setMotivo(motivo);
	}

	public void setEmailColaborador(String emailColaborador)
	{
		if (contato == null)
			contato = new Contato();
		contato.setEmail(emailColaborador);
	}

	public void setUsuarioSenha(String usuarioSenha)
	{
		iniciaUsuario();
		usuario.setSenha(usuarioSenha);
	}

	public void setUsuarioIdProjection(Long usuarioIdProjection)
	{
		iniciaUsuario();
		usuario.setId(usuarioIdProjection);
	}

	public void setUsuarioNomeProjection(String usuarioNome)
	{
		iniciaUsuario();
		usuario.setNome(usuarioNome);
	}

	public void setUsuarioAcessoSistema(Boolean acessoSistema)
	{
		iniciaUsuario();
		usuario.setAcessoSistema(acessoSistema);
	}

	private void iniciaUsuario() 
	{
		if (usuario == null)
			usuario = new Usuario();
	}
	
	public void setAreaOrganizacionalId(Long id)
	{
		iniciaAreaOrganizacional();
		areaOrganizacional.setId(id);
	}
	
	public Long getAreaOrganizacionalId()
	{
		if(areaOrganizacional == null)
			return null;
			
		return areaOrganizacional.getId();
	}

	public void setAreaOrganizacionalNome(String nome)
	{
		iniciaAreaOrganizacional();
		areaOrganizacional.setNome(nome);
	}

	public Long getAreaOrganizacionalResponsavelId()
	{
		if(areaOrganizacional != null && areaOrganizacional.getResponsavel() != null )
			return areaOrganizacional.getResponsavel().getId();
			
		return null;
	}
	
	public Long getAreaOrganizacionalCoResponsavelId()
	{
		if(areaOrganizacional != null && areaOrganizacional.getCoResponsavel() != null )
			return areaOrganizacional.getCoResponsavel().getId();
		
		return null;
	}
	
	public void setAreaOrganizacionalCodigoAC(String codigoAC)
	{
		iniciaAreaOrganizacional();
		areaOrganizacional.setCodigoAC(codigoAC);
	}

	private void iniciaAreaOrganizacional() 
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
	}

	public void setAreaOrganizacionalDescricao(String nome)
	{
		iniciaAreaOrganizacional();
		areaOrganizacional.setDescricao(nome);
	}
	
	public void setAreaOrganizacionalNomeComHierarquia(String nomeComHierarquia)
	{
		iniciaAreaOrganizacional();
		areaOrganizacional.setNomeComHierarquia(nomeComHierarquia);
	}

	public void setAreaOrganizacionalAreaMaeId(Long areaMaeId)
	{
		iniciaAreaOrganizacional();
		areaOrganizacional.setAreaMaeId(areaMaeId);
	}

	public void setEmpresaId(Long empresaId)
	{
		criarEmpresa();
		empresa.setId(empresaId);
	}

	public void setEmpresaNome(String nome)
	{
		criarEmpresa();
		empresa.setNome(nome);
	}
	
	public void setEmpresaAcIntegra(Boolean acIntegra)
	{
		criarEmpresa();
		
		if(acIntegra == null)
			acIntegra = false;
		
		empresa.setAcIntegra(acIntegra);
	}

	public void setEmpresaEmailRemetente(String emailRemetente)
	{
		criarEmpresa();
		empresa.setEmailRemetente(emailRemetente);
	}
	
	public void setEmpresaCnpj(String cnpj)
	{
		criarEmpresa();
		empresa.setCnpj(cnpj);
	}
	
	@NaoAudita
	public String getEmpresaNome()
	{
		if(empresa == null || empresa.getNome()==null)
			return "";
		
		return empresa.getNome();
	}

	@NaoAudita
	public String getEstabelecimentoNome()
	{
		if(estabelecimento == null || estabelecimento.getNome()==null)
			return "";
		
		return estabelecimento.getNome();
	}

	private void criarEmpresa() {
		if (empresa == null)
			empresa = new Empresa();
	}

	public void setEmpresaCodigoAC(String codigo)
	{
		criarEmpresa();
		empresa.setCodigoAC(codigo);
	}

	public void setEmpresaGrupoAC(String codigo)
	{
		criarEmpresa();
		empresa.setGrupoAC(codigo);
	}

	public void setCampoExtraColaborador(Boolean campoExtraColaborador)
	{
		if(campoExtraColaborador != null)
		{
			criarEmpresa();
			empresa.setCampoExtraColaborador(campoExtraColaborador);			
		}
	}
	
	public void setCampoExtraAtualizarMeusDados(Boolean campoExtraAtualizarMeusDados)
	{
		if(campoExtraAtualizarMeusDados != null){
			criarEmpresa();
			empresa.setCampoExtraAtualizarMeusDados(campoExtraAtualizarMeusDados);			
		}
	}

	public void setProjectionNitRepresentanteLegal(String projectionNitRepresentanteLegal)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setNitRepresentanteLegal(projectionNitRepresentanteLegal);
	}

	public void setProjectionRepresentanteLegal(String projectionRepresentanteLegal)
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
		this.empresa.setRepresentanteLegal(projectionRepresentanteLegal);
	}

	@ChaveDaAuditoria
	public String getNome()
	{
		return nome;
	}
	@NaoAudita
	public String getNomeDesligado()
	{
		if(this.desligado)
			return this.nome + " (Desligado)";
		else
			return this.nome;
	}
	@NaoAudita
	public String getNomeComercialDesligado()
	{
		String retorno = this.nome;
		if(StringUtils.isNotEmpty(this.nomeComercial))
			retorno += " (" + this.nomeComercial + ")";
		if(this.desligado)
			retorno += " (Desligado)";
	
			return retorno;
	}
	@NaoAudita
	public String getNomeEOuNomeComercial()
	{
		String retorno = this.nome;
		if(StringUtils.isNotEmpty(this.nomeComercial) && !retorno.equalsIgnoreCase(this.nomeComercial))
			retorno += " (" + this.nomeComercial + ")";
		
		return retorno;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public AreaOrganizacional getAreaOrganizacional()
	{
		return areaOrganizacional;
	}
	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional = areaOrganizacional;
	}
	public Contato getContato()
	{
		return contato;
	}
	public void setContato(Contato contato)
	{
		this.contato = contato;
	}
	public Date getDataAdmissao()
	{
		return dataAdmissao;
	}
	@NaoAudita
	public String getDataAdmissaoFormatada()
	{
		return DateUtil.formataDate(this.dataAdmissao, "dd/MM/yyyy");
	}
	public void setDataAdmissao(Date dataAdmissao)
	{
		this.dataAdmissao = dataAdmissao;
	}
	public Date getDataDesligamento()
	{
		return dataDesligamento;
	}
	@NaoAudita
	public String getDataDesligamentoFormatada()
	{
		return DateUtil.formataDiaMesAno(this.dataDesligamento);
	}
	public void setDataDesligamento(Date dataDesligamento)
	{
		this.dataDesligamento = dataDesligamento;
	}
	public Collection<Dependente> getDependentes()
	{
		return dependentes;
	}
	public void setDependentes(Collection<Dependente> dependentes)
	{
		this.dependentes = dependentes;
	}
	public Endereco getEndereco()
	{
		return endereco;
	}
	public void setEndereco(Endereco endereco)
	{
		this.endereco = endereco;
	}
	public String getMatricula()
	{
		return matricula;
	}
	public void setMatricula(String matricula)
	{
		this.matricula = matricula;
	}
	public String getNomeComercial()
	{
		return nomeComercial;
	}
	public void setNomeComercial(String nomeComercial)
	{
		this.nomeComercial = nomeComercial;
	}
	public Pessoal getPessoal()
	{
		return pessoal;
	}
	public Usuario getUsuario()
	{
		return usuario;
	}
	public Long getUsuarioId()
	{
		if(usuario != null )
			return usuario.getId();
		
		return null;
	}
	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public void setPessoal(Pessoal pessoal)
	{
		this.pessoal = pessoal;
	}

	public boolean isDesligado()
	{
		return desligado;
	}

	public void setDesligado(boolean desligado)
	{
		this.desligado = desligado;
	}

	public String getObservacao()
	{
		return observacao;
	}

	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}

	public String getCodigoAC()
	{
		return codigoAC;
	}

	public void setCodigoAC(String codigoAC)
	{
		if("".equals(codigoAC))
			codigoAC = null;
		
		this.codigoAC = codigoAC;
	}

	public String getCursos()
	{
		return cursos;
	}

	public void setCursos(String cursos)
	{
		this.cursos = cursos;
	}

	public Collection<Experiencia> getExperiencias()
	{
		return experiencias;
	}
	public void setExperiencias(Collection<Experiencia> experiencias)
	{
		this.experiencias = experiencias;
	}
	public Collection<Formacao> getFormacao()
	{
		return formacao;
	}
	public void setFormacao(Collection<Formacao> formacao)
	{
		this.formacao = formacao;
	}
	public Collection<ColaboradorIdioma> getColaboradorIdiomas()
	{
		return colaboradorIdiomas;
	}
	public void setColaboradorIdiomas(Collection<ColaboradorIdioma> colaboradorIdiomas)
	{
		this.colaboradorIdiomas = colaboradorIdiomas;
	}
	public String getVinculo()
	{
		return vinculo;
	}
	@NaoAudita
	public String getVinculoDescricao()
	{
		if (StringUtils.isNotBlank(this.vinculo))
		{
			Vinculo vinculoAux = new Vinculo();
			return vinculoAux.get(this.vinculo).toString();
		}
		else
			return "";
	}
	
	public void setVinculo(String vinculo)
	{
		this.vinculo = vinculo;
	}
	public Candidato getCandidato()
	{
		return candidato;
	}
	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}
	public Empresa getEmpresa()
	{
		if (this.empresa == null)
			this.empresa = new Empresa();
			
		return empresa;
	}
	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}
	public MotivoDemissao getMotivoDemissao()
	{
		return motivoDemissao;
	}
	public void setMotivoDemissao(MotivoDemissao motivoDemissao)
	{
		this.motivoDemissao = motivoDemissao;
	}
	public Collection<ColaboradorOcorrencia> getColaboradorOcorrencia()
	{
		return colaboradorOcorrencia;
	}
	public void setColaboradorOcorrencia(Collection<ColaboradorOcorrencia> colaboradorOcorrencia)
	{
		this.colaboradorOcorrencia = colaboradorOcorrencia;
	}
	public FaixaSalarial getFaixaSalarial()
	{
		return faixaSalarial;
	}
	public void setFaixaSalarial(FaixaSalarial faixaSalarial)
	{
		this.faixaSalarial = faixaSalarial;
	}
	public Double getSalario()
	{
		return salario;
	}
	public void setSalario(Double salario)
	{
		this.salario = salario;
	}
	public Double getSalarioVariavel() {
		return salarioVariavel;
	}
	public void setSalarioVariavel(Double salarioVariavel) {
		this.salarioVariavel = salarioVariavel;
	}
	public Double getMensalidade() {
		return mensalidade;
	}
	public void setMensalidade(Double mensalidade) {
		this.mensalidade = mensalidade;
	}
	public String getRegimeRevezamento()
	{
		return regimeRevezamento;
	}
	public void setRegimeRevezamento(String regimeRevezamento)
	{
		this.regimeRevezamento = regimeRevezamento;
	}
	public Funcao getFuncao()
	{
		return funcao;
	}
	public void setFuncao(Funcao funcao)
	{
		this.funcao = funcao;
	}
	public void setFuncaoId(Long funcaoId){
		inicializaFuncao();
		funcao.setId(funcaoId);
	}
	public void setFuncaoNome(String funcaoNome){
		inicializaFuncao();
		funcao.setNome(funcaoNome);
	}
	
	public void setFuncaoHistoricoFuncaoAtualId(Long historicoFuncaoId){
		inicializaFuncao();
		funcao.setHistoricoAtualId(historicoFuncaoId);
	}
	
	public void setFuncaoHistoricoFuncaoAtualDescricao(String historicoFuncaoDescricao){
		inicializaFuncao();
		funcao.setHistoricoAtualDescricao(historicoFuncaoDescricao);
	}

	private void inicializaFuncao() {
		if(this.funcao == null)
			this.funcao = new Funcao();
	}
	
	public Collection<HistoricoColaborador> getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}

	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors)
	{
		this.historicoColaboradors = historicoColaboradors;
	}

	@NaoAudita
	public Estabelecimento getEstabelecimento()
	{
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento)
	{
		this.estabelecimento = estabelecimento;
	}

	public boolean isNaoIntegraAc()
	{
		return naoIntegraAc;
	}

	public void setNaoIntegraAc(boolean naoIntegraAc)
	{
		this.naoIntegraAc = naoIntegraAc;
	}

	@NaoAudita
	public String getPeriodoFormatado()
	{
		String result = DateUtil.formataDate(this.dataAdmissao, "dd/MM/yyyy");
		if (this.dataDesligamento != null)
			result += " a " + DateUtil.formataDate(this.dataDesligamento, "dd/MM/yyyy");
		else
			result += " até o momento";

		return result + " " + DateUtil.getIntervaloEntreDatas(this.dataAdmissao, this.dataDesligamento);
	}
	
	@NaoAudita
	public String getTempoDeServico()
	{
		String tempoDeServico = "";
		
		if (this.dataAdmissao != null)
			tempoDeServico = DateUtil.calculaIntervaloAteHoje(dataAdmissao);
		
		if(tempoDeServico.equals("-"))
			tempoDeServico = "menos de 1 mês"; 
		
		return tempoDeServico;
	}
	
	@NaoAudita
	public String getTempoDeServicoComAno()
	{
		return tempoServico > 1 ? tempoServico + " anos" : tempoServico + " ano" ;
	}

	public String toString()
	{
		ToStringBuilder string = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
		string.append("id", this.getId());
		string.append("nome", this.nome);
		string.append("nomeComercial", this.nomeComercial);
		string.append("matricula", this.matricula);
		string.append("pessoal", this.pessoal);
		string.append("endereco", this.endereco);
		string.append("contato", this.contato);
		string.append("vinculo", this.vinculo);
		string.append("desligado", this.desligado);
		string.append("dataAdmissao", this.dataAdmissao);
		string.append("dataDesligamento", this.dataDesligamento);
		string.append("codigoAC", this.codigoAC);
		string.append("dataAtualizacao", this.dataAtualizacao);

		return string.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public Habilitacao getHabilitacao()
	{
		return habilitacao;
	}

	public void setHabilitacao(Habilitacao habilitacao)
	{
		this.habilitacao = habilitacao;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	@NaoAudita
	public ReajusteColaborador getReajusteColaborador()
	{
		return reajusteColaborador;
	}

	public void setReajusteColaborador(ReajusteColaborador reajusteColaborador)
	{
		this.reajusteColaborador = reajusteColaborador;
	}
	@NaoAudita
	public Boolean getEhProjecao()
	{
		return ehProjecao;
	}

	public void setEhProjecao(Boolean ehProjecao)
	{
		this.ehProjecao = ehProjecao;
	}
	@NaoAudita
	public String getIdEstabelecimentoAreaOrganizacional()
	{
		return this.getEstabelecimento().getId() + "-" + this.areaOrganizacional.getId();
	}
	@NaoAudita
	public String getDescricaoEstabelecimentoAreaOrganizacional()
	{
		if (this.getEstabelecimento() != null
				&& this.areaOrganizacional != null) {
			return this.getEstabelecimento().getNome() + " - " + this.areaOrganizacional.getDescricao();
		}
		return "";
	}
	@NaoAudita
	public String getDescricaoEmpresaEstabelecimentoAreaOrganizacional()
	{
		if (this.empresa != null && this.getEstabelecimento() != null && this.areaOrganizacional != null) 
			return this.empresa.getNome() + " - " + this.getEstabelecimento().getNome() + " - " + this.areaOrganizacional.getDescricao();

		return "";
	}
	@NaoAudita
	public String getNomeComercialEmpresa()
	{
		StringBuilder descricao = new StringBuilder();
		if (this.empresa != null)
		{
			descricao.append(this.empresa.getNome());
			descricao.append(" - " + getNomeMaisNomeComercial());
		}
		
		return descricao.toString();
	}
	
	@NaoAudita
	public String getNomeMaisNomeComercial()
	{
		String nomeMaisNomeComercial  = ""; 

		if(StringUtils.isNotBlank(this.nome))
			nomeMaisNomeComercial = this.nome;
		
		if (StringUtils.isNotBlank(this.nomeComercial))
			nomeMaisNomeComercial += " (" + this.nomeComercial + ")";
		else
			nomeMaisNomeComercial += " (Sem Nome Comercial)";
			
		return nomeMaisNomeComercial;
	}

	@NaoAudita
	public String getMatriculaNomeMaisNomeComercial()
	{
		String nome  = ""; 
		
		if (StringUtils.isNotBlank(this.matricula))
			nome = this.matricula + " - ";
		
		return nome + getNomeMaisNomeComercial();
	}
	
	public boolean isRespondeuEntrevista()
	{
		return respondeuEntrevista;
	}

	public void setRespondeuEntrevista(boolean respondeuEntrevista)
	{
		this.respondeuEntrevista = respondeuEntrevista;
	}

	@NaoAudita
	public Collection<ColaboradorTurma> getColaboradorTurmas()
	{
		return colaboradorTurmas;
	}

	public void setColaboradorTurmas(Collection<ColaboradorTurma> colaboradorTurmas)
	{
		this.colaboradorTurmas = colaboradorTurmas;
	}
	@NaoAudita
	public File getFoto()
	{
		return foto;
	}

	public void setFoto(File foto)
	{
		this.foto = foto;
	}
	@NaoAudita
	public String getNomeCpf()
	{
		String nomeCpf = "";
		if(nome != null)
			nomeCpf = nome;
		if(!nome.equals(nomeComercial))
			nomeCpf += " (" + nomeComercial + ")";
		if(pessoal != null && pessoal.getCpf() != null)
			nomeCpf += " - " + pessoal.getCpfFormatado();
		if(isDesligado())
			nomeCpf += " (Desligado)";

		return nomeCpf;
	}
	@NaoAudita
	public String getNomeCpfMatricula()
	{
		String nomeCpf = "";
		if(nome != null)
			nomeCpf = nome;
		if (pessoal != null)
		{
			if(pessoal.getCpf() != null)
				nomeCpf += " - " + pessoal.getCpfFormatado();
		}
		if(StringUtils.isNotBlank(matricula))
			nomeCpf += " - " + matricula;
		if(desligado)
			nomeCpf += " (Desligado)";

		return nomeCpf;
	}

	@NaoAudita
	public String getNomeMatricula()
	{
		String nomeMatricula = "";
		if(nome != null)
			nomeMatricula = nome;

		if(StringUtils.isNotBlank(matricula))
			nomeMatricula += " - " + matricula;
		if(desligado)
			nomeMatricula += " (Desligado)";
		
		return nomeMatricula;
	}
	
	@NaoAudita
	public String getDesligadoDescricao()
	{
		if(desligado)
			return "Desligado";
		else
			return "Ativo";
	}

	@NaoAudita
	public Collection<SolicitacaoExame> getSolicitacaoExames()
	{
		return solicitacaoExames;
	}

	public void setSolicitacaoExames(Collection<SolicitacaoExame> solicitacaoExames)
	{
		this.solicitacaoExames = solicitacaoExames;
	}

	@NaoAudita
	public Collection<Cat> getCats() {
		return cats;
	}

	public void setCats(Collection<Cat> cats) {
		this.cats = cats;
	}
	@NaoAudita
	public String getCnpjDaEmpresa() {
		Empresa empresa = this.getEmpresa();
		if (empresa != null)
			return empresa.getCnpj();
		return "";
	}

	@NaoAudita
	public Double getSalarioCalculado()
	{
		return SalarioUtil.getValor(this.getHistoricoColaborador().getTipoSalario(), 
				this.getHistoricoColaborador().getFaixaSalarial(), 
				this.getHistoricoColaborador().getIndice(), 
				this.getHistoricoColaborador().getQuantidadeIndice(), 
				this.getHistoricoColaborador().getSalario());
	}
	
	@NaoAudita
	public String getSalarioCalculadoString()
	{
		Double salario = SalarioUtil.getValor(this.getHistoricoColaborador().getTipoSalario(), 
				this.getHistoricoColaborador().getFaixaSalarial(), 
				this.getHistoricoColaborador().getIndice(), 
				this.getHistoricoColaborador().getQuantidadeIndice(), 
				this.getHistoricoColaborador().getSalario());
		
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(salario);
	}
	
	@NaoAudita
	public String getFuncaoNome()
	{
		if(funcao != null && funcao.getNome() != null)
			return funcao.getNome();
		
		return "";
	}
	@NaoAudita
	public String getAmbienteNome()
	{
		if(ambiente != null && ambiente.getNome() != null)
			return ambiente.getNome();
		
		return "";
	}
	
//	@NaoAudita
//	public Solicitacao getSolicitacao() {
//		return solicitacao;
//	}
//
//	public void setSolicitacao(Solicitacao solicitacao) {
//		this.solicitacao = solicitacao;
//	}

	@NaoAudita
	public Collection<ColaboradorAfastamento> getColaboradorAfastamento() {
		return colaboradorAfastamento;
	}

	public void setColaboradorAfastamento(
			Collection<ColaboradorAfastamento> colaboradorAfastamento) {
		this.colaboradorAfastamento = colaboradorAfastamento;
	}

	public CamposExtras getCamposExtras()
	{
		return camposExtras;
	}

	public void setCamposExtras(CamposExtras camposExtras)
	{
		this.camposExtras = camposExtras;
	}
	
	public void setCamposExtrasId(Long camposExtrasId)
	{
		if(this.camposExtras == null)
			this.camposExtras = new CamposExtras();
		
		this.camposExtras.setId(camposExtrasId);
	}
	
	@NaoAudita
	public String getAdmitidoHa() 
	{
		return diasDeEmpresa + " dias";
	}
	
	@NaoAudita
	public String getDiaMesDataAdmissao()
	{
		String diaMes = "-";
		if (this.dataAdmissao != null)
		{
			Integer tmp=0;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.dataAdmissao);

			tmp = calendar.get(Calendar.DAY_OF_MONTH);
			diaMes = (tmp<10?"0"+tmp : tmp.toString()) + "/";
			tmp = calendar.get(Calendar.MONTH)+1;
			diaMes += (tmp<10?"0"+tmp : tmp.toString());
		}
		return diaMes;
	}
	
	@NaoAudita
	public String getMesAdmissaoPorExtenso(){
		String mes = "-";
		if (this.dataAdmissao != null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(this.dataAdmissao);
			mes = DateUtil.getNomeMesCompleto(calendar.get(Calendar.MONTH)+1);
		}
		return mes;
	}
	
	@NaoAudita
	public String getSugestaoPeriodoAcompanhamentoExperiencia() {
		return sugestaoPeriodoAcompanhamentoExperiencia;
	}

	public void setSugestaoPeriodoAcompanhamentoExperiencia(String sugestaoPeriodoAcompanhamentoExperiencia) {
		this.sugestaoPeriodoAcompanhamentoExperiencia = sugestaoPeriodoAcompanhamentoExperiencia;
	}

	@NaoAudita
	public Integer getDiasDeEmpresa() {
		return diasDeEmpresa;
	}

	public void setDiasDeEmpresa(Integer diasDeEmpresa) {
		this.diasDeEmpresa = diasDeEmpresa;
	}

	@NaoAudita
	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}

	public void setColaboradorQuestionarios(Collection<ColaboradorQuestionario> colaboradorQuestionarios) {
		this.colaboradorQuestionarios = colaboradorQuestionarios;
	}

	@NaoAudita
	public Date getAvaliacaoRespondidaEm() {
		return avaliacaoRespondidaEm;
	}

	public void setAvaliacaoRespondidaEm(Date avaliacaoRespondidaEm) {
		this.avaliacaoRespondidaEm = avaliacaoRespondidaEm;
	}

	public String getDatasDeAvaliacao() {
		return datasDeAvaliacao;
	}

	public void setDatasDeAvaliacao(String datasDeAvaliacao)
	{
		if(this.datasDeAvaliacao == null)
			this.datasDeAvaliacao = new String();
		
		this.datasDeAvaliacao += datasDeAvaliacao;
	}

	@NaoAudita
	public Long getAvaliacaoDesempenhoId() {
		return avaliacaoDesempenhoId;
	}

	public String getAvaliacaoTitulo() {
		return avaliacaoTitulo;
	}

	@NaoAudita
	public Date getRespondidaEm() {
		return respondidaEm;
	}
	@NaoAudita
	public String getPerformance() {
		Double result = performance == null ? 0.0 : (performance * 100);
		
		NumberFormat formata = new DecimalFormat("#0.00");
		return formata.format(result).toString() + " %"; 
	}

	@NaoAudita
	public Double getPerformanceDouble() 
	{
		return performance ; 
	}

	public String getTitulo() {
		return titulo;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public boolean jaFoiUmCandidato() {
		return (candidato != null && candidato.getId() != null);
	}

	public void setAvaliacaoDesempenhoId(Long avaliacaoDesempenhoId) {
		this.avaliacaoDesempenhoId = avaliacaoDesempenhoId;
	}

	public String getObservacaoDemissao() {
		return observacaoDemissao;
	}

	public void setObservacaoDemissao(String observacaoDemissao) {
		this.observacaoDemissao = observacaoDemissao;
	}

	@NaoAudita
	public Integer getQtdDiasRespondeuAvExperiencia() {
		return qtdDiasRespondeuAvExperiencia;
	}

	public Long getPeriodoExperienciaId() {
		return periodoExperienciaId;
	}
	@NaoAudita
	public String getRespondidaEmFormatada()
	{
		return DateUtil.formataDate(this.respondidaEm, "dd/MM/yyyy");
	}
	@NaoAudita
	public String getCargoFaixa()
	{
		try {
			return StringUtils.defaultString(faixaSalarial.getCargo().getNome()) + " " + StringUtils.defaultString(faixaSalarial.getNome());
		} catch (Exception e) {
			return "-";
		}
	}
	
	@NaoAudita
	public String getFaixaSalarialCodigoCbo()
	{
		try {
			return faixaSalarial.getCodigoCbo();
		} catch (Exception e) {
			return "-";
		}
	}
	
	public void setFaixaSalarialCodigoCbo(String codigoCbo)
	{
		if(faixaSalarial == null)
			faixaSalarial = new FaixaSalarial();
		
		this.faixaSalarial.setCodigoCbo(codigoCbo);
	}
	
	@NaoAudita
	public Cargo getCargo()
	{
		return this.faixaSalarial.getCargo();
	}
	@NaoAudita
	public String getTipoSalarioDescricao()
	{
		return com.fortes.rh.model.dicionario.TipoAplicacaoIndice.getDescricao(historicoColaborador.getTipoSalario());
	}

	@NaoAudita
	public Double getSalarioHistorico() 
	{
		return historicoColaborador.getSalarioCalculado(); 
	}
	
	@NaoAudita
	public AreaOrganizacional getAreaOrganizacionalMatriarca() {
		return areaOrganizacionalMatriarca;
	}

	public void setAreaOrganizacionalMatriarca(AreaOrganizacional areaOrganizacionalMatriarca) {
		this.areaOrganizacionalMatriarca = areaOrganizacionalMatriarca;
	}

	@NaoAudita
	public String getNomeAvaliador() {
		return nomeAvaliador;
	}

	@NaoAudita
	public Ambiente getAmbiente() {
		return ambiente;
	}

	@NaoAudita
	public String getAvaliacaoDesempenhoTitulo() {
		return avaliacaoDesempenhoTitulo;
	}

	@NaoAudita
	public String getStatusAvaliacao() {
		return statusAvaliacao;
	}

	public void setStatusAvaliacao(String statusAvaliacao) {
		this.statusAvaliacao = statusAvaliacao;
	}

	@NaoAudita
	public Date getDataSolicitacaoDesligamento() {
		return dataSolicitacaoDesligamento;
	}
	
	@NaoAudita
	public String getDataSolicitacaoDesligamentoStr(){
		return DateUtil.formataDiaMesAno(this.dataSolicitacaoDesligamento);
	}

	public void setDataSolicitacaoDesligamento(Date dataSolicitacaoDesligamento) {
		this.dataSolicitacaoDesligamento = dataSolicitacaoDesligamento;
	}
	
	@NaoAudita
	public Date getDataSolicitacaoDesligamentoAc() {
		return dataSolicitacaoDesligamentoAc;
	}

	public void setDataSolicitacaoDesligamentoAc(Date dataSolicitacaoDesligamentoAc) {
		this.dataSolicitacaoDesligamentoAc = dataSolicitacaoDesligamentoAc;
	}

	public boolean isExibePerformanceProfissional() {
		return exibePerformanceProfissional;
	}

	public void setExibePerformanceProfissional(boolean exibePerformanceProfissional) {
		this.exibePerformanceProfissional = exibePerformanceProfissional;
	}

	@NaoAudita
	public int getSomaCompetencias() {
		return somaCompetencias;
	}

	public void setSomaCompetencias(int somaCompetencias) {
		this.somaCompetencias = somaCompetencias;
	}

	@NaoAudita
	public Collection<ConfiguracaoNivelCompetenciaColaborador> getConfiguracaoNivelCompetenciaColaboradors() {
		return configuracaoNivelCompetenciaColaboradors;
	}

	public void setConfiguracaoNivelCompetenciaColaboradors(Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradors) {
		this.configuracaoNivelCompetenciaColaboradors = configuracaoNivelCompetenciaColaboradors;
	}

	@NaoAudita
	public double getPercentualCompatibilidade() {
		return percentualCompatibilidade;
	}

	public void setPercentualCompatibilidade(double percentualCompatibilidade) {
		this.percentualCompatibilidade = percentualCompatibilidade;
	}

	@NaoAudita
	public Double getMediaPerformance()
	{
		return mediaPerformance;
	}
	
	@NaoAudita
	public String getMediaPerformanceFormatada()
	{
		Double result = 0.0;
		if(mediaPerformance != null)
			result = (mediaPerformance * 100);
		
		NumberFormat formata = new DecimalFormat("#0.00");
		return formata.format(result).toString() + " %";
	}

	
	public void setColaboradorCPF(String colaboradorCPF){
		if (pessoal == null) {
			setPessoal(new Pessoal());
		}
		pessoal.setCpf(colaboradorCPF);
	}
	
	public void setMediaPerformance(Double mediaPerformance)
	{
		this.mediaPerformance = mediaPerformance;
	}

	@NaoAudita
	public Integer getTempoServico() {
		return tempoServico;
	}
	
	@NaoAudita
	public String getTempoServicoMaisMes() {
		if(tempoServico == 1)
			return tempoServico +  " mes";
		else
			return tempoServico +  " meses";
	}

	public void setTempoServico(Integer tempoServico) {
		this.tempoServico = tempoServico;
	}

	@NaoAudita
	public String getIntervaloTempoServico() {
		return intervaloTempoServico;
	}

	public void setIntervaloTempoServico(String intervaloTempoServico) {
		this.intervaloTempoServico = intervaloTempoServico;
	}
	
	public void setAvaliacaoDesempenhoTitulo(String avaliacaoDesempenhoTitulo)
	{
		this.avaliacaoDesempenhoTitulo = avaliacaoDesempenhoTitulo;
	}

	public boolean isMembroComissaoCipa() {
		return membroComissaoCipa;
	}

	public void setMembroComissaoCipa(boolean membroComissaoCipa)
	{
		this.membroComissaoCipa = membroComissaoCipa;
	}

	@NaoAudita
	public Date getDataFimComissaoCipa() {
		return dataFimComissaoCipa;
	}

	public void setDataFimComissaoCipa(Date dataFimComissaoCipa) {
		this.dataFimComissaoCipa = dataFimComissaoCipa;
	}
	
	public Date getDataEncerramentoContrato()
	{
		return dataEncerramentoContrato;
	}

	public void setDataEncerramentoContrato(Date dataEncerramentoContrato)
	{
		this.dataEncerramentoContrato = dataEncerramentoContrato;
	}
	
	@NaoAudita
	public String getDataEncerramentoContratoFormatada()
	{
		return DateUtil.formataDate(this.dataEncerramentoContrato, "dd/MM/yyyy");
	}

	@NaoAudita
	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradorPeriodoExperienciaAvaliacaos() 
	{
		return colaboradorPeriodoExperienciaAvaliacaos;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaos(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorPeriodoExperienciaAvaliacaos) 
	{
		this.colaboradorPeriodoExperienciaAvaliacaos = colaboradorPeriodoExperienciaAvaliacaos;
	}

	@NaoAudita
	public Long getAvaliacaoId() {
		return avaliacaoId;
	}

	public void setAvaliacaoId(Long avaliacaoId) 
	{
		this.avaliacaoId = avaliacaoId;
	}

	@NaoAudita
	public String getTempoServicoString() 
	{
		return tempoServicoString;
	}

	public void setTempoServicoString(String tempoServicoString) 
	{
		this.tempoServicoString = tempoServicoString;
	}

	@NaoAudita
	public String getAfastadoString() {
		return afastado ? "Sim" : "Não";
	}

	@NaoAudita
	public Boolean getAfastado() {
		return afastado;
	}

	public void setAfastado(Boolean afastado) {
		this.afastado = afastado;
	}

	@NaoAudita
	public Collection<ComissaoMembro> getComissaoMembros() {
		return comissaoMembros;
	}

	public void setComissaoMembros(Collection<ComissaoMembro> comissaoMembros) {
		this.comissaoMembros = comissaoMembros;
	}
	
	public String getEnviadoParaAC() {
		if (empresa == null || !empresa.isAcIntegra())
			return "-";
		
		return naoIntegraAc ? "Não" : "Sim";
	}
	
	@NaoAudita
	public String getCodigoACRelatorio() {
		if (empresa == null || !empresa.isAcIntegra())
			return "-";
		
		return codigoAC;
	}

	@NaoAudita
	public Colaborador getSolicitanteDemissao() {
		return solicitanteDemissao;
	}

	public void setSolicitanteDemissao(Colaborador solicitanteDemissao) {
		this.solicitanteDemissao = solicitanteDemissao;
	}
	
	public void setSolicitanteDemissaoId(Long solicitanteDemissaoId) {
		if (solicitanteDemissao == null)
			solicitanteDemissao = new Colaborador();
		
		this.solicitanteDemissao.setId(solicitanteDemissaoId);
	}

//	public void setSolicitacaoId(Long solicitacaoId) {
//		if(this.solicitacao == null)
//			this.solicitacao = new Solicitacao();
//		
//		this.solicitacao.setId(solicitacaoId);
//	}
	
	public boolean isStatusAcPessoalAguardandoConfirmacao() 
	{
		if(statusAcPessoal == null)
			statusAcPessoal = StatusRetornoAC.CONFIRMADO;
		
		return statusAcPessoal == StatusRetornoAC.AGUARDANDO;
	}
		
	public void setStatusAcPessoal(Integer statusAcPessoal) {
			this.statusAcPessoal = statusAcPessoal;
	}
	
	@Override
	public String[] getDependenciasDesconsideradasNaRemocao() {
		String [] dependencias = new String[]{"candidato", "candidatosolicitacao", "colaboradoridioma", "configuracaonivelcompetencia", "configuracaonivelcompetenciacolaborador", 
												"colaboradorperiodoexperienciaavaliacao", "experiencia", "formacao", "historicocolaborador", "mensagem", "usuariomensagem", "solicitacaoexame"};
		return dependencias; 
	}

	@NaoAudita
	public Character getDemissaoGerouSubstituicao() {
		return demissaoGerouSubstituicao;
	}

	public void setDemissaoGerouSubstituicao(Character demissaoGerouSubstituicao) {
		this.demissaoGerouSubstituicao = demissaoGerouSubstituicao;
	}
	
	@NaoAudita
	public String getDescricaoDemissaoGerouSubstituicao() {
		if(demissaoGerouSubstituicao == null || demissaoGerouSubstituicao == 'I') 
			return "Indiferente";
		else if(demissaoGerouSubstituicao == 'S')
			return "Sim";
		else
			return "Não";		
	}

	@NaoAudita
	public BigDecimal getNota()
	{
		return nota;
	}

	public void setNota(BigDecimal nota)
	{
		this.nota = nota;
	}

	@NaoAudita
	public Indice getIndice() {
		return indice;
	}

	public void setIndice(Indice indice) {
		this.indice = indice;
	}

	public boolean isManterFoto() {
		return manterFoto;
	}

	public void setManterFoto(boolean manterFoto) {
		this.manterFoto = manterFoto;
	}

	@NaoAudita
	public Double getQtdAnosDeEmpresa() {
		return qtdAnosDeEmpresa;
	}

	public void setQtdAnosDeEmpresa(Double qtdAnosDeEmpresa) {
		this.qtdAnosDeEmpresa = qtdAnosDeEmpresa;
	}

	@NaoAudita
	public Collection<Colaborador> getAvaliados() {
		return avaliados;
	}

	public void setAvaliados(Collection<Colaborador> avaliados) {
		this.avaliados = avaliados;
	}

	@NaoAudita
	public Collection<FaixaSalarial> getFaixaSalariaisAvaliados() {
		return faixaSalariaisAvaliados;
	}

	public void setFaixaSalariaisAvaliados(
			Collection<FaixaSalarial> faixaSalariaisAvaliados) {
		this.faixaSalariaisAvaliados = faixaSalariaisAvaliados;
	}

	@NaoAudita
	public ColaboradorQuestionario getColaboradorQuestionario() {
		return colaboradorQuestionario;
	}

	public void setColaboradorQuestionario(
			ColaboradorQuestionario colaboradorQuestionario) {
		this.colaboradorQuestionario = colaboradorQuestionario;
	}

	@NaoAudita
	public Integer getPesoAvaliador() {
		return pesoAvaliador;
	}

	public void setPesoAvaliador(Integer pesoAvaliador) {
		this.pesoAvaliador = pesoAvaliador;
	}

	@NaoAudita
	public Double getProdutividade() {
		return produtividade == null ? 0 : produtividade;
	}

	public void setProdutividade(Double produtividade) {
		this.produtividade = produtividade;
	}

	@NaoAudita
	public Collection<OrdemDeServico> getOrdensDeServico() {
		return ordensDeServico;
	}

	public void setOrdensDeServico(Collection<OrdemDeServico> ordensDeServico) {
		this.ordensDeServico = ordensDeServico;
	}

	@NaoAudita
	public Collection<ColaboradorCertificacao> getColaboradorCertificacaos() {
		return colaboradorCertificacaos;
	}

	public void setColaboradorCertificacaos(Collection<ColaboradorCertificacao> colaboradorCertificacaos) {
		this.colaboradorCertificacaos = colaboradorCertificacaos;
	}

	public ColaboradorCertificacao getColaboradorCertificacao() {
		return colaboradorCertificacao;
	}

	public void setColaboradorCertificacao(	ColaboradorCertificacao colaboradorCertificacao) {
		this.colaboradorCertificacao = colaboradorCertificacao;
	}

	private void iniciaColaboradorCertificacao() {
		if(this.colaboradorCertificacao == null)
			this.colaboradorCertificacao = new ColaboradorCertificacao();
	}
	
	public void setColaboradorCertificacaoId(Long colaboradorCertificacaoId){
		iniciaColaboradorCertificacao();
		this.colaboradorCertificacao.setId(colaboradorCertificacaoId);
	}
	
	public void setColaboradorCertificacaoData(Date colaboradorCertificacaoData){
		iniciaColaboradorCertificacao();
		this.colaboradorCertificacao.setData(colaboradorCertificacaoData);
	}

	@NaoAudita
	public boolean isTemFuncao() {
		return temFuncao;
	}

	public void setTemFuncao(boolean temFuncao) {
		this.temFuncao = temFuncao;
	}

	@NaoAudita
	public boolean isIscritoNaTurma() {
		return iscritoNaTurma;
	}

	public void setIscritoNaTurma(boolean iscritoNaTurma) {
		this.iscritoNaTurma = iscritoNaTurma;
	}

	@NaoAudita
	public String getAvaliadorNome() {
		return avaliadorNome;
	}

	public void setAvaliadorNome(String avaliadorNome) {
		this.avaliadorNome = avaliadorNome;
	}

	@NaoAudita
	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	@NaoAudita
	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}
	
	@NaoAudita
	public String getAreaOrganizacionalNome()
	{
		if(areaOrganizacional == null || areaOrganizacional.getNome()==null)
			return "";
		
		return areaOrganizacional.getNome();
	}

}
