package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.fortes.model.type.FileUtil;
import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.CandidatoCurriculoManager;
import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoImpressaoCurriculoManager;
import com.fortes.rh.business.captacao.ConhecimentoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.IdiomaManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.geral.AreaFormacaoManager;
import com.fortes.rh.business.geral.AreaInteresseManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.ConfiguracaoImpressaoCurriculo;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Idioma;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.AvaliacaoCandidatosRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Deficiencia;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Estado;
import com.fortes.rh.model.dicionario.NivelIdioma;
import com.fortes.rh.model.dicionario.PesosTriagemAutomatica;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SolicitacaoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaFormacao;
import com.fortes.rh.model.geral.AreaInteresse;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.geral.relatorio.CurriculoCandidatoRelatorio;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CampoExtraUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.EmpresaUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"unchecked","rawtypes"})
public class CandidatoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private Boolean compartilharCadastros;
	private static final byte COLABORADOR = 1;
	private static final byte CANDIDATO = 2;

	private CandidatoManager candidatoManager;
	private ColaboradorManager colaboradorManager;
	private CargoManager cargoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private AnuncioManager anuncioManager;
	private AreaInteresseManager areaInteresseManager;
	private AreaFormacaoManager areaFormacaoManager;
	private ConhecimentoManager conhecimentoManager;
	private IdiomaManager idiomaManager;
	private EstadoManager estadoManager;
	private CidadeManager cidadeManager;
    private BairroManager bairroManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private FormacaoManager formacaoManager;
	private ExperienciaManager experienciaManager;
	private CandidatoIdiomaManager candidatoIdiomaManager;
	private HistoricoCandidatoManager historicoCandidatoManager;
	private SolicitacaoManager solicitacaoManager;
	private EmpresaManager empresaManager;
	private CandidatoCurriculoManager candidatoCurriculoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private ConfiguracaoImpressaoCurriculoManager configuracaoImpressaoCurriculoManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ConfiguracaoCampoExtraManager configuracaoCampoExtraManager;
	private CamposExtrasManager camposExtrasManager;
		
	private Collection<HistoricoCandidato> historicoCandidatos;
	private Collection<SolicitacaoHistoricoColaborador> historicos;
	private Collection<Candidato> candidatos;
	private Collection<Colaborador> colaboradores;
	private Collection<Solicitacao> solicitacaos;
	private Collection<Idioma> idiomas = new ArrayList<Idioma>();

	private Candidato candidato = new Candidato();
	private Solicitacao solicitacao = new Solicitacao();
	
	private ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo;

	private Map deficiencias;
	private Map<String, String> escolaridades;
	private Map nivels;
	private Map<String, String> sexos;
	private Map ufs;
	private Map cidades;
	private Map session;

	//Variaveis do listCheckBox
	private String[] areasFormacaoCheck;
	private Collection<CheckBox> areasFormacaoCheckList = new ArrayList<CheckBox>();
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Collection<CheckBox> faixasCheckList = new ArrayList<CheckBox>();
	private String[] faixasCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] conhecimentosCheck;
	private Collection<CheckBox> conhecimentosCheckList = new ArrayList<CheckBox>();
	private String[] empresasCheck;
	private Collection<CheckBox> empresasCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] gruposCheck;
	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();
	private Long[] cidadesCheck;
	private Collection<CheckBox> cidadesCheckList = new ArrayList<CheckBox>();
	private String emailAvulso;
	private String anuncioBDS;

	//variavel para busca no candidatoList
	private String nomeBusca = "";
	private String ddd = "";
	private String foneFixo = "";
	private String foneCelular = "";
	private String indicadoPorBusca = "";
	private String cpfBusca = "";
	private char visualizar; //T = todos, D = disponiveis, I = indisponiveis
	private Long etapaSeletivaId;
	private String observacaoRH;

	private String indicadoPor; // para manter filtro de candidatoList

	private Long idioma;
	private Long uf;
	private Long cidade;
	private Long empresaId;
	private char veiculo;
	private String idadeMin;
	private String idadeMax;
	private String escolaridade;
	private String nivel;
	private String sexo;
	private Date dataCadIni;
	private Date dataCadFim;
	private String palavrasChaveCurriculoEscaneado;
	private String palavrasChaveOutrosCampos;
	private String formas;
	private String tempoExperiencia;
	private String deficiencia;
	private Long cargoId;

	private String[] candidatosId;
	private Long[] colaboradoresIds;

	// Uso para exibir mensagen ao deletar candidato
	// N - Não tem mensagem, E = Erro e O = Deu certo
	private char msgDelete = 'N';
	private String ordenar = "dataAtualizacao";
	private String msgAlert = "";
	private boolean BDS;

	private Map<String, Object> parametros = new HashMap<String, Object>();
	private ParametrosDoSistema parametrosDoSistema;
	private Collection<CurriculoCandidatoRelatorio> dataSource;

	private Collection<AvaliacaoCandidatosRelatorio> avaliacaoCandidatos;

	//Utilizado para não recarregar as informações da solicitação no filtro quando não necessárias
	private boolean filtro = false;

    private Collection<Empresa> empresas;

    private String[] bairrosCheck;
    private Collection<CheckBox> bairrosCheckList = new ArrayList<CheckBox>();
    private String[] experienciasCheck;
    private Collection<CheckBox> experienciasCheckList = new ArrayList<CheckBox>();

    private String palavras;
    private String forma;
	private String nomeImg;
	private String origemList; // CA - Candidato; CO - Colaborador  > Lista de origem que invoca a visualizacao do curriculo do candidato

	// Utilizado para escapar nomes de candidatos contendo apóstrofos
	private StringUtil stringUtil = new StringUtil();
	private boolean montaFiltroBySolicitacao = true;
	
	private boolean exibeContratados = false; //exibir contratados na listagem
	private boolean exibeExterno = false; //exibir somente os do módulo externo
	private boolean exibeCompatibilidade; //exibir compatibilidade de competencias na triagem de colaboradores
	
	private boolean somenteCandidatosSemSolicitacao;
	
	private Integer qtdRegistros = 100;
	private Integer percentualMinimo;
	private char statusSolicitacao;
	
	private Map<String, Integer> pesos;

	private boolean mostraOpcaoSolicitacaoPessoal;
	
	private int filtrarPor = 1;

	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private CamposExtras camposExtras;
	
	private boolean modoImpressao;

	private boolean opcaoTodasEmpresas = false;

	private Long[] empresasPermitidas;
	
	public String list() throws Exception
	{
		populaEmpresas(CANDIDATO);
		
		if(empresaId == null)
			empresaId = getEmpresaSistema().getId();
		
		cpfBusca = StringUtil.removeMascara(cpfBusca);
		setTotalSize(candidatoManager.getCount(nomeBusca, cpfBusca, ddd, foneFixo, foneCelular, indicadoPor, visualizar, dataCadIni, dataCadFim, observacaoRH, exibeContratados, exibeExterno, EmpresaUtil.empresasSelecionadas(empresaId, empresas)));
		candidatos = candidatoManager.list(getPage(), getPagingSize(), nomeBusca, cpfBusca, ddd, foneFixo, foneCelular, indicadoPor, visualizar, dataCadIni, dataCadFim, observacaoRH, exibeContratados, exibeExterno, EmpresaUtil.empresasSelecionadas(empresaId, empresas));

		if(candidatos == null || candidatos.isEmpty())
			addActionMessage("Não existem candidatos a serem listados");

		if (!msgAlert.equals(""))
			addActionMessage(msgAlert);

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		candidatoManager.removeCandidato(candidato);
		addActionSuccess("Candidato excluído com sucesso!");

		return Action.SUCCESS;
	}
	
	public String listCbo() throws Exception
	{
		setTotalSize(0);
		return Action.SUCCESS;
	}

	private void prepareBuscaCandidato() throws Exception
	{
		populaEmpresas(CANDIDATO);
		
		if(empresaId == null)		
			empresaId = getEmpresaSistema().getId();
		
		deficiencias = new Deficiencia();
		deficiencias.put(100, "Qualquer Deficiência");
		escolaridades = new Escolaridade();
		nivels = new NivelIdioma();
		sexos = new Sexo();
		ufs = CollectionUtil.convertCollectionToMap(estadoManager.findAll(new String[]{"sigla"}), "getId", "getSigla", Estado.class);

		idiomas = idiomaManager.findAll(new String[]{"nome"});

		Collection<Cargo> cargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, EmpresaUtil.empresasSelecionadas(empresaId, empresas));
		cargosCheckList = CheckListBoxUtil.populaCheckListBox(cargos, "getId", "getNomeMercadoComStatus");
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

		Collection<AreaInteresse> areaInteressesAux = areaInteresseManager.findAllSelect(EmpresaUtil.empresasSelecionadas(empresaId, empresas));
		areasCheckList = CheckListBoxUtil.populaCheckListBox(areaInteressesAux, "getId", "getNome");
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		
		Collection<AreaFormacao> areaFormacaoAux = areaFormacaoManager.findAll();
		areasFormacaoCheckList = CheckListBoxUtil.populaCheckListBox(areaFormacaoAux, "getId", "getNome");
		areasFormacaoCheckList = CheckListBoxUtil.marcaCheckListBox(areasFormacaoCheckList, areasFormacaoCheck);
		
		conhecimentosCheckList = conhecimentoManager.populaCheckOrderNome(EmpresaUtil.empresasSelecionadas(empresaId, empresas));

		experienciasCheckList = CheckListBoxUtil.populaCheckListBox(cargos, "getId", "getNomeMercado");
		experienciasCheckList = CheckListBoxUtil.marcaCheckListBox(experienciasCheckList, experienciasCheck);

		if(!filtro && solicitacao != null && solicitacao.getId() != null)
		{
			solicitacao = solicitacaoManager.findByIdProjection(solicitacao.getId());
			
			if(solicitacao.getCidade() != null && solicitacao.getCidade().getUf() != null)
			{
				Collection<Cidade> cidadesList = cidadeManager.find(new String[]{"uf.id"},new Object[]{solicitacao.getCidade().getUf().getId()}, new String[]{"nome"});
				
				uf = uf==null?solicitacao.getCidade().getUf().getId():uf;
				cidades = CollectionUtil.convertCollectionToMap(cidadeManager.find(new String[]{"uf.id"},new Object[]{uf}, new String[]{"nome"}), "getId", "getNome", Cidade.class);
				cidade = (cidade==null?solicitacao.getCidade().getId():cidade);
				cidadesCheckList = CheckListBoxUtil.populaCheckListBox(cidadesList, "getId", "getNome");
				cidadesCheckList = CheckListBoxUtil.marcaCheckListBox(cidadesCheckList, StringUtil.LongToString(cidadesCheck));
				Collection<Bairro> bairroList = bairroManager.findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"cidade.id"}, new Object[]{solicitacao.getCidade().getId()}, new String[]{"nome"});
            	bairrosCheckList = CheckListBoxUtil.populaCheckListBox(bairroList, "getId", "getNome");
            	Collection<Bairro> marcados = bairroManager.getBairrosBySolicitacao(solicitacao.getId());
				bairrosCheckList = CheckListBoxUtil.marcaCheckListBox(bairrosCheckList, marcados, "getId");
            }

			escolaridade = solicitacao.getEscolaridade();
			sexo = (sexo==null?solicitacao.getSexo():sexo);
			idadeMin = ((idadeMin==null || idadeMin.equals(""))?StringUtil.valueOf(solicitacao.getIdadeMinima()):idadeMin);
			idadeMax = ((idadeMax==null ||idadeMax.equals(""))?StringUtil.valueOf(solicitacao.getIdadeMaxima()):idadeMax);

			for (CheckBox cb : cargosCheckList)
			{
				if (cb.getId().equals(solicitacao.getFaixaSalarial().getCargo().getId()))
				{
					cb.setSelecionado(true);
					break;
				}
			}
			cargoId = solicitacao.getFaixaSalarial().getCargo().getId();

			Collection<AreaInteresse> areasInteresse = areaInteresseManager.findAreasInteresseByAreaOrganizacional(solicitacao.getAreaOrganizacional());

			for (CheckBox cb : areasCheckList)
			{
				AreaInteresse areaAux = new AreaInteresse();
				areaAux.setId(Long.valueOf(cb.getId()));
				areaAux.setNome(cb.getNome());

				if(!areasInteresse.isEmpty() && areasInteresse.contains(areaAux))
					cb.setSelecionado(true);
			}
		}
	}

	private void populaEmpresas(byte compartilharDados) 
	{
		parametrosDoSistema = parametrosDoSistemaManager.findById(1L);
		compartilharCadastros = (compartilharDados == CANDIDATO ? parametrosDoSistema.getCompartilharCandidatos() : parametrosDoSistema.getCompartilharColaboradores());
        empresas = empresaManager.findEmpresasPermitidas(compartilharCadastros, getEmpresaSistema().getId(), SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()));
	}

	public String prepareBusca() throws Exception
	{
		prepareBuscaCandidato();
		populaCheckListCidadeMarcados();
		
		if (!msgAlert.equals(""))
			addActionMessage(msgAlert);
		
		setShowFilter(true);
		
		return Action.SUCCESS;
	}

	public String prepareBuscaSimples() throws Exception
	{
		populaEmpresas(CANDIDATO);
		
		if(empresaId==null)
			empresaId = getEmpresaSistema().getId();
		
		cargosCheckList = cargoManager.populaCheckBox(false, EmpresaUtil.empresasSelecionadas(empresaId, empresas));
		conhecimentosCheckList = conhecimentoManager.populaCheckOrderNome(EmpresaUtil.empresasSelecionadas(empresaId, empresas));

		if(solicitacao != null && solicitacao.getId() != null && montaFiltroBySolicitacao)
		{
			solicitacao = solicitacaoManager.findByIdProjection(solicitacao.getId());
			Collection<Cargo> cargosDaSolicitacao = cargoManager.findBySolicitacao(solicitacao.getId());
			cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosDaSolicitacao, "getId");
		}
		else
		{
			cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);
			conhecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(conhecimentosCheckList, conhecimentosCheck);
		}

		ufs = CollectionUtil.convertCollectionToMap(estadoManager.findAll(new String[]{"sigla"}), "getId", "getSigla", Estado.class);
		populaCheckListCidadeMarcados();
		escolaridades = new Escolaridade();
		
		setShowFilter(true);
		
		return Action.SUCCESS;
	}
	
	public String prepareTriagemAutomatica() throws Exception
	{
		if(solicitacao != null && solicitacao.getId() != null)
			solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
		
		tempoExperiencia = (tempoExperiencia == null) ? "12" : tempoExperiencia;
		qtdRegistros = (qtdRegistros == null) ? 100 : qtdRegistros;
		percentualMinimo = (percentualMinimo == null) ? 50 : percentualMinimo;
		pesos = (pesos == null) ? new PesosTriagemAutomatica() : pesos;
		
		escolaridades = new Escolaridade();
		sexos = new Sexo();
	
		setShowFilter(true);

		return Action.SUCCESS;
	}
	
	public String prepareTriagemColaboradores() throws Exception
	{
		solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
		
		percentualMinimo = (percentualMinimo == null) ? 0 : percentualMinimo;
		escolaridades = new Escolaridade();
		sexos = new Sexo();

		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		
		Collection<FaixaSalarial> faixas = faixaSalarialManager.findAllSelectByCargo(getEmpresaSistema().getId());
		faixasCheckList = CheckListBoxUtil.populaCheckListBox(faixas, "getId", "getDescricaoComStatus");

		populaEmpresas(COLABORADOR);
		empresaId = getEmpresaSistema().getId();
	
		setShowFilter(true);

		return Action.SUCCESS;
	}
	
	public String triagemColaboradores() throws Exception
	{
		percentualMinimo = (percentualMinimo == null) ? 0 : percentualMinimo;
		
		try {
			colaboradores = colaboradorManager.triar(solicitacao.getId(), escolaridade, sexo, idadeMin, idadeMax, faixasCheck, areasCheck, exibeCompatibilidade, percentualMinimo, opcaoTodasEmpresas, EmpresaUtil.empresasSelecionadas(empresaId, empresasPermitidas));

			if(colaboradores == null || colaboradores.size() == 0)
				addActionMessage("Não existem colaboradores a serem listados!");
			else
				setShowFilter(false);
		} catch (Exception e) {
			addActionMessage(e.getMessage());
		}

		prepareTriagemColaboradores();
		
		return Action.SUCCESS;
	}
	
	public String insertColaboradores() throws Exception
	{
		if (colaboradoresIds != null && colaboradoresIds.length > 0)
			colaboradorManager.insertColaboradoresSolicitacao(colaboradoresIds, solicitacao, StatusCandidatoSolicitacao.APROMOVER);
		
		return Action.SUCCESS;
	}
	
	private void populaCheckListCidadeMarcados() throws Exception 
	{
		if (uf == null && solicitacao != null && solicitacao.getCidade() != null && solicitacao.getCidade().getUf() != null)
			uf = solicitacao.getCidade().getUf().getId();
			
		Collection<Cidade> cidadesList = cidadeManager.find(new String[]{"uf.id"},new Object[]{uf}, new String[]{"nome"});
		cidadesCheckList = CheckListBoxUtil.populaCheckListBox(cidadesList, "getId", "getNome");
		
		if(cidadesCheck == null || cidadesCheck.length == 0)
		{
			if(solicitacao != null && solicitacao.getCidade() != null && solicitacao.getCidade().getId() != null)
				cidade = solicitacao.getCidade().getId();
			if (cidade != null)
				cidadesCheck = new Long[]{cidade};
		}
		cidadesCheckList = CheckListBoxUtil.marcaCheckListBox(cidadesCheckList, StringUtil.LongToString(cidadesCheck));
	}
	
	//TODO: SEM TESTE
	public String busca() throws Exception
	{
		cpfBusca = StringUtil.removeMascara(cpfBusca);
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("indicadoPor", indicadoPorBusca);
		parametros.put("nomeBusca", nomeBusca);
		parametros.put("idioma", idioma);
		parametros.put("cpfBusca", cpfBusca);
		parametros.put("dataCadIni",dataCadIni);
		parametros.put("dataCadFim",dataCadFim);
		parametros.put("sexo", sexo);
		parametros.put("idadeMin", idadeMin);
		parametros.put("idadeMax", idadeMax);
		parametros.put("uf", uf);
		parametros.put("escolaridade", escolaridade);
		parametros.put("veiculo", veiculo);
		parametros.put("nivel", nivel);
		parametros.put("palavrasChaveCurriculoEscaneado", palavrasChaveCurriculoEscaneado);
		parametros.put("palavrasChaveOutrosCampos", palavrasChaveOutrosCampos);
		parametros.put("formas", formas);
		parametros.put("tempoExperiencia", tempoExperiencia);
		parametros.put("deficiencia", deficiencia);

		Long[] areasCheckLong = StringUtil.stringToLong(areasCheck);
		if(opcaoTodasEmpresas){
			parametros.put("cargosNomeMercado", cargosCheck);
			parametros.put("conhecimentosNomes", conhecimentosCheck);
		}
		else{
			parametros.put("cargosIds", StringUtil.stringToLong(cargosCheck));
			parametros.put("conhecimentosIds", StringUtil.stringToLong(conhecimentosCheck));
		}
		
		Long[] bairrosCheckLong = StringUtil.stringToLong(bairrosCheck);
		Long[] experienciasCheckLong = StringUtil.stringToLong(experienciasCheck);
		Long[] areasFormacaoCheckLong = StringUtil.stringToLong(areasFormacaoCheck);
		parametros.put("areasIds", areasCheckLong);
		parametros.put("bairrosIds", bairrosCheckLong);
		parametros.put("cidadesIds", cidadesCheck);
		parametros.put("areasFormacaoIds", areasFormacaoCheckLong);
		
		if (experienciasCheckLong.length > 0)
			parametros.put("experiencias", experienciasCheckLong);

		if (BDS)
			empresaId = getEmpresaSistema().getId();
		
		candidatos = candidatoManager.busca(parametros, solicitacao.getId(), somenteCandidatosSemSolicitacao, qtdRegistros, ordenar, EmpresaUtil.empresasSelecionadas(empresaId, empresasPermitidas));

		prepareBusca();

		if(candidatos == null || candidatos.size() == 0)
			addActionMessage("Não existem candidatos a serem listados");
		else
			setShowFilter(false);

		cidades = CollectionUtil.convertCollectionToMap(cidadeManager.find(new String[]{"uf.id"},new Object[]{uf}, new String[]{"nome"}), "getId", "getNome", Cidade.class);

		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		bairrosCheckList = CheckListBoxUtil.marcaCheckListBox(bairrosCheckList, bairrosCheck);

		CollectionUtil<CheckBox> cu = new CollectionUtil<CheckBox>();
		areasCheckList = cu.sortCollectionStringIgnoreCase(areasCheckList, "nome");

		Collection<Conhecimento> conhecimentos;

		if (areasCheck == null || areasCheck.length == 0)
			conhecimentos =  conhecimentoManager.findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"nome"});
		else
			conhecimentos =  conhecimentoManager.findByAreaInteresse(StringUtil.stringToLong(areasCheck),getEmpresaSistema().getId());

		conhecimentosCheckList = CheckListBoxUtil.populaCheckListBox(conhecimentos, "getId", "getNome");
		conhecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(conhecimentosCheckList, conhecimentosCheck);

		return Action.SUCCESS;
	}

	public String buscaSimples() throws Exception
	{
		cpfBusca = StringUtil.removeMascara(cpfBusca);
		montaFiltroBySolicitacao = false;
		prepareBuscaSimples();

		candidatos = candidatoManager.buscaSimplesDaSolicitacao(indicadoPorBusca, nomeBusca, cpfBusca, escolaridade, uf, cidadesCheck, cargosCheck, conhecimentosCheck, solicitacao.getId(), somenteCandidatosSemSolicitacao, qtdRegistros, ordenar, opcaoTodasEmpresas, EmpresaUtil.empresasSelecionadas(empresaId, empresas));

		if(candidatos == null || candidatos.size() == 0)
			addActionMessage("Não existem candidatos a serem listados");
		else {
			setShowFilter(false);
		}

		return Action.SUCCESS;
	}
	
	public String triagemAutomatica() throws Exception
	{
		candidatos = candidatoManager.triagemAutomatica(solicitacao, StringUtil.isBlank(tempoExperiencia)?0:Integer.parseInt(tempoExperiencia), pesos, percentualMinimo);

		if(candidatos == null || candidatos.size() == 0)
			addActionMessage("Não existem candidatos a serem listados");
		else
			setShowFilter(false);

		prepareTriagemAutomatica();
		
		return Action.SUCCESS;
	}

	public String insertCandidatos() throws Exception
	{
		if(candidatosId != null && candidatosId.length > 0)
			candidatoSolicitacaoManager.insertCandidatos(candidatosId, solicitacao,StatusCandidatoSolicitacao.INDIFERENTE);

		return Action.SUCCESS;
	}

	public String selecionaDestinatariosBDS() throws Exception
	{
		prepareBuscaCandidato();
		empresasCheckList = anuncioManager.getEmpresasCheck(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String exportaBDS() throws Exception
	{
		BDS = true;
		if(candidatosId != null && candidatosId.length > 0)
		{
			Collection<Candidato> candidatos = candidatoManager.findCandidatosById(LongUtil.arrayStringToArrayLong(candidatosId[0].split(",")));
			candidatos = candidatoManager.populaCandidatos(candidatos);

			try
			{
				if(candidatoManager.exportaCandidatosBDS(getEmpresaSistema(), candidatos, empresasCheck, emailAvulso, anuncioBDS))
					addActionSuccess("Candidatos exportados com sucesso.");

			}
			catch (Throwable e)
			{
				addActionError("Não foi possível exportar Candidatos.");
				e.printStackTrace();
			}
		}

		prepareBusca();

		return Action.SUCCESS;
	}

	public String verCurriculoEscaneado() throws Exception
	{
		candidato.setCandidatoCurriculos(candidatoCurriculoManager.findToList(new String[]{"curriculo"}, new String[]{"curriculo"}, new String[]{"candidato.id"}, new Object[]{candidato.getId()}));

		return Action.SUCCESS;
	}

	//TODO: SEM TESTE
	public String showImagensCurriculo() throws Exception
	{
		java.io.File file = ArquivoUtil.getArquivo(nomeImg,"curriculos");
		com.fortes.model.type.File arquivo = new com.fortes.model.type.File();
		arquivo.setBytes(FileUtil.getFileBytes(file));
		arquivo.setName(file.getName());
		arquivo.setSize(file.length());
		int pos = arquivo.getName().indexOf(".");

		if(pos > 0)
			arquivo.setContentType(arquivo.getName().substring(pos));

		if (arquivo != null && arquivo.getBytes() != null)
		{
			HttpServletResponse response = ServletActionContext.getResponse();

			response.addHeader("Expires", "0");
			response.addHeader("Pragma", "no-cache");
			response.addHeader("Content-type", arquivo.getContentType());
			response.addHeader("Content-Transfer-Encoding", "binary");

			response.getOutputStream().write(arquivo.getBytes());
		}

		return Action.SUCCESS;
	}

	public String verCurriculoTextoOcr() throws Exception
	{
		candidato.setOcrTexto(candidatoManager.getOcrTextoById(candidato.getId()));

		if(StringUtils.isNotBlank(palavras))
		{
			String[] palavrasArray = palavras.split(" ");
			if(forma.equals("3"))
				palavrasArray = new String[]{palavras};

			candidato.setOcrTexto( StringUtil.destacarExpressoesApresentacao(candidato.getOcrTexto(), palavrasArray) );
		}

		return Action.SUCCESS;
	}
	
	public String verExamePalografico()
	{
		candidato = candidatoManager.findByIdProjection(candidato.getId());
		
		return Action.SUCCESS;
	}

	public String verCurriculo() throws Exception
	{
		findAndSetCandidato();
		
		setCamposExtras();

		return Action.SUCCESS;
	}

	private void setCamposExtras()
	{
		if(getEmpresaSistema().isCampoExtraCandidato()){
			if(candidato.getCamposExtras() != null && candidato.getCamposExtras().getId() != null)
				camposExtras = camposExtrasManager.findById(candidato.getCamposExtras().getId());
			
			configuracaoCampoExtras = configuracaoCampoExtraManager.find(new String[]{"ativoCandidato", "empresa.id"}, new Object[]{true, getEmpresaSistema().getId()}, new String[]{"ordem"});
		}
	}

	public String imprimirCurriculo() throws Exception
	{
		if(configuracaoImpressaoCurriculo == null)
			configuracaoImpressaoCurriculo = new ConfiguracaoImpressaoCurriculo();
		
		configuracaoImpressaoCurriculo.setUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()));
		configuracaoImpressaoCurriculo.setEmpresa(getEmpresaSistema());
		configuracaoImpressaoCurriculoManager.saveOrUpdate(configuracaoImpressaoCurriculo);
		
		findAndSetCandidato();

		setCamposExtras();
		
		//ver historico do candidato
		historicoCandidatos = historicoCandidatoManager.findByCandidato(candidato);
		historicos = historicoCandidatoManager.montaMapaHistorico(historicoCandidatos);

		solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
		
		CurriculoCandidatoRelatorio curriculo = new CurriculoCandidatoRelatorio();
		curriculo.setCandidatos(candidato);
		curriculo.setSolicitacao(solicitacao);
		curriculo.setHistoricos(historicos);
		curriculo.setConfiguracaoCamposExtras(CampoExtraUtil.preencheConteudoCampoExtra(camposExtras,  configuracaoCampoExtras));

		dataSource = new ArrayList<CurriculoCandidatoRelatorio>();
		dataSource.add(curriculo);
		
		parametros = RelatorioUtil.getParametrosRelatorio("Currículo", getEmpresaSistema(), "");
		configuracaoImpressaoCurriculo.populaParametros(parametros);

		mostraOpcaoSolicitacaoPessoal = (solicitacao != null && solicitacao.getId() != null);
		parametros.put("mostraOpcaoSolicitacaoPessoal", mostraOpcaoSolicitacaoPessoal);
		parametros.put("mostraColaboradorSubstituido", getEmpresaSistema().isSolPessoalExibirColabSubstituido());
		
		return Action.SUCCESS;
	}

	private void findAndSetCandidato()
	{
		candidato = candidatoManager.findByIdProjection(candidato.getId());

		try
		{
			candidato.setFoto(candidatoManager.getFoto(candidato.getId()));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		candidato.setCargos(candidatoManager.findCargosByCandidatoId(candidato.getId()));
		candidato.setFormacao(formacaoManager.findByCandidato(candidato.getId()));
		candidato.setCandidatoIdiomas(candidatoIdiomaManager.findByCandidato(candidato.getId()));
		candidato.setExperiencias(experienciaManager.findByCandidato(candidato.getId()));
		candidato.setConhecimentos(conhecimentoManager.findByCandidato(candidato.getId()));
	}

	public String prepareRelatorioAvaliacaoCandidatos() throws Exception
	{
		setVideoAjuda(784L);
		
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

		gruposCheckList = grupoOcupacionalManager.populaCheckOrderNome(getEmpresaSistema().getId());
		gruposCheckList = CheckListBoxUtil.marcaCheckListBox(gruposCheckList, gruposCheck);

		cargosCheckList = cargoManager.populaCheckBox(gruposCheck, cargosCheck, getEmpresaSistema().getId());
		cargosCheckList = CheckListBoxUtil.marcaCheckListBox(cargosCheckList, cargosCheck);

		return Action.SUCCESS;
	}

	public String relatorioAvaliacaoCandidatos() throws Exception
	{
		avaliacaoCandidatos = new ArrayList<AvaliacaoCandidatosRelatorio>();
		try
		{
			Long[] areasIds = null;
			Long[] cargosIds = null;
			
			if (filtrarPor == 1)
				areasIds = LongUtil.arrayStringToArrayLong(areasCheck);
			
			if (filtrarPor == 2)
				cargosIds = LongUtil.arrayStringToArrayLong(cargosCheck);
			
			avaliacaoCandidatos = candidatoManager.findRelatorioAvaliacaoCandidatos(dataCadIni, dataCadFim, getEmpresaSistema().getId(), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), areasIds, cargosIds, statusSolicitacao);
			parametros = RelatorioUtil.getParametrosRelatorio("Avaliações dos Candidatos", getEmpresaSistema(), getPeriodoFormatado());
			return SUCCESS;

		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioAvaliacaoCandidatos();
			return INPUT;
		}
	}

	private String getPeriodoFormatado()
	{
		String periodoFormatado = "-";
		if (dataCadIni != null && dataCadFim != null)
		{
			StatusSolicitacao status = new StatusSolicitacao();
			periodoFormatado = status.get(statusSolicitacao) + " - " +  "Período: " + DateUtil.formataDiaMesAno(dataCadIni) + " - " + DateUtil.formataDiaMesAno(dataCadFim);
		}

		return periodoFormatado;
	}

	public String sucessoInclusao() throws Exception
	{
		addActionSuccess("Operação efetuada com sucesso");
		return Action.SUCCESS;
	}

	public String infoCandidato() throws Exception
	{
		configuracaoImpressaoCurriculo = configuracaoImpressaoCurriculoManager.findByUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()).getId(), getEmpresaSistema().getId());
		mostraOpcaoSolicitacaoPessoal = (solicitacao != null && solicitacao.getId() != null);
		return Action.SUCCESS;
	}
	
	public String prepareExamePalografico() throws Exception 
	{
		return list();
	}

	public Collection<Candidato> getCandidatos()
	{
		return candidatos;
	}
	public Candidato getCandidato()
	{
		return candidato;
	}
	public void setCandidato(Candidato candidato)
	{
		this.candidato = candidato;
	}
	public void setCandidatoManager(CandidatoManager candidatoManager)
	{
		this.candidatoManager = candidatoManager;
	}

	public String[] getCargosCheck()
	{
		return cargosCheck;
	}

	public void setCargosCheck(String[] cargosCheck)
	{
		this.cargosCheck = cargosCheck;
	}

	public Collection<CheckBox> getCargosCheckList()
	{
		return cargosCheckList;
	}

	public void setCargosCheckList(Collection<CheckBox> cargosCheckList)
	{
		this.cargosCheckList = cargosCheckList;
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

	public void setAreaInteresseManager(AreaInteresseManager areaInteresseManager)
	{
		this.areaInteresseManager = areaInteresseManager;
	}

	public String[] getConhecimentosCheck()
	{
		return conhecimentosCheck;
	}

	public void setConhecimentosCheck(String[] conhecimentosCheck)
	{
		this.conhecimentosCheck = conhecimentosCheck;
	}

	public Collection<CheckBox> getConhecimentosCheckList()
	{
		return conhecimentosCheckList;
	}

	public void setConhecimentosCheckList(Collection<CheckBox> conhecimentosCheckList)
	{
		this.conhecimentosCheckList = conhecimentosCheckList;
	}

	public void setConhecimentoManager(ConhecimentoManager conhecimentoManager)
	{
		this.conhecimentoManager = conhecimentoManager;
	}

	public Map<String, String> getEscolaridades()
	{
		return escolaridades;
	}

	public void setEscolaridades(Map<String, String> escolaridades)
	{
		this.escolaridades = escolaridades;
	}

	public Collection<Idioma> getIdiomas()
	{
		return idiomas;
	}

	public void setIdiomas(Collection<Idioma> idiomas)
	{
		this.idiomas = idiomas;
	}

	public void setIdiomaManager(IdiomaManager idiomaManager)
	{
		this.idiomaManager = idiomaManager;
	}

	public Map getNivels()
	{
		return nivels;
	}

	public void setNivels(Map nivels)
	{
		this.nivels = nivels;
	}

	public Map<String, String> getSexos()
	{
		return sexos;
	}

	public void setSexos(Map<String, String> sexos)
	{
		this.sexos = sexos;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Map getUfs()
	{
		return ufs;
	}

	public void setUfs(Map ufs)
	{
		this.ufs = ufs;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public Long getCidade()
	{
		return cidade;
	}

	public void setCidade(Long cidade)
	{
		this.cidade = cidade;
	}

	public String getEscolaridade()
	{
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade)
	{
		this.escolaridade = escolaridade;
	}

	public String getIdadeMax()
	{
		return idadeMax;
	}

	public void setIdadeMax(String idadeMax)
	{
		this.idadeMax = idadeMax;
	}

	public String getIdadeMin()
	{
		return idadeMin;
	}

	public void setIdadeMin(String idadeMin)
	{
		this.idadeMin = idadeMin;
	}

	public Long getIdioma()
	{
		return idioma;
	}

	public void setIdioma(Long idioma)
	{
		this.idioma = idioma;
	}

	public String getNivel()
	{
		return nivel;
	}

	public void setNivel(String nivel)
	{
		this.nivel = nivel;
	}

	public char getVeiculo()
	{
		return veiculo;
	}

	public void setVeiculo(char veiculo)
	{
		this.veiculo = veiculo;
	}

	public String getSexo()
	{
		return sexo;
	}

	public void setSexo(String sexo)
	{
		this.sexo = sexo;
	}

	public Long getUf()
	{
		return uf;
	}

	public void setUf(Long uf)
	{
		this.uf = uf;
	}

	public Map getCidades()
	{
		return cidades;
	}

	public void setCidades(Map cidades)
	{
		this.cidades = cidades;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
	}

	public String[] getCandidatosId()
	{
		return candidatosId;
	}

	public void setCandidatosId(String[] candidatosId)
	{
		this.candidatosId = candidatosId;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public void setCandidatoIdiomaManager(CandidatoIdiomaManager candidatoIdiomaManager)
	{
		this.candidatoIdiomaManager = candidatoIdiomaManager;
	}

	public void setExperienciaManager(ExperienciaManager experienciaManager)
	{
		this.experienciaManager = experienciaManager;
	}

	public void setFormacaoManager(FormacaoManager formacaoManager)
	{
		this.formacaoManager = formacaoManager;
	}

	public String getCpfBusca()
	{
		return cpfBusca;
	}

	public void setCpfBusca(String cpfBusca)
	{
		this.cpfBusca = cpfBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = StringUtil.retiraAcento(nomeBusca);
	}

	public Date getDataCadFim() {
		return dataCadFim;
	}

	public void setDataCadFim(Date dataCadFim) {
		this.dataCadFim = dataCadFim;
	}

	public Date getDataCadIni() {
		return dataCadIni;
	}

	public void setDataCadIni(Date dataCadIni) {
		this.dataCadIni = dataCadIni;
	}

	public char getMsgDelete() {
		return msgDelete;
	}

	public void setMsgDelete(char msgDelete) {
		this.msgDelete = msgDelete;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros)
	{
		this.parametros = parametros;
	}

	public ParametrosDoSistema getParametrosDoSistema()
	{
		return parametrosDoSistema;
	}

	public void setParametrosDoSistema(ParametrosDoSistema parametrosDoSistema)
	{
		this.parametrosDoSistema = parametrosDoSistema;
	}

	public Collection<HistoricoCandidato> getHistoricoCandidatos()
	{
		return historicoCandidatos;
	}

	public void setHistoricoCandidatos(Collection<HistoricoCandidato> historicoCandidatos)
	{
		this.historicoCandidatos = historicoCandidatos;
	}

	public Collection<SolicitacaoHistoricoColaborador> getHistoricos()
	{
		return historicos;
	}

	public void setHistoricos(Collection<SolicitacaoHistoricoColaborador> historicos)
	{
		this.historicos = historicos;
	}

	public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
	{
		this.historicoCandidatoManager = historicoCandidatoManager;
	}

	public Collection<CurriculoCandidatoRelatorio> getDataSource()
	{
		return dataSource;
	}

	public void setDataSource(Collection<CurriculoCandidatoRelatorio> dataSource)
	{
		this.dataSource = dataSource;
	}

	public String getMsgAlert()
	{
		return msgAlert;
	}

	public void setMsgAlert(String msgAlert)
	{
		this.msgAlert = msgAlert;
	}

	public boolean isBDS()
	{
		return BDS;
	}

	public void setBDS(boolean BDS)
	{
		this.BDS = BDS;
	}

	public Collection<CheckBox> getEmpresasCheckList()
	{
		return empresasCheckList;
	}

	public void setEmpresasCheckList(Collection<CheckBox> empresasCheckList)
	{
		this.empresasCheckList = empresasCheckList;
	}

	public void setAnuncioManager(AnuncioManager anuncioManager)
	{
		this.anuncioManager = anuncioManager;
	}

	public String getEmailAvulso()
	{
		return emailAvulso;
	}

	public void setEmailAvulso(String emailAvulso)
	{
		this.emailAvulso = emailAvulso;
	}

	public String[] getEmpresasCheck()
	{
		return empresasCheck;
	}

	public void setEmpresasCheck(String[] empresasCheck)
	{
		this.empresasCheck = empresasCheck;
	}

	public String getAnuncioBDS()
	{
		return anuncioBDS;
	}

	public void setAnuncioBDS(String anuncioBDS)
	{
		this.anuncioBDS = anuncioBDS;
	}

	public Map getSession()
	{
		return session;
	}

	public void setSession(Map session)
	{
		this.session = session;
	}

	public Collection<Solicitacao> getSolicitacaos()
	{
		return solicitacaos;
	}

	public void setSolicitacaos(Collection<Solicitacao> solicitacaos)
	{
		this.solicitacaos = solicitacaos;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}
	public boolean isFiltro()
	{
		return filtro;
	}

	public void setFiltro(boolean filtro)
	{
		this.filtro = filtro;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public String[] getBairrosCheck()
	{
		return bairrosCheck;
	}

	public void setBairrosCheck(String[] bairrosCheck)
	{
		this.bairrosCheck = bairrosCheck;
	}

	public Collection<CheckBox> getBairrosCheckList()
	{
		return bairrosCheckList;
	}

	public void setBairrosCheckList(Collection<CheckBox> bairrosCheckList)
	{
		this.bairrosCheckList = bairrosCheckList;
	}

	public void setBairroManager(BairroManager bairroManager)
	{
		this.bairroManager = bairroManager;
	}

	public void setCandidatoCurriculoManager(CandidatoCurriculoManager candidatoCurriculoManager)
	{
		this.candidatoCurriculoManager = candidatoCurriculoManager;
	}

	public String getPalavrasChaveCurriculoEscaneado()
	{
		return palavrasChaveCurriculoEscaneado;
	}

	public void setPalavrasChaveCurriculoEscaneado(String palavrasChaveCurriculoEscaneado)
	{
		this.palavrasChaveCurriculoEscaneado = palavrasChaveCurriculoEscaneado;
	}

	public String getFormas()
	{
		return formas;
	}

	public void setFormas(String formas)
	{
		this.formas = formas;
	}

	public String getForma()
	{
		return forma;
	}

	public void setForma(String forma)
	{
		this.forma = forma;
	}

	public String getPalavras()
	{
		return palavras;
	}

	public void setPalavras(String palavras)
	{
		this.palavras = palavras;
	}

	public String[] getExperienciasCheck()
	{
		return experienciasCheck;
	}

	public void setExperienciasCheck(String[] experienciasCheck)
	{
		this.experienciasCheck = experienciasCheck;
	}

	public Collection<CheckBox> getExperienciasCheckList()
	{
		return experienciasCheckList;
	}

	public void setExperienciasCheckList(Collection<CheckBox> experienciasCheckList)
	{
		this.experienciasCheckList = experienciasCheckList;
	}

	public String getTempoExperiencia()
	{
		return tempoExperiencia;
	}

	public void setTempoExperiencia(String tempoExperiencia)
	{
		this.tempoExperiencia = tempoExperiencia;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public String getIndicadoPorBusca()
	{
		return indicadoPorBusca;
	}

	public void setIndicadoPorBusca(String indicadoPorBusca)
	{
		this.indicadoPorBusca = StringUtil.retiraAcento(indicadoPorBusca);
	}

	public Long getEtapaSeletivaId()
	{
		return etapaSeletivaId;
	}

	public void setEtapaSeletivaId(Long etapaSeletivaId)
	{
		this.etapaSeletivaId = etapaSeletivaId;
	}

	public void setNomeImg(String nomeImg)
	{
		this.nomeImg = nomeImg;
	}

	public StringUtil getStringUtil()
	{
		return stringUtil;
	}

	public String getIndicadoPor()
	{
		return indicadoPor;
	}

	public void setIndicadoPor(String indicadoPor)
	{
		this.indicadoPor = indicadoPor;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public Collection<CheckBox> getGruposCheckList()
	{
		return gruposCheckList;
	}

	public void setGruposCheck(String[] gruposCheck)
	{
		this.gruposCheck = gruposCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<AvaliacaoCandidatosRelatorio> getAvaliacaoCandidatos()
	{
		return avaliacaoCandidatos;
	}

	public Long getEmpresaId()
	{
		return empresaId;
	}

	public void setEmpresaId(Long empresaId)
	{
		this.empresaId = empresaId;
	}

	public Map getDeficiencias()
	{
		return deficiencias;
	}

	public void setDeficiencias(Map deficiencias)
	{
		this.deficiencias = deficiencias;
	}

	public String getDeficiencia()
	{
		return deficiencia;
	}

	public void setDeficiencia(String deficiencia)
	{
		this.deficiencia = deficiencia;
	}

	public String getObservacaoRH()
	{
		return observacaoRH;
	}

	public void setObservacaoRH(String observacaoRH)
	{
		this.observacaoRH = observacaoRH;
	}

	public ConfiguracaoImpressaoCurriculo getConfiguracaoImpressaoCurriculo()
	{
		return configuracaoImpressaoCurriculo;
	}

	public void setConfiguracaoImpressaoCurriculo(ConfiguracaoImpressaoCurriculo configuracaoImpressaoCurriculo)
	{
		this.configuracaoImpressaoCurriculo = configuracaoImpressaoCurriculo;
	}

	public void setConfiguracaoImpressaoCurriculoManager(ConfiguracaoImpressaoCurriculoManager configuracaoImpressaoCurriculoManager)
	{
		this.configuracaoImpressaoCurriculoManager = configuracaoImpressaoCurriculoManager;
	}

	public boolean isExibeContratados() {
		return exibeContratados;
	}

	public void setExibeContratados(boolean exibeContratados) {
		this.exibeContratados = exibeContratados;
	}

	public boolean isSomenteCandidatosSemSolicitacao() {
		return somenteCandidatosSemSolicitacao;
	}

	public void setSomenteCandidatosSemSolicitacao(boolean somenteCandidatosSemSolicitacao) {
		this.somenteCandidatosSemSolicitacao = somenteCandidatosSemSolicitacao;
	}

	public boolean isExibeExterno() {
		return exibeExterno;
	}

	public void setExibeExterno(boolean exibeExterno) {
		this.exibeExterno = exibeExterno;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) {
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}

	public Boolean getCompartilharCandidatos() {
		return compartilharCadastros;
	}

	public Integer getQtdRegistros() {
		return qtdRegistros;
	}

	public void setQtdRegistros(Integer qtdRegistros) {
		this.qtdRegistros = qtdRegistros;
	}

	public String getOrdenar() {
		return ordenar;
	}

	public void setOrdenar(String ordenar) {
		this.ordenar = ordenar;
	}

	public char getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(char statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public Integer getPercentualMinimo() {
		return percentualMinimo;
	}

	public void setPercentualMinimo(Integer percentualMinimo) {
		this.percentualMinimo = percentualMinimo;
	}

	public Map<String, Integer> getPesos() {
		return pesos;
	}

	public void setPesos(Map<String, Integer> pesos) {
		this.pesos = pesos;
	}

	public boolean isExibeCompatibilidade() {
		return exibeCompatibilidade;
	}

	public void setExibeCompatibilidade(boolean exibeCompatibilidade) {
		this.exibeCompatibilidade = exibeCompatibilidade;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Long[] getColaboradoresIds() {
		return colaboradoresIds;
	}

	public void setColaboradoresIds(Long[] colaboradoresIds) {
		this.colaboradoresIds = colaboradoresIds;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager) {
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public Collection<CheckBox> getFaixasCheckList() {
		return faixasCheckList;
	}

	public void setFaixasCheckList(Collection<CheckBox> faixasCheckList) {
		this.faixasCheckList = faixasCheckList;
	}

	public String[] getFaixasCheck() {
		return faixasCheck;
	}

	public void setFaixasCheck(String[] faixasCheck) {
		this.faixasCheck = faixasCheck;
	}
	
	public boolean isMostraOpcaoSolicitacaoPessoal()
	{
		return mostraOpcaoSolicitacaoPessoal;
	}
	
	public String getOrigemList()
	{
		return origemList;
	}
	
	public void setOrigemList(String origemList)
	{
		this.origemList = origemList;
	}

	public int getFiltrarPor()
	{
		return filtrarPor;
	}

	public void setFiltrarPor(int filtrarPor)
	{
		this.filtrarPor = filtrarPor;
	}

	public Collection<CheckBox> getCidadesCheckList() {
		return cidadesCheckList;
	}

	public void setCidadesCheckList(Collection<CheckBox> cidadesCheckList) {
		this.cidadesCheckList = cidadesCheckList;
	}

	public Long[] getCidadesCheck() {
		return cidadesCheck;
	}

	public void setCidadesCheck(Long[] cidadesCheck) {
		this.cidadesCheck = cidadesCheck;
	}
	
	public String getPalavrasChaveOutrosCampos()
	{
		return palavrasChaveOutrosCampos;
	}
	
	public void setPalavrasChaveOutrosCampos(String palavrasChaveOutrosCampos)
	{
		this.palavrasChaveOutrosCampos = palavrasChaveOutrosCampos;
	}
	
	public void setConfiguracaoCampoExtraManager(ConfiguracaoCampoExtraManager configuracaoCampoExtraManager)
	{
		this.configuracaoCampoExtraManager = configuracaoCampoExtraManager;
	}
	
	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras()
	{
		return configuracaoCampoExtras;
	}

	public CamposExtras getCamposExtras()
	{
		return camposExtras;
	}
	
	public void setCamposExtrasManager(CamposExtrasManager camposExtrasManager)
	{
		this.camposExtrasManager = camposExtrasManager;
	}

	public boolean isModoImpressao() {
		return modoImpressao;
	}

	public void setModoImpressao(boolean modoImpressao) {
		this.modoImpressao = modoImpressao;
	}
	
	public void setopcaoTodasEmpresas(boolean opcaoTodasEmpresas) {
		this.opcaoTodasEmpresas = opcaoTodasEmpresas;
	}
	
	public void setEmpresasPermitidas(Long[] empresasPermitidas) {
		this.empresasPermitidas = empresasPermitidas;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getFoneFixo() {
		return foneFixo;
	}

	public void setFoneFixo(String foneFixo) {
		this.foneFixo = foneFixo;
	}

	public String getFoneCelular() {
		return foneCelular;
	}

	public void setFoneCelular(String foneCelular) {
		this.foneCelular = foneCelular;
	}

	public void setAreaFormacaoManager(AreaFormacaoManager areaFormacaoManager) {
		this.areaFormacaoManager = areaFormacaoManager;
	}

	public Collection<CheckBox> getAreasFormacaoCheckList() {
		return areasFormacaoCheckList;
	}

	public void setAreasFormacaoCheck(String[] areasFormacaoCheck) {
		this.areasFormacaoCheck = areasFormacaoCheck;
	}
}