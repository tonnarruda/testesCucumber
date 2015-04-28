package com.fortes.rh.web.action.desenvolvimento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import com.fortes.model.type.File;
import com.fortes.rh.business.desenvolvimento.AproveitamentoAvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorPresencaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.desenvolvimento.DNTManager;
import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.TipoDespesaManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.DNT;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.FiltroPlanoTreinamento;
import com.fortes.rh.model.desenvolvimento.PrioridadeTreinamento;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.desenvolvimento.TurmaAvaliacaoTurma;
import com.fortes.rh.model.desenvolvimento.relatorio.CertificacaoTreinamentosRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.ColaboradorCursoMatriz;
import com.fortes.rh.model.desenvolvimento.relatorio.SomatorioCursoMatriz;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.TipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class TurmaEditAction extends MyActionSupportList implements ModelDriven
{
	private Map<String,Object> parametros = new HashMap<String, Object>();

	private TurmaManager turmaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorPresencaManager colaboradorPresencaManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private CursoManager cursoManager;
	private DiaTurmaManager diaTurmaManager;
	private DNTManager dNTManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AvaliacaoCursoManager avaliacaoCursoManager;
	private AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;
	private TipoDespesaManager tipoDespesaManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;

	private Turma turma;
	private Colaborador colaborador;

	private Collection<ColaboradorTurma> colaboradorTurmasLista;
	private Collection<ColaboradorPresenca> colaboradorPresencas;
	private Collection<DiaTurma> diaTurmas;
	private Collection<Colaborador> colaboradors;
	private Collection<Turma> turmas;
	private Collection emptyData = new ArrayList();
	private Collection<Curso> cursos = new ArrayList<Curso>();
	private Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
	private Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
	private Collection<ColaboradorTurma> colaboradoresTurma = new ArrayList<ColaboradorTurma>();
	private Collection<Empresa> empresas;
	private Collection<TipoDespesa> tipoDespesas;
	
	private String[] areasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private Collection<PrioridadeTreinamento> prioridadeTreinamentos;

	private boolean semPlano;
	private Collection<Certificado> dataSource;

	private String[] selectPrioridades;
	private String[] colaboradorTurma;

	private Curso curso;

	private String voltarPara;

	private String[] diasCheck;
	private Collection<CheckBox> diasCheckList = new ArrayList<CheckBox>();

	private Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs = new ArrayList<ColaboradorCursoMatriz>();
	private Collection<SomatorioCursoMatriz> somatorioCursoMatrizs = new ArrayList<SomatorioCursoMatriz>();
	private Collection<DNT> dnts = new HashSet<DNT>();

	private DNT dnt;
	private String data;

	private Date dataIni;
	private Date dataFim;

	private String[] colaboradoresCheck;
	private Collection<CheckBox> colaboradoresCheckList;
	private String[] avaliacaoTurmasCheck;
	private Collection<CheckBox> avaliacaoTurmasCheckList;
	
	private Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
	private Collection<AproveitamentoAvaliacaoCurso> aproveitamentos;
	private Collection<CertificacaoTreinamentosRelatorio> certificacaoTreinamentos = new ArrayList<CertificacaoTreinamentosRelatorio>();

	private String dataCompleta;
	private String responsavel;
	private Long empresaId;

	private int page = 1;
	private int pagingSize = 15;
	private int totalSize;

	private AvaliacaoCurso avaliacaoCurso;

	private FiltroPlanoTreinamento filtroPlanoTreinamento;
	private String[] notas;
	private Long[] colaboradorTurmaIds;

	private Long[] colaboradorTurmasIds;
	// Indica se a requisição veio do plano de treinamento
	private boolean planoTreinamento;

	// modelos de avaliacao de turma
	private Collection<AvaliacaoTurma> avaliacaoTurmas = new ArrayList<AvaliacaoTurma>(0);
	private AvaliacaoTurma avaliacaoTurma;
	private boolean avaliacaoRespondida;
	
	private Collection<TurmaAvaliacaoTurma> turmaAvaliacaoTurmas = new ArrayList<TurmaAvaliacaoTurma>();
	private TurmaAvaliacaoTurma turmaAvaliacaoTurma;

	private char certificadoDe = 'T';
	private Certificacao certificacao;
	private Certificado certificado;
	private char realizada;
	private String custos;
	private boolean contemCustosDetalhados = false;
	private boolean turmaPertenceAEmpresaLogada = true;
	private boolean exibirAssinaturaDigital;
	private boolean manterAssinatura;
	private boolean imprimirNotaNoVerso;

	private Map<Long, String> despesas = new HashMap<Long, String>();
	private String[] horariosIni;
	private String[] horariosFim;
	
	private File assinaturaDigital;
	
	public AvaliacaoTurma getAvaliacaoTurma()
	{
		return avaliacaoTurma;
	}

	public void setAvaliacaoTurma(AvaliacaoTurma avaliacaoTurma)
	{
		this.avaliacaoTurma = avaliacaoTurma;
	}

	public Collection<AvaliacaoTurma> getAvaliacaoTurmas()
	{
		return avaliacaoTurmas;
	}

	public void setAvaliacaoTurmas(Collection<AvaliacaoTurma> avaliacaoTurmas)
	{
		this.avaliacaoTurmas = avaliacaoTurmas;
	}

	public void prepare() throws Exception
	{
		if (turma != null && turma.getId() != null)
			turma = (Turma) turmaManager.findByIdProjection(turma.getId());

		cursos = cursoManager.findAllByEmpresasParticipantes(getEmpresaSistema().getId());
	
		tipoDespesas = tipoDespesaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao"});
		
		avaliacaoTurmas = avaliacaoTurmaManager.findAllSelect(true, getEmpresaSistema().getId());
		avaliacaoTurmasCheckList = CheckListBoxUtil.populaCheckListBox(avaliacaoTurmas, "getId", "getQuestionarioTitulo");
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		if (filtroPlanoTreinamento != null && filtroPlanoTreinamento.getCursoId() != null)
			turma.setCursoId(filtroPlanoTreinamento.getCursoId());
		
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		
		if (curso != null && !cursoManager.existeEmpresasNoCurso(getEmpresaSistema().getId(), curso.getId()))
		{
			addActionWarning("A turma solicitada não existe ou não está compartilhada para a empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		if(!turma.getEmpresa().equals(getEmpresaSistema()))
			turmaPertenceAEmpresaLogada = false;
		
		avaliacaoRespondida = turmaManager.verificaAvaliacaoDeTurmaRespondida(turma.getId());
		
		if(!avaliacaoRespondida)
			avaliacaoRespondida = turmaAvaliacaoTurmaManager.verificaAvaliacaoliberada(turma.getId());

		diaTurmas = diaTurmaManager.find(new String[] { "turma.id" }, new Object[] { turma.getId() });

		Collection<AvaliacaoTurma> avaliacaoTurmasMarcadas = avaliacaoTurmaManager.findByTurma(turma.getId());
		avaliacaoTurmasCheckList = CheckListBoxUtil.marcaCheckListBoxString(avaliacaoTurmasCheckList, avaliacaoTurmasMarcadas, "getQuestionarioTitulo");
				
		turma.setTemPresenca(colaboradorPresencaManager.existPresencaByTurma(turma.getId()));
		contemCustosDetalhados = !turmaTipoDespesaManager.findTipoDespesaTurma(turma.getId()).isEmpty();

		return Action.SUCCESS;
	}

	public String prepareFiltroEstabelecimentoAreaOrganizacional() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());

		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		turma.setEmpresa(getEmpresaSistema());

		turma = turmaManager.setAssinaturaDigital(false, turma, assinaturaDigital, "assinaturas");
		
		turmaManager.inserir(turma, diasCheck, custos, LongUtil.arrayStringToArrayLong(avaliacaoTurmasCheck), horariosIni, horariosFim);
		
		return planoTreinamento ? "successFiltroPlanoTreinamento" : Action.SUCCESS;
	}

	public String update() throws Exception
	{
		turma = turmaManager.setAssinaturaDigital(manterAssinatura, turma, assinaturaDigital, "assinaturas");
		
		turmaManager.atualizar(turma, diasCheck, horariosIni, horariosFim, colaboradorTurma, selectPrioridades, LongUtil.arrayStringToArrayLong(avaliacaoTurmasCheck), getEmpresaSistema().getId().equals(turma.getEmpresa().getId()));
		
		return planoTreinamento ? "successFiltroPlanoTreinamento" : Action.SUCCESS;
	}
	
	public String showAssinatura() throws Exception
	{
		if (turma.getAssinaturaDigitalUrl() != null && !turma.getAssinaturaDigitalUrl().equals(""))
		{
			java.io.File file = ArquivoUtil.getArquivo(turma.getAssinaturaDigitalUrl(),"assinaturas");
			HttpServletResponse response = ServletActionContext.getResponse();
			
			ArquivoUtil.showFile(file, response);
		}
		
		return Action.SUCCESS;
	}
	
	public String prepareImprimirTurma() throws Exception
	{

		dnts = dNTManager.findToList(new String[] { "id", "nome" }, new String[] { "id", "nome" }, new String[] { "empresa.id" },
				new Object[] { getEmpresaSistema().getId() }, new String[] { "data desc" });

		prepareFiltroEstabelecimentoAreaOrganizacional();

		return Action.SUCCESS;
	}

	public String preparePresenca() throws Exception
	{
		diaTurmas = diaTurmaManager.findByTurma(turma.getId());

		if (diaTurmas.isEmpty())
			addActionMessage("Não existe previsão de dias para esta turma.");

		colaboradorTurmasLista = colaboradorTurmaManager.findByTurma(turma.getId(), null, true, null, null);
		CollectionUtil<ColaboradorTurma> util = new CollectionUtil<ColaboradorTurma>();
		colaboradorTurmasIds = util.convertCollectionToArrayIds(colaboradorTurmasLista);

		turma = turmaManager.findByIdProjection(turma.getId());
		colaboradorPresencas = colaboradorPresencaManager.findPresencaByTurma(turma.getId());

		return Action.SUCCESS;
	}

	public String prepareFrequencia() throws Exception
	{
		cursos = cursoManager.find(new String[] { "empresa.id" }, new Object[] { getEmpresaSistema().getId() }, new String[] { "nome" });
		return Action.SUCCESS;
	}

	public String verTurmasCurso() throws Exception
	{
		String[] keys = new String[] { "curso.id" };
		Object[] values = new Object[] { curso.getId() };
		String[] orders = new String[] { "descricao" };
			
		setTotalSize(turmaManager.getCount(keys, values));
		turmas = turmaManager.find(getPage(), getPagingSize(), keys, values, orders);
		
		if (turmas.isEmpty())
		{
			addActionMessage("Não existe turma para o curso informado.<br>");
		}

		prepareFrequencia();

		return Action.SUCCESS;
	}

	public String insertPresenca() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareRelatorio() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareImprimirCertificado() throws Exception
	{
		empresaId = getEmpresaSistema().getId();
		cursos = cursoManager.findAllByEmpresasParticipantes(getEmpresaSistema().getId());
		certificacaos = certificacaoManager.findAllSelect(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}

	public String getColaboradoresByFiltro() throws Exception
	{
		if(certificadoDe == 'T')
		{
			colaboradores = colaboradorTurmaManager.montaExibicaoAprovadosReprovados(null, turma.getId());
			turma = turmaManager.findByIdProjection(turma.getId());
			certificado = new Certificado(turma, empresaManager.findCidade(getEmpresaSistema().getId()), true, true);
		}
		else
		{
			turma = null;
			cursos = cursoManager.findByCertificacao(certificacao.getId());
			colaboradores = colaboradorTurmaManager.findAprovadosByCertificacao(certificacao, cursos == null?0:cursos.size());
			certificacao = certificacaoManager.findByIdProjection(certificacao.getId());
			certificado = new Certificado(cursos, certificacao, empresaManager.findCidade(getEmpresaSistema().getId()), true, true);
		}

		if(colaboradores.isEmpty())
			addActionMessage("Não existem Colaboradores aprovados.");
		else
			colaboradoresCheckList = CheckListBoxUtil.populaCheckListBox(colaboradores, "getId", "getNome");

		cursos = cursoManager.findAllSelect(getEmpresaSistema().getId());
		certificacaos = certificacaoManager.findAllSelect(getEmpresaSistema().getId());


		if (curso != null && curso.getId() != null)
			turmas = turmaManager.findAllSelect(curso.getId());

		return Action.SUCCESS;
	}

	public String imprimirCertificado() throws Exception
	{
		if (colaboradoresCheck == null || colaboradoresCheck.length <= 0)
		{
			prepareImprimirCertificado();
			return Action.INPUT;
		}
		
		String EscolhaDeLogo = getEmpresaSistema().getLogoCertificadoUrl() == null ? getEmpresaSistema().getLogoUrl():  getEmpresaSistema().getLogoCertificadoUrl();
		String logo = ArquivoUtil.getPathLogoEmpresa() + EscolhaDeLogo;
		String path = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separator;
		parametros.put("LOGO", logo);
		parametros.put("PATH_IMG", path);
		parametros.put("EXIBIRASSINSTRUTOR", exibirAssinaturaDigital);
		
		if(exibirAssinaturaDigital)
		{
			String assinaturaDigitalUrl = ArquivoUtil.getPathAssinaturas() + turma.getAssinaturaDigitalUrl();
			parametros.put("ASS", assinaturaDigitalUrl);
			parametros.put("NOMEINSTRUTOR", turma.getInstrutor() != null && !"".equals(turma.getInstrutor()) ? turma.getInstrutor() + "\nInstrutor" : "");
		}
		
		colaboradores = colaboradorManager.findComNotaDoCurso(LongUtil.arrayStringToCollectionLong(colaboradoresCheck), turma.getId());
		dataSource = colaboradorTurmaManager.montaCertificados(colaboradores, certificado, getEmpresaSistema().getId());

		if(certificado.getTamanho().equals("declaracao"))
			return "successDeclaracao";
		else if(certificado.getTamanho().equals("1"))
			return "successGrande";
		else
			return "successPequeno";
	}

	public String imprimirCertificadoVerso() throws Exception
	{
		if (colaboradoresCheck == null || colaboradoresCheck.length <= 0)
		{
			prepareImprimirCertificado();
			return Action.INPUT;
		}

		String path = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separator;
		parametros.put("PATH_IMG", path);
		parametros.put("CONTEUDO_PROGRAMATICO", cursoManager.getConteudoProgramatico(curso.getId()));
		parametros.put("IMPRIMIR_NOTA_VERSO", imprimirNotaNoVerso);
		
		colaboradores = colaboradorManager.findComNotaDoCurso(LongUtil.arrayStringToCollectionLong(colaboradoresCheck), turma.getId());
		dataSource = Certificado.gerarColecaoVerso(colaboradores, certificado);

		if(certificado.getTamanho().equals("1"))
			return "successGrande";
		else
			return "successPequeno";

	}

	public String imprimirCertificacaoVerso() throws Exception
	{
		if (colaboradoresCheck == null || colaboradoresCheck.length <= 0)
		{
			prepareImprimirCertificado();
			return Action.INPUT;
		}

		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/") + java.io.File.separator;
		String pathImg = ServletActionContext.getServletContext().getRealPath("/imgs/") + java.io.File.separator;

		certificacao = certificacaoManager.findByIdProjection(certificacao.getId());

		parametros.put("SUBREPORT_DIR", path);
		parametros.put("PATH_IMG", pathImg);
		parametros.put("CERTIFICACAO", certificacao.getNome());

		cursos = cursoManager.findByCertificacao(certificacao.getId());
		parametros.put("CURSOS", cursos);
		certificacaoTreinamentos = Certificado.montaCertificacao(colaboradoresCheck, certificado, cursos);

		if(certificado.getTamanho().equals("1"))
			return "successGrande";
		else
			return "successPequeno";

	}

	public String prepareAproveitamento()
	{
		avaliacaoCursos = avaliacaoCursoManager.findByCurso(curso.getId());
		if(avaliacaoCursos.isEmpty())
			addActionMessage("Não existe avaliação para esta turma.");

		if(avaliacaoCurso != null && !avaliacaoCurso.getId().equals(-1L))
		{
			colaboradoresTurma = colaboradorTurmaManager.findColaboradorByTurma(turma.getId(), avaliacaoCurso.getId());

			if(colaboradoresTurma.isEmpty())
				addActionMessage("Não existe colaborador inscrito nesta turma.");
			else
			{
				avaliacaoCurso = avaliacaoCursoManager.findById(avaliacaoCurso.getId());
				CollectionUtil<ColaboradorTurma> util = new CollectionUtil<ColaboradorTurma>();
				aproveitamentos = aproveitamentoAvaliacaoCursoManager.findNotas(avaliacaoCurso.getId(), util.convertCollectionToArrayIds(colaboradoresTurma));
			}
		}

		return Action.SUCCESS;
	}

	public String saveAproveitamentoCurso()
	{
		try
		{
			aproveitamentoAvaliacaoCursoManager.saveNotas(colaboradorTurmaIds, notas, avaliacaoCurso);
			addActionSuccess("Aproveitamento/Notas salvos com sucesso.");
			prepareAproveitamento();
			return Action.SUCCESS;
		}
		catch (NumberFormatException e)
		{
			addActionWarning("Aproveitamento/Notas não foram salvos pois existem notas inválidas.");
			prepareAproveitamento();
			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao salvar aproveitamento/notas");
			prepareAproveitamento();
			return Action.INPUT;
		}
	}

	public String updateAproveitamento()
	{
		return Action.SUCCESS;
	}

	public String prepareImprimirMatriz() throws Exception
	{
		return prepareFiltroEstabelecimentoAreaOrganizacional();
	}

	@SuppressWarnings("unchecked")
	public String imprimirMatriz() throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		Date date;
		try
		{
			date = dateFormat.parse(getData());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Data inválida.");
			prepareImprimirMatriz();
			return INPUT;
		}

		LinkedHashMap filtro = new LinkedHashMap();
		if (date != null)
			filtro.put("data", date);

		filtro.put("areas", LongUtil.arrayStringToCollectionLong(areasCheck));
		filtro.put("estabelecimentos", LongUtil.arrayStringToCollectionLong(estabelecimentosCheck));

		colaboradorTurmasLista = colaboradorTurmaManager.filtroRelatorioMatriz(filtro);

		if (colaboradorTurmasLista == null || colaboradorTurmasLista.size() == 0)
		{
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			addActionMessage(bundle.getString("error.relatorio.vazio"));

			prepareImprimirMatriz();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			return Action.INPUT;
		}

		colaboradorCursoMatrizs = colaboradorTurmaManager.montaMatriz(colaboradorTurmasLista);
		cursos = colaboradorTurmaManager.getListaCursos(colaboradorTurmasLista);
		somatorioCursoMatrizs = colaboradorTurmaManager.getSomaPontuacao(colaboradorCursoMatrizs);
		dataIni = date;

		return Action.SUCCESS;
	}

	public String relatorioInvestimentoPorColaborador() throws Exception
	{
		colaboradors = colaboradorManager.findByNomeCpfMatricula(null, getEmpresaSistema().getId(), false, null);
		
		return Action.SUCCESS;
	}
	
	public String imprimirRelatorioInvestimentoPorColaborador() throws Exception
	{
		colaboradors = colaboradorManager.findByNomeCpfMatricula(null, getEmpresaSistema().getId(), false, null);
		if(colaborador == null || colaborador.getId() == null)
		{
			addActionMessage("Colaborador selecionado é inválido.");
			return Action.INPUT;
		}
			
		colaboradoresTurma = colaboradorTurmaManager.findColaboradoresComCustoTreinamentos(colaborador.getId(), dataIni, dataFim, BooleanUtil.getValueCombo(realizada));
		if (colaboradoresTurma.isEmpty()) 
		{
			addActionMessage("Não existem dados para o filtro informado");
			return Action.INPUT;
		}
		
		colaborador = ((ColaboradorTurma)colaboradoresTurma.toArray()[0]).getColaborador();
		parametros = RelatorioUtil.getParametrosRelatorio("Relatorio de Investimento por Colaborador", getEmpresaSistema(), "Colaborador: " + colaborador.getNome() + "\nTurma iniciada entre: " + DateUtil.formataDiaMesAno(dataIni) + " e " + DateUtil.formataDiaMesAno(dataFim));
		
		return Action.SUCCESS;
	}
	
	private boolean assinaturaValida(com.fortes.model.type.File assinatura)
	{
		boolean fotoValida =  true;
		if(assinatura != null)
		{
			if(assinatura.getContentType().length() >= 5)
			{
				if(!assinatura.getContentType().substring(0, 5).equals("image"))
				{
					addActionError("Tipo de arquivo não suportado");
					fotoValida = false;
				}

				else if(assinatura.getSize() > 524288)
				{
					addActionError("Tamanho do arquivo maior que o suportado");
					fotoValida = false;
				}
			}
		}

		return fotoValida;
	}
	
	public Turma getTurma()
	{
		if (turma == null)
			turma = new Turma();
		return turma;
	}

	public void setTurma(Turma turma)
	{
		this.turma = turma;
	}

	public Object getModel()
	{
		return getTurma();
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmasLista()
	{
		return colaboradorTurmasLista;
	}

	public void setColaboradorTurmasLista(Collection<ColaboradorTurma> colaboradorTurmasLista)
	{
		this.colaboradorTurmasLista = colaboradorTurmasLista;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public void setAreasCheckList(Collection<CheckBox> areasCheckList)
	{
		this.areasCheckList = areasCheckList;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public void setColaboradors(Collection<Colaborador> colaboradors)
	{
		this.colaboradors = colaboradors;
	}

	public boolean isSemPlano()
	{
		return semPlano;
	}

	public void setSemPlano(boolean semPlano)
	{
		this.semPlano = semPlano;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<PrioridadeTreinamento> getPrioridadeTreinamentos()
	{
		return prioridadeTreinamentos;
	}

	public void setPrioridadeTreinamentos(Collection<PrioridadeTreinamento> prioridadeTreinamentos)
	{
		this.prioridadeTreinamentos = prioridadeTreinamentos;
	}

	public String[] getColaboradorTurma()
	{
		return colaboradorTurma;
	}

	public void setColaboradorTurma(String[] colaboradorTurma)
	{
		this.colaboradorTurma = colaboradorTurma;
	}

	public String[] getSelectPrioridades()
	{
		return selectPrioridades;
	}

	public void setSelectPrioridades(String[] selectPrioridades)
	{
		this.selectPrioridades = selectPrioridades;
	}

	public Collection<Turma> getTurmas()
	{
		return turmas;
	}

	public void setTurmas(Collection<Turma> turmas)
	{
		this.turmas = turmas;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros)
	{
		this.parametros = parametros;
	}

	public List getEmptyDataSource()
	{
		return new CollectionUtil().convertCollectionToList(emptyData);
	}

	public void setCursoManager(CursoManager cursoManager)
	{
		this.cursoManager = cursoManager;
	}

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}

	public String[] getDiasCheck()
	{
		return diasCheck;
	}

	public void setDiasCheck(String[] diasCheck)
	{
		this.diasCheck = diasCheck;
	}

	public Collection<CheckBox> getDiasCheckList()
	{
		return diasCheckList;
	}

	public void setDiasCheckList(Collection<CheckBox> diasCheckList)
	{
		this.diasCheckList = diasCheckList;
	}

	public void setDiaTurmaManager(DiaTurmaManager diaTurmaManager)
	{
		this.diaTurmaManager = diaTurmaManager;
	}

	public Collection<Curso> getCursos()
	{
		return cursos;
	}

	public void setCursos(Collection<Curso> cursos)
	{
		this.cursos = cursos;
	}

	public Collection<ColaboradorCursoMatriz> getColaboradorCursoMatrizs()
	{
		return colaboradorCursoMatrizs;
	}

	public void setColaboradorCursoMatrizs(Collection<ColaboradorCursoMatriz> colaboradorCursoMatrizs)
	{
		this.colaboradorCursoMatrizs = colaboradorCursoMatrizs;
	}

	public Collection<DiaTurma> getDiaTurmas()
	{
		return diaTurmas;
	}

	public void setDiaTurmas(Collection<DiaTurma> diaTurmas)
	{
		this.diaTurmas = diaTurmas;
	}

	public Collection<ColaboradorPresenca> getColaboradorPresencas()
	{
		return colaboradorPresencas;
	}

	public void setColaboradorPresencas(Collection<ColaboradorPresenca> colaboradorPresencas)
	{
		this.colaboradorPresencas = colaboradorPresencas;
	}

	public void setColaboradorPresencaManager(ColaboradorPresencaManager colaboradorPresencaManager)
	{
		this.colaboradorPresencaManager = colaboradorPresencaManager;
	}

	public Collection<SomatorioCursoMatriz> getSomatorioCursoMatrizs()
	{
		return somatorioCursoMatrizs;
	}

	public void setSomatorioCursoMatrizs(Collection<SomatorioCursoMatriz> somatorioCursoMatrizs)
	{
		this.somatorioCursoMatrizs = somatorioCursoMatrizs;
	}

	public DNT getDnt()
	{
		return dnt;
	}

	public void setDnt(DNT dnt)
	{
		this.dnt = dnt;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Collection<CheckBox> getColaboradoresCheckList()
	{
		return colaboradoresCheckList;
	}

	public void setColaboradoresCheckList(Collection<CheckBox> colaboradoresCheckList)
	{
		this.colaboradoresCheckList = colaboradoresCheckList;
	}

	public Collection<Certificado> getDataSource()
	{
		return dataSource;
	}

	public String[] getColaboradoresCheck()
	{
		return colaboradoresCheck;
	}

	public void setColaboradoresCheck(String[] colaboradoresCheck)
	{
		this.colaboradoresCheck = colaboradoresCheck;
	}

	public String getDataCompleta()
	{
		return dataCompleta;
	}

	public void setDataCompleta(String dataCompleta)
	{
		this.dataCompleta = dataCompleta;
	}

	public String getResponsavel()
	{
		return responsavel;
	}

	public void setResponsavel(String responsavel)
	{
		this.responsavel = responsavel;
	}

	public Collection<DNT> getDnts()
	{
		return dnts;
	}

	public void setDnts(Collection<DNT> dnts)
	{
		this.dnts = dnts;
	}

	public void setDNTManager(DNTManager manager)
	{
		dNTManager = manager;
	}

	public String getVoltarPara()
	{
		return voltarPara;
	}

	public void setVoltarPara(String voltarPara)
	{
		this.voltarPara = voltarPara;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public int getPage()
	{
		return page;
	}

	public int getPagingSize()
	{
		return pagingSize;
	}

	public int getTotalSize()
	{
		return totalSize;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public void setTotalSize(Integer totalSize)
	{
		this.totalSize = totalSize;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public FiltroPlanoTreinamento getFiltroPlanoTreinamento()
	{
		return filtroPlanoTreinamento;
	}

	public void setFiltroPlanoTreinamento(FiltroPlanoTreinamento filtroPlanoTreinamento)
	{
		this.filtroPlanoTreinamento = filtroPlanoTreinamento;
	}

	public Collection<AvaliacaoCurso> getAvaliacaoCursos()
	{
		return avaliacaoCursos;
	}

	public void setAvaliacaoCursoManager(AvaliacaoCursoManager avaliacaoCursoManager)
	{
		this.avaliacaoCursoManager = avaliacaoCursoManager;
	}

	public AvaliacaoCurso getAvaliacaoCurso()
	{
		return avaliacaoCurso;
	}

	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso)
	{
		this.avaliacaoCurso = avaliacaoCurso;
	}

	public void setNotas(String[] notas)
	{
		this.notas = notas;
	}

	public void setAproveitamentoAvaliacaoCursoManager(AproveitamentoAvaliacaoCursoManager aproveitamentoAvaliacaoCursoManager)
	{
		this.aproveitamentoAvaliacaoCursoManager = aproveitamentoAvaliacaoCursoManager;
	}

	public void setColaboradorTurmaIds(Long[] colaboradorTurmaIds)
	{
		this.colaboradorTurmaIds = colaboradorTurmaIds;
	}

	public Collection<AproveitamentoAvaliacaoCurso> getAproveitamentos()
	{
		return aproveitamentos;
	}

	public Long[] getColaboradorTurmasIds()
	{
		return colaboradorTurmasIds;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager)
	{
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}

	public boolean isAvaliacaoRespondida()
	{
		return avaliacaoRespondida;
	}

	public Collection<Certificacao> getCertificacaos()
	{
		return certificacaos;
	}

	public Certificacao getCertificacao()
	{
		return certificacao;
	}

	public void setCertificacao(Certificacao certificacao)
	{
		this.certificacao = certificacao;
	}

	public char getCertificadoDe()
	{
		return certificadoDe;
	}

	public void setCertificadoDe(char certificadoDe)
	{
		this.certificadoDe = certificadoDe;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager)
	{
		this.certificacaoManager = certificacaoManager;
	}

	public Certificado getCertificado()
	{
		return certificado;
	}

	public void setCertificado(Certificado certificado)
	{
		this.certificado = certificado;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<ColaboradorTurma> getColaboradoresTurma()
	{
		return colaboradoresTurma;
	}

	public Collection<CertificacaoTreinamentosRelatorio> getCertificacaoTreinamentos()
	{
		return certificacaoTreinamentos;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public boolean isPlanoTreinamento() {
		return planoTreinamento;
	}

	public void setPlanoTreinamento(boolean planoTreinamento) {
		this.planoTreinamento = planoTreinamento;
	}

	public Collection<Empresa> getEmpresas() {
		return empresas;
	}

	public char getRealizada() {
		return realizada;
	}

	public void setRealizada(char realizada) {
		this.realizada = realizada;
	}

	public String[] getAvaliacaoTurmasCheck() {
		return avaliacaoTurmasCheck;
	}

	public void setAvaliacaoTurmasCheck(String[] avaliacaoTurmasCheck) {
		this.avaliacaoTurmasCheck = avaliacaoTurmasCheck;
	}

	public Collection<CheckBox> getAvaliacaoTurmasCheckList() {
		return avaliacaoTurmasCheckList;
	}

	public void setAvaliacaoTurmasCheckList(
			Collection<CheckBox> avaliacaoTurmasCheckList) {
		this.avaliacaoTurmasCheckList = avaliacaoTurmasCheckList;
	}

	public void setTipoDespesaManager(TipoDespesaManager tipoDespesaManager) {
		this.tipoDespesaManager = tipoDespesaManager;
	}

	public Collection<TipoDespesa> getTipoDespesas() {
		return tipoDespesas;
	}

	public void setTipoDespesas(Collection<TipoDespesa> tipoDespesas) {
		this.tipoDespesas = tipoDespesas;
	}

	public Map<Long, String> getDespesas() {
		return despesas;
	}

	public void setDespesas(Map<Long, String> despesas) {
		this.despesas = despesas;
	}

	public void setCustos(String custos) {
		this.custos = custos;
	}

	public void setTurmaTipoDespesaManager(TurmaTipoDespesaManager turmaTipoDespesaManager) {
		this.turmaTipoDespesaManager = turmaTipoDespesaManager;
	}

	public boolean isContemCustosDetalhados() {
		return contemCustosDetalhados;
	}

	public Collection<TurmaAvaliacaoTurma> getTurmaAvaliacaoTurmas() {
		return turmaAvaliacaoTurmas;
	}

	public void setTurmaAvaliacaoTurmas(Collection<TurmaAvaliacaoTurma> turmaAvaliacaoTurmas) {
		this.turmaAvaliacaoTurmas = turmaAvaliacaoTurmas;
	}

	public TurmaAvaliacaoTurma getTurmaAvaliacaoTurma() {
		return turmaAvaliacaoTurma;
	}

	public void setTurmaAvaliacaoTurma(TurmaAvaliacaoTurma turmaAvaliacaoTurma) {
		this.turmaAvaliacaoTurma = turmaAvaliacaoTurma;
	}

	public void setTurmaAvaliacaoTurmaManager(TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager) {
		this.turmaAvaliacaoTurmaManager = turmaAvaliacaoTurmaManager;
	}
	
	public boolean isTurmaPertenceAEmpresaLogada()
	{
		return turmaPertenceAEmpresaLogada;
	}

	public File getAssinaturaDigital() {
		return assinaturaDigital;
	}

	public void setAssinaturaDigital(File assinaturaDigital) {
		this.assinaturaDigital = assinaturaDigital;
	}

	public boolean isExibirAssinaturaDigital() {
		return exibirAssinaturaDigital;
	}

	public void setExibirAssinaturaDigital(boolean exibirAssinaturaDigital) {
		this.exibirAssinaturaDigital = exibirAssinaturaDigital;
	}

	public void setManterAssinatura(boolean manterAssinatura) {
		this.manterAssinatura = manterAssinatura;
	}

	public void setHorariosIni(String[] horariosIni) {
		this.horariosIni = horariosIni;
	}

	public void setHorariosFim(String[] horariosFim) {
		this.horariosFim = horariosFim;
	}

	public void setImprimirNotaNoVerso(boolean imprimirNotaNoVerso)
	{
		this.imprimirNotaNoVerso = imprimirNotaNoVerso;
	}
}