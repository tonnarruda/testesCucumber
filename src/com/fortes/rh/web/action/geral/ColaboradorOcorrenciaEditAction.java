package com.fortes.rh.web.action.geral;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.OcorrenciaManager;
import com.fortes.rh.business.geral.ProvidenciaManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.model.geral.relatorio.AbsenteismoCollection;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;

@SuppressWarnings({"serial"})
public class ColaboradorOcorrenciaEditAction extends MyActionSupportList
{
	private ColaboradorOcorrenciaManager colaboradorOcorrenciaManager;
	private ColaboradorManager colaboradorManager;
	private OcorrenciaManager ocorrenciaManager;
	private AfastamentoManager afastamentoManager;
	private HistoricoColaboradorManager historicoColaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private ProvidenciaManager providenciaManager;

	private ColaboradorOcorrencia colaboradorOcorrencia;
	private Colaborador colaborador;
	private HistoricoColaborador historicoColab;
	private Collection<Ocorrencia> ocorrencias;
	private Ocorrencia ocorrencia;
	private Collection<Providencia> providencias;
	private Providencia providencia;

	private Collection<ColaboradorOcorrencia> colaboradorOcorrencias;
	private Collection<Colaborador> colaboradors;
	
	private String[] areasCheck;
	private String[] estabelecimentosCheck;
	private String[] ocorrenciasCheck;
	private String[] afastamentosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> ocorrenciasCheckList = new ArrayList<CheckBox>();
	private Collection<CheckBox> afastamentosCheckList = new ArrayList<CheckBox>();
	
	private Map<String, Object> parametros = new HashMap<String, Object>();
	private Collection<AbsenteismoCollection> dataSource = new ArrayList<AbsenteismoCollection>();
	private String dataDe;
	private String dataAte;
	private boolean somenteDesligados;

	public String prepare() throws Exception
	{
		if(colaboradorOcorrencia != null && colaboradorOcorrencia.getId() != null)
			colaboradorOcorrencia = (ColaboradorOcorrencia) colaboradorOcorrenciaManager.findById(colaboradorOcorrencia.getId());
		
		if (colaboradorOcorrencia != null && colaboradorOcorrencia.getColaborador() != null)
			colaborador = colaboradorOcorrencia.getColaborador();
		
		if (colaborador != null && colaborador.getId() != null && colaborador.getNome() == null)
			colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());

		ocorrencias = ocorrenciaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao asc"});
		providencias = providenciaManager.find(new String[]{"empresa.id"}, new Object[]{getEmpresaSistema().getId()}, new String[]{"descricao"});

		return SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		if (! colaboradorOcorrencia.getColaborador().getEmpresa().getId().equals(getEmpresaSistema().getId()))
		{
			addActionWarning("A ocorrência solicitada não existe na empresa " + getEmpresaSistema().getNome() + ".");

			list();

			return INPUT;
		}
		
		return SUCCESS;
	}

	public String insertOrUpdate() throws Exception
	{
		String msg = null;
		try
		{
			HistoricoColaborador primeiroHistorico = historicoColaboradorManager.getPrimeiroHistorico(colaborador.getId());
			if(primeiroHistorico.getData().compareTo(colaboradorOcorrencia.getDataIni()) == 1)
			{
				msg = "Não é permitido inserir ocorrência antes da data da primeira situação do colaborador: (" + DateUtil.formataDiaMesAno(primeiroHistorico.getData()) + ")";
				throw new Exception();
			}
			
			colaboradorOcorrencia.setColaborador(colaborador);
			//boolean jaExisteOcorrenciaNoMesmoDia = colaboradorOcorrenciaManager.verifyExistsMesmaData(colaboradorOcorrencia.getId(), colaboradorOcorrencia.getColaborador().getId(), colaboradorOcorrencia.getOcorrencia().getId(), getEmpresaSistema().getId(), colaboradorOcorrencia.getDataIni(), null);
			Collection<ColaboradorOcorrencia> ocorrenciasNaMesmaData = colaboradorOcorrenciaManager.verifyOcorrenciasMesmaData(colaboradorOcorrencia.getId(), colaboradorOcorrencia.getColaborador().getId(), colaboradorOcorrencia.getOcorrencia().getId(), getEmpresaSistema().getId(), colaboradorOcorrencia.getDataIni(), colaboradorOcorrencia.getDataFim());

			if (ocorrenciasNaMesmaData.size() > 0)
			{
				msg = "A ocorrência não pôde ser gravada com as datas informadas. <br/>"
					+ "A mesma ocorrência já foi cadastrada para esse colaborador nas seguintes datas: <br/>";
				for (ColaboradorOcorrencia colaboradorOcorrencia : ocorrenciasNaMesmaData) {
					msg += " - " + colaboradorOcorrencia.getDataIniString();
					if ( colaboradorOcorrencia.getDataFim() != null)
						msg += " a " + colaboradorOcorrencia.getDataFimString();
					msg += "<br/>";
				}
				throw new Exception();
			}

			ocorrencia = ocorrenciaManager.findById(colaboradorOcorrencia.getOcorrencia().getId());
			colaboradorOcorrencia.setOcorrencia(ocorrencia);//utilizado dentro dos metodos seguintes
			
			if(colaboradorOcorrencia.getProvidencia().getId() == null)
				colaboradorOcorrencia.setProvidencia(null);
			
			colaboradorOcorrenciaManager.saveColaboradorOcorrencia(colaboradorOcorrencia, getEmpresaSistema());
			
			list();

			return SUCCESS;
		}
		catch (IntegraACException e)
		{
			e.printStackTrace();
			addActionError("Cadastro não pôde ser realizado no Fortes Pessoal.");

			prepare();

			return INPUT;
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();

			if (e.getTargetException() != null && e.getTargetException() instanceof IntegraACException)
				addActionError("Cadastro não pôde ser realizado no Fortes Pessoal.");
			else
				addActionError("Cadastro não pôde ser realizado.");
			
			prepare();
			
			return INPUT;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(msg == null)
				addActionError("Cadastro não pôde ser realizado.");
			else
				addActionWarning(msg);

			prepare();

			return INPUT;
		}
	}

	public String list() throws Exception
	{
		if (colaborador != null)
		{
			colaboradors = colaboradorOcorrenciaManager.findColaboraesPermitidosByUsuario(SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession()), colaborador, getEmpresaSistema().getId(), SecurityUtil.verifyRole(ActionContext.getContext().getSession(), new String[]{"ROLE_VER_AREAS"}), somenteDesligados);
			
			if (colaborador.getId() != null)
			{
				colaborador = colaboradorManager.findColaboradorByIdProjection(colaborador.getId());
				setTotalSize(colaboradorOcorrenciaManager.getCount(new String[]{"colaborador.id"}, new Object[]{colaborador.getId()}));
				colaboradorOcorrencias = colaboradorOcorrenciaManager.findProjection(getPage(), getPagingSize(), colaborador.getId());
			}
		}
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			colaboradorOcorrenciaManager.remove(colaboradorOcorrencia, getEmpresaSistema());
			addActionSuccess("Ocorrência do colaborador removida com sucesso.");
		}
		catch (IntegraACException ie)
		{
			addActionError("A ocorrência deste colaborador não pôde ser removida no Fortes Pessoal.");
			ie.printStackTrace();
		}
		catch (Exception e)
		{
			addActionError("A ocorrência deste colaborador não pôde ser removida.");
			e.printStackTrace();
		}

		return list();
	}

	public ColaboradorOcorrencia getColaboradorOcorrencia()
	{
		if(colaboradorOcorrencia == null)
			colaboradorOcorrencia = new ColaboradorOcorrencia();
		return colaboradorOcorrencia;
	}
	
	
	public String prepareRelatorioAbsenteismo() throws Exception
	{
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		
		Collection<Ocorrencia> ocorrencia = ocorrenciaManager.findOcorrenciasComAbseteismo(getEmpresaSistema().getId());
		ocorrenciasCheckList = CheckListBoxUtil.populaCheckListBox(ocorrencia, "getId", "getDescricao", null);		
		
		Collection<Afastamento> afastamentos = afastamentoManager.findToList(new String[]{"id", "descricao"}, new String[]{"id", "descricao"}, new String[]{"absenteismo"}, new Object[]{Boolean.TRUE});
		afastamentosCheckList = CheckListBoxUtil.populaCheckListBox(afastamentos, "getId", "getDescricao", null);		

		CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);
		CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
		CheckListBoxUtil.marcaCheckListBox(ocorrenciasCheckList, ocorrenciasCheck);

		return Action.SUCCESS;
	}

	public String relatorioAbsenteismo() throws Exception 
	{
		Date dataIni = DateUtil.criarDataMesAno(dataDe);
		Date dataFim = DateUtil.getUltimoDiaMes(DateUtil.criarDataMesAno(dataAte));
		
		if(DateUtil.mesesEntreDatas(dataIni, dataFim) >= 12)//imundo, tem que ser maior igual
		{
			addActionWarning("Não é permitido um período maior que 12 meses para a geração deste relatório");
			prepareRelatorioAbsenteismo();
			return Action.INPUT;
		}
		
		try 
		{
			AbsenteismoCollection absenteismoCollection = new AbsenteismoCollection();
			absenteismoCollection.setAbsenteismos(colaboradorOcorrenciaManager.montaAbsenteismo(dataIni, dataFim, Arrays.asList(getEmpresaSistema().getId()), LongUtil.arrayStringToCollectionLong(estabelecimentosCheck), LongUtil.arrayStringToCollectionLong(areasCheck), LongUtil.arrayStringToCollectionLong(ocorrenciasCheck), LongUtil.arrayStringToCollectionLong(afastamentosCheck), null, getEmpresaSistema()));
			dataSource = Arrays.asList(absenteismoCollection);
			
			String filtro = montaFiltroRelatorio();
			parametros = RelatorioUtil.getParametrosRelatorio("Absenteísmo", getEmpresaSistema(), filtro);
			
			return Action.SUCCESS;
		} catch (ColecaoVaziaException e) {

			addActionMessage(e.getMessage());
			prepareRelatorioAbsenteismo();
			return Action.INPUT;
		}
	}
	
	private String montaFiltroRelatorio() {
		String filtro =  "Período: " + dataDe + " a " + dataAte;

		if (estabelecimentosCheck != null && estabelecimentosCheck.length > 0)
			filtro +=  "\nEstabelecimentos: " + StringUtil.subStr(estabelecimentoManager.nomeEstabelecimentos(LongUtil.arrayStringToArrayLong(estabelecimentosCheck), null), 90, "...");
		else
			filtro +=  "\nTodos os Estabelecimentos";
		
		if (areasCheck != null && areasCheck.length > 0)
			filtro +=  "\nÁreas Organizacionais: " + areaOrganizacionalManager.nomeAreas(LongUtil.arrayStringToArrayLong(areasCheck));
		else
			filtro +=  "\nTodas as Áreas Organizacionais";
		
		return filtro;
	}

	public void setColaboradorOcorrencia(ColaboradorOcorrencia colaboradorOcorrencia)
	{
		this.colaboradorOcorrencia = colaboradorOcorrencia;
	}

	public void setColaboradorOcorrenciaManager(ColaboradorOcorrenciaManager colaboradorOcorrenciaManager)
	{
		this.colaboradorOcorrenciaManager = colaboradorOcorrenciaManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setOcorrenciaManager(OcorrenciaManager ocorrenciaManager)
	{
		this.ocorrenciaManager = ocorrenciaManager;
	}

	public Collection<Ocorrencia> getOcorrencias()
	{
		return ocorrencias;
	}

	public void setOcorrencias(Collection<Ocorrencia> ocorrencias)
	{
		this.ocorrencias = ocorrencias;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Ocorrencia getOcorrencia()
	{
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia)
	{
		this.ocorrencia = ocorrencia;
	}

	public Collection<ColaboradorOcorrencia> getColaboradorOcorrencias()
	{
		return colaboradorOcorrencias;
	}

	public void setHistoricoColaboradorManager(HistoricoColaboradorManager historicoColaboradorManager)
	{
		this.historicoColaboradorManager = historicoColaboradorManager;
	}

	public HistoricoColaborador getHistoricoColab()
	{
		return historicoColab;
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setAreasCheck(String[] areasCheck) {
		this.areasCheck = areasCheck;
	}

	public void setEstabelecimentosCheck(String[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public Collection<CheckBox> getAreasCheckList() {
		return areasCheckList;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}

	public String getDataDe() {
		return dataDe;
	}

	public void setDataDe(String dataDe) {
		this.dataDe = dataDe;
	}

	public String getDataAte() {
		return dataAte;
	}

	public void setDataAte(String dataAte) {
		this.dataAte = dataAte;
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public Collection<AbsenteismoCollection> getDataSource() {
		return dataSource;
	}

	public Collection<CheckBox> getOcorrenciasCheckList() {
		return ocorrenciasCheckList;
	}

	public void setOcorrenciasCheck(String[] ocorrenciasCheck) {
		this.ocorrenciasCheck = ocorrenciasCheck;
	}

	public void setProvidenciaManager(ProvidenciaManager providenciaManager) {
		this.providenciaManager = providenciaManager;
	}

	public Collection<Providencia> getProvidencias() {
		return providencias;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager) {
		this.afastamentoManager = afastamentoManager;
	}

	public void setAfastamentosCheck(String[] afastamentosCheck) {
		this.afastamentosCheck = afastamentosCheck;
	}

	public Collection<CheckBox> getAfastamentosCheckList() {
		return afastamentosCheckList;
	}

	public String[] getAfastamentosCheck() {
		return afastamentosCheck;
	}

	public boolean isSomenteDesligados() {
		return somenteDesligados;
	}

	public void setSomenteDesligados(boolean somenteDesligados) {
		this.somenteDesligados = somenteDesligados;
	}
}