package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanComparator;
import org.springframework.core.NestedRuntimeException;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.relatorio.RelatorioPromocoes;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.relatorio.DataGrafico;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.ComparatorString;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

public class HistoricoColaboradorListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private HistoricoColaboradorManager historicoColaboradorManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private EstabelecimentoManager estabelecimentoManager;
	private ColaboradorManager colaboradorManager;
	private EmpresaManager empresaManager;

	private Collection<HistoricoColaborador> historicoColaboradors;

	private HistoricoColaborador historicoColaborador;
	private Colaborador colaborador;

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] areasCheck;
	private Date dataBase;
	private Date dataIni;
	private Date dataFim;
	
	private String origemSituacao = "T";
	
	private Collection<RelatorioPromocoes> dataSource = new ArrayList<RelatorioPromocoes>();
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private StatusRetornoAC statusRetornoAC = new StatusRetornoAC();
	private boolean integradoAC;

	private Collection<Empresa> empresas;
	private Long[] empresaIds;//repassado para o DWR
	private Empresa empresa;
	private String grfFormacaoEscolars = "";
	private String grfFaixaEtarias = "";
	private String grfSexo = "";
	private String grfEstadoCivil = "";
	private String grfDeficiencia = "";
	private String grfDesligamento = "";

	private String grfColocacao = "";
	
	public String painelIndicadores() throws Exception
	{
		Date hoje = new Date();
		if (dataBase == null)
			dataBase = hoje;
		if (dataFim == null)
			dataFim = hoje;
		if (dataIni == null)
			dataIni = DateUtil.retornaDataAnteriorQtdMeses(hoje, 12, true);
			
		Collection<DataGrafico> graficoformacaoEscolars = colaboradorManager.countFormacaoEscolar(dataBase, getEmpresaSistema().getId());
		Collection<DataGrafico> graficofaixaEtaria = colaboradorManager.countFaixaEtaria(dataBase, getEmpresaSistema().getId());
		Collection<DataGrafico> graficoSexo = colaboradorManager.countSexo(dataBase, getEmpresaSistema().getId());
		Collection<DataGrafico> graficoEstadoCivil = colaboradorManager.countEstadoCivil(dataBase, getEmpresaSistema().getId());
		Collection<DataGrafico> graficoDeficiencia = colaboradorManager.countDeficiencia(dataBase, getEmpresaSistema().getId());
		Collection<DataGrafico> graficoDesligamento = colaboradorManager.countMotivoDesligamento(dataIni, dataFim, getEmpresaSistema().getId());
		Collection<DataGrafico> graficoColocacao = colaboradorManager.countColocacao(dataBase, getEmpresaSistema().getId());

		grfFormacaoEscolars = StringUtil.toJSON(graficoformacaoEscolars, null);
		grfFaixaEtarias = StringUtil.toJSON(graficofaixaEtaria, null);
		grfSexo = StringUtil.toJSON(graficoSexo, null);
		grfEstadoCivil = StringUtil.toJSON(graficoEstadoCivil, null);
		grfDeficiencia = StringUtil.toJSON(graficoDeficiencia, null);
		grfDesligamento = StringUtil.toJSON(graficoDesligamento, null);
		grfColocacao  = StringUtil.toJSON(graficoColocacao, null);
		
		return Action.SUCCESS;
	}

	public String prepareRelatorioPromocoes() throws Exception
	{
		empresas = empresaManager.findByUsuarioPermissao(SecurityUtil.getIdUsuarioLoged(ActionContext.getContext().getSession()), "ROLE_REL_PROMOCAO");
		CollectionUtil<Empresa> clu = new CollectionUtil<Empresa>();
		empresaIds = clu.convertCollectionToArrayIds(empresas);
		
		empresa = getEmpresaSistema();

		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String imprimirRelatorioPromocoes() throws Exception
	{
		Collection<RelatorioPromocoes> promocoes = historicoColaboradorManager.getPromocoes(LongUtil.arrayStringToArrayLong(areasCheck), LongUtil.arrayStringToArrayLong(estabelecimentosCheck), dataIni, dataFim);

		if (promocoes.isEmpty())
		{
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			addActionError(bundle.getString("error.relatorio.vazio"));
			setActionMsg(bundle.getString("error.relatorio.vazio"));

			prepareRelatorioPromocoes();
			return Action.INPUT;
		}

		try
		{
			Comparator comp = new BeanComparator("estabelecimento.nome", new ComparatorString());
			Collections.sort((List) promocoes, comp);
			setDataSource(promocoes);

			String filtro = "Período: " + DateUtil.formataDiaMesAno(dataIni) + " à " + DateUtil.formataDiaMesAno(dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio("Indicador de Promoções", getEmpresaSistema(), filtro);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			prepareRelatorioPromocoes();
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		if (colaborador != null && colaborador.getId() != null)
		{
			colaborador = colaboradorManager.findColaboradorById(colaborador.getId());

			Collection<AreaOrganizacional> areaOrganizacionals = areaOrganizacionalManager.findAllList(getEmpresaSistema().getId(), AreaOrganizacional.TODAS);
			areaOrganizacionals = areaOrganizacionalManager.montaFamilia(areaOrganizacionals);
			historicoColaborador = historicoColaboradorManager.getHistoricoAtualOuFuturo(colaborador.getId());
			historicoColaborador.setAreaOrganizacional(areaOrganizacionalManager.getAreaOrganizacional(areaOrganizacionals, historicoColaborador.getAreaOrganizacional().getId()));

			historicoColaboradors = historicoColaboradorManager.progressaoColaborador(colaborador.getId(), getEmpresaSistema().getId());

			integradoAC = getEmpresaSistema().isAcIntegra();
			if(colaborador.isNaoIntegraAc())
				integradoAC = false;

			return Action.SUCCESS;
		}
		else
		{
			addActionError("Colaborador não selecionado");
			return Action.INPUT;
		}
	}

	public String historicoColaboradorList() throws Exception
	{
		colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());

		integradoAC = getEmpresaSistema().isAcIntegra();
		if(colaborador.isNaoIntegraAc())
			integradoAC = false;

		historicoColaboradors = historicoColaboradorManager.findByColaborador(colaborador.getId(), getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			historicoColaboradorManager.removeHistoricoAndReajuste(historicoColaborador.getId(), colaborador.getId(), getEmpresaSistema());
			addActionMessage("Situação excluída com sucesso.");
		}
		catch (NestedRuntimeException e) // TODO rever necessidade desse catch
		{
			addActionError("Não foi possível excluir a Situação.");
		}
		catch (IntegraACException e)
		{
			if (e.getMensagemDetalhada() != null)
				addActionError(e.getMensagemDetalhada());
			else
				addActionError(e.getMessage());
		}
		catch (Exception e)
		{
			addActionMessage(e.getMessage());
		}

		return historicoColaboradorList();
	}
	
	public String prepareRelatorioSituacoes()
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		return Action.SUCCESS;
	}
	
	public String relatorioSituacoes()
	{
		try 
		{
			historicoColaboradors = historicoColaboradorManager.montaRelatorioSituacoes(getEmpresaSistema().getId(), dataIni, dataFim, LongUtil.arrayStringToArrayLong(estabelecimentosCheck), LongUtil.arrayStringToArrayLong(areasCheck), origemSituacao);
			
			String filtro = "Período : " + DateUtil.formataDiaMesAno(dataIni) + " a " + DateUtil.formataDiaMesAno(dataFim);
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Situações", getEmpresaSistema(), filtro);
			
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorioSituacoes();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			return Action.INPUT;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar relatório");
			e.printStackTrace();
			prepareRelatorioSituacoes();

			return Action.INPUT;
		}
		
		return Action.SUCCESS;
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

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public void setHistoricoColaboradors(Collection<HistoricoColaborador> historicoColaboradors)
	{
		this.historicoColaboradors = historicoColaboradors;
	}

	public void setHistoricoColaborador(HistoricoColaborador historicoColaborador)
	{
		this.historicoColaborador = historicoColaborador;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection getHistoricoColaboradors()
	{
		return historicoColaboradors;
	}

	public HistoricoColaborador getHistoricoColaborador()
	{
		if (historicoColaborador == null)
		{
			historicoColaborador = new HistoricoColaborador();
		}
		return historicoColaborador;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public Map getParametros()
	{
		return parametros;
	}

	public void setDataSource(Collection<RelatorioPromocoes> dataSource)
	{
		this.dataSource = dataSource;
	}

	public Collection<RelatorioPromocoes> getDataSource()
	{
		return dataSource;
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

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public StatusRetornoAC getStatusRetornoAC()
	{
		return statusRetornoAC;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public String getOrigemSituacao() {
		return origemSituacao;
	}

	public void setOrigemSituacao(String origemSituacao) {
		this.origemSituacao = origemSituacao;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public Long[] getEmpresaIds()
	{
		return empresaIds;
	}

	public Empresa getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Empresa empresa)
	{
		this.empresa = empresa;
	}

	public Collection<Empresa> getEmpresas()
	{
		return empresas;
	}

	public String getGrfFormacaoEscolars() {
		return grfFormacaoEscolars;
	}

	public String getGrfFaixaEtarias() {
		return grfFaixaEtarias;
	}

	public String getGrfSexo() {
		return grfSexo;
	}

	public String getGrfEstadoCivil() {
		return grfEstadoCivil;
	}

	public String getGrfDeficiencia() {
		return grfDeficiencia;
	}

	public String getGrfDesligamento() {
		return grfDesligamento;
	}

	public Date getDataBase() {
		return dataBase;
	}

	public void setDataBase(Date dataBase) {
		this.dataBase = dataBase;
	}

	public String getGrfColocacao() {
		return grfColocacao;
	}	
}