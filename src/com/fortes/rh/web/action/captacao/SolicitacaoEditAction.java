/* Autor: Robertson Freitas
 * Data: 25/06/2006
 * Requisito: RFA015 */
package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.captacao.relatorio.SolicitacaoPessoalRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusAprovacaoSolicitacao;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.thread.EnviaEmailSolicitanteThread;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings("unchecked")
public class SolicitacaoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	private SolicitacaoManager solicitacaoManager;
    private AreaOrganizacionalManager areaOrganizacionalManager;
    private EtapaSeletivaManager etapaSeletivaManager;
    private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
    private CargoManager cargoManager;
    private HistoricoCandidatoManager historicoCandidatoManager;
    private FaixaSalarialManager faixaSalarialManager;
    private MotivoSolicitacaoManager motivoSolicitacaoManager;
    private FuncaoManager funcaoManager;
    private AmbienteManager ambienteManager;
    private EstabelecimentoManager estabelecimentoManager;
    private EstadoManager estadoManager;
    private CidadeManager cidadeManager;
    private BairroManager bairroManager;
    private EmpresaManager empresaManager;
    private AvaliacaoManager avaliacaoManager;
    private ColaboradorManager colaboradorManager;
    private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;
    private ColaboradorQuestionarioManager colaboradorQuestionarioManager;
    private ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager;

    private Solicitacao solicitacao = new Solicitacao();
    private MotivoSolicitacao motivoSolicitacao;

    private Collection<AreaOrganizacional> areas;
    private Collection<FaixaSalarial> faixaSalarials;
    private Collection<MotivoSolicitacao> motivoSolicitacaos;
    private Collection<Estabelecimento> estabelecimentos;
    Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos;

    private Collection<Funcao> funcoes;
    private Collection<Ambiente> ambientes;

    private String[] colaboradoresSusbstituidos = new String[]{};
    private String[] bairrosCheck;
    private Collection<CheckBox> bairrosCheckList = new ArrayList<CheckBox>();
    private String[] emailsCheck;
    private Collection<CheckBox> emailsCheckList = new ArrayList<CheckBox>();
    private String[] etapaCheck;
    private Collection<CheckBox> etapaSeletivaCheckList = new ArrayList<CheckBox>();
    private String[] avaliacoesCheck;
    private Collection<CheckBox> avaliacoesCheckList = new ArrayList<CheckBox>();
    
    private Collection<Cargo> cargos = new ArrayList<Cargo>();
    private Long cargoId;
    private Cargo cargo;

	private HashMap escolaridades;
    private HashMap sexos;
    private HashMap vinculos;
    private HashMap situacoes;
    private HashMap status;

    private String ano;
    private String nomeLiberador;

    private List<SolicitacaoPessoalRelatorio> dataSource = new ArrayList<SolicitacaoPessoalRelatorio>();
    private Map<String,Object> parametros = new HashMap<String, Object>();
    private ParametrosDoSistema parametrosDoSistema;

    private Collection<CandidatoSolicitacao> candidatoSolicitacaos;
    private Collection<ProcessoSeletivoRelatorio> processoSeletivoRelatorios;
	private Collection<Estado> estados = new ArrayList<Estado>();
	private Collection<Cidade> cidades = new ArrayList<Cidade>();
	private Collection<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

	private Estado estado;
	private Cidade cidade;

	private boolean exibeColaboradorSubstituido;
	private boolean exibeSalario;
	private boolean obrigaDadosComplementares;
	private boolean clone;
	private boolean somenteLeitura;
	private boolean dataStatusSomenteLeitura;
	private boolean possuiCandidatoSolicitacao;
	private boolean imprimirObservacao;

	private char statusSolicitacao;
	private char situacaoCandidato;
	private char visualizar;

	private int qtdAvaliacoesRespondidas;
	private boolean obrigarAmbienteFuncao;
	private Date dataIni;
	private Date dataFim;
	private boolean existeCompetenciaRespondida;
	
	private char statusSolicitacaoAnterior;
    private Date dataStatusSolicitacaoAnterior;
    private String obsStatusSolicitacaoAnterior;

    private void prepare() throws Exception
    {
    	Empresa empresa = empresaManager.findByIdProjection(getEmpresaSistema().getId());
    	obrigarAmbienteFuncao = getEmpresaSistema().isObrigarAmbienteFuncao();

    	exibeSalario = empresa.isSolPessoalExibirSalario();
    	exibeColaboradorSubstituido = empresa.isSolPessoalExibirColabSubstituido();
    	obrigaDadosComplementares = empresa.isSolPessoalObrigarDadosComplementares();
    	
    	this.estados = estadoManager.findAll(new String[]{"sigla"});
		Long faixaInativaId = null;
		Long areaInativaId = null;
		
		avaliacoes = avaliacaoManager.findAllSelect(null, null, getEmpresaSistema().getId(), true, TipoModeloAvaliacao.SOLICITACAO, null);
        avaliacoesCheckList = CheckListBoxUtil.populaCheckListBox(avaliacoes, "getId", "getTitulo");
		
    	if (solicitacao != null && solicitacao.getId() != null)
        {
            solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
            
            if(solicitacao.getColaboradorSubstituido() != null)
            	colaboradoresSusbstituidos = solicitacao.getColaboradorSubstituido().split(", "); 
            
            estado = solicitacao.getCidade().getUf();
            faixaInativaId = solicitacao.getFaixaSalarial().getId();
            areaInativaId = solicitacao.getAreaOrganizacional().getId();

            if(estado != null && estado.getId() != null)
    			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{Long.valueOf(estado.getId())}, new String[]{"nome"});

            if(solicitacao.getCidade() != null && solicitacao.getCidade().getId() != null)
            {
            	Collection<Bairro> bairroList = bairroManager.findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"cidade.id"}, new Object[]{solicitacao.getCidade().getId()}, new String[]{"nome"});
            	bairrosCheckList = CheckListBoxUtil.populaCheckListBox(bairroList, "getId", "getNome");
            }
            
            // Campos somente leitura se já houver candidatos na solicitação
            if (candidatoSolicitacaoManager.getCount(new String[] {"solicitacao.id"}, new Object[]{solicitacao.getId()}) > 0)
            	possuiCandidatoSolicitacao = true;
            
			if(solicitacao.getFaixaSalarial() != null && solicitacao.getFaixaSalarial().getCargo() != null && solicitacao.getFaixaSalarial().getCargo().getId() != null)
				funcoes = funcaoManager.findByCargo(solicitacao.getFaixaSalarial().getCargo().getId());

			if(solicitacao.getEstabelecimento() != null && solicitacao.getEstabelecimento().getId() != null)
				ambientes = ambienteManager.findByEstabelecimento(solicitacao.getEstabelecimento().getId());
            
            Colaborador colaboradorLiberador = colaboradorManager.findByUsuarioProjection(solicitacao.getLiberador().getId(), null);
			nomeLiberador = colaboradorLiberador!=null?colaboradorLiberador.getNomeMaisNomeComercial():solicitacao.getLiberador().getNome();
			
			solicitacaoAvaliacaos = solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(), null);
			avaliacoesCheckList = CheckListBoxUtil.marcaCheckListBox(avaliacoesCheckList, solicitacaoAvaliacaos, "getAvaliacaoId");
			
			qtdAvaliacoesRespondidas = colaboradorQuestionarioManager.findBySolicitacaoRespondidas(solicitacao.getId()).size();
			
			existeCompetenciaRespondida = configuracaoNivelCompetenciaManager.findByCandidatoAndSolicitacao(null, solicitacao.getId()).size() > 0;
        }

    	Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
    	Long empresaSistemaId = getEmpresaSistema().getId();
    	
    	boolean roleMovSolicitacaoSelecao = SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_SOLICITACAO_SELECAO"});
		if(roleMovSolicitacaoSelecao)
			areas = areaOrganizacionalManager.findAllSelectOrderDescricao(empresaSistemaId, AreaOrganizacional.ATIVA, areaInativaId);
		else
			areas = areaOrganizacionalManager.findAllSelectOrderDescricaoByUsuarioId(empresaSistemaId, usuarioLogado.getId(), AreaOrganizacional.ATIVA, areaInativaId);
		
    	estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();
		faixaSalarials = faixaSalarialUtil.sortCollectionStringIgnoreCase(faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO, faixaInativaId), "cargo.nome");

        motivoSolicitacaos = motivoSolicitacaoManager.findAll();
        
        escolaridades = new Escolaridade();
        sexos = new Sexo();
        vinculos = new Vinculo();
        situacoes = new SituacaoSolicitacao();
        status = new StatusAprovacaoSolicitacao();
    }

    public String prepareInsert() throws Exception
    {
        prepare();
        solicitacao.setQuantidade(1);
        solicitacao.setSexo("I");
        return Action.SUCCESS;
    }

    public String prepareUpdate() throws Exception
    {
        prepare();
        
        this.setStatusSolicitacaoAnterior(solicitacao.getStatus());
        this.setDataStatusSolicitacaoAnterior(solicitacao.getDataStatus());
        this.setObsStatusSolicitacaoAnterior(solicitacao.getObservacaoLiberador());
        
        bairrosCheckList = CheckListBoxUtil.marcaCheckListBox(bairrosCheckList, bairroManager.getBairrosBySolicitacao(solicitacao.getId()), "getId");
        
        if(solicitacao.getStatus() != StatusAprovacaoSolicitacao.ANALISE && !SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"})) {
        	addActionMessage("Esta solicitação está aprovada ou reprovada. Só é possível visualizá-la.");
        	somenteLeitura = true;
        } else if ( SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"}) &&
        		!SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_SOLICITACAO_EDITAR"}) ) {
        	somenteLeitura = true;
        }
        
        if(!SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_EDITA_DATA_STATUS_SOLICITACAO"})) {
        	dataStatusSomenteLeitura = true;
        }
        
        return Action.SUCCESS;
    }

    public String prepareClonar() throws Exception
    {
    	prepare();

    	bairrosCheckList = CheckListBoxUtil.marcaCheckListBox(bairrosCheckList, bairroManager.getBairrosBySolicitacao(solicitacao.getId()), "getId");
    	areas = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null);
    	estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

    	solicitacao.setId(null);
    	solicitacao.setSolicitante(null);
    	solicitacao.setLiberador(null);
    	solicitacao.setData(null);

    	clone = true;

    	return Action.SUCCESS;
    }

    public String insert() throws Exception
    {
        if (bairrosCheck != null)
        {
        	Collection<Bairro> bairrosTmp = montaBairros();
        	solicitacao.setBairros(bairrosTmp);
        }

        Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
        solicitacao.setSolicitante(usuarioLogado);
        solicitacao.setEmpresa(getEmpresaSistema());
        
        if (solicitacao.getCidade() != null && solicitacao.getCidade().getId() == null)
        	solicitacao.setCidade(null);
        
        if(solicitacao.getStatus() == StatusAprovacaoSolicitacao.APROVADO)
           	solicitacao.setLiberador(usuarioLogado);
        else
           	solicitacao.setLiberador(null);
        
      	if (solicitacao.getFuncao() == null || solicitacao.getFuncao().getId() == null || solicitacao.getFuncao().getId() == -1L)
      		solicitacao.setFuncao(null);
      	if (solicitacao.getAmbiente() == null || solicitacao.getAmbiente().getId() == null || solicitacao.getAmbiente().getId() == -1L)
      		solicitacao.setAmbiente(null);
      	
      	insereColaboradorSubstituto();

        solicitacaoManager.save(solicitacao, emailsCheck, LongUtil.arrayStringToArrayLong(avaliacoesCheck));
        
        return Action.SUCCESS;
    }

    public String update() throws Exception
    {
    	if (solicitacao.getDataStatus() == null && solicitacao.getStatus() != statusSolicitacaoAnterior)
			solicitacao.setDataStatus(new Date());
    	
    	if (solicitacao.getStatus() != statusSolicitacaoAnterior)
    		solicitacao.setLiberador(getUsuarioLogado());
    	
        if (bairrosCheck != null)
        	solicitacao.setBairros(montaBairros());
        else
        	solicitacao.setBairros(null);
        
        insereColaboradorSubstituto();
        
        if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"}) && 
       			!SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_SOLICITACAO_EDITAR"}))
		{
        	solicitacao.setLiberador(getUsuarioLogado());
        	solicitacaoManager.updateStatusSolicitacao(solicitacao);
        	
        	new EnviaEmailSolicitanteThread(solicitacao, getEmpresaSistema(), getUsuarioLogado()).start();
		} else
			solicitacaoManager.updateSolicitacao(solicitacao, LongUtil.arrayStringToArrayLong(avaliacoesCheck), getEmpresaSistema(), solicitacao.getLiberador());
        
        return Action.SUCCESS;
    }

	private void insereColaboradorSubstituto() 
	{
		if(colaboradoresSusbstituidos != null && colaboradoresSusbstituidos.length > 0)
      	{
      		solicitacao.setColaboradorSubstituido(colaboradoresSusbstituidos[0]);
      		for (int i = 1; i < colaboradoresSusbstituidos.length; i++) 
				if(!colaboradoresSusbstituidos[i].equals("") && !solicitacao.getColaboradorSubstituido().contains(colaboradoresSusbstituidos[i]))
						solicitacao.setColaboradorSubstituido(solicitacao.getColaboradorSubstituido() + "|;" + colaboradoresSusbstituidos[i]);
      	}
	}

	private Collection<Bairro> montaBairros() {
		Collection<Bairro> bairrosTmp = new ArrayList<Bairro>();

		for (int i = 0; i < bairrosCheck.length; i++)
		{
			Bairro bairroTmp = new Bairro();
			bairroTmp.setId(Long.parseLong(bairrosCheck[i]));
			bairrosTmp.add(bairroTmp);
		}
		return bairrosTmp;
	}

    public String prepareRelatorioProcessoSeletivo() throws Exception
    {
    	setVideoAjuda(785L);
        cargos = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome", null, Cargo.TODOS);
        etapaSeletivaCheckList = CheckListBoxUtil.populaCheckListBox(etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome");
        return Action.SUCCESS;
    }

	public String imprimirRelatorioProcessoSeletivo() throws Exception
    {
        processoSeletivoRelatorios = historicoCandidatoManager.relatorioProcessoSeletivo(ano, cargoId, StringUtil.stringToLong(etapaCheck));

		String nomeCargo = cargoId==null? "Todos" : cargoManager.findByIdProjection(cargoId).getNome();
		String filtro = "Cargo: " + nomeCargo;

		parametros = RelatorioUtil.getParametrosRelatorio("Relatório Processo Seletivo - " + ano, getEmpresaSistema(), filtro);

		return Action.SUCCESS;
    }

    public String prepareRelatorio() throws Exception
    {
        etapaSeletivaCheckList = CheckListBoxUtil.populaCheckListBox(etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome");
        return Action.SUCCESS;
    }

	public String imprimirRelatorio() throws Exception
    {
        String titulo = "Lista de Candidatos da Seleção";
		parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), StatusSolicitacao.getDescricao(statusSolicitacao));

        candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatosBySolicitacao(etapaCheck, getEmpresaSistema().getId(), statusSolicitacao, situacaoCandidato, dataIni, dataFim);

        if(candidatoSolicitacaos.size() > 0)
        {
            dataSource = new CollectionUtil().convertCollectionToList(candidatoSolicitacaos);
            return imprimirObservacao ? "sucess_observacao": Action.SUCCESS;
        }
        else
        {
            ResourceBundle bundle = ResourceBundle.getBundle("application");
            addActionMessage(bundle.getString("error.relatorio.vazio"));

            etapaSeletivaCheckList = CheckListBoxUtil.populaCheckListBox(etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome");

            return Action.INPUT;
        }
    }

    public String imprimirSolicitacaoPessoal() throws Exception
    {
    	Empresa empresa = empresaManager.findByIdProjection(getEmpresaSistema().getId());
    	
        solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
    	parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Solicitação de Pessoal", getEmpresaSistema(), "");
    	parametros.put("EXIBIR_COLABORADOR_SUBSTITUIDO", empresa.isSolPessoalExibirColabSubstituido());

		SolicitacaoPessoalRelatorio solicitacaoPessoal = new SolicitacaoPessoalRelatorio(solicitacao);

		dataSource.add(solicitacaoPessoal);

    	return Action.SUCCESS;
    }

    public Solicitacao getSolicitacao()
    {
        return solicitacao;
    }

    public Collection<AreaOrganizacional> getAreas()
    {
        return areas;
    }

    public void setAreas(Collection<AreaOrganizacional> areas)
    {
        this.areas = areas;
    }

    public HashMap getEscolaridades()
    {
        return escolaridades;
    }

    public void setEscolaridades(HashMap escolaridades)
    {
        this.escolaridades = escolaridades;
    }

    public Collection<FaixaSalarial> getFaixaSalarials()
    {
        return faixaSalarials;
    }

    public void setFaixaSalarials(Collection<FaixaSalarial> faixaSalarials)
    {
        this.faixaSalarials = faixaSalarials;
    }

    public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
    {
        this.areaOrganizacionalManager = areaOrganizacionalManager;
    }

    public void setSolicitacao(Solicitacao solicitacao)
    {
        this.solicitacao = solicitacao;
    }

    public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
    {
        this.solicitacaoManager = solicitacaoManager;
    }

    public HashMap getSexos()
    {
        return sexos;
    }

    public void setSexos(HashMap sexos)
    {
        this.sexos = sexos;
    }

    public HashMap getVinculos()
    {
        return vinculos;
    }

    public void setVinculos(HashMap vinculos)
    {
        this.vinculos = vinculos;
    }

    public void setEtapaSeletivaManager(EtapaSeletivaManager etapaSeletivaManager) {
        this.etapaSeletivaManager = etapaSeletivaManager;
    }

    public Collection<CheckBox> getEtapaSeletivaCheckList() {
        return etapaSeletivaCheckList;
    }

    public void setEtapaSeletivaCheckList(
            Collection<CheckBox> etapaSeletivaCheckList) {
        this.etapaSeletivaCheckList = etapaSeletivaCheckList;
    }

    public String[] getEtapaCheck() {
        return etapaCheck;
    }

    public void setEtapaCheck(String[] etapaCheck) {
        this.etapaCheck = etapaCheck;
    }

    public void setCandidatoSolicitacaoManager(
            CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
        this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
    }

    public List getDataSource()
    {
        return dataSource;
    }

    public Map getParametros()
    {
        return parametros;
    }

    public void setDataSource(List<SolicitacaoPessoalRelatorio> dataSource)
    {
        this.dataSource = dataSource;
    }

    public void setCargoManager(CargoManager cargoManager)
    {
        this.cargoManager = cargoManager;
    }

    public String getAno()
    {
        return ano;
    }

    public void setAno(String ano)
    {
        this.ano = ano;
    }

    public ParametrosDoSistema getParametrosDoSistema()
    {
        return parametrosDoSistema;
    }

    public void setParametrosDoSistema(ParametrosDoSistema parametrosDoSistema)
    {
        this.parametrosDoSistema = parametrosDoSistema;
    }

    public void setHistoricoCandidatoManager(HistoricoCandidatoManager historicoCandidatoManager)
    {
        this.historicoCandidatoManager = historicoCandidatoManager;
    }

    public Collection<Cargo> getCargos()
    {
        return cargos;
    }

    public void setCargos(Collection<Cargo> cargos)
    {
        this.cargos = cargos;
    }

    public Long getCargoId()
    {
        return cargoId;
    }

    public void setCargoId(Long cargoId)
    {
        this.cargoId = cargoId;
    }

	public void setMotivoSolicitacaoManager(MotivoSolicitacaoManager motivoSolicitacaoManager)
	{
		this.motivoSolicitacaoManager = motivoSolicitacaoManager;
	}

	public Collection<MotivoSolicitacao> getMotivoSolicitacaos()
	{
		return motivoSolicitacaos;
	}

	public void setMotivoSolicitacaos(Collection<MotivoSolicitacao> motivoSolicitacaos)
	{
		this.motivoSolicitacaos = motivoSolicitacaos;
	}

	public MotivoSolicitacao getMotivoSolicitacao()
	{
		return motivoSolicitacao;
	}

	public void setMotivoSolicitacao(MotivoSolicitacao motivoSolicitacao)
	{
		this.motivoSolicitacao = motivoSolicitacao;
	}

	public Collection<CandidatoSolicitacao> getCandidatoSolicitacaos() {
        return candidatoSolicitacaos;
    }

    public void setCandidatoSolicitacaos(
            Collection<CandidatoSolicitacao> candidatoSolicitacaos) {
        this.candidatoSolicitacaos = candidatoSolicitacaos;
    }

	public FuncaoManager getFuncaoManager()
	{
		return funcaoManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public AmbienteManager getAmbienteManager()
	{
		return ambienteManager;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public Collection<Ambiente> getAmbientes()
	{
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes)
	{
		this.ambientes = ambientes;
	}

	public Collection<Funcao> getFuncoes()
	{
		return funcoes;
	}

	public void setFuncoes(Collection<Funcao> funcoes)
	{
		this.funcoes = funcoes;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Cidade> getCidades()
	{
		return cidades;
	}

	public void setCidades(Collection<Cidade> cidades)
	{
		this.cidades = cidades;
	}

	public Collection<Estado> getEstados()
	{
		return estados;
	}

	public void setEstados(Collection<Estado> estados)
	{
		this.estados = estados;
	}

	public void setEstadoManager(EstadoManager estadoManager)
	{
		this.estadoManager = estadoManager;
	}

	public void setCidadeManager(CidadeManager cidadeManager)
	{
		this.cidadeManager = cidadeManager;
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

	public Cidade getCidade()
	{
		return cidade;
	}

	public void setCidade(Cidade cidade)
	{
		this.cidade = cidade;
	}

	public Estado getEstado()
	{
		return estado;
	}

	public void setEstado(Estado estado)
	{
		this.estado = estado;
	}

	public void setBairroManager(BairroManager bairroManager)
	{
		this.bairroManager = bairroManager;
	}

	public HashMap getSituacoes()
	{
		return situacoes;
	}

	public void setSituacoes(HashMap situacoes)
	{
		this.situacoes = situacoes;
	}

	public boolean isExibeSalario()
	{
		return exibeSalario;
	}

	public void setExibeSalario(boolean exibeSalario)
	{
		this.exibeSalario = exibeSalario;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public boolean isClone()
	{
		return clone;
	}

	public void setVisualizar(char visualizar)
	{
		this.visualizar = visualizar;
	}

	public char getVisualizar()
	{
		return visualizar;
	}

	public Cargo getCargo()
	{
		return cargo;
	}

	public void setCargo(Cargo cargo)
	{
		this.cargo = cargo;
	}

	public boolean isSomenteLeitura()
	{
		return somenteLeitura;
	}

	public boolean isPossuiCandidatoSolicitacao() {
		return possuiCandidatoSolicitacao;
	}

	public Collection<ProcessoSeletivoRelatorio> getProcessoSeletivoRelatorios()
	{
		return processoSeletivoRelatorios;
	}

	public String getDataDoDia() 
	{
		return DateUtil.formataDiaMesAno(new Date());
	}

	public void setAvaliacaoManager(AvaliacaoManager avaliacaoManager) {
		this.avaliacaoManager = avaliacaoManager;
	}

	public Collection<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public Collection<CheckBox> getEmailsCheckList() {
		return emailsCheckList;
	}

	public void setEmailsCheck(String[] emailsCheck) {
		this.emailsCheck = emailsCheck;
	}

	public HashMap getStatus() {
		return status;
	}

	public void setStatus(HashMap status) {
		this.status = status;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public String getNomeLiberador() {
		return nomeLiberador;
	}

	public void setNomeLiberador(String nomeLiberador) {
		this.nomeLiberador = nomeLiberador;
	}

	public boolean isExibeColaboradorSubstituido() {
		return exibeColaboradorSubstituido;
	}

	public char getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(char statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}

	public char getSituacaoCandidato() {
		return situacaoCandidato;
	}

	public void setSituacaoCandidato(char situacaoCandidato) {
		this.situacaoCandidato = situacaoCandidato;
	}
	
	public void setImprimirObservacao(boolean imprimirObservacao)
	{
		this.imprimirObservacao = imprimirObservacao;
	}

	public void setAvaliacoesCheck(String[] avaliacoesCheck) {
		this.avaliacoesCheck = avaliacoesCheck;
	}

	public Collection<CheckBox> getAvaliacoesCheckList() {
		return avaliacoesCheckList;
	}

	public void setSolicitacaoAvaliacaoManager(
			SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager) {
		this.solicitacaoAvaliacaoManager = solicitacaoAvaliacaoManager;
	}

	public void setColaboradorQuestionarioManager(
			ColaboradorQuestionarioManager colaboradorQuestionarioManager) {
		this.colaboradorQuestionarioManager = colaboradorQuestionarioManager;
	}

	public int getQtdAvaliacoesRespondidas() {
		return qtdAvaliacoesRespondidas;
	}

	public Collection<SolicitacaoAvaliacao> getSolicitacaoAvaliacaos() {
		return solicitacaoAvaliacaos;
	}

	public boolean isObrigarAmbienteFuncao() {
		return obrigarAmbienteFuncao;
	}
	
	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}
	
	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public String[] getColaboradoresSusbstituidos() {
		return colaboradoresSusbstituidos;
	}

	public void setColaboradoresSusbstituidos(String[] colaboradoresSusbstituidos) {
		this.colaboradoresSusbstituidos = colaboradoresSusbstituidos;
	}

	public boolean isObrigaDadosComplementares() {
		return obrigaDadosComplementares;
	}

	public boolean isExisteCompetenciaRespondida() {
		return existeCompetenciaRespondida;
	}

	public void setConfiguracaoNivelCompetenciaManager(
			ConfiguracaoNivelCompetenciaManager configuracaoNivelCompetenciaManager) {
		this.configuracaoNivelCompetenciaManager = configuracaoNivelCompetenciaManager;
	}

	public boolean isDataStatusSomenteLeitura() {
		return dataStatusSomenteLeitura;
	}

	public void setDataStatusSomenteLeitura(boolean dataStatusSomenteLeitura) {
		this.dataStatusSomenteLeitura = dataStatusSomenteLeitura;
	}

	public char getStatusSolicitacaoAnterior() {
		return statusSolicitacaoAnterior;
	}

	public void setStatusSolicitacaoAnterior(char statusSolicitacaoAnterior) {
		this.statusSolicitacaoAnterior = statusSolicitacaoAnterior;
	}

	public Date getDataStatusSolicitacaoAnterior() {
		return dataStatusSolicitacaoAnterior;
	}

	public void setDataStatusSolicitacaoAnterior(
			Date dataStatusSolicitacaoAnterior) {
		this.dataStatusSolicitacaoAnterior = dataStatusSolicitacaoAnterior;
	}

	public String getObsStatusSolicitacaoAnterior() {
		return obsStatusSolicitacaoAnterior;
	}

	public void setObsStatusSolicitacaoAnterior(
			String obsStatusSolicitacaoAnterior) {
		this.obsStatusSolicitacaoAnterior = obsStatusSolicitacaoAnterior;
	}
}