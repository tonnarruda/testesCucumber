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
import com.fortes.rh.business.captacao.EtapaSeletivaManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.MotivoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.BairroManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.MotivoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.ProcessoSeletivoRelatorio;
import com.fortes.rh.model.captacao.relatorio.SolicitacaoPessoalRelatorio;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.SituacaoSolicitacao;
import com.fortes.rh.model.dicionario.TipoModeloAvaliacao;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Bairro;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
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

    private Solicitacao solicitacao = new Solicitacao();
    private MotivoSolicitacao motivoSolicitacao;

    private Collection<AreaOrganizacional> areas;
    private Collection<FaixaSalarial> faixaSalarials;
    private Collection<MotivoSolicitacao> motivoSolicitacaos;
    private Collection<Estabelecimento> estabelecimentos;

    private Collection<Funcao> funcoes;
    private Collection<Ambiente> ambientes;

    private String[] bairrosCheck;
    private Collection<CheckBox> bairrosCheckList = new ArrayList<CheckBox>();
    private String[] emailsCheck;
    private Collection<CheckBox> emailsCheckList = new ArrayList<CheckBox>();
    private Collection<Cargo> cargos = new ArrayList<Cargo>();
    private Long cargoId;

	private HashMap escolaridades;
    private HashMap sexos;
    private HashMap vinculos;
    private HashMap situacoes;

    private Collection<CheckBox> etapaSeletivaCheckList = new ArrayList<CheckBox>();
    private String[] etapaCheck;

    private String ano;

    private List<SolicitacaoPessoalRelatorio> dataSource = new ArrayList<SolicitacaoPessoalRelatorio>();
    private Map<String,Object> parametros = new HashMap<String, Object>();
    private ParametrosDoSistema parametrosDoSistema;

    private Collection<CandidatoSolicitacao> candidatoSolicitacaos;
    private Collection<ProcessoSeletivoRelatorio> processoSeletivoRelatorios;
	private Collection<Estado> estados = new ArrayList<Estado>();
	private Collection<Cidade> cidades = new ArrayList<Cidade>();

	private Estado estado;
	private Cidade cidade;

	private boolean exibeSalario;
	private boolean clone;
	private boolean somenteLeitura;

	private char visualizar;
	private Cargo cargo;
	private Collection<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();

    private void prepare() throws Exception
    {
    	exibeSalario = empresaManager.findExibirSalarioById(getEmpresaSistema().getId());

    	this.estados = estadoManager.findAll(new String[]{"sigla"});

    	if (solicitacao != null && solicitacao.getId() != null)
        {
            solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
            estado = solicitacao.getCidade().getUf();

            if(estado != null && estado.getId() != null)
    			cidades = cidadeManager.find(new String[]{"uf.id"}, new Object[]{Long.valueOf(estado.getId())}, new String[]{"nome"});

            if(solicitacao.getCidade() != null && solicitacao.getCidade().getId() != null)
            {
            	Collection<Bairro> bairroList = bairroManager.findToList(new String[]{"id", "nome"}, new String[]{"id", "nome"}, new String[]{"cidade.id"}, new Object[]{solicitacao.getCidade().getId()}, new String[]{"nome"});
            	bairrosCheckList = CheckListBoxUtil.populaCheckListBox(bairroList, "getId", "getNome");
            }
            
            // Campos somente leitura se já houver candidatos na solicitação
            if (candidatoSolicitacaoManager.getCount(new String[] {"solicitacao.id"}, new Object[]{solicitacao.getId()}) > 0)
            	somenteLeitura = true;
        }

    	areas = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA);
    	estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();
		faixaSalarials = faixaSalarialUtil.sortCollectionStringIgnoreCase(faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO), "cargo.nome");

        motivoSolicitacaos = motivoSolicitacaoManager.findAll();
        
        avaliacoes = avaliacaoManager.findAllSelect(getEmpresaSistema().getId(), true, TipoModeloAvaliacao.SOLICITACAO);

        escolaridades = new Escolaridade();
        sexos = new Sexo();
        vinculos = new Vinculo();
        situacoes = new SituacaoSolicitacao();
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

        bairrosCheckList = CheckListBoxUtil.marcaCheckListBox(bairrosCheckList, bairroManager.getBairrosBySolicitacao(solicitacao.getId()), "getId");

        return Action.SUCCESS;
    }

    public String prepareClonar() throws Exception
    {
    	prepare();

    	bairrosCheckList = CheckListBoxUtil.marcaCheckListBox(bairrosCheckList, bairroManager.getBairrosBySolicitacao(solicitacao.getId()), "getId");
    	areas = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.TODAS);
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
        	Collection<Bairro> bairrosTmp = montaCargos();
        	solicitacao.setBairros(bairrosTmp);
        }

        solicitacao.setData(new Date());
        Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
        solicitacao.setSolicitante(usuarioLogado);
        solicitacao.setEmpresa(getEmpresaSistema());
        
        if (solicitacao.getCidade() != null && solicitacao.getCidade().getId() == null)
        	solicitacao.setCidade(null);
        
        if (solicitacao.getAvaliacao().getId() == null)
        	solicitacao.setAvaliacao(null);
        
        if(solicitacao.isLiberada())
           	solicitacao.setLiberador(usuarioLogado);
        else
           	solicitacao.setLiberador(null);
        
        solicitacaoManager.save(solicitacao, emailsCheck);

        return Action.SUCCESS;
    }

    public String update() throws Exception
    {
    	Solicitacao solicitacaoAux = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
    	
        if (bairrosCheck != null)
        {
        	Collection<Bairro> bairrosTmp = montaCargos();
        	solicitacao.setBairros(bairrosTmp);
        }
        else
        	solicitacao.setBairros(null);

        if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_LIBERA_SOLICITACAO"}))
        {
        	if(solicitacao.isLiberada() && !solicitacaoAux.isLiberada())
    		{
    			solicitacao.setLiberador(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()));
    			solicitacaoManager.emailParaSolicitante(solicitacaoAux.getSolicitante(), solicitacao, getEmpresaSistema());
    		}
        	
        	if(!solicitacao.isLiberada())
        		solicitacao.setLiberador(null);
        }else
        {
        	solicitacao.setLiberada(solicitacaoAux.isLiberada());
        	solicitacao.setLiberador(solicitacaoAux.getLiberador());
        }

        if (solicitacao.getAreaOrganizacional() == null || solicitacao.getAreaOrganizacional().getId() == null)
        	solicitacao.setAreaOrganizacional(solicitacaoAux.getAreaOrganizacional());
        if (solicitacao.getEstabelecimento() == null || solicitacao.getEstabelecimento().getId() == null)
        	solicitacao.setEstabelecimento(solicitacaoAux.getEstabelecimento());
        if (solicitacao.getFaixaSalarial() == null || solicitacao.getFaixaSalarial().getId() == null)
        	solicitacao.setFaixaSalarial(solicitacaoAux.getFaixaSalarial());
        if (solicitacao.getEmpresa() == null || solicitacao.getEmpresa().getId() == null)
        	solicitacao.setEmpresa(solicitacaoAux.getEmpresa());

        if (solicitacao.getAvaliacao().getId() == null)
        	solicitacao.setAvaliacao(null);
        if (solicitacao.getCidade() != null && solicitacao.getCidade().getId() == null)
        	solicitacao.setCidade(null);
        
        solicitacaoManager.update(solicitacao);

        return Action.SUCCESS;
    }

	private Collection<Bairro> montaCargos() {
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
        cargos = cargoManager.findAllSelect(getEmpresaSistema().getId(), "nome");
        etapaSeletivaCheckList = CheckListBoxUtil.populaCheckListBox(etapaSeletivaManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome");
        return Action.SUCCESS;
    }

	public String imprimirRelatorioProcessoSeletivo() throws Exception
    {
        processoSeletivoRelatorios = historicoCandidatoManager.relatorioProcessoSeletivo(ano, cargoId, StringUtil.stringToLong(etapaCheck));

		String filtro = "Cargo: " + cargoManager.findByIdProjection(cargoId).getNome();
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
        String titulo = "Relatório de Candidatos Aptos das Solicitações Abertas";
		parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), "");

        candidatoSolicitacaos = candidatoSolicitacaoManager.getCandidatosBySolicitacaoAberta(etapaCheck, getEmpresaSistema().getId());

        if(candidatoSolicitacaos.size() > 0)
        {
            dataSource = new CollectionUtil().convertCollectionToList(candidatoSolicitacaos);
            return Action.SUCCESS;
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
        solicitacao = solicitacaoManager.findByIdProjectionForUpdate(solicitacao.getId());
    	parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Solicitação Pessoal", getEmpresaSistema(), "");

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
}