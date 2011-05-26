package com.fortes.rh.web.action.captacao.indicador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.DuracaoPreenchimentoVagaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("unchecked")
public class IndicadorDuracaoPreenchimentoVagaListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private Solicitacao solicitacao;
	private Collection indicadorDuracaoPreenchimentoVagas;
	private IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga;

	private Date dataDe;
	private Date dataAte;
	private String[] areasCheck;
	private String[] estabelecimentosCheck;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();

	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private AreaOrganizacionalManager areaOrganizacionalManager;
	private DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager;
	private EstabelecimentoManager estabelecimentoManager;

	private Collection<IndicadorDuracaoPreenchimentoVaga> indicador;
	private Map<String, Object> parametros = new HashMap<String, Object>();

	private String reportFilter;
	private String reportTitle;
	
	public String prepare() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String prepareMotivo() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String list() throws Exception  
	{
		Collection<Long> areasOrganizacionais = LongUtil.arrayStringToCollectionLong(areasCheck);
		Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);

		try
		{
			indicador = duracaoPreenchimentoVagaManager.gerarIndicadorDuracaoPreenchimentoVagas(dataDe, dataAte, areasOrganizacionais,estabelecimentos, getEmpresaSistema().getId());

			reportFilter = "Período: " + DateUtil.formataDiaMesAno(dataDe) + " a " + DateUtil.formataDiaMesAno(dataAte);
			reportTitle = "Indicador de Duração para preenchimento de Vagas";

			parametros = getParametrosRelatorio(reportTitle, reportFilter);

			return Action.SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			prepare();
			areasCheckList = CheckListBoxUtil.marcaCheckListBox(areasCheckList, areasCheck);
			estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

			addActionMessage(e.getMessage());

			return Action.INPUT;
		}
	}

	public String listMotivos() throws Exception 
	{
		Collection<Long> areasOrganizacionais = LongUtil.arrayStringToCollectionLong(areasCheck);
		Collection<Long> estabelecimentos = LongUtil.arrayStringToCollectionLong(estabelecimentosCheck);

//		if(areaOrganizacionalManager.findAreasQueNaoPertencemAEmpresa(areasOrganizacionais, getEmpresaSistema()))
//		{
//			prepareMotivo();
//			addActionError("Algumas das Áreas Organizacionais selecionadas não existem na empresa " + getEmpresaSistema().getNome() +".");
//			return "acessonegado";
//		}

		try
		{
			indicador = duracaoPreenchimentoVagaManager.gerarIndicadorMotivoPreenchimentoVagas(dataDe, dataAte, areasOrganizacionais,estabelecimentos, getEmpresaSistema().getId());
			
			reportFilter = "Período: " + DateUtil.formataDiaMesAno(dataDe) + " a " + DateUtil.formataDiaMesAno(dataAte);
			
			parametros = getParametrosRelatorio("Indicador - Estatísticas de Vagas por Motivo", reportFilter);
			
			return Action.SUCCESS;
		} 
		catch (ColecaoVaziaException e)
		{
			prepareMotivo();
			addActionMessage(e.getMessage());
			return Action.INPUT;
		}
	}

	private Map<String, Object> getParametrosRelatorio(String titulo, String filtro)
	{
		return RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), filtro);
	}

	public Collection getIndicadorDuracaoPreenchimentoVagas() {
		return indicadorDuracaoPreenchimentoVagas;
	}

	public IndicadorDuracaoPreenchimentoVaga getIndicadorDuracaoPreenchimentoVaga()
	{
		if(indicadorDuracaoPreenchimentoVaga == null)
			indicadorDuracaoPreenchimentoVaga = new IndicadorDuracaoPreenchimentoVaga();
		return indicadorDuracaoPreenchimentoVaga;
	}

	public void setIndicadorDuracaoPreenchimentoVaga(IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga)
	{
		this.indicadorDuracaoPreenchimentoVaga=indicadorDuracaoPreenchimentoVaga;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao=solicitacao;
	}

	public Date getDataAte()
	{
		return dataAte;
	}

	public void setDataAte(Date dataAte)
	{
		this.dataAte = dataAte;
	}

	public Date getDataDe()
	{
		return dataDe;
	}

	public void setDataDe(Date dataDe)
	{
		this.dataDe = dataDe;
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

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void setIndicadorDuracaoPreenchimentoVagas(Collection indicadorDuracaoPreenchimentoVagas)
	{
		this.indicadorDuracaoPreenchimentoVagas = indicadorDuracaoPreenchimentoVagas;
	}

	public Collection<IndicadorDuracaoPreenchimentoVaga> getIndicador()
	{
		return indicador;
	}

	public void setIndicador(Collection<IndicadorDuracaoPreenchimentoVaga> indicador)
	{
		this.indicador = indicador;
	}

	public void setDuracaoPreenchimentoVagaManager(DuracaoPreenchimentoVagaManager duracaoPreenchimentoVagaManager)
	{
		this.duracaoPreenchimentoVagaManager = duracaoPreenchimentoVagaManager;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList()
	{
		return estabelecimentosCheckList;
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

	public void setEstabelecimentosCheckList(Collection<CheckBox> estabelecimentosCheckList)
	{
		this.estabelecimentosCheckList = estabelecimentosCheckList;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros)
	{
		this.parametros = parametros;
	}

	public String getReportFilter() {
		return reportFilter;
	}

	public String getReportTitle() {
		return reportTitle;
	}
	
}