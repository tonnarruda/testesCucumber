package com.fortes.rh.web.action.cargosalario;

import static com.fortes.rh.util.CheckListBoxUtil.populaCheckListBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.GrupoOcupacionalManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.cargosalario.ReajusteColaboradorManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.GrupoOcupacional;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.cargosalario.ReajusteColaborador;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

public class ReajusteColaboradorEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;
	
	private ReajusteColaboradorManager reajusteColaboradorManager;
	private FaixaSalarialManager faixaSalarialManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private FuncaoManager funcaoManager;
	private AmbienteManager ambienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private GrupoOcupacionalManager grupoOcupacionalManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private IndiceManager indiceManager;

	private ReajusteColaborador reajusteColaborador;
	private TabelaReajusteColaborador tabelaReajusteColaborador;

	// TODO Verificar a necessidade da variável faixasPropostas(ftls e etc.)
	private Collection<FaixaSalarial> faixasPropostas = new ArrayList<FaixaSalarial>();

	private Collection<Colaborador> colaboradores = new ArrayList<Colaborador>();
	private Collection<FaixaSalarial> faixaSalarials = new ArrayList<FaixaSalarial>();
	private Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();
	private Collection<Ambiente> ambientes = new ArrayList<Ambiente>();
	private Collection<Funcao> funcaos = new ArrayList<Funcao>();
	private Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();

	private Estabelecimento estabelecimentoAtual;
	private Estabelecimento estabelecimentoProposto;
	private AreaOrganizacional areaOrganizacionalAtual;
	private AreaOrganizacional areaOrganizacionalProposta;
	private FaixaSalarial faixaSalarialAtual;
	private FaixaSalarial faixaSalarialProposta;
	private double salarioAtual;
	private double salarioProposto;
	private Funcao funcaoAtual;
	private Funcao funcaoProposta;
	private Ambiente ambienteAtual;
	private Ambiente ambienteProposto;
	private Cargo cargoAtual;
	private Colaborador colaborador;

	private AreaOrganizacional areaOrganizacionalFiltro;
	private GrupoOcupacional grupoOcupacionalFiltro;

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> gruposCheckList = new ArrayList<CheckBox>();
	private String[] gruposCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;

	private char dissidioPor;
	private char filtrarPor = '1';
	private Double valorDissidio;

	private boolean exibeSalario;
	private boolean obrigarAmbienteFuncao;
	private Map<Object, Object> tiposSalarios = new TipoAplicacaoIndice();
	private TipoAplicacaoIndice tipoSalario = new TipoAplicacaoIndice();
	private Collection<Indice> indices = new ArrayList<Indice>();

	private Double valor;
	private Double salarioCalculado;

	private String descricaoTipoSalario;

	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();

	private void prepare() throws Exception
	{
		obrigarAmbienteFuncao = getEmpresaSistema().isObrigarAmbienteFuncao();
		
		if(reajusteColaborador != null && reajusteColaborador.getId() != null)
		{
			reajusteColaborador = (ReajusteColaborador) reajusteColaboradorManager.getSituacaoReajusteColaborador(reajusteColaborador.getId());
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String prepareUpdate() throws Exception
	{
		prepare();

		indices = indiceManager.findAll(getEmpresaSistema());

		CollectionUtil<AreaOrganizacional> areasOrganizacionaisUtil = new CollectionUtil<AreaOrganizacional>();

		Map session = ActionContext.getContext().getSession();
		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(session);

		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());

		//Se tem papel especifico, carrega todas as áreas organizacionais
		if(SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_SOLICITACAO_REALINHAMENTO"}))
		{
			areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.ATIVA, Arrays.asList(reajusteColaborador.getAreaOrganizacionalAtual().getId(), reajusteColaborador.getAreaOrganizacionalProposta().getId()), getEmpresaSistema().getId());
		}
		else
		{
			Colaborador colaborador = colaboradorManager.findByUsuario(usuarioLogado, getEmpresaSistema().getId());
			if(colaborador != null && colaborador.getId() != null)
				areaOrganizacionals = areaOrganizacionalManager.findAllList(colaborador.getId(), getEmpresaSistema().getId(), AreaOrganizacional.ATIVA, reajusteColaborador.getAreaOrganizacionalProposta().getId());
		}

		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		areaOrganizacionals = areasOrganizacionaisUtil.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");

		reajusteColaborador.setAreaOrganizacionalAtual(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, reajusteColaborador.getAreaOrganizacionalAtual().getId()));

		Funcao funcaoVazio = new Funcao();
		funcaoVazio.setId(-1L);
		funcaoVazio.setNome("Nenhuma");

		funcaos.add(funcaoVazio);

		if(reajusteColaborador.getFaixaSalarialProposta() != null && reajusteColaborador.getFaixaSalarialProposta().getId() != null)
			funcaos = funcaoManager.findFuncaoByFaixa(reajusteColaborador.getFaixaSalarialProposta().getId());

		if(reajusteColaborador.getEstabelecimentoProposto() != null && reajusteColaborador.getEstabelecimentoProposto().getId() != null)
			ambientes = ambienteManager.findByEstabelecimento(reajusteColaborador.getEstabelecimentoProposto().getId());

		if(reajusteColaborador.getAmbienteAtual().getNome() == null)
			reajusteColaborador.getAmbienteAtual().setNome("Não possui");

		if(reajusteColaborador.getFuncaoAtual().getNome() == null)
			reajusteColaborador.getFuncaoAtual().setNome("Não possui");

		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();
		faixaSalarials = faixaSalarialUtil.sortCollectionStringIgnoreCase(faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO, reajusteColaborador.getFaixaSalarialProposta().getId()), "cargo.nome");

		salarioCalculado = reajusteColaboradorManager.calculaSalarioProposto(reajusteColaborador);

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		reajusteColaboradorManager.save(reajusteColaborador);

		return Action.SUCCESS;
	}

	public String insertSolicitacaoReajuste() throws Exception
	{
		try
		{
			tabelaReajusteColaborador = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());
			colaborador = colaboradorManager.findByIdDadosBasicos(colaborador.getId(), null);
			
			if(!tabelaReajusteColaborador.getData().after(colaborador.getDataAdmissao()))
			{
				prepareSolicitacaoReajuste();
				throw new Exception("Não Foi possível gravar o realinhamento, pois a data da solicitação de realinhamento ("+ 
				         DateUtil.formataDiaMesAno(tabelaReajusteColaborador.getData()) +") deve ser maior que a data de admissão" +
				         "do colaborador ("+ colaborador.getDataAdmissaoFormatada() +"). ");
			}
			
			obrigarAmbienteFuncao = getEmpresaSistema().isObrigarAmbienteFuncao();
			
			setDadosSolicitacaoReajuste();
			reajusteColaboradorManager.validaSolicitacaoReajuste(reajusteColaborador);
			
			reajusteColaboradorManager.insertSolicitacaoReajuste(reajusteColaborador, getEmpresaSistema().getId(), colaborador);
			
			addActionSuccess("Solicitação de realinhamento incluída com sucesso");
		} 
		catch(FortesException fe)
		{
			addActionWarning(fe.getMessage());
		}
		catch(Exception e)
		{
			addActionError(e.getMessage());
		}
		
		return prepareSolicitacaoReajuste();
	}
	
	private void setDadosSolicitacaoReajuste() 
	{
		if(reajusteColaborador.getIndiceAtual() == null || reajusteColaborador.getIndiceAtual().getId() == null)
			reajusteColaborador.setIndiceAtual(null);
		
		if (reajusteColaborador.getFuncaoAtual() != null && reajusteColaborador.getFuncaoAtual().getId() == null)
			reajusteColaborador.setFuncaoAtual(null);
		
		if (reajusteColaborador.getAmbienteAtual() != null && reajusteColaborador.getAmbienteAtual().getId() == null)
			reajusteColaborador.setAmbienteAtual(null);
		
		if (reajusteColaborador.getFuncaoProposta() != null && (reajusteColaborador.getFuncaoProposta().getId() == null || reajusteColaborador.getFuncaoProposta().getId() == -1))
			reajusteColaborador.setFuncaoProposta(null);
		
		if(reajusteColaborador.getAmbienteProposto() != null && (reajusteColaborador.getAmbienteProposto().getId() == null || reajusteColaborador.getAmbienteProposto().getId() == -1))
			reajusteColaborador.setAmbienteProposto(null);
		
		reajusteColaborador.setColaborador(colaborador);
		reajusteColaborador.setTabelaReajusteColaborador(tabelaReajusteColaborador);
		
		// Situacao atual
//		reajusteColaborador.setFuncaoAtual(funcaoAtual);
//		reajusteColaborador.setAmbienteAtual(ambienteAtual);
		
		// Situacao proposta
//		reajusteColaborador.setFuncaoProposta(funcaoProposta);
//		reajusteColaborador.setAmbienteProposto(ambienteProposto);
	}

	// Está sendo usado em ftl, não apague.
	public Object getModel()
	{
		return getReajusteColaborador();
	}

	public String update() throws Exception
	{
		if(areaOrganizacionalManager.verificaMaternidade(reajusteColaborador.getAreaOrganizacionalProposta().getId(), null))
		{
			addActionWarning("Não é possível fazer solicitações para áreas que possuem sub-áreas.");
			prepareUpdate();
			return Action.INPUT;
		}

		reajusteColaboradorManager.updateReajusteColaborador(reajusteColaborador);
		
		tabelaReajusteColaborador = reajusteColaborador.getTabelaReajusteColaborador();

		return Action.SUCCESS;
	}

	public ReajusteColaborador getReajusteColaborador()
	{
		if (reajusteColaborador == null)
		{
			reajusteColaborador = new ReajusteColaborador();
		}
		return reajusteColaborador;
	}

	public String prepareSolicitacaoReajuste() throws Exception
	{
		exibeSalario = getEmpresaSistema().isExibirSalario();
		obrigarAmbienteFuncao = getEmpresaSistema().isObrigarAmbienteFuncao();
		
		indices = indiceManager.findAll(getEmpresaSistema());

		Usuario usuarioLogado = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());
		Long empresaId = getEmpresaSistema().getId();

		//Se for o super-usuário ou papel especifico, carrega todas as áreas organizacionais
		if(usuarioLogado.getId() == 1L || SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_MOV_SOLICITACAO_REALINHAMENTO"}))
			areaOrganizacionals = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);
		else
			areaOrganizacionals = areaOrganizacionalManager.findAreasByUsuarioResponsavel(usuarioLogado, empresaId);

		CollectionUtil<AreaOrganizacional> areaOrganizacionalsUtil = new CollectionUtil<AreaOrganizacional>();

		areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
		areaOrganizacionalsUtil = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = areaOrganizacionalsUtil.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");

		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(empresaId, TipoReajuste.COLABORADOR);

		estabelecimentos = estabelecimentoManager.findAllSelect(empresaId);

		CollectionUtil<FaixaSalarial> faixaSalarialUtil = new CollectionUtil<FaixaSalarial>();
		faixaSalarials = faixaSalarialUtil.sortCollectionStringIgnoreCase(faixaSalarialManager.findFaixas(getEmpresaSistema(), Cargo.ATIVO, null), "cargo.nome");

		return Action.SUCCESS;
	}

	public String prepareDissidio() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");

		gruposCheckList = populaCheckListBox(grupoOcupacionalManager.findAllSelect(getEmpresaSistema().getId()), "getId", "getNome");

		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.COLABORADOR);

		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		gruposCheckList = CheckListBoxUtil.marcaCheckListBox(gruposCheckList, gruposCheck);

		return Action.SUCCESS;
	}

	public String aplicarDissidio() throws Exception
	{
		try
		{
			TabelaReajusteColaborador tabelaReajusteColaboradorTmp = tabelaReajusteColaboradorManager.findByIdProjection(tabelaReajusteColaborador.getId());
			Collection<HistoricoColaborador> historicoColaboradores = historicoColaboradorManager.getHistoricosAtuaisByEstabelecimentoAreaGrupo(StringUtil.stringToLong(estabelecimentosCheck), filtrarPor, StringUtil.stringToLong(areasCheck) , StringUtil.stringToLong(gruposCheck), getEmpresaSistema().getId(), tabelaReajusteColaboradorTmp.getData());
			if(historicoColaboradores.isEmpty())
			{
				addActionMessage("Não existe colaborador para o filtro informado.");
			}
			else
			{
				reajusteColaboradorManager.aplicarDissidio(historicoColaboradores, tabelaReajusteColaborador, dissidioPor, valorDissidio);
				addActionSuccess("Reajuste de realinhamento inserido com sucesso.");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível inserir o reajuste de realinhamento");
		}

		prepareDissidio();

		return Action.SUCCESS;
	}

	public void setReajusteColaborador(ReajusteColaborador reajusteColaborador)
	{
		this.reajusteColaborador = reajusteColaborador;
	}

	public void setReajusteColaboradorManager(ReajusteColaboradorManager reajusteColaboradorManager)
	{
		this.reajusteColaboradorManager = reajusteColaboradorManager;
	}

	public Collection<FaixaSalarial> getFaixasPropostas()
	{
		return faixasPropostas;
	}

	public void setFaixasPropostas(Collection<FaixaSalarial> faixasPropostas)
	{
		this.faixasPropostas = faixasPropostas;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador()
	{
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador)
	{
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public Collection<Colaborador> getColaboradores()
	{
		return colaboradores;
	}

	public void setColaboradores(Collection<Colaborador> colaboradores)
	{
		this.colaboradores = colaboradores;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public void setAreaOrganizacionals(Collection<AreaOrganizacional> areaOrganizacionals)
	{
		this.areaOrganizacionals = areaOrganizacionals;
	}

	public AreaOrganizacional getAreaOrganizacionalProposta()
	{
		return areaOrganizacionalProposta;
	}

	public void setAreaOrganizacionalProposta(AreaOrganizacional areaOrganizacionalProposta)
	{
		this.areaOrganizacionalProposta = areaOrganizacionalProposta;
	}

	public AreaOrganizacional getAreaOrganizacionalAtual()
	{
		return areaOrganizacionalAtual;
	}

	public void setAreaOrganizacionalAtual(AreaOrganizacional areaOrganizacionalAtual)
	{
		this.areaOrganizacionalAtual = areaOrganizacionalAtual;
	}

	public Cargo getCargoAtual()
	{
		return cargoAtual;
	}

	public void setCargoAtual(Cargo cargoAtual)
	{
		this.cargoAtual = cargoAtual;
	}

	public double getSalarioAtual()
	{
		return salarioAtual;
	}

	public void setSalarioAtual(double salarioAtual)
	{
		this.salarioAtual = salarioAtual;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public double getSalarioProposto()
	{
		return salarioProposto;
	}

	public void setSalarioProposto(double salarioProposto)
	{
		this.salarioProposto = salarioProposto;
	}

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors()
	{
		return tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradors(Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors)
	{
		this.tabelaReajusteColaboradors = tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager)
	{
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}

	public AreaOrganizacional getAreaOrganizacionalFiltro()
	{
		return areaOrganizacionalFiltro;
	}

	public void setAreaOrganizacionalFiltro(AreaOrganizacional areaOrganizacionalFiltro)
	{
		this.areaOrganizacionalFiltro = areaOrganizacionalFiltro;
	}

	public GrupoOcupacional getGrupoOcupacionalFiltro()
	{
		return grupoOcupacionalFiltro;
	}

	public void setGrupoOcupacionalFiltro(GrupoOcupacional grupoOcupacionalFiltro)
	{
		this.grupoOcupacionalFiltro = grupoOcupacionalFiltro;
	}

	public Ambiente getAmbienteAtual()
	{
		return ambienteAtual;
	}

	public void setAmbienteAtual(Ambiente ambienteAtual)
	{
		this.ambienteAtual = ambienteAtual;
	}

	public Ambiente getAmbienteProposto()
	{
		return ambienteProposto;
	}

	public void setAmbienteProposto(Ambiente ambienteProposto)
	{
		this.ambienteProposto = ambienteProposto;
	}

	public Funcao getFuncaoAtual()
	{
		return funcaoAtual;
	}

	public void setFuncaoAtual(Funcao funcaoAtual)
	{
		this.funcaoAtual = funcaoAtual;
	}

	public Funcao getFuncaoProposta()
	{
		return funcaoProposta;
	}

	public void setFuncaoProposta(Funcao funcaoProposta)
	{
		this.funcaoProposta = funcaoProposta;
	}

	public Collection<Ambiente> getAmbientes()
	{
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes)
	{
		this.ambientes = ambientes;
	}

	public Collection<Funcao> getFuncaos()
	{
		return funcaos;
	}

	public void setFuncaos(Collection<Funcao> funcaos)
	{
		this.funcaos = funcaos;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager)
	{
		this.funcaoManager = funcaoManager;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager)
	{
		this.ambienteManager = ambienteManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Estabelecimento getEstabelecimentoAtual()
	{
		return estabelecimentoAtual;
	}

	public void setEstabelecimentoAtual(Estabelecimento estabelecimentoAtual)
	{
		this.estabelecimentoAtual = estabelecimentoAtual;
	}

	public Estabelecimento getEstabelecimentoProposto()
	{
		return estabelecimentoProposto;
	}

	public void setEstabelecimentoProposto(Estabelecimento estabelecimentoProposto)
	{
		this.estabelecimentoProposto = estabelecimentoProposto;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public String[] getAreasCheck()
	{
		return areasCheck;
	}

	public void setAreasCheck(String[] areasCheck)
	{
		this.areasCheck = areasCheck;
	}

	public String[] getEstabelecimentosCheck()
	{
		return estabelecimentosCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck)
	{
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
	}

	public String[] getGruposCheck()
	{
		return gruposCheck;
	}

	public void setGruposCheck(String[] gruposCheck)
	{
		this.gruposCheck = gruposCheck;
	}

	public Collection<CheckBox> getGruposCheckList()
	{
		return gruposCheckList;
	}

	public void setGrupoOcupacionalManager(GrupoOcupacionalManager grupoOcupacionalManager)
	{
		this.grupoOcupacionalManager = grupoOcupacionalManager;
	}

	public char getDissidioPor()
	{
		return dissidioPor;
	}

	public void setDissidioPor(char dissidioPor)
	{
		this.dissidioPor = dissidioPor;
	}

	public Double getValorDissidio()
	{
		return valorDissidio;
	}

	public void setValorDissidio(Double valorDissidio)
	{
		this.valorDissidio = valorDissidio;
	}

	public char getFiltrarPor()
	{
		return filtrarPor;
	}

	public void setFiltrarPor(char filtrarPor)
	{
		this.filtrarPor = filtrarPor;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public boolean isExibeSalario()
	{
		return exibeSalario;
	}

	public TipoAplicacaoIndice getTipoSalario()
	{
		return tipoSalario;
	}

	public void setTipoSalario(TipoAplicacaoIndice tipoSalario)
	{
		this.tipoSalario = tipoSalario;
	}

	public Map<Object, Object> getTiposSalarios()
	{
		return tiposSalarios;
	}

	public void setTiposSalarios(Map<Object, Object> tiposSalarios)
	{
		this.tiposSalarios = tiposSalarios;
	}

	public IndiceManager getIndiceManager()
	{
		return indiceManager;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public Collection<Indice> getIndices()
	{
		return indices;
	}

	public void setIndices(Collection<Indice> indices)
	{
		this.indices = indices;
	}

	public FaixaSalarial getFaixaSalarialAtual()
	{
		return faixaSalarialAtual;
	}

	public void setFaixaSalarialAtual(FaixaSalarial faixaSalarialAtual)
	{
		this.faixaSalarialAtual = faixaSalarialAtual;
	}

	public void setFaixaSalarialProposta(FaixaSalarial faixaSalarialProposta)
	{
		this.faixaSalarialProposta = faixaSalarialProposta;
	}

	public FaixaSalarial getFaixaSalarialProposta()
	{
		return faixaSalarialProposta;
	}

	public Collection<FaixaSalarial> getFaixaSalarials()
	{
		return faixaSalarials;
	}

	public void setFaixaSalarials(Collection<FaixaSalarial> faixaSalarials)
	{
		this.faixaSalarials = faixaSalarials;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public String getDescricaoTipoSalario()
	{
		return descricaoTipoSalario;
	}

	public void setDescricaoTipoSalario(String descricaoTipoSalario)
	{
		this.descricaoTipoSalario = descricaoTipoSalario;
	}

	public Double getValor()
	{
		return valor;
	}

	public void setValor(Double valor)
	{
		this.valor = valor;
	}

	public Double getSalarioCalculado()
	{
		return salarioCalculado;
	}

	public TipoAplicacaoIndice getTipoAplicacaoIndice()
	{
		return tipoAplicacaoIndice;
	}

	public void setTipoAplicacaoIndice(TipoAplicacaoIndice tipoAplicacaoIndice)
	{
		this.tipoAplicacaoIndice = tipoAplicacaoIndice;
	}

	public boolean isObrigarAmbienteFuncao() {
		return obrigarAmbienteFuncao;
	}
}