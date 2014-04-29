/* Autor: Igo Coelho
 * Data: 26/05/2006
 * Requisito: RFA0026 */
package com.fortes.rh.model.geral;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.ComissaoMembro;
import com.fortes.rh.model.sesmt.Funcao;
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
	@Column(length=60)
	private String nome;
	@Column(length=30)
	private String nomeComercial;
	private boolean desligado = false;
	@Temporal(TemporalType.DATE)
	private Date dataDesligamento;
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacaoDesligamento;
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacaoDesligamentoAc;
	@Lob
	private String observacao;
	@Temporal(TemporalType.DATE)
	private Date dataAdmissao;

	@Transient
	private Estabelecimento estabelecimento;
	@Transient
	private AreaOrganizacional areaOrganizacional;
	@Transient
	private AreaOrganizacional areaOrganizacionalMatriarca;
	@Transient
	private FaixaSalarial faixaSalarial;
	@Transient
	private Double salario;
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
	private Date respondidaEm;
	@Transient
	private Double performance;
	@Transient
	private Double mediaPerformance;
	@Transient
	private String titulo;
	@Transient
	private String nomeAvaliador;
	
	@Lob
	private String observacaoDemissao;
	
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

	@OneToOne(optional=true)
	private Candidato candidato;
	@OneToOne(optional=true)
	private Solicitacao solicitacao; // solicitação de pessoal que contratou o colaborador.
	@ManyToOne
	private MotivoDemissao motivoDemissao;
	@Column(length=50)
	private String regimeRevezamento;

	private boolean naoIntegraAc = false;

	@Transient
	private HistoricoColaborador historicoColaborador;
	@Transient
	private ReajusteColaborador reajusteColaborador;

	@Transient
	private Boolean ehProjecao;
	
	@Transient
	private Date avaliacaoRespondidaEm;

	private boolean respondeuEntrevista = false;
	private File foto;

	@OneToOne(fetch=FetchType.LAZY)
	private CamposExtras camposExtras;
		
	@Transient
	private String datasDeAvaliacao = "";
	@Transient
	private String statusAvaliacao = "";
	@Transient
	private boolean statusAcPessoalConfirmado;
	@Transient
	private Long avaliacaoDesempenhoId;
	@Transient
	private String avaliacaoDesempenhoTitulo;
	@Transient
	private boolean exibePerformanceProficional;
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
	
	public Colaborador(Long id, Date avaliacaoRespondidaEm, Double performance, Integer qtdDiasRespondeuAvExperiencia, Long periodoExperienciaId)
	{
		this.setId(id);
		this.avaliacaoRespondidaEm = avaliacaoRespondidaEm;
		this.qtdDiasRespondeuAvExperiencia = qtdDiasRespondeuAvExperiencia;
		this.performance = performance;
		this.periodoExperienciaId = periodoExperienciaId;
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
	public Colaborador(Long id, String nome, String matricula, Date dataAdmissao, Date dataDesligamento, String observacaoDemissao, Long motivoId, String motivoDemissao, String cargoNome, String faixaSalarialNome, Long estabelecimentoId, String estabelecimentoNome, Long areaId, String areaNome)
	{
		this.setId(id);
		this.nome = nome;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		this.dataDesligamento = dataDesligamento;
		this.observacaoDemissao = observacaoDemissao;
		
		if(this.motivoDemissao == null)
			this.motivoDemissao = new MotivoDemissao();
		this.motivoDemissao.setId(motivoId);
		this.motivoDemissao.setMotivo(motivoDemissao);

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
	public Colaborador(Long id, String nome, String nomeComercial, String matricula, Date dataAdmissao, boolean desligado, String cargoNome, String faixaSalarialNome, Long estabelecimentoId, String estabelecimentoNome, Long areaId, String areaNome, Long areaMaeId, String areaMaeNome)
	{
		this.setId(id);
		this.nome = nome;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		this.desligado = desligado;
		
		if (nome.equals(nomeComercial))
			this.nomeComercial = "";
		else
			this.nomeComercial = " (" + nomeComercial + ")";

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

	public Colaborador(Long id, String nome, Date dataNascimento, Character sexo, String escolaridade, int somaCompetencias)
	{
		this.setId(id);
		this.nome = nome;
		setProjectionDataNascimento(dataNascimento);
		setProjectionSexo(sexo);
		setPessoalEscolaridade(escolaridade);
		this.somaCompetencias = somaCompetencias;
	}
	
	// usado em findByAreasOrganizacionaisEstabelecimentos
	public Colaborador(Long id, String nome, String nomeComercial, Long historicoAreaId, String historicoAreaNome,Long historicoAreaMaeId, String historicoAreaMaeNome, Long historicoEstabelecimentoId, String historicoEstabelecimentoNome, Long historicoFaixaSalarialId, String historicoFaixaSalarialNome)
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
		
		this.faixaSalarial = new FaixaSalarial();
		this.faixaSalarial.setId(historicoFaixaSalarialId);
		this.faixaSalarial.setNome(historicoFaixaSalarialNome);
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
		this.funcao = historicoColaborador.getFuncao();
		this.naoIntegraAc = colaborador.isNaoIntegraAc();
		this.camposExtras = colaborador.getCamposExtras();
		this.solicitacao = colaborador.getSolicitacao();
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
						String nomeComercial,  String matricula, Boolean desligado, Date dataAdmissao, Date dataDesligamento, String vinculo, boolean naoIntegraAc,  String cursos,
						String estadoCivil, String escolaridade, String mae, String pai, String cpf, String pis, String rg, 
						String rgOrgaoEmissor, Character deficiencia, Date rgDataExpedicao, Character sexo, 
						Date dataNascimento, String conjuge, Integer qtdFilhos, String ctpsNumero, String ctpsSerie, Character ctpsDv, String numeroHab, Date emissao, 
						Date vencimento, String categoria, String logradouro, String complemento, String numero, 
						String bairro, String cep, String email, String foneCelular, String foneFixo, String funcaoNome, String ambienteNome,  
						String cidadeNome, String ufSigla, Date afastamentoInicio, Date afastamentoFim, String candIndicadoPor, 
						String texto1,  String texto2,  String texto3,  String texto4,  String texto5,  String texto6,  String texto7,  String texto8,  String texto9,  String texto10,
						Date data1,  Date data2,  Date data3,  Double valor1,  Double valor2,  Integer numero1  
					   ) 
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
		
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
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
		
		if (candidato == null)
			this.candidato = new Candidato();
		if(candidato.getPessoal() == null)
			candidato.setPessoal(new Pessoal());
		
		candidato.getPessoal().setIndicadoPor(candIndicadoPor);

		
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
		this.getCamposExtras().setTexto9(texto9);
		this.getCamposExtras().setTexto10(texto10);
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
			String nomeComercial,  String matricula, Boolean desligado, Date dataAdmissao, Date dataDesligamento, String vinculo, boolean naoIntegraAc, String cursos,
			String estadoCivil, String escolaridade, String mae, String pai, String cpf, String pis, String rg, 
			String rgOrgaoEmissor, Character deficiencia, Date rgDataExpedicao, Character sexo, 
			Date dataNascimento, String conjuge, Integer qtdFilhos, String ctpsNumero, String ctpsSerie, Character ctpsDv, String numeroHab, Date emissao, 
			Date vencimento, String categoria, String logradouro, String complemento, String numero, 
			String bairro, String cep, String email, String foneCelular, String foneFixo, String funcaoNome, String ambienteNome, 
			String cidadeNome, String ufSigla, Date afastamentoInicio, Date afastamentoFim, String candIndicadoPor) 
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
		
		if (this.funcao == null)
			this.funcao =  new Funcao();
		this.funcao.setNome(funcaoNome);

		if (this.ambiente == null)
			this.ambiente =  new Ambiente();
		this.ambiente.setNome(ambienteNome);
		
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
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
	
	//usado por findByIdHistoricoAtual 
	public Colaborador(Long id, String nome, String nomeComercial, Long areaOrganizacionalId, String areaOrganizacionalNome, String faixaSalarialNome, String cargoNome, Date dataAdmissao, String estabelecimentoNome, String ddd, String foneFixo, String celular)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.setAreaOrganizacionalId(areaOrganizacionalId);
		this.setAreaOrganizacionalNome(areaOrganizacionalNome);
		this.setFaixaSalarialNomeProjection(faixaSalarialNome);
		this.setCargoNomeProjection(cargoNome);
		this.setEstabelecimentoNomeProjection(estabelecimentoNome);
		this.setDataAdmissao(dataAdmissao);
		this.setContatoDdd(ddd);
		this.setContatoFoneFixo(foneFixo);
		this.setContatoCelular(celular);
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
	public Colaborador (Long id, String nome, String nomeComercial, String matricula, Date dataAdmissao, Integer statusRetornoAC, AreaOrganizacional areaOrganizacional, Cargo cargo, FaixaSalarial faixaSalarial, Empresa empresa)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		this.matricula = matricula;
		this.dataAdmissao = dataAdmissao;
		setAreaOrganizacional(areaOrganizacional);
		setEmpresa(empresa);

		if(this.faixaSalarial == null)
			setFaixaSalarial(faixaSalarial);
			
		this.getFaixaSalarial().setCargo(cargo);

		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		this.historicoColaborador.setStatus(statusRetornoAC);
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
	public Colaborador(Long id, String nome, String nomeComercial, String nomeAvaliador, Date respondidaEm, Double performance, boolean anonima, Long avaliacaoDesempenhoId, String avaliacaoDesempenhoTitulo, String nomeEmpresa)
	{
		this.setId(id);
		this.nome = nome;
		this.nomeComercial = nomeComercial;
		if(anonima)
			this.nomeAvaliador = " - ";
		else
			this.nomeAvaliador = nomeAvaliador;
		
		this.respondidaEm = respondidaEm;
		this.performance = performance;
		this.avaliacaoDesempenhoTitulo = avaliacaoDesempenhoTitulo;
		this.avaliacaoDesempenhoId = avaliacaoDesempenhoId;//para o relatório em XLS
		
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

	public Colaborador(String nome) {
		this.nome = nome;
	}
	
		private void setFaixaSalarialHistoricoStatusProjection(Integer faixaSalarialHistoricoStatus)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setStatus(faixaSalarialHistoricoStatus);
	}
	
	private void setProjectionCamposExtrasId(Long projectionCamposExtrasId)
	{
		if(this.camposExtras == null)
			this.camposExtras = new CamposExtras();
		
		this.camposExtras.setId(projectionCamposExtrasId);
	}
		
	private void setHistoricoColaboradorStatusProjection(Integer historicoColaboradorStatus)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		this.getHistoricoColaborador().setStatus(historicoColaboradorStatus);
	}

	private void setFaixaSalarialHistoricoIndiceHistoricoValorProjection(Double faixaSalarialHistoricoIndiceHistoricoValor)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

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
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setQuantidade(faixaSalarialHistoricoQuantidade);
	}

	private void setFaixaSalarialHistoricoValorProjection(Double faixaSalarialHistoricoValor)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setValor(faixaSalarialHistoricoValor);
	}

	private void setFaixaSalarialHistoricoTipoProjection(Integer faixaSalarialHistoricoTipo)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		if(this.getHistoricoColaborador().getFaixaSalarial() == null)
			this.getHistoricoColaborador().setFaixaSalarial(new FaixaSalarial());

		if(this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual() == null)
			this.getHistoricoColaborador().getFaixaSalarial().setFaixaSalarialHistoricoAtual(new FaixaSalarialHistorico());

		this.getHistoricoColaborador().getFaixaSalarial().getFaixaSalarialHistoricoAtual().setTipo(faixaSalarialHistoricoTipo);
	}

	private void setHistoricoColaboradorIndiceHistoricoValorProjection(Double historicoColaboradorIndiceHistoricoValor)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		if(this.getHistoricoColaborador().getIndice() == null)
			this.getHistoricoColaborador().setIndice(new Indice());

		if(this.getHistoricoColaborador().getIndice().getIndiceHistoricoAtual() == null)
			this.getHistoricoColaborador().getIndice().setIndiceHistoricoAtual(new IndiceHistorico());

		this.getHistoricoColaborador().getIndice().getIndiceHistoricoAtual().setValor(historicoColaboradorIndiceHistoricoValor);
	}

	private void setHistoricoColaboradorQuantidadeIndiceProjection(Double historicoColaboradorQuantidadeIndice)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		this.getHistoricoColaborador().setQuantidadeIndice(historicoColaboradorQuantidadeIndice);
	}

	private void setHistoricoColaboradorSalarioProjection(Double historicoColaboradorSalario)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		this.getHistoricoColaborador().setSalario(historicoColaboradorSalario);
	}

	private void setHistoricoColaboradorTipoSalarioProjection(Integer historicoColaboradorTipoSalario)
	{
		if(this.historicoColaborador == null)
			this.setHistoricoColaborador(new HistoricoColaborador());

		this.getHistoricoColaborador().setTipoSalario(historicoColaboradorTipoSalario);
	}

	private void setGrupoIdProjection(Long grupoId)
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		if(this.getFaixaSalarial().getCargo().getGrupoOcupacional() == null)
			this.getFaixaSalarial().getCargo().setGrupoOcupacional(new GrupoOcupacional());

		this.getFaixaSalarial().getCargo().getGrupoOcupacional().setId(grupoId);
	}

	private void setGrupoNomeProjection(String grupoNome)
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		if(this.getFaixaSalarial().getCargo().getGrupoOcupacional() == null)
			this.getFaixaSalarial().getCargo().setGrupoOcupacional(new GrupoOcupacional());

		this.getFaixaSalarial().getCargo().getGrupoOcupacional().setNome(grupoNome);
	}

	public void setCargoIdProjection(Long cargoId)
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		this.getFaixaSalarial().getCargo().setId(cargoId);
	}

	public void setCargoNomeProjection(String cargoNome)
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());

		if(this.getFaixaSalarial().getCargo() == null)
			this.getFaixaSalarial().setCargo(new Cargo());

		this.getFaixaSalarial().getCargo().setNome(cargoNome);
	}

	public void setFaixaSalarialNomeProjection(String faixaSalarialNome)
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());

		this.getFaixaSalarial().setNome(faixaSalarialNome);
	}

	public void setFaixaSalarialIdProjection(Long faixaSalarialId)
	{
		if(this.faixaSalarial == null)
			this.setFaixaSalarial(new FaixaSalarial());

		this.getFaixaSalarial().setId(faixaSalarialId);
	}

	public void setEstabelecimentoNomeProjection(String estabelecimentoNome)
	{
		if(this.estabelecimento == null)
			this.setEstabelecimento(new Estabelecimento());

		this.getEstabelecimento().setNome(estabelecimentoNome);
	}

	public void setEstabelecimentoIdProjection(Long estabelecimentoId)
	{
		if(this.estabelecimento == null)
			this.setEstabelecimento(new Estabelecimento());
		
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
	
	public void setProjectionTexto9(String texto9)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto9(texto9);
	}
	
	public void setProjectionTexto10(String texto10)
	{
		inicializaCamposExtras();
		this.camposExtras.setTexto10(texto10);
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

	public void setContatoCelular(String celular)
	{
		if (this.contato == null)
			this.contato = new Contato();
		this.contato.setFoneCelular(celular);
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
		if (usuario == null)
			usuario = new Usuario();
		usuario.setSenha(usuarioSenha);
	}

	public void setUsuarioIdProjection(Long usuarioIdProjection)
	{
		if (usuario == null)
			usuario = new Usuario();
		usuario.setId(usuarioIdProjection);
	}

	public void setUsuarioNomeProjection(String usuarioNome)
	{
		if (usuario == null)
			usuario = new Usuario();
		usuario.setNome(usuarioNome);
	}

	public void setAreaOrganizacionalId(Long id)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setId(id);
	}

	public void setAreaOrganizacionalNome(String nome)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setNome(nome);
	}

	public void setAreaOrganizacionalDescricao(String nome)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		areaOrganizacional.setDescricao(nome);
	}
	
	public void setAreaOrganizacionalAreaMaeId(Long areaMaeId)
	{
		if (areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
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
	public Collection<HistoricoColaborador> getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}
	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors)
	{
		this.historicoColaboradors = historicoColaboradors;
	}
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

	public Collection<SolicitacaoExame> getSolicitacaoExames()
	{
		return solicitacaoExames;
	}

	public void setSolicitacaoExames(Collection<SolicitacaoExame> solicitacaoExames)
	{
		this.solicitacaoExames = solicitacaoExames;
	}

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
	
	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

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
	@NaoAudita
	public String getAdmitidoHa() 
	{
		return diasDeEmpresa + " dias";
	}
	
	public String getSugestaoPeriodoAcompanhamentoExperiencia() {
		return sugestaoPeriodoAcompanhamentoExperiencia;
	}

	public void setSugestaoPeriodoAcompanhamentoExperiencia(String sugestaoPeriodoAcompanhamentoExperiencia) {
		this.sugestaoPeriodoAcompanhamentoExperiencia = sugestaoPeriodoAcompanhamentoExperiencia;
	}

	public Integer getDiasDeEmpresa() {
		return diasDeEmpresa;
	}

	public void setDiasDeEmpresa(Integer diasDeEmpresa) {
		this.diasDeEmpresa = diasDeEmpresa;
	}

	public Collection<ColaboradorQuestionario> getColaboradorQuestionarios() {
		return colaboradorQuestionarios;
	}

	public void setColaboradorQuestionarios(Collection<ColaboradorQuestionario> colaboradorQuestionarios) {
		this.colaboradorQuestionarios = colaboradorQuestionarios;
	}

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

	public Long getAvaliacaoDesempenhoId() {
		return avaliacaoDesempenhoId;
	}

	public String getAvaliacaoTitulo() {
		return avaliacaoTitulo;
	}

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
	public String getTipoSalarioDescricao()
	{
		return com.fortes.rh.model.dicionario.TipoAplicacaoIndice.getDescricao(historicoColaborador.getTipoSalario());
	}
	
	@NaoAudita
	public String getSalarioHistoricoFormatado() 
	{
		NumberFormat formata = new DecimalFormat("#,##0.00");
		return formata.format(historicoColaborador.getSalarioCalculado()).toString(); 
	}
	@NaoAudita
	public AreaOrganizacional getAreaOrganizacionalMatriarca() {
		return areaOrganizacionalMatriarca;
	}

	public void setAreaOrganizacionalMatriarca(AreaOrganizacional areaOrganizacionalMatriarca) {
		this.areaOrganizacionalMatriarca = areaOrganizacionalMatriarca;
	}

	public String getNomeAvaliador() {
		return nomeAvaliador;
	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public String getAvaliacaoDesempenhoTitulo() {
		return avaliacaoDesempenhoTitulo;
	}

	public String getStatusAvaliacao() {
		return statusAvaliacao;
	}

	public void setStatusAvaliacao(String statusAvaliacao) {
		this.statusAvaliacao = statusAvaliacao;
	}

	public Date getDataSolicitacaoDesligamento() {
		return dataSolicitacaoDesligamento;
	}

	public void setDataSolicitacaoDesligamento(Date dataSolicitacaoDesligamento) {
		this.dataSolicitacaoDesligamento = dataSolicitacaoDesligamento;
	}
	
	public Date getDataSolicitacaoDesligamentoAc() {
		return dataSolicitacaoDesligamentoAc;
	}

	public void setDataSolicitacaoDesligamentoAc(Date dataSolicitacaoDesligamentoAc) {
		this.dataSolicitacaoDesligamentoAc = dataSolicitacaoDesligamentoAc;
	}

	public boolean isExibePerformanceProficional() {
		return exibePerformanceProficional;
	}

	public void setExibePerformanceProficional(boolean exibePerformanceProficional) {
		this.exibePerformanceProficional = exibePerformanceProficional;
	}

	public int getSomaCompetencias() {
		return somaCompetencias;
	}

	public void setSomaCompetencias(int somaCompetencias) {
		this.somaCompetencias = somaCompetencias;
	}

	public Collection<ConfiguracaoNivelCompetenciaColaborador> getConfiguracaoNivelCompetenciaColaboradors() {
		return configuracaoNivelCompetenciaColaboradors;
	}

	public void setConfiguracaoNivelCompetenciaColaboradors(Collection<ConfiguracaoNivelCompetenciaColaborador> configuracaoNivelCompetenciaColaboradors) {
		this.configuracaoNivelCompetenciaColaboradors = configuracaoNivelCompetenciaColaboradors;
	}

	public double getPercentualCompatibilidade() {
		return percentualCompatibilidade;
	}

	public void setPercentualCompatibilidade(double percentualCompatibilidade) {
		this.percentualCompatibilidade = percentualCompatibilidade;
	}

	
	public Double getMediaPerformance()
	{
		return mediaPerformance;
	}
	
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

	public Integer getTempoServico() {
		return tempoServico;
	}

	public void setTempoServico(Integer tempoServico) {
		this.tempoServico = tempoServico;
	}

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

	public Collection<ColaboradorPeriodoExperienciaAvaliacao> getColaboradorPeriodoExperienciaAvaliacaos() 
	{
		return colaboradorPeriodoExperienciaAvaliacaos;
	}

	public void setColaboradorPeriodoExperienciaAvaliacaos(Collection<ColaboradorPeriodoExperienciaAvaliacao> colaboradorPeriodoExperienciaAvaliacaos) 
	{
		this.colaboradorPeriodoExperienciaAvaliacaos = colaboradorPeriodoExperienciaAvaliacaos;
	}

	public Long getAvaliacaoId() {
		return avaliacaoId;
	}

	public void setAvaliacaoId(Long avaliacaoId) 
	{
		this.avaliacaoId = avaliacaoId;
	}

	public String getTempoServicoString() 
	{
		return tempoServicoString;
	}

	public void setTempoServicoString(String tempoServicoString) 
	{
		this.tempoServicoString = tempoServicoString;
	}

	public String getAfastadoString() {
		return afastado ? "Sim" : "Não";
	}

	public Boolean getAfastado() {
		return afastado;
	}

	public void setAfastado(Boolean afastado) {
		this.afastado = afastado;
	}

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

	public boolean isStatusAcPessoalConfirmado() {
		return statusAcPessoalConfirmado;
	}

	public void setStatusAcPessoalConfirmado(boolean statusAcPessoalConfirmado) {
		this.statusAcPessoalConfirmado = statusAcPessoalConfirmado;
	}
}
