package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExceditoException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.CodigoGFIP;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class HistoricoColaboradorEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private HistoricoColaboradorManager historicoColaboradorManager;
	private IndiceManager indiceManager;
	private FuncaoManager funcaoManager;
	private AmbienteManager ambienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private FaixaSalarialManager faixaSalarialManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ColaboradorManager colaboradorManager;
	private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;

	private Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
	private Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
	private Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
	private Collection<Funcao> funcaos = new ArrayList<Funcao>();
	private Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
	private Collection<Indice> indices = new ArrayList<Indice>();
	
	private HistoricoColaborador historicoColaborador;
	private Map<Object, Object> tiposSalarios = new TipoAplicacaoIndice();
	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();
	private Colaborador colaborador;
	private boolean folhaProcessada;
	private Double salarioProcessado;
	
	private Map<String, String> codigosGFIP = CodigoGFIP.getInstance();

	private Collection<HistoricoColaborador> historicoColaboradors;
	private Collection<Colaborador> colaboradors;
	private String colaboradorNome;
	
	private Candidato candidato;
	private Long candidatoSolicitacaoId;

	private boolean integraAc;

	private Date dataPrimeiroHist;	
	
	public void prepare() throws Exception
	{
		integraAc = getEmpresaSistema().isAcIntegra();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		indices = indiceManager.findAll(getEmpresaSistema());
		
		Long faixaInativaId = null;
		Long areaInativaId = null;
		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();
		
		if(historicoColaborador != null)
		{
			if(historicoColaborador.getFaixaSalarial() != null && historicoColaborador.getFaixaSalarial().getCargo() != null && historicoColaborador.getFaixaSalarial().getCargo().getId() != null)
				funcaos = funcaoManager.findByCargo(historicoColaborador.getFaixaSalarial().getCargo().getId());
			
			if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getId() != null)
				ambientes = ambienteManager.findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());
			
			salarioProcessado = historicoColaborador.getSalarioCalculado();
			
			faixaInativaId = historicoColaborador.getFaixaSalarial().getId();
			areaInativaId = historicoColaborador.getAreaOrganizacional().getId();
		}
		else
		{
			historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId());
			historicoColaborador.setId(null);
			historicoColaborador.setData(new Date());
		}

		faixaSalarials = faixaSalarialUtil.sortCollectionStringIgnoreCase(faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO, faixaInativaId), "cargo.nome");
		
		areaOrganizacionals = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, areaInativaId);
	}
	
	public String prepareInsert() throws Exception
	{
		dataPrimeiroHist = historicoColaboradorManager.getPrimeiroHistorico(colaborador.getId()).getData();
		
		historicoColaborador = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());
		if(historicoColaborador != null)
		{
			historicoColaborador.setData(new Date());
			historicoColaborador.setId(null);
			historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		}
		
		prepare();
		
		return Action.SUCCESS;
	}
	
	public String insert() throws Exception
	{
		try
		{
			if(historicoColaboradorManager.existeHistoricoData(historicoColaborador))
			{
				addActionMessage("Já existe uma Situação nessa data.");
				prepareInsert();
				return Action.INPUT;				
			}
			
			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), historicoColaborador.getColaborador().getId());
			
			if(historicoColaborador.getMotivo().equals(MotivoHistoricoColaborador.CONTRATADO))
				historicoColaboradorManager.ajustaMotivoContratado(historicoColaborador.getColaborador().getId());
			
			historicoColaborador = historicoColaboradorManager.ajustaAmbienteFuncao(historicoColaborador);
			historicoColaboradorManager.insertHistorico(historicoColaborador, getEmpresaSistema());
			if (candidatoSolicitacaoId != null)
				candidatoSolicitacaoManager.setStatus(candidatoSolicitacaoId, StatusCandidatoSolicitacao.PROMOVIDO);
			
			return Action.SUCCESS;
		}
		catch (IntegraACException e)
		{
			String msg = "Não foi possível atualizar esta Situação no AC Pessoal.";
			if (e.getMensagemDetalhada() != null)
				msg = e.getMensagemDetalhada();
			
			addActionError(msg);
			
			prepareInsert();

			return Action.INPUT;
		}
		catch (LimiteColaboradorExceditoException e)
		{
			e.printStackTrace();
			addActionError(e.getMessage());
			prepareInsert();
			
			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível inserir Situação.");
			prepareInsert();

			return Action.INPUT;
		}
	}

	public String prepareUpdate() throws Exception
	{
		historicoColaborador = historicoColaboradorManager.findByIdHQL(historicoColaborador.getId());
		
		if(getEmpresaSistema().isAcIntegra() && !historicoColaborador.getColaborador().isNaoIntegraAc())
		{
			if(historicoColaboradorManager.verificaHistoricoNaFolhaAC(historicoColaborador.getId(), historicoColaborador.getColaborador().getCodigoAC(), getEmpresaSistema()))
			{
				folhaProcessada = true;
				setActionMsg("<div>Uma Folha de Pagamento foi processada no AC Pessoal com este Histórico.<br>Só é permitido editar Função e Ambiente.</div>");
			}
		}		
		
		prepare();
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try
		{
			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), historicoColaborador.getColaborador().getId());
			
			historicoColaborador = historicoColaboradorManager.ajustaAmbienteFuncao(historicoColaborador);
			historicoColaboradorManager.updateHistorico(historicoColaborador, getEmpresaSistema());
			
			return Action.SUCCESS;
		}
		catch (IntegraACException e)
		{
			String msg = "Não foi possível atualizar esta Situação no AC Pessoal.";
			if (e.getMensagemDetalhada() != null)
				msg = e.getMensagemDetalhada();
			
			addActionError(msg);
			prepareUpdate();
			return Action.INPUT;
		}
		catch (LimiteColaboradorExceditoException e)
		{
			e.printStackTrace();
			addActionError(e.getMessage());
			prepareInsert();
			
			return Action.INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível atualizar esta Situação.");
			prepareUpdate();
			
			return Action.INPUT;
		}
	}
	
	public String list() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public String prepareUpdateAmbientesEFuncoes() throws Exception
	{
		if(colaborador != null)
		{
			colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, getEmpresaSistema().getId(), true);
			
			if(colaborador.getId() != null)
			{
				colaboradorNome = colaboradorManager.getNome(colaborador.getId());
				
				historicoColaboradors = historicoColaboradorManager.getHistoricosComAmbienteEFuncao(colaborador.getId());
			}
		}
		
		return SUCCESS;
	}
	
	public String updateAmbientesEFuncoes() throws Exception
	{
		try
		{
			historicoColaboradorManager.updateAmbientesEFuncoes(historicoColaboradors);
			
			prepareUpdateAmbientesEFuncoes();
			addActionMessage("Ambientes e Funções do Colaborador gravados com sucesso.");
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar os Ambientes e Funções do Colaborador.");
			prepareUpdateAmbientesEFuncoes();
			return INPUT;
		}
		
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		if (historicoColaborador == null)
			historicoColaborador = new HistoricoColaborador();

		return historicoColaborador;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public Object getModel()
	{
		return getHistoricoColaborador();
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public Collection<Ambiente> getAmbientes()
	{
		return ambientes;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public Collection<FaixaSalarial> getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public Collection<Funcao> getFuncaos()
	{
		return funcaos;
	}

	public Collection<Indice> getIndices()
	{
		return indices;
	}

	public TipoAplicacaoIndice getTipoAplicacaoIndice()
	{
		return tipoAplicacaoIndice;
	}

	public Map<Object, Object> getTiposSalarios()
	{
		return tiposSalarios;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public boolean isFolhaProcessada()
	{
		return folhaProcessada;
	}

	public Double getSalarioProcessado()
	{
		return salarioProcessado;
	}

	public Map<String, String> getCodigosGFIP() {
		return codigosGFIP;
	}

	public Collection<HistoricoColaborador> getHistoricoColaboradors() {
		return historicoColaboradors;
	}

	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors) {
		this.historicoColaboradors = historicoColaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public String getColaboradorNome() {
		return colaboradorNome;
	}

	public void setCandidato(Candidato candidato) {
		this.candidato = candidato;
	}
	
	public void setCandidatoSolicitacaoId(Long candidatoSolicitacaoId) {
		this.candidatoSolicitacaoId = candidatoSolicitacaoId;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager) {
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public Long getCandidatoSolicitacaoId() {
		return candidatoSolicitacaoId;
	}

	public boolean isIntegraAc() {
		return integraAc;
	}

	public Date getDataPrimeiroHist() {
		return dataPrimeiroHist;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager) {
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}
	
}