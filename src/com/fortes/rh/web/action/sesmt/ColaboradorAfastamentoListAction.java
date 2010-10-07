package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;


public class ColaboradorAfastamentoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ColaboradorAfastamentoManager colaboradorAfastamentoManager = null;
	private Collection<ColaboradorAfastamento> colaboradorAfastamentos = null;
	private ColaboradorAfastamento colaboradorAfastamento;
	private AfastamentoManager afastamentoManager;

	private Collection<Afastamento> afastamentos;
	private String nomeBusca;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();
	private String[] estabelecimentosCheck;
	private EstabelecimentoManager estabelecimentoManager;
	private boolean ordenaColaboradorPorNome;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	public String list() throws Exception
	{
		if (!validaPeriodo())
			return SUCCESS;

		setTotalSize(colaboradorAfastamentoManager.getCount(getEmpresaSistema().getId(), nomeBusca, estabelecimentosCheck, colaboradorAfastamento));
		colaboradorAfastamentos = colaboradorAfastamentoManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), nomeBusca, estabelecimentosCheck, colaboradorAfastamento, "DESC", false);
	
		afastamentos = afastamentoManager.findAll();

		return SUCCESS;
	}

	private boolean validaPeriodo()
	{
		ColaboradorAfastamento tmp = getColaboradorAfastamento();
		if (tmp.getInicio() != null && tmp.getFim() != null)
		{
			if (tmp.getFim().before(tmp.getInicio()))
			{
				addActionError("Data final anterior à data inicial do período.");
				return false;
			}
		}
		return true;
	}

	public String delete() throws Exception
	{
		colaboradorAfastamentoManager.remove(colaboradorAfastamento.getId());

		return SUCCESS;
	}

	public String prepareRelatorio() throws Exception
	{

		afastamentos = afastamentoManager.findAll();

		Collection<Estabelecimento> estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		estabelecimentosCheckList = CheckListBoxUtil.populaCheckListBox(estabelecimentos, "getId", "getNome");
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, estabelecimentosCheck);

		return SUCCESS;
	}

	public String relatorioAfastamentos() throws Exception
	{
		try
		{
			if (!validaPeriodo())
				return INPUT;
			
			colaboradorAfastamentos = colaboradorAfastamentoManager.findRelatorioAfastamentos(getEmpresaSistema().getId(), nomeBusca, estabelecimentosCheck, colaboradorAfastamento, ordenaColaboradorPorNome);
			parametros = RelatorioUtil.getParametrosRelatorio("Afastamentos", getEmpresaSistema(), getPeriodoFormatado());
		}
		catch (ColecaoVaziaException e)
		{
			addActionMessage(e.getMessage());
			prepareRelatorio();
			return INPUT;
		}

		return SUCCESS;
	}

	private String getPeriodoFormatado()
	{
		String periodoFormatado = "-";
		if (colaboradorAfastamento.getInicio() != null && colaboradorAfastamento.getFim() != null)
			periodoFormatado = "Período: " + DateUtil.formataDiaMesAno(colaboradorAfastamento.getInicio()) + " - " + DateUtil.formataDiaMesAno(colaboradorAfastamento.getFim());

		return periodoFormatado;
	}

	public Collection<ColaboradorAfastamento> getColaboradorAfastamentos()
	{
		return colaboradorAfastamentos;
	}

	public ColaboradorAfastamento getColaboradorAfastamento()
	{
		if(colaboradorAfastamento == null){
			colaboradorAfastamento = new ColaboradorAfastamento();
		}
		return colaboradorAfastamento;
	}

	public void setColaboradorAfastamento(ColaboradorAfastamento colaboradorAfastamento){
		this.colaboradorAfastamento=colaboradorAfastamento;
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager){
		this.colaboradorAfastamentoManager=colaboradorAfastamentoManager;
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

	public Collection<Afastamento> getAfastamentos()
	{
		return afastamentos;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager)
	{
		this.afastamentoManager = afastamentoManager;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public boolean isOrdenaColaboradorPorNome() {
		return ordenaColaboradorPorNome;
	}

	public void setOrdenaColaboradorPorNome(boolean ordenaColaboradorPorNome) {
		this.ordenaColaboradorPorNome = ordenaColaboradorPorNome;
	}

}