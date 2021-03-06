package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.QuantidadeLimiteColaboradoresPorCargoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.ESocialException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.exception.LimiteColaboradorExcedidoException;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.CodigoGFIP;
import com.fortes.rh.model.dicionario.MotivoHistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
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
	private SolicitacaoManager solicitacaoManager;
	private QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager;
	private PlatformTransactionManager transactionManager;

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
	private Map situacaos = new SituacaoColaborador();
	private String situacao;

	private Map<String, String> codigosGFIP = CodigoGFIP.getInstance();

	private Collection<HistoricoColaborador> historicoColaboradors;
	private Collection<Colaborador> colaboradors;
	private String colaboradorNome;

	private Long candidatoSolicitacaoId;
	private Date dataPrimeiroHist;
	private boolean integraAc;
	private boolean obrigarAmbienteFuncao;	
	private Boolean somenteAtivos = null;	
	private String encerrarSolicitacao;

	private Solicitacao solicitacao;

	private boolean empresaEstaIntegradaEAderiuAoESocial;

	private boolean disabledCamposIntegrados;

	public void prepare() throws Exception
	{
		obrigarAmbienteFuncao = getEmpresaSistema().isObrigarAmbienteFuncao();
		integraAc = getEmpresaSistema().isAcIntegra();
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		indices = indiceManager.findAll(getEmpresaSistema());
		funcaos = funcaoManager.findByEmpresa(getEmpresaSistema().getId());

		Long faixaInativaId = null;
		Long areaInativaId = null;
		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();

		if (historicoColaborador != null) {

			if(historicoColaborador.getEstabelecimento() != null && historicoColaborador.getEstabelecimento().getId() != null)
				ambientes = ambienteManager.findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());

			salarioProcessado = historicoColaborador.getSalarioCalculado();
			if (salarioProcessado == null || salarioProcessado == 0) {
				salarioProcessado = historicoColaborador.getSalario();
			}

			faixaInativaId = historicoColaborador.getFaixaSalarial().getId();
			areaInativaId = historicoColaborador.getAreaOrganizacional().getId();
		} else {
			historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId());
			historicoColaborador.setId(null);
			historicoColaborador.setData(new Date());
			historicoColaborador.setMotivo(MotivoHistoricoColaborador.SEM_MOTIVO);
			ambientes = ambienteManager.findByEstabelecimento(historicoColaborador.getEstabelecimento().getId());
		}

		faixaSalarials = faixaSalarialUtil.sortCollectionStringIgnoreCase(faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO, faixaInativaId), "cargo.nome");

		areaOrganizacionals = areaOrganizacionalManager.findAllSelectOrderDescricao(getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, areaInativaId, true);
		
		if ( getEmpresaSistema().isAcIntegra() ) {
			String mascara = areaOrganizacionalManager.getMascaraLotacoesAC(getEmpresaSistema()).replaceAll("9", "A");
			if(mascara != "") {
				for (AreaOrganizacional area : areaOrganizacionals) {
					area.setMascara(mascara);
				}
			}
		}
	}

	public String prepareInsert() throws Exception
	{
		
		if((historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), StatusRetornoAC.AGUARDANDO).size() > 0 )){
			setActionMsg("Não é possível inserir uma nova situação, pois existe situação aguardando confirmação no Fortes Pessoal.");
			return Action.INPUT;
		}

		dataPrimeiroHist = historicoColaboradorManager.getPrimeiroHistorico(colaborador.getId()).getData();

		HistoricoColaborador historicoColaboradorAtual = historicoColaboradorManager.getHistoricoAtual(colaborador.getId());
		
		if(isEmpresaIntegradaEAderiuAoESocial() && historicoColaboradorManager.existeHistoricoContratualComPendenciaNoESocial(getEmpresaSistema(), historicoColaboradorAtual.getColaborador().getCodigoAC())){
			setActionMsg("No Fortes Pessoal existem pendências no histórico contratual do colaborador. Não é possível inserir uma nova situação enquando essas pendências não forem resolvidas no Fortes Pessoal.");
			return Action.INPUT;
		}
						
		if (solicitacao != null && solicitacao.getId() != null && solicitacao.getId() > 0) {
			historicoColaborador = new HistoricoColaborador();
			historicoColaborador.setColaborador(historicoColaboradorAtual.getColaborador());
			historicoColaborador.setGfip(historicoColaboradorAtual.getGfip());
			
			Solicitacao solicitacaoPromocao = solicitacaoManager.findById(solicitacao.getId());
			
			historicoColaborador.setSalario(solicitacaoPromocao.getRemuneracao());
			
			if (solicitacaoPromocao.getEstabelecimento() != null) {
				historicoColaborador.setEstabelecimento(solicitacaoPromocao.getEstabelecimento());
			}
			
			if (solicitacaoPromocao.getAreaOrganizacional() != null) {
				historicoColaborador.setAreaOrganizacional(solicitacaoPromocao.getAreaOrganizacional());
			}
			
			if (solicitacaoPromocao.getAmbiente() != null) {
				historicoColaborador.setAmbiente(solicitacaoPromocao.getAmbiente());
			}
			
			if (solicitacaoPromocao.getFaixaSalarial() != null) {
				historicoColaborador.setFaixaSalarial(faixaSalarialManager.findById(solicitacaoPromocao.getFaixaSalarial().getId()));
			}
			
			if (solicitacaoPromocao.getFuncao() != null) {
				historicoColaborador.setFuncao(solicitacaoPromocao.getFuncao());
			}
		
			historicoColaborador.setId(null);
			historicoColaborador.setData(new Date());
			historicoColaborador.setMotivo(MotivoHistoricoColaborador.PROMOCAO);
		}

		prepare();

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);

		try
		{
			colaborador = colaboradorManager.findColaboradorById(historicoColaborador.getColaborador().getId());
			historicoColaborador.setColaborador(colaborador);
			
			if(historicoColaboradorManager.existeHistoricoData(historicoColaborador))
			{
				addActionMessage("Já existe uma Situação nessa data.");
				prepareInsert();
				return Action.INPUT;				
			}

			if(historicoColaboradorManager.verificaPrimeiroHistoricoAdmissao(true, historicoColaborador, colaborador))
			{
				addActionError("Data do histórico não pode ser inferior à data de admissão (Data Admissão: " + colaborador.getDataAdmissaoFormatada() + ")." );
				prepareInsert();
				return Action.INPUT;
			}

			quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), historicoColaborador.getColaborador().getId());

			if(historicoColaborador.getMotivo().equals(MotivoHistoricoColaborador.CONTRATADO))
				historicoColaboradorManager.ajustaMotivoContratado(historicoColaborador.getColaborador().getId());

			historicoColaborador = historicoColaboradorManager.ajustaAmbienteFuncao(historicoColaborador);

			if (candidatoSolicitacaoId != null)
			{
				candidatoSolicitacaoManager.setStatusAndDataContratacaoOrPromocao(candidatoSolicitacaoId, StatusCandidatoSolicitacao.PROMOVIDO, getDataPromocao());
				historicoColaborador.setcandidatoSolicitacaoId(candidatoSolicitacaoId);
			}

			historicoColaboradorManager.insertHistorico(historicoColaborador, getEmpresaSistema());
			
			if (StringUtils.equals(encerrarSolicitacao, "S")) 
				solicitacaoManager.encerrarSolicitacaoAoPreencherTotalVagas(solicitacao, getEmpresaSistema());

			transactionManager.commit(status);

			if(solicitacao != null && solicitacao.getId() != null)
			{
				addActionSuccess("Colaborador <strong>" + colaborador.getNome() + "</strong>  promovido com sucesso.");
				return "successSolicitacao";
			}else
				return Action.SUCCESS;
		}
		catch (IntegraACException e)
		{
			transactionManager.rollback(status);

			String msg = "Não foi possível atualizar esta Situação no Fortes Pessoal.";
			if (e.getMensagemDetalhada() != null)
				msg = e.getMensagemDetalhada();

			addActionError(msg);

			prepareInsert();

			return Action.INPUT;
		}
		catch (LimiteColaboradorExcedidoException e)
		{
			transactionManager.rollback(status);

			e.printStackTrace();
			addActionWarning(e.getMessage());
			prepareInsert();

			return Action.INPUT;
		}
		catch (Exception e)
		{
			transactionManager.rollback(status);

			addActionError("Não foi possível inserir Situação.");
			prepareInsert();

			return Action.INPUT;
		}
	}
	
	private Date getDataPromocao(){
		if(historicoColaborador.getData().after(DateUtil.criarDataMesAno(new Date()))){
			return new Date();
		}
		return historicoColaborador.getData();
	}

	public String prepareUpdate() throws Exception
	{
		Collection<HistoricoColaborador> historicosColaboradores = historicoColaboradorManager.findByColaboradorProjection(colaborador.getId(), StatusRetornoAC.AGUARDANDO);
		historicoColaborador = historicoColaboradorManager.findByIdHQL(historicoColaborador.getId());
		
		
		if(historicosColaboradores.size() > 0  && !historicosColaboradores.contains(historicoColaborador)){
			setActionMsg("Não é possível editar esta situação, enquanto existir outra situação aguardando confirmação no Fortes Pessoal.");
			return Action.INPUT;
		}

		if(getEmpresaSistema().isAcIntegra() && !historicoColaborador.getColaborador().isNaoIntegraAc())
		{
			if(historicoColaboradorManager.verificaHistoricoNaFolhaAC(historicoColaborador.getId(), historicoColaborador.getColaborador().getCodigoAC(), getEmpresaSistema()))
			{
				folhaProcessada = true;
				setActionMsg("<div>Uma Folha de Pagamento foi processada no Fortes Pessoal com este Histórico.<br>Só é permitido editar Função e Ambiente.</div>");
			}
				if(!folhaProcessada)
					configuraEdicaoCamposIntegrados();	
		}		

		prepare();
		return Action.SUCCESS;
	}

	private void configuraEdicaoCamposIntegrados() {
		disabledCamposIntegrados = false;
		if(isEmpresaIntegradaEAderiuAoESocial()){
			try {
				verificaPendenciaNoESocial();
				if(!disabledCamposIntegrados)
					verificaInicioDoVinculo();
			} catch (Exception e) {
				e.printStackTrace();
				disabledCamposIntegrados = true;
				addActionWarning("Não foi possível estabelecer conexão com o Fortes Pessoal, desta forma não é possível editar as informações integradas.");
			}
		}
	}

	private void verificaPendenciaNoESocial() throws Exception{
		disabledCamposIntegrados = historicoColaboradorManager.existeHistoricoContratualComPendenciaNoESocial(getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC());
		if(disabledCamposIntegrados){
			addActionMessage("Existem pendências no histórico contratual do colaborador junto ao eSocial. Não é possível editar as informações integradas no Fortes RH, só podem ser alteradas no Fortes Pessoal.");
		} 
	}
	
	private void verificaInicioDoVinculo() throws Exception{
		disabledCamposIntegrados = historicoColaboradorManager.situacaoContratualEhInicioVinculo(getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC(), historicoColaborador.getData());
		if(disabledCamposIntegrados){
			addActionMessage("Histórico contratual associado ao início do vínculo do colaborador junto ao eSocial. Não é possível editar as informações integradas no Fortes RH, só podem ser alteradas no Fortes Pessoal.");
		}
	}
	
	public String update() throws Exception
	{
		try {
			boolean possuiPendencia = false;
			empresaEstaIntegradaEAderiuAoESocial = isEmpresaIntegradaEAderiuAoESocial();
			
			if(empresaEstaIntegradaEAderiuAoESocial)
				possuiPendencia = historicoColaboradorManager.existeHistoricoContratualComPendenciaNoESocial(getEmpresaSistema(), historicoColaborador.getColaborador().getCodigoAC());
		    		    
		    if(disabledCamposIntegrados || possuiPendencia){
		    	historicoColaboradorManager.updateAmbienteEFuncao(historicoColaborador);
		    }
		    else{
		    	quantidadeLimiteColaboradoresPorCargoManager.validaLimite(historicoColaborador.getAreaOrganizacional().getId(), historicoColaborador.getFaixaSalarial().getId(), getEmpresaSistema().getId(), historicoColaborador.getColaborador().getId());
				historicoColaborador = historicoColaboradorManager.ajustaAmbienteFuncao(historicoColaborador);
				historicoColaboradorManager.updateHistorico(historicoColaborador, getEmpresaSistema());
		    }
		    
		    if(possuiPendencia)
		    	addActionSuccess("Os dados integrados não podem ser editados pois no Fortes Pessoal existe pendência com eSocial. <br/> Dados editados: Função e Ambiente.");
		    	
			return Action.SUCCESS;
			
		} catch (IntegraACException e) {
			
			String msg = "Não foi possível atualizar esta Situação no Fortes Pessoal.";
			if (e.getMensagemDetalhada() != null)
				msg = e.getMensagemDetalhada();

			addActionError(msg);
			prepareUpdate();
			
			return Action.INPUT;
			
		}catch (ESocialException e) {
            addActionWarning(e.getMessage());
            setActionMsg(e.getMessage());
            return "eSocial";
		} catch (LimiteColaboradorExcedidoException e) {
			e.printStackTrace();
			addActionWarning(e.getMessage());
			prepareUpdate();

			return Action.INPUT;
			
		} catch (Exception e) {
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
			situacaoColaborador();
			colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, somenteAtivos, null, null, getEmpresaSistema().getId());

			if(colaborador.getId() != null)
			{
				colaboradorNome = colaboradorManager.getNome(colaborador.getId());
				historicoColaboradors = historicoColaboradorManager.getHistoricosComAmbienteEFuncao(colaborador.getId(), getEmpresaSistema().getId());
			}
		}

		return SUCCESS;
	}

	private void situacaoColaborador() 
	{
		if(SituacaoColaborador.ATIVO.equals(situacao))
			somenteAtivos = true;
		else if(SituacaoColaborador.DESLIGADO.equals(situacao))
			somenteAtivos = false;
	}

	public String updateAmbientesEFuncoes() throws Exception
	{
		try
		{
			historicoColaboradorManager.updateAmbientesEFuncoes(historicoColaboradors);

			prepareUpdateAmbientesEFuncoes();
			addActionSuccess("Ambientes e funções do colaborador gravados com sucesso.");
			return SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Ocorreu um erro ao gravar os ambientes e as funções do colaborador.");
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

	public Map<String, String> getCodigosGFIP()
	{
		return codigosGFIP;
	}

	public Collection<HistoricoColaborador> getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}

	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors)
	{
		this.historicoColaboradors = historicoColaboradors;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public String getColaboradorNome()
	{
		return colaboradorNome;
	}

	public void setCandidatoSolicitacaoId(Long candidatoSolicitacaoId)
	{
		this.candidatoSolicitacaoId = candidatoSolicitacaoId;
	}

	public void setCandidatoSolicitacaoManager(CandidatoSolicitacaoManager candidatoSolicitacaoManager)
	{
		this.candidatoSolicitacaoManager = candidatoSolicitacaoManager;
	}

	public Long getCandidatoSolicitacaoId()
	{
		return candidatoSolicitacaoId;
	}

	public boolean isIntegraAc()
	{
		return integraAc;
	}

	public Date getDataPrimeiroHist()
	{
		return dataPrimeiroHist;
	}

	public void setQuantidadeLimiteColaboradoresPorCargoManager(QuantidadeLimiteColaboradoresPorCargoManager quantidadeLimiteColaboradoresPorCargoManager)
	{
		this.quantidadeLimiteColaboradoresPorCargoManager = quantidadeLimiteColaboradoresPorCargoManager;
	}

	public boolean isObrigarAmbienteFuncao()
	{
		return obrigarAmbienteFuncao;
	}

	public void setEncerrarSolicitacao(String encerrarSolicitacao)
	{
		this.encerrarSolicitacao = encerrarSolicitacao;
	}

	public String getEncerrarSolicitacao()
	{
		return encerrarSolicitacao;
	}

	public void setSolicitacaoManager(SolicitacaoManager solicitacaoManager)
	{
		this.solicitacaoManager = solicitacaoManager;
	}


	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}


	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public Boolean getSomenteAtivos() {
		return somenteAtivos;
	}

	public void setSomenteAtivos(Boolean somenteAtivos) {
		this.somenteAtivos = somenteAtivos;
	}

	public Map getSituacaos() {
		return situacaos;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public boolean isEmpresaEstaIntegradaEAderiuAoESocial() {
		return empresaEstaIntegradaEAderiuAoESocial;
	}

	public boolean isDisabledCamposIntegrados() {
		return disabledCamposIntegrados;
	}

	public void setDisabledCamposIntegrados(boolean disabledCamposIntegrados) {
		this.disabledCamposIntegrados = disabledCamposIntegrados;
	}
}